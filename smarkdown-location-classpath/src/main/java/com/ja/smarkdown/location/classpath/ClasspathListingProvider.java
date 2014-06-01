package com.ja.smarkdown.location.classpath;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.vfs.SystemDir;
import org.reflections.vfs.Vfs;
import org.reflections.vfs.Vfs.Dir;
import org.reflections.vfs.Vfs.UrlType;

import com.google.common.base.Predicate;
import com.ja.smarkdown.load.AbstractListingProvider;
import com.ja.smarkdown.load.MountPointUtil;
import com.ja.smarkdown.model.config.Location;

@Slf4j
public class ClasspathListingProvider extends AbstractListingProvider<Location> {

	/*
	 * Hack/Fix for the Resources exception. Handle .so-files and create a
	 * tmp-dir for it so Vfs gets quiet.
	 */
	static {
		final UrlType soType = new UrlType() {

			@Override
			public boolean matches(final URL url) throws Exception {
				return url.getProtocol().equals("file")
						&& url.toExternalForm().endsWith(".so");
			}

			@Override
			public Dir createDir(final URL url) throws Exception {
				final Path tmpDir = Files.createTempDirectory("vfs");
				return new SystemDir(tmpDir.toFile());
			}
		};
		final UrlType generatedDir = new UrlType() {

			@Override
			public boolean matches(final URL url) throws Exception {
				return url.getProtocol().equals("file")
						&& url.toExternalForm().endsWith("/");
			}

			@Override
			public Dir createDir(final URL url) throws Exception {
				final Path tmpDir = Files.createTempDirectory("vfs");
				return new SystemDir(tmpDir.toFile());
			}
		};

		final List<UrlType> types = new ArrayList<>();
		types.add(soType);
		types.add(generatedDir);
		types.addAll(Vfs.getDefaultUrlTypes());
		Vfs.setDefaultURLTypes(types);
	}

	@Override
	protected List<String> getDocuments(final Location location) {
		final List<String> result = new ArrayList<String>();
		final String subDir = StringUtils.substringAfter(location.getUrl(),
				"classpath:");

		final Reflections reflections = new Reflections(
				new ConfigurationBuilder().addUrls(
						ClasspathHelper.forManifest()).addScanners(
						new ResourcesScanner()));
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
