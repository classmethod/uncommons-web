/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/06/19
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
package jp.xet.uncommons.wicket.behavior;

import java.util.Collection;

import jp.xet.uncommons.wicket.utils.WicketUtil;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

/**
 * 指定したコレクションモデルの要素が空の場合に visibility がONになるビヘイビア。
 * 
 * @since 1.8
 * @version $Id: VisibleIfCollectionIsEmptyBehavior.java 8627 2012-10-12 02:30:06Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class VisibleIfCollectionIsEmptyBehavior extends Behavior {
	
	private final IModel<? extends Collection<?>> model;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param model dependent collection model
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.8
	 */
	public VisibleIfCollectionIsEmptyBehavior(IModel<? extends Collection<?>> model) {
		Args.notNull(model, "model");
		this.model = model;
	}
	
	@Override
	public void detach(Component component) {
		WicketUtil.detachIfNotNull(model);
		super.detach(component);
	}
	
	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);
		Collection<?> object = model.getObject();
		component.setVisibilityAllowed(object == null || object.isEmpty());
	}
}
