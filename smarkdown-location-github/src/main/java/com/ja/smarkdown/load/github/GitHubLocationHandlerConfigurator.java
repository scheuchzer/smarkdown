package com.ja.smarkdown.load.github;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.config.ConfigEvent;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public class GitHubLocationHandlerConfigurator {

	@Inject
	private GitHubLocationHandler handler;

	public void configure(@Observes final ConfigEvent event) {
		log.info("ConfigEvent received. Location={}", event.getLocation());
		final Location location = event.getLocation();
		if (location.getUrl().startsWith("github:")) {
			log.info("I might be able to handle this.");
			handler.add(location);
			event.setHandler(handler);
		} else {
			log.info("Nope.");
		}
	}
}
