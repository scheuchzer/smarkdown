package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.model.config.SmarkdownConfiguration;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

@RunWith(MockitoJUnitRunner.class)
public class ResourceLoaderTest {

	@Inject
	private SmarkdownConfiguration config;

	@Mock
	private LocationHandler handler1;

	@Mock
	private LocationHandler handler2;

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private ResourceLoader loader;

	@Test
	public void testLoadDocumentNothingFound() throws MalformedURLException {
		doReturn(Arrays.asList(handler1, handler2)).when(config)
				.getLocationHandlers();
		final ResourceInfo result = loader.loadResource("foo.md");
		verify(handler1, times(1)).loadDocument(eq("foo.md"));
		verify(handler2, times(1)).loadDocument(eq("foo.md"));
		assertThat(result, is(nullValue()));
		verifyNoMoreInteractions(handler1, handler2);
	}

	@Test
	public void testLoadDocumentHandler2Successful()
			throws MalformedURLException {
		doReturn(Arrays.asList(handler1, handler2)).when(config)
				.getLocationHandlers();
		final ResourceInfo doc = new ResourceInfo("foo.md",
				Location.create("foo://bar"), new ByteArrayInputStream(
						"".getBytes()));
		doReturn(doc).when(handler2).loadDocument(eq("foo.md"));

		final ResourceInfo result = loader.loadResource("foo.md");
		assertThat(result, is(notNullValue()));
	}

	@Test
	public void testLoadDocumentBothSuccessfulDuplicateCheckEnabled()
			throws MalformedURLException {
		doReturn(Arrays.asList(handler1, handler2)).when(config)
				.getLocationHandlers();
		final ResourceInfo doc1 = new ResourceInfo("foo.md",
				Location.create("foo://bar"), new ByteArrayInputStream(
						"".getBytes()));
		final ResourceInfo doc2 = new ResourceInfo("foo.md",
				Location.create("foo://bar"), new ByteArrayInputStream(
						"".getBytes()));
		doReturn(doc1).when(handler1).loadDocument(eq("foo.md"));
		doReturn(doc2).when(handler2).loadDocument(eq("foo.md"));

		final ResourceInfo result = loader.loadResource("foo.md", true);
		assertThat(result, is(notNullValue()));
		assertThat(result.getOverridden().size(), is(1));
	}

	@Test
	public void testLoadDocumentFirstThrowsExceptionHandler2Successful()
			throws MalformedURLException {
		doReturn(Arrays.asList(handler1, handler2)).when(config)
				.getLocationHandlers();
		final ResourceInfo doc = new ResourceInfo("foo.md",
				Location.create("foo://bar"), new ByteArrayInputStream(
						"".getBytes()));
		doThrow(new RuntimeException("forced")).when(handler1).loadDocument(
				anyString());
		doReturn(doc).when(handler2).loadDocument(eq("foo.md"));

		final ResourceInfo result = loader.loadResource("foo.md");
		assertThat(result, is(notNullValue()));
	}
}
