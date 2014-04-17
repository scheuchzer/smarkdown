package com.ja.smarkdown.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UrlUtils {

	private UrlUtils() {

	}

	public static String encode(final String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			log.error("Encoding failed.", e);
			return value;
		}

	}

	public static String decode(final String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			log.error("Encoding failed.", e);
			return value;
		}
	}

}
