package com.ja.smarkdown.load;

import java.io.InputStream;
import java.net.URL;
import java.net.URLStreamHandler;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

import com.ja.smarkdown.model.MarkdownDocument;

@Slf4j
public abstract class AbstractMarkdownProvider {

	public void onEvent(@Observes final LoadEvent event) {
		log.debug("Event received. {}", event);
		try {

			final URL url = new URL(null, event.getDocumentUrl(), getHandler());
			log.debug("Resolved url={}", url);
			try (InputStream in = url.openStream()) {
				final String content = IOUtils.toString(in);
				log.debug("File read. Content={}", ContentToString.of(content));
				if (content != null) {
					event.addResult(new MarkdownDocument(content));
				}
			}
		} catch (final Exception e) {
			log.debug("Can't process this url={}", event.getDocumentUrl());
		}
	}

	protected abstract URLStreamHandler getHandler();
}