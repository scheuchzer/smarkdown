package com.ja.smarkdown.load;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.model.config.Location;

@Slf4j
public class MountPointUtil {

	public static String apply(final Location location,
			final String documentName) {
		final String strippedDocumentName = StringUtils
				.strip(documentName, "/");
		String mountPoint = location.getConfig().get(
				Location.Properties.mountPoint.toString());
		String result = strippedDocumentName;
		if (!StringUtils.isEmpty(mountPoint)) {
			mountPoint = StringUtils.strip(mountPoint, "/");
			result = String.format("%s/%s", mountPoint, strippedDocumentName);
			log.debug("applying mount point. from={}, to={}", documentName,
					result);
		}
		return result;
	}

	public static String remove(final Location location, final String resource) {
		final String removed = StringUtils.removeStart(resource,
				location.getMountPoint());
		return StringUtils.strip(removed, "/");
	}

}
