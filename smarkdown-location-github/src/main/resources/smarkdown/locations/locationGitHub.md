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


### Additional Properties

####branch

Selects the [GitHub](https://github.com) branch. The default is ``master``.

for example: 
```json
{
  "url": "github:scheuchzer/smarkdown",
  "branch": "master"
}
``` 


####authToken

Sets the [GitHub](https://github.com) Authentication Token for accessing the repository. When working without the ``authToken`` you will [only be able 
to send 60 requests to GitHub](https://developer.github.com/v3/#rate-limiting) what might not be enough to read the complete listing 
or accessing your documents frequently.

for example: 
```json
{
  "url": "github:scheuchzer/smarkdown",
  "autToken": "16f20b0923454f3e6e23432456d766d05aa06bdfc0"
}
``` 



### Listing


All Markdown files ``*.md`` in the defined directory and its sub directories 
will be included into the [Sitemap](../../sitemap.xhtml).
