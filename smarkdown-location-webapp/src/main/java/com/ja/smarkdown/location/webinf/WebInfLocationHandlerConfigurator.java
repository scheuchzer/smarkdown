package com.ja.smarkdown.location.webinf;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class WebInfLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public WebInfLocationHandlerConfigurator() {
		super("web-inf:");
	}

	@Inject
	private WebInfLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
