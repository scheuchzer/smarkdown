# Location - Classpath


Markdown files can be read directly from the classpath of the web application
and the JEE container.



## Configuration


### Enable Classpath lookups

To enable a file system location add an ``classpath`` url to the configuration. 

Syntax:
```bash
classpath:{path-to-root}
```


for example: 
```json
{"url": "classpath:smarkdown/md"}
``` 
is what's needed to be configured to see this file 
at url
```bash
http://{host}:{port}/{context-root}/smarkdown/docs/locationClasspath.html
```


In a Maven project, put your files under 
```bash
src/main/resources
```
to have them on the classpath.



### Listing


All Markdown files ``*.md`` in the defined directory and its sub directories 
will be included into the [Sitemap](../../sitemap.xhtml).


