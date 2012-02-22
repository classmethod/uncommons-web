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
package jp.xet.uncommons.wicket.bootstrap.form;

import jp.xet.uncommons.wicket.behavior.AttributeDeleter;
import jp.xet.uncommons.wicket.gp.GenericWebMarkupContainer;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ControlGroup<T> extends GenericWebMarkupContainer<T> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	public ControlGroup(String id) {
		super(id);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model
	 */
	public ControlGroup(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		
		if (isValid(this) == false) {
			add(new AttributeAppender("class", "error").setSeparator(" "));
		} else {
			add(new AttributeDeleter("class", "error").setSeparator(" "));
		}
	}
	
	private boolean isValid(MarkupContainer mc) {
		for (Component c : mc) {
			if (c instanceof FormComponent<?>) {
				if (((FormComponent<?>) c).isValid() == false) {
					return false;
				}
			} else if (c instanceof MarkupContainer) {
				if (isValid((MarkupContainer) c) == false) {
					return false;
				}
			}
		}
		return true;
	}
}
