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

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @param <T> the type of the panel's model object
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class LinkPanel<T> extends GenericPanel<T> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	protected LinkPanel(String id) {
		super(id);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	protected LinkPanel(String id, IModel<T> model) {
		super(id, model);
	}
	
	/**
	 * Sets the link's body model.
	 * 
	 * @param bodyModel body model to be set
	 * @return {@code this} for method chaining
	 * @since 1.0
	 */
	public abstract LinkPanel<T> setBody(IModel<String> bodyModel);
}
