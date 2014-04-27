package com.ja.smarkdown.load.url;

import java.net.URL;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.load.LoadEvent;
import com.ja.smarkdown.model.ResourceInfo;

@Slf4j
public class UrlMarkdownProvider {

	public void onEvent(@Observes final LoadEvent event) {
		log.debug("Event received. {}", event);
		try {
			final URL url = new URL(event.getDocumentUrl());
			log.debug("Resolved url={}", url);
			event.addResult(new ResourceInfo(this.getClass(), url.toString(),
					url.openStream()));
		} catch (final Exception e) {
			log.debug("Can't process this url={}", event.getDocumentUrl());
		}
	}

}
