package com.ja.smarkdown.location.classpath;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;

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
		/* Fix for Glassfish */
		final UrlType generatedDir = new UrlType() {

			@Override
			public boolean matches(final URL url) throws Exception {
				return url.getProtocol().equals("file")
						&& url.toExternalForm().contains("generated")
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

	@Inject
	private Instance<ServletContext> servletContext;

	/**
	 * JBoss returns URLs with the vfszip and vfsfile protocol for resources,
	 * and the org.reflections library doesn't recognize them. This is more a
	 * bug inside the reflections library, but we can write a small workaround
	 * for a quick fix on our side.
	 */
	private Set<URL> filterURLs(final Set<URL> urls) {
		final Set<URL> results = new HashSet<URL>(urls.size());
		for (final URL url : urls) {
			String cleanURL = url.toString();
			// Fix JBoss URLs
			if (url.getProtocol().startsWith("vfszip:")) {
				cleanURL = cleanURL.replaceFirst("vfszip:", "file:");
			} else if (url.getProtocol().startsWith("vfsfile:")) {
				cleanURL = cleanURL.replaceFirst("vfsfile:", "file:");
			}
			cleanURL = cleanURL.replaceFirst("\\.jar/", ".jar!/");
			try {
				results.add(new URL(cleanURL));
			} catch (final MalformedURLException ex) {
				// Shouldn't happen, but we can't do more to fix this URL.
			}
		}
		return results;
	}

	@Override
	protected void readDocumentsFromListingFile(Location location,
			List<String> documents, String listingFileName) {
		// not supported. Too complicated if we have some jar with listing file
		// and some without.

	}

	@Override
	protected void readDocuments(Location location, List<String> documents) {
		// Do this better if more or more complex excludes
		// are required. Reading from location config would be another
		// enhancement.
		final Set<String> excludes = new HashSet<String>();
		excludes.add("META-INF");

		final List<String> result = new ArrayList<String>();
		final String subDir = StringUtils.substringAfter(location.getUrl(),
				"classpath:");
		final Set<URL> classloaders = ClasspathHelper.forManifest();
		/*
		 * Glassfish works fine with forManifest() only but JBoss needs
		 * forWebInfLib and forWebInfClasses.
		 */
		if (servletContext != null && !servletContext.isUnsatisfied()) {
			log.info("Including ServletContext urls.");
			classloaders.addAll(ClasspathHelper.forWebInfLib(servletContext
					.get()));
			classloaders.add(ClasspathHelper.forWebInfClasses(servletContext
					.get()));
		}
		final Reflections reflections = new Reflections(
				new ConfigurationBuilder().addUrls(filterURLs(classloaders))
						.addScanners(new ResourcesScanner()));
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
			if (accept(name, excludes)) {
				log.debug("accepting file={}", name);
				result.add(MountPointUtil.apply(location, name));
			}
		}

		documents.addAll(result);
	}

	private boolean accept(String name, Set<String> excludes) {
		for (String exclude : excludes) {
			System.out.println(exclude + "    " + name);
			if (name.contains(exclude)) {
				return false;
			}
		}
		return true;
	}

}
