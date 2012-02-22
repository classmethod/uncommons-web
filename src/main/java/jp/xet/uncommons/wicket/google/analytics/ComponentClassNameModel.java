/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/10/18
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
package jp.xet.uncommons.wicket.google.analytics;

import jp.xet.uncommons.wicket.utils.WicketUtil;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * An implementation of IModel<String> that returns the class name of a component as its object. It will look up that
 * class name each time it is asked for it, and it can be different each time. It will query a MarkupContainer to find a
 * child component with a certain component id, and return the class name of that component. Any wicket component that
 * can hold other components is a MarkupContainer, including {@link Panel} and {@link Page}.
 * <p/>
 * By using models to resolve both the markup container and the component id, there is a lot of flexibility and the
 * decision to ask which container for which comnponent id can be dynamically made just before the value of this model
 * is needed.
 * 
 * @since 1.0
 * @version $Id$
 * @author Erwin Bolwidt (ebolwidt@worldturner.nl)
 * @author daisuke
 */
@SuppressWarnings("serial")
public class ComponentClassNameModel extends AbstractReadOnlyModel<String> {
	
	/**
	 * A model that, when queried, will return the markup container that will be asked for a child component.
	 */
	private IModel<MarkupContainer> containerModel;
	
	/**
	 * A model that, when queried, will return the component id of the child within the markup container, that we are
	 * interested in.
	 */
	private IModel<String> componentIdModel;
	
	
	/**
	 * Constructor.
	 * 
	 * @param containerModel A model that, when queried, will return the markup container that will be asked
	 * 		for a child component.
	 * @param componentIdModel A model that, when queried, will return the component id of the child
	 * 		within the markup container, that we are interested in.
	 */
	public ComponentClassNameModel(IModel<MarkupContainer> containerModel, IModel<String> componentIdModel) {
		this.containerModel = containerModel;
		this.componentIdModel = componentIdModel;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param container The markup container that will be asked for a child component.
	 * @param componentId The component id of the child within the markup container, that we are interested in.
	 */
	public ComponentClassNameModel(MarkupContainer container, String componentId) {
		this(new Model<MarkupContainer>(container), new Model<String>(componentId));
	}
	
	@Override
	public void detach() {
		super.detach();
		
		WicketUtil.detachIfNotNull(containerModel);
		WicketUtil.detachIfNotNull(componentIdModel);
	}
	
	@Override
	public String getObject() {
		MarkupContainer container = containerModel.getObject();
		String componentId = componentIdModel.getObject();
		if (container == null || componentId == null) {
			return null;
		}
		Component component = container.get(componentId);
		if (component == null) {
			return null;
		} else {
			return component.getClass().getName();
		}
	}
}
