/*
 * Created on 2011/09/29
 */
package jp.xet.uncommons.wicket.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link AbstractSpringDataProvider}のテストクラス。
 * 
 * @since 1.0.0
 * @version $Id: AbstractSpringDataProviderTest.java 752 2011-11-02 12:59:02Z miyamoto $
 * @author daisuke
 */
public class AbstractSpringDataProviderTest {
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_getPage() {
		assertThat(AbstractSpringDataProvider.getPage(0, 1), is(0));
		assertThat(AbstractSpringDataProvider.getPage(0, 10), is(0));
		assertThat(AbstractSpringDataProvider.getPage(0, 100), is(0));
		assertThat(AbstractSpringDataProvider.getPage(0, 3), is(0));
		assertThat(AbstractSpringDataProvider.getPage(3, 3), is(1));
		assertThat(AbstractSpringDataProvider.getPage(6, 3), is(2));
		assertThat(AbstractSpringDataProvider.getPage(30, 3), is(10));
	}
}
