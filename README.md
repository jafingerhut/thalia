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

Here are links to parts of it that are reasonably complete.  Comments
and corrections are welcome.

* [Comparators in Clojure][ComparatorsInClojure], including the "See
  also" links within it to functions for sorted maps and sets, and
  sorting.
* [Equality][Equality]

[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
[Equality]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/equality.md


## License

Copyright © 2013 Andy Fingerhut

Distributed under the Eclipse Public License.
