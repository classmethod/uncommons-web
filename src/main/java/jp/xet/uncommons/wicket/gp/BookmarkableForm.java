/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/28
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
package jp.xet.uncommons.wicket.gp;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;

/**
 * TODO for daisuke
 * 
 * @param <T> The model object type
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class BookmarkableForm<T> extends Form<T> {
	
	private String actionPath;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 */
	public BookmarkableForm(String id) {
		this(id, "");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param actionPath
	 */
	public BookmarkableForm(String id, String actionPath) {
		super(id);
		this.actionPath = actionPath;
	}
	
	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		if (isRootForm()) {
			StringBuilder buffer = new StringBuilder();
			getResponse().write(buffer);
		}
		renderComponentTagBody(markupStream, openTag);
	}
	
	@Override
	protected boolean getStatelessHint() {
		return true;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		tag.put("action", actionPath);
	}
	
	private void renderComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
		if ((markupStream != null) && (markupStream.getCurrentIndex() > 0)) {
			ComponentTag origOpenTag = (ComponentTag) markupStream.get(markupStream.getCurrentIndex() - 1);
			if (origOpenTag.isOpenClose()) {
				return;
			}
		}
		boolean render = openTag.requiresCloseTag();
		if (render == false) {
			render = !openTag.hasNoCloseTag();
		}
		if (render == true) {
			renderAll(markupStream, openTag);
		}
	}
}
