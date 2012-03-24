/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/24
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
package jp.xet.uncommons.wicket.fixedurl;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.resolver.AutoLinkResolver.IAutolinkResolverDelegate;
import org.apache.wicket.markup.resolver.AutoLinkResolver.PathInfo;

class ForceReloadAutolinkResolverDelegate implements IAutolinkResolverDelegate {
	
	private final IAutolinkResolverDelegate delegate;
	
	
	ForceReloadAutolinkResolverDelegate(IAutolinkResolverDelegate delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public Component newAutoComponent(MarkupContainer container, String autoId, PathInfo pathInfo) {
		PathInfo newPathInfo;
		if (pathInfo.getPageParameters() != null
				&& pathInfo.getPageParameters().getNamedKeys().contains(FixedUrlMountedMapper.KEY_RELOAD)) {
			newPathInfo = pathInfo;
		} else {
			String reference = pathInfo.getReference();
			StringBuilder newReference = new StringBuilder(reference);
			int questionIndex = reference.indexOf('?');
			if (questionIndex == StringUtils.INDEX_NOT_FOUND) {
				int hashIndex = reference.indexOf('#');
				if (hashIndex == StringUtils.INDEX_NOT_FOUND) {
					newReference.append("?").append(FixedUrlMountedMapper.KEY_RELOAD);
				} else {
					newReference.insert(hashIndex, "?" + FixedUrlMountedMapper.KEY_RELOAD);
				}
			} else {
				newReference.insert(questionIndex + 1, FixedUrlMountedMapper.KEY_RELOAD + "&");
			}
			
			newPathInfo = new PathInfo(newReference.toString());
		}
		return delegate.newAutoComponent(container, autoId, newPathInfo);
	}
}
