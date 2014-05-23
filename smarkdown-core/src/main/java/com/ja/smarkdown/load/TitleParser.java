package com.ja.smarkdown.load;

import org.apache.commons.lang3.StringUtils;

public class TitleParser {

	public static String toTitle(final String name) {
		final String shortName = StringUtils.contains(name, "/") ? StringUtils
				.substringAfterLast(name, "/") : name;
		boolean lastWasLowercase = false;
		boolean forceLowercase = false;
		boolean forceUppercase = false;
		final StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < shortName.length(); i++) {
			if (i == 0) {
				forceUppercase = true;
			}
			final char c = shortName.charAt(i);
			switch (c) {
			case '_':
			case '-':
			case '+':
				tmp.append(' ');
				continue;
			default:
			}

			if (lastWasLowercase && Character.isUpperCase(c)) {
				tmp.append(' ');
				forceLowercase = true;
			}
			lastWasLowercase = Character.isLowerCase(c);

			if (forceLowercase) {
				tmp.append(Character.toLowerCase(c));
				forceLowercase = false;
			} else if (forceUppercase) {
				tmp.append(Character.toUpperCase(c));
				forceUppercase = false;
			} else {
				tmp.append(c);
			}
		}
		return tmp.toString();
	}
}
