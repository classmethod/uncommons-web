/*
 * Copyright 2011 datemplatecopy.
 * Created on 2011/11/08
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
package jp.xet.uncommons.wicket.bootstrap;

import jp.xet.uncommons.wicket.behavior.FocusOnLoadBehavior;
import jp.xet.uncommons.wicket.bootstrap.form.ControlGroup;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authentication.strategy.DefaultAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.settings.ISecuritySettings;

/**
 * Reusable user sign in panel with username and password as well as support for persistence of the
 * both. When the SignInPanel's form is submitted, the method signIn(String, String) is called,
 * passing the username and password submitted. The signIn() method should authenticate the user's
 * session.
 * 
 * @since 1.0.0
 * @version $Id: SignInPanel.java 181 2011-11-16 15:21:56Z daisuke $
 * @see IAuthenticationStrategy
 * @see ISecuritySettings#getAuthenticationStrategy()
 * @see DefaultAuthenticationStrategy
 * @see WebSession#authenticate(String, String)
 * @author Jonathan Locke
 * @author Juergen Donnerstag
 * @author Eelco Hillenius
 * @author daisuke
 */
@SuppressWarnings("serial")
public class SignInPanel extends Panel {
	
	private static final String SIGN_IN_FORM = "signInForm";
	
	/** True if the panel should display a remember-me checkbox */
	private boolean includeRememberMe = true;
	
	/** True if the user should be remembered via form persistence (cookies) */
	private boolean rememberMe = true;
	
	/** password. */
	private String password;
	
	/** username. */
	private String username;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	public SignInPanel(String id) {
		this(id, true, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param includeRememberMe {@code true} if form should include a remember-me checkbox
	 * @param rememberMeDefaultValue default value of remember-me checkbox
	 */
	public SignInPanel(String id, boolean includeRememberMe, boolean rememberMeDefaultValue) {
		this(id, "", includeRememberMe, rememberMeDefaultValue);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param username デフォルトで表示するユーザ名
	 */
	public SignInPanel(String id, String username) {
		this(id, username, true, false);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param username デフォルトで表示するユーザ名
	 * @param includeRememberMe {@code true} if form should include a remember-me checkbox
	 * @param rememberMe default value of remember-me checkbox
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public SignInPanel(String id, String username, boolean includeRememberMe, boolean rememberMe) {
		super(id);
		
		this.username = username;
		this.includeRememberMe = includeRememberMe;
		this.rememberMe = rememberMe;
		
		// Add sign-in form to page, passing feedback panel as
		// validation error handler
		add(new SignInForm(SIGN_IN_FORM));
	}
	
	/**
	 * Convenience method to access the password.
	 * 
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Get model object of the rememberMe checkbox
	 * 
	 * @return True if user should be remembered in the future
	 */
	public boolean getRememberMe() {
		return rememberMe;
	}
	
	/**
	 * Convenience method to access the username.
	 * 
	 * @return The user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set the password
	 * 
	 * @param password The password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @param rememberMe If true, rememberMe will be enabled (username and password will be persisted somewhere)
	 */
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	/**
	 * Set the username
	 * 
	 * @param username The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return signin form
	 */
	protected SignInForm getForm() {
		return (SignInForm) get(SIGN_IN_FORM);
	}
	
	@Override
	protected void onBeforeRender() {
		// logged in already?
		if (isSignedIn() == false) {
			IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings()
				.getAuthenticationStrategy();
			// get username and password from persistence store
			String[] data = authenticationStrategy.load();
			
			if ((data != null) && (data.length > 1)) {
				// try to sign in the user
				if (signIn(data[0], data[1])) {
					username = data[0];
					password = data[1];
					
					// logon successful. Continue to the original destination
					if (continueToOriginalDestination() == false) {
						// Ups, no original destination. Go to the home page
						throw new RestartResponseException(getSession().getPageFactory().newPage(
								getApplication().getHomePage()));
					}
				} else {
					// the loaded credentials are wrong. erase them.
					authenticationStrategy.remove();
				}
			}
		}
		
		// don't forget
		super.onBeforeRender();
	}
	
	/**
	 * Called when sign in failed
	 */
	protected void onSignInFailed() {
		// Try the component based localizer first. If not found try the
		// application localizer. Else use the default
		error(getLocalizer().getString("signInFailed", this, "Sign in failed"));
	}
	
	/**
	 * Called when sign in was successful
	 */
	protected void onSignInSucceeded() {
		// If login has been called because the user was not yet logged in, than continue to the
		// original destination, otherwise to the Home page
		if (continueToOriginalDestination() == false) {
			setResponsePage(getApplication().getHomePage());
		}
	}
	
	/**
	 * @return true, if signed in
	 */
	private boolean isSignedIn() {
		return AuthenticatedWebSession.get().isSignedIn();
	}
	
	/**
	 * Sign in user if possible.
	 * 
	 * @param username The username
	 * @param password The password
	 * @return True if signin was successful
	 */
	private boolean signIn(String username, String password) {
		return AuthenticatedWebSession.get().signIn(username, password);
	}
	
	
	/**
	 * Sign in form.
	 */
	public final class SignInForm extends Form<SignInPanel> {
		
		/**
		 * Constructor.
		 * 
		 * @param id id of the form component
		 */
		public SignInForm(String id) {
			super(id);
			
			setModel(new CompoundPropertyModel<SignInPanel>(SignInPanel.this));
			
			// Attach textfields for username and password
			add(new ControlGroup<Void>("usernameGroup") {
				
				@Override
				protected void onInitialize() {
					super.onInitialize();
					add(new RequiredTextField<String>("username")
						.add(new FocusOnLoadBehavior()));
				}
			});
			
			add(new ControlGroup<Void>("passwordGroup") {
				
				@Override
				protected void onInitialize() {
					super.onInitialize();
					add(new PasswordTextField("password"));
				}
			});
			
			// MarkupContainer row for remember me checkbox
			add(new ControlGroup<Void>("rememberMeGroup") {
				
				@Override
				protected void onInitialize() {
					super.onInitialize();
					add(new CheckBox("rememberMe")
						.setVisible(includeRememberMe));
				}
			});
		}
		
		@Override
		public void onSubmit() {
			IAuthenticationStrategy strategy = getApplication().getSecuritySettings().getAuthenticationStrategy();
			
			if (signIn(getUsername(), getPassword())) {
				if (rememberMe == true) {
					strategy.save(username, password);
				} else {
					strategy.remove();
				}
				
				onSignInSucceeded();
			} else {
				onSignInFailed();
				strategy.remove();
			}
		}
		
		@Override
		protected boolean getStatelessHint() {
			return true;
		}
	}
}
