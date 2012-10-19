/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/19
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
package jp.xet.uncommons.wicket.utils;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.util.lang.Args;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BooleanChoiceRenderer extends ChoiceRenderer<Boolean> {
	
	private final Component component;
	
	private final String prefix;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param prefix
	 * @param component
	 * @throws IllegalArgumentException 引数{@code prefix}に{@code null}を与えた場合
	 */
	public BooleanChoiceRenderer(String prefix, Component component) {
		Args.notNull(prefix, "prefix");
		this.prefix = prefix;
		this.component = component;
	}
	
	@Override
	public Object getDisplayValue(Boolean object) {
		Localizer localizer = Application.get().getResourceSettings().getLocalizer();
		return localizer.getString(prefix + "." + object.toString(), component);
	}
}
