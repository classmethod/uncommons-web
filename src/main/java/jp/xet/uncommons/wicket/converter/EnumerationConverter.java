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
 * リソースファイル内の情報によって列挙型の値を文字列に変換するコンバータ。
 * 
 * <p>リソースのキーは、列挙型の完全修飾名の後に "." と値名をつなげたものである。
 * 例えば、 {@link java.lang.Thread.State#TERMINATED} のリソースキーは
 * {@code java.lang.Thread.State.TERMINATED}となる。</p>
 * 
 * @param <T> 列挙型の型
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class EnumerationConverter<T extends Enum<T>> extends AbstractConverter<T> {
	
	private final Class<T> clazz;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param clazz 列挙型
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public EnumerationConverter(Class<T> clazz) {
		Validate.notNull(clazz);
		this.clazz = clazz;
	}
	
	@Override
	public T convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(T value, Locale locale) {
		if (value == null) {
			return null;
		}
		
		Localizer localizer = Application.get().getResourceSettings().getLocalizer();
		return localizer.getString(value.getClass().getName() + "." + value, (Component) null);
	}
	
	@Override
	protected Class<T> getTargetType() {
		return clazz;
	}
}
