/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/19
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
package jp.xet.uncommons.wicket.converter;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * リソースファイル内の情報によって {@link Boolean} 値を文字列に変換するコンバータ。
 * 
 * <p>リソースのキーは、指定した{@code prefix}の後に "." と値名をつなげたものである。
 * 例えば、{@code prefix}を{@code foo}とした場合、{@code true}のリソースキーは
 * {@code foo.true}となる。</p>
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BooleanConverter extends AbstractConverter<Boolean> {
	
	private final String prefix;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param prefix リソースキーの接頭辞
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public BooleanConverter(String prefix) {
		Validate.notNull(prefix);
		this.prefix = prefix;
	}
	
	@Override
	public Boolean convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(Boolean value, Locale locale) {
		if (value == null) {
			return null;
		}
		
		Localizer localizer = Application.get().getResourceSettings().getLocalizer();
		return localizer.getString(prefix + "." + value, (Component) null, (String) null);
	}
	
	@Override
	protected Class<Boolean> getTargetType() {
		return Boolean.class;
	}
}
