/*
 * Copyright 2011-2012 Daisuke Miyamoto.
 * Created on 2012/11/08
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
package jp.xet.uncommons.wicket.model;

import java.io.Serializable;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 必要時にロードされるが、デタッチ時にロードされた値が破棄されないモデル。
 * 
 * <p>{@link LoadableDetachableModel}と同様、必要時に一度だけロード（評価）される。
 * 但し、{@link #detach()} によって model object がアンロードされない点が異なる。
 * その結果、{@code modeObject}値が直接シリアライズされ、セッションに保存される。</p>
 * 
 * <p>{@link LoadableDetachableModel}の {@link #detach()} のような、ロード値を破棄する操作は、{@link #unload()}で行える。</p>
 * 
 * @param <T> The Model Object type
 * @since 1.8
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class LoadableModel<T extends Serializable> extends Model<T> {
	
	private static Logger logger = LoggerFactory.getLogger(LoadableModel.class);
	
	/** keeps track of whether this model object is loaded */
	private boolean loaded = false;
	
	/** loaded model object. */
	private T modelObject;
	
	
	@Override
	public synchronized T getObject() {
		if (loaded == false) {
			loaded = true;
			modelObject = load();
			
			if (logger.isDebugEnabled()) {
				logger.debug("loaded model object " + modelObject + " for " + this
						+ ", requestCycle " + RequestCycle.get());
			}
			
			onLoad();
		}
		return modelObject;
	}
	
	/**
	 * Gets the loaded status of this model instance
	 * 
	 * @return true if the model is loaded, false otherwise
	 */
	public final synchronized boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Manually loads the model with the specified object. Subsequent calls to {@link #getObject()}
	 * will return {@code object}.
	 * 
	 * @param object The object to set into the model
	 */
	@Override
	public synchronized void setObject(T object) {
		loaded = true;
		modelObject = object;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(":loaded=").append(loaded);
		if (loaded) {
			sb.append(":modelObject=[").append(modelObject).append("]");
		}
		return sb.toString();
	}
	
	/**
	 * Unloads the model object.
	 * 
	 * @since 1.8
	 */
	public final synchronized void unload() {
		loaded = false;
		modelObject = null;
	}
	
	/**
	 * Loads and returns the model object.
	 * 
	 * @return the model object
	 */
	protected abstract T load();
	
	/**
	 * Called on load model object. Implement this method with custom behavior, such as loading
	 * the model object.
	 */
	protected void onLoad() {
	}
}
