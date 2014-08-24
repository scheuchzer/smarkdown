package com.ja.smarkdown.location.http;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class HttpLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public HttpLocationHandlerConfigurator() {
		super("http://");
	}

	@Inject
	private HttpLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
