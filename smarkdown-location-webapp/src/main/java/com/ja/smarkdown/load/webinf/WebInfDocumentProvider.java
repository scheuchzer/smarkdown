package com.ja.smarkdown.load.webinf;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import com.ja.smarkdown.load.AbstractUrlDocumentProvider;
import com.ja.smarkdown.model.config.Location;

public class WebInfDocumentProvider extends
		AbstractUrlDocumentProvider<Location> {

	public WebInfDocumentProvider() {
		super("web-inf:", "/WEB-INF/");
	}

	@Inject
	private ServletContext servletContext;

	@Override
	protected URL getUrl(final Location location, final String path)
			throws MalformedURLException {
		return servletContext.getResource(path);
	}

}
