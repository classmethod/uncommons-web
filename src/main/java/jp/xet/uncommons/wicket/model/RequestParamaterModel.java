/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/09/11
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
package jp.xet.uncommons.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;

/**
 * リクエストパラメータの値モデル。
 * 
 * @since 1.7
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class RequestParamaterModel extends LoadableDetachableModel<String> {
	
	private final String paramName;
	
	private final String defaultValue;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param paramName リクエストパラメータ名
	 * @since 1.7
	 */
	public RequestParamaterModel(String paramName) {
		this(paramName, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param paramName リクエストパラメータ名
	 * @param defaultValue デフォルト値
	 * @since 1.8
	 */
	public RequestParamaterModel(String paramName, String defaultValue) {
		this.paramName = paramName;
		this.defaultValue = defaultValue;
	}
	
	@Override
	protected String load() {
		return getValue(paramName).toString(defaultValue);
	}
	
	private StringValue getValue(String paramName) {
		RequestCycle requestCycle = RequestCycle.get();
		Request request = requestCycle.getRequest();
		IRequestParameters requestParameters = request.getRequestParameters();
		return requestParameters.getParameterValue(paramName);
	}
}
