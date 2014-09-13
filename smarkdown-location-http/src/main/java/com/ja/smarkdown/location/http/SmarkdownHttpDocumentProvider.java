package com.ja.smarkdown.location.http;

public class SmarkdownHttpDocumentProvider extends AbstractHttpDocumentProvider {

	public SmarkdownHttpDocumentProvider() {
		super("smarkdown:http://", "http://");
	}

	@Override
	protected String getRootPath(HttpLocation location) {
		return String.format("%s/raw", super.getRootPath(location));
	}

}
