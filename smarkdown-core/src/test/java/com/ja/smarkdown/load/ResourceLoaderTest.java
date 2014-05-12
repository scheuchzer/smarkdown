package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.net.MalformedURLException;
import java.util.Arrays;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

@RunWith(MockitoJUnitRunner.class)
public class ResourceLoaderTest {

	@Inject
	private Event<LoadEvent> loadEvent;

	@Inject
	private SmarkdownConfiguration config;

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private ResourceLoader loader;

	@Test
	public void testLoadDocumentNothingFound() throws MalformedURLException {
		final Location cpLoc = Location.create("classpath:");
		final Location fileLoc = Location.create("file:///var/tmp");
		doReturn(Arrays.asList(cpLoc, fileLoc)).when(config).getLocations();
		final ResourceInfo result = loader.loadResource("foo.md");
		verify(loadEvent, times(1)).fire(new LoadEvent("classpath:/foo.md"));
		verify(loadEvent, times(1)).fire(
				new LoadEvent("file:///var/tmp/foo.md"));
		assertThat(result, is(nullValue()));
		verifyNoMoreInteractions(loadEvent);
	}

	@Test
	public void testLoadDocumentMountPoint() throws MalformedURLException {
		final Location fileLoc = Location.create("file:///var/tmp");
		fileLoc.setMountPoint("foo/bar");
		doReturn(Arrays.asList(fileLoc)).when(config).getLocations();
		final ResourceInfo result = loader.loadResource("foo/bar/foo.md");
		verify(loadEvent, times(1)).fire(
				new LoadEvent("file:///var/tmp/foo.md"));
		assertThat(result, is(nullValue()));
		verifyNoMoreInteractions(loadEvent);
	}
}
