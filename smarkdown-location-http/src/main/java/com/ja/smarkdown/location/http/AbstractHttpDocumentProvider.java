package com.ja.smarkdown.location.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.load.AbstractDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;

@Slf4j
public class AbstractHttpDocumentProvider extends
		AbstractDocumentProvider<HttpLocation> {

	@Getter
	private String realProtocol;

	public AbstractHttpDocumentProvider(final String protocol) {
		this(protocol, protocol);
	}

	public AbstractHttpDocumentProvider(final String protocol,
			final String realProtocol) {
		super(protocol, "");
		this.realProtocol = realProtocol;
	}

	@Override
	protected ResourceInfo getResource(final HttpLocation location,
			final String path) throws FileNotFoundException {
		final String url = resolveUrl(path);
		log.debug("Resolved url={}", url);
		try {
			final URL resourceUrl = new URL(url);
			return new ResourceInfo(path, location, resourceUrl.openStream());
		} catch (final IOException e) {
			throw new FileNotFoundException(path + " " + e);
		}
	}

	protected String resolveUrl(final String path) {
		return String.format("%s%s", getRealProtocol(), path);
	}
}
