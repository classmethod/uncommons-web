/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/09/07
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

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @param <T>
 * @since 1.7
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class NullableEnumDropDownChoice<T extends Enum<T>> extends EnumDropDownChoice<T> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz {@link Enum} class
	 * @since 1.7
	 */
	public NullableEnumDropDownChoice(String id, Class<T> clazz) {
		super(id, clazz);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz {@link Enum} class
	 * @param renderer {@link IChoiceRenderer} implementation
	 * @since 1.7
	 */
	public NullableEnumDropDownChoice(String id, Class<T> clazz, IChoiceRenderer<? super T> renderer) {
		super(id, clazz, renderer);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param clazz {@link Enum} class
	 * @since 1.7
	 */
	public NullableEnumDropDownChoice(String id, IModel<T> model, Class<T> clazz) {
		super(id, model, clazz);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param clazz {@link Enum} class
	 * @param renderer {@link IChoiceRenderer} implementation
	 * @since 1.7
	 */
	public NullableEnumDropDownChoice(String id, IModel<T> model, Class<T> clazz, IChoiceRenderer<? super T> renderer) {
		super(id, model, clazz, renderer);
	}
	
	@Override
	protected void onInvalidNull() {
	}
}
