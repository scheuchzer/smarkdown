# Jekyll support


The Jekyll extension enables propper rendering of [Jekyll](http://jekyllrb.com/) Markdown documents.

The extensions reads the Jekyll config section that is located at the beginning of a document


## Headers 

	---
	layout: post
	title: "Smarkdown rules!"
	description: "a blog post about smarkdown"
	category: JEE
	tags: [JEE, Markdown]
	---
	{% include JB/setup %}


In the example above the attribute title will be inserted as title at the beginning of the Markdown document.
The ``category`` will be rendered next to the title as a ``label``. The ``tags`` are rendered
right below the title. [See this example.](examples/Jekyll.md)


## Code highlighting

[Jekyll](http://jekyllrb.com/) supports code highlighting. It's done in a different way that in the markdown supported by 
smarkdown.

	{% highlight java %}
	public class HelloWorld {
	}
	{% endhighlight %}

The extension will translate the example from above into:

	```java
	public class HelloWorld {
	}
	```


## Configuration


### Maven

Activate this extension by adding following dependency:

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-jekyll</artifactId>
</dependency>
```
