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

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

/**
 * {@link TextArea}用{@link FormComponentPanel}実装クラス。
 * 
 * @param <T> {@link TextArea}のモデルの型
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class TextAreaPanel<T> extends FormComponentPanel<T, TextArea<T>> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public TextAreaPanel(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	protected TextArea<T> createFormComponent(String id, IModel<T> model) {
		return new TextArea<T>(id, model);
	}
}
