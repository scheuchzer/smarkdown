package com.ja.smarkdown.load.webapp;

import java.net.URLStreamHandler;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractMarkdownProvider;

public class WebappMarkdownProvider extends AbstractMarkdownProvider {

	@Inject
	private WebappURLStreamHandler handler;

	@Override
	protected URLStreamHandler getHandler() {
		return handler;
	}

}
