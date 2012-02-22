/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/14
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
package jp.xet.uncommons.wicket.jquery;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * JQueryを利用するためのリソースクラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public class JQuery {
	
	/** JQuery JavaScript resource reference */
	public static final JavaScriptResourceReference JQUERY_JS =
			new JavaScriptResourceReference(JQuery.class, "jquery-1.6.4.js");
	
	/** JQuery-UI JavaScript resource reference */
	public static final JavaScriptResourceReference JQUERY_UI_JS =
			new JavaScriptResourceReference(JQuery.class, "jquery-ui-1.8.13.min.js");
	
	/** JQuery-UI CSS resource reference */
	public static final CssResourceReference JQUERY_UI_CSS =
			new CssResourceReference(JQuery.class, "jquery-ui-1.8.13.css");
	
	
	private JQuery() {
	}
}
