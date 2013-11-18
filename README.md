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

* Create text files containing extended doc strings in a directory
  beneath the `doc/project-docs` directory.  See 'Directory structure'
  below for the path names that should be used.

* Create one file `resource/<language>.clj` per language for all
  extended doc strings written in that language.  See 'Create language
  resource files' below.

* Use Leiningen to create a JAR file containing a little bit of
  Clojure code and the files in the `resource` directory.  This is
  deployed for others to use, e.g. on clojars.org.  See 'Create JAR
  file' below.


### Directory structure

The path name of each text file should be of the following form, where
`/` characters are used as on Mac OS X and Linux to separate directory
names in the path.

    doc/project-docs/<language>/<project-name>/<version>/<namespace>/<symbol>.txt

Below are some example paths for the language US English, abbreviated
`en_US`.  Execute the expression `(str (java.util.Locale/getDefault))`
at the REPL to see the abbreviation for your language.

The project is `clojure.core`, version 1.5.1, namespace
`clojure.string`, symbol `replace`:

    doc/project-docs/en_US/clojure.core/1.5.1/clojure.string/replace.txt

Below is an example for everything the same as above, except the
namespace is `clojure.core` and the symbol is `==`.  The file name has
been modified in a way similar to how special characters are modified
when they appear in URLs.  If you have a project, version, namespace,
or symbol name with any characters that are not ASCII alphabetic,
numeric, nor the `-` character, you can use the function
`thalia.core/encode-url-component` to see what the file name should
be.

    doc/project-docs/en_US/clojure.core/1.5.1/clojure.core/%3D%3D.txt


### Create language resource files

Run this command:

    lein run create-doc-rsrc en_US

Other languages besides US English may be supported in the future, if
someone writes doc strings in those languages.


### Create JAR file

Run one or more of the following commands.  To install in your local
Maven repo (usually in `$HOME/.m2`):

    lein install

To create a JAR file locally:

    lein jar

To deploy to clojars.org:

    lein deploy


### Creating directory tree skeleton

These instructions have only been tested with Mac OS X and Linux,
although they might work on Windows with Cygwin installed.  They are
not expected to work with Windows (without Cygwin) because of the bash
shell scripts involved.

TBD: Flesh these instructions out.  They involve using
lein-clojuredocs:

    https://github.com/clojuredocs/lein-clojuredocs

Also these commands:

    lein run json2edn
    lein run create-empty-doc-files

For Clojure contrib libraries, the bash script `scripts/gen-most.sh`
may be useful.


## To do

Write extended doc strings for many more vars.

Create a function or macro (topic <topic-name>) that can be used to
show documentation about topics that are not necessarily about a
particular var, similary to Python's 'topics' in its help system.
There are already references to (topic Equality) and (topic
Comparators) in existing doc strings.

Try to enable (optional) less-like pager behavior for long doc
strings.  The following StackOverflow page may lead to a solution:

    http://stackoverflow.com/questions/19665348/send-clojure-doc-output-through-pager

If we ever have a lot of these, consider making a version of the
tooltip-enabled Clojure cheat sheet with the longer doc strings, with
a line limit much longer than the current 15 lines.


## License

Copyright © 2013 Andy Fingerhut

Distributed under the Eclipse Public License.
