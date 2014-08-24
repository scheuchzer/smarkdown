package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.config.Location;

@Slf4j
public abstract class AbstractListingProvider<LOCATION_TYPE extends Location> {

	private final Cache cache = new Cache();

	public List<String> getDocuments(final List<LOCATION_TYPE> locations) {
		final List<String> documents = new ArrayList<String>();
		log.debug("Starting listing from {} locations", locations.size());
		for (final LOCATION_TYPE location : locations) {
			log.debug("Start listing documents from lcoation={}", location);

			List<String> result = cache.get(location);
			if (result == null) {
				result = getDocuments(location);
				cache.put(location, result);
			} else {
				log.debug("Using cached listing for location={}", location);
			}
			documents.addAll(result);
		}
		return documents;
	}

	protected List<String> getDocuments(final LOCATION_TYPE location) {

		List<String> documents = new ArrayList<String>();
		readDocumentsFromListingFile(location, documents, "listing.json");
		if (documents.isEmpty()) {
			readDocuments(location, documents);
		}
		return documents;
	}

	abstract protected void readDocumentsFromListingFile(
			final LOCATION_TYPE location, final List<String> documents,
			final String listingFileName);

	abstract protected void readDocuments(final LOCATION_TYPE location,
			final List<String> documents);
}
