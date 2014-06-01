# Location - WEB-INF


Markdown files can be read from the ``WEB-INF`` directory of the webapp. 



## Configuration


### Enable WEB-INF lookups

To enable a WEB-INF location add a ``web-inf`` url to the configuration. 

Syntax:
```bash
web-inf:{path-to-root}
```


for example:
```json
{"url": "web-inf:md"}
``` 
This enables Markdown files inside the war file in the directory ``WEB-INF/md``. 



### Listing


Unfortunately it's not possible (at least not easily) to list all files 
inside a war-file and therefore the content of the WEB-INF directory.


All pages visible for listing must be listed in a file called ``listing.json`` that
must be listed in the root of the configured lookup directory.


example ``listing.json``:

```json
{"files":[
  "file1.md",
  "another file.md"
]}
```