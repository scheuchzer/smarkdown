package com.ja.smarkdown.config.servletcontext;

import com.ja.smarkdown.json.AbstractParser;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

public class SmarkdownConfigurationParser extends
		AbstractParser<SmarkdownConfiguration> {

	public SmarkdownConfigurationParser() {
		super(SmarkdownConfiguration.class);
	}
}