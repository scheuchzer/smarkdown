package com.ja.smarkdown.jekyll;

import java.util.List;

import com.ja.smarkdown.processing.DefaultLineContext;
import com.ja.smarkdown.processing.LineProcessor;
import com.ja.smarkdown.processing.MetaData;

public class ProcessorHelper {

	public static String processLines(List<String> lines, LineProcessor processor, MetaData metaData) {
		StringBuilder actual = new StringBuilder();
		for (String line : lines) {
			DefaultLineContext ctx = new DefaultLineContext(line, metaData);
			processor.processLine(line, ctx);
			String newLine = ctx.applyActions();
			if (newLine != null) {
				if (actual.length() > 0) {
					actual.append('\n');
				}
				actual.append(newLine);
			}
		}
		return actual.toString();
	}
}
