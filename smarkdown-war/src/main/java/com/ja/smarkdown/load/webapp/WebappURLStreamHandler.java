package com.ja.smarkdown.load.webapp;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * A {@link URLStreamHandler} that handles resources in a webapp.
 */
public class WebappURLStreamHandler extends URLStreamHandler {

	@Inject
	private ServletContext servletContext;

	@Override
	protected URLConnection openConnection(final URL u) throws IOException {
		final String path = String.format("/%s", u.getPath());
		final URL resourceUrl = servletContext.getResource(path);
		return resourceUrl.openConnection();
	}
}
