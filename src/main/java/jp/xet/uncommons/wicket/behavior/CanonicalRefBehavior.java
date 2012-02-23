/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/23
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
package jp.xet.uncommons.wicket.behavior;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * TODO for daisuke
 * 
 * @author daisuke
 * @since 1.0
 * @see <a href="http://pushinginertia.com/2011/12/adding-a-relcanonical-link-in-wicket-for-duplicate-content/"
 * 		>Adding a rel=canonical link in Wicket for duplicate content</a>
 */
@SuppressWarnings("serial")
public class CanonicalRefBehavior extends Behavior {
	
	private final String canonicalUrl;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param canonicalUrl href
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CanonicalRefBehavior(String canonicalUrl) {
		Validate.notNull(canonicalUrl);
		this.canonicalUrl = canonicalUrl;
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		StringBuilder sb = new StringBuilder();
		sb.append("<link rel=\"canonical\" href=\"");
		sb.append(canonicalUrl);
		sb.append("\"/>");
		response.renderString(sb.toString());
	}
}
