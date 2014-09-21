package com.ja.smarkdown.processing.plugins;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.processing.AbstractContentDataProcessor;
import com.ja.smarkdown.processing.Action;
import com.ja.smarkdown.processing.ActionType;
import com.ja.smarkdown.processing.LineContext;
import com.ja.smarkdown.processing.MetaData;

/**
 * Replaces ever occurrence of .md with .html.
 * 
 * @author Thomas Scheuchzer
 *
 */
@Slf4j
public class MarkdownLinksContentDataProcessor extends
		AbstractContentDataProcessor {
	@Override
	public void processLine(String line, LineContext ctx) {
		if (line.contains(".md")) {
			ctx.custom(new Action(this, ActionType.dontCare,
					"will replace content") {
				public String apply(final String line, final MetaData md) {
					if (line != null) {
						log.info("Replacing .md with .html");
						return StringUtils.replace(line, ".md", ".html");
					}
					return line;
				}
			});
		}
	}
}
