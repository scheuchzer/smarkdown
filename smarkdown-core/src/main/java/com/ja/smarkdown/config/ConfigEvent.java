package com.ja.smarkdown.config;

import lombok.Data;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Data
public class ConfigEvent {

	private final SmarkdownConfiguration config;
	private final Location location;
	private LocationHandler handler;
}
