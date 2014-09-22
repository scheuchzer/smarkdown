# YUML support


This extensions converts code blocks containing [YUML](http://yuml.me)
code into images.


Example:

	```yuml
	[foo]^-[bar]
	[foo]^-[tar]
	```

will be rendered as

```yuml
[foo]^-[bar]
[foo]^-[tar]
```


## Configuration

Inside the code block some parameters can be configured. Use ``## key=value`` to configre these properties.


### Style

Choose your Yuml style:

- scruffy (default)
- plain
- boring


Example:

	```;yuml
	## style=boring
	[foo]^-[bar]
	```

```yuml
## style=boring
[foo]^-[bar]
```


### Direction

Choose the layout direction:

- topDown (default)
- leftToRight
- rightToLeft


Example:

	```yuml
	## direction=leftToRight
	## style=plain
	[foo{bg:orange}]^-[bar{bg:green}]
	```

```yuml
## direction=leftToRight
## style=plain
[foo{bg:orange}]^-[bar{bg:green}]
```


### Maven

Activate this extension by adding following dependency:

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-yuml</artifactId>
</dependency>
```
