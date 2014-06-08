package com.ja.smarkdown.config;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

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
	private Instance<ConfigurationProvider> configurationProviders;
	@Mock
	private ConfigurationProvider configurationProvider;
	@Mock
	private ConfigurationProvider configurationProvider2;
	@Inject
	private Event<ConfigEvent> events;
	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private Configuration configuration;

	@Test
	public void testCreate() {
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().clear();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(configurationProvider).getConfiguration();
		doReturn(configurationProvider).when(configurationProviders).get();

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
	public void testCreateMultipleConfigurationProviders() {
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().clear();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(configurationProvider).getConfiguration();
		doReturn(true).when(configurationProviders).isAmbiguous();
		doReturn(
				Arrays.asList(configurationProvider, configurationProvider2)
						.iterator()).when(configurationProviders).iterator();

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
	public void testCreateNoConfigurationProviders() {
		doReturn(true).when(configurationProviders).isUnsatisfied();

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
		assertThat(actual.getLocations().iterator().next().getUrl(),
				startsWith("file:"));
	}

	@Test
	public void testCreateWithLocationWithoutHandler() {
		final SmarkdownConfiguration config = new SmarkdownConfiguration();
		config.getLocations().clear();
		config.getLocations().add(Location.create("dummy:foo"));
		doReturn(config).when(configurationProvider).getConfiguration();
		doReturn(configurationProvider).when(configurationProviders).get();

		final SmarkdownConfiguration actual = configuration.create();
		assertThat(actual.getLocationHandlers().size(), is(0));
	}

}
