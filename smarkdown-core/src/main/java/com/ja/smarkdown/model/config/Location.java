package com.ja.smarkdown.model.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.util.TimeUnitUtils;

@Data
public class Location {
	public enum Properties {
		mountPoint, cacheDuration;
	}

	private String url;
	private List<String> includes = new ArrayList<String>();
	private List<String> excludes = new ArrayList<String>();
	private Map<String, String> config = new HashMap<String, String>();

	public Location() {
		setCacheDuration(1, TimeUnit.MINUTES);
		includes.add(".*");
		excludes.add("META-INF/.*");
	}

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

	public long getCacheDuration() {
		String value = getConfig().get(Properties.cacheDuration.toString());
		value = StringUtils.defaultString(value, "0");
		return TimeUnitUtils.parseToMillis(value);
	}

	public void setCacheDuration(final long timeMillis) {
		setCacheDuration(timeMillis, TimeUnit.MILLISECONDS);
	}

	public void setCacheDuration(final long amount, final TimeUnit unit) {
		String duration = String.format("%s %s", amount, unit.toString());
		getConfig().put(Properties.cacheDuration.toString(), duration);
	}
}
