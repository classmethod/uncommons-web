/*
 * Copyright 2011 Daisuke Miyamoto.
 * Crimport java.util.Locale;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.PropertyResolver;
software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.xet.uncommons.wicket.converter;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.PropertyResolver;

/**
 * TODO for daisuke
 * 
 * @param <T>
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PropertyConverter<T> extends AbstractConverter<T> {
	
	private final Class<T> clazz;
	
	private final String expression;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param clazz
	 * @param expression
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public PropertyConverter(Class<T> clazz, String expression) {
		Validate.notNull(clazz);
		Validate.notNull(expression);
		this.clazz = clazz;
		this.expression = expression;
	}
	
	@Override
	public T convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String convertToString(Object value, Locale locale) {
		if (value == null) {
			return null;
		}
		
		Object convertedValue = PropertyResolver.getValue(expression, value);
		IConverterLocator converterLocator = Application.get().getConverterLocator();
		
		@SuppressWarnings("unchecked")
		IConverter<Object> converter = (IConverter<Object>) converterLocator.getConverter(convertedValue.getClass());
		
		return converter.convertToString(convertedValue, locale);
	}
	
	@Override
	protected Class<T> getTargetType() {
		return clazz;
	}
}
