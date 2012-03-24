/*
 * Copyright 2012 Daisuke Miyamoto.
 * Created on 2012/03/24
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
package jp.xet.uncommons.wicket.expire;

/**
 * TODO for daisuke
 * 
 * <code><pre>
 * private Set<Integer> expiredPageIds = new HashSet<Integer>();
 * 
 * public void setPageExpired(int pageId, boolean expired) {
 *   if (expired) {
 *     expiredPageIds.add(pageId);
 *   } else {
 *     expiredPageIds.remove(pageId);
 *   }
 *   dirty();
 * }
 * 
 * public boolean isPageExpired(int pageId) {
 *   return expiredPageIds.contains(pageId);
 * }
 * </pre></code>
 * 
 * @since 1.1
 * @version $Id$
 * @author daisuke
 */
public interface ExpiredPageIdStore {
	
	/**
	 * 指定したページIDの有効期限切れ状態を返す。
	 * 
	 * @param pageId ページID
	 * @return 有効期限切れ場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.1
	 */
	boolean isPageExpired(int pageId);
	
	/**
	 * 指定したページIDの有効期限切れ状態を設定する。
	 * 
	 * @param pageId ページID
	 * @param expired 有効期限切れである場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.1
	 */
	void setPageExpired(int pageId, boolean expired);
	
}
