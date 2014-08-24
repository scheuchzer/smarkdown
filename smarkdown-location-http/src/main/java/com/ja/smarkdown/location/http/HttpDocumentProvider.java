package com.ja.smarkdown.location.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.load.AbstractDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;

@Slf4j
public class HttpDocumentProvider extends
		AbstractDocumentProvider<HttpLocation> {

	public HttpDocumentProvider() {
		super("http://", "");
	}

	@Override
	protected ResourceInfo getResource(final HttpLocation location,
			final String path) throws FileNotFoundException {
		final String url = String.format("http://%s", path);
		log.debug("Resolved url={}", url);
		try {
			final URL resourceUrl = new URL(url);
			return new ResourceInfo(path, location, resourceUrl.openStream());
		} catch (final IOException e) {
			throw new FileNotFoundException(path + " " + e);
		}
	}
}
