package com.ja.smarkdown.location.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.load.AbstractDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public class FileDocumentProvider extends AbstractDocumentProvider<Location> {

	public FileDocumentProvider() {
		super("file://", "");
	}

	@Override
	protected ResourceInfo getResource(final Location location,
			final String path) throws FileNotFoundException {
		final String url = String.format("file://%s", path);
		log.debug("Resolved url={}", url);
		try {
			final URL resourceUrl = new URL(url);
			return new ResourceInfo(path, location, resourceUrl.openStream());
		} catch (final IOException e) {
			throw new FileNotFoundException(path + " " + e);
		}
	}
}
