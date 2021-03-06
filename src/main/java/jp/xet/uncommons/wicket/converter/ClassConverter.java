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

import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * {@link Class}用のwicket {@link IConverter}実装クラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ClassConverter extends AbstractConverter<Class<?>> {
	
	@Override
	public Class<?> convertToObject(String value, Locale locale) {
		try {
			return Class.forName(value);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	@Override
	public String convertToString(Class<?> value, Locale locale) {
		return value == null ? null : value.getName();
	}
	
	@Override
	@SuppressWarnings({
		"unchecked",
		"rawtypes"
	})
	protected Class<Class<?>> getTargetType() {
		return (Class) Class.class;
	}
}
