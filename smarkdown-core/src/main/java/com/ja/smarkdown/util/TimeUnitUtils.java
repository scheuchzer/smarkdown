package com.ja.smarkdown.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public final class TimeUnitUtils {

	private TimeUnitUtils() {

	}

	public static long parseToMillis(final String duration) {
		final String trimmed = StringUtils.trimToEmpty(duration);
		if ("0".equals(trimmed)) {
			return 0;
		}
		final String[] tokens = StringUtils.split(trimmed, " ");
		if (tokens.length != 2) {
			throw new IllegalArgumentException("Duration '" + duration
					+ "' not parsable");
		}
		try {
			final long amount = Long.parseLong(tokens[0]);
			String unitToken = tokens[1].toUpperCase();
			if (!unitToken.endsWith("S")) {
				unitToken = unitToken + "S";
			}
			final TimeUnit unit = TimeUnit.valueOf(unitToken);
			return unit.toMillis(amount);
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
