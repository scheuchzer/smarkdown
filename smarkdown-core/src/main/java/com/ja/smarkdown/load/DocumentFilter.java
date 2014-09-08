package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.config.Location;

@Slf4j
public class DocumentFilter {

	public List<String> filter(final List<String> documents,
			final Location location) {
		final List<String> includesMatches = new ArrayList<String>();

		final List<Pattern> includes = createPatterns(location.getIncludes());
		final List<Pattern> excludes = createPatterns(location.getExcludes());

		for (String document : documents) {
			for (Pattern p : includes) {
				if (p.matcher(document).matches()) {
					log.debug("accepting document={}", document);
					includesMatches.add(document);
					break;
				}
			}
		}

		final List<String> matches = new ArrayList<String>();
		if (excludes.isEmpty()) {
			matches.addAll(includesMatches);
		} else {
			for (String document : includesMatches) {
				boolean accept = true;
				for (Pattern p : excludes) {
					if (p.matcher(document).matches()) {
						accept = false;
						break;
					}
				}
				if (accept) {
					matches.add(document);
				} else {
					log.debug("excluding document={}", document);
				}

			}
		}
		return matches;
	}

	private List<Pattern> createPatterns(List<String> regexps) {
		final List<Pattern> patterns = new ArrayList<Pattern>();
		for (String regexp : regexps) {
			try {
				Pattern p = Pattern.compile(regexp);
				patterns.add(p);
			} catch (PatternSyntaxException e) {
				log.error(
						"Invalid include/exclude expression. Ignoring it. Expression={}",
						regexp);
				continue;
			}
		}
		return patterns;
	}
}
