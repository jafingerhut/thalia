# Regular expressions in Clojure

Regular expressions, or regexes for short, allow you to specify
patterns that you can search for within strings, e.g. the regex:

    foo [0-9a-fA-F]+ (bar|gloop)

matches "foo ", followed by one or more hexadecimal digits, followed
by " ", followed by either "bar" or "gloop".

Regexes are not general enough to match any set of strings, e.g. you
cannot write one to match all strings of balanced parentheses, nor all
legal C or Clojure programs, but are useful for many tasks.

This document does not attempt to give a complete description of
everything that regexes are capable of -- only some tips on using
them, with examples, plus a few references if you wish to read
further.

Regexes in Clojure are Java regexes, implemented by the Java classes
`java.util.regex.Pattern` and `java.util.regex.Matcher`.  Regexes in
other variants of Clojure (e.g. Clojure/CLR or ClojureScript, based on
JavaScript) have much in common with Java regexes, but they are not
identical.  Don't expect 100% compatibility of regex syntax and
matching behavior between different regex implementations.


## Examples

```clojure
    (require '[clojure.string :as s])
```

No match returns nil

```clojure
    user> (re-find #"Time:" "No time like the present")
    nil
```

If the regex contains no parenthesized _capturing groups_, then on a
match `re-find` returns the part of the string that matches the regex.

```clojure
    user> (re-find #"Time: ..:..:.."
                   "At the chime it is Time: 09:58:10.")
    "Time: 09:58:10"
```

If there are capturing groups, then on a match re-find returns a
vector of strings, the first being the string that matches the entire
regex, and each following string matching successive capturing groups.

```clojure
    user> (re-find #"Time: (..):(..):(..)"
                   "At the chime it is Time: 09:58:10.")
    ["Time: 09:58:10" "09" "58" "10"]
```

The order is the order that the left parens appear in the regex.

```clojure
    user> (re-find #"(Time: ((..):(..))):(..)"
                   "At the chime it is Time: 09:58:10.")
    ["Time: 09:58:10" "Time: 09:58" "09:58" "09" "58" "10"]
```

[Destructuring][destructuring] can be a handy way to give names to the
strings of these capturing groups.  Sorry, Java regexes do not provide
a way to embed these names inside the regex itself.

[destructuring]: http://clojure.org/special_forms#binding-forms

```clojure
    user> (let [matches (re-find #"Time: (..):(..):(..)"
                                 "At the chime it is Time: 09:58:10.")
                [hours mins secs] (map #(BigInteger. %) (rest matches))
                t (+ (* 3600 hours) (* 60 mins) secs)]
            (println (str t) "seconds since midnight"))
    35890 seconds since midnight
    nil
```

You can call `clojure.string/replace` and
`clojure.string/replace-first` with a string in which to search, a
regex, and a function to calculate the replacement strings.  This
function is called once for each match.  Its one argument is the
return value from `re-find` as described above: a string if there are
no capturing groups in the regex, or a vector of strings if there are.

```clojure
    user> (s/replace "0x4e out of 0x64 dentists agree they prefer decimal"
       		     #"0x[0-9a-fA-F]+"
 		     (fn [s] (str (BigInteger. (subs s 2) 16))))
    "78 out of 100 dentists agree they prefer decimal"
```


## Code


## See also

+ [Quick start][regex-quickstart] section on regular-expressions.info
site.  Note: this site describes features that exist in regex
implementations that are not implemented in Java, but usually does a
good job distinguishing which features exist in Java vs. those that do
not.

+ [Documentation][Java-regex] for Java regular expressions.

+ [The Regex Coach][regex-coach]: A Windows program for interactively
building up regular expressions and showing which parts of a regular
expression match which parts of a string, by colorizing those parts
with the same colors.

+ [Wikipedia article][Wikipedia-regex] on regular expressions.

[regex-quickstart]: http://www.regular-expressions.info/quickstart.html
[Java-regex]: http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
[regex-coach]: http://www.weitz.de/regex-coach/
[Wikipedia-regex]: http://en.wikipedia.org/wiki/Regular_expression

See also: `re-pattern`, `re-matcher`, `re-matches`, `re-seq`,
`re-groups`, `clojure.string/replace`, `clojure.string/replace-first`


TBD: Some explanation on the difference between specifying regex with
#"" syntax vs. as a string using re-pattern.  Basically #"" lets you
type fewer backslash characters.


TBD: How to take a string and use it as part of a regex, matching its
exact contents literally, with any special regex characters _not_
treated specially.  See Java method `java.util.regex.Pattern/quote`


TBD: Writing and reading back regexes.  Regex syntax is not part of
`edn`, so one must either use the potentially unsafe
`clojure.core/read` or `read-string` to read them in, or find a
different way to write them out, such as creating a data reader tag
for regexes.


TBD: Any way with github Markdown to include an auto-generated table
of contents, i.e. of headers?
