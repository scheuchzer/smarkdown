package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.MarkdownDocument;

@Slf4j
@ApplicationScoped
public class MarkdownLoader {

	private final List<String> locations = new ArrayList<>();
	@Inject
	private Event<LoadEvent> loadEvent;

	public MarkdownLoader() {
		addLocation("classpath:");
		addLocation("classpath:smarkdown/");
		addLocation(String.format("file://%s/smarkdown/",
				System.getProperty("user.home")));
	}

	private void addLocation(final String location) {
		log.info("Adding location={}", location);
		locations.add(location);
	}

	public MarkdownDocument loadDocument(final String document) {
		log.info("loading document={}", document);

		for (final String location : locations) {
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
