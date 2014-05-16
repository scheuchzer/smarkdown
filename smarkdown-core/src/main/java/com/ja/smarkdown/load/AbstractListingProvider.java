package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractListingProvider<LOCATION_TYPE> {

	public List<String> getDocuments(final List<LOCATION_TYPE> locations) {
		final List<String> documents = new ArrayList<String>();
		log.info("Starting listing from {} locations", locations.size());
		for (final LOCATION_TYPE location : locations) {
			log.info("Start listing documents from lcoation={}", location);
			documents.addAll(getDocuments(location));
		}
		return documents;
	}

	abstract protected List<String> getDocuments(final LOCATION_TYPE location);
}
