package com.ja.smarkdown.load.github;

import java.io.IOException;
import java.util.List;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.ja.smarkdown.load.ListEvent;

@Slf4j
public class GitListingProvider {

	public void onEvent(@Observes final ListEvent event) {
		log.debug("Event received. {}", event);
		final GitHubLocation location = new GitHubLocation(event.getLocation());
		if (!location.isAcceptable()) {
			return;
		}
		try {
			final GitHub github = location.open();
			final GHRepository repo = github.getRepository(location
					.getRepoName());
			final GHBranch master = repo.getBranches()
					.get(location.getBranch());
			listRepo(repo, master, "/");

		} catch (final Exception e) {
			log.error("failed", e);
		}
		log.debug("End event.");
	}

	private void listRepo(final GHRepository repo, final GHBranch branch,
			final String path) throws IOException {
		final List<GHContent> content = repo.getDirectoryContent(path,
				branch.getName());
		for (final GHContent c : content) {
			switch (c.getType()) {
			case "file":
				handleFile(c);
				break;
			case "dir":
				handleDir(repo, branch, c);
				break;
			default:
				log.info("ignoring {}", c.getName());
			}
		}
	}

	private void handleDir(final GHRepository repo, final GHBranch branch,
			final GHContent c) throws IOException {
		log.info("handle dir={}", c.getPath());
		listRepo(repo, branch, c.getPath());

	}

	private void handleFile(final GHContent c) {
		log.debug("handle file={}", c.getPath());
		if (StringUtils.endsWith(c.getName(), "md")) {
			System.out.println(c.getPath());
		}

	}
}
