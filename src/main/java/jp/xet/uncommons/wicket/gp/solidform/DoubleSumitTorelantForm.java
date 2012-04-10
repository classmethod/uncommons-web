/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/04/10
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
package jp.xet.uncommons.wicket.gp.solidform;

import javax.servlet.http.HttpServletResponse;

import jp.xet.baseunits.timeutil.Clock;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO for daisuke
 * 
 * @param <T> The model object type
 * @since 1.3
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class DoubleSumitTorelantForm<T> extends Form<T> {
	
	private static Logger logger = LoggerFactory.getLogger(DoubleSumitTorelantForm.class);
	
	private FormKey key;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	public DoubleSumitTorelantForm(String id) {
		super(id);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public DoubleSumitTorelantForm(String id, IModel<T> model) {
		super(id, model);
	}
	
	/**
	 * このフォームが属するページのIDを返す。
	 * 
	 * @return ページID
	 * @since 1.3
	 */
	protected int getPageId() {
		return getPage().getPageId();
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		key = new FormKey(getPageId(), getId(), Clock.now());
		Session session = getSession();
		if (session instanceof DoubleSumitTorelantFormKeyContainer) {
			DoubleSumitTorelantFormKeyContainer solidFormSession = (DoubleSumitTorelantFormKeyContainer) session;
			solidFormSession.addFormKey(key);
		} else {
			logger.warn("session must implement DoubleSumitTorelantFormKeyContainer");
		}
	}
	
	/**
	 * Implemented by subclasses to deal with double (or mode) form submits.
	 * 
	 * @since 1.3
	 */
	protected void onDoubleSubmit() {
		logger.warn("double submit detected: abort with 400 Bad Request");
		throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	/**
	 * Implemented by subclasses to deal with single form submits.
	 * 
	 * @since 1.3
	 */
	protected abstract void onSingleSubmit();
	
	@Override
	protected final void onSubmit() {
		logger.trace("onSubmit");
		Session session = getSession();
		if (session instanceof DoubleSumitTorelantFormKeyContainer) {
			DoubleSumitTorelantFormKeyContainer solidFormSession = (DoubleSumitTorelantFormKeyContainer) session;
			
			if (solidFormSession.removeFormKey(key) == false) {
				onDoubleSubmit();
			} else {
				onSingleSubmit();
			}
		} else {
			logger.warn("session must implement DoubleSumitTorelantFormKeyContainer");
		}
	}
}
