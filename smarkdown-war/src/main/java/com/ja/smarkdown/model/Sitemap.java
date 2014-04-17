package com.ja.smarkdown.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.util.LowerCaseStringComparator;

@Data
public class Sitemap {

	private final int level;
	private final String name;

	private List<ListingDocument> pages = new ArrayList<>();

	private Map<String, Sitemap> sections = new HashMap<>();

	public Sitemap() {
		level = 0;
		name = null;
	}

	public Sitemap(final String name, final int level) {
		this.name = name;
		this.level = level;
	}

	public void addAll(final Collection<ListingDocument> pages) {
		for (final ListingDocument page : pages) {
			add(page);
		}
	}

	public void add(final ListingDocument page) {
		final String[] tokens = StringUtils.split(page.getName(), "/");
		add(tokens, page);
	}

	private void add(final String[] tokens, final ListingDocument page) {
		if (tokens.length == 1) {
			pages.add(page);
		} else {
			final String sectionName = tokens[0];
			final Sitemap section = getSection(sectionName);
			section.add(Arrays.copyOfRange(tokens, 1, tokens.length), page);
		}
	}

	private Sitemap getSection(final String sectionName) {
		Sitemap section = sections.get(sectionName);
		if (section == null) {
			section = new Sitemap(sectionName, getNextLevel());
			sections.put(sectionName, section);
		}
		return section;
	}

	private int getNextLevel() {
		return level + 1;
	}

	public void toString(final StringBuilder buf) {
		if (level < 2) {
			buf.append('\n');
		}
		if (StringUtils.isNotEmpty(name)) {
			indentName(buf);
			buf.append(name).append("\n");
			if (level > 0) {
				buf.append('\n');
			}
		}
		for (final ListingDocument p : pages) {
			indent(buf);
			buf.append(String.format("- [%s](%s.html)\n", p.getTitle(),
					p.getUrlEncodedName()));
		}
		final List<String> keys = new ArrayList<>(sections.keySet());
		Collections.sort(keys, new LowerCaseStringComparator());
		for (final String key : keys) {
			sections.get(key).toString(buf);
		}
	}

	private void indent(final StringBuilder buf) {
		for (int i = 0; i < level * 2; i++) {
			buf.append(' ');
		}

	}

	private void indentName(final StringBuilder buf) {
		if (level > 0) {
			for (int i = 0; i < (level - 1) * 2; i++) {
				buf.append(' ');
			}
			if (level > 1) {
				buf.append("- ");
			}
		}

	}

	@Override
	public String toString() {
		final StringBuilder buf = new StringBuilder();
		toString(buf);
		// TODO: fix the new line stuff
		final String result = StringUtils.stripStart(buf.toString(), "\n");
		return StringUtils.replace(result, "\n\n\n", "\n\n");
	}
}
