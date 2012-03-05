/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/03/05
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

import java.io.Serializable;

import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A stateless link to any ResourceReference.
 * 
 * @param <T> type of model object
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class StatelessResourceLink<T extends Serializable> extends ResourceLink<T> {
	
	/**
	 * Constructs a link directly to the provided resource.
	 * 
	 * @param id See Component
	 * @param resource The resource
	 */
	public StatelessResourceLink(String id, IResource resource) {
		super(id, resource);
	}
	
	/**
	 * Constructs an StatelessResourceLink from an resourcereference. That resource reference will bind its
	 * resource to the current SharedResources.
	 * 
	 * @param id See Component
	 * @param resourceReference The shared resource to link to
	 */
	public StatelessResourceLink(String id, ResourceReference resourceReference) {
		super(id, resourceReference);
	}
	
	/**
	 * Constructs an StatelessResourceLink from an resourcereference. That resource reference will bind its
	 * resource to the current SharedResources.
	 * 
	 * @param id See Component
	 * @param resourceReference The shared resource to link to
	 * @param resourceParameters The resource parameters
	 */
	public StatelessResourceLink(String id, ResourceReference resourceReference, PageParameters resourceParameters) {
		super(id, resourceReference, resourceParameters);
	}
	
	@Override
	protected boolean getStatelessHint() {
		return true;
	}
}
