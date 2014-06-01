# Location - File System


Markdown files can be read directly from the file system outside the web application
and the JEE container.



## Configuration


### Enable File System lookups

To enable a file system location add an ``file`` url to the configuration. 

Syntax:
```bash
file://{path-to-root}
```


Remember, on Linux, to include the leading slash ``/``. You will end up with 
three slashes in a row.


for example: 
```json
{"url": "file:///var/lib/smarkdown"}
``` 
is what's needed to be configured to see the file 

```bash
/var/lib/smarkdown/foo.md
``` 
at url
```bash
http://{host}:{port}/{context-root}/foo.html
```



### Listing


All Markdown files ``*.md`` in the defined directory and its sub directories 
will be included into the [Sitemap](../../sitemap.xhtml).
