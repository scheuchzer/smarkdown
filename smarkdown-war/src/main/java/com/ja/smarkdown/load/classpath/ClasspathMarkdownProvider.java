package com.ja.smarkdown.load.classpath;

import java.net.URLStreamHandler;

import com.ja.smarkdown.load.AbstractMarkdownProvider;

public class ClasspathMarkdownProvider extends AbstractMarkdownProvider {

	private ClasspathURLStreamHandler handler = new ClasspathURLStreamHandler();

	@Override
	protected URLStreamHandler getHandler() {
		return handler;
	}

}
