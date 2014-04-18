package com.ja.smarkdown;

import com.sun.enterprise.util.StringUtils;

public class MarkdownPreprocessor {

	public String process(final String content) {
		final String result = StringUtils.replace(content, ".md)", ".html)");
		return result;
	}

}
