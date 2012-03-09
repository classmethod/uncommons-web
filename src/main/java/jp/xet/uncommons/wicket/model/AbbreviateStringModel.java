/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/07
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

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @since TODO for daisuke
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class AbbreviateStringModel extends AbstractReadOnlyModel<String> {
	
	private final IModel<String> source;
	
	private final int maxWidth;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * TODO あとで使う
	 */
	public AbbreviateStringModel(IModel<String> source, int maxWidth) {
		this.source = source;
		this.maxWidth = maxWidth;
	}
	
	@Override
	public void detach() {
		source.detach();
		super.detach();
	}
	
	@Override
	public String getObject() {
		return StringUtils.abbreviate(source.getObject(), maxWidth);
	}
}
