package com.ja.smarkdown.load;

import java.net.URL;
import java.net.URLStreamHandler;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.ResourceInfo;

@Slf4j
public abstract class AbstractMarkdownProvider {

	public void onEvent(@Observes final LoadEvent event) {
		log.debug("Event received. {}", event);
		try {
			final URL url = new URL(null, event.getDocumentUrl(), getHandler());
			log.debug("Resolved url={}", url);
			event.addResult(new ResourceInfo(this.getClass(), url.toString(),
					url.openStream()));
		} catch (final Exception e) {
			log.debug("Can't process this url={}", event.getDocumentUrl());
		}
	}

	protected abstract URLStreamHandler getHandler();
}
