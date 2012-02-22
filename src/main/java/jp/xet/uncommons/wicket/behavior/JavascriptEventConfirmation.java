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
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

/**
 * {@link Button}等に対するクリック等のタイミングで、Javascriptによる確認を行うビヘイビア実装クラス。
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
	 * @param msg 確認メッセージ
	 */
	public JavascriptEventConfirmation(String event, String msg) {
		super(event, new Model<String>(msg));
	}
	
	@Override
	protected String newValue(final String currentValue, final String replacementValue) {
		String prefix = "var conf = confirm('" + replacementValue + "'); " + "if (!conf) return false; ";
		String result = prefix;
		if (currentValue != null) {
			result = prefix + currentValue;
		}
		return result;
	}
}
