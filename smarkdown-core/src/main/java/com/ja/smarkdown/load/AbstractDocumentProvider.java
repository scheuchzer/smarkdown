package com.ja.smarkdown.load;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@Slf4j
@AllArgsConstructor
public abstract class AbstractDocumentProvider<LOCATION_TYPE extends Location> {

	private final String protocol;
	private final String resourcePrefix;

	public ResourceInfo getDocument(final List<LOCATION_TYPE> locations,
			final String resource) {
		log.debug("Start loading document from {} locations", locations.size());
		for (final LOCATION_TYPE location : locations) {
			log.debug("Loading documents from location {}", location);
			final ResourceInfo document = getDocument(location, resource);
			if (document == null) {
				log.debug("Document not found, yet. LOCATION_TYPE was {}",
						location);
			} else {
				log.debug("Document found at location {}", location);
				return document;
			}
		}
		log.debug("Document not found in any location");
		return null;
	}

	protected ResourceInfo getDocument(final LOCATION_TYPE location,
			final String resource) {
		ResourceInfo document = null;
		final String strippedResource = MountPointUtil.remove(location,
				resource);
		try {
			final String rootPath = getRootPath(location);
			String path = null;
			if (StringUtils.isBlank(rootPath)) {
				path = String.format("%s%s", resourcePrefix, strippedResource);
			} else {
				path = String.format("%s%s/%s", resourcePrefix, rootPath,
						strippedResource);
			}
			log.debug("Resource path={}", path);
			document = new ResourceInfo(this.getClass(), getInputStream(
					location, path));
		} catch (final FileNotFoundException e) {
			return null;
		} catch (final Exception e) {
			log.error("Can't process this url={}", resource, e);
		}
		return document;
	}

	protected String getRootPath(final LOCATION_TYPE location) {
		return StringUtils.substringAfter(location.getUrl(), protocol);
	}

	abstract protected InputStream getInputStream(final LOCATION_TYPE location,
			final String path) throws FileNotFoundException;
}
