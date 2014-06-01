package com.ja.smarkdown.location.github;

import static org.junit.Assume.assumeNotNull;
import lombok.extern.slf4j.Slf4j;

import org.junit.rules.ExternalResource;

import com.ja.smarkdown.model.config.Location;

@Slf4j
public class AuthTokenRule extends ExternalResource {

	private String authToken;

	@Override
	protected void before() throws Throwable {
		authToken = System.getProperty(GitHubLocation.Properties.authToken
				.toString());
	}

	public void assumeAuthToken() {
		log.info("AuthToken set: {}", authToken != null);
		assumeNotNull(authToken);
	}

	public void setAuthToken(Location location) {
		location.getConfig().put(
				GitHubLocation.Properties.authToken.toString(), authToken);

	}

}
