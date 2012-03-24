/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/14
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
package jp.xet.uncommons.web.env;

import org.apache.commons.lang.Validate;

/**
 * TODO for daisuke
 * 
 * @since 1.1
 * @version $Id$
 * @author daisuke
 */
public class LocalEnvironmentProfile implements EnvironmentProfile {
	
	private final String environmentName;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param environmentName 環境名
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合 
	 */
	public LocalEnvironmentProfile(String environmentName) {
		Validate.notNull(environmentName);
		this.environmentName = environmentName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (EnvironmentProfile.class.isAssignableFrom(obj.getClass()) == false) {
			return false;
		}
		return toString().equals(obj.toString());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + environmentName.toString().hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return environmentName;
	}
}
