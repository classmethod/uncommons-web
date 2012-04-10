/*
 * Copyright 2011 datemplatecopy.
 * Created on 2011/11/04
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

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;

/**
 * ユーザ名として利用できる文字列かどうかを判定するバリデータ。
 * 
 * @since 1.0.0
 * @version $Id: UsernameValidator.java 195 2012-01-20 01:19:22Z daisuke $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class UsernameValidator extends CompoundValidator<String> {
	
	private static final String[] RESERVED = {
		// 含有禁止
		".*admin.*",
		".*root.*",
		".*anonymous.*",
		".*login.*",
		".*logout.*",
		".*auth.*",
		".*security.*",
		".*privacy.*",
		".*facebook.*",
		".*twitter.*",
		".*mixi.*",
		
		// 直接禁止(操作系単語)
		"add",
		"cancel",
		"create",
		"delete",
		"edit",
		"like",
		"order",
		"get",
		"put",
		"post",
		"remove",
		"search",
		"save",
		
		// 直接禁止(ドメインオブジェクト単語)
		"alert",
		"api",
		"app",
		"apps",
		"application",
		"applications",
		"error",
		"fatal",
		"info",
		"mine",
		"sales",
		"user",
		"warn",
		"warning",
		
		"about",
		"account",
		"config",
		"configuration",
		"configurations",
		"connect",
		"contact",
		"contacts",
		"cart",
		"feed",
		"help",
		"home",
		"map",
		"register",
		"signup",
		"setting",
		"settings",
		"status",
		"term",
		"terms",
		"welcome",
		"tour",
		
		// 先頭末尾禁止
		".*master$",
		"^master.*",
		".*owner$",
		"^owner.*",
		".*support$",
		"^support.*",
		".*staff$",
		"^staff.*",
		".*official$",
		"^official.*",
		".*debug$",
		"^debug.*",
		".*test$",
		"^test.*",
		
		// 先頭禁止
		"^account.*",
		"^enterprise.*",
		"^jobs.*",
		"^news.*",
		"^popular.*",
		"^shop.*",
		"^translations.*",
		"^wiki.*",
		"^organizations.*",
		"^better.*",
		"^hosting.*",
		"^url.*",
		"^archive.*",
		"^download.*",
		"^email.*",
		"^faq.*",
		"^favorite.*",
		"^invit.*",
		"^openid.*",
		"^session.*",
		"^ssl.*",
		"^activate.*",
		"^document.*",
		"^email.*",
		"^friend.*",
		"^group.*",
		"^recruitment.*",
		"^sys.*",
		
		"^sitemap.*",
		"^index.*",
		".*rss$",
		".*json$",
		".*xml$",
	
	};
	
	private static final String DEFAULT_PATTERN = "[a-z0-9_\\.\\-]*";
	
	
	/**
	 * インスタンスを生成する。
	 */
	public UsernameValidator() {
		this(DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pattern validとするユーザ名の正規表現パターン
	 */
	public UsernameValidator(String pattern) {
		add(new PatternValidator(pattern) {
			
			@Override
			protected String resourceKey() {
				return "UsernameValidator.pattern";
			}
		});
		
		for (String reserved : RESERVED) {
			add(new ReservedWordValidator(reserved));
		}
	}
	
	
	private static class ReservedWordValidator extends PatternValidator {
		
		private ReservedWordValidator(String pattern) {
			super(pattern);
			setReverse(true);
		}
		
		@Override
		protected String resourceKey() {
			return "UsernameValidator.reserved";
		}
	}
}
