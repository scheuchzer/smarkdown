package com.ja.smarkdown.location.github;

import java.io.IOException;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ContentInputStream extends InputStream {

	private final InputStream in;

	private boolean endOfContent;

	private int next;

	public void init() throws IOException {
		String prefixString = "content\":\"";
		char c = 0;
		int pos = 0;
		while ((c = (char) in.read()) != -1) {
			if (c == prefixString.charAt(pos)) {
				pos++;
				if (pos >= prefixString.length()) {
					log.info("Start of content reached.");
					next = in.read();
					return;
				}
			} else {
				pos = 0;
			}
		}
	}

	@Override
	public int read() throws IOException {
		if (endOfContent) {
			return -1;
		}
		int current = next;
		next = in.read();
		// filter out the \n strings in the content
		if ('\\' == (char) current && 'n' == (char) next) {
			current = '\n';
			next = in.read();
		}
		return isEndOfContent(current);
	}

	private int isEndOfContent(int current) {
		if ('"' == (char) current) {
			log.debug("Content end reached.");
			endOfContent = true;
			return -1;
		}
		return current;
	}

	@Override
	public void close() throws IOException {
		super.close();
		in.close();
	}

}
