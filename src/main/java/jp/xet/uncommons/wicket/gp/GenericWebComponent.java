/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/26
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

import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

/**
 * A {@link WebComponent} with typesafe getters and setters for the model and its underlying object.
 * 
 * @param <T> the type of the component's model object
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class GenericWebComponent<T> extends WebComponent {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	public GenericWebComponent(String id) {
		super(id);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public GenericWebComponent(String id, IModel<T> model) {
		super(id, model);
	}
	
	/**
	 * Typesafe getter for the page's model
	 * 
	 * @return the model
	 */
	@SuppressWarnings("unchecked")
	public final IModel<T> getModel() {
		return (IModel<T>) getDefaultModel();
	}
	
	/**
	 * Typesafe getter for the model's object
	 * 
	 * @return the model object
	 */
	@SuppressWarnings("unchecked")
	public final T getModelObject() {
		return (T) getDefaultModelObject();
	}
	
	/**
	 * Typesafe setter for the model
	 * 
	 * @param model the new model
	 */
	public final void setModel(IModel<T> model) {
		setDefaultModel(model);
	}
	
	/**
	 * Typesafe setter for the model object
	 * 
	 * @param modelObject
	 *            the new model object
	 */
	public final void setModelObject(T modelObject) {
		setDefaultModelObject(modelObject);
	}
}
