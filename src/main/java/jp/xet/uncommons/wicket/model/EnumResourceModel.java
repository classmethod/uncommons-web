/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/18
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
package jp.xet.uncommons.wicket.model;

import org.apache.wicket.model.ResourceModel;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class EnumResourceModel extends ResourceModel {
	
	private static String toResourceKey(Enum<?> e) {
		if (e == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder(e.getClass().getSimpleName());
		sb.append(".").append(e.name());
		return sb.toString();
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param e enum instance
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public EnumResourceModel(Enum<?> e) {
		super(toResourceKey(e));
	}
}
