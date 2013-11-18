# thalia

A collection of documentation for Clojure functions, macros, and other
vars.  This extra documentation can easily be added to the doc strings
in a running Clojure process, such that `(doc sorted-set)` will show
the standard doc string, plus the extra documentation.

This documentation is intended to describe the way things behave in
particular versions of Clojure, including examples, bugs, and issues
Clojure programmers should be cautious about.  It has no official
status as Clojure documentation.  You should not rely on any of it as
a promise that future Clojure versions will continue to behave in the
ways documented.


## Releases and dependency information

Latest stable release: 0.1.0

* [All Released Versions](https://clojars.org/thalia/versions)

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

```clojure
[thalia "0.1.0"]
```

[Maven](http://maven.apache.org/) dependency information:

```xml
<dependency>
  <groupId>thalia</groupId>
  <artifactId>thalia</artifactId>
  <version>0.1.0</version>
</dependency>
```


## Usage

Add this to the dependencies of your Leiningen project:

```clojure
[thalia "0.1.0"]
```

If you wish it to be part of the dependencies in all of your Leiningen
projects at the REPL, add this to your `~/.lein/profiles.clj` file:

```clojure
{:repl {:dependencies [[thalia "0.1.0"]]}}
```

In the REPL, you can add the extra docs to the normal doc strings with
these commands:

```clojure
user=> (require 'thalia.doc)
user=> (thalia.doc/add-extra-docs!)
```

To test it, look at the output of `(doc ==)`.

It is expected that this collection of documentation will grow slowly
over time, as it is written.


## Web docs

Here are links to some docs that were written in Markdown format for
browsing on Github.  I am not sure whether I will be enhancing these
in the future.  My current thinking is to focus on making enhanced doc
strings for use in the REPL rather than web browsing.

* [Comparators in Clojure][ComparatorsInClojure], including the "See
  also" links within it to functions for sorted maps and sets, and
  sorting.
* [Equality][Equality]

[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
[Equality]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/equality.md


## Developer documentation

The basic flow is:

* Create text files of extended doc strings in the directory
  doc/project-docs with a particular directory structure.  The 

* Edit those text files to provide examples, details, and warnings to
  developers about Clojure functions, macros, etc.

* 


## License

Copyright © 2013 Andy Fingerhut

Distributed under the Eclipse Public License.
