package com.ja.smarkdown.location.github;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.ja.smarkdown.load.AbstractDocumentProvider;

@Slf4j
public class GitHubDocumentProvider extends
		AbstractDocumentProvider<GitHubLocation> {

	public GitHubDocumentProvider() {
		super("github:", "");
	}

	// @Override
	// protected ResourceInfo getDocument(final GitHubLocation location,
	// final String resource) {
	// ResourceInfo document = null;
	// final String strippedResource = MountPointUtil.remove(location,
	// resource);
	// try {
	// final GitHub github = location.open();
	// final GHRepository repo = github.getRepository(location
	// .getRepoName());
	// log.info("Using branch={}", location.getBranch());
	// final GHBranch branch = repo.getBranches()
	// .get(location.getBranch());
	//
	// final String path = String.format("%s/%s", location.getPath(),
	// strippedResource);
	// log.info("GitHub path={}", path);
	// final GHContent content = repo.getFileContent(path,
	// branch.getName());
	// final String text = content.getContent();
	//
	// document = new ResourceInfo(this.getClass(), resource,
	// new ByteArrayInputStream(text.getBytes()));
	// } catch (final FileNotFoundException e) {
	// log.info("This file is not in my repo. {}", e.getMessage());
	// } catch (final Exception e) {
	// log.error("Can't process this url={}", resource, e);
	// }
	// return document;
	// }

	@Override
	protected InputStream getInputStream(final GitHubLocation location,
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

			return new ByteArrayInputStream(text.getBytes());
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
