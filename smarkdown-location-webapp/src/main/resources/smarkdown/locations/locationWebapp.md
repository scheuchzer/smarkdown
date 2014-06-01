# Location - Webapp


Markdown files can be read from a webapp outside a classpath or the WEB-INF directory.



## Configuration


### Enable Webapp lookups

To enable a Webapp location add an ``webap`` url to the configuration. 

Syntax:
```bash
webapp:{path-to-root}
```


for example: 
```json
{"url": "webapp:smarkdown"}
```
This enables Markdown files inside the war file in the directory ``smarkdown``. 



### Listing


Unfortunately it's not possible (at least not easily) to list all files 
inside a war-file.


All pages visible for listing must be listed in a file called ``listing.json`` that
must be listed in the root of the configured lookup directory.


example ``listing.json``:

```json
{"files":[
  "file1.md",
  "another file.md"
]}
```