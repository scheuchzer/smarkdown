# PlantUml support


This extensions converts code blocks containing [PlantUml](http://plantuml.sourceforge.net)
code into images.


Example sequence diagram:

	```plant
	Bob->Alice : hello
	```

will be rendered as

```plant
Bob->Alice : hello
```

[more examples...](examples/PlantUml.md)

[PlantUml](http://plantuml.sourceforge.net) is able to render [class](http://plantuml.sourceforge.net/classes.html), [activity](http://plantuml.sourceforge.net/activity2.html), [usecase](http://plantuml.sourceforge.net/usecase.html), [component](http://plantuml.sourceforge.net/component.html), [state](http://plantuml.sourceforge.net/state.html) and [object](http://plantuml.sourceforge.net/objects.html) diagrams and event [wireframes](http://plantuml.sourceforge.net/salt.html). PlantUml can produce different image formats and even Ascii-art is possible!


## Configuration

Inside the code block some parameters can be configured. Use ``## key=value`` to configre these properties.


### Format

Choose your Image format:

- png (default)
- svg
- txt


Example:

	```plant
	## format=txt
	Bob->Alice : hello
	```
	
Gets rendered as

```plant
## format=txt
Bob->Alice : hello
```


### Css

The css attribut can be uses for sizing. Add one of the ``width*`` styles that are delivered with smarkdown to resize the generated image.


Example:

	```plant
	## css=width400
	Bob->Alice : hello
	```
	
Gets rendered as

```plant
## css=width400
Bob->Alice : hello
```


### WEB-XML - Fallback server

To render some diagram types PlantUml relies on [GraphViz](http://graphviz.org/) that needs to be installed on the server. If you don't have [GrapViz](http://graphviz.org/) installed, Smarkdown will send a render request
to the [diagram generator](http://www.plantuml.com/plantuml/) at http://www.plantuml.com/plantuml/.

This url can be configured if you have your own PlantUml server.


#### Fallback server

The config property ``plantuml.fallback.server`` defines the location of the fallback server to use 
if GraphViz isn't installed on the server. Default is ``http://www.plantuml.com/plantuml``

```xml
<context-param>
	<param-name>smarkdown.configuration</param-name>
	<param-value>
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
			{
				"url":"classpath:"
			}
		],
		"extensions":{
			"plantuml.fallback.server":"http://www.plantuml.com/plantuml"
		}
	}
	</param-value>
</context-param>
```


#### Fallback enabled

Activates or deactivates the fallback mode. Default is ``true``.

```xml
		"extensions":{
			"plantuml.fallback.enabled":"false"
		}
```


<div class='alert'>
The implementation of PlantUml only gives you feedback about the GraphViz capabilities through a rendered image. The first time it tries to access GraphViz a
huge exception stack trace will be logged to your log file. Smarkdown can't prevent it from doing this because PlantUml simply uses ``e.printStacktrace()``.
</div>


#### GraphViz Check

The following image shows if GraphViz is installed on your server.

```plant
@startuml
testdot
@enduml
```

If this image shows an error then the fallback server will be used for rendering if enabled.



## Maven

Activate this extension by adding following dependency:

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-plantuml</artifactId>
</dependency>
```
