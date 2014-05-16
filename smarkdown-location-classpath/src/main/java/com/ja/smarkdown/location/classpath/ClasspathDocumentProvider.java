package com.ja.smarkdown.location.classpath;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.ja.smarkdown.load.AbstractDocumentProvider;
import com.ja.smarkdown.model.config.Location;

public class ClasspathDocumentProvider extends
		AbstractDocumentProvider<Location> {

	public ClasspathDocumentProvider() {
		super("classpath:", "");
	}

	@Override
	protected InputStream getInputStream(final Location location,
			final String path) throws FileNotFoundException {
		final URL resourceUrl = getClass().getClassLoader().getResource(path);
		if (resourceUrl == null) {
			throw new FileNotFoundException(path);
		}
		try {
			return resourceUrl.openStream();
		} catch (final IOException e) {
			throw new FileNotFoundException(path + " " + e);
		}
	}

}
