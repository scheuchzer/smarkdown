package com.ja.smarkdown.config;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.json.SmarkdownConfigurationParser;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@ApplicationScoped
@Slf4j
public class Configuration {

	private static final String SMARKDOWN_CONFIGURATION = "smarkdown.configuration";

	@Inject
	private ServletContext servletContext;

	@Inject
	private SmarkdownConfigurationParser parser;

	private SmarkdownConfiguration config;

	@Produces
	public SmarkdownConfiguration create() {

		if (config == null) {
			final String configString = servletContext
					.getInitParameter(SMARKDOWN_CONFIGURATION);
			log.info("Configuration from web.xml is: {}", configString);

			if (StringUtils.isEmpty(configString)) {
				log.info("Using default configuration.");
				config = new SmarkdownConfiguration();
			} else {
				config = parser.parse(new StringReader(configString));
			}
		}
		return config;
	}
}
