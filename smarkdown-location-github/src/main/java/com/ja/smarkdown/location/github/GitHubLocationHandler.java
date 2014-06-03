package com.ja.smarkdown.location.github;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@ApplicationScoped
public class GitHubLocationHandler implements LocationHandler {

	@Inject
	private GitHubListingProvider listing;
	@Inject
	private GitHubDocumentProvider documents;

	private final List<GitHubLocation> locations = new ArrayList<>();

	@Override
	public List<String> listDocuments() {
		return listing.getDocuments(locations);
	}

	@Override
	public ResourceInfo loadDocument(final String resource) {
		return documents.getDocument(locations, resource);
	}

	public void add(final GitHubLocation location) {
		locations.add(location);
	}

	@Override
	public void add(final Location location) {
		add(new GitHubLocation(location));
	}
}
