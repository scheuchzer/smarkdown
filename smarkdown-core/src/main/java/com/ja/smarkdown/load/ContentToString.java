package com.ja.smarkdown.load;

import org.apache.commons.lang3.StringUtils;

public class ContentToString {

	private static final int MAX_WIDTH = 50;

	private final String content;

	public ContentToString(final String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return StringUtils.abbreviate(content, MAX_WIDTH);
	}

	public static ContentToString of(final String content) {
		return new ContentToString(content);
	}

}
