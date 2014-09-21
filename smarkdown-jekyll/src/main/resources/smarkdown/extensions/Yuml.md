# YUML support


This extensions converts code blocks containing [YUML](http://yuml.me)
code into images.


Example:

<pre><code>&#096;&#096;&#096;yuml
[foo]^-[bar]
[foo]^-[tar]
&#096;&#096;&#096;</code></pre>

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

<pre><code>&#096;&#096;&#096;yuml
## style=boring
[foo]^-[bar]
&#096;&#096;&#096;</code></pre>

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

<pre><code>&#096;&#096;&#096;yuml
## direction=leftToRight
## style=plain
[foo{bg:orange}]^-[bar{bg:green}]
&#096;&#096;&#096;</code></pre>

```yuml
## direction=leftToRight
## style=plain
[foo{bg:orange}]^-[bar{bg:green}]
```
