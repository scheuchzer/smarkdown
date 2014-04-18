package com.ja.smarkdown.model;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.util.UrlUtils;

@Data
public class ListingDocument {

	private final String name;
	private final String fileName;
	private final String title;

	public String getUrlEncodedName() {
		final String[] paths = StringUtils.split(name, "/");
		final StringBuilder sb = new StringBuilder();
		for (final String path : paths) {
			if (sb.length() > 0) {
				sb.append('/');
			}
			sb.append(UrlUtils.encode(path));
		}
		return sb.toString();
	}
}
