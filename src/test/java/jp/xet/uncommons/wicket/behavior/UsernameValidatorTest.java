/*
 * Copyright 2011 Cloudstudy, Inc..
 * Created on 2012/01/19
 * 
 * All rights reserved.
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
		
		// testに関しては頭でなければOK
		assertValid(validator, "contest", true);
		
		// 小文字のみOK、大文字はNG、数字はOK、_.-はOK
		assertValid(validator, "abcdefg", true);
		assertValid(validator, "Abcdefg", false);
		assertValid(validator, "abc123def", true);
		assertValid(validator, "ab_cd.ef-gh", true);
	}
}
