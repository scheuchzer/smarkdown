package com.ja.smarkdown.load;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.model.Listing;

@Slf4j
public abstract class AbstractServletContextListingProvider {

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

	public void onEvent(@Observes final ListEvent event) throws Exception {
		log.debug("Event received. {}", event);
		if (event.getLocation().getUrl().startsWith(urlPrefix)) {
			readListingFile(event, StringUtils.substringAfter(event
					.getLocation().getUrl(), urlPrefix));
		}
		log.debug("End event.");
	}

	private void readListingFile(final ListEvent event, final String root) {
		try {
			final String listingResource = String.format("%s/%s/listing.json",
					resourcePrefix, root);

			log.debug("Reading listing from: {}", listingResource);
			final URL url = servletContext.getResource(listingResource);
			if (url == null) {
				return;
			}

			try (Reader in = new InputStreamReader(url.openStream())) {
				final Listing listing = parser.parse(in);
				for (final String file : listing.getFiles()) {
					event.addResult(MountPointUtil.apply(event.getLocation(),
							file));
				}
			}
		} catch (final Exception e) {
			log.debug("Failed to read listing.", e);
		}

	}
}
