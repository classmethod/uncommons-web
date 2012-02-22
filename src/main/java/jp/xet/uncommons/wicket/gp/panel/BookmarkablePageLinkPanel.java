/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/04
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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BookmarkablePageLinkPanel<T> extends LinkPanel<T> {
	
	/** TODO for daisuke */
	private static final String LINK_ID = "link";
	
	private BookmarkablePageLink<T> bookmarkablePageLink;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 */
	public BookmarkablePageLinkPanel(String id, Class<? extends Page> pageClass, PageParameters params) {
		super(id);
		commonInit(pageClass, params);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 * @param model
	 */
	public BookmarkablePageLinkPanel(String id, IModel<T> model, Class<? extends Page> pageClass,
			PageParameters params) {
		super(id, model);
		commonInit(pageClass, params);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param label
	 * @return
	 * @since 1.0
	 */
	@Override
	public BookmarkablePageLinkPanel<T> setBody(IModel<String> label) {
		bookmarkablePageLink.setBody(label);
		return this;
	}
	
	private void commonInit(Class<? extends Page> pageClass, PageParameters params) {
		bookmarkablePageLink = new BookmarkablePageLink<T>(LINK_ID, pageClass, params);
		add(bookmarkablePageLink);
	}
}
