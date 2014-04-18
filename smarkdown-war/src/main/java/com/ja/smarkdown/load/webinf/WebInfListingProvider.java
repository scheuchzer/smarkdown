package com.ja.smarkdown.load.webinf;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.load.ListEvent;

@Slf4j
public class WebInfListingProvider {

	private static final String PREFIX = "web-inf:";
	@Inject
	private ServletContext servletContext;

	@Inject
	private ListingParser parser;

	public void onEvent(@Observes final ListEvent event) throws Exception {
		log.info("Event received. {}", event);
		if (event.getBaseLocation().startsWith(PREFIX)) {
			readListingFile(event,
					StringUtils.substringAfter(event.getBaseLocation(), PREFIX));
		}
	}

	private void readListingFile(final ListEvent event, final String root) {
		try {
			final URL url = servletContext.getResource(String.format(
					"/WEB-INF/%s/listing.json", root));
			log.info("Reading listing from: {}", url);
			if (url == null) {
				return;
			}

			try (Reader in = new InputStreamReader(url.openStream())) {
				final Set<String> files = parser.parse(in);
				event.addResults(files);
			}
		} catch (final Exception e) {
			log.info("Failed to read listing.", e);
		}

	}
}
