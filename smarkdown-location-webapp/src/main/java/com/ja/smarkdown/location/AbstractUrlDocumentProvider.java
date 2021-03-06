package com.ja.smarkdown.location;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.load.AbstractDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public abstract class AbstractUrlDocumentProvider<LOCATION_TYPE extends Location>
		extends AbstractDocumentProvider<LOCATION_TYPE> {

	public AbstractUrlDocumentProvider(final String locationPrefix,
			final String resourcePrefix) {
		super(locationPrefix, resourcePrefix);
	}

	@Override
	protected ResourceInfo getResource(final LOCATION_TYPE location,
			final String path) throws FileNotFoundException {
		try {
			final URL resourceUrl = getUrl(location, path);
			if (resourceUrl == null) {
				log.debug("Resource not found at path={}", path);
				throw new FileNotFoundException(path);
			} else {
				return new ResourceInfo(path, location,
						resourceUrl.openStream());
			}
		} catch (final FileNotFoundException e) {
			throw e;
		} catch (final IOException e) {
			log.debug("Resource not found at path={}", path, e);
			throw new FileNotFoundException(path);
		}
	}

	abstract protected URL getUrl(final LOCATION_TYPE location,
			final String path) throws MalformedURLException;

}
