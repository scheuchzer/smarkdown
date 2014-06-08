package com.ja.smarkdown.model.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.inject.Alternative;

import lombok.Data;

import com.ja.smarkdown.model.LocationHandler;

@Data
@Alternative
public class SmarkdownConfiguration {

	private String applicationName = "Smarkdown";

	private PagesConfiguration pages = new PagesConfiguration();

	private SlidesConfiguration slides = new SlidesConfiguration();

	private Collection<Location> locations = new ArrayList<>();

	private Collection<LocationHandler> locationHandlers = new ArrayList<>();

}
