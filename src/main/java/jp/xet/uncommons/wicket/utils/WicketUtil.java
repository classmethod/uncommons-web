/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/18
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
package jp.xet.uncommons.wicket.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Wicket関連のユーティリティクラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public final class WicketUtil {
	
	/**
	 * 引数が{@code null}でない場合、デタッチを行う。
	 * 
	 * @param detachables デタッチするオブジェクト
	 * @since 1.0
	 */
	public static void detachIfNotNull(IDetachable... detachables) {
		for (IDetachable detachable : detachables) {
			if (detachable != null) {
				detachable.detach();
			}
		}
	}
	
	/**
	 * 現在処理中の{@link HttpServletRequest}を返す。
	 * 
	 * @return {@link HttpServletRequest}
	 * @since 1.0
	 */
	public static HttpServletRequest getHttpServletRequest() {
		RequestCycle requestCycle = RequestCycle.get();
		if (requestCycle == null) {
			return null;
		}
		Request request = requestCycle.getRequest();
		return (HttpServletRequest) request.getContainerRequest();
	}
	
	/**
	 * 現在処理中の{@link HttpServletResponse}を返す。
	 * 
	 * @return {@link HttpServletResponse}
	 * @since 1.0
	 */
	public static HttpServletResponse getHttpServletResponse() {
		RequestCycle requestCycle = RequestCycle.get();
		if (requestCycle == null) {
			return null;
		}
		Response response = requestCycle.getResponse();
		return (HttpServletResponse) response.getContainerResponse();
	}
	
	private WicketUtil() {
	}
}
