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

import java.util.Locale;
import java.util.regex.Pattern;

import com.ibm.icu.text.Transliterator;

import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public class HiraganaConverter extends AbstractConverter<CharSequence> {
	
	private static final Transliterator HALFWIDTH_TO_FULLWIDTH = Transliterator.getInstance("Halfwidth-Fullwidth");
	
	private static final Pattern HIRAGANA_PATTERN = Pattern.compile("^[ぁ-んゔ ]*$");
	
	private static final Transliterator KATAKANA_TO_HIRAGANA = Transliterator.getInstance("Katakana-Hiragana");
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public CharSequence convertToObject(String value, Locale locale) {
		String result = HALFWIDTH_TO_FULLWIDTH.transform(value);
		result = KATAKANA_TO_HIRAGANA.transform(result);
		result = result.replaceAll("　", " ");
		
		if (HIRAGANA_PATTERN.matcher(result).matches() == false) {
			throw newConversionException("Cannot convert to hiragana", value, locale).setResourceKey(
					"HiraganaConverter");
		}
		return result;
	}
	
	@Override
	protected Class<CharSequence> getTargetType() {
		return CharSequence.class;
	}
}
