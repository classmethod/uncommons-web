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
package jp.xet.uncommons.wicket.gp;

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * TODO for daisuke
 * 
 * @param <T> The Model type.
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class StatelessDataView<T> extends DataView<T> {
	
	/** TODO for daisuke */
	public static final String DEFAULT_PARAM_KEY_PAGE = "page";
	
	private final String paramKey;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param itemsPerPage items per page
	 * @param params
	 */
	public StatelessDataView(String id, IDataProvider<T> dataProvider, int itemsPerPage, PageParameters params) {
		this(id, dataProvider, itemsPerPage, params, DEFAULT_PARAM_KEY_PAGE);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param itemsPerPage items per page
	 * @param params
	 * @param paramKey ページ数を格納する {@link PageParameters}用のキー文字列
	 */
	public StatelessDataView(String id, IDataProvider<T> dataProvider, int itemsPerPage, PageParameters params,
			String paramKey) {
		super(id, dataProvider, itemsPerPage);
		this.paramKey = paramKey;
		
		setCurrentPage(params, paramKey);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param params
	 */
	public StatelessDataView(String id, IDataProvider<T> dataProvider, PageParameters params) {
		this(id, dataProvider, params, DEFAULT_PARAM_KEY_PAGE);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider data provider
	 * @param params
	 * @param paramKey 
	 */
	public StatelessDataView(String id, IDataProvider<T> dataProvider, PageParameters params, String paramKey) {
		super(id, dataProvider);
		this.paramKey = paramKey;
		
		setCurrentPage(params, paramKey);
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
	protected boolean getStatelessHint() {
		return true;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param params
	 * @param paramKey
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	protected void setCurrentPage(PageParameters params, String paramKey) {
		Validate.notNull(params);
		Validate.notNull(paramKey);
		String pageNumberStr = params.get(paramKey).toString("1");
		if (pageNumberStr.contains(".wicket-")) {
			pageNumberStr = pageNumberStr.substring(0, pageNumberStr.indexOf(".wicket-"));
		}
		
		int pageNumber = 0;
		try {
			pageNumber = Integer.valueOf(pageNumberStr);
		} catch (NumberFormatException e) {
			// ignore
		}
		if (0 < pageNumber && pageNumber <= getPageCount()) {
			setCurrentPage(pageNumber - 1);
		}
	}
}
