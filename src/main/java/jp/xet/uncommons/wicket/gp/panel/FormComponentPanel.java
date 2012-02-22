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

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * {@link FormComponent}をラップした{@link Panel}の骨格実装クラス。
 * 
 * <p>通常の {@link FormComponent} はHTMLのノードチェックを行う。例えば {@link PasswordTextField} は
 * input要素で、type属性に{@code password}が設定されていなければならない。この制約を回避し、
 * 複数種類の {@link FormComponent} を {@link Panel} としてラップし、抽象化するためのクラスである。</p>
 * 
 * @param <T> モデルの型
 * @param <F> {@link FormComponent}実装クラスの型
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class FormComponentPanel<T, F extends FormComponent<T>> extends Panel {
	
	private static final String FORM_COMPONENT_ID = "formComponent";
	
	private F formComponent;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public FormComponentPanel(String id, IModel<?> model) {
		super(id, model);
	}
	
	/**
	 * この {@link Panel}がラップする {@link FormComponent} を取得する。
	 * 
	 * <p>ただし、このパネルの初期化（{@link #onInitialize()}）以前は{@code null}を返すことに注意すること。</p>
	 * 
	 * @return この {@link Panel}がラップする {@link FormComponent}
	 * @since 1.0
	 */
	public final F getFormComponent() {
		return formComponent;
	}
	
	/**
	 * {@link FormComponent}のインスタンスを生成する。
	 * 
	 * @param id {@link FormComponent}のID
	 * @param model {@link FormComponent}に設定するモデル
	 * @return 生成した {@link FormComponent}
	 * @since 1.0
	 */
	protected abstract F createFormComponent(String id, IModel<T> model);
	
	@Override
	@SuppressWarnings("unchecked")
	protected final void onInitialize() {
		super.onInitialize();
		
		formComponent = createFormComponent(FORM_COMPONENT_ID, (IModel<T>) getDefaultModel());
		add(formComponent);
	}
}
