package com.ja.smarkdown.config.servletcontext;

import static com.ja.smarkdown.model.config.Location.create;

import javax.enterprise.inject.Alternative;

import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Alternative
public class DefaultSmarkdownConfiguration extends SmarkdownConfiguration {

	public DefaultSmarkdownConfiguration() {
		super();
		getLocations().add(
				create(String.format("file://%s/smarkdown",
						System.getProperty("user.home"))));
		getLocations().add(create("web-inf:smarkdown/md"));
		getLocations().add(create("webapp:smarkdown/md"));
		getLocations().add(create("classpath:"));
	}
}
