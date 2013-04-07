# Regular expressions in Clojure

TBD: Any way with github Markdown to include an auto-generated table
of contents, i.e. of headers?

See also: `re-pattern`, `re-matcher`, `re-matches`, `re-seq`,
`re-groups`, `clojure.string/replace`, `clojure.string/replace-first`

## Searching and replacing within strings

The tables below uses `s/` as an alias for the full namespace
`clojure.string/`, which can be achieved if you use a `:require`
statement in your `ns` declaration like this:

```clojure
    (ns my.namespace.name
      (:require [clojure.string :as s]))
```

    | What to   | Find              |            |
    | find      | first             | Find all   |
    |-----------|-------------------|------------|

    | character | (.indexOf         | (2)        |
    |           |   s (int ch)) (1) |            |

    | string    | (.indexOf         | (2)        |
    |           |   s find-s) (1)   |            |

    | regex     | (re-find          | (re-seq    |
    |           |   regex s)        |   regex s) |

(1) These `.indexOf` Java methods returns the index within string `s`
of the first occurrence of character `ch` or string `find-s`, or -1 if
there is no such character/string.

(2) I know of nothing built into Java or Clojure to do this, but
`index-seq` in the "Code" section below is one way to do it.

    | What to   |                      |                        |
    | replace   | Replace first        | Replace all            |
    |-----------|----------------------|------------------------|

    | character | (s/replace-first     | (s/replace             |
    |           |   s old-ch new-ch)   |   s old-ch new-ch) (3) |

    | string    | (s/replace-first     | (s/replace             |
    |           |   s old-s new-s)     |   s old-s new-s)       |

    | regex     | (s/replace-first     | (s/replace             |
    |           |   s regex new-s) (4) |   s regex new-s) (4)   |

(3) To replace multiple different characters, each with a potentially
different string, see also `clojure.string/escape`.

(4) `$` and `\` characters in `new-s` are treated specially.  See TBD.


TBD: Some discussion on the different between specifying regex with
#"" syntax vs. as a string using re-pattern.

TBD: How to take a string and uses it as part of a regex, matching its
exact contents literally, with any special regex characters _not_
treated specially.

TBD: Storing and reading back regexes.


== Code

`index-seq`'s implementation is based on Clojure's `re-seq`.  It
returns a lazy sequence, so only the portion of the values you use
will be computed.

```clojure
    (defn index-seq
      "Returns a lazy sequence of indices within s of successive
    occurrences of the character or string ch-or-str.  Examples:

        (index-seq \"It isn't there\" \"no match\")
        => nil

        (thalia.stringsearch/index-seq \"Occurs more than once\" \\c)
        => (1 2 19)

    Note that if an index i is returned, the next index returned will
    always be at least (+ i n), where n is the length of the string to
    find:

        (index-seq \"foo babababa done\" \"baba\")
        => (4 8)       ; *not* (4 6 8)"
      [^String s ch-or-str]
      (let [^String find-s (if (char? ch-or-str) (str ch-or-str) ch-or-str)
            len (int (count find-s))]
        ((fn step [start-idx]
           (let [idx (.indexOf s find-s (int start-idx))]
             (when (not= idx -1)
               (cons idx (lazy-seq (step (+ idx len)))))))
         0)))
```
