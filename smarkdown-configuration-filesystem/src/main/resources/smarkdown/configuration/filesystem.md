# Configuration Location - Filesystem

This module reads the smarkdown configuration file from the filesystem.

## Configuration

The location of the config file has to be defined in the runtime parameter `smarkdown.cfg.file`. Define it with the `-D` parameter like `-Dsmarkdown.cfg.file=/etc/myconfig.json`.

The file specified there must be a json file as documented on [the basic configuration page](../configuration.md)


### Maven

Enable the filesystem configuration by adding following lines to your pom:

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-configuration-filesystem</artifactId>
</dependency>
```

Don't forget to remove the default configuration module `smarkdown-configuration-web-xml` from your pom!
