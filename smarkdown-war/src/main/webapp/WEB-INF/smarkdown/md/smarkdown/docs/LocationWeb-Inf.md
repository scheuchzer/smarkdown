# File locations - WEB-INF


This file is located under the ``WEB-INF`` directory. The exact location is

```
WEB-INF/smarkdown/md/smarkdown/docs/LocationWeb-Inf.md
```



## Configuration


### Enable WEB-INF lookups

To enable a WEB-INF location add a ``web-inf`` url to the configuration. 

Syntax:
```
web-inf:{path-to-root}
```


for example:
```
web-inf:smarkdown/md
``` 
is what's needed to be configured to see this page at url
```
http://{host}:{port}/{context-root}/smarkdown/docs/LocationWeb-Inf.md
```


### Listing

Unfortunately it's not possible (at least not easily) to list all files 
inside a war-file and therefore the content of the WEB-INF directory.


All pages visible for listing must be listed in a file called ``listing.json`` that
must be listed in the root of the configured lookup directory.


example ``listing.json``:

```json
{"files":[
  "smarkdown/docs/LocationWeb-Inf.md",
  "another file"
]}
```
