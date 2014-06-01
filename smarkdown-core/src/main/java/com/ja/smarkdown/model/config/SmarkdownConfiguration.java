package com.ja.smarkdown.model.config;

import com.ja.smarkdown.model.LocationHandler;
import lombok.Data;

import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.Collection;

import static com.ja.smarkdown.model.config.Location.create;

@Data
@Alternative
public class SmarkdownConfiguration {

    private String applicationName = "Smarkdown";

    private PagesConfiguration pages = new PagesConfiguration();

    private SlidesConfiguration slides = new SlidesConfiguration();

    private Collection<Location> locations = new ArrayList<>();

    private Collection<LocationHandler> locationHandlers = new ArrayList<>();

    public SmarkdownConfiguration() {
        locations.add(create(String.format("file://%s/smarkdown",
                System.getProperty("user.home"))));
        locations.add(create("web-inf:smarkdown/md"));
        locations.add(create("webapp:smarkdown/md"));
        locations.add(create("classpath:"));
    }

}
