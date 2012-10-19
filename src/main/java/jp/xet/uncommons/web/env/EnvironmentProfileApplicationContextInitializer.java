/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/14
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
package jp.xet.uncommons.web.env;

import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ObjectUtils;

/**
 * TODO for daisuke
 * 
 * @since 1.1
 * @version $Id$
 * @author daisuke
 */
public class EnvironmentProfileApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {
	
	private static Logger logger = LoggerFactory.getLogger(EnvironmentProfileApplicationContextInitializer.class);
	
	private static final EnvironmentProfile DEFAULT_PROFILE = RemoteEnvironmentProfile.DEVELOPMENT;
	
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		
		String[] activeProfiles = environment.getActiveProfiles();
		if (ObjectUtils.isEmpty(activeProfiles)) {
			String propEnvironment = environment.getProperty("environment");
			if (Strings.isNullOrEmpty(propEnvironment)) {
				environment.setActiveProfiles("development");
				logger.warn("Spring active profiles are not specified.  Set default value [{}]", DEFAULT_PROFILE);
			} else {
				environment.setActiveProfiles(propEnvironment);
				logger.info("Set Spring active profiles to [{}]", propEnvironment);
			}
		} else {
			logger.info("Spring active profiles [{}]", activeProfiles);
		}
	}
}
