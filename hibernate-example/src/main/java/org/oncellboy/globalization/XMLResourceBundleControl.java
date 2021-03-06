package org.oncellboy.globalization;

import java.util.ResourceBundle.Control;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * 출처 : https://gist.github.com/asicfr/1b76ea60029264d7be15d019a866e1a4#file-xmlresourcebundlecontrol-java
 * */

public class XMLResourceBundleControl extends Control {

	private static final String XML = "xml";
	private static final List<String> SINGLETON_LIST = Collections.singletonList(XML);

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {

		if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
			throw new IllegalArgumentException("baseName, locale, format and loader cannot be null");
		}
		if (!format.equals(XML)) {
			throw new IllegalArgumentException("format must be xml");
		}

		final String bundleName = toBundleName(baseName, locale);
		final String resourceName = toResourceName(bundleName, format);
		final URL url = loader.getResource(resourceName);
		if (url == null) {
			return null;
		}
		
		final URLConnection urlconnection = url.openConnection();
		if (urlconnection == null) {
			return null;
		}
		
		if (reload) {
			urlconnection.setUseCaches(false);
		}
		
		try ( 	final InputStream stream = urlconnection.getInputStream();
				final BufferedInputStream bis = new BufferedInputStream(stream);
				) {
			final ResourceBundle bundle = new XMLResourceBundle(bis);
			return bundle;
		}
	}

	@Override
	public List<String> getFormats(String baseName) {
		return SINGLETON_LIST;
	}

}
