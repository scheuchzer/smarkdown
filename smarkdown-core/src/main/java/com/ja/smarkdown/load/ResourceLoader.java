package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		return loadResource(resource, false);
	}

	public ResourceInfo loadResource(final String resource,
			final Boolean duplicateCheckOverride) {
		log.info("loading resource={}", resource);
		final List<ResourceInfo> results = new ArrayList<>();
		for (final LocationHandler handler : config.getLocationHandlers()) {
			try {
				log.debug("Calling handler={}", handler);
				final ResourceInfo result = handler.loadDocument(resource);
				if (result != null) {
					log.debug("Handler {} sucessful", handler);
					results.add(result);
					if (!isDuplicateCheckEnabled(duplicateCheckOverride)) {
						break;
					}

				} else {
					log.debug("Handler {} not successful", handler);
				}
			} catch (final Exception e) {
				log.warn("Handler {} failed for resource {}. {}", handler,
						resource, e);
			}

		}

		return merge(resource, results);
	}

	private ResourceInfo merge(final String resource,
			final List<ResourceInfo> results) {
		if (results.isEmpty()) {
			log.debug("Resource {} not found.", resource);
			return null;
		}
		final Iterator<ResourceInfo> it = results.iterator();
		final ResourceInfo first = it.next();
		while (it.hasNext()) {
			first.getOverridden().add(it.next());
		}
		if (!first.getOverridden().isEmpty()) {
			log.info("Resource {} has overrides. Selected={}", resource, first);

		}
		return first;

	}

	private boolean isDuplicateCheckEnabled(final Boolean duplicateCheckOverride) {

		return duplicateCheckOverride == null ? config.getPages()
				.isCheckForDuplicates() : duplicateCheckOverride;
	}

}
