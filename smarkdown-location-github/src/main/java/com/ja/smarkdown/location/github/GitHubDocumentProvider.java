package com.ja.smarkdown.location.github;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.lang3.StringUtils;
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
		GitHub github = null;
		GHRepository repo = null;
		GHBranch branch = null;
		try {
			github = location.open();
			repo = github.getRepository(location.getRepoName());
			log.debug("Using branch={}", location.getBranch());
			branch = repo.getBranches().get(location.getBranch());
			final GHContent content = repo.getFileContent(path,
					branch.getName());
			final byte[] data = Base64.decodeBase64(content.getEncodedContent()
					.getBytes());
			return new ResourceInfo(path, location, new ByteArrayInputStream(
					data));
		} catch (final FileNotFoundException e) {
			throw e;
		} catch (final IOException e) {
			if (e.getMessage().contains("too_large")) {
				return getLargeResource(github, repo, branch, location, path);
			}
			throw new FileNotFoundException(
					"Could not locate the file at path " + path + ".  e");
		}
	}

	private ResourceInfo getLargeResource(GitHub github, GHRepository repo,
			GHBranch branch, GitHubLocation location, String path)
			throws FileNotFoundException {
		log.info("Loading large file.");
		try {
			String dir = StringUtils.substringBeforeLast(path, "/");
			String fileName = StringUtils.substringAfterLast(path, "/");
			StringUtils.trimToEmpty(dir);
			if (fileName == null) {
				fileName = path;
			}
			List<GHContent> directoryContent = repo.getDirectoryContent(dir);
			for (GHContent content : directoryContent) {
				if (content.getName().equals(fileName)) {
					String repoName = StringUtils.substringAfter(repo.getUrl(),
							"github.com/");

					String blobUrl = String.format(
							"https://api.github.com/repos/%s/git/blobs/%s",
							repoName, content.getSha());
					log.info("Blob url={}", blobUrl);
					URL url = new URL(blobUrl);
					URLConnection urlConnection = url.openConnection();

					if (location.getAuthToken() != null) {
						String authString = location.getAuthToken()
								+ ":x-oauth-basic";
						byte[] authEncBytes = Base64.encodeBase64(authString
								.getBytes());
						String authStringEnc = new String(authEncBytes);
						urlConnection.setRequestProperty("Authorization",
								"Basic " + authStringEnc);
					}
					ContentInputStream is = new ContentInputStream(
							urlConnection.getInputStream());
					is.init();

					return new ResourceInfo(path, location,
							new Base64InputStream(is));

				}
			}
		} catch (IOException e) {
			throw new FileNotFoundException(
					"Could not locate the file at path " + path + ".  e");
		}
		return null;
	}

	@Override
	protected String getRootPath(final GitHubLocation location) {
		final String rootPath = super.getRootPath(location);
		return StringUtils.substringAfter(rootPath, ":");
	}

}
