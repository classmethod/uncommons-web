/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/10/16
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

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * TODO for daisuke
 * 
 * @since 1.7
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public final class EmptyPage<T> implements Page<T>, Serializable {
	
	private final Pageable pageable;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageable 
	 */
	public EmptyPage(Pageable pageable) {
		this.pageable = pageable;
	}
	
	@Override
	public List<T> getContent() {
		return Collections.emptyList();
	}
	
	@Override
	public int getNumber() {
		return pageable == null ? 0 : pageable.getPageNumber();
	}
	
	@Override
	public int getNumberOfElements() {
		return 0;
	}
	
	@Override
	public int getSize() {
		return pageable == null ? 0 : pageable.getPageSize();
	}
	
	@Override
	public Sort getSort() {
		return pageable == null ? null : pageable.getSort();
	}
	
	@Override
	public long getTotalElements() {
		return 0L;
	}
	
	@Override
	public int getTotalPages() {
		return 0;
	}
	
	@Override
	public boolean hasContent() {
		return false;
	}
	
	@Override
	public boolean hasNextPage() {
		return false;
	}
	
	@Override
	public boolean hasPreviousPage() {
		return false;
	}
	
	@Override
	public boolean isFirstPage() {
		return true;
	}
	
	@Override
	public boolean isLastPage() {
		return true;
	}
	
	@Override
	public Iterator<T> iterator() {
		return Iterators.emptyIterator();
	}
}
