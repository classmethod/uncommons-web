/*
 * Copyright 2011 datemplatecopy.
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
package jp.xet.uncommons.wicket.bootstrap.alert;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;

/**
 * アラートパネル実装クラス。
 * 
 * @since 1.0.0
 * @version $Id: AlertPanel.java 1031 2011-11-16 10:57:51Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public class AlertPanel extends GenericPanel<Void> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param title タイトル
	 * @param body 本文
	 * @param closable 閉じるボタンを表示する場合は{@code true}、そうでない場合は{@code false}
	 */
	public AlertPanel(String id, String title, String body, boolean closable) {
		super(id);
		add(new Label("title", title));
		add(new Label("body", body));
		add(new Label("closeButton", "x").setVisible(closable));
	}
}
