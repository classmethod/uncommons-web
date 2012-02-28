/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/28
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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;

/**
 * TODO for daisuke
 * 
 * @since TODO for daisuke
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BookmarkableNavigator extends PagingNavigator {
	
	private static final String DEFAULT_PAGE_KEYNAME = "page";
	
	private final String pageKeyName;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 * @param pageable
	 */
	public BookmarkableNavigator(String id, IPageable pageable) {
		this(id, pageable, DEFAULT_PAGE_KEYNAME);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 * @param pageable
	 * @param pageKeyName
	 */
	public BookmarkableNavigator(String id, IPageable pageable, String pageKeyName) {
		super(id, pageable);
		this.pageKeyName = pageKeyName;
	}
	
	@Override
	protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
		return new PagingNavigation(id, pageable, labelProvider) {
			
			@Override
			protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageIndex) {
				return new BookmarkableNavigationLink(id, getPage().getClass(), pageable, pageIndex);
			}
		};
	}
	
	@Override
	protected AbstractLink newPagingNavigationIncrementLink(String id, final IPageable pageable, final int increment) {
		int idx = pageable.getCurrentPage() + increment;
		int page = Math.max(0, Math.min(pageable.getPageCount() - 1, idx));
		return new BookmarkablePageLink<Void>(id, getPage().getClass(), getParams(page)) {
			
			public boolean isLast() {
				return pageable.getCurrentPage() >= (pageable.getPageCount() - 1);
			}
			
			@Override
			public boolean linksTo(final Page page) {
				pageable.getCurrentPage();
				return ((increment < 0) && isFirst()) || ((increment > 0) && isLast());
			}
			
			private boolean isFirst() {
				return pageable.getCurrentPage() <= 0;
			}
		}.setAutoEnable(true);
	}
	
	@Override
	protected AbstractLink newPagingNavigationLink(String id, final IPageable pageable, final int pageNumber) {
		return new BookmarkableNavigationLink(id, getPage().getClass(), pageable, cullPageNumber(pageNumber));
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		int currentPage;
		try {
			currentPage = getPage().getPageParameters().get(pageKeyName).toInt();
		} catch (StringValueConversionException e) {
			currentPage = 0;
		}
		getPageable().setCurrentPage(currentPage);
	}
	
	// copy from PagingNavigationLink#cullPageNumber
	private int cullPageNumber(int pageNumber) {
		int idx = pageNumber;
		IPageable pageable = getPageable();
		if (idx < 0) {
			idx = pageable.getPageCount() + idx;
		}
		if (idx > (pageable.getPageCount() - 1)) {
			idx = pageable.getPageCount() - 1;
		}
		if (idx < 0) {
			idx = 0;
		}
		return idx;
	}
	
	private PageParameters getParams(int page) {
		PageParameters params = new PageParameters(getPage().getPageParameters()); // copy
		params.set(pageKeyName, page);
		return params;
	}
	
	
	private class BookmarkableNavigationLink extends BookmarkablePageLink<Void> {
		
		private final int pageNumber;
		
		private final IPageable pageable;
		
		
		public <c extends Page> BookmarkableNavigationLink(String id, Class<c> pageClass, IPageable pageable,
				int pageNumber) {
			super(id, pageClass, getParams(pageNumber));
			this.pageNumber = pageNumber;
			this.pageable = pageable;
			setAutoEnable(true);
		}
		
		@Override
		public final boolean linksTo(final Page page) {
			return cullPageNumber(pageNumber) == pageable.getCurrentPage();
		}
	}
}
