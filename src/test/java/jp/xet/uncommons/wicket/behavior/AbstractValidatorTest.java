/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/01/19
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang.Validate;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.Validatable;

/**
 * {@link IValidator}のテストヘルパー基底クラス。
 * 
 * @since 1.0.0
 * @version $Id: AbstractValidatorTest.java 2297 2012-01-19 06:41:23Z miyamoto $
 * @author daisuke
 */
public abstract class AbstractValidatorTest {
	
	/**
	 * バリデータが指定した値を{@code valid}と判断するというアサーションメソッド。
	 * 
	 * @param validator バリデータ
	 * @param value 値
	 * @param valid validと判断すべき場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	protected static <T>void assertValid(IValidator<T> validator, T value, boolean valid) {
		Validate.notNull(validator);
		Validatable<T> target = new Validatable<T>(value);
		validator.validate(target);
		assertThat(target.isValid(), is(valid));
	}
}
