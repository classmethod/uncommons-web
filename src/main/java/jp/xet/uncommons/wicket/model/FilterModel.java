/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/14
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

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.commons.lang.Validate;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class FilterModel<T> extends AbstractReadOnlyModel<List<? extends T>> {
	
	private final IModel<List<? extends T>> delegate;
	
	private final Predicate<T> predicate;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param delegate 
	 * @param predicate 
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public FilterModel(IModel<List<? extends T>> delegate, Predicate<T> predicate) {
		Validate.notNull(delegate);
		Validate.notNull(predicate);
		this.delegate = delegate;
		this.predicate = predicate;
	}
	
	@Override
	public void detach() {
		delegate.detach();
	}
	
	@Override
	public List<T> getObject() {
		return Lists.newArrayList(Iterables.filter(delegate.getObject(), predicate));
	}
}
