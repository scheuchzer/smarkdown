package com.ja.smarkdown.location.github;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.Listing;

@Slf4j
public class GitHubListingProvider extends
		AbstractListingProvider<GitHubLocation> {

	@Inject
	private ListingParser parser;

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

	@Override
	protected void readDocumentsFromListingFile(GitHubLocation location,
			List<String> documents, String listingFileName) {
		try {
			final GHRepository repo = openRepo(location);
			final GHBranch branch = selectBranch(location, repo);
			final GHContent content = repo.getFileContent(listingFileName,
					branch.getName());
			final byte[] data = Base64.decodeBase64(content.getEncodedContent()
					.getBytes());
			try {
				try (Reader in = new InputStreamReader(
						new ByteArrayInputStream(data))) {
					final Listing listing = parser.parse(in);
					for (final String file : listing.getFiles()) {
						documents.add(MountPointUtil.apply(location, file));
					}
				}
			} catch (Exception e) {
				log.debug(
						"Could not read listing file. That's propably ok if it's missing. msg={}, file={}",
						e.getMessage(), listingFileName);
			}

		} catch (final Exception e) {
			log.error("failed", e);
		}

	}

	@Override
	protected void readDocuments(GitHubLocation location, List<String> documents) {
		final List<String> result = new ArrayList<String>();
		try {
			final GHRepository repo = openRepo(location);
			final GHBranch branch = selectBranch(location, repo);
			listRepo(result, location, repo, branch, location.getPath());

		} catch (final Exception e) {
			log.error("failed", e);
		}
		log.info("End listing. Found {} documents.", result.size());
		documents.addAll(result);
	}

	private GHBranch selectBranch(GitHubLocation location,
			final GHRepository repo) throws IOException {
		log.info("Using branch={}", location.getBranch());
		final GHBranch branch = repo.getBranches().get(location.getBranch());
		return branch;
	}

	private GHRepository openRepo(GitHubLocation location) throws IOException {
		final GitHub github = location.open();
		final GHRepository repo = github.getRepository(location.getRepoName());
		return repo;
	}
}
