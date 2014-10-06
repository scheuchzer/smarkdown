package com.ja.smarkdown.jekyll;

import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.processing.AbstractMetaDataProcessor;
import com.ja.smarkdown.processing.LineContext;

@ExtensionMethod(StringUtils.class)
@Slf4j
public class JekyllMetaDataProcessor extends AbstractMetaDataProcessor {

	private boolean started = false;
	private boolean jekyll = false;

	@Override
	public void processLine(String line, LineContext ctx) {
		if (!started && line.startsWith("---")) {
			jekyll = true;
			ctx.remove();
		} else if (started && line.startsWith("---")) {
			jekyll = false;
			ctx.remove();
		} else if (started && jekyll) {
			readProperty(line);
			ctx.remove();
		}

		if (line.startsWith("{%") && line.contains("include")
				&& line.endsWith("%}")) {
			ctx.remove();
		}

		started = true;
	}

	private void readProperty(String line) {
		String key = StringUtils.trimToNull(StringUtils.substringBefore(line,
				":"));
		String value = StringUtils.trimToNull(StringUtils.substringAfter(line,
				":"));
		if (key.isEmpty() && value.isEmpty()) {
			return;
		}
		log.debug("Property key={}, value={}", key, value);
		String prefixedKey = String.format("jekyll.%s", key);
		if (value.startsWith("[")) {
			for (String v : value.split(",")) {
				getMetaData().add(prefixedKey, v.trim().strip("[]"));
			}
		} else {
			String v = value.strip("\"'").trimToNull();
			if (v != null) {
				getMetaData().add(prefixedKey, v);
			}
		}

	}
}
