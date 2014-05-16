package com.ja.smarkdown.location.file;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class FileLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public FileLocationHandlerConfigurator() {
		super("file://");
	}

	@Inject
	private FileLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}

}
