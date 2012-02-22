/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2012/01/19
 * 
 * All rights reserved.
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
