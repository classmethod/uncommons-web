/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/18
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
package jp.xet.uncommons.wicket.google.analytics;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Generates javascript for google analytics asynchronous (new) tracking code. The model is the url that is reported to
 * the tracker, which can also be something other than a URL, such as the java class name of the most important panel on
 * a page. The account name is stored in a separate model.
 * 
 * @since 1.0
 * @version $Id$
 * @author Erwin Bolwidt (ebolwidt@worldturner.nl)
 * @author daisuke
 */
@SuppressWarnings("serial")
public class GoogleAnalyticsScriptPanel extends GenericPanel<String> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param urlModel Model of URL
	 * @param accountNameModel Model of tracking ID
	 * @param domainNameModel Model of domain name
	 */
	public GoogleAnalyticsScriptPanel(String id, IModel<String> urlModel, IModel<String> accountNameModel,
			IModel<String> domainNameModel) {
		super(id, urlModel);
		add(new Label("accountName", accountNameModel).setRenderBodyOnly(true));
		add(new Label("trackingUrl", urlModel).setRenderBodyOnly(true));
		add(new Label("domainName", domainNameModel).setRenderBodyOnly(true));
		
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param urlModel Model of URL
	 * @param domainName domain name
	 */
	public GoogleAnalyticsScriptPanel(String id, IModel<String> urlModel, String domainName) {
		this(id, urlModel, null, Model.of(domainName));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param domainName domain name
	 */
	public GoogleAnalyticsScriptPanel(String id, String domainName) {
		this(id, (IModel<String>) null, (IModel<String>) null, Model.of(domainName));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param url URL
	 * @param account tracking ID
	 * @param domainName domain name
	 */
	public GoogleAnalyticsScriptPanel(String id, String url, String account, String domainName) {
		this(id, Model.of(url), Model.of(account), Model.of(domainName));
	}
}
