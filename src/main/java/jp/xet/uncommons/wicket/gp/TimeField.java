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

import java.sql.Time;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class TimeField extends AutoCompleteTextField<Time> {
	
	private static final IAutoCompleteRenderer<Time> AUTO_COMPLETE_TEXT_RENDERER =
			new AbstractAutoCompleteTextRenderer<Time>() {
				
				@Override
				protected String getTextValue(Time object) {
					return Application.get().getConverterLocator().getConverter(Time.class)
						.convertToString(object, Session.get().getLocale());
				}
			};
	
	private static final Pattern NORMALIZE_PATTERN = Pattern.compile("^0|[^0-9]");
	
	
	private static AutoCompleteSettings newDefaultAutoCompleteSettings() {
		AutoCompleteSettings settings = new AutoCompleteSettings();
		settings.setShowCompleteListOnFocusGain(true);
		settings.setShowListOnEmptyInput(true);
		settings.setShowListOnFocusGain(true);
		settings.setMaxHeightInPx(160); // CHECKSTYLE IGNORE THIS LINE
		settings.setThrottleDelay(0);
		
		return settings;
	}
	
	
	private final List<Time> times;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	public TimeField(String id) {
		this(id, null, newDefaultAutoCompleteSettings());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param settings {@link AutoCompleteSettings}
	 */
	public TimeField(String id, AutoCompleteSettings settings) {
		this(id, null, settings);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public TimeField(String id, IModel<Time> model) {
		this(id, model, newDefaultAutoCompleteSettings());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param settings {@link AutoCompleteSettings}
	 */
	public TimeField(String id, IModel<Time> model, AutoCompleteSettings settings) {
		super(id, model, null, TimeField.AUTO_COMPLETE_TEXT_RENDERER, settings);
		
		setType(Time.class);
		this.add(new AttributeAppender("class", Model.of("time"), " "));
		
		Calendar calendar = Calendar.getInstance(Session.get().getLocale());
		calendar.clear();
		List<Time> t = Lists.newArrayList();
		for (int i = 0; i < 48; i++) { // CHECKSTYLE IGNORE THIS LINE
			t.add(new Time(calendar.getTimeInMillis()));
			calendar.add(Calendar.MINUTE, 30); // CHECKSTYLE IGNORE THIS LINE
		}
		
		times = Collections.unmodifiableList(t);
		
	}
	
	@Override
	protected Iterator<Time> getChoices(final String input) {
		final String normalizedInput = TimeField.NORMALIZE_PATTERN.matcher(input).replaceAll("");
		
		return Collections2.filter(times, new Predicate<Time>() {
			
			@Override
			public boolean apply(Time time) {
				String timeAsString =
						TimeField.this.getConverter(Time.class).convertToString(time, TimeField.this.getLocale());
				return TimeField.NORMALIZE_PATTERN.matcher(timeAsString).replaceAll("").startsWith(normalizedInput);
			}
		}).iterator();
	}
}
