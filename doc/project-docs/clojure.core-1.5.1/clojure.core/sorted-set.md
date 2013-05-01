# Summary

`sorted-set` returns a new sorted set containing the given elements.
Its elements are maintained in sorted order using the function
[`compare`][doc-compare] to compare elements.  If you want a sorted
set with a different order, use [`sorted-set-by`][doc-sorted-set-by].

[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[doc-sorted-set-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-set-by.md

```clojure
user=> (sorted-set 4 2 1)
#{1 2 4}
user=> (conj (sorted-set 4 2 1) 3)
#{1 2 3 4}
```

Sorted sets are in most ways similar to unsorted sets created with
[`hash-set`][doc-hash-set], [`set`][doc-set], or as a literal,
e.g. `#{1 2 3}`.

Read about how sorted _maps_ are different from unsorted maps at the
documentation for [`sorted-map`][doc-sorted-map].  Those same
differences apply for how sorted sets are different from unsorted
sets, if you replace "key/value pairs" with "elements", and sorting by
keys with sorting by elements.

[doc-hash-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/hash-set.md
[doc-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/set.md
[doc-sorted-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map.md
