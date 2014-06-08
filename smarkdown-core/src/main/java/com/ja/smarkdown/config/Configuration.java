package com.ja.smarkdown.config;

import java.util.Iterator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@ApplicationScoped
@Slf4j
public class Configuration {

	@Inject
	private Event<ConfigEvent> events;
	@Inject
	private Instance<ConfigurationProvider> configurationProviders;

	private SmarkdownConfiguration config;

	@Produces
	public SmarkdownConfiguration create() {
		if (config != null) {
			return config;
		}

		final SmarkdownConfiguration newConfig = resolveConfig();
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

	private SmarkdownConfiguration resolveConfig() {
		SmarkdownConfiguration config = null;
		if (configurationProviders.isUnsatisfied()) {
			log.warn("No {} found! Use minimalistic config.",
					ConfigurationProvider.class.getName());
			config = new SmarkdownConfiguration();
			config.getLocations().add(
					Location.create(String.format("file://%s/smarkdown",
							System.getProperty("user.home"))));
			return config;
		} else if (configurationProviders.isAmbiguous()) {
			log.warn("More than one {} configured",
					ConfigurationProvider.class.getName());
			final Iterator<ConfigurationProvider> it = configurationProviders
					.iterator();
			final ConfigurationProvider provider = it.next();
			log.warn("Will use {}={}", ConfigurationProvider.class.getName(),
					provider);
			config = provider.getConfiguration();
			while (it.hasNext()) {
				log.warn("Skipping configuration from {}={}",
						ConfigurationProvider.class.getName(), it.next());
			}
		} else {
			final ConfigurationProvider provider = configurationProviders.get();
			log.info("Reading configuration from {}={}",
					ConfigurationProvider.class.getName(), provider);
			config = provider.getConfiguration();
		}
		return config;
	}
}
