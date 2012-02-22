/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/04
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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 静的画像コンポーネント。
 * 
 * @since 1.0.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class StaticImage extends GenericWebComponent<String> {
	
	private static final int NOT_SPECIFIED = -1;
	
	private int width;
	
	private int height;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model which represents source url
	 */
	public StaticImage(String id, IModel<String> model) {
		this(id, model, NOT_SPECIFIED, NOT_SPECIFIED);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model which represents source url
	 * @param width 幅
	 * @param height 高さ
	 */
	public StaticImage(String id, IModel<String> model, int width, int height) {
		super(id, model);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id component id
	 * @param url source url
	 */
	public StaticImage(String id, String url) {
		this(id, Model.of(url), NOT_SPECIFIED, NOT_SPECIFIED);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id component id
	 * @param url source url
	 * @param width 幅
	 * @param height 高さ
	 */
	public StaticImage(String id, String url, int width, int height) {
		this(id, Model.of(url), width, height);
	}
	
	/**
	 * 画像の高さを返す。
	 * 
	 * @return 高さ
	 * @since 1.0
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 画像の幅を返す。
	 * 
	 * @return 幅
	 * @since 1.0
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 画像の高さを設定する。
	 * 
	 * @param height 高さ
	 * @since 1.0
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * 画像のURLを設定する。
	 * 
	 * @param url URL
	 * @since 1.0
	 */
	public void setUrl(String url) {
		setDefaultModelObject(url);
	}
	
	/**
	 * 画像の幅を設定する。
	 * 
	 * @param width 幅
	 * @since 1.0
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		checkComponentTag(tag, "img");
		super.onComponentTag(tag);
		tag.put("src", getDefaultModelObjectAsString());
		
		if (width >= 0) {
			tag.put("width", width);
		}
		if (height >= 0) {
			tag.put("height", height);
		}
	}
}
