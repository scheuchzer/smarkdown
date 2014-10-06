package com.ja.smarkdown.load;

import javax.enterprise.event.Observes;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.config.ConfigEvent;
import com.ja.smarkdown.model.LocationHandler;
import com.ja.smarkdown.model.config.Location;

@Slf4j
@Data
public abstract class AbstractLocationHandlerConfigurator {

	private final String prefix;

	public void configure(@Observes final ConfigEvent event) {
		log.debug("ConfigEvent received. Location={}, {}", event.getLocation(),
				this);
		final Location location = event.getLocation();
		if (location.getUrl().startsWith(prefix)) {
			final LocationHandler handler = getHandler();
			log.debug("I might be able to handle this. {}", this);
			handler.add(location);
			event.setHandler(handler);
		} else {
			log.debug("Nope. {}", this);
		}
	}

	abstract protected LocationHandler getHandler();

}
