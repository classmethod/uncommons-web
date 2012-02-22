/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/12/08
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

import org.apache.commons.lang.Validate;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class StatelessSimplePagingNavigator<T extends Page> extends SimplePagingNavigator {
	
	private final Class<T> clazz;
	
	private final PageParameters params;
	
	private final int currentPage; // zero-based
	
	private final int pageCount;
	
	private final String paramKey;
	
	private final String anchor;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz
	 * @param params
	 * @param pageable
	 * @param viewsize
	 */
	public StatelessSimplePagingNavigator(String id, Class<T> clazz, PageParameters params, IPageable pageable,
			int viewsize) {
		this(id, clazz, params, pageable, "page", viewsize, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz
	 * @param params
	 * @param pageable
	 * @param paramKey 
	 * @param viewsize
	 * @param anchorSelf
	 */
	public StatelessSimplePagingNavigator(String id, Class<T> clazz, PageParameters params, IPageable pageable,
			String paramKey, int viewsize, boolean anchorSelf) {
		super(id, pageable, viewsize, anchorSelf);
		Validate.notNull(clazz);
		Validate.notNull(pageable);
		Validate.notNull(paramKey);
		
		this.clazz = clazz;
		this.params = params;
		currentPage = pageable.getCurrentPage();
		pageCount = pageable.getPageCount();
		this.paramKey = paramKey;
		anchor = null;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz
	 * @param params
	 * @param pageable
	 * @param paramKey 
	 * @param viewsize
	 * @param anchor
	 */
	public StatelessSimplePagingNavigator(String id, Class<T> clazz, PageParameters params, IPageable pageable,
			String paramKey, int viewsize, String anchor) {
		super(id, pageable, viewsize, false);
		Validate.notNull(clazz);
		Validate.notNull(pageable);
		Validate.notNull(paramKey);
		
		this.clazz = clazz;
		this.params = params;
		currentPage = pageable.getCurrentPage();
		pageCount = pageable.getPageCount();
		this.paramKey = paramKey;
		this.anchor = anchor;
	}
	
	@Override
	protected boolean getStatelessHint() {
		return true;
	}
	
	@Override
	protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
		PagingNavigation pn = new PagingNavigation(id, pageable, labelProvider) {
			
			@Override
			protected Link<Void> newPagingNavigationLink(String id, IPageable pageable, final int pageIndex) {
				PageParameters pp = new PageParameters(StatelessSimplePagingNavigator.this.params);
				pp.set(paramKey, String.valueOf(pageIndex + 1));
				BookmarkablePageLink<Void> lnk = new BookmarkablePageLink<Void>(id, clazz, pp) {
					
					@Override
					public boolean isEnabled() {
						return currentPage != pageIndex;
					}
					
					@Override
					protected void onComponentTag(ComponentTag tag) {
						super.onComponentTag(tag);
						if (anchor != null && tag.getAttribute("href") != null) {
							String href = tag.getAttribute("href");
							String atag = anchor.contains("#") ? anchor : "#" + anchor;
							tag.put("href", href + atag);
						}
					}
				};
				if (isAnchorSelf()) {
					lnk.setAnchor(StatelessSimplePagingNavigator.this);
				}
				return lnk;
			}
		};
		pn.setViewSize(getViewsize());
		return pn;
	}
	
	@Override
	protected Link<Void> newPagingNavigationIncrementLink(String id, IPageable pageable, final int increment) {
		final int page = Integer.valueOf(params.get(paramKey).toString("1"));
		PageParameters pp = new PageParameters(this.params);
		pp.set(paramKey, String.valueOf(page + increment + 1));
		return new BookmarkablePageLink<Void>(id, clazz, pp) {
			
			@Override
			public boolean isEnabled() {
				return (page + increment) >= 0;
			}
		};
	}
	
	@Override
	protected Link<Void> newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
		PageParameters pp = new PageParameters(this.params);
		pp.set(paramKey, String.valueOf(pageNumber + 1));
		return new BookmarkablePageLink<Void>(id, clazz, pp);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param id The non-null id of this component
	 * @param increment
	 * @return
	 * @since 1.0
	 */
	protected Link<Void> newStatelessPagingNavigationLink(String id, final int increment) {
		PageParameters pp = new PageParameters(this.params);
		pp.set(paramKey, String.valueOf(currentPage + increment + 1));
		return new BookmarkablePageLink<Void>(id, clazz, pp) {
			
			@Override
			public boolean isEnabled() {
				return 0 <= (currentPage + increment) && (currentPage + increment) < pageCount;
			}
		};
	}
	
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		addOrReplace(newStatelessPagingNavigationLink("next", 1));
		addOrReplace(newStatelessPagingNavigationLink("prev", -1));
	}
}
