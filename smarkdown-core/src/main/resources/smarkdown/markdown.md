#Smarkdown
## Markdown


You can get more information about Markdown on the [official Markdown page](http://daringfireball.net/projects/markdown/syntax).


## Some special Markdown

As Markdown has a very simple syntax and some aspects like 
tables and image layouting have been omitted on purpose we need some workarounds 
if we don't want to fall back to plain HTML for such things.


### Image size

Markdown doesn't say anything about image sizes. The code 

```
![](smarkdown.png)
```
will just render the image as it is.

![](smarkdown.png)


In Smarkdown we do the sizing with CSS. There are predefined style for width and height.
```
width100
width200
...
width2900

height100
height200
...
height2900
```


You can select exactly one of these styles by putting the name of the style in the
``alt`` section of the image. 


So, if you want to set the size of your image to 400 pixels, write

```
![width200](smarkdown.png)
```

![width200](smarkdown.png)


If this doesn't fit your requirements you still have the possibility to fall back 
to simple HTML and the ``IMG`` tag. But you have to remember that relative images won't work inside Smarkdown 
this way.

 