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

import java.io.Serializable;

import jp.xet.baseunits.time.TimePoint;

import org.apache.wicket.util.lang.Args;

/**
 * レンダリングしたフォームを特定するためのキーオブジェクト。
 * 
 * @since 1.3
 * @version $Id$
 * @author Tsutomu YANO
 */
@SuppressWarnings("serial")
public class FormKey implements Serializable {
	
	private final int pageId;
	
	private final String formId;
	
	private final long timestamp;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageId フォームが属するページのID番号
	 * @param formId フォームコンポーネントのID文字列
	 * @param timestamp フォームレンダリングのタイムスタンプ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.3
	 */
	public FormKey(int pageId, String formId, long timestamp) {
		Args.notNull(formId, "formId");
		this.pageId = pageId;
		this.formId = formId;
		this.timestamp = timestamp;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageId フォームが属するページのID番号
	 * @param formId フォームコンポーネントのID文字列
	 * @param timestamp フォームレンダリングのタイムスタンプ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.3
	 */
	public FormKey(int pageId, String formId, TimePoint timestamp) {
		Args.notNull(formId, "formId");
		Args.notNull(timestamp, "timestamp");
		this.pageId = pageId;
		this.formId = formId;
		this.timestamp = timestamp.toEpochMillisec();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FormKey)) {
			return false;
		}
		FormKey other = (FormKey) obj;
		if (formId == null) {
			if (other.formId != null) {
				return false;
			}
		} else if (!formId.equals(other.formId)) {
			return false;
		}
		if (pageId != other.pageId) {
			return false;
		}
		if (timestamp != other.timestamp) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formId == null) ? 0 : formId.hashCode());
		result = prime * result + pageId;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32)); // CHECKSTYLE IGNORE THIS LINE
		return result;
	}
	
	@Override
	public String toString() {
		return "FormKey [pageId=" + pageId + ", formId=" + formId + ", timestamp=" + timestamp + "]";
	}
}
