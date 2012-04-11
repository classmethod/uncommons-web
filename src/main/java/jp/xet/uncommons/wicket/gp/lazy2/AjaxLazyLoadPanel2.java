/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/21
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
package jp.xet.uncommons.wicket.gp.lazy2;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;

/**
 * A panel where you can lazy load another panel. This can be used if you have a panel/component
 * that is pretty heavy in creation and you first want to show the user the page and then replace
 * the panel when it is ready.
 * 
 * <p>{@link #onRespond(AjaxRequestTarget, RespondState)} is added.</p>
 * 
 * @author daisuke
 * @since 1.0
 * @see org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel
 */
@SuppressWarnings("serial")
public abstract class AjaxLazyLoadPanel2 extends Panel {
	
	/**
	 * The component id which will be used to load the lazily loaded component.
	 */
	public static final String LAZY_LOAD_COMPONENT_ID = "content";
	
	private RespondState state = RespondState.LOADING_COMPONENT_ADDED;
	
	
	/**
	 * Constructor
	 * 
	 * @param id The non-null id of this component
	 */
	public AjaxLazyLoadPanel2(String id) {
		this(id, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public AjaxLazyLoadPanel2(String id, IModel<?> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		add(new AbstractDefaultAjaxBehavior() {
			
			private static final long serialVersionUID = 1L;
			
			
			@Override
			public void renderHead(final Component component, final IHeaderResponse response) {
				super.renderHead(component, response);
				if (state != RespondState.COMPONENT_REPLACED) {
					handleCallbackScript(response, getCallbackScript().toString());
				}
			}
			
			@Override
			protected AjaxChannel getChannel() {
				return AjaxLazyLoadPanel2.this.getChannel();
			}
			
			@Override
			protected void respond(final AjaxRequestTarget target) {
				if (state != RespondState.COMPONENT_REPLACED) {
					Component component = getLazyLoadComponent(LAZY_LOAD_COMPONENT_ID);
					AjaxLazyLoadPanel2.this.replace(component);
					setState(RespondState.COMPONENT_REPLACED);
				}
				target.add(AjaxLazyLoadPanel2.this);
				onRespond(target, state);
			}
			
		});
	}
	
	/**
	 * 
	 * @param markupId
	 *            The components markupid.
	 * @return The component that must be lazy created. You may call setRenderBodyOnly(true) on this
	 *         component if you need the body only.
	 */
	public abstract Component getLazyLoadComponent(String markupId);
	
	/**
	 * @param markupId
	 *            The components markupid.
	 * @return The component to show while the real component is being created.
	 */
	public Component getLoadingComponent(String markupId) {
		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);
		return new Label(markupId, "<img alt=\"Loading...\" src=\"" + RequestCycle.get().urlFor(handler) + "\"/>")
			.setEscapeModelStrings(false);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return {@code null}
	 * @since 1.0
	 */
	protected AjaxChannel getChannel() {
		return null;
	}
	
	/**
	 * Allows subclasses to change the callback script if needed.
	 * 
	 * @param response {@link IHeaderResponse}
	 * @param callbackScript callback srcipt
	 */
	protected void handleCallbackScript(IHeaderResponse response, String callbackScript) {
		response.renderOnDomReadyJavaScript(callbackScript);
	}
	
	@Override
	protected void onBeforeRender() {
		if (state == RespondState.LOADING_COMPONENT_ADDED) {
			add(getLoadingComponent(LAZY_LOAD_COMPONENT_ID));
			setState(RespondState.WAITING_FOR_AJAX_REPLACE);
		}
		super.onBeforeRender();
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param target {@link AjaxRequestTarget}
	 * @param state {@link RespondState}
	 * @since 1.0
	 */
	protected void onRespond(AjaxRequestTarget target, RespondState state) {
	}
	
	private void setState(final RespondState state) {
		this.state = state;
		getPage().dirty();
	}
	
	
	public enum RespondState {
		
		/** add loading component */
		LOADING_COMPONENT_ADDED,
		
		/** loading component added, waiting for ajax replace */
		WAITING_FOR_AJAX_REPLACE,
		
		/** ajax replacement completed */
		COMPONENT_REPLACED
	}
}
