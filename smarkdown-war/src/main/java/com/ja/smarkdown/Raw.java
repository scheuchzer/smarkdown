package com.ja.smarkdown;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

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
		log.info("path={}", path);
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
			return MediaType.TEXT_PLAIN;
		case "txt":
			return MediaType.TEXT_PLAIN;
		case "html":
			return MediaType.TEXT_HTML;
		case "htm":
			return MediaType.TEXT_HTML;
		case "xml":
			return MediaType.TEXT_XML;
		case "json":
			return MediaType.APPLICATION_JSON;
		case "jpg":
			return "image/jpeg";
		case "jpeg":
			return "image/jpeg";
		case "png":
			return "image/png";
		case "gif":
			return "image/gif";
		case "svg":
			return MediaType.APPLICATION_SVG_XML;
		case "zip":
			return "application/zip";
		default:
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

	private InputStream load(final String path) {
		final ResourceInfo resource = resourceLoader.loadResource(path);
		if (resource != null) {
			log.info("Resource found.");
		} else {
			log.info("{} not found.", path);
		}
		return resource.getInputStream();
	}

}
