/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/03/28
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

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.util.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CSRF対策フォーム。
 * 
 * @param <T> The model object type
 * @since 1.2
 * @version $Id: CsrfSecureForm.java 4573 2012-03-29 09:48:16Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class CsrfSecureForm<T> extends Form<T> {
	
	private static Logger logger = LoggerFactory.getLogger(CsrfSecureForm.class);
	
	private static final Random RAND = new SecureRandom();
	
	private static final String INPUT_NAME = "csrf_token";
	
	private String csrfToken;
	
	private boolean ignoreKeyForTest;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @since 1.2
	 */
	public CsrfSecureForm(String id) {
		super(id);
		logger.trace("construct.");
		initCsrfToken();
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @since 1.2
	 */
	public CsrfSecureForm(String id, IModel<T> model) {
		super(id, model);
		logger.trace("construct.");
		initCsrfToken();
	}
	
	/**
	 * CSRFトークンを返す。
	 * 
	 * @return CSRFトークン
	 * @since 1.2
	 */
	public String getCsrfToken() {
		return csrfToken;
	}
	
	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		super.onComponentTagBody(markupStream, openTag);
		getResponse().write(
				"<div style=\"width: 0px; height: 0px; position: absolute;"
						+ " left: -100px; top: -100px; overflow-x: hidden; overflow-y: hidden; \">");
		getResponse().write("<input type=\"hidden\" name=\"" + INPUT_NAME + "\" value=\"" + csrfToken + "\"/>");
		getResponse().write("</div>");
	}
	
	/**
	 * テスト用にCSRFトークンチェックをパスするかどうかを設定する。
	 * 
	 * @param ignoreKeyForTest トークンチェックをパスする場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.2
	 */
	public void setIgnoreKeyForTest(boolean ignoreKeyForTest) {
		this.ignoreKeyForTest = ignoreKeyForTest;
	}
	
	@Override
	protected void onValidate() {
		super.onValidate();
		IRequestParameters params = getRequest().getRequestParameters();
		String actualKey = params.getParameterValue(INPUT_NAME).toString();
		
		logger.trace("CSRF checking {}:{} - actual = {}, expected = {}", new Object[] {
			getPage().getClass().getName(),
			getPageRelativePath(),
			actualKey,
			csrfToken
		});
		
		if (ignoreKeyForTest == false && Objects.equal(actualKey, csrfToken) == false) {
			logger.warn("CSRF detected in {}:{} - actual = {}, expected = {}", new Object[] {
				getPage().getClass().getName(),
				getPageRelativePath(),
				actualKey,
				csrfToken
			});
			throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			logger.debug("submit successfully.");
			initCsrfToken();
		}
	}
	
	private void initCsrfToken() {
		csrfToken = String.valueOf(RAND.nextLong());
		logger.trace("new CSRF token for {}: {}", getClass().getName(), csrfToken);
	}
}
