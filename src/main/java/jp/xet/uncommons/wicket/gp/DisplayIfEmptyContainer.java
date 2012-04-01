/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/25
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

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 * {@link IDataProvider}が空の時のみ表示する{@link WebMarkupContainer}クラス。
 * 
 * @since 1.2
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class DisplayIfEmptyContainer extends WebMarkupContainer {
	
	private final IDataProvider<?> provider;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param provider 対象の{@link IDataProvider}
	 */
	public DisplayIfEmptyContainer(String id, IDataProvider<?> provider) {
		super(id);
		Validate.notNull(provider);
		this.provider = provider;
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		int size = provider.size();
		setVisibilityAllowed(size <= 0);
	}
	
	@Override
	protected void onDetach() {
		provider.detach();
		super.onDetach();
	}
}
