package com.ja.smarkdown.model.config;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Location {
	public enum Properties {
		mountPoint
	}

	private String url;
	private Map<String, String> config = new HashMap<String, String>();

	public static Location create(final String url) {
		final Location l = new Location();
		l.setUrl(url);
		return l;
	}

	public void setMountPoint(final String mountPoint) {
		config.put(Properties.mountPoint.toString(), mountPoint);
	}

	public String getMountPoint() {
		return config.get(Properties.mountPoint.toString());
	}
}
