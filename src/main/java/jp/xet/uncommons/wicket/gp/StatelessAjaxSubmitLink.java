/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/01/23
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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.AbstractSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class StatelessAjaxSubmitLink extends AbstractSubmitLink {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 * @param form
	 */
	public StatelessAjaxSubmitLink(String id, Form<?> form) {
		super(id, form);
		add(new StatelessAjaxFormSubmitBehavior(form, "onclick") {
			
			@Override
			public boolean getDefaultProcessing() {
				return StatelessAjaxSubmitLink.this.getDefaultFormProcessing();
			}
			
			@Override
			public boolean getStatelessHint(Component component) {
				return true;
			}
			
			@Override
			protected Form<?> findForm() {
				return StatelessAjaxSubmitLink.this.getForm();
			}
			
			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator() {
				return StatelessAjaxSubmitLink.this.getAjaxCallDecorator();
			}
			
			@Override
			protected CharSequence getEventHandler() {
				return new AppendingStringBuffer(super.getEventHandler()).append("; return false;");
			}
			
			@Override
			protected PageParameters getPageParameters() {
				return getPage().getPageParameters();
			}
			
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// write the onclick handler only if link is enabled
				if (isLinkEnabled()) {
					super.onComponentTag(tag);
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target) {
				StatelessAjaxSubmitLink.this.onError(target, getForm());
			}
			
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				StatelessAjaxSubmitLink.this.onSubmit(target, getForm());
			}
		});
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 * @param form
	 * @param params 
	 */
	public StatelessAjaxSubmitLink(String id, Form<?> form, final PageParameters params) {
		super(id, form);
		add(new StatelessAjaxFormSubmitBehavior(form, "onclick") {
			
			@Override
			public boolean getDefaultProcessing() {
				return StatelessAjaxSubmitLink.this.getDefaultFormProcessing();
			}
			
			@Override
			public boolean getStatelessHint(Component component) {
				return true;
			}
			
			@Override
			protected Form<?> findForm() {
				return StatelessAjaxSubmitLink.this.getForm();
			}
			
			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator() {
				return StatelessAjaxSubmitLink.this.getAjaxCallDecorator();
			}
			
			@Override
			protected CharSequence getEventHandler() {
				return new AppendingStringBuffer(super.getEventHandler()).append("; return false;");
			}
			
			@Override
			protected PageParameters getPageParameters() {
				return params;
			}
			
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// write the onclick handler only if link is enabled
				if (isLinkEnabled()) {
					super.onComponentTag(tag);
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target) {
				StatelessAjaxSubmitLink.this.onError(target, getForm());
			}
			
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				StatelessAjaxSubmitLink.this.onSubmit(target, getForm());
			}
		});
	}
	
	/**
	 * Final implementation of the Button's onError. AjaxSubmitLinks have their own onError which is
	 * called.
	 * 
	 * @see org.apache.wicket.markup.html.form.Button#onError()
	 */
	@Override
	public final void onError() {
	}
	
	/**
	 * Final implementation of the Button's onSubmit. AjaxSubmitLinks have there own onSubmit which
	 * is called.
	 * 
	 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
	 */
	@Override
	public final void onSubmit() {
	}
	
	/**
	 * Returns the {@link IAjaxCallDecorator} that will be used to modify the generated javascript.
	 * This is the preferred way of changing the javascript in the onclick handler
	 * 
	 * @return call decorator used to modify the generated javascript or null for none
	 */
	protected IAjaxCallDecorator getAjaxCallDecorator() {
		return null;
	}
	
	@Override
	protected boolean getStatelessHint() {
		return true;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		
		if (isLinkEnabled()) {
			if (tag.getName().toLowerCase().equals("a")) {
				tag.put("href", "#");
			}
		} else {
			disableLink(tag);
		}
	}
	
	/**
	 * Listener method invoked on form submit with errors
	 * 
	 * @param target {@link AjaxRequestTarget}
	 * @param form
	 */
	protected abstract void onError(AjaxRequestTarget target, Form<?> form);
	
	/**
	 * Listener method invoked on form submit
	 * 
	 * @param target {@link AjaxRequestTarget}
	 * @param form
	 */
	protected abstract void onSubmit(AjaxRequestTarget target, Form<?> form);
}
