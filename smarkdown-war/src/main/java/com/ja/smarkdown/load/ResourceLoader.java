package com.ja.smarkdown.load;

import java.util.Iterator;

import javax.enterprise.event.Event;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Slf4j
@ApplicationScoped
public class ResourceLoader {
	@Inject
	private SmarkdownConfiguration config;
	@Inject
	private Event<LoadEvent> loadEvent;

	public ResourceInfo loadResource(final String resource) {
		log.debug("loading resource={}", resource);
		ResourceInfo result = null;

		for (final String location : config.getLocations()) {
			final String url = String.format("%s/%s", location, resource);
			log.debug("Loading resource from url={}", url);
			final LoadEvent event = new LoadEvent(url);
			loadEvent.fire(event);
			log.debug("Resource loaded. results={}", event.getResults().size());

			if (!event.getResults().isEmpty()) {
				final Iterator<ResourceInfo> it = event.getResults().iterator();
				result = it.next();
				if (it.hasNext()) {
					final StringBuilder sb = new StringBuilder();
					sb.append("Ambigous result. {} overrides resources: ");
					while (it.hasNext()) {
						final ResourceInfo ri = it.next();
						sb.append(ri).append(", ");
						IOUtils.closeQuietly(ri.getInputStream());
					}
					log.info(sb.toString(), result);
				}
				return result;
			}
		}
		return null;
	}
}
