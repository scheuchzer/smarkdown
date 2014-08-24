package com.ja.smarkdown.location.http;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class HttpDocumentProviderTest {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	@InjectMocks
	private HttpDocumentProvider provider;

	@Test
	public void testGetDocumentInFolder() throws Exception {
		final Location location = Location.create("http://skalender.ch/~celli");
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(new HttpLocation(location)), "httptest/test1.md");
		assertThat(actual, is(notNullValue()));
		assertThat(IOUtils.toString(actual.getInputStream()), is("#test1\n"));
	}

}