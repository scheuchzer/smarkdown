package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.net.MalformedURLException;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.ja.smarkdown.model.MarkdownDocument;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

public class MarkdownLoaderTest {

	@Mock
	@Inject
	private Event<LoadEvent> loadEvent;

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private MarkdownLoader loader;

	@Test
	public void testLoadDocumentNothingFound() throws MalformedURLException {
		MarkdownDocument result = loader.loadDocument("foo.md");
		verify(loadEvent, times(1)).fire(new LoadEvent("classpath:foo.md"));
		verify(loadEvent, times(1)).fire(
				new LoadEvent("classpath:smarkdown/foo.md"));
		verify(loadEvent, times(1)).fire(
				new LoadEvent("file://" + System.getProperty("user.home")
						+ "/smarkdown/foo.md"));
		assertThat(result, is(nullValue()));
		verifyNoMoreInteractions(loadEvent);
	}

}
