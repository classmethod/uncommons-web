/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/09/26
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

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * 値と表示値（ラベル）を個別指定する {@link IChoiceRenderer} 実装クラス。
 * 
 * @param <T> 値の型
 * @since 1.0.0
 * @version $Id: LabelValueChoiceRenderer.java 207 2011-10-02 14:13:40Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class LabelValueChoiceRenderer<T> implements IChoiceRenderer<T> {
	
	private final List<T> values = Lists.newArrayList();
	
	private final Map<T, String> displayValues = Maps.newHashMap();
	
	
	/**
	 * 値と表示値（ラベル）の組を追加する。
	 * 
	 * @param value 値
	 * @param displayValue 表示値（ラベル）
	 * @return {@code this}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	public LabelValueChoiceRenderer<T> addValue(T value, String displayValue) {
		Validate.notNull(value);
		Validate.notNull(displayValue);
		this.values.add(value);
		this.displayValues.put(value, displayValue);
		return this;
	}
	
	@Override
	public String getDisplayValue(T object) {
		return displayValues.get(object);
	}
	
	@Override
	public String getIdValue(T object, int index) {
		return object.toString();
	}
	
	/**
	 * 値のリストを返す。
	 * 
	 * @return 値のリスト
	 * @since 1.0.0
	 */
	public List<T> getValues() {
		return Lists.newArrayList(values);
	}
}
