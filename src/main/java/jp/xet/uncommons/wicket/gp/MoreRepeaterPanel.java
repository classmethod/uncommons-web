package jp.xet.uncommons.wicket.gp;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 * TODO for daisuke
 * 
 * @param <T> 
 * @since 1.0.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public final class MoreRepeaterPanel<T> extends Panel {
	
	private WebMarkupContainer container;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param dataProvider {@link IDataProvider}
	 * @param itemsParPage
	 * @param pp
	 */
	public MoreRepeaterPanel(String id, IDataProvider<T> dataProvider, int itemsParPage, PopulationProcessor<T> pp) {
		this(id, dataProvider, itemsParPage, pp, 0);
	}
	
	MoreRepeaterPanel(String id, final IDataProvider<T> dataProvider, final int itemsParPage,
			final PopulationProcessor<T> p, final int index) {
		super(id);
		
		DataView<T> view = new DataView<T>("repeatingList", dataProvider, itemsParPage) {
			
			@Override
			protected void populateItem(Item<T> item) {
				p.populateItem("content", item);
			}
		};
		view.setCurrentPage(index);
		add(view);
		
		final WebMarkupContainer next = new WebMarkupContainer("next");
		next.setOutputMarkupId(true);
		next.setOutputMarkupPlaceholderTag(true);
		next.setVisible(false);
		add(next);
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);
		
		AjaxLink<Void> button = new AjaxLink<Void>("repeatingButton") {
			
			private static final long serialVersionUID = 1L;
			
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				Panel panel = new MoreRepeaterPanel<T>(next.getId(), dataProvider, itemsParPage, p, index + 1);
				next.replaceWith(panel);
				container.setVisible(false);
				target.add(container, panel);
			}
		};
		if (index >= view.getPageCount() - 1) {
			setRepeatingButtonVisible(false);
		}
		container.add(button);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param visible
	 * @return
	 * @since TODO
	 */
	public MoreRepeaterPanel<T> setRepeatingButtonVisible(boolean visible) {
		container.setVisible(visible);
		return this;
	}
	
	
	/**
	 * TODO for daisuke
	 * 
	 * @param <T>
	 * @since 1.0
	 * @version $Id$
	 * @author daisuke
	 */
	public interface PopulationProcessor<T> extends Serializable {
		
		/**
		 * TODO for daisuke
		 * 
		 * @param id
		 * @param item
		 * @since 1.0
		 */
		void populateItem(String id, Item<T> item);
		
	}
}
