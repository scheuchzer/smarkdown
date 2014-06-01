package com.ja.smarkdown.location.classpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;

import com.google.common.base.Predicate;
import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public class ClasspathListingProvider extends AbstractListingProvider<Location> {

	@Override
	protected List<String> getDocuments(final Location location) {
		final List<String> result = new ArrayList<String>();
		final String subDir = StringUtils.substringAfter(location.getUrl(),
				"classpath:");
		final Reflections reflections = new Reflections(
				ClasspathHelper.forManifest(), new ResourcesScanner());
		final Set<String> files = reflections.getStore().getResources(
				new Predicate<String>() {

					@Override
					public boolean apply(final String input) {
						return input.toLowerCase().endsWith(".md");
					}
				});

		for (final String file : files) {
			String name = StringUtils.substringAfter(file, subDir);
			name = StringUtils.trimToNull(StringUtils.stripStart(name, "/"));
			if (name != null) {
				log.debug("accepting file={}", name);
				result.add(MountPointUtil.apply(location, name));
			}
		}
		return result;
	}

}
