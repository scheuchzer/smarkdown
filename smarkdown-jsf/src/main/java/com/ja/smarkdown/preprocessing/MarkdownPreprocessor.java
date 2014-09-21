package com.ja.smarkdown.preprocessing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.model.ResourceInfo;

@Slf4j
@Named
@ApplicationScoped
public class MarkdownPreprocessor {

	@Inject
	private ServletContext servletContext;

	public String process(final String path, final String content) {
		final ProcessingContext ctx = ProcessingContext.create(path,
				servletContext);
		String result = content.replaceAll("\\.md\\)", "\\.html\\)");
		result = handleImages(ctx, result);
		return result;
	}

	private String handleImages(final ProcessingContext ctx,
			final String content) {
		/*
		 * Img Syntax=![alt](url)
		 * 
		 * (?!http) = not http, (?!/) = not /
		 */
		final Pattern p = Pattern.compile("\\!\\[.*\\(((?!http)(?!/).*)\\)");
		final Matcher m = p.matcher(content);
		final List<String> toBeReplaced = new ArrayList<>();
		final List<String> replacement = new ArrayList<>();
		while (m.find()) {
			final String match = m.group(1);
			String from = String.format("(%s)", match);
			String to = String.format("(%s/raw%s/%s)", ctx.getBaseUrl(),
					ctx.getDirectory(), match);
			log.info("Replacing from={}, to={}", from, to);
			toBeReplaced.add(from);
			replacement.add(to);
		}
		return StringUtils.replaceEach(content,
				toBeReplaced.toArray(new String[0]),
				replacement.toArray(new String[0]));
	}

	public String process(final String page, final ResourceInfo resource) {
		return process(page, readContent(resource));
	}

	private String readContent(final ResourceInfo resource) {
		try (InputStream in = resource.getInputStream()) {
			return addDuplicateInfo(resource, IOUtils.toString(in));
		} catch (final IOException e) {
			log.error("Failed to read markdown from input stream.", e);
		}
		return "Internal error";
	}

	private String addDuplicateInfo(final ResourceInfo resource,
			final String content) {
		final StringBuilder buf = new StringBuilder();
		if (!resource.getOverridden().isEmpty()) {
			buf.append("<div class='alert alert-info'>");
			buf.append("Document found in multiple locations!");
			buf.append("<ul>");
			buf.append("<li>").append(resource.getLocation().getUrl()).append("</li>");
			for (final ResourceInfo ri : resource.getOverridden()) {
				buf.append("<li>").append(ri.getLocation().getUrl())
						.append(ri.getPath()).append("</li>");
			}
			buf.append("</ul>");
			buf.append("</div>\n");
		}
		buf.append(content);
		return buf.toString();
	}
}
