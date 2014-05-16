package com.ja.smarkdown.load.webapp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

public class WebappLocationHandler implements LocationHandler {

	@Inject
	private WebappListingProvider listing;
	@Inject
	private WebappDocumentProvider documents;

	private List<Location> locations = new ArrayList<Location>();

	@Override
	public List<String> listDocuments() {
		return listing.getDocuments(locations);
	}

	@Override
	public ResourceInfo loadDocument(final String resource) {
		return documents.getDocument(locations, resource);
	}

	public void add(final Location location) {
		locations.add(location);
	}

}
