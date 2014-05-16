package com.ja.smarkdown.location.classpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.config.Location;

public class ClasspathListingProvider extends AbstractListingProvider<Location> {

	@Override
	protected List<String> getDocuments(final Location location) {
		final List<String> result = new ArrayList<String>();
		final String subDir = StringUtils.substringAfter(location.getUrl(),
				"classpath:");
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
				result.add(MountPointUtil.apply(location, name));
			}
		}
		return result;
	}

}
