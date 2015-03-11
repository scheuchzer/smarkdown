package com.ja.smarkdown.config.filesystem;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.model.config.DefaultSmarkdownConfiguration;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;
import com.ja.smarkdown.model.config.SmarkdownConfigurationParser;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

@RunWith(MockitoJUnitRunner.class)
public class FileSystemConfigurationProviderTest {
	@Inject
	private final SmarkdownConfigurationParser parser = new SmarkdownConfigurationParser();
	@Rule
	public NeedleRule needleRule = new NeedleRule();
	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();
	@ObjectUnderTest
	private FileSystemConfigurationProvider provider;

	@Test
	public void testGetConfigurationDefault() {
		final SmarkdownConfiguration actual = provider.getConfiguration();
		assertEquals(new DefaultSmarkdownConfiguration(), actual);
	}

	@Test
	public void testGetConfigurationFileNotFoundGetDefault() {
		final String tmp = System
				.getProperty(FileSystemConfigurationProvider.SMARKDOWN_CFG);
		System.setProperty(FileSystemConfigurationProvider.SMARKDOWN_CFG,
				"/var/tmp/nothing.txt");
		try {
			final SmarkdownConfiguration actual = provider.getConfiguration();
			assertEquals(new DefaultSmarkdownConfiguration(), actual);
		} finally {
			if (tmp != null) {
				System.setProperty(
						FileSystemConfigurationProvider.SMARKDOWN_CFG, tmp);
			}
		}
	}

	@Test
	public void testGetConfigurationInvalidConfigGetDefault() throws Exception {
		final String configString = "blabla";
		final File cfgFile = tmp.newFile();
		try (FileOutputStream out = new FileOutputStream(cfgFile)) {
			IOUtils.write(configString, out);
		}

		final String tmp = System
				.getProperty(FileSystemConfigurationProvider.SMARKDOWN_CFG);
		System.setProperty(FileSystemConfigurationProvider.SMARKDOWN_CFG,
				cfgFile.getAbsolutePath());

		doThrow(new RuntimeException("Forced")).when(parser).parse(
				any(Reader.class));

		try {
			final SmarkdownConfiguration actual = provider.getConfiguration();
			assertEquals(new DefaultSmarkdownConfiguration(), actual);
		} finally {
			if (tmp != null) {
				System.setProperty(
						FileSystemConfigurationProvider.SMARKDOWN_CFG, tmp);
			}
		}
	}

	@Test
	public void testGetConfigurationValidConfiguration() throws Exception {
		final String configString = "{\"locations\":[{\"url\":\"dummy:foo}]}";
		final File cfgFile = tmp.newFile();
		try (FileOutputStream out = new FileOutputStream(cfgFile)) {
			IOUtils.write(configString, out);
		}

		final String tmp = System
				.getProperty(FileSystemConfigurationProvider.SMARKDOWN_CFG);
		System.setProperty(FileSystemConfigurationProvider.SMARKDOWN_CFG,
				cfgFile.getAbsolutePath());

		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().clear();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(parser).parse(any(Reader.class));

		try {
			final SmarkdownConfiguration actual = provider.getConfiguration();
			assertThat(actual.getLocations().size(), is(1));
			assertThat(actual.getLocations().iterator().next().getUrl(),
					is("dummy:foo"));
		} finally {
			if (tmp != null) {
				System.setProperty(
						FileSystemConfigurationProvider.SMARKDOWN_CFG, tmp);
			}
		}
	}

}
