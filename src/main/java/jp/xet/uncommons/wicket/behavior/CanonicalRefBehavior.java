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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.springframework.util.Assert;

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
	
	private static final String FORMAT = "<link rel=\"canonical\" href=\"%s\"/>";
	
	private final IModel<String> canonicalUrlModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param canonicalUrlModel hrefモデル
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CanonicalRefBehavior(IModel<String> canonicalUrlModel) {
		Assert.notNull(canonicalUrlModel);
		this.canonicalUrlModel = canonicalUrlModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param canonicalUrl href
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CanonicalRefBehavior(String canonicalUrl) {
		this(Model.of(canonicalUrl));
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		String canonicalUrl = canonicalUrlModel.getObject();
		if (Strings.isEmpty(canonicalUrl) == false) {
			response.renderString(String.format(FORMAT, Strings.escapeMarkup(canonicalUrl)));
		}
	}
}
