# Configuration


- Smarkdown works out of the box without any configuration
- Just deploy the war file and you're done


Simply place some Markdown files into a directory called ``smarkdown`` in your 
home-directory. 

In Linux this would be the directory 
```
~/smarkdown
```


After creating some files, go to the [Sitemap](../../sitemap.xhtml) page the see them listed.



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
		"theme":"bootstrap"
	},
	"slides":{
		"theme":"sky",
		"transition":"default"
	},
	"locations":[
		"file://~/smarkdown",
		"webapp:smarkdown/md",
		"web-inf:smarkdown/md",
		"classpath:smarkdown/md",
		"classpath:"
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
file.


Whereas war file and classpath locations represent static files that only can be
updated with a redeployment of the war file, the file-system location can be updated at runtime.


Currently the following location types are supported:

- [Classpath](locationClasspath.md)
- [WEB-INF](locationWeb-Inf.md)
- [Webapp](locationWebapp.md)
- [File-System](locationFileSystem.md)



## Ways to modify web.xml


- Clone Smarkdown
- Deployment-Plans/-Overlays 