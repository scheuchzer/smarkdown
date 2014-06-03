package com.ja.smarkdown.location.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;

@Slf4j
public class GitHubListingProvider extends
		AbstractListingProvider<GitHubLocation> {

	@Override
	protected List<String> getDocuments(final GitHubLocation location) {
		final List<String> documents = new ArrayList<String>();
		try {
			final GitHub github = location.open();
			final GHRepository repo = github.getRepository(location
					.getRepoName());
			log.info("Using branch={}", location.getBranch());
			final GHBranch master = repo.getBranches()
					.get(location.getBranch());
			listRepo(documents, location, repo, master, location.getPath());

		} catch (final Exception e) {
			log.error("failed", e);
		}
		log.info("End listing. Found {} documents.", documents.size());
		return documents;
	}

	private void listRepo(final List<String> documents,
			final GitHubLocation location, final GHRepository repo,
			final GHBranch branch, final String path) throws IOException {
		final List<GHContent> content = repo.getDirectoryContent(path,
				branch.getName());
		for (final GHContent c : content) {
			switch (c.getType()) {
			case "file":
				handleFile(documents, location, c);
				break;
			case "dir":
				handleDir(documents, location, repo, branch, c);
				break;
			default:
				log.info("ignoring {}", c.getName());
			}
		}
	}

	private void handleDir(final List<String> documents,
			final GitHubLocation location, final GHRepository repo,
			final GHBranch branch, final GHContent c) throws IOException {
		log.debug("handle dir={}", c.getPath());
		listRepo(documents, location, repo, branch, encode(c.getPath()));

	}

	private String encode(final String path) {
		return StringUtils.replace(path, " ", "%20");
	}

	private void handleFile(final List<String> documents,
			final GitHubLocation location, final GHContent c) {
		log.debug("handle file={}", c.getPath());
		if (StringUtils.endsWith(c.getName(), "md")) {
			final String name = StringUtils.substringAfter(c.getPath(),
					location.getPath());
			final String listingName = MountPointUtil.apply(location, name);
			documents.add(listingName);
		}

	}
}
