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

import java.util.TimeZone;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public final class TimeZoneUtil {
	
	/**
	 * クライアントのタイムゾーンを取得する。
	 * 
	 * <p>クライアントのJavaScript環境より、リダイレクトを利用してタイムゾーンを取得する。
	 * クライアント側でJavaScriptが無効な場合、{@code null}を返すことに注意。</p>
	 * 
	 * @param component 
	 * @return タイムゾーン。取得に失敗した場合は{@code null}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	public static TimeZone getTimeZone(Component component) {
		Validate.notNull(component);
		WebClientInfo wci = (WebClientInfo) component.getSession().getClientInfo();
		ClientProperties properties = wci.getProperties();
		TimeZone timeZone = properties.getTimeZone();
		return timeZone;
	}
	
	private TimeZoneUtil() {
	}
}
