# thalia

A collection of documentation for Clojure functions, macros, and other
vars.  This extra documentation can easily be added to the doc strings
in a running Clojure process, such that `(doc ==)` will show the
standard doc string, plus the extra documentation.  Here is the output
of `(doc ==)` with unmodified Clojure 1.5.1:

    user=> (doc ==)
    -------------------------
    clojure.core/==
    ([x] [x y] [x y & more])
      Returns non-nil if nums all have the equivalent
      value (type-independent), otherwise false

Here is the output of `(doc ==)` after loading thalia extended doc
strings (no modification to Clojure source code is required):

    user=> (doc ==)
    -------------------------
    clojure.core/==
    ([x] [x y] [x y & more])
      Returns non-nil if nums all have the equivalent
      value (type-independent), otherwise false
    --------- ^^^ original docs --------- VVV unofficial extra docs ---------
    (== x y) is true if x and y are both numbers, and represent
    numerically equal values.  Unlike =, there are no separate
    'categories' of numeric values that are treated as always unequal to
    each other.  If you call == with more than two arguments, the result
    will be true when all consecutive pairs are ==.  An exception is
    thrown if any argument is not a numeric value.
    
    Exceptions, or possible surprises:
    
    * == is false for BigDecimal values with different scales, e.g. (==
      1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118
    * 'Not a Number' values Float/NaN and Double/NaN are not equal to any
      value, not even themselves.
    
    Examples:
    
        user=> (= 2 2.0)   ; = has different categories integer and floating point
        false
        user=> (== 2 2.0)  ; but == sees same numeric value
        true
        user=> (== 5 5N (float 5.0) (double 5.0) (biginteger 5))
        true
        user=> (== 5 5.0M) ; this is likely a bug
        false
        user=> (== Double/NaN Double/NaN)  ; this is normal
        false
        user=> (== 2 "a")
        ClassCastException java.lang.String cannot be cast to java.lang.Number  clojure.lang.Numbers.equiv (Numbers.java:206)

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
projects at the REPL, add this to your `$HOME/.lein/profiles.clj`
file:

```clojure
{:repl {:dependencies [[thalia "0.1.0"]]}}
```

In the REPL, you can add the extra docs to the normal doc strings with
these commands:

```clojure
user=> (require 'thalia.doc)
user=> (thalia.doc/add-extra-docs!)  ; (thalia.doc/add-extra-docs! :language "en_US") if you get a locale related error
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

* Create text files (in Github-flavored Markdown format) containing
  extended doc strings in a directory beneath the `doc/project-docs`
  directory.  See 'Directory structure' below for the path names that
  should be used.

* Create one file `resource/<language>.clj` per language for all
  extended doc strings written in that language.  See 'Create language
  resource files' below.

* Use Leiningen to create a JAR file containing a little bit of
  Clojure code and the files in the `resource` directory.  This is
  deployed for others to use, e.g. on clojars.org.  See 'Create JAR
  file' below.

* Create tests in `test/thalia/doc_test.clj`, or some other test file.
  These should test all examples given in the extended doc strings,
  and perhaps more.  See 'Run tests' below.


### Directory structure

The path name of each text file should be of the following form, where
`/` characters are used as on Mac OS X and Linux to separate directory
names in the path.

    doc/project-docs/<language>/<project-name>/<version>/<namespace>/<symbol>.md

Below are some example paths for the language US English, abbreviated
`en_US`.  Execute the expression `(str (java.util.Locale/getDefault))`
at the REPL to see the abbreviation for your language.

The project is `clojure.core`, version 1.5.1, namespace
`clojure.string`, symbol `replace`:

    doc/project-docs/en_US/clojure.core/1.5.1/clojure.string/replace.md

Below is an example for everything the same as above, except the
namespace is `clojure.core` and the symbol is `==`.  The file name has
been modified in a way similar to how special characters are modified
when they appear in URLs.  If you have a project, version, namespace,
or symbol name with any characters that are not ASCII alphabetic,
numeric, nor the `-` character, you can use the function
`thalia.core/encode-url-component` to see what the file name should
be.

    doc/project-docs/en_US/clojure.core/1.5.1/clojure.core/%3D%3D.md


### Create language resource files

Run this command:

    lein run create-doc-rsrc en_US

Other languages besides US English may be supported in the future, if
someone writes doc strings in those languages.


### Create JAR file

Run one or more of the following commands.

    # To install in your local Maven repo, usually in $HOME/.m2
    lein install
    
    # To create a JAR file locally
    lein jar
    
    # To deploy to clojars.org
    lein deploy clojars


### Run tests

There is definitely repetition involved here in having the same or
similar code in both the doc string text files and the unit tests.

In their current form, running the tests is quite simple:

    lein test-all

It would be preferable not to do so, e.g. by having an automated way
to extract the examples from the doc strings and execute them,
verifying the output is as shown in the example.  If someone knows of
a good way to do that, please let me know.  Something like
[`doctest`][doctest] in Python would be good.

[doctest]: http://docs.python.org/2/library/doctest.html

### Creating directory tree skeleton

These instructions have only been tested with Mac OS X and Linux,
although they might work on Windows with Cygwin installed.  They are
not expected to work with Windows (without Cygwin) because of the bash
shell scripts involved.

TBD: Flesh these instructions out.  They involve using
[`lein-clojuredocs`][lein-clojuredocs].

[lein-clojuredocs]: https://github.com/clojuredocs/lein-clojuredocs

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

Copyright (c) 2013-2015 Andy Fingerhut

Distributed under the Eclipse Public License.
