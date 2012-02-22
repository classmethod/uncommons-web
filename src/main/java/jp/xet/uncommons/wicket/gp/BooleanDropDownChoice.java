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
package jp.xet.uncommons.wicket.gp;

import java.util.Arrays;
import java.util.List;

import jp.xet.uncommons.wicket.utils.BooleanChoiceRenderer;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BooleanDropDownChoice extends DropDownChoice<Boolean> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param prefix
	 */
	public BooleanDropDownChoice(String id, IModel<Boolean> model, String prefix) {
		super(id, model, (List<Boolean>) null);
		
		this.setChoices(Arrays.asList(true, false));
		setChoiceRenderer(new BooleanChoiceRenderer(prefix, this));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param prefix
	 */
	public BooleanDropDownChoice(String id, String prefix) {
		this(id, null, prefix);
	}
}
