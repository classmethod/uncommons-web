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
 * {@link UsernameValidator}のテスト。
 * 
 * @since 1.0.0
 * @version $Id: UsernameValidatorTest.java 2297 2012-01-19 06:41:23Z miyamoto $
 * @author daisuke
 */
public class UsernameValidatorTest extends AbstractValidatorTest {
	
	static final UsernameValidator validator = new UsernameValidator();
	
	
	@Test
	@SuppressWarnings("javadoc")
	public void testUsernameValidator() {
		assertValid(validator, "foo", true);
		assertValid(validator, "bar", true);
		assertValid(validator, "baz", true);
		
		// 不正単語
		assertValid(validator, "admin", false);
		assertValid(validator, "administrator", false);
		assertValid(validator, "studyadmin", false);
		assertValid(validator, "anonymous", false);
		assertValid(validator, "anonymous123", false);
		assertValid(validator, "foo_anonymous", false);
		assertValid(validator, "test", false);
		assertValid(validator, "test12", false);
		
		// 小文字のみOK、大文字はNG、数字はOK、_.-はOK
		assertValid(validator, "abcdefg", true);
		assertValid(validator, "Abcdefg", false);
		assertValid(validator, "abc123def", true);
		assertValid(validator, "ab_cd.ef-gh", true);
	}
}
