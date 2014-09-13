package com.ja.smarkdown.location.http;

import java.util.concurrent.TimeUnit;

import com.ja.smarkdown.model.config.Location;

public class HttpLocation extends Location {

	private static final String PREFIX = "http";

	private final String prefix;

	public HttpLocation(final Location location) {
		this(location, PREFIX);
	}

	protected HttpLocation(final Location location, final String prefix) {
		this.prefix = prefix;
		setConfig(location.getConfig());
		setMountPoint(location.getMountPoint());
		setUrl(location.getUrl());
		setIncludes(location.getIncludes());
		setExcludes(location.getExcludes());
		if (location.getCacheDuration() == 0) {
			setCacheDuration(TimeUnit.MINUTES.toMillis(1));
		}
	}

	public boolean isAcceptable() {
		return getUrl().startsWith(prefix);
	}

}
