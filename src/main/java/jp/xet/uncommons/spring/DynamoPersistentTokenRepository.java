/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/10/06
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.xet.uncommons.spring;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.Assert;

/**
 * Amazon DynamoDB based persistent login token repository implementation.
 * 
 * @since 1.7
 * @version $Id$
 * @author daisuke
 */
public class DynamoPersistentTokenRepository implements PersistentTokenRepository, InitializingBean {
	
	private static Logger logger = LoggerFactory.getLogger(DynamoPersistentTokenRepository.class);
	
	private static final String USERNAME = "Username";
	
	private static final String SERIES = "Series";
	
	private static final String TOKEN = "Token";
	
	private static final String LAST_USED = "LastUsed";
	
	private boolean createTableOnStartup;
	
	private AmazonDynamoDB dynamoDb;
	
	private String persistentLoginTable;
	
	private long defaultReadCapacityUnits = 10L;
	
	private long defaultWriteCapacityUnits = 5L;
	
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(dynamoDb, "AmazonDynamoDB required");
		Assert.notNull(persistentLoginTable, "persistentLoginTable required");
		
		if (createTableOnStartup && existTable(persistentLoginTable) == false) {
			createTable(persistentLoginTable);
		}
	}
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		Assert.notNull(token);
		if (logger.isTraceEnabled()) {
			logger.trace("Create token: username={}, series={}, tokenValue={}, date={}", new Object[] {
				token.getUsername(),
				token.getSeries(),
				token.getTokenValue(),
				token.getDate()
			});
		}
		
		try {
			Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
			item.put(USERNAME, new AttributeValue(token.getUsername()));
			item.put(SERIES, new AttributeValue(token.getSeries()));
			item.put(TOKEN, new AttributeValue(token.getTokenValue()));
			item.put(LAST_USED, new AttributeValue(DateUtils.formatISO8601Date(token.getDate())));
			
			PutItemRequest putRequest = new PutItemRequest()
				.withTableName(persistentLoginTable)
				.withItem(item);
			PutItemResult result = dynamoDb.putItem(putRequest);
			if (logger.isDebugEnabled()) {
				logger.debug("Token created: {}", result);
			}
		} catch (Exception e) {
			logger.error("unknown exception", e);
		}
	}
	
	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		if (logger.isTraceEnabled()) {
			logger.trace("Retrieve token: seriesId={}", seriesId);
		}
		
		try {
			GetItemRequest getRequest = new GetItemRequest()
				.withTableName(persistentLoginTable)
				.withKey(Collections.singletonMap(SERIES, new AttributeValue(seriesId)));
			GetItemResult result = dynamoDb.getItem(getRequest);
			if (logger.isDebugEnabled()) {
				logger.debug("Token retrieved: {}", result);
			}
			
			Map<String, AttributeValue> item = result.getItem();
			if (item == null) {
				if (logger.isInfoEnabled()) {
					logger.info("Querying token for series '{}' returned no results.", seriesId);
				}
				return null;
			}
			
			String username = item.get(USERNAME).getS();
			String series = item.get(SERIES).getS();
			String tokenValue = item.get(TOKEN).getS();
			Date lastUsed = DateUtils.parseISO8601Date(item.get(LAST_USED).getS());
			return new PersistentRememberMeToken(username, series, tokenValue, lastUsed);
		} catch (AmazonServiceException e) {
			logger.error("Failed to load token for series " + seriesId, e);
		} catch (AmazonClientException e) {
			logger.error("Failed to load token for series " + seriesId, e);
		} catch (Exception e) {
			logger.error("unknown exception", e);
		}
		return null;
	}
	
	@Override
	public void removeUserTokens(String username) {
		if (logger.isTraceEnabled()) {
			logger.trace("Remove token: username={}", username);
		}
		
		try {
			Condition cond = new Condition()
				.withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue(username));
			
			ScanRequest scanRequest = new ScanRequest()
				.withTableName(persistentLoginTable)
				.withAttributesToGet(SERIES)
				.withScanFilter(Collections.singletonMap(USERNAME, cond));
			ScanResult result = dynamoDb.scan(scanRequest);
			
			List<WriteRequest> writeRequests = Lists.newArrayListWithCapacity(result.getCount());
			for (Map<String, AttributeValue> item : result.getItems()) {
				DeleteRequest deleteRequest = new DeleteRequest()
					.withKey(Collections.singletonMap(SERIES, item.get(SERIES)));
				writeRequests.add(new WriteRequest().withDeleteRequest(deleteRequest));
			}
			
			Map<String, List<WriteRequest>> requestItems = Maps.newHashMapWithExpectedSize(0);
			requestItems.put(persistentLoginTable, writeRequests);
			
			BatchWriteItemResult batchItemResult;
			do {
				BatchWriteItemRequest batchWriteItemRequest =
						new BatchWriteItemRequest().withRequestItems(requestItems);
				batchItemResult = dynamoDb.batchWriteItem(batchWriteItemRequest);
				requestItems = batchItemResult.getUnprocessedItems();
				if (logger.isDebugEnabled()) {
					logger.debug("Token removed: {}", batchItemResult);
				}
			} while (batchItemResult.getUnprocessedItems().size() > 0);
		} catch (Exception e) {
			logger.error("unknown exception", e);
		}
	}
	
	@SuppressWarnings("javadoc")
	public void setCreateTableOnStartup(boolean createTableOnStartup) {
		this.createTableOnStartup = createTableOnStartup;
	}
	
	@SuppressWarnings("javadoc")
	public void setDefaultReadCapacityUnits(long defaultReadCapacityUnits) {
		this.defaultReadCapacityUnits = defaultReadCapacityUnits;
	}
	
	@SuppressWarnings("javadoc")
	public void setDefaultWriteCapacityUnits(long defaultWriteCapacityUnits) {
		this.defaultWriteCapacityUnits = defaultWriteCapacityUnits;
	}
	
	@SuppressWarnings("javadoc")
	public void setDynamoDb(AmazonDynamoDB dynamoDb) {
		Assert.notNull(dynamoDb);
		this.dynamoDb = dynamoDb;
	}
	
	@SuppressWarnings("javadoc")
	public void setPersistentLoginTable(String persistentLoginTable) {
		Assert.notNull(persistentLoginTable);
		this.persistentLoginTable = persistentLoginTable;
	}
	
	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		if (logger.isTraceEnabled()) {
			logger.trace("Update token: series={}, tokenValue={}, lastUsed={}", new Object[] {
				series,
				tokenValue,
				lastUsed
			});
		}
		
		try {
			String now = DateUtils.formatISO8601Date(new Date());
			Map<String, AttributeValueUpdate> attributeUpdates = Maps.newHashMapWithExpectedSize(2);
			attributeUpdates.put(TOKEN, new AttributeValueUpdate(new AttributeValue(tokenValue), AttributeAction.PUT));
			attributeUpdates.put(LAST_USED, new AttributeValueUpdate(new AttributeValue(now), AttributeAction.PUT));
			
			UpdateItemRequest updateRequest = new UpdateItemRequest()
				.withTableName(persistentLoginTable)
				.withKey(Collections.singletonMap(SERIES, new AttributeValue(series)))
				.withAttributeUpdates(attributeUpdates);
			UpdateItemResult result = dynamoDb.updateItem(updateRequest);
			if (logger.isDebugEnabled()) {
				logger.debug("Token updated: {}", result);
			}
		} catch (Exception e) {
			logger.error("unknown exception", e);
		}
	}
	
	private void createTable(String tableName) {
		if (logger.isTraceEnabled()) {
			logger.trace("DynamoDB table create: {}", tableName);
		}
		
		KeySchemaElement kse = new KeySchemaElement()
			.withKeyType(KeyType.HASH)
			.withAttributeName(SERIES);
		
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
			.withReadCapacityUnits(defaultReadCapacityUnits)
			.withWriteCapacityUnits(defaultWriteCapacityUnits);
		
		CreateTableRequest request = new CreateTableRequest()
			.withTableName(tableName)
			.withKeySchema(kse)
			.withProvisionedThroughput(provisionedThroughput);
		
		CreateTableResult result = dynamoDb.createTable(request);
		if (logger.isInfoEnabled()) {
			logger.info("DynamoDB table created: {}", result);
		}
	}
	
	private boolean existTable(String tableName) {
		DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
		try {
			DescribeTableResult result = dynamoDb.describeTable(describeTableRequest);
			if (logger.isTraceEnabled()) {
				logger.trace("DynamoDB table '{}' found: {}", tableName, result);
			}
			return true;
		} catch (ResourceNotFoundException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("DynamoDB table '{}' not found", tableName);
			}
		}
		return false;
	}
}
