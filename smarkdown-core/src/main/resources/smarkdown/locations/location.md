#Smarkdown
## Locations


Smarkdown can read Markdown files from several locations. This can be the classpath
or a a sub-directory of it, a file-system or from somewhere inside your application war
file. 

It's possible to implement your own location.


Whereas war file and classpath locations represent static files that only can be
updated with a redeployment of the war file, the file-system location can be updated at runtime.


Currently the following location types are supported:

- [Classpath](locationClasspath.md)
- [WEB-INF](locationWeb-Inf.md)
- [Webapp](locationWebapp.md)
- [File-System](locationFileSystem.md)
- [GitHub](locationGitHub.md)
- [HTTP/HTTPS](locationHttp.md)
- [other Smarkdown installations](locationSmarkdown.md)


Every location has four configuration attributes. These are

- url
- includes
- excludes
- config

Note that only the ``url`` attribute is mandatory.


**Element:** ``locations/url``

The base url of a location. The format depends on the location type. For the very simple
locations like ``classpath`` or ``file`` the ``url`` attribute will be the only attribute
to configure. For more specific configuration you'll have to add the ``config`` attribute.


**Element:** ``locations/includes``

Documents in a location can be filtered by adding includes and excludes. By 
default every document get's included. The default include regex is ``.*``.

Example: 

```
{
	"url":"file:///home/{your_home_dir}/smarkdown",
	"includes": [
		"directory1/.*",
		"directory2/.*"
	]
}
```


**Element:** ``locations/excludes``

By default documents the ``META-INF`` directory get excluded. The default exclude regex is ``META-INF/.*``. 

Example: 

```
{
	"url":"file:///home/{your_home_dir}/smarkdown",
	"excludes": [
		"META-INF/.*"
		"directory2/.*"
	]
}
```


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


**Element:** ``locations/config/cacheDuration``

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