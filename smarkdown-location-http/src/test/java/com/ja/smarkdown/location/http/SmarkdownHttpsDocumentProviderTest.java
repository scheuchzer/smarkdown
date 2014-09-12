package com.ja.smarkdown.location.http;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
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

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class SmarkdownHttpsDocumentProviderTest extends AbstractWiremockTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
			.port(getHttpPort()).httpsPort(getHttpsPort())
			.notifier(getNotifier()));
	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	@InjectMocks
	private SmarkdownHttpsDocumentProvider provider;

	@Test
	public void testGetDocumentInFolder() throws Exception {
		stubFor(get(urlEqualTo("/test/raw/httptest/test1.md")).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", "text/plain")
						.withBody("#test1\n")));

		final Location location = Location.create(String.format(
				"smarkdown:https://localhost:%s/test", getHttpPort()));
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(new HttpLocation(location)), "httptest/test1.md");
		assertThat(actual, is(notNullValue()));
		assertThat(IOUtils.toString(actual.getInputStream()), is("#test1\n"));
	}
}