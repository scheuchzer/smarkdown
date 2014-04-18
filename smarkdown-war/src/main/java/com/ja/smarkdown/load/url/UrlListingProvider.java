package com.ja.smarkdown.load.url;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.ListEvent;

@Slf4j
public class UrlListingProvider {
	public void onEvent(@Observes final ListEvent event) {
		log.info("Event received. {}", event);
		if (!event.getBaseLocation().startsWith("file")) {
			return;
		}
		try {
			final String base = StringUtils.removeStart(
					event.getBaseLocation(), "file://");
			Files.walkFileTree(Paths.get(base), new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(final Path file,
						final BasicFileAttributes attrs) throws IOException {
					if (file.toString().endsWith(".md")) {
						event.addResult(StringUtils.removeStart(
								file.toString(), base));
					}
					return super.visitFile(file, attrs);
				}
			});
		} catch (final IOException e) {
			log.error("failed", e);
		}
	}
}
