/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/25
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;

/**
 * TODO for daisuke
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class SizedImage extends Image {
	
	private final int width;
	
	private final int height;
	
	private final String alt;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param imageResource The image resource
	 * @param width 幅
	 * @param height 高さ
	 * @throws IllegalArgumentException 引数{@code width}または{@code height}に負数を与えた場合
	 */
	public SizedImage(String id, IResource imageResource, int width, int height) {
		this(id, imageResource, width, height, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param imageResource The image resource
	 * @param width 幅
	 * @param height 高さ
	 * @param alt alt属性文字列
	 */
	public SizedImage(String id, IResource imageResource, int width, int height, String alt) {
		super(id, imageResource);
		Validate.isTrue(width >= 0);
		Validate.isTrue(height >= 0);
		this.width = width;
		this.height = height;
		this.alt = alt;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param resourceReference The image resource reference
	 * @param width 幅
	 * @param height 高さ
	 */
	public SizedImage(String id, ResourceReference resourceReference, int width, int height) {
		this(id, resourceReference, width, height, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param resourceReference The image resource reference
	 * @param width 幅
	 * @param height 高さ
	 * @param alt alt属性文字列
	 */
	public SizedImage(String id, ResourceReference resourceReference, int width, int height, String alt) {
		super(id, resourceReference);
		Validate.isTrue(width >= 0);
		Validate.isTrue(height >= 0);
		this.width = width;
		this.height = height;
		this.alt = alt;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param resourceReference The image resource reference
	 * @param resourceParameters The resource parameters
	 * @param width 幅
	 * @param height 高さ
	 */
	public SizedImage(String id, ResourceReference resourceReference, PageParameters resourceParameters, int width,
			int height) {
		this(id, resourceReference, resourceParameters, width, height, null);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param resourceReference The image resource reference
	 * @param resourceParameters The resource parameters
	 * @param width 幅
	 * @param height 高さ
	 * @param alt alt属性文字列
	 */
	public SizedImage(String id, ResourceReference resourceReference, PageParameters resourceParameters, int width,
			int height, String alt) {
		super(id, resourceReference, resourceParameters);
		Validate.isTrue(width >= 0);
		Validate.isTrue(height >= 0);
		this.width = width;
		this.height = height;
		this.alt = alt;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		
		tag.put("width", width);
		tag.put("height", height);
		if (StringUtils.isEmpty(alt) == false) {
			CharSequence escapedAlt = Strings.escapeMarkup(alt);
			tag.put("alt", escapedAlt);
			tag.put("title", escapedAlt);
		}
	}
}
