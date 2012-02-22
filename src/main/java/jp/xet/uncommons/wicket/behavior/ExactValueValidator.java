/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/18
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

import org.apache.commons.lang.ObjectUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

/**
 * 指定した値のみを許可するバリデータ実装クラス。
 * 
 * @param <T> 検証値の型
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ExactValueValidator<T> extends AbstractValidator<T> {
	
	/** whether to exclude matching input **/
	private boolean reverse = false;
	
	private final T standard;
	
	
	/**
	 * インスタンスを生成する。
	 * @param standard 基準
	 */
	public ExactValueValidator(T standard) {
		this.standard = standard;
	}
	
	/**
	 * If set to true then input that matches the pattern is considered invalid.
	 * 
	 * @param reverse
	 * @return itself
	 */
	public ExactValueValidator<T> setReverse(boolean reverse) {
		this.reverse = reverse;
		return this;
	}
	
	@Override
	protected void onValidate(IValidatable<T> validatable) {
		T value = validatable.getValue();
		if (ObjectUtils.equals(value, standard) == reverse) {
			error(validatable);
		}
	}
}
