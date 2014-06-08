package com.ja.smarkdown.config.servletcontext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.io.Reader;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

@RunWith(MockitoJUnitRunner.class)
public class ServletContextConfigurationProviderTest {
	@Inject
	private ServletContext servletContext;
	@Inject
	private final SmarkdownConfigurationParser parser = new SmarkdownConfigurationParser();
	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private ServletContextConfigurationProvider provider;

	@Test
	public void testCreate() {
		final String configString = "{\"locations\":[{\"url\":\"dummy:foo}]}";
		doReturn(configString).when(servletContext).getInitParameter(
				eq("smarkdown.configuration"));
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().clear();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(parser).parse(any(Reader.class));

		final SmarkdownConfiguration actual = provider.getConfiguration();
		assertThat(actual.getLocations().size(), is(1));
		assertThat(actual.getLocations().iterator().next().getUrl(),
				is("dummy:foo"));
	}

}
