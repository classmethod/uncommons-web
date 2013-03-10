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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

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
		Args.isTrue(maxWidth >= 4, "Minimum abbreviation width is 4");
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
		Args.isTrue(maxWidth >= 4, "Minimum abbreviation width is 4");
		this.maxWidth = maxWidth;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param label label string
	 * @param maxWidth  maximum length of result String, must be at least 4
	 */
	public AbbreviateLabel(String id, String label, int maxWidth) {
		super(id, label);
		Args.isTrue(maxWidth >= 4, "Minimum abbreviation width is 4");
		this.maxWidth = maxWidth;
	}
	
	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		String abbreviated = abbreviate(getDefaultModelObjectAsString(), maxWidth);
		replaceComponentTagBody(markupStream, openTag, Strings.escapeMarkup(abbreviated));
	}
	
	/**
	 * <p>Abbreviates a String using ellipses. This will turn
	 * "Now is the time for all good men" into "Now is the time for..."</p>
	 *
	 * <p>Specifically:
	 * <ul>
	 *   <li>If <code>str</code> is less than <code>maxWidth</code> characters
	 *       long, return it.</li>
	 *   <li>Else abbreviate it to <code>(substring(str, 0, max-3) + "...")</code>.</li>
	 *   <li>If <code>maxWidth</code> is less than <code>4</code>, throw an
	 *       <code>IllegalArgumentException</code>.</li>
	 *   <li>In no case will it return a String of length greater than
	 *       <code>maxWidth</code>.</li>
	 * </ul>
	 * </p>
	 *
	 * <pre>
	 * StringUtils.abbreviate(null, *)      = null
	 * StringUtils.abbreviate("", 4)        = ""
	 * StringUtils.abbreviate("abcdefg", 6) = "abc..."
	 * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
	 * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
	 * StringUtils.abbreviate("abcdefg", 4) = "a..."
	 * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
	 * </pre>
	 *
	 * @param str  the String to check, may be null
	 * @param maxWidth  maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws IllegalArgumentException if the width is too small
	 */
	protected String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}
	
	/**
	 * <p>Abbreviates a String using ellipses. This will turn
	 * "Now is the time for all good men" into "...is the time for..."</p>
	 *
	 * <p>Works like <code>abbreviate(String, int)</code>, but allows you to specify
	 * a "left edge" offset.  Note that this left edge is not necessarily going to
	 * be the leftmost character in the result, or the first character following the
	 * ellipses, but it will appear somewhere in the result.
	 *
	 * <p>In no case will it return a String of length greater than
	 * <code>maxWidth</code>.</p>
	 *
	 * <pre>
	 * StringUtils.abbreviate(null, *, *)                = null
	 * StringUtils.abbreviate("", 0, 4)                  = ""
	 * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
	 * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
	 * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
	 * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
	 * </pre>
	 *
	 * @param str  the String to check, may be null
	 * @param offset  left edge of source String
	 * @param maxWidth  maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws IllegalArgumentException if the width is too small
	 */
	protected String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		if (maxWidth < 4) {
			throw new IllegalArgumentException("Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (offset > str.length()) {
			offset = str.length();
		}
		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}
		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
		}
		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}
}
