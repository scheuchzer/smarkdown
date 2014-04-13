package com.ja.smarkdown.load.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * A {@link URLStreamHandler} that handles resources on the classpath.
 */
public class ClasspathHandler extends URLStreamHandler {
	private final ClassLoader classLoader;

	public ClasspathHandler() {
		this.classLoader = getClass().getClassLoader();
	}

	public ClasspathHandler(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	protected URLConnection openConnection(final URL u) throws IOException {
		final URL resourceUrl = classLoader.getResource(u.getPath());
		return resourceUrl.openConnection();
	}
}
