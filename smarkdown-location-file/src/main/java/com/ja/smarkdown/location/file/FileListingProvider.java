package com.ja.smarkdown.location.file;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.Listing;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public class FileListingProvider extends AbstractListingProvider<Location> {

	private static final String PROTOCOL = "file://";

	@Inject
	private ListingParser parser;

	@Override
	protected void readDocumentsFromListingFile(Location location,
			List<String> documents, String listingFileName) {
		URL listingFile = null;
		try {
			listingFile = new URL(String.format("%s/%s", location.getUrl(),
					listingFileName));
			try (Reader in = new InputStreamReader(listingFile.openStream())) {
				final Listing listing = parser.parse(in);
				for (final String file : listing.getFiles()) {
					documents.add(MountPointUtil.apply(location, file));
				}
			}
		} catch (Exception e) {
			log.debug(
					"Could not read listing file. That's propably ok if it's missing. msg={}, file={}",
					e.getMessage(), listingFile);
		}
	}

	@Override
	protected void readDocuments(final Location location,
			final List<String> documents) {
		final List<String> result = new ArrayList<String>();
		try {
			final String base = StringUtils.removeStart(location.getUrl(),
					PROTOCOL);
			Files.walkFileTree(Paths.get(base), new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(final Path file,
						final BasicFileAttributes attrs) throws IOException {
					log.debug("Visiting {}", file);
					if (file.toString().endsWith(".md")) {
						log.debug("accepting file={}", file);
						result.add(MountPointUtil.apply(location,
								StringUtils.removeStart(file.toString(), base)));
					}
					return super.visitFile(file, attrs);
				}
			});
		} catch (final IOException e) {
			log.error("failed", e);
		}
		documents.addAll(result);
	}

}
