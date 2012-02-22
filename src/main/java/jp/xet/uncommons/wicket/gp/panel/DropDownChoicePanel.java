/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/14
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
package jp.xet.uncommons.wicket.gp.panel;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.WildcardListModel;

/**
 * {@link DropDownChoice}をラップした{@link Panel}クラス。
 * 
 * @param <T> {@link DropDownChoice}のモデルの型
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class DropDownChoicePanel<T> extends FormComponentPanel<T, DropDownChoice<T>> {
	
	private final IModel<List<? extends T>> choices;
	
	private final IChoiceRenderer<? super T> renderer;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param choices The drop down choices
	 */
	public DropDownChoicePanel(String id, IModel<T> model, IModel<List<? extends T>> choices) {
		this(id, model, choices, new ChoiceRenderer<T>());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param choices The drop down choices
	 * @param renderer The rendering engine
	 */
	public DropDownChoicePanel(String id, IModel<T> model, IModel<List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer) {
		super(id, model);
		this.choices = choices;
		this.renderer = renderer;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param choices The drop down choices
	 */
	public DropDownChoicePanel(String id, IModel<T> model, List<? extends T> choices) {
		this(id, model, new WildcardListModel<T>(choices), new ChoiceRenderer<T>());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param choices The drop down choices
	 * @param renderer The rendering engine
	 */
	public DropDownChoicePanel(String id, IModel<T> model, List<? extends T> choices,
			IChoiceRenderer<? super T> renderer) {
		this(id, model, new WildcardListModel<T>(choices), renderer);
	}
	
	@Override
	protected DropDownChoice<T> createFormComponent(String id, IModel<T> model) {
		return new DropDownChoice<T>(id, model, choices, renderer);
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if (choices != null) {
			choices.detach();
		}
	}
}
