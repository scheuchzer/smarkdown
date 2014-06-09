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


Whereas war file and classpath locations represent static files that only can be
updated with a redeployment of the war file, the file-system location can be updated at runtime.


Currently the following location types are supported:

- [Classpath](locations/locationClasspath.md)
- [WEB-INF](locations/locationWeb-Inf.md)
- [Webapp](locations/locationWebapp.md)
- [File-System](locations/locationFileSystem.md)
- [GitHub](locations/locationGitHub.md)


Every location has two configuration attributes. These are

- url
- config


**Element:** ``locations/url``

The base url of a location. The format depends on the location type. For the very simple
locations like ``classpath`` or ``file`` the ``url`` attribute will be the only attribute
to configure. For more specific configuration you'll have to add the ``config`` attribute.


**Element:** ``locations/config``

The configuration attribute contains a map of attributes. Locations can use this to define
location specific configuration attributes.

Config attributes that are available for all location types are:

- mountPoint
- cacheDuration


**Element:** ``locations/config/mountPoint``

Locations can be mounted into the smarkdown file hierarchy. By default they just get added to the root.


Example:
```json
{
	"url": "file///var/tmp",
	"config":{
		"mountPoint":"foo/bar"
	}
} 
```
In this example, all markdown files located under ``/var/tmp`` will be made available
at ``foo/bar``


Say, file ``/var/tmp/test.md`` will be available at
```url
http://localhost:8080/smarkdown/foo/bar/test.html
```


**Element:** ``location/config/cacheDuration``

As loading the sitemap might take some time, the results gets cached. By default the
cache time is one minute. The default value can be changed per location with the this attribute.


The syntax is 

```
{number} {TimeUnit}
```

See [the Java TimeUnit class](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html) 
for possible time units. For readability also singular and lowercase forms of the Java TimeUnits are supported.


Example:
```json
{
	"url": "file///var/tmp",
	"config":{
		"cacheDuration":"1 hour"
	}
} 
```
In this example we scan the ``/var/tmp`` directory only once an hour.



## Ways to modify web.xml

- Clone Smarkdown
- Deployment-Plans/-Overlays
- Build your own application 