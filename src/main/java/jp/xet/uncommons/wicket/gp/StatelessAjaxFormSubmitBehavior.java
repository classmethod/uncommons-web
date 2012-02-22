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

import com.google.code.joliratools.StatelessAjaxEventBehavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class StatelessAjaxFormSubmitBehavior extends StatelessAjaxEventBehavior {
	
	/**
	 * should never be accessed directly (thus the __ cause its overkill to create a super class),
	 * instead always use #getForm()
	 */
	private Form<?> __form;
	
	private boolean defaultProcessing = true;
	
	
	/**
	 * Construct.
	 * 
	 * @param form
	 *            form that will be submitted
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 */
	public StatelessAjaxFormSubmitBehavior(Form<?> form, String event) {
		super(event);
		__form = form;
		
		if (form != null) {
			form.setOutputMarkupId(true);
		}
	}
	
	/**
	 * Constructor. This constructor can only be used when the component this behavior is attached
	 * to is inside a form.
	 * 
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 */
	public StatelessAjaxFormSubmitBehavior(String event) {
		this(null, event);
	}
	
	/**
	 * @see Button#getDefaultFormProcessing()
	 * 
	 * @return {@code true} for default processing
	 */
	public boolean getDefaultProcessing() {
		return defaultProcessing;
	}
	
	/**
	 * 
	 * @return Form that will be submitted by this behavior
	 */
	public final Form<?> getForm() {
		if (__form == null) {
			__form = findForm();
			
			if (__form == null) {
				throw new IllegalStateException(
						"form was not specified in the constructor and cannot "
								+ "be found in the hierarchy of the component this behavior "
								+ "is attached to: Component=" + getComponent().toString(false));
			}
		}
		return __form;
	}
	
	/**
	 * @see Button#setDefaultFormProcessing(boolean)
	 * @param defaultProcessing
	 */
	public void setDefaultProcessing(boolean defaultProcessing) {
		this.defaultProcessing = defaultProcessing;
	}
	
	/**
	 * Finds form that will be submitted
	 * 
	 * @return form to submit or {@code null} if none found
	 */
	protected Form<?> findForm() {
		// try to find form in the hierarchy of owning component
		Component component = getComponent();
		if (component instanceof Form<?>) {
			return (Form<?>) component;
		} else {
			return component.findParent(Form.class);
		}
	}
	
	/**
	 * 
	 * @see org.apache.wicket.ajax.AjaxEventBehavior#getEventHandler()
	 */
	@Override
	protected CharSequence getEventHandler() {
		final String formId = getForm().getMarkupId();
		final CharSequence url = getCallbackUrl();
		
		AppendingStringBuffer call = new AppendingStringBuffer("wicketSubmitFormById('").append(
				formId)
			.append("', '")
			.append(url)
			.append("', ");
		
		if (getComponent() instanceof IFormSubmittingComponent) {
			call.append("'")
				.append(((IFormSubmittingComponent) getComponent()).getInputName())
				.append("' ");
		} else {
			call.append("null");
		}
		
		return generateCallbackScript(call) + ";";
	}
	
	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#getPreconditionScript()
	 */
	@Override
	protected CharSequence getPreconditionScript() {
		return "return Wicket.$$(this)&&Wicket.$$('" + getForm().getMarkupId() + "')";
	}
	
	/**
	 * Listener method invoked when the form has been processed and errors occurred
	 * 
	 * @param target
	 */
	protected abstract void onError(AjaxRequestTarget target);
	
	/**
	 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void onEvent(final AjaxRequestTarget target) {
		getForm().getRootForm().onFormSubmitted(new IFormSubmitter() {
			
			@Override
			public boolean getDefaultFormProcessing() {
				return StatelessAjaxFormSubmitBehavior.this.getDefaultProcessing();
			}
			
			@Override
			public Form<?> getForm() {
				return StatelessAjaxFormSubmitBehavior.this.getForm();
			}
			
			@Override
			public void onError() {
				StatelessAjaxFormSubmitBehavior.this.onError(target);
			}
			
			@Override
			public void onSubmit() {
				StatelessAjaxFormSubmitBehavior.this.onSubmit(target);
			}
		});
	}
	
	/**
	 * Listener method that is invoked after the form has been submitted and processed without
	 * errors
	 * 
	 * @param target
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);
}
