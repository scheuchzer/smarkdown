package com.ja.smarkdown.util;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

public class LowerCaseStringComparator implements Comparator<String> {

	@Override
	public int compare(final String o1, final String o2) {
		return (StringUtils.countMatches(o1, "/") + o1.toLowerCase())
				.compareTo((StringUtils.countMatches(o2, "/") + o2
						.toLowerCase()));
	}

}
