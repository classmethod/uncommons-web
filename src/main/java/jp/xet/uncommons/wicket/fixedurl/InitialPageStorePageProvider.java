/*
 * Copyright 2012 Tsutomu YANO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.uncommons.wicket.fixedurl;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *
 * @author Tsutomu YANO
 * @since 1.1
 */
public class InitialPageStorePageProvider extends PageProvider {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageClass
	 * @since 1.1
	 */
	public InitialPageStorePageProvider(Class<? extends IRequestablePage> pageClass) {
		super(pageClass);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageClass
	 * @param pageParameters
	 * @since 1.1
	 */
	public InitialPageStorePageProvider(Class<? extends IRequestablePage> pageClass, PageParameters pageParameters) {
		super(pageClass, pageParameters);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageId
	 * @param pageClass
	 * @param renderCount
	 * @since 1.1
	 */
	public InitialPageStorePageProvider(int pageId, Class<? extends IRequestablePage> pageClass, Integer renderCount) {
		super(pageId, pageClass, renderCount);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageId
	 * @param pageClass
	 * @param pageParameters
	 * @param renderCount
	 * @since 1.1
	 */
	public InitialPageStorePageProvider(int pageId, Class<? extends IRequestablePage> pageClass,
			PageParameters pageParameters, Integer renderCount) {
		super(pageId, pageClass, pageParameters, renderCount);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageId
	 * @param renderCount
	 * @since 1.1
	 */
	public InitialPageStorePageProvider(int pageId, Integer renderCount) {
		super(pageId, renderCount);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param page
	 * @since 1.1
	 */
	public InitialPageStorePageProvider(IRequestablePage page) {
		super(page);
	}
	
	@Override
	public IRequestablePage getPageInstance() {
		IRequestablePage pageInstance = super.getPageInstance();
		if (isPageInstanceFresh()) {
			// 作成したページのpageIdをセッションに保存する
			Session session = Session.get();
			if (session != null && session instanceof IInitialPageIdStore) {
				IInitialPageIdStore store = (IInitialPageIdStore) session;
				store.putInitialId(getPageClass().asSubclass(Page.class), pageInstance.getPageParameters(),
						pageInstance.getPageId());
			}
		}
		return pageInstance;
	}
}
