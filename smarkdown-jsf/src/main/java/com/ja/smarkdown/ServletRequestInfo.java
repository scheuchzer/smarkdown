package com.ja.smarkdown;

import javax.servlet.ServletContext;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.processing.RequestInfo;

@Data
@Slf4j
public class ServletRequestInfo implements RequestInfo {

	private final String path;
	private final String directory;
	private final String baseUrl;
	private final Object externalContext;

	public static ServletRequestInfo create(final String path,
			final ServletContext servletContext) {
		String directory = "";
		if (path.contains("/")) {
			directory = "/" + StringUtils.substringBeforeLast(path, "/");
		}
		if (StringUtils.startsWith(directory, "/slides")) {
			directory = StringUtils.removeStart(directory, "/slides");
		}
		ServletRequestInfo pc = new ServletRequestInfo(path, directory,
				servletContext.getContextPath(), servletContext);
		log.debug("ServletRequestInfo={}", pc);
		return pc;
	}
}
