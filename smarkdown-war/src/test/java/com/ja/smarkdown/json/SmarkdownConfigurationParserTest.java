package com.ja.smarkdown.json;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.junit.Test;

import com.ja.smarkdown.model.config.SmarkdownConfiguration;

public class SmarkdownConfigurationParserTest {

	@Test
	public void testRead() {
		final StringReader in = new StringReader(
				"{\"applicationName\":\"foobarApp\",\"slides\":{\"theme\":\"barTheme\"},\"locations\":[\"classpath:/\"]}");
		final SmarkdownConfiguration config = new SmarkdownConfigurationParser()
				.parse(in);
		assertThat(config.getApplicationName(), is("foobarApp"));
		assertThat(config.getPages().getTheme(), is("bootstrap"));
		assertThat(config.getSlides().getTheme(), is("barTheme"));
		assertThat(config.getSlides().getTransition(), is("default"));
		assertThat(config.getLocations().size(), is(1));
		assertThat(config.getLocations().iterator().next(), is("classpath:/"));
	}

	@Test
	public void testReadDefault() {
		final StringReader in = new StringReader("{}");
		final SmarkdownConfiguration config = new SmarkdownConfigurationParser()
				.parse(in);
		assertThat(config.getApplicationName(), is("Smarkdown"));
		assertThat(config.getPages().getTheme(), is("bootstrap"));
		assertThat(config.getSlides().getTheme(), is("sky"));
		assertThat(config.getSlides().getTransition(), is("default"));
		assertThat(config.getLocations().size(), is(5));
		assertThat(config.getLocations().contains("classpath:"), is(true));
	}

	@Test
	public void testWrite() throws Exception {

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final SmarkdownConfigurationParser parser = new SmarkdownConfigurationParser();

		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.setApplicationName("foobarApp");
		config.getPages().setTheme("fooTheme");
		config.getSlides().setTheme("barTheme");
		config.getLocations().add("classpath:/");

		parser.write(config, out);
		final String result = out.toString();
		System.out.println(result);
	}
}
