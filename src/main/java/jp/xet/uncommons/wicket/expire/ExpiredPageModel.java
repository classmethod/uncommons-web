/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/03/24
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
package jp.xet.uncommons.wicket.expire;

import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO for daisuke
 * 
 * <code><pre>
 * private IModel<Boolean> expiredModel;
 * 	
 * protected void onDetach() {
 *   expiredModel.detach();
 *   super.onDetach();
 * }
 * 	
 * protected void onInitialize() {
 *   super.onInitialize();
 *   expiredModel = new ExpiredPageModel(getPageId());
 * }
 * 	
 * protected void onConfigure() {
 *   super.onConfigure();
 *   if (expiredModel.getObject()) {
 *     throw new PageExpiredException("page expired because...");
 *   }
 * }
 * </pre></code>
 * 
 * @since 1.0.0
 * @version $Id: ExpiredPageModel.java 4314 2012-03-24 05:29:46Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ExpiredPageModel implements IModel<Boolean> {
	
	private static Logger logger = LoggerFactory.getLogger(ExpiredPageModel.class);
	
	private int pageId;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageId 対象ページID
	 */
	public ExpiredPageModel(int pageId) {
		this.pageId = pageId;
	}
	
	@Override
	public void detach() {
	}
	
	@Override
	public Boolean getObject() {
		Session session = Session.get();
		if (session instanceof ExpiredPageIdStore == false) {
			throw new IllegalStateException("Illegal Session Class: " + session.getClass().getName());
		}
		ExpiredPageIdStore expiredPageIdStore = (ExpiredPageIdStore) session;
		return expiredPageIdStore.isPageExpired(pageId);
	}
	
	@Override
	public void setObject(Boolean object) {
		Session session = Session.get();
		if (session instanceof ExpiredPageIdStore == false) {
			throw new IllegalStateException("Illegal Session Class: " + session.getClass().getName());
		}
		ExpiredPageIdStore expiredPageIdStore = (ExpiredPageIdStore) session;
		if (object) {
			expiredPageIdStore.setPageExpired(pageId, true);
		} else {
			logger.warn("unexpected unexpired action...");
			expiredPageIdStore.setPageExpired(pageId, false);
		}
	}
}
