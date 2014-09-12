package com.ja.smarkdown.location.http;

public class SmarkdownHttpDocumentProvider extends AbstractHttpDocumentProvider {

	public SmarkdownHttpDocumentProvider() {
		this("smarkdown:http://", "http://");
	}

	protected SmarkdownHttpDocumentProvider(final String protocol,
			final String realProtocol) {
		super(protocol, realProtocol);
	}

	@Override
	protected String getRootPath(HttpLocation location) {
		return String.format("%s/raw", super.getRootPath(location));
	}

}
