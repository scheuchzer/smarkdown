package com.ja.smarkdown.load.webinf;

import java.net.URLStreamHandler;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractMarkdownProvider;

public class WebInfMarkdownProvider extends AbstractMarkdownProvider {

	@Inject
	private WebInfURLStreamHandler handler;

	@Override
	protected URLStreamHandler getHandler() {
		return handler;
	}

}
