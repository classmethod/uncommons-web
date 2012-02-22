/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/13
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

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * Bootstrap, from Twitter を利用するためのリソースクラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 * @see <a href="http://twitter.github.com/bootstrap/">Bootstrap, from Twitter</a>
 */
public class TwitterBootstrap {
	
	/** Bootstrap CSS resource reference */
	public static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(
			TwitterBootstrap.class, "bootstrap.min.css");
	
	/** Bootstrap JS resource reference */
	public static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(
			TwitterBootstrap.class, "bootstrap.min.js");
	
	
	private TwitterBootstrap() {
	}
}
