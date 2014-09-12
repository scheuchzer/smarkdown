package com.ja.smarkdown.location.http;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.model.Listing;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class SmarkdownHttpListingProviderTest extends AbstractWiremockTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
			.port(getHttpPort()).httpsPort(getHttpsPort())
			.notifier(getNotifier()));

	@Mock
	private ListingParser parser;
	@InjectMocks
	private SmarkdownHttpListingProvider provider = new SmarkdownHttpListingProvider();

	@Test
	public void testGetDocuments() {
		stubFor(get(urlEqualTo("/test/listing.json")).willReturn(
				aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "text/plain")
						.withBody(
								"{\"files\":[\n" + "\"httptest/test1.md\",\n"
										+ "\"httptest/test2.md\"\n" + "]}")));

		final Location location = Location.create(String.format(
				"smarkdown:http://localhost:%s/test", getHttpPort()));
		final Listing listing = new Listing();
		when(parser.parse(any(Reader.class))).thenAnswer(new Answer<Listing>() {

			@SuppressWarnings("unchecked")
			@Override
			public Listing answer(InvocationOnMock invocation) throws Throwable {
				Reader reader = (Reader) invocation.getArguments()[0];
				List<String> lines = IOUtils.readLines(reader);
				for (String line : lines) {
					if (line.contains(".md")) {
						listing.getFiles().add(
								StringUtils.substringBetween(line, "\""));
					}
				}
				return listing;
			}

		});
		final List<String> actual = provider.getDocuments(Arrays
				.asList(new HttpLocation(location)));

		assertThat(actual.size(), is(2));
		assertTrue(actual.contains("httptest/test1.md"));
		assertTrue(actual.contains("httptest/test1.md"));
	}

	@Test
	public void testGetDocumentsHttps() {
		stubFor(get(urlEqualTo("/test/listing.json")).willReturn(
				aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "text/plain")
						.withBody(
								"{\"files\":[\n" + "\"httptest/test1.md\",\n"
										+ "\"httptest/test2.md\"\n" + "]}")));

		final Location location = Location.create(String.format(
				"smarkdown:https://localhost:%s/test", getHttpsPort()));
		final Listing listing = new Listing();
		when(parser.parse(any(Reader.class))).thenAnswer(new Answer<Listing>() {

			@SuppressWarnings("unchecked")
			@Override
			public Listing answer(InvocationOnMock invocation) throws Throwable {
				Reader reader = (Reader) invocation.getArguments()[0];
				List<String> lines = IOUtils.readLines(reader);
				for (String line : lines) {
					if (line.contains(".md")) {
						listing.getFiles().add(
								StringUtils.substringBetween(line, "\""));
					}
				}
				return listing;
			}

		});
		final List<String> actual = provider.getDocuments(Arrays
				.asList(new HttpLocation(location)));

		assertThat(actual.size(), is(2));
		assertTrue(actual.contains("httptest/test1.md"));
		assertTrue(actual.contains("httptest/test1.md"));
	}

}
