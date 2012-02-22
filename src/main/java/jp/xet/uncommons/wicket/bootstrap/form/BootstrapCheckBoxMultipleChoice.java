/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/26
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

import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BootstrapCheckBoxMultipleChoice<T> extends CheckBoxMultipleChoice<T> {
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id) {
		super(id);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, IModel<? extends Collection<T>> model,
			IModel<? extends List<? extends T>> choices) {
		super(id, model, choices);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, IModel<? extends Collection<T>> model,
			IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer) {
		super(id, model, choices, renderer);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, IModel<? extends Collection<T>> model, List<? extends T> choices) {
		super(id, model, choices);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, IModel<? extends Collection<T>> model, List<? extends T> choices,
			IChoiceRenderer<? super T> renderer) {
		super(id, model, choices, renderer);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, IModel<? extends List<? extends T>> choices) {
		super(id, choices);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, IModel<? extends List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer) {
		super(id, choices, renderer);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, List<? extends T> choices) {
		super(id, choices);
	}
	
	@SuppressWarnings("javadoc")
	public BootstrapCheckBoxMultipleChoice(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer) {
		super(id, choices, renderer);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void appendOptionHtml(final AppendingStringBuffer buffer, final T choice, int index,
			final String selected) {
		Object displayValue = getChoiceRenderer().getDisplayValue(choice);
		Class<?> objectClass = displayValue == null ? null : displayValue.getClass();
		// Get label for choice
		String label = "";
		if (objectClass != null && objectClass != String.class) {
			@SuppressWarnings("rawtypes")
			IConverter converter = getConverter(objectClass);
			label = converter.convertToString(displayValue, getLocale());
		} else if (displayValue != null) {
			label = displayValue.toString();
		}
		
		// If there is a display value for the choice, then we know that the
		// choice is automatic in some way. If label is /null/ then we know
		// that the choice is a manually created checkbox tag at some random
		// location in the page markup!
		if (label != null) {
			// Append option prefix
			buffer.append(getPrefix(index, choice));
			
			String id = getChoiceRenderer().getIdValue(choice, index);
			final String idAttr = getCheckBoxMarkupId(id);
			
			buffer.append("<label for=\"");
			buffer.append(idAttr);
			buffer.append("\">");
			
			// Add checkbox element
			buffer.append("<input name=\"");
			buffer.append(getInputName());
			buffer.append("\"");
			buffer.append(" type=\"checkbox\"");
			if (isSelected(choice, index, selected)) {
				buffer.append(" checked=\"checked\"");
			}
			if (isDisabled(choice, index, selected) || !isEnabledInHierarchy()) {
				buffer.append(" disabled=\"disabled\"");
			}
			buffer.append(" value=\"");
			buffer.append(id);
			buffer.append("\" id=\"");
			buffer.append(idAttr);
			buffer.append("\"/>");
			
			// Add label for checkbox
			String display = label;
			if (localizeDisplayValues()) {
				display = getLocalizer().getString(label, this, label);
			}
			
			final CharSequence escaped = getEscapeModelStrings() ? Strings.escapeMarkup(display) : display;
			
			buffer.append(escaped).append("</label>");
			
			// Append option suffix
			buffer.append(getSuffix(index, choice));
		}
	}
}
