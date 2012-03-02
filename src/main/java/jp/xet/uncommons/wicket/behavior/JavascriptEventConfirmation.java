/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/07
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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * {@link Button}等に対するクリック等のタイミングで、Javascriptによる確認を行うビヘイビア実装クラス。
 * 
 * <p>{@link AjaxLink}には<a href=
 * "http://apache-wicket.1842946.n4.nabble.com/AttributeModifier-and-AjaxLink-in-1-5-td3830027.html">利用できない</a
 * >ので注意のこと。</p>
 * 
 * @since 1.0.0
 * @version $Id: JavascriptEventConfirmation.java 115 2011-10-07 01:10:56Z daisuke $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class JavascriptEventConfirmation extends AttributeModifier {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param event イベント名
	 * @param model 確認メッセージモデル
	 */
	public JavascriptEventConfirmation(String event, IModel<?> model) {
		super(event, model);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param event イベント名
	 * @param msg 確認メッセージ
	 */
	public JavascriptEventConfirmation(String event, String msg) {
		this(event, Model.of(msg));
	}
	
	@Override
	protected String newValue(final String currentValue, final String replacementValue) {
		StringBuilder sb = new StringBuilder();
		sb.append("var conf = confirm('").append(replacementValue).append("'); ");
		sb.append("if (!conf) return false; ");
		
		if (currentValue != null) {
			sb.append(currentValue);
		}
		return sb.toString();
	}
}
