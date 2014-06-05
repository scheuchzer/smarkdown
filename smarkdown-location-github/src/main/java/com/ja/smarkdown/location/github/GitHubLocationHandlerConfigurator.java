package com.ja.smarkdown.location.github;

import javax.inject.Inject;

import com.ja.smarkdown.load.AbstractLocationHandlerConfigurator;
import com.ja.smarkdown.model.LocationHandler;

public class GitHubLocationHandlerConfigurator extends
		AbstractLocationHandlerConfigurator {
	public GitHubLocationHandlerConfigurator() {
		super("github:");
	}

	@Inject
	private GitHubLocationHandler handler;

	@Override
	protected LocationHandler getHandler() {
		return handler;
	}
}
