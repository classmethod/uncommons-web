/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/05/17
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

import jp.xet.uncommons.wicket.utils.WicketUtil;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * TODO for daisuke
 * 
 * @since 1.8
 * @version $Id: ProviderCountModel.java 6989 2012-07-13 09:58:41Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ProviderCountModel extends AbstractReadOnlyModel<Long> {
	
	private final IDataProvider<?> provider;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param provider {@link IDataProvider}
	 * @since 1.8
	 */
	public ProviderCountModel(IDataProvider<?> provider) {
		this.provider = provider;
	}
	
	@Override
	public void detach() {
		WicketUtil.detachIfNotNull(provider);
		super.detach();
	}
	
	@Override
	public Long getObject() {
		return provider == null ? 0L : provider.size();
	}
}
