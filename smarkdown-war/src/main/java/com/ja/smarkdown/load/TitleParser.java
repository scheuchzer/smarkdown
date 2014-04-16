package com.ja.smarkdown.load;

import org.apache.commons.lang3.StringUtils;

public class TitleParser {

	public static String toTitle(final String name) {
		final String shortName = StringUtils.contains(name, "/") ? StringUtils
				.substringAfterLast(name, "/") : name;
		boolean lastWasLowercase = false;
		boolean forceUppercase = true;
		final StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < shortName.length(); i++) {
			final char c = shortName.charAt(i);
			switch (c) {
			case '_':
			case '-':
			case '+':
				tmp.append(' ');
				forceUppercase = true;
				continue;
			default:
			}

			if (lastWasLowercase && Character.isUpperCase(c)) {
				tmp.append(' ');
			}
			lastWasLowercase = Character.isLowerCase(c);

			if (forceUppercase) {
				tmp.append(Character.toUpperCase(c));
				forceUppercase = false;
			} else {
				tmp.append(c);
			}
		}
		return tmp.toString();
	}
}
