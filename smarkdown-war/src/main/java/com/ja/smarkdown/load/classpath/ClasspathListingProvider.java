package com.ja.smarkdown.load.classpath;

import java.util.Set;
import java.util.regex.Pattern;

import javax.enterprise.event.Observes;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.ja.smarkdown.load.ListEvent;

@Slf4j
public class ClasspathListingProvider {

	public void onEvent(@Observes final ListEvent event) {
		log.debug("Event received. {}", event);
		final String subDir = StringUtils.substringAfter(event.getLocation()
				.getUrl(), "classpath:");
		final String pkg = StringUtils.replace(subDir, "/", ".");
		final Reflections r = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage(pkg)).addScanners(
						new ResourcesScanner()));

		final Set<String> files = r.getResources(Pattern.compile(".*\\.md"));

		final String exclude = "smarkdown/md";
		for (final String file : files) {
			String name = StringUtils.substringAfter(file, subDir);
			name = StringUtils.stripStart(name, "/");
			if (StringUtils.isNotEmpty(name) && !name.startsWith(exclude)) {
				event.addResult(name);
			}
		}
	}
}
