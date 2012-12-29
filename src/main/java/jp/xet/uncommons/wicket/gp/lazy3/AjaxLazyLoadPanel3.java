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
package jp.xet.uncommons.wicket.gp.lazy3;

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
 * @since 1.9
 * @see org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel
 */
@SuppressWarnings("serial")
public abstract class AjaxLazyLoadPanel3 extends Panel {
	
	/**
	 * The component id which will be used to load the lazily loaded component.
	 */
	public static final String LAZY_LOAD_COMPONENT_ID = "content";
	
	private RespondState state = RespondState.LOADING_COMPONENT_SHOUDL_BE_ADDED;
	
	
	/**
	 * Constructor
	 * 
	 * @param id The non-null id of this component
	 * @since 1.9
	 */
	public AjaxLazyLoadPanel3(String id) {
		this(id, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @since 1.9
	 */
	public AjaxLazyLoadPanel3(String id, IModel<?> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		add(new AbstractDefaultAjaxBehavior() {
			
			@Override
			public void renderHead(final Component component, final IHeaderResponse response) {
				super.renderHead(component, response);
				if (state != RespondState.COMPONENT_REPLACED) {
					handleCallbackScript(component, response, getCallbackScript().toString());
				}
			}
			
			@Override
			protected AjaxChannel getChannel() {
				return AjaxLazyLoadPanel3.this.getChannel();
			}
			
			@Override
			protected void respond(final AjaxRequestTarget target) {
				if (state != RespondState.COMPONENT_REPLACED) {
					Component component = getLazyLoadComponent(LAZY_LOAD_COMPONENT_ID);
					AjaxLazyLoadPanel3.this.replace(component);
					respondReplaced(component, target);
					setState(RespondState.COMPONENT_REPLACED);
				}
				target.add(AjaxLazyLoadPanel3.this);
				onRespond(target, state);
			}
			
		});
	}
	
	/**
	 * lazy load componentを生成して返す。
	 * 
	 * @param markupId The components markupid.
	 * @return The component that must be lazy created. You may call setRenderBodyOnly(true) on this
	 *         component if you need the body only.
	 * @since 1.9
	 */
	public abstract Component getLazyLoadComponent(String markupId);
	
	/**
	 * loading componentを生成して返す。
	 * 
	 * @param markupId The components markupid.
	 * @return The component to show while the real component is being created.
	 * @since 1.9
	 */
	public Component getLoadingComponent(String markupId) {
		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);
		return new Label(markupId, "<img alt=\"Loading...\" src=\"" + RequestCycle.get().urlFor(handler) + "\"/>")
			.setEscapeModelStrings(false);
	}
	
	/**
	 * Provides an {@link AjaxChannel} for this ajax Behavior.
	 * 
	 * @return an {@link AjaxChannel} - Defaults to {@code null}.
	 * @since 1.9
	 * */
	protected AjaxChannel getChannel() {
		return null;
	}
	
	/**
	 * Allows subclasses to change the callback script if needed.
	 * 
	 * @param component component which is contributing to the response. This parameter is here to give the component
	 * 		as the context for component-awares implementing this interface
	 * @param response {@link IHeaderResponse}
	 * @param callbackScript callback srcipt
	 * @since 1.9
	 */
	protected void handleCallbackScript(Component component, IHeaderResponse response, String callbackScript) {
		response.renderOnDomReadyJavaScript(callbackScript);
	}
	
	@Override
	protected void onBeforeRender() {
		if (state == RespondState.LOADING_COMPONENT_SHOUDL_BE_ADDED) {
			add(getLoadingComponent(LAZY_LOAD_COMPONENT_ID));
			setState(RespondState.WAITING_FOR_AJAX_REPLACE);
		}
		super.onBeforeRender();
	}
	
	/**
	 * {@link AbstractDefaultAjaxBehavior}へのcallbackが発生した際に呼ばれるフックメソッド。
	 * 
	 * @param target {@link AjaxRequestTarget}
	 * @param state {@link RespondState}
	 * @since 1.9
	 */
	protected void onRespond(AjaxRequestTarget target, RespondState state) {
	}
	
	/**
	 * lazy load componentがaddされ、遅延ロードが完了する際に呼ばれるフックメソッド。
	 * 
	 * @param component lazy load component
	 * @param target {@link AjaxRequestTarget}
	 * @since 1.9
	 */
	protected void respondReplaced(Component component, AjaxRequestTarget target) {
	}
	
	private void setState(final RespondState state) {
		this.state = state;
		getPage().dirty();
	}
	
	
	/**
	 * {@link AjaxLazyLoadPanel3}のステートを表す列挙型。
	 * 
	 * @version $Id: AjaxLazyLoadPanel3.java 9516 2012-11-27 02:14:06Z miyamoto $
	 * @author daisuke
	 * @since 1.9
	 */
	public enum RespondState {
		
		/**
		 * initial state.
		 * 
		 * <p>{@link #LAZY_LOAD_COMPONENT_ID}にあたるコンポーネントは、このパネルの子コンポーネントとしてaddされていない状態。</p>
		 */
		LOADING_COMPONENT_SHOUDL_BE_ADDED,
		
		/**
		 * loading component added, waiting for ajax replace.
		 * 
		 * <p>{@link #LAZY_LOAD_COMPONENT_ID}に{@link AjaxLazyLoadPanel3#getLoadingComponent(String) loading component}が
		 * addされている。{@link AbstractDefaultAjaxBehavior}へのcallbackによって、このコンポーネントが
		 * {@link AjaxLazyLoadPanel3#getLazyLoadComponent(String) lazy load component}に置き換わるのを待っている状態。</p>
		 */
		WAITING_FOR_AJAX_REPLACE,
		
		/**
		 * ajax replacement completed.
		 * 
		 * <p>{@link #LAZY_LOAD_COMPONENT_ID}に{@link AjaxLazyLoadPanel3#getLazyLoadComponent(String) lazy load component}が
		 * addされており、遅延ロードが完了している状態。</p>
		 */
		COMPONENT_REPLACED
	}
}
