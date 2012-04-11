/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/31
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
package jp.xet.uncommons.wicket.link;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * TODO for daisuke
 * 
 * @param <T> type of model object
 * @since 1.2
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ConfigurableBookmarkablePageLink<T> extends Link<T> {
	
	/** The page class that this link links to. */
	private final IModel<Class<? extends Page>> pageClassModel;
	
	/** The parameters to pass to the class constructor when instantiated. */
	protected PageParameters parameters;
	
	
	/**
	 * Constructor.
	 * 
	 * @param <C> type of page
	 * @param id The name of this component
	 * @param pageClass The class of page to link to
	 */
	public <C extends Page> ConfigurableBookmarkablePageLink(String id, Class<? extends Page> pageClass) {
		this(id, pageClass, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The name of this component
	 * @param pageClass The class of page to link to
	 * @param parameters The parameters to pass to the new page when the link is clicked
	 */
	public <C extends Page> ConfigurableBookmarkablePageLink(String id, Class<? extends Page> pageClass,
			PageParameters parameters) {
		this(id, new Model<Class<? extends Page>>(pageClass), parameters);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The name of this component
	 * @param pageClassModel model of the class of page to link to
	 */
	public <C extends Page> ConfigurableBookmarkablePageLink(String id, IModel<Class<? extends Page>> pageClassModel) {
		this(id, pageClassModel, null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param id See Component
	 * @param pageClassModel The class of page to link to
	 * @param parameters The parameters to pass to the new page when the link is clicked
	 */
	public <C extends Page> ConfigurableBookmarkablePageLink(String id, IModel<Class<? extends Page>> pageClassModel,
			PageParameters parameters) {
		super(id);
		
		this.pageClassModel = pageClassModel;
		this.parameters = parameters;
	}
	
	/**
	 * Get tge page class registered with the link
	 * 
	 * @return Page class
	 */
	public final Class<? extends Page> getPageClass() {
		return pageClassModel.getObject();
	}
	
	/**
	 * @return page parameters
	 */
	public PageParameters getPageParameters() {
		if (parameters == null) {
			parameters = new PageParameters();
		}
		return parameters;
	}
	
	/**
	 * Whether this link refers to the given page.
	 * 
	 * @param page
	 *            the page
	 * @see org.apache.wicket.markup.html.link.Link#linksTo(org.apache.wicket.Page)
	 */
	@Override
	public boolean linksTo(final Page page) {
		return page.getClass() == getPageClass();
	}
	
	/**
	 * THIS METHOD IS NOT USED! Bookmarkable links do not have a click handler. It is here to
	 * satisfy the interface only, as bookmarkable links will be dispatched by the handling servlet.
	 * 
	 * @see org.apache.wicket.markup.html.link.Link#onClick()
	 */
	@Override
	public final void onClick() {
		// Bookmarkable links do not have a click handler.
		// Instead they are dispatched by the request handling servlet.
	}
	
	@Override
	protected boolean getStatelessHint() {
		return true;
	}
	
	/**
	 * Gets the url to use for this link.
	 * 
	 * @return The URL that this link links to
	 * @see org.apache.wicket.markup.html.link.Link#getURL()
	 */
	@Override
	protected CharSequence getURL() {
		PageParameters parameters = getPageParameters();
		return urlFor(getPageClass(), parameters);
	}
}
