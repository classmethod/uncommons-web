/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/01
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
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.lang.Args;

/**
 * 要素に対して、指定したCSSクラスを追加するビヘイビア実装クラス。
 * 
 * @since 1.0
 * @version $Id: CssClassAppender.java 104 2011-10-02 14:10:48Z daisuke $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class CssClassAppender extends Behavior {
	
	private final String cssClass;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param cssClass 追加するクラス名
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public CssClassAppender(String cssClass) {
		Args.notNull(cssClass, "cssClass");
		this.cssClass = cssClass;
	}
	
	@Override
	public boolean isTemporary(Component component) {
		return true;
	}
	
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		tag.append("class", cssClass, " ");
	}
}
