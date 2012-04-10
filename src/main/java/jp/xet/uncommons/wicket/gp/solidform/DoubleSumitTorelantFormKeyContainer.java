/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/04/10
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
package jp.xet.uncommons.wicket.gp.solidform;

import org.apache.wicket.Session;

/**
 * ダブルクリックsubmit防止用キーのコンテナインターフェイス。
 * 
 * <p>通常、{@link Session}の実装クラスにimplementsする。</p>
 * 
 * @since 1.3
 * @version $Id$
 * @author daisuke
 */
public interface DoubleSumitTorelantFormKeyContainer {
	
	/**
	 * {@link FormKey}を追加する。
	 * 
	 * @param key {@link FormKey}
	 * @return 追加できた場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.3
	 */
	boolean addFormKey(FormKey key);
	
	/**
	 * {@link FormKey}を削除する。
	 * 
	 * @param key {@link FormKey}
	 * @return 削除できた場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.3
	 */
	boolean removeFormKey(FormKey key);
	
}
