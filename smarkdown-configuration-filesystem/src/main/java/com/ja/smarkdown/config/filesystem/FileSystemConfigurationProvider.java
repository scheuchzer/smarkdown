package com.ja.smarkdown.config.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.config.ConfigurationProvider;
import com.ja.smarkdown.model.config.DefaultSmarkdownConfiguration;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;
import com.ja.smarkdown.model.config.SmarkdownConfigurationParser;

@Slf4j
public class FileSystemConfigurationProvider implements ConfigurationProvider {
	public static final String SMARKDOWN_CFG = "smarkdown.cfg.file";

	@Inject
	private SmarkdownConfigurationParser parser;

	@Override
	public SmarkdownConfiguration getConfiguration() {
		final String configFile = System.getProperty(SMARKDOWN_CFG);
		SmarkdownConfiguration config = null;
		if (StringUtils.isEmpty(configFile)) {
			log.info("No config file defined. Using default configuration.");
			config = new DefaultSmarkdownConfiguration();
		} else {
			final File file = new File(configFile);
			if (file.exists()) {
				log.info("Reaing configuration from file {}.",
						file.getAbsolutePath());
				try (InputStreamReader is = new InputStreamReader(
						new FileInputStream(file))) {
					config = parser.parse(is);
				} catch (Exception e) {
					log.error(
							"Failed to read config file. Falling back to default config",
							e);
					config = new DefaultSmarkdownConfiguration();
				}
			} else {
				log.error(
						"Config file {} not found. Using default configuration.",
						file.getAbsolutePath());
				config = new DefaultSmarkdownConfiguration();
			}
		}

		return config;
	}

}