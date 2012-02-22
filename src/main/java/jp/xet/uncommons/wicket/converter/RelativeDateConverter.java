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
package jp.xet.uncommons.wicket.converter;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Localizer;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class RelativeDateConverter extends AbstractConverter<Date> {
	
	private final Component component; // nullable
	
	
	/**
	 * インスタンスを生成する。
	 */
	public RelativeDateConverter() {
		this(null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param component
	 */
	public RelativeDateConverter(Component component) {
		this.component = component;
	}
	
	@Override
	public Date convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(Date value, Locale locale) {
		long duration = (System.currentTimeMillis() - value.getTime()) / 1000;
		
		Localizer localizer = Application.get().getResourceSettings().getLocalizer();
		
		if (duration < 60) {
			return localizer.getString("RelativeDateConverter.second", component, Model.of(duration));
		}
		
		duration /= 60;
		if (duration < 60) {
			return localizer.getString("RelativeDateConverter.minute", component, Model.of(duration));
		}
		
		duration /= 24;
		if (duration < 24) {
			return localizer.getString("RelativeDateConverter.hour", component, Model.of(duration));
		}
		
		duration /= 7;
		if (duration == 1) {
			return localizer.getString("RelativeDateConverter.yesterday", component, Model.of(duration));
		}
		if (duration < 7) {
			return localizer.getString("RelativeDateConverter.day", component, Model.of(duration));
		}
		
		IConverterLocator locator = Application.get().getConverterLocator();
		return locator.getConverter(Date.class).convertToString(value, locale);
	}
	
	@Override
	protected Class<Date> getTargetType() {
		return Date.class;
	}
}
