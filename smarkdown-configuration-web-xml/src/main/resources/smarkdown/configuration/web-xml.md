# Configuration Location - web.xml

This module reads the smarkdown configuration from the `web.xml` file located in the war-file.

## Configuration

If needed, Smarkdown can be configured and customized through the ``context-param`` named 
``smarkdown.configuration``.

```xml
<context-param>
		<param-name>smarkdown.configuration</param-name>
		<param-value>
		... configuration ...
		<param-value>
</context-param>
```

Thist configuration lookup is provided by the project `smarkdown-configuration-web-xml` and is part of 
the default war file.

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-configuration-web-xml</artifactId>
</dependency>
```

### Maven

Enable the web.xml configuration by adding following lines to your pom:

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-configuration-web-xml</artifactId>
</dependency>
```

The web.xml config lookup is enabled by default if you work with the default war file.