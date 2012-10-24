/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/07
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
package jp.xet.uncommons.spring.social;

import java.util.Collection;

import com.google.common.base.Preconditions;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.connect.ConnectionKey;

/**
 * ソーシャルログインした場合の認証トークン。
 * 
 * @since 1.8
 * @version $Id: SocialAuthenticationToken.java 4970 2012-04-11 08:00:18Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class SocialAuthenticationToken extends AbstractAuthenticationToken {
	
	private final Object principal;
	
	private final ConnectionKey connectionKey;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param principal ユーザ
	 * @param connectionKey ソーシャル連携キー
	 * @param authorities 権限
	 * @throws NullPointerException 引数{@code principal}または{@code connectionKey}に{@code null}を与えた場合
	 */
	public SocialAuthenticationToken(Object principal, ConnectionKey connectionKey,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		Preconditions.checkNotNull(principal);
		Preconditions.checkNotNull(connectionKey);
		this.principal = principal;
		this.connectionKey = connectionKey;
		super.setAuthenticated(true); // must use super, as we override
	}
	
	/**
	 * ソーシャル連携キーを返す。
	 * 
	 * @return ソーシャル連携キー
	 * @since 1.2
	 */
	public ConnectionKey getConnectionKey() {
		return connectionKey;
	}
	
	@Override
	public Object getCredentials() {
		return null;
	}
	
	@Override
	public Object getPrincipal() {
		return principal;
	}
	
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		
		super.setAuthenticated(false);
	}
}
