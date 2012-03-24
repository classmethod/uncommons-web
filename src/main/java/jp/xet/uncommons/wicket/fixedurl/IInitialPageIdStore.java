/*
 * Copyright 2012 Tsutomu YANO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.uncommons.wicket.fixedurl;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * 
 * <code><pre>
 * private Map<String, Integer> pageIdMap = new HashMap<String, Integer>();
 * 
 * public Integer putInitialId(Class<? extends Page> pageClass, PageParameters parameters, Integer id) {
 *   RequestCycle cycle = RequestCycle.get();
 *   Url url = cycle.mapUrlFor(pageClass, parameters);
 *   
 *   Integer oldValue = pageIdMap.put(url.getPath(), id);
 *   dirty();
 *   return oldValue;
 * }
 * 
 * public Integer getInitialId(Class<? extends Page> pageClass, PageParameters parameters) {
 *   RequestCycle cycle = RequestCycle.get();
 *   Url url = cycle.mapUrlFor(pageClass, parameters);
 *   
 *   return pageIdMap.get(url.getPath());
 * }
 * 
 * public Integer removeInitialId(Class<? extends Page> pageClass, PageParameters parameters) {
 *   RequestCycle cycle = RequestCycle.get();
 *   Url url = cycle.mapUrlFor(pageClass, parameters);
 *   
 *   Integer oldValue = pageIdMap.remove(url.getPath());
 *   dirty();
 *   return oldValue;
 * }
 * </pre></code>
 * 
 * @author Tsutomu YANO
 * @since 1.1
 */
public interface IInitialPageIdStore {
	
	/**
	 * TODO for daisuke
	 * 
	 * @param pageClass
	 * @param parameters
	 * @return
	 * @since 1.1
	 */
	Integer getInitialId(Class<? extends Page> pageClass, PageParameters parameters);
	
	/**
	 * TODO for daisuke
	 * 
	 * @param pageClass
	 * @param parameters
	 * @param id
	 * @return
	 * @since 1.1
	 */
	Integer putInitialId(Class<? extends Page> pageClass, PageParameters parameters, Integer id);
	
	/**
	 * TODO for daisuke
	 * 
	 * @param pageClass
	 * @param parameters
	 * @return
	 * @since 1.1
	 */
	Integer removeInitialId(Class<? extends Page> pageClass, PageParameters parameters);
	
}
