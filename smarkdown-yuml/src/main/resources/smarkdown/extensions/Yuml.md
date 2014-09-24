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


[YUML](http://yuml.me) is able to render class, activity and usecase diagrams. 

```yuml
[Customer]1-0..*[Address]
```

```yuml
(start)-><a>[kettle empty]->(Fill Kettle)->(Boil Kettle),<a>[kettle full]->(Boil Kettle)->(end)
```

```yuml
[Customer]-(Login), [Customer]-(note: Cust can be registered or not{bg:beige})
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


### Type

Smarkdown tries to detect the diagram type of the yuml code. If it doesn't render the diagram you expect you
can provide a ``type`` hint.

	```yuml
	## type=usecase
	[foo]
	```

```yuml
## type=usecase
[foo]
```


The same code without a type hin will render:
```yuml
[foo]
```


### Maven

Activate this extension by adding following dependency:

```xml
<dependency>
    <groupId>com.java-adventures.smarkdown</groupId>
    <artifactId>smarkdown-yuml</artifactId>
</dependency>
```
