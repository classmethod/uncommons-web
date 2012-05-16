/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/04/18
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
package jp.xet.uncommons.wicket.aggregateresource;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.resource.filtering.JavaScriptFilteredIntoFooterHeaderResponse;

/**
 * TODO for daisuke
 * 
 * @since TODO for daisuke
 * @version $Id$
 * @author daisuke
 */
public class ResourceAggregationDecorator implements IHeaderResponseDecorator {
	
	private final String footerBucketName;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param footerBucketName the name of the bucket that you will use for your footer container
	 * (see the class javadocs for a reminder about putting this container in your footer)
	 */
	public ResourceAggregationDecorator(String footerBucketName) {
		this.footerBucketName = footerBucketName;
	}
	
	@Override
	public IHeaderResponse decorate(IHeaderResponse response) {
		// use grouping header response for the CSS resources, this way we can load several
		// .css files in one http request. See HomePage#renderHead() header.css and
		// footer.css
		GroupingHeaderResponse groupingHeaderResponse = new GroupingHeaderResponse(response);
		
		// use this header resource decorator to load all JavaScript resources in the page
		// footer (after </body>)
		JavaScriptFilteredIntoFooterHeaderResponse javaScriptFooterResponse =
				new JavaScriptFilteredIntoFooterHeaderResponse(response, footerBucketName);
		
		// finally use one that delegates to the two above
		return new GroupingAndFilteringHeaderResponse(groupingHeaderResponse, javaScriptFooterResponse);
	}
}
