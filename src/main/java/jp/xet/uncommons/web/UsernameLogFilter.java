/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/09
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
package jp.xet.uncommons.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * ログイン中のユーザ名を SLF4J のログに出力するためのServletFilter実装クラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public class UsernameLogFilter implements Filter {
	
	private static final String USER_KEY = "username";
	
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		
		boolean successfulRegistration = false;
		if (auth != null) {
			String username = auth.getName();
			successfulRegistration = registerUsername(username);
		}
		
		try {
			chain.doFilter(request, response);
		} finally {
			if (successfulRegistration) {
				MDC.remove(USER_KEY);
			}
		}
	}
	
	@Override
	@SuppressWarnings("unused")
	public void init(FilterConfig config) throws ServletException {
	}
	
	/**
	 * Register the user in the {@link MDC} under {@link #USER_KEY}.
	 * 
	 * @param username the username
	 * @return true id the user can be successfully registered
	 */
	private boolean registerUsername(String username) {
		if (username != null && username.trim().length() > 0) {
			MDC.put(USER_KEY, username);
			return true;
		}
		return false;
	}
}
