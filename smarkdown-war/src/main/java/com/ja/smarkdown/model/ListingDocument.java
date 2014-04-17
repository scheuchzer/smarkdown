package com.ja.smarkdown.model;

import lombok.Data;

import com.ja.smarkdown.util.UrlUtils;

@Data
public class ListingDocument {

	private final String name;
	private final String fileName;
	private final String title;

	public String getUrlEncodedName() {
		return UrlUtils.encode(name);
	}
}
