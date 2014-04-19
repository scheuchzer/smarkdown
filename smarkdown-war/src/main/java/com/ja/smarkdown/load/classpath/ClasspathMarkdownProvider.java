package com.ja.smarkdown.load.classpath;

import java.net.URLStreamHandler;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractMarkdownProvider;

public class ClasspathMarkdownProvider extends AbstractMarkdownProvider {

	@Inject
	private ClasspathURLStreamHandler handler;

	@Override
	protected URLStreamHandler getHandler() {
		return handler;
	}

}
