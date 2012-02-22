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

import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * {@link Locale} を文字列に変換するコンバータ。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class LocaleConverter extends AbstractConverter<Locale> {
	
	@Override
	public Locale convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(Locale value, Locale locale) {
		return value.getDisplayLanguage(locale) + " (" + value.getDisplayLanguage(value) + ")";
	}
	
	@Override
	protected Class<Locale> getTargetType() {
		return Locale.class;
	}
}
