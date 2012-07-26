/*
 * Copyright 2012 Tsutomu YANO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.uncommons.wicket.fixedurl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.resolver.AutoLinkResolver;
import org.apache.wicket.markup.resolver.AutoLinkResolver.IAutolinkResolverDelegate;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.mapper.MountedMapper;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.settings.IPageSettings;
import org.apache.wicket.util.ClassProvider;

/**
 * {@link IRequestMapper} implementation for Fixed URL strategy.
 * 
 * @author Tsutomu YANO
 * @since 1.1
 */
public class FixedUrlMountedMapper extends MountedMapper {
	
	static final String KEY_RELOAD = "reload";
	
	
	/**
	 * {@link FixedUrlMountedMapper}用に、aタグでの自動リンクに ?reload を自動付与する処理を追加する。
	 * 
	 * @param application {@link Application}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.6
	 */
	public static void initialize(Application application) {
		Validate.notNull(application);
		List<IComponentResolver> resolvers = application.getPageSettings().getComponentResolvers();
		for (IComponentResolver resolver : resolvers) {
			if (resolver instanceof AutoLinkResolver) {
				AutoLinkResolver autoLinkResolver = (AutoLinkResolver) resolver;
				IAutolinkResolverDelegate delegateForAElement = autoLinkResolver.getAutolinkResolverDelegate("a");
				IAutolinkResolverDelegate newDelegate = new ForceReloadAutolinkResolverDelegate(delegateForAElement);
				autoLinkResolver.addTagReferenceResolver("a", "href", newDelegate);
			}
		}
	}
	
	/**
	 * {@link FixedUrlMountedMapper}用に、aタグでの自動リンクに ?reload を自動付与する処理を追加する。
	 * 
	 * @param pageSettings {@link IPageSettings}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.2
	 * @deprecated use {@link #initialize(Application)}
	 */
	@Deprecated
	public static void initialize(IPageSettings pageSettings) {
		Validate.notNull(pageSettings);
		List<IComponentResolver> resolvers = pageSettings.getComponentResolvers();
		for (IComponentResolver resolver : resolvers) {
			if (resolver instanceof AutoLinkResolver) {
				AutoLinkResolver autoLinkResolver = (AutoLinkResolver) resolver;
				IAutolinkResolverDelegate delegateForAElement = autoLinkResolver.getAutolinkResolverDelegate("a");
				IAutolinkResolverDelegate newDelegate = new ForceReloadAutolinkResolverDelegate(delegateForAElement);
				autoLinkResolver.addTagReferenceResolver("a", "href", newDelegate);
			}
		}
	}
	
	/**
	 * Create new {@link PageParameters} with reload parameter.
	 * 
	 * @return new parameter
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public static PageParameters newReloadParameter() {
		return updateReloadParameter(new PageParameters());
	}
	
	/**
	 * Add reload parameter to given {@link PageParameters}.
	 * 
	 * @param params parameters to be added
	 * @return {@code params}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public static PageParameters updateReloadParameter(PageParameters params) {
		Validate.notNull(params);
		if (params.getNamedKeys().contains(KEY_RELOAD) == false) {
			params.add(KEY_RELOAD, "");
		}
		return params;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mountPath mount path
	 * @param pageClass
	 * @since 1.1
	 */
	public FixedUrlMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass) {
		super(mountPath, pageClass);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mountPath mount path
	 * @param pageClass
	 * @param pageParametersEncoder
	 * @since 1.1
	 */
	public FixedUrlMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass,
			IPageParametersEncoder pageParametersEncoder) {
		super(mountPath, pageClass, pageParametersEncoder);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mountPath mount path
	 * @param pageClassProvider
	 * @since 1.1
	 */
	public FixedUrlMountedMapper(String mountPath, ClassProvider<? extends IRequestablePage> pageClassProvider) {
		super(mountPath, pageClassProvider);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mountPath mount path
	 * @param pageClassProvider
	 * @param pageParametersEncoder
	 * @since 1.1
	 */
	public FixedUrlMountedMapper(String mountPath, ClassProvider<? extends IRequestablePage> pageClassProvider,
			IPageParametersEncoder pageParametersEncoder) {
		super(mountPath, pageClassProvider, pageParametersEncoder);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>スーパークラスの実装は、ページインスタンスがステートレスの場合のみ、URLにページIDを付与しない。</p>
	 * 
	 * <p>このサブクラスは、上記に加え、ページインスタンスのページIDがページIDストアに保存されているページIDと
	 * 一致する時も、URLにページIDを埋め込まない。</p>
	 */
	@Override
	public Url mapHandler(IRequestHandler requestHandler) {
		// requestHandlerがRenderPageRequestHandlerの時だけ特別な処理を実施する。
		// それ以外はスーパークラスに任せる
		if (requestHandler instanceof RenderPageRequestHandler) {
			// possibly hybrid URL - bookmarkable URL with page instance information
			// but only allowed if the page was created by bookmarkable URL
			
			RenderPageRequestHandler handler = (RenderPageRequestHandler) requestHandler;
			
			if (checkPageClass(handler.getPageClass()) == false) {
				return null;
			}
			
			if (handler.getPageProvider().isNewPageInstance()) {
				// no existing page instance available, don't bother creating new page instance
				PageInfo info = new PageInfo();
				UrlInfo urlInfo =
						new UrlInfo(new PageComponentInfo(info, null), handler.getPageClass(),
								handler.getPageParameters());
				
				return buildUrl(urlInfo);
			}
			
			IRequestablePage page = handler.getPage();
			
			if (checkPageInstance(page) && (!pageMustHaveBeenCreatedBookmarkable() || page.wasCreatedBookmarkable())) {
				PageInfo info = null;
				
				// ページがステートレスのときに加え、リクエストハンドラの持つページIDがセッションに保存されている初期ページIDと
				// 一致する場合もURLにページIDを付与しない
				Class<? extends Page> pageClass = handler.getPageClass().asSubclass(Page.class);
				if (!page.isPageStateless() && !isStoredPage(pageClass, page.getPageParameters(), page.getPageId())) {
					info = new PageInfo(page.getPageId());
				}
				PageComponentInfo pageComponentInfo = info != null ? new PageComponentInfo(info, null) : null;
				
				UrlInfo urlInfo = new UrlInfo(pageComponentInfo, page.getClass(), handler.getPageParameters());
				return buildUrl(urlInfo);
			} else {
				return null;
			}
		} else {
			return super.mapHandler(requestHandler);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>requestにpageIdが埋め込まれていない場合でも、表示しようとしているページインスタンスの生成時ページIDがセッションに
	 * 保存されている場合は、保存されているページIDを使用します。</p>
	 */
	@Override
	@SuppressWarnings("null")
	public IRequestHandler mapRequest(Request request) {
		UrlInfo urlInfo = parseRequest(request);
		
		// check if the URL is long enough and starts with the proper segments
		if (urlInfo != null) {
			PageComponentInfo info = urlInfo.getPageComponentInfo();
			Class<? extends Page> pageClass = urlInfo.getPageClass().asSubclass(Page.class);
			PageParameters pageParameters = urlInfo.getPageParameters();
			
			Integer storedPageId = getStoredPageId(pageClass, pageParameters);
			boolean reload = pageParameters != null && pageParameters.getNamedKeys().contains(KEY_RELOAD);
			
			// リクエストにページIDが埋め込まれていない場合はブックマークページとして扱う。
			// さらに、ページIDストアにページIDが保存されていないことも確認し、
			// ページIDが保存済みの場合も、ブックマークページとしては扱わない。
			// processBookmarkableメソッドもオーバーライドされており、独自のPageProviderを使用している。
			// 独自のPageProviderは、ページインスタンス生成時に、生成したページのページIDをページIDストアに保存する。
			// reloadパラメータが指定されている場合は、無条件に新規ページ作成を行う。
			if (reload || ((info == null || info.getPageInfo().getPageId() == null) && storedPageId == null)) {
				// if there are is no page instance information (only page map name - optionally)
				// then this is a simple bookmarkable URL
				
				if (reload) {
					pageParameters.remove(KEY_RELOAD);
				}
				
				return processBookmarkable(pageClass, pageParameters);
			} else if (((info != null && info.getPageInfo() != null && info.getPageInfo().getPageId() != null) || storedPageId != null)
					&& (info == null || info.getComponentInfo() == null)) {
				// if there is page instance information in the URL but no component and listener
				// interface then this is a hybrid URL - we need to try to reuse existing page
				// instance
				
				// URLにページIDが埋め込まれている場合はそちらを優先して使用する。
				// URLにページIDがなく、storedPageID != null の場合、storedPageIdをページIDとして使用する。
				PageInfo pageInfo = info == null ? null : info.getPageInfo();
				if ((pageInfo == null || pageInfo.getPageId() == null) && storedPageId != null) {
					pageInfo = new PageInfo(storedPageId);
				}
				
				return processHybrid(pageInfo, pageClass, pageParameters, null);
			} else if (info != null && info.getComponentInfo() != null) {
				// with both page instance and component+listener this is a listener interface URL
				return processListener(info, pageClass, pageParameters);
			}
		}
		return null;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param pageClass
	 * @param params
	 * @return
	 * @since 1.1
	 */
	protected final Integer getStoredPageId(Class<? extends Page> pageClass, PageParameters params) {
		Session session = Session.get();
		if (session != null && session instanceof IInitialPageIdStore) {
			IInitialPageIdStore store = (IInitialPageIdStore) session;
			return store.getInitialId(pageClass, params);
		}
		return null;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param pageClass
	 * @param params
	 * @param pageId page ID
	 * @return
	 * @since 1.1
	 */
	protected final boolean isStoredPage(Class<? extends Page> pageClass, PageParameters params, Integer pageId) {
		Session session = Session.get();
		if (session != null && session instanceof IInitialPageIdStore) {
			IInitialPageIdStore store = (IInitialPageIdStore) session;
			Integer storedPageId = store.getInitialId(pageClass, params);
			return storedPageId != null && storedPageId.equals(pageId);
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>この実装では、PageProviderクラスの代わりに、{@link InitialPageStorePageProvider}クラスを使用する。
	 * {@link InitialPageStorePageProvider} は、新しいページインスタンスを生成したときに、ページIDストアに
	 * 生成時のページIDを記録する。</p>
	 * 
	 * @since 1.1
	 */
	@Override
	protected IRequestHandler processBookmarkable(Class<? extends IRequestablePage> pageClass,
			PageParameters pageParameters) {
		
		// ページIDストアの初期化
		// ページIDストアからこのクラスのエントリを消しておく
		removeStoredPageId(pageClass.asSubclass(Page.class), pageParameters);
		
		// ページ生成時にページIDストアを更新するPageProviderを使用する。
		PageProvider provider = new InitialPageStorePageProvider(pageClass, pageParameters);
		provider.setPageSource(getContext());
		return new RenderPageRequestHandler(provider);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param pageClass
	 * @param params
	 * @since 1.1
	 */
	protected final void removeStoredPageId(Class<? extends Page> pageClass, PageParameters params) {
		Session session = Session.get();
		if (session != null && session instanceof IInitialPageIdStore) {
			IInitialPageIdStore store = (IInitialPageIdStore) session;
			store.removeInitialId(pageClass, params);
		}
	}
}
