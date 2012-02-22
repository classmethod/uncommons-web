/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/10
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

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * アタッチ・デタッチ時にログ出力をする {@link LoadableDetachableModel} の抽象クラス。
 * 
 * @param <T> The Model Object type
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public abstract class LoggingLoadableDetachableModel<T> extends LoadableDetachableModel<T> {
	
	private static Logger logger = LoggerFactory.getLogger(LoggingLoadableDetachableModel.class);
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		if (logger.isDebugEnabled()) {
			logger.debug(getClass().getSimpleName() + " attached");
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if (logger.isDebugEnabled()) {
			logger.debug(getClass().getSimpleName() + " detached");
		}
	}
}
