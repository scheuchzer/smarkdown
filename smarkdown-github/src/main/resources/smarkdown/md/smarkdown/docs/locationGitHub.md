# Location - GitHub


Markdown files can be read directly from a [GitHub](https://github.com) repository



## Configuration


### Enable GitHub lookups

To enable a [GitHub](https://github.com) location add an ``github`` url to the configuration. 

Syntax:
```bash
github:{repo}[:sub-directory]
```
for example: 
```json
{"url": "github:scheuchzer/smarkdown"}
``` 

TODO



### Listing


All Markdown files ``*.md`` in the defined directory and its sub directories 
will be included into the [Sitemap](../../sitemap.xhtml).
