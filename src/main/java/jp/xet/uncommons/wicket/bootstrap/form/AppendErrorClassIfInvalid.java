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
package jp.xet.uncommons.wicket.bootstrap.form;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

/**
 * 指定したコンポーネントでエラーが発生した際、CSS class "error" を追加するビヘイビア。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class AppendErrorClassIfInvalid extends AttributeAppender {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param component バリデーション対象 {@link FormComponent}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public AppendErrorClassIfInvalid(final FormComponent<?> component) {
		this(new AbstractReadOnlyModel<FormComponent<?>>() {
			
			@Override
			public FormComponent<?> getObject() {
				return component;
			}
		});
		Args.notNull(component, "component");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param supplier バリデーション対象 {@link FormComponent}を提供するモデル
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public AppendErrorClassIfInvalid(final IModel<FormComponent<?>> supplier) {
		super("class", new AbstractReadOnlyModel<String>() {
			
			@Override
			public String getObject() {
				return supplier.getObject().isValid() ? "" : " error";
			}
		});
		Args.notNull(supplier, "supplier");
	}
}
