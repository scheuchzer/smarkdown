package com.ja.smarkdown.model.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.inject.Alternative;

import lombok.Data;

@Data
@Alternative
public class SmarkdownConfiguration {

	private String applicationName = "Smarkdown";

	private PagesConfiguration pages = new PagesConfiguration();

	private SlidesConfiguration slides = new SlidesConfiguration();

	private Collection<String> locations = new ArrayList<>();

	public SmarkdownConfiguration() {
		locations.add(String.format("file://%s/smarkdown",
				System.getProperty("user.home")));
		locations.add("web-inf:smarkdown/md");
		locations.add("webapp:smarkdown/md");
		locations.add("classpath:");
		locations.add("classpath:smarkdown/md");
	}

}
