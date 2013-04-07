# Searching and replacing within strings

The tables below give Clojure expressions assuming that `s` is the
string in which you want to find or replace occurences of things.  The
expressions use `s/` as an alias for the namespace `clojure.string/`.
If you have a `:require` like this in your `ns` declaration, you can
do the same in your code:

```clojure
    (ns my.namespace.name
      (:require [clojure.string :as s]))
```

    | What to   |                            |                            |
    | find      | Find first                 | Find all                   |
    |-----------|----------------------------|----------------------------|
    | character | (.indexOf s (int ch))  (1) | (index-seq s find-ch)  (2) |
    | string    | (.indexOf s find-s)    (1) | (index-seq s find-s)   (2) |
    | regex     | (re-find regex s)          | (re-seq regex s)           |

    | What to   |                                      |                                   |
    | replace   | Replace first                        | Replace all                       |
    |-----------|--------------------------------------|-----------------------------------|
    | character | (s/replace-first s old-ch new-ch)    | (s/replace s old-ch new-ch)   (3) |
    | string    | (s/replace-first s old-s new-s)      | (s/replace s old-s new-s)         |
    | regex     | (s/replace-first s regex new-s)  (4) | (s/replace s regex new-s)     (4) |

(1) These `.indexOf` Java methods return the index within `s` of the
first occurrence of character `ch` or string `find-s`, or -1 if there
is no such occurrence.

(2) I know of nothing built into Java or Clojure to do this, but
`index-seq` in the "Code" section below is one way to do it.

(3) To replace multiple different characters, each with its own
independent string, see `clojure.string/escape`.

(4) `$` and `\` characters in `new-s` are treated specially.  You can
also give a function instead of `new-s` that will be called with the
original string and returns the replacement string.  See
`clojure.string/replace-first`.


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
