/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/21
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class URIConverter extends AbstractConverter<URI> {
	
	private static Logger logger = LoggerFactory.getLogger(URIConverter.class);
	
	
	@Override
	public URI convertToObject(String value, Locale locale) {
		try {
			return new URI(value);
		} catch (URISyntaxException e) {
			logger.warn(value, e);
			throw newConversionException("", value, locale);
		}
	}
	
	@Override
	public String convertToString(URI value, Locale locale) {
		return value == null ? null : value.toASCIIString();
	}
	
	@Override
	protected Class<URI> getTargetType() {
		return URI.class;
	}
}
