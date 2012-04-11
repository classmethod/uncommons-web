/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/07
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
package jp.xet.uncommons.wicket.social;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * TODO for daisuke
 * 
 * @param <T> the type of the panel's model object
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class SocialLoginFormPanel<T> extends GenericPanel<T> {
	
	private static final String FORM_ID = "form";
	
	private static final String IMAGE_ID = "image";
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	protected SocialLoginFormPanel(String id) {
		super(id);
		commonInit();
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	protected SocialLoginFormPanel(String id, IModel<T> model) {
		super(id, model);
		commonInit();
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return
	 * @since 1.0
	 */
	protected Component createForm() {
		return new Form<Void>(FORM_ID) {
			
			@Override
			protected boolean getStatelessHint() {
				return true;
			}
			
			@Override
			protected void onSubmit() {
				SocialLoginFormPanel.this.onSubmit();
			}
		}.add(new ImageButton(IMAGE_ID, getButtonImageResource()));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return
	 * @since TODO
	 */
	protected abstract ResourceReference getButtonImageResource();
	
	/**
	 * TODO for daisuke
	 * 
	 * @since TODO
	 */
	protected abstract void onSubmit();
	
	private void commonInit() {
		add(createForm());
	}
}
