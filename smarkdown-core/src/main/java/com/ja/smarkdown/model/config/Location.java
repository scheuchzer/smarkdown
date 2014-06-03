package com.ja.smarkdown.model.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.util.TimeUnitUtils;

import lombok.Data;

@Data
public class Location {
	public enum Properties {
		mountPoint, cacheDuration;
	}

	private String url;
	private Map<String, String> config = new HashMap<String, String>();

	public Location() {
		setCacheDuration(1, TimeUnit.MINUTES);
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
