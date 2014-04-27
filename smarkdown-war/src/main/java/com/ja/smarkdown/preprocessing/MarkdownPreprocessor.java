package com.ja.smarkdown.preprocessing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
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
			toBeReplaced.add(String.format("(%s)", match));
			replacement.add(String.format("(%s/raw%s/%s)", ctx.getBaseUrl(),
					ctx.getDirectory(), match));
		}
		return StringUtils.replaceEach(content,
				toBeReplaced.toArray(new String[0]),
				replacement.toArray(new String[0]));
	}

	public String process(final String page, final InputStream inputStream) {
		try (InputStream in = inputStream) {
			return process(page, IOUtils.toString(in));
		} catch (final IOException e) {
			log.error("Failed to read markdown from input stream.", e);
		}
		return "Internal error";
	}
}
