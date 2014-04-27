package com.ja.smarkdown.preprocessing;

import javax.servlet.ServletContext;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class ProcessingContext {

	private final String path;
	private final String directory;
	private final String baseUrl;

	public static ProcessingContext create(final String path,
			final ServletContext servletContext) {
		String directory = "";
		if (path.contains("/")) {
			directory = StringUtils.substringBefore(path, "/");
		}
		return new ProcessingContext(path, directory,
				servletContext.getContextPath());
	}
}
