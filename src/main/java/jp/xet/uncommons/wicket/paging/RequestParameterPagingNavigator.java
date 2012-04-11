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
package jp.xet.uncommons.wicket.paging;

import jp.xet.uncommons.wicket.gp.SimplePagingNavigator;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0
 * @version $Id: RequestParameterPagingNavigator.java 4593 2012-03-29 15:22:54Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class RequestParameterPagingNavigator<T extends Page> extends SimplePagingNavigator {
	
	private static final String DEFAULT_PAGE_KEYNAME = "page";
	
	private final Class<T> clazz;
	
	private final String pageKeyName;
	
	private final String anchor;
	
	private final IPageable pageable;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz
	 * @param pageable
	 * @param viewsize
	 */
	public RequestParameterPagingNavigator(String id, Class<T> clazz, IPageable pageable, int viewsize) {
		this(id, clazz, pageable, DEFAULT_PAGE_KEYNAME, viewsize, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz
	 * @param pageable
	 * @param pageKeyName 
	 * @param viewsize
	 * @param anchorSelf
	 */
	public RequestParameterPagingNavigator(String id, Class<T> clazz, IPageable pageable, String pageKeyName,
			int viewsize, boolean anchorSelf) {
		super(id, pageable, viewsize, anchorSelf);
		Validate.notNull(clazz);
		Validate.notNull(pageable);
		Validate.notNull(pageKeyName);
		
		this.clazz = clazz;
		this.pageable = pageable;
		this.pageKeyName = pageKeyName;
		anchor = null;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param clazz
	 * @param pageable
	 * @param pageKeyName 
	 * @param viewsize
	 * @param anchor
	 */
	public RequestParameterPagingNavigator(String id, Class<T> clazz, IPageable pageable, String pageKeyName,
			int viewsize, String anchor) {
		super(id, pageable, viewsize, false);
		Validate.notNull(clazz);
		Validate.notNull(pageable);
		Validate.notNull(pageKeyName);
		
		this.clazz = clazz;
		this.pageable = pageable;
		this.pageKeyName = pageKeyName;
		this.anchor = anchor;
	}
	
	@Override
	protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
		PagingNavigation pn = new PagingNavigation(id, pageable, labelProvider) {
			
			@Override
			protected Link<Void> newPagingNavigationLink(String id, final IPageable pageable, final int pageIndex) {
				PageParameters pp = newPageParameters();
				pp.set(pageKeyName, String.valueOf(pageIndex + 1));
				BookmarkablePageLink<Void> lnk = new BookmarkablePageLink<Void>(id, clazz, pp) {
					
					@Override
					public boolean isEnabled() {
						return pageable.getCurrentPage() != pageIndex;
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
					lnk.setAnchor(RequestParameterPagingNavigator.this);
				}
				return lnk;
			}
		};
		pn.setViewSize(getViewsize());
		return pn;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return
	 * @since 1.0
	 */
	protected PageParameters newPageParameters() {
		return new PageParameters();
	}
	
	@Override
	protected Link<Void> newPagingNavigationIncrementLink(String id, IPageable pageable, final int increment) {
		IRequestParameters requestParameters = getRequest().getRequestParameters();
		
		final int page = parsePageNumber(requestParameters);
		PageParameters pp = newPageParameters();
		pp.set(pageKeyName, String.valueOf(page + increment + 1));
		return new BookmarkablePageLink<Void>(id, clazz, pp) {
			
			@Override
			public boolean isEnabled() {
				return (page + increment) >= 0;
			}
		};
	}
	
	@Override
	protected Link<Void> newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
		PageParameters pp = newPageParameters();
		pp.set(pageKeyName, String.valueOf(pageNumber + 1));
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
		PageParameters pp = newPageParameters();
		pp.set(pageKeyName, String.valueOf(pageable.getCurrentPage() + increment + 1));
		return new BookmarkablePageLink<Void>(id, clazz, pp) {
			
			@Override
			public boolean isEnabled() {
				int currentPage = pageable.getCurrentPage();
				int pageCount = pageable.getPageCount();
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
	
	private int parsePageNumber(IRequestParameters requestParameters) {
		try {
			return Integer.valueOf(requestParameters.getParameterValue(pageKeyName).toString("1"));
		} catch (NumberFormatException e) {
			// ignore
		}
		return 1;
	}
}
