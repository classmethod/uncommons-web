/*
 * Copyright 2011-2012 Cloudstudy, Inc..
 * Created on 2012/03/25
 * 
 * All rights reserved.
 */
package jp.xet.uncommons.wicket.fixedurl;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.HomePageMapper;
import org.apache.wicket.request.mapper.ICompoundRequestMapper;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.util.ClassProvider;

import jp.xet.uncommons.wicket.fixedurl.FixedUrlMountedMapper;

/**
 * TODO for daisuke
 * 
 * @since 1.0.0
 * @version $Id$
 * @author daisuke
 */
public class FixedUrlHomePageMapper extends FixedUrlMountedMapper {
	
	public static void replaceHomePageMapper(final Application application) {
		ICompoundRequestMapper mappers = application.getRootRequestMapperAsCompound();
		IRequestMapper homePageMapper = null;
		for (IRequestMapper mapper : mappers) {
			if (mapper instanceof HomePageMapper) {
				homePageMapper = mapper;
				break;
			}
		}
		if (homePageMapper != null) {
			mappers.remove(homePageMapper);
		}
		mappers.add(new FixedUrlHomePageMapper(new ClassProvider<Page>(null) {
			
			@Override
			public Class<Page> get() {
				@SuppressWarnings("unchecked")
				Class<Page> homePage = (Class<Page>) application.getHomePage();
				return homePage;
			}
		}));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageClass
	 * @param pageParametersEncoder
	 */
	public FixedUrlHomePageMapper(Class<? extends IRequestablePage> pageClass,
			IPageParametersEncoder pageParametersEncoder) {
		super("/", pageClass, pageParametersEncoder);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageClass
	 */
	public FixedUrlHomePageMapper(Class<? extends IRequestablePage> pageClass) {
		super("/", pageClass);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageClassProvider
	 * @param pageParametersEncoder
	 */
	public FixedUrlHomePageMapper(ClassProvider<? extends IRequestablePage> pageClassProvider,
			IPageParametersEncoder pageParametersEncoder) {
		super("/", pageClassProvider, pageParametersEncoder);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pageClassProvider
	 */
	public FixedUrlHomePageMapper(ClassProvider<? extends IRequestablePage> pageClassProvider) {
		super("/", pageClassProvider);
	}
	
	/**
	 * Matches only when there are no segments/indexed parameters
	 * 
	 * @see org.apache.wicket.request.mapper.AbstractBookmarkableMapper#parseRequest(org.apache.wicket.request.Request)
	 */
	@Override
	protected UrlInfo parseRequest(Request request) {
		// get canonical url
		final Url url = request.getUrl().canonical();
		
		if (url.getSegments().size() > 0) {
			// home page cannot have segments/indexed parameters
			return null;
		}
		
		return super.parseRequest(request);
	}
	
	/**
	 * Use this mapper as a last option. Let all other mappers to try to handle the request
	 * 
	 * @see org.apache.wicket.request.mapper.MountedMapper#getCompatibilityScore(org.apache.wicket.request.Request)
	 */
	@Override
	public int getCompatibilityScore(Request request) {
		return Integer.MIN_VALUE + 1;
	}
}
