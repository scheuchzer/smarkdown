package com.ja.smarkdown.location.github;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.ja.smarkdown.load.AbstractDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;

@Slf4j
public class GitHubDocumentProvider extends
		AbstractDocumentProvider<GitHubLocation> {

	public GitHubDocumentProvider() {
		super("github:", "");
	}

	@Override
	protected ResourceInfo getResource(final GitHubLocation location,
			final String path) throws FileNotFoundException {
		try {
			final GitHub github = location.open();
			final GHRepository repo = github.getRepository(location
					.getRepoName());
			log.info("Using branch={}", location.getBranch());
			final GHBranch branch = repo.getBranches()
					.get(location.getBranch());

			final GHContent content = repo.getFileContent(path,
					branch.getName());
			final String text = content.getContent();

			return new ResourceInfo(path, location, new ByteArrayInputStream(
					text.getBytes()));
		} catch (final FileNotFoundException e) {
			throw e;
		} catch (final IOException e) {
			throw new FileNotFoundException("Could not loate the file at path "
					+ path + ".  e");
		}
	}

	@Override
	protected String getRootPath(final GitHubLocation location) {
		final String rootPath = super.getRootPath(location);
		return StringUtils.substringAfter(rootPath, ":");
	}

}
