package com.ja.smarkdown.location.http;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

public class SmarkdownHttpsLocationHandler implements LocationHandler {

	@Inject
	private SmarkdownHttpListingProvider listing;
	@Inject
	private SmarkdownHttpsDocumentProvider documents;

	private List<HttpLocation> locations = new ArrayList<HttpLocation>();

	@Override
	public List<String> listDocuments() {
		return listing.getDocuments(locations);
	}

	@Override
	public ResourceInfo loadDocument(final String resource) {
		return documents.getDocument(locations, resource);
	}

	public void add(final HttpLocation location) {
		locations.add(location);
	}

	@Override
	public void add(final Location location) {
		add(new HttpLocation(location));
	}

}
