package com.ja.smarkdown.load.webinf;

import java.io.InputStream;
import java.net.URL;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.LoadEvent;
import com.ja.smarkdown.model.MarkdownDocument;

@Slf4j
public class WebInfMarkdownProvider {

	private static final String PREFIX = "web-inf:";

	@Inject
	private ServletContext servletContext;

	public void onEvent(@Observes final LoadEvent event) {
		log.info("Event received. {}", event);
		try {

			final String doc = StringUtils.substringAfter(
					event.getDocumentUrl(), PREFIX);
			if (doc == null) {
				log.info("Can't process this url={}", event.getDocumentUrl());
				return;
			}
			final URL url = servletContext.getResource(String.format(
					"/WEB-INF/%s", doc));
			if (url == null) {
				log.info("Can't process this url={}", event.getDocumentUrl());
				return;
			}
			log.info("Reading markdown from: {}", url);
			try (InputStream in = url.openStream()) {
				event.addResult(new MarkdownDocument(IOUtils.toString(in)));
			}
		} catch (final Exception e) {
			log.info("Can't process this url={}", event.getDocumentUrl());
		}
	}
}
