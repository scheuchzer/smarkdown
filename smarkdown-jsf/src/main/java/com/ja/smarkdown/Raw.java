package com.ja.smarkdown;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.ResourceLoader;
import com.ja.smarkdown.model.ResourceInfo;

@WebServlet(urlPatterns = { "/raw/*" })
@Slf4j
public class Raw extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Inject
	private ResourceLoader resourceLoader;

	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		final String path = StringUtils.removeStart(req.getPathInfo(), "/");
		log.debug("path={}", path);
		final InputStream in = load(path);
		if (in != null) {
			try (InputStream i = in) {
				resp.setContentType(resolveContentType(path));
				IOUtils.copy(i, resp.getOutputStream());
			}
		} else {
			super.doGet(req, resp);
		}
	}

	private String resolveContentType(final String path) {
		final String extension = StringUtils.substringAfter(path, ".")
				.toLowerCase();
		switch (extension) {
		case "md":
			return "text/plain";
		case "txt":
			return "text/plain";
		case "html":
			return "text/html";
		case "htm":
			return "text/html";
		case "xml":
			return "text/xml";
		case "json":
			return "application/json";
		case "jpg":
			return "image/jpeg";
		case "jpeg":
			return "image/jpeg";
		case "png":
			return "image/png";
		case "gif":
			return "image/gif";
		case "svg":
			return "application/svg+xml";
		case "zip":
			return "application/zip";
		default:
			return "application/octet-stream";
		}
	}

	private InputStream load(final String path) {
		final ResourceInfo resource = resourceLoader.loadResource(path);
		if (resource != null) {
			log.debug("Resource found.");
			return resource.getInputStream();
		} else {
			log.debug("{} not found.", path);
			return null;
		}
	}

}
