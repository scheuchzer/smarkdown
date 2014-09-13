# Location - Http/Https


Markdown files can be read from any web location using HTTP(S). The only
requirement is that there is a ``listing.json`` present on the server that
knowns what documents are available. 



## Configuration


### Enable HTTP(S) lookups

To enable a HTTP and HTTPS locations add a ``http`` or ``https`` url to the configuration. 

Syntax:
```bash
http:{path-to-root}
https:{path-to-root}
```


for example:
```json
{"url": "http://example.com/smarkdown"}
``` 


### Listing


Unfortunately it's not possible (at least not easily) to list all files 
of a common http site (This would be possible using WebDAV).


All pages visible for listing must be listed in a file called ``listing.json`` that
must be listed in the root of the configured lookup directory.


example ``listing.json``:

```json
{"files":[
  "file1.md",
  "another file.md"
]}
```