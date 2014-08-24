package com.ja.smarkdown.location.http;

import java.util.concurrent.TimeUnit;

import com.ja.smarkdown.model.config.Location;

public class HttpLocation extends Location {

	private static final String PREFIX = "http";

	public enum Properties {
		authToken, branch
	}

	public HttpLocation(final Location location) {
		setConfig(location.getConfig());
		setMountPoint(location.getMountPoint());
		setUrl(location.getUrl());
		if (location.getCacheDuration() == 0) {
			setCacheDuration(TimeUnit.MINUTES.toMillis(60));
		}
	}

	public boolean isAcceptable() {
		return getUrl().startsWith(PREFIX);
	}

}
