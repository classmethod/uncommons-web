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
package jp.xet.uncommons.wicket.model;

import java.util.NoSuchElementException;

import jp.xet.uncommons.wicket.utils.WicketUtil;

import com.google.common.collect.Iterables;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Args;

/**
 * {@link Iterable}の要素のうち、唯一の要素を表すモデル。
 * 
 * <p>{@link Iterable}が要素を持たない場合や、複数の要素を持つ場合は、{@code null}となる。</p>
 * 
 * @param <T> 要素の型
 * @since 1.8
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class OnlyElementModel<T> extends LoadableDetachableModel<T> {
	
	private final IModel<? extends Iterable<T>> collectionModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param collectionModel コレクション
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public OnlyElementModel(IModel<? extends Iterable<T>> collectionModel) {
		Args.notNull(collectionModel, "collectionModel");
		this.collectionModel = collectionModel;
	}
	
	@Override
	protected T load() {
		Iterable<T> iterable = collectionModel.getObject();
		try {
			return Iterables.getOnlyElement(iterable);
		} catch (NoSuchElementException e) {
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	@Override
	protected void onDetach() {
		WicketUtil.detachIfNotNull(collectionModel);
		super.onDetach();
	}
}
