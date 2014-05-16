package com.ja.smarkdown.load.webapp;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import com.ja.smarkdown.load.AbstractUrlDocumentProvider;
import com.ja.smarkdown.model.config.Location;

public class WebappDocumentProvider extends
		AbstractUrlDocumentProvider<Location> {

	public WebappDocumentProvider() {
		super("webapp:", "/");
	}

	@Inject
	private ServletContext servletContext;

	@Override
	protected URL getUrl(final Location location, final String path)
			throws MalformedURLException {
		return servletContext.getResource(path);
	}
}
