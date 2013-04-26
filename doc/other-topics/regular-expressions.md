# Regular expressions in Clojure

Regular expressions, or regexes for short, allow you to specify
patterns that you can search for within strings, e.g. the regex:

    foo [0-9a-fA-F]+ (bar|gloop)

matches "foo ", followed by one or more hexadecimal digits, followed
by " ", followed by either "bar" or "gloop".

Regexes are not general enough to match any set of strings, e.g. you
cannot write one to match all strings of balanced parentheses, nor all
legal C or Clojure programs, but they are useful for many tasks.

This document does not attempt to give a complete description of
everything that regexes are capable of -- only some tips on using
them, with examples, plus a few references if you wish to read
further.

Regexes in Clojure are Java regexes, implemented by the Java classes
[`java.util.regex.Pattern`][Java-Pattern] and
[`java.util.regex.Matcher`][Java-Matcher].  Regexes in other variants
of Clojure (e.g. Clojure/CLR or ClojureScript) have much in common
with Java regexes, but they are not identical.  Do not expect 100%
compatibility of regex syntax and matching behavior between different
regex implementations.

[Java-Pattern]: http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
[Java-Matcher]: http://docs.oracle.com/javase/6/docs/api/java/util/regex/Matcher.html


## Examples

```clojure
    (require '[clojure.string :as s])
```

`re-find` returns nil if there is no match.

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

If there are capturing groups, then on a match `re-find` returns a
vector of strings.  The first is the string that matches the entire
regex.  Each following string matches one capturing group.

```clojure
    user> (re-find #"Time: (..):(..):(..)"
                   "At the chime it is Time: 09:58:10.")
    ["Time: 09:58:10" "09" "58" "10"]
```

The order of matching strings is the order that the left parens appear
in the regex.

```clojure
    user> (re-find #"(Time: ((..):(..))):(..)"
                   "At the chime it is Time: 09:58:10.")
    ["Time: 09:58:10" "Time: 09:58" "09:58" "09" "58" "10"]
```

[Destructuring][destructuring] can be a handy way to give names to the
strings of these capturing groups.  Unlike Perl and some other regex
libraries, Java regexes do not provide a way to embed these names
inside the regex itself.

[destructuring]: http://clojure.org/special_forms#binding-forms

```clojure
    user> (let [matches (re-find #"Time: (..):(..):(..)"
                                 "At the chime it is Time: 09:58:10.")
                [hours mins secs] (map #(bigint %) (rest matches))
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
                     #"0x([0-9a-fA-F]+)"
                     (fn [[_ s]] (str (BigInteger. s 16))))
    "78 out of 100 dentists agree they prefer decimal"
```

A parenthesized subexpression is normally a capturing group.  If you
never want to get back a separate return value for the part of the
string matched by that subexpression, you can make it a non-capturing
group.  The simplest way is to put `?:` immediately after the left
paren.

    TBD: Simple examples

A capturing group, just like most other regexes, can be followed by a
`?` to indicate that the part of the regex just before the `?` may
appear in the matched string once, but need not appear at all.  If
such a capturing group is not matched by anything in the string, `nil`
is returned for that capturing group.

    TBD: Examples

Capturing groups with a `*` after them also return `nil` if they match
0 times in the string.

Capturing groups followed by `*`, `+`, or `{m,n}` that match more than
once in the string only capture the last occurrence, not all of them.
If you want all matches, use another set of parens to make the
expression, including the `*`, `+`, or `{m,n}` into a capture group.
The inner one can be made a non-capturing group if you do not want the
result of its last match.

    TBD: Examples

subexpression within the parentheses is optional,

```clojure
    user> (re-find #"([a-zA-Z]+)?(\d)+" "word 587")
    ["587" nil "7"]
    user> (re-find #"([a-zA-Z]+)?(\d)+" "word587")
    ["word587" "word" "7"]

    user> (re-find #"([a-zA-Z]+)?(\d+)" "word 587")
    ["587" nil "587"]
    user> (re-find #"([a-zA-Z]+)?(\d+)" "word587")
    ["word587" "word" "587"]

    user> (re-find #"term: ([ab])*" "term: ")
    ["term: " nil]
    user> (re-find #"term: ([ab])*" "term: a")
    ["term: a" "a"]
    user> (re-find #"term: ([ab])*" "term: ab")
    ["term: ab" "b"]
    user> (re-find #"term: ([ab])*" "term: bbbbbbba")
    ["term: bbbbbbba" "a"]

    user> (re-find #"term: ([ab]*)" "term: ")
    ["term: " ""]
    user> (re-find #"term: ([ab]*)" "term: a")
    ["term: a" "a"]
    user> (re-find #"term: ([ab]*)" "term: ab")
    ["term: ab" "ab"]
    user> (re-find #"term: ([ab]*)" "term: bbbbbbba")
    ["term: bbbbbbba" "bbbbbbba"]
```


## Code


## See also

+ [Quick start][regex-quickstart] section on regular-expressions.info
site.  Note: While many of the regex features described on this site
can be used in Java regexes, not all of them can.  The site usually
does a good job distinguishing which features exist in Java vs. those
that do not.

+ [Documentation][Java-Pattern] for Java regular expressions.

+ [The Regex Coach][regex-coach]: A Windows program for interactively
building up regular expressions and showing which parts of a regular
expression match which parts of a string, by colorizing those parts
with the same colors.

+ [Wikipedia article][Wikipedia-regex] on regular expressions.

[regex-quickstart]: http://www.regular-expressions.info/quickstart.html
[regex-coach]: http://www.weitz.de/regex-coach/
[Wikipedia-regex]: http://en.wikipedia.org/wiki/Regular_expression

See also: `re-pattern`, `re-matcher`, `re-matches`, `re-seq`,
`re-groups`, `clojure.string/replace`, `clojure.string/replace-first`


TBD: Some explanation on the difference between specifying regex
using #"" syntax vs. as a string using re-pattern.  Basically #"" lets
you type fewer backslash characters.


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

Perl Cookbook examples that would be good to include similar examples
for:

TBD 6.4 Commenting regular expressions

TBD 6.6 Matching within multiple lines

TBD 6.7 Reading records with a separator

TBD 6.14 Matching from where the last pattern left off

TBD 6.15 Greedy and non-greedy matches

There is a dead simple way to code this by matching "^(.*?)(pattern
I'm looking for)(.*)$", but that incurs the overhead of explicitly
scanning the rest of the string after the pattern is matched to match
the final (.*) (TBD: measure this effect to verify, with very long
strings, and 'normal line length' strings).

It is trickier to write the code for it, but if you use Java start()
and end() methods of the underlying Matcher object, you can get the
position of the match without.



## Misc code

```clojure
    (defn re-group-posns
      [^java.util.regex.Matcher m]
      (let [gc (. m (groupCount))]
        (if (zero? gc)
          [(. m (start)) (. m (end))]
          (loop [ret [] c 0]
            (if (<= c gc)
              (recur (conj ret (if (neg? (. m (start c)))
                                 nil
                                 [(. m (start c)) (. m (end c))]))
                     (inc c))
              ret)))))

    (defn re-find-pos
      ([^java.util.regex.Matcher m]
       (when (. m (find))
         (re-group-posns m)))
      ([^java.util.regex.Pattern re s]
       (let [m (re-matcher re s)]
         (re-find-pos m))))
```

Web app that takes a regex and parses it, explaining its pieces in
English: http://www.myezapp.com/apps/dev/regexp/show.ws

TBD: I know the Common Lisp library CL-PPCRE can let you parse a regex
and turn it into a kind of parse tree, and also lets you create parse
trees and then use them to match.  Is there a Java library for this?

Regular expression test page:
http://www.regexplanet.com/advanced/java/index.html

Commercial program RegexBuddy for learning regular expressions, and
analyzing and explaining them: http://www.regexbuddy.com/

Benchmarks of different Java regex libraries:
http://tusker.org/regex/regex_benchmark.html
