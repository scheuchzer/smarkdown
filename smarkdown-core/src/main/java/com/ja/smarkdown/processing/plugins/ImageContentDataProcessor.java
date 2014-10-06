package com.ja.smarkdown.processing.plugins;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.processing.AbstractContentDataProcessor;
import com.ja.smarkdown.processing.Action;
import com.ja.smarkdown.processing.ActionType;
import com.ja.smarkdown.processing.LineContext;
import com.ja.smarkdown.processing.MetaData;

/**
 * Change the image path of local images so that they're loaded from the /raw/
 * path.
 * 
 * @author Thomas Scheuchzer
 *
 */
@Slf4j
public class ImageContentDataProcessor extends AbstractContentDataProcessor {
	@Override
	public void processLine(String line, LineContext ctx) {
		/*
		 * Img Syntax=![alt](url)
		 * 
		 * (?!http) = not http, (?!/) = not /
		 */
		final Pattern p = Pattern.compile("\\!\\[.*\\(((?!http)(?!/).*)\\)");
		final Matcher m = p.matcher(line);
		while (m.find()) {
			final String match = m.group(1);
			final String from = String.format("(%s)", match);
			final String to = String.format("(%s/raw%s/%s)", getMetaData()
					.getRequestInfo().getBaseUrl(), getMetaData()
					.getRequestInfo().getDirectory(), match);

			ctx.custom(new Action(this, ActionType.dontCare,
					"will replace content") {
				public String apply(final String line, final MetaData md) {
					if (line != null) {
						log.debug("Replacing from={}, to={}", from, to);
						return StringUtils.replace(line, from, to);
					}
					return line;
				}
			});
		}
	}
}
