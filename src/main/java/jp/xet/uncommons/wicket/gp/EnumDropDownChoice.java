/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/28
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

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;

/**
 * TODO for daisuke
 * 
 * @param <T> type of enum
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class EnumDropDownChoice<T extends Enum<T>> extends DropDownChoice<T> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz {@link Enum} class
	 * @since 1.0
	 */
	public EnumDropDownChoice(String id, Class<T> clazz) {
		super(id, Arrays.asList(clazz.getEnumConstants()), new EnumChoiceRenderer<T>());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz {@link Enum} class
	 * @param renderer {@link IChoiceRenderer} implementation
	 * @since 1.0
	 */
	public EnumDropDownChoice(String id, Class<T> clazz, IChoiceRenderer<? super T> renderer) {
		super(id, Arrays.asList(clazz.getEnumConstants()), renderer);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param clazz {@link Enum} class
	 * @since 1.0
	 */
	public EnumDropDownChoice(String id, IModel<T> model, Class<T> clazz) {
		super(id, model, Arrays.asList(clazz.getEnumConstants()), new EnumChoiceRenderer<T>());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param clazz {@link Enum} class
	 * @param renderer {@link IChoiceRenderer} implementation
	 * @since 1.0
	 */
	public EnumDropDownChoice(String id, IModel<T> model, Class<T> clazz, IChoiceRenderer<? super T> renderer) {
		super(id, model, Arrays.asList(clazz.getEnumConstants()), renderer);
	}
	
	@Override
	protected T convertChoiceIdToChoice(String id) {
		T visibility = super.convertChoiceIdToChoice(id);
		if (isNullValid() == false && visibility == null) {
			onInvalidNull();
		}
		return visibility;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @since 1.1
	 */
	protected void onInvalidNull() {
		throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_BAD_REQUEST);
	}
}
