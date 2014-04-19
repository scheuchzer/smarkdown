# File locations - Webapp


This file is located directly in the webapp (or the war-file) outside a classpath or the WEB-INF directory. The exact location is

```
smarkdown/md/smarkdown/docs/LocationWebapp.md
```



## Configuration


### Enable Webapp lookups

To enable a Webapp location add an ``web-inf`` url to the configuration. 

Syntax:
```
webapp:{path-to-root}
```


for example: 
```
webapp:smarkdown/md
``` 
is what's needed to be configured to see this pageat url
```
http://{host}:{port}/{context-root}/smarkdown/docs/LocationWebapp.md
```


### Listing

Unfortunately it's not possible (at least not easily) to list all files 
inside a war-file.


All pages visible for listing must be listed in a file called ``listing.json`` that
must be listed in the root of the configured lookup directory.


example ``listing.json``:

```json
{"files":[
  "smarkdown/docs/LocationWebapp.md",
  "another file"
]}
```
