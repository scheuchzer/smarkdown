# Welcome to Smarkdown

This is Smarkdown, a simple Markdown viewer application for JEE.




## Overview


### The goal

Keep it simple!


### What is Smarkdown

Smarkdown consists out of three parts:

- plain text as document basis
- JavaScript HTML-Renderer
- JavaScript Slideshow HTML-Renderer


This means:

- you write your documents once in plain text
- present your documents in browsers
- present slideshows in browsers


### Smarkdown is

- simple
- not reinventing the wheel  
- a war file containing JavaScript<br/>and some Java glue code


### Smarkdown combines

1. [Markdown](http://daringfireball.net/projects/markdown/) for documents
2. [Strapdown.js](http://strapdownjs.com/) for HTML rendering
3. [Reveal.js](http://lab.hakim.se/reveal-js) for slideshows


**BIG UP** on these three wonderful open source projects that make simple 
documentation possible!



## The Motivation behind Smarkdown


### The Problem

Ever had do write documentation?
 
- in Word?
- in a Wiki?
- directly in the Project structure?
- for multiple versions of your software?
- all of them?


**Yes?** How did you feel?


And then there was this other guy saying: "Could you create some slides and present it on the next team meeting?"


**Yes?** Then I think you know what I'm talking about and where this is leading.


**Did you answer all questions with NO?**

Well, lucky one! You can leave this page now and have fun with your life! Bye!


## The Solution


1. [Markdown](http://daringfireball.net/projects/markdown/) for documents
2. [Strapdown.js](http://strapdownjs.com/) for HTML rendering
3. [Reveal.js](http://lab.hakim.se/reveal-js) for slideshows


And yes, **Smarkdown** of course! At least for the JEE world.



### Markdown


[Markdown](http://daringfireball.net/projects/markdown/) is an easy-to-read, easy-to-write plain text format.


[Markdown](http://daringfireball.net/projects/markdown/) can be viewed and edit with every text editor. You can see this page in plain text by clicking on the **Raw**
link in the navigation bar.



#### Why markdown?

- it's simple
- supported by [GitHub](https://github.com) and [Bitbucket](https://bitbucket.org/)
- don't waste time formatting your word document


#### GitHub and Bitbucket

[GitHub](https://github.com) and [Bitbucket](https://bitbucket.org/) provide 
Markdown rendering when browsing the source code. We think this is cool!


See this file directly on GitHub: 

https://github.com/scheuchzer/smarkdown/tree/master/smarkdown-war/src/main/resources/smarkdown/md/smarkdown/smarkdown.md 


Documents can be referenced from Markdown file to Markdown file. Such a link will
work on GitHub as well as on Smarkdown, without changing anything!  


#### Limitations


Some might say that the Markdown syntax is somewhat limited. We think that this is 
even a positive aspect of Markdown! 


**Keep it simple!**


Don't waste your time with formatting your document. Word templates will change once a
year anyway. 


Smarkdown provides some [additional features for Markdown](markdown.md) to counteract
some limitations.


#### But I need a Word file!


**Well, that day might come!** 


Copy-Paste will be your friend. Copy the Markdown text into Word and apply the
formatting. Maybe there is even a converter tool out there.


We think that going from a plain text format to a proprietary one is much easier than 
converting a Word text into something else like a Wiki for example. 


Plain text documentation stored close or with our project source code is a good base
for creating 

- up-to-date
- version-controlled 

documentation that can be transformed into any desired format later on.



### Strapdown.js

[Strapdown.js](http://strapdownjs.com/) is a JavaScript library that renders
Markdown as HTML


#### Why Strapdown.js?

- it's simple
- has themes
- does syntax highlighting


#### Is that all?

Yes, there isn't much more to say about [Strapdown.js](http://strapdownjs.com/).

It does one thing, and it does it good!



### Reveal.js

[Reveal.js](http://lab.hakim.se/reveal-js) is a JavaScript presentation framework.


#### Why Reveal.js

- it's simple
- provides an Markdown extension


#### Transforming your Markdown file into a slideshow


Any Mardown file in Smarkdown will work as a slideshow. But to make it look sexier
you should remember following three points:


- Reveal.js slideshows are two-dimensional!
- thee empty lines will create a horizontal slide
- two empty lines will add a vertical slide


Example:
```
# Topic Foo\n
\n
\n
## Foo - History\n
\n
- ...\n
- ...\n
\n
\n
\n
# Topic Bar\n
and so on\n
```
(the ``\n`` just show where to press return)


All these new lines don't change the look of your common HTML document rendered by
Strapdown.js as Strapdown removes redundant new lines.


#### Limitations


Well, we have to admit that it's not possible to write a document in such a way that
it's perfectly readable in HTML and that it looks perfect as a slideshow. You can only
make one or the other to look great.


But you might still remember the sentence from the *The Problem* chapter.

> And then there was this other guy saying: "Could you create some slides and present it on the next team meeting?"


In such cases Reveal.js is just the right solution. The cost-income ratio is just perfect!

**No time effort, nice little presentation**

Just use the already existing documentation and switch into presentation mode!
