/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/07
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
package jp.xet.uncommons.spring;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 複数の {@link SaltSource} の合成を行う実装クラス。
 * 
 * <p>複数の{@link SaltSource}からsaltを生成し、 {@link Object#toString() toString()}で文字列化したものを
 * {@code separator}で指定した文字列を間に挟んで接続する。</p>
 * 
 * @since 1.0.0
 * @version $Id: CompositeSaltSource.java 201 2012-02-04 00:42:19Z daisuke $
 * @author daisuke
 */
public class CompositeSaltSource implements SaltSource {
	
	private final SaltSource[] sources;
	
	private final String separator;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param sources array of {@link SaltSource}
	 * @throws IllegalArgumentException 引数{@code sources}に{@code null}を与えた場合
	 */
	public CompositeSaltSource(SaltSource[] sources) {
		this(sources, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param sources array of {@link SaltSource}
	 * @param separator the separator character to use, {@code null} treated as ""
	 * @throws IllegalArgumentException 引数{@code sources}に{@code null}を与えた場合
	 */
	public CompositeSaltSource(SaltSource[] sources, String separator) {
		Validate.noNullElements(sources);
		this.sources = sources.clone();
		this.separator = StringUtils.defaultString(separator);
	}
	
	@Override
	public Object getSalt(UserDetails user) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sources.length; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(sources[i].getSalt(user));
		}
		return sb.toString();
	}
}
