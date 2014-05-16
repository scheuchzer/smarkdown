package com.ja.smarkdown.location.classpath;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class ClasspathLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public ClasspathLocationHandlerConfigurator() {
		super("classpath:");
	}

	@Inject
	private ClasspathLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
