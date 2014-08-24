package com.ja.smarkdown.location;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.Listing;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public abstract class AbstractServletContextListingProvider extends
		AbstractListingProvider<Location> {

	private final String urlPrefix;
	private final String resourcePrefix;

	@Inject
	private ServletContext servletContext;

	@Inject
	private ListingParser parser;

	protected AbstractServletContextListingProvider(final String urlPrefix,
			final String resourcePrefix) {
		this.urlPrefix = urlPrefix;
		this.resourcePrefix = resourcePrefix;
	}

	@Override
	protected void readDocumentsFromListingFile(final Location location,
			final List<String> documents, final String listingFileName) {
		try {

			final String listingResource = String.format("%s/%s/%s",
					resourcePrefix,
					StringUtils.substringAfter(location.getUrl(), urlPrefix),
					listingFileName);

			log.debug("Reading listing from: {}", listingResource);
			final URL url = servletContext.getResource(listingResource);
			if (url == null) {
				return;
			}

			try (Reader in = new InputStreamReader(url.openStream())) {
				final Listing listing = parser.parse(in);
				for (final String file : listing.getFiles()) {
					documents.add(MountPointUtil.apply(location, file));
				}
			}
		} catch (final Exception e) {
			log.debug("Failed to read listing.", e);
		}
	}

	@Override
	protected void readDocuments(Location location, final List<String> documents) {
		// Nothing to do as we can't scan the ServletContext. We read the files
		// from the listing file.
	}
}
