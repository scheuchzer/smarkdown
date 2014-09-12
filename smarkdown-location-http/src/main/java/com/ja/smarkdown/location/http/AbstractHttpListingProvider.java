package com.ja.smarkdown.location.http;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.Listing;

@Slf4j
public abstract class AbstractHttpListingProvider extends
		AbstractListingProvider<HttpLocation> {

	@Inject
	private ListingParser parser;

	@Override
	protected void readDocumentsFromListingFile(HttpLocation location,
			List<String> documents, String listingFileName) {
		try {
			final URL listingFile = resolveListingFileUrl(location,
					listingFileName);
			try (Reader in = new InputStreamReader(listingFile.openStream())) {
				final Listing listing = parser.parse(in);
				for (final String file : listing.getFiles()) {
					documents.add(transformFileName(location, file));
				}
			}
		} catch (Exception e) {
			log.error("Failed to read the listing file for location={}",
					location, e);
		}
	}

	protected URL resolveListingFileUrl(HttpLocation location,
			String listingFileName) throws MalformedURLException {
		return new URL(String.format("%s/%s", location.getUrl(),
				listingFileName));
	}

	protected String transformFileName(HttpLocation location, final String file) {
		return MountPointUtil.apply(location, file);
	}

	@Override
	protected void readDocuments(final HttpLocation location,
			final List<String> documents) {
		// not supported. We can only read the listing file.
	}

}
