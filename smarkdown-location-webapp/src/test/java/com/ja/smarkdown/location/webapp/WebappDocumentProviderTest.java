package com.ja.smarkdown.location.webapp;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;

import javax.servlet.ServletContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.location.webapp.WebappDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class WebappDocumentProviderTest {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	@Mock
	private ServletContext servletContext;

	@InjectMocks
	private WebappDocumentProvider provider;

	@Test
	public void testGetDocumentInRoot() throws Exception {
		doReturn(temp.newFile().toURI().toURL()).when(servletContext)
				.getResource(eq("/index.md"));
		final Location location = Location.create("webapp:");
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "index.md");
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getInputStream(), is(notNullValue()));
	}

	@Test
	public void testGetDocumentInFolder() throws Exception {
		doReturn(temp.newFile().toURI().toURL()).when(servletContext)
				.getResource(eq("/md/index.md"));
		final Location location = Location.create("webapp:md");
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "index.md");
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getInputStream(), is(notNullValue()));
	}
}
