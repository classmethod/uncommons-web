/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/24
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
package jp.xet.uncommons.wicket.model;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.PropertyModel;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class AbbreviateStringPropertyModel extends PropertyModel<String> {
	
	private final int maxWidth;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param modelObject The model object, which may or may not implement IModel
	 * @param expression Property expression for property access
	 * @param maxWidth maximum length of string
	 */
	public AbbreviateStringPropertyModel(Object modelObject, String expression, int maxWidth) {
		super(modelObject, expression);
		this.maxWidth = maxWidth;
	}
	
	@Override
	public String getObject() {
		String string = super.getObject();
		return StringUtils.abbreviate(string, maxWidth);
	}
}
