package com.ja.smarkdown.util;

public class ToString {

	private final String format;
	private final Object[] values;

	public ToString(final String format, final Object... values) {
		this.format = format;
		this.values = values;
	}

	public String toString() {
		return String.format(format, values);
	}

}
