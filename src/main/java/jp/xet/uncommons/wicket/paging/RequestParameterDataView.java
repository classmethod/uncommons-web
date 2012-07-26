/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/12/08
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
package jp.xet.uncommons.wicket.paging;

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * TODO for daisuke
 * 
 * @param <T> The Model type.
 * @since 1.0
 * @version $Id: RequestParameterDataView.java 4540 2012-03-29 05:20:55Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class RequestParameterDataView<T> extends DataView<T> {
	
	/** TODO for daisuke */
	public static final String DEFAULT_PARAM_KEY_PAGE = "page";
	
	private final String paramKey;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 */
	public RequestParameterDataView(String id, IDataProvider<T> dataProvider) {
		this(id, dataProvider, DEFAULT_PARAM_KEY_PAGE);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param itemsPerPage items per page
	 */
	public RequestParameterDataView(String id, IDataProvider<T> dataProvider, int itemsPerPage) {
		this(id, dataProvider, itemsPerPage, DEFAULT_PARAM_KEY_PAGE);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param itemsPerPage items per page
	 * @param paramKey ページ数を格納する {@link PageParameters}用のキー文字列
	 */
	public RequestParameterDataView(String id, IDataProvider<T> dataProvider, int itemsPerPage, String paramKey) {
		super(id, dataProvider, itemsPerPage);
		this.paramKey = paramKey;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param paramKey 
	 */
	public RequestParameterDataView(String id, IDataProvider<T> dataProvider, String paramKey) {
		super(id, dataProvider);
		this.paramKey = paramKey;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return
	 * @since 1.0
	 */
	public String getParamKey() {
		return paramKey;
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		setCurrentPage(paramKey);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param paramKey
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	protected void setCurrentPage(String paramKey) {
		Validate.notNull(paramKey);
		
		String pageNumberStr = getRequest().getRequestParameters().getParameterValue(paramKey).toString();
		if (pageNumberStr == null) {
			return;
		}
		if (pageNumberStr.contains(".wicket-")) {
			pageNumberStr = pageNumberStr.substring(0, pageNumberStr.indexOf(".wicket-"));
		}
		
		try {
			int pageNumber = Integer.valueOf(pageNumberStr);
			if (0 < pageNumber && pageNumber <= getPageCount()) {
				setCurrentPage(pageNumber - 1);
			}
		} catch (NumberFormatException e) {
			// ignore
		}
	}
}
