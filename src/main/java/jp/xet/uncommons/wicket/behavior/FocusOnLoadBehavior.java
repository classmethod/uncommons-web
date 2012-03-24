/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/09/20
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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * bodyタグのonloadにフォームへのフォーカスを当てるビヘイビア。
 * 
 * @since 1.0
 * @version $Id: FocusLoadBehavior.java 98 2011-10-01 14:37:41Z daisuke $
 * @author James Carman
 * @author daisuke
 * @see <a href="https://cwiki.apache.org/confluence/display/WICKET/Request+Focus+on+a+Specific+Form+Component">Request
 * 		Focus on a Specific Form Component</a>
 */
@SuppressWarnings("serial")
public class FocusOnLoadBehavior extends Behavior {
	
	@Override
	public boolean getStatelessHint(Component component) {
		return true;
	}
	
	@Override
	public boolean isTemporary(Component component) {
		// remove the behavior after component has been rendered
		return true;
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		component.setOutputMarkupId(true);
		
		// last semicolon is not required
		String js = String.format("document.getElementById('%s').focus()", component.getMarkupId());
		response.renderOnLoadJavaScript(js);
	}
}
