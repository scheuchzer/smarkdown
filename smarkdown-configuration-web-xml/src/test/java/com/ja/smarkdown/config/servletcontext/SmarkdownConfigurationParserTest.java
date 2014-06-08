package com.ja.smarkdown.config.servletcontext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.junit.Test;

import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

public class SmarkdownConfigurationParserTest {

	@Test
	public void testRead() {
		final StringReader in = new StringReader(
				"{\"applicationName\":\"foobarApp\",\"slides\":{\"theme\":\"barTheme\"},\"locations\":[{\"url\":\"classpath:/\"},{\"url\":\"file:///\",\"config\":{\"mountPoint\":\"test\"}}]}");
		final SmarkdownConfiguration config = new SmarkdownConfigurationParser()
				.parse(in);
		assertThat(config.getApplicationName(), is("foobarApp"));
		assertThat(config.getPages().getTheme(), is("bootstrap"));
		assertThat(config.getSlides().getTheme(), is("barTheme"));
		assertThat(config.getSlides().getTransition(), is("default"));
		assertThat(config.getLocations().size(), is(2));
		final Iterator<Location> it = config.getLocations().iterator();
		Location loc = it.next();
		assertThat(loc.getUrl(), is("classpath:/"));
		assertThat(loc.getConfig().size(), is(1));
		assertThat(
				loc.getConfig().get(
						Location.Properties.cacheDuration.toString()),
				is("1 MINUTES"));
		assertThat(loc.getCacheDuration(), is(60000L));
		loc = it.next();
		assertThat(loc.getUrl(), is("file:///"));
		assertThat(loc.getConfig().isEmpty(), is(false));
		assertThat(loc.getConfig().get("mountPoint"), is("test"));
	}

	@Test
	public void testWrite() throws Exception {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final SmarkdownConfigurationParser parser = new SmarkdownConfigurationParser();

		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.setApplicationName("foobarApp");
		config.getPages().setTheme("fooTheme");
		config.getSlides().setTheme("barTheme");
		config.getLocations().add(Location.create("classpath:/"));
		final Location loc2 = Location.create("file:///");
		loc2.getConfig().put("mountPoint", "test");
		config.getLocations().add(loc2);

		parser.write(config, out);
		final String result = out.toString();
		System.out.println(result);
	}

	@Test
	public void testWriteDefault() throws Exception {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final SmarkdownConfigurationParser parser = new SmarkdownConfigurationParser();
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		parser.write(config, out);
		final String result = out.toString();
		System.out.println(result);
	}
}
