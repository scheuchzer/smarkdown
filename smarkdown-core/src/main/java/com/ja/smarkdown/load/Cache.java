package com.ja.smarkdown.load;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.model.config.Location;

@Slf4j
public class Cache {

	private final Map<Location, Cache.Entry> data = new HashMap<>();

	public synchronized List<String> get(final Location key) {
		Cache.Entry entry = data.get(key);
		if (entry != null) {
			if (entry.isOutdated()) {
				log.info("Clearing cache. url={}, cacheDuration={}", key.getUrl(), key.getCacheDuration());
				entry = null;
				data.remove(key);
			}
		}
		return entry == null ? null : entry.getValue();
	}

	public synchronized void put(final Location key, List<String> value) {
		Cache.Entry entry = data.get(key);
		if (entry == null) {
			data.put(key, new Cache.Entry(key.getCacheDuration(), value));
		}
	}

	@Data
	static class Entry {
		private final long cacheDuration;
		private final long creation = System.currentTimeMillis();
		private final List<String> value;

		public boolean isOutdated() {
			final long now = System.currentTimeMillis();
			final long age = now - creation;
			if (age > cacheDuration) {
				log.debug("Entry is outdated. age={}, cacheDuration={}", age, cacheDuration);
				return true;
			} else {
				log.debug("Entry still valid. age={}, cacheDuration={}", age, cacheDuration);
				return false;
			}
		}
	}

}
