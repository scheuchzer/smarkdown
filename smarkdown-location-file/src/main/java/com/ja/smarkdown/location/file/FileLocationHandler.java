package com.ja.smarkdown.location.file;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

public class FileLocationHandler implements LocationHandler {

	@Inject
	private FileListingProvider listing;
	@Inject
	private FileDocumentProvider documents;

	private List<Location> locations = new ArrayList<Location>();

	@Override
	public List<String> listDocuments() {
		return listing.getDocuments(locations);
	}

	@Override
	public ResourceInfo loadDocument(final String resource) {
		return documents.getDocument(locations, resource);
	}

	@Override
	public void add(final Location location) {
		locations.add(location);
	}

}
