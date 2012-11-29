/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/10/25
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
package jp.xet.uncommons.utils;

import java.io.Serializable;

import com.google.common.base.Function;

/**
 * TODO for daisuke
 * 
 * @since 1.8
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class QuoteFunction implements Function<String, String>, Serializable {
	
	private final boolean emptyIfNull;
	
	private final String prefix;
	
	private final String suffix;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param quote 引用符
	 * @since 1.8
	 */
	public QuoteFunction(String quote) {
		this(quote, quote, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param quote 引用符
	 * @param emptyIfNull {@code null}入力時に空文字をクオートする場合は{@code true}、{@code null}を返す場合は{@code false}
	 * @since 1.8
	 */
	public QuoteFunction(String quote, boolean emptyIfNull) {
		this(quote, quote, emptyIfNull);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param prefix 前置句
	 * @param suffix 後置句
	 * @since 1.8
	 */
	public QuoteFunction(String prefix, String suffix) {
		this(prefix, suffix, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param prefix 前置句
	 * @param suffix 後置句
	 * @param emptyIfNull {@code null}入力時に空文字をクオートする場合は{@code true}、{@code null}を返す場合は{@code false}
	 * @since 1.8
	 */
	public QuoteFunction(String prefix, String suffix, boolean emptyIfNull) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.emptyIfNull = emptyIfNull;
	}
	
	@Override
	public String apply(String input) {
		if (input == null && emptyIfNull) {
			input = ""; // CHECKSTYLE IGNORE THIS LINE
		}
		return input == null ? null : String.format("%s%s%s", prefix, input, suffix);
	}
}
