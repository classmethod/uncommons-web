/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/03/27
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
package jp.xet.uncommons.wicket.auth;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * TODO for daisuke
 * 
 * @since 1.1
 * @version $Id: LoginInvisibleWebMarkupContainer.java 4398 2012-03-27 01:02:54Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class LoginInvisibleWebMarkupContainer extends WebMarkupContainer {
	
	/**
	 * @see Component#Component(String)
	 */
	public LoginInvisibleWebMarkupContainer(String id) {
		super(id);
	}
	
	/**
	 * @see Component#Component(String, IModel)
	 */
	public LoginInvisibleWebMarkupContainer(String id, IModel<?> model) {
		super(id, model);
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		boolean signedIn = AuthenticatedWebSession.get().isSignedIn();
		setVisibilityAllowed(signedIn == false);
	}
}
