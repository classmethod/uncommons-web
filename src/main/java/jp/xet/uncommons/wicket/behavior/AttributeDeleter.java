/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/05
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

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;

/**
 * 属性を削除するビヘイビア実装クラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class AttributeDeleter extends AttributeModifier {
	
	/**
	 * Separator between the current value and the concatenated value, typically a space ' ' or
	 * colon ';'.
	 */
	private String separator;
	
	
	/**
	 * Creates an attribute modifier that concatenates the {@code replaceModel} to the attribute's
	 * current value, optionally separated by the {@link #getSeparator() separator}.
	 * 
	 * @param attribute the attribute to delete the deleteModels value to
	 * @param replaceModel The model to replace the value with
	 */
	public AttributeDeleter(String attribute, IModel<?> replaceModel) {
		super(attribute, replaceModel);
	}
	
	/**
	 * Creates an AttributeModifier that deletes the deleteModel's value to the current value of the
	 * attribute, and will add the attribute when it is not there already.
	 * 
	 * @param attribute the attribute to delete the deleteModels value to
	 * @param deleteModel the model supplying the value to delete
	 * @param separator the separator string, comes between the original value and the delete value
	 */
	public AttributeDeleter(String attribute, IModel<?> deleteModel, String separator) {
		super(attribute, deleteModel);
		setSeparator(separator);
	}
	
	/**
	 * Creates an attribute modifier that deletes the {@code value} to the attribute's current
	 * value, optionally separated by the {@link #getSeparator() separator}.
	 * 
	 * @param attribute the attribute to delete the deleteModels value to
	 * @param value The value for the attribute
	 */
	public AttributeDeleter(String attribute, Serializable value) {
		super(attribute, value);
	}
	
	/**
	 * Gets the separator used by attribute deleters and prependers.
	 * 
	 * @return the separator used by attribute deleters and prependers.
	 */
	public String getSeparator() {
		return separator;
	}
	
	/**
	 * Sets the separator used by attribute deleters and prependers.
	 * 
	 * @param separator
	 *            a space, semicolon or other character used to separate the current value and the
	 *            appended/prepended value.
	 * @return this
	 */
	public AttributeDeleter setSeparator(String separator) {
		this.separator = separator;
		return this;
	}
	
	@Override
	public String toString() {
		String attributeModifier = super.toString();
		attributeModifier =
				attributeModifier.substring(0, attributeModifier.length() - 2) + ", separator=" + separator + "]";
		return attributeModifier;
	}
	
	@Override
	protected String newValue(String currentValue, String deleteValue) {
		// Short circuit when one of the values is empty: return the other value.
		if (Strings.isNullOrEmpty(currentValue)) {
			return null;
		} else if (Strings.isNullOrEmpty(deleteValue)) {
			return currentValue;
		}
		
		if (Strings.isNullOrEmpty(separator)) {
			return currentValue.replace(deleteValue, "");
		} else {
			List<String> out = Lists.newArrayList();
			for (String element : currentValue.split(separator)) {
				if (element.equals(deleteValue) == false) {
					out.add(element);
				}
			}
			return Joiner.on(separator).join(out);
		}
	}
}
