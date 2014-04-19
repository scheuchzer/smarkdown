package com.ja.smarkdown.preprocessing;

public class MarkdownPreprocessor {

	public String process(final String content) {
		final String result = content.replaceAll("\\.md\\)", "\\.html\\)");
		return result;
	}

}
