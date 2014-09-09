#Smarkdown
## Configuration


- Smarkdown works out of the box without any configuration
- Just deploy the war file and you're done


Simply place some Markdown files into a directory called ``smarkdown`` in your 
home-directory. 

In Linux this would be the directory 
```
~/smarkdown
```


After creating some files, go to the [Sitemap](../sitemap.xhtml) page the see them listed.



## web.xml

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



## JSON Configuration

- The configuration is in JSON format
- By default Smarkdown uses an internal configuration that can be overridden as needed.


```json
{
	"applicationName":"Smarkdown",
	"pages":{
		"theme":"bootstrap",
		"checkForDuplicates":"false"
	},
	"slides":{
		"theme":"sky",
		"transition":"default"
	},
	"locations":[
		{"url":"file:///home/celli/smarkdown"},
		{"url":"web-inf:smarkdown/md"},
		{"url":"webapp:smarkdown/md"},
		{"url":"classpath:"},
	]
}
```


**Element:** ``applicationName``

This is a simple string defining the name of your application. It will be
displayed on the upper left corner on every page.


**Element:** ``pages``

Configures the look and feel of the HTML pages that get rendered by [Strapdown.js](http://strapdownjs.com/)


**Element:** ``pages/theme`` 

Defines the theme used by [Strapdown.js](http://strapdownjs.com/) to render the HTML page. You can find the themes in the directory ``js/strapdown/0.2/themes``.


Currently available themes are: 

- amelia
- bootstrap
- cerulean
- cyborg
- journal
- readable
- simplex
- slate
- spacelab
- spruce
- superhero
- united 


**Element:** ``pages/checkForDuplicates``

Boolean value to define if Smarkdown should check all locations for a document. If 
a document is present in several locations a message listing all locations is shown
at the top of the document.


- true: check all locations
- false: (default) stop if document has been found


This value can be overridden by the query parameter ``checkForDuplicates``.

Example:
```url
http://localhost:8080/smarkdown/index.html?checkForDuplicates=true
```


**Element:** ``slides``

Configures the look and feel of the HTML slides that get rendered by [reveal.js](http://lab.hakim.se/reveal-js)


**Element:** ``slides/theme`` 

Defines the theme used by [reveal.js](http://lab.hakim.se/reveal-js) to render the HTML slides. You can find the themes in the directory ``js/revealjs/2.6.1/css/theme``.


Currently available themes are: 

- beige
- blood
- default
- moon
- night
- serif
- simple
- sky
- solarized


**Element:** ``slides/transition``

[reveal.js](http://lab.hakim.se/reveal-js) supports several slide transitions. These are:

- concave 
- cube 
- default
- fade 
- linear 
- none 
- page 
- zoom 
 


**Element:** ``locations``

Smarkdown can read Markdown files from several locations. This can be the classpath
or a a sub-directory of it, a file-system or from somewhere inside your application war
file. It's possible to implement your own location.


Currently the following location types are supported:

- [Classpath](locations/locationClasspath.md)
- [WEB-INF](locations/locationWeb-Inf.md)
- [Webapp](locations/locationWebapp.md)
- [File-System](locations/locationFileSystem.md)
- [GitHub](locations/locationGitHub.md)
- [HTTP/HTTPS](locations/locationHttp.md)


See [Locations](locations/location.md) for more details about the location configuration.



## Document naming

Smarkdown will translate folder and document names automatically in readable
names containing spaces.

See special [Naming documentation](naming/naming.md) for details.



## Ways to modify web.xml

- Clone Smarkdown
- Deployment-Plans/-Overlays
- Build your own application 