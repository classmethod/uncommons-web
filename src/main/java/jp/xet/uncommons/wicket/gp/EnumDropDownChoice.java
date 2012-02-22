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

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class EnumDropDownChoice<T extends Enum<T>> extends DropDownChoice<T> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id 
	 * @param model 
	 * @param clazz 
	 */
	public EnumDropDownChoice(String id, IModel<T> model, Class<T> clazz) {
		super(id, model, Arrays.asList(clazz.getEnumConstants()), new EnumChoiceRenderer<T>());
	}
}
