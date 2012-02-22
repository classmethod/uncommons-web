/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/15
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
package jp.xet.uncommons.wicket.utils;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.timeutil.Clock;

import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO for daisuke
 * 
 * @since 1.0.0
 * @version $Id$
 * @author daisuke
 */
public class RequestProcessingTimeListener extends AbstractRequestCycleListener {
	
	private static Logger logger = LoggerFactory.getLogger(RequestProcessingTimeListener.class);
	
	private final ThreadLocal<TimePoint> start = new ThreadLocal<TimePoint>();
	
	
	@Override
	public void onBeginRequest(RequestCycle cycle) {
		logger.trace("begin request");
		start.set(Clock.now());
	}
	
	@Override
	public void onEndRequest(RequestCycle cycle) {
		Duration d = Duration.milliseconds(Clock.now().toEpochMillisec() - start.get().toEpochMillisec());
		logger.info("{} taken for processing request {}", d.toString(), cycle.getRequest().getUrl());
		logger.trace("end request");
	}
}
