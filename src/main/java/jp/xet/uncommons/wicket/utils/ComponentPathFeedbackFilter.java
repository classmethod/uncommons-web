/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/04
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
package jp.xet.uncommons.wicket.utils;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ComponentPathFeedbackFilter implements IFeedbackMessageFilter {
	
	private final String path;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param path 
	 */
	public ComponentPathFeedbackFilter(String path) {
		this.path = path;
	}
	
	@Override
	public boolean accept(FeedbackMessage message) {
		return message.getReporter().getPath().equals(path);
	}
}
