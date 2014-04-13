package com.ja.smarkdown.load.url;

import java.io.InputStream;
import java.net.URL;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

import com.ja.smarkdown.load.ContentToString;
import com.ja.smarkdown.load.LoadEvent;
import com.ja.smarkdown.model.MarkdownDocument;

@Slf4j
public class UrlMarkdownProvider {

	public void onEvent(@Observes final LoadEvent event) {
		log.info("Event received. {}", event);
		try {
			final URL url = new URL(event.getDocumentUrl());
			log.info("Resolved url={}", url);
			try (InputStream in = url.openStream()) {
				final String content = IOUtils.toString(in);
				log.info("File read. Content={}", ContentToString.of(content));
				if (content != null) {
					event.addResult(new MarkdownDocument(content));
				}
			}
		} catch (final Exception e) {
			log.info("Can't process this url={}", event.getDocumentUrl());
		}
	}

}
