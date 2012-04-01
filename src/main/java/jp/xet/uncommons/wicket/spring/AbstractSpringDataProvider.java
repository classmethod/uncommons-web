/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/09/29
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
package jp.xet.uncommons.wicket.spring;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Spring Dataの {@link Pageable} リクエストに対応する {@link IDataProvider} 実装クラス。
 * 
 * @param <T> モデルの型
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class AbstractSpringDataProvider<T extends Serializable> implements IDataProvider<T> {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractSpringDataProvider.class);
	
	
	/**
	 * first, count から page を算出する。
	 * 
	 * @param first number of first entry (zero order)
	 * @param count size of entries
	 * @return page number (zero order)
	 * @since 1.0.0
	 */
	protected static int getPage(int first, int count) {
		logger.debug("first={}, count={}", first, count);
		return first / count;
	}
	
	
	private final int itemsPerPage;
	
	private long cachedItemCount;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param itemsPerPage 1ページあたりのアイテム数
	 * @throws IllegalArgumentException 引数{@code itemsPerPage}が正数でなかった場合
	 */
	public AbstractSpringDataProvider(int itemsPerPage) {
		Validate.isTrue(itemsPerPage > 0);
		this.itemsPerPage = itemsPerPage;
		clearCachedItemCount();
	}
	
	@Override
	public void detach() {
		clearCachedItemCount();
	}
	
	/**
	 * エンティティ集合から、特定のページを検索して返す。
	 * 
	 * @param pageable ページング設定
	 * @return ページ
	 * @since 1.0
	 */
	public abstract Page<T> find(Pageable pageable);
	
	@Override
	public Iterator<? extends T> iterator(int first, int count) {
		Pageable request = newPageable(first, count);
		Page<T> page = find(request);
		List<T> content = page.getContent();
		if (content.size() > count) {
			content = content.subList(0, count);
		}
		return content.iterator();
	}
	
	@Override
	public IModel<T> model(T object) {
		return new Model<T>(object);
	}
	
	@Override
	public final int size() {
		if (isItemCountCached()) {
			return (int) getCachedItemCount();
		}
		long count = internalGetItemCount();
		setCachedItemCount(count);
		return (int) count;
	}
	
	/**
	 * @return total item count
	 */
	protected abstract long internalGetItemCount();
	
	protected Pageable newPageable(int first, int count) {
		return new PageRequest(getPage(first, itemsPerPage), itemsPerPage);
	}
	
	private void clearCachedItemCount() {
		cachedItemCount = -1;
	}
	
	private long getCachedItemCount() {
		if (cachedItemCount < 0) {
			throw new IllegalStateException("getItemCountCache() called when cache was not set");
		}
		return cachedItemCount;
	}
	
	private boolean isItemCountCached() {
		return cachedItemCount >= 0;
	}
	
	private void setCachedItemCount(long itemCount) {
		cachedItemCount = itemCount;
	}
}
