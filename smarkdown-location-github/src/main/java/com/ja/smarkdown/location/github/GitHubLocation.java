package com.ja.smarkdown.location.github;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GitHub;

import com.ja.smarkdown.model.config.Location;

@Slf4j
public class GitHubLocation extends Location {

	private static final String PREFIX = "github:";

	public enum Properties {
		authToken, branch
	}

	public GitHubLocation(final Location location) {
		setConfig(location.getConfig());
		setMountPoint(location.getMountPoint());
		setUrl(location.getUrl());
		if (location.getCacheDuration() == 0) {
			setCacheDuration(TimeUnit.MINUTES.toMillis(60));
		}
	}

	public boolean isAcceptable() {
		return getUrl().startsWith(PREFIX);
	}

	public String getRepoName() {
		final String tmp = StringUtils.substringAfter(getUrl(), PREFIX);
		if (StringUtils.contains(tmp, ":")) {
			return StringUtils.substringBefore(tmp, ":");
		} else {
			return tmp;
		}
	}

	public String getPath() {
		return StringUtils.trimToEmpty(StringUtils.substringAfter(getUrl(),
				getRepoName() + ":"));
	}

	public String getBranch() {
		final String branch = getConfig().get(Properties.branch.toString());
		return branch == null ? "master" : branch;
	}

	public String getAuthToken() {
		return getConfig().get(Properties.authToken.toString());
	}

	public GitHub open() throws IOException {
		if (getAuthToken() == null) {
			log.warn("Annonymous login for location={}. You will only have 60 requests per hour! This might not be enough to read the content listing!");
			return GitHub.connectAnonymously();
		} else {
			log.info("Connecting using authToken");
			return GitHub.connectUsingOAuth(getAuthToken());
		}
	}
}
