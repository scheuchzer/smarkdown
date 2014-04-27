package com.ja.smarkdown.preprocessing;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class ProcessingContext {

	private final String path;
	private final String directory;

	public static ProcessingContext create(final String path) {
		String directory = "";
		if (path.contains("/")) {
			directory = StringUtils.substringBefore(path, "/");
		}
		return new ProcessingContext(path, directory);
	}
}
