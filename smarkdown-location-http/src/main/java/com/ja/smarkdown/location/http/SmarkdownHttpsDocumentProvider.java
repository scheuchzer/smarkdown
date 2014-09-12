package com.ja.smarkdown.location.http;

public class SmarkdownHttpsDocumentProvider extends
		SmarkdownHttpDocumentProvider {

	public SmarkdownHttpsDocumentProvider() {
		super("smarkdown:https://", "https://");
	}

	@Override
	protected String getRootPath(HttpLocation location) {
		return String.format("%s/raw", super.getRootPath(location));
	}

}
