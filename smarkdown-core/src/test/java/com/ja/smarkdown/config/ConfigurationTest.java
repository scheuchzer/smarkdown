package com.ja.smarkdown.config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import java.io.Reader;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.ja.smarkdown.json.SmarkdownConfigurationParser;
import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTest {
	@Mock
	private LocationHandler handlerMock;
	@Inject
	private ServletContext servletContext;
	@Inject
	private SmarkdownConfigurationParser parser = new SmarkdownConfigurationParser();
	@Inject
	private Event<ConfigEvent> events;
	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private Configuration configuration;

	@Test
	public void testCreate() {
		final String configString = "{\"locations\":[{\"url\":\"dummy:foo}]}";
		doReturn(configString).when(servletContext).getInitParameter(
				eq("smarkdown.configuration"));
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().clear();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(parser).parse(any(Reader.class));

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(final InvocationOnMock invocation)
					throws Throwable {
				final ConfigEvent event = (ConfigEvent) invocation
						.getArguments()[0];
				event.setHandler(handlerMock);
				return null;
			}
		}).when(events).fire(any(ConfigEvent.class));

		final SmarkdownConfiguration actual = configuration.create();
		assertThat(actual.getLocationHandlers().size(), is(1));
		assertThat(actual.getLocationHandlers().iterator().next(),
				is(handlerMock));
	}

	@Test
	public void testCreateWithLocationWithoutHandler() {
		final String configString = "{\"locations\":[{\"url\":\"dummy:foo}]}";
		doReturn(configString).when(servletContext).getInitParameter(
				eq("smarkdown.configuration"));
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(parser).parse(any(Reader.class));

		final SmarkdownConfiguration actual = configuration.create();
		assertThat(actual.getLocationHandlers().size(), is(0));
	}

}
