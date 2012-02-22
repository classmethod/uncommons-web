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
package jp.xet.uncommons.wicket.gp;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * {@link Label} implementation which abbreviates a String using ellipses.
 * 
 * <p>This will turn {@code "Now is the time for all good men"} into
 * {@code "Now is the time for..."}</p>
 *
 * <p>Specifically:
 * <ul>
 *   <li>If label string is less than {@code maxWidth} characters long, display it.</li>
 *   <li>Else abbreviate it to {@code (substring(str, 0, max-3) + "...")}.</li>
 *   <li>If {@code maxWidth} is less than {@code 4}, throw an
 *       {@link IllegalArgumentException}.</li>
 *   <li>In no case will it return a String of length greater than
 *       {@code maxWidth}.</li>
 * </ul>
 * </p>
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 * @see StringUtils#abbreviate(String, int)
 */
@SuppressWarnings("serial")
public class AbbreviateLabel extends Label {
	
	private int maxWidth;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param maxWidth  maximum length of result String, must be at least 4
	 */
	public AbbreviateLabel(String id, IModel<?> model, int maxWidth) {
		super(id, model);
		Validate.isTrue(maxWidth >= 4, "Minimum abbreviation width is 4");
		this.maxWidth = maxWidth;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param maxWidth  maximum length of result String, must be at least 4
	 */
	public AbbreviateLabel(String id, int maxWidth) {
		super(id);
		Validate.isTrue(maxWidth >= 4, "Minimum abbreviation width is 4");
		this.maxWidth = maxWidth;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param label
	 * @param maxWidth  maximum length of result String, must be at least 4
	 */
	public AbbreviateLabel(String id, String label, int maxWidth) {
		super(id, label);
		Validate.isTrue(maxWidth >= 4, "Minimum abbreviation width is 4");
		this.maxWidth = maxWidth;
	}
	
	@Override
	public <C>IConverter<C> getConverter(final Class<C> type) {
		return new AbstractConverter<C>() {
			
			@Override
			public C convertToObject(String value, Locale locale) {
				return null;
			}
			
			@Override
			public String convertToString(C value, Locale locale) {
				IConverter<C> converter = AbbreviateLabel.super.getConverter(type);
				String string = converter.convertToString(value, locale);
				return StringUtils.abbreviate(string, maxWidth);
			}
			
			@Override
			protected Class<C> getTargetType() {
				return type;
			}
		};
	}
}
