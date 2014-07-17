package com.ja.smarkdown.preprocessing;

import javax.servlet.ServletContext;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@Slf4j
@Data
public class ProcessingContext {

	private final String path;
	private final String directory;
	private final String baseUrl;

	public static ProcessingContext create(final String path,
			final ServletContext servletContext) {
		String directory = "";
		if (path.contains("/")) {
			directory = "/" + StringUtils.substringBeforeLast(path, "/");
		}
		ProcessingContext pc = new ProcessingContext(path, directory,
				servletContext.getContextPath());
		log.info("processingContext={}", pc);
		return pc;
	}
}
