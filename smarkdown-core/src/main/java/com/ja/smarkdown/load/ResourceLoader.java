package com.ja.smarkdown.load;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Slf4j
@ApplicationScoped
public class ResourceLoader {

	@Inject
	private SmarkdownConfiguration config;

	public ResourceInfo loadResource(final String resource) {
		log.info("loading resource={}", resource);

		for (final LocationHandler handler : config.getLocationHandlers()) {
			try {
				log.debug("Calling handler={}", handler);
				final ResourceInfo result = handler.loadDocument(resource);
				if (result != null) {
					log.debug("Handler {} sucessful", handler);
					return result;
				}
				log.debug("Handler {} not successful", handler);
			} catch (final Exception e) {
				log.warn("Handler {} failed for resource {}. {}", handler,
						resource, e);
			}

		}
		log.debug("Resource {} not found.", resource);
		return null;
	}
}
