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

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * {@link EmailTextField}をラップした{@link Panel}クラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class EmailTextFieldPanel extends FormComponentPanel<String, EmailTextField> {
	
	private final IValidator<String> emailValidator;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public EmailTextFieldPanel(String id, IModel<String> model) {
		this(id, model, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param emailValidator 
	 */
	public EmailTextFieldPanel(String id, IModel<String> model, IValidator<String> emailValidator) {
		super(id, model);
		this.emailValidator = emailValidator;
	}
	
	@Override
	protected EmailTextField createFormComponent(String id, IModel<String> model) {
		if (emailValidator != null) {
			return new EmailTextField(id, model, emailValidator);
		}
		return new EmailTextField(id, model);
	}
}
