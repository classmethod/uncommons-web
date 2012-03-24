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

import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;

/**
 * TODO for daisuke
 * 
 * @since TODO for daisuke
 * @version $Id$
 * @author daisuke
 */
public enum RemoteEnvironmentProfile implements EnvironmentProfile {
	
	/** プロダクションデプロイ環境 */
	PRODUCTION,
	
	/** ステージングデプロイ環境 */
	STAGING,
	
	/** デベロップメントデプロイ環境 */
	DEVELOPMENT;
	
	public static EnvironmentProfile toEnvironmentProfile(String[] activeProfiles) {
		for (RemoteEnvironmentProfile p : RemoteEnvironmentProfile.values()) {
			if (ArrayUtils.contains(activeProfiles, p.toString())) {
				return p;
			}
		}
		if (ArrayUtils.isEmpty(activeProfiles)) {
			return DEVELOPMENT;
		} else {
			return new LocalEnvironmentProfile(activeProfiles[0]);
		}
	}
	
	@Override
	public String toString() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
