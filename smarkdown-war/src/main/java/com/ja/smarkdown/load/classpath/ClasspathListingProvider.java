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
		log.info("Event received. {}", event);
		final String pkg = StringUtils.replace(StringUtils.substringAfter(
				event.getBaseLocation(), "classpath:"), "/", ".");
		final Reflections r = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage(pkg)).addScanners(
						new ResourcesScanner()));

		final Set<String> files = r.getResources(Pattern.compile(".*\\.md"));
		event.addResults(files);
	}

}
