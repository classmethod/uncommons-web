/*
 * Copyright 2011-2012 Cloudstudy, Inc..
 * Created on 2012/03/24
 * 
 * All rights reserved.
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
