/*
 * Copyright 2011-2012 Daisuke Miyamoto.
 * Created on 2011/12/19
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
package jp.xet.uncommons.wicket.dataprovider;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 1つだけの要素を提供するデータプロバイダ実装クラス。
 * 
 * @param <T> 要素の型
 * @since 1.0.0
 * @version $Id: SingletonDataProvider.java 3627 2012-03-08 01:06:09Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class SingletonDataProvider<T extends Serializable> implements IDataProvider<T> {
	
	private List<T> list;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param singleton 唯一の要素
	 */
	public SingletonDataProvider(T singleton) {
		list = Collections.singletonList(singleton);
	}
	
	@Override
	public void detach() {
		// nothing to do
	}
	
	@Override
	public Iterator<? extends T> iterator(int first, int count) {
		return list.iterator();
	}
	
	@Override
	public IModel<T> model(T object) {
		return Model.of(object);
	}
	
	@Override
	public int size() {
		return 1;
	}
}
