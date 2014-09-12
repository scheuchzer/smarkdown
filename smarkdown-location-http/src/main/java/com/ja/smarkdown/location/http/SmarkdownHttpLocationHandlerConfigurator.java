package com.ja.smarkdown.location.http;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class SmarkdownHttpLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public SmarkdownHttpLocationHandlerConfigurator() {
		super("smarkdown:http://");
	}

	@Inject
	private SmarkdownHttpLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
