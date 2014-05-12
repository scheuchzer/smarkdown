package com.ja.smarkdown.model.config;

import static com.ja.smarkdown.model.config.Location.create;

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

	private Collection<Location> locations = new ArrayList<>();

	public SmarkdownConfiguration() {
		locations.add(create(String.format("file://%s/smarkdown",
				System.getProperty("user.home"))));
		locations.add(create("web-inf:smarkdown/md"));
		locations.add(create("webapp:smarkdown/md"));
		locations.add(create("classpath:"));
		locations.add(create("classpath:smarkdown/md"));
	}

}
