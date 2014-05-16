package com.ja.smarkdown.location.webapp;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class WebappLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public WebappLocationHandlerConfigurator() {
		super("webapp:");
	}

	@Inject
	private WebappLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
