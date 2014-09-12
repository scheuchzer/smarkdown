package com.ja.smarkdown.location.http;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class SmarkdownHttpsLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public SmarkdownHttpsLocationHandlerConfigurator() {
		super("smarkdown:http://");
	}

	@Inject
	private SmarkdownHttpLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
