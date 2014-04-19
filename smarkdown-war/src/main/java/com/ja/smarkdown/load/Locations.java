package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Data
public class Locations {

	private final List<String> locations = new ArrayList<>();

	public Locations() {
		addLocation("web-inf:smarkdown/md");
		addLocation("webapp:smarkdown/md");
		addLocation("classpath:");
		addLocation("classpath:smarkdown");
		addLocation(String.format("file://%s/smarkdown",
				System.getProperty("user.home")));
	}

	private void addLocation(final String location) {
		log.info("Adding location={}", location);
		locations.add(location);
	}
}
