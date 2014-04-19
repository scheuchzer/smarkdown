package com.ja.smarkdown.load.webinf;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * A {@link URLStreamHandler} that handles resources in the WEB-INF directory.
 */
public class WebInfURLStreamHandler extends URLStreamHandler {

	@Inject
	private ServletContext servletContext;

	@Override
	protected URLConnection openConnection(final URL u) throws IOException {
		final String path = String.format("/WEB-INF/%s", u.getPath());
		final URL resourceUrl = servletContext.getResource(path);
		return resourceUrl.openConnection();
	}
}
