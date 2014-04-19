package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.model.ListingDocument;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;
import com.ja.smarkdown.util.LowerCaseStringComparator;

@Slf4j
public class DocumentScanner {

	@Inject
	private SmarkdownConfiguration config;
	@Inject
	private Event<ListEvent> listEvent;

	public List<ListingDocument> getDocuments() {
		log.debug("loading listing.");
		final Set<String> documents = new HashSet<>();
		for (final String location : config.getLocations()) {
			final ListEvent event = new ListEvent(location);
			listEvent.fire(event);
			log.debug("Documents listed. results={}", event.getResults().size());
			documents.addAll(event.getResults());
		}
		final List<String> result = new ArrayList<>(documents);
		Collections.sort(result, new LowerCaseStringComparator());

		final List<ListingDocument> listingDocuments = new ArrayList<>();
		for (final String doc : result) {
			final String fileName = doc;
			final String name = StringUtils.substringBefore(doc, ".md");
			final String title = TitleParser.toTitle(name);
			listingDocuments.add(new ListingDocument(name, fileName, title));
		}
		return listingDocuments;
	}

}
