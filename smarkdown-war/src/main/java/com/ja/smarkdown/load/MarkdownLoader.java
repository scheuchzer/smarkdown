package com.ja.smarkdown.load;

import javax.enterprise.event.Event;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.MarkdownDocument;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Slf4j
@ApplicationScoped
public class MarkdownLoader {
	@Inject
	private SmarkdownConfiguration config;
	@Inject
	private Event<LoadEvent> loadEvent;

	public MarkdownDocument loadDocument(final String document) {
		log.debug("loading document={}", document);

		for (final String location : config.getLocations()) {
			final String url = String.format("%s/%s", location, document);
			log.debug("Loading document from url={}", url);
			final LoadEvent event = new LoadEvent(url);
			loadEvent.fire(event);
			log.debug("Document loaded. results={}", event.getResults().size());
			if (!event.getResults().isEmpty()) {
				return event.getResults().get(0);
			}
		}
		return null;
	}
}
