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

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class SimplePagingNavigator extends PagingNavigator {
	
	private int viewsize = 0;
	
	private boolean anchorSelf = false;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param pageable
	 * @param viewsize
	 */
	public SimplePagingNavigator(String id, IPageable pageable, int viewsize) {
		this(id, pageable, viewsize, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param pageable
	 * @param viewsize
	 * @param anchorSelf
	 */
	public SimplePagingNavigator(String id, IPageable pageable, int viewsize, boolean anchorSelf) {
		super(id, pageable);
		setOutputMarkupId(true);
		setViewsize(viewsize);
		setAnchorSelf(anchorSelf);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return
	 * @since TODO
	 */
	public int getViewsize() {
		return viewsize;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @return
	 * @since TODO
	 */
	public boolean isAnchorSelf() {
		return anchorSelf;
	}
	
	@Override
	public boolean isVisible() {
		if (getPageable() != null) {
			return getPageable().getPageCount() > 1;
		}
		return true;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param anchorSelf
	 * @since TODO
	 */
	public void setAnchorSelf(boolean anchorSelf) {
		this.anchorSelf = anchorSelf;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param viewsize
	 * @since TODO
	 */
	public void setViewsize(int viewsize) {
		this.viewsize = viewsize;
	}
	
	@Override
	protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
		PagingNavigation pg = new PagingNavigation("navigation", pageable, labelProvider) {
			
			@Override
			protected Link<Void> newPagingNavigationLink(String id, IPageable pageable, int pageIndex) {
				@SuppressWarnings("unchecked")
				Link<Void> lnk = (Link<Void>) super.newPagingNavigationLink(id, pageable, pageIndex);
				if (isAnchorSelf()) {
					lnk.setAnchor(SimplePagingNavigator.this);
				}
				return lnk;
			}
		};
		pg.setViewSize(getViewsize());
		return pg;
	}
	
	@Override
	protected void onBeforeRender() {
		if (get("navigation") != null) {
			remove("navigation");
		}
		if (get("prev") != null) {
			remove("prev");
		}
		if (get("next") != null) {
			remove("next");
		}
		super.onBeforeRender();
		if (get("first") != null) {
			remove("first");
		}
		if (get("last") != null) {
			remove("last");
		}
		if (getViewsize() != 0) {
			getPagingNavigation().setViewSize(getViewsize());
		}
	}
}
