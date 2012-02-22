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

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ExternalLinkPanel extends LinkPanel<Void> {
	
	/** TODO for daisuke */
	private static final String LINK_ID = "link";
	
	private ExternalLink externalLink;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id
	 */
	public ExternalLinkPanel(String id, String href) {
		super(id);
		commonInit(href);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param label
	 * @return
	 * @since 1.0
	 */
	@Override
	public ExternalLinkPanel setBody(IModel<String> label) {
		externalLink.setBody(label);
		return this;
	}
	
	private void commonInit(String href) {
		externalLink = new ExternalLink(LINK_ID, href);
		add(externalLink);
	}
}
