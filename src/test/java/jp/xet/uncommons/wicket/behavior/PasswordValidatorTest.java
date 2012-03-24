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

import org.junit.Test;

/**
 * {@link PasswordValidator}のテストクラス。
 * 
 * @since 1.0.0
 * @version $Id: PasswordValidatorTest.java 2297 2012-01-19 06:41:23Z miyamoto $
 * @author daisuke
 */
public class PasswordValidatorTest extends AbstractValidatorTest {
	
	@Test
	public void testDanger() {
		PasswordValidator validator = new PasswordValidator();
		assertValid(validator, "123456", false);
		assertValid(validator, "john", false);
	}
	
	@Test
	public void testLength() {
		PasswordValidator validator = new PasswordValidator();
		
		assertValid(validator, "foo", false); // 6文字未満
		assertValid(validator, "bar", false); // 6文字未満
		assertValid(validator, "foobar", true);
		assertValid(validator, "bazqux", true);
		assertValid(validator, "asdfasdf;lkj;sadfhk;wero@i;ksd", true); // 30文字
		assertValid(validator, "asdfasdf;lkj;sadfhk;wero@i;ksdjnv", false); // 33文字
	}
	
	@Test
	public void testUsername() {
		PasswordValidator validator = new PasswordValidator("barbaz");
		
		assertValid(validator, "foobar", true);
		assertValid(validator, "foobarbaz", true);
		assertValid(validator, "barbaz", false);
		assertValid(validator, "barbazqux", true);
		assertValid(validator, "foobarbazqux", true);
	}
}
