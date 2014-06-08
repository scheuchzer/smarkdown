package com.ja.smarkdown.config.servletcontext;

import java.io.StringReader;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.config.ConfigurationProvider;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Slf4j
public class ServletContextConfigurationProvider implements
		ConfigurationProvider {
	private static final String SMARKDOWN_CONFIGURATION = "smarkdown.configuration";

	@Inject
	private ServletContext servletContext;
	@Inject
	private SmarkdownConfigurationParser parser;

	@Override
	public SmarkdownConfiguration getConfiguration() {
		final String configString = servletContext
				.getInitParameter(SMARKDOWN_CONFIGURATION);
		log.info("Configuration from web.xml is: {}", configString);

		SmarkdownConfiguration config = null;
		if (StringUtils.isEmpty(configString)) {
			log.info("Using default configuration.");
			config = new DefaultSmarkdownConfiguration();
		} else {
			config = parser.parse(new StringReader(configString));
		}
		return config;
	}

}
