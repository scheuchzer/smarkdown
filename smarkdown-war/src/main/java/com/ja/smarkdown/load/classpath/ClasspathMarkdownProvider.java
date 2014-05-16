package com.ja.smarkdown.load.classpath;

import java.net.URLStreamHandler;

import com.ja.smarkdown.load.AbstractDocumentProvider;

public class ClasspathMarkdownProvider extends AbstractDocumentProvider {

	private ClasspathURLStreamHandler handler = new ClasspathURLStreamHandler();

	@Override
	protected URLStreamHandler getHandler() {
		return handler;
	}

}
