package com.ja.smarkdown.config;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.json.SmarkdownConfigurationParser;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@ApplicationScoped
@Slf4j
public class Configuration {

	private static final String SMARKDOWN_CONFIGURATION = "smarkdown.configuration";

	@Inject
	private ServletContext servletContext;
	@Inject
	private Event<ConfigEvent> events;
	@Inject
	private SmarkdownConfigurationParser parser;

	private SmarkdownConfiguration config;

	@Produces
	public SmarkdownConfiguration create() {
		if (config != null) {
			return config;
		}
		final String configString = servletContext
				.getInitParameter(SMARKDOWN_CONFIGURATION);
		log.info("Configuration from web.xml is: {}", configString);

		SmarkdownConfiguration newConfig = null;
		if (StringUtils.isEmpty(configString)) {
			log.info("Using default configuration.");
			newConfig = new SmarkdownConfiguration();
		} else {
			newConfig = parser.parse(new StringReader(configString));
		}

		for (final Location location : newConfig.getLocations()) {
			final ConfigEvent event = new ConfigEvent(newConfig, location);
			events.fire(event);
			if (event.getHandler() != null) {
				log.info("LocationHandler found for location={}, handler={}",
						location.getUrl(), event.getHandler());
				newConfig.getLocationHandlers().add(event.getHandler());
			} else {
				log.error(
						"Failed to find a LocationHandler found for location={}",
						location.getUrl());
			}
		}
		config = newConfig;
		return config;
	}
}
