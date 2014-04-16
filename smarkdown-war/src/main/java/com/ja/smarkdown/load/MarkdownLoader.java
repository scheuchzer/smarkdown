package com.ja.smarkdown.load;

import javax.enterprise.event.Event;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.MarkdownDocument;

@Slf4j
@ApplicationScoped
public class MarkdownLoader {
	@Inject
	private Locations locations;
	@Inject
	private Event<LoadEvent> loadEvent;

	public MarkdownDocument loadDocument(final String document) {
		log.info("loading document={}", document);

		for (final String location : locations.getLocations()) {
			final String url = location + document;
			log.info("Loading document from url={}", url);
			final LoadEvent event = new LoadEvent(url);
			loadEvent.fire(event);
			log.info("Document loaded. results={}", event.getResults().size());
			if (!event.getResults().isEmpty()) {
				return event.getResults().get(0);
			}
		}
		return null;
	}
}
