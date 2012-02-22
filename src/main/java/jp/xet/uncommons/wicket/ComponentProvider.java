/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/07
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
package jp.xet.uncommons.wicket;

import org.apache.wicket.Component;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public interface ComponentProvider<T extends Component> {
	
	/**
	 * TODO for daisuke
	 * 
	 * @param id
	 * @return
	 * @since 1.0
	 */
	T create(String id);
}
