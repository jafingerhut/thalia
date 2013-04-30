`sorted-map` returns a new sorted map.  Its keys are maintained in
sorted order using the function [`compare`][doc-compare] to compare
keys.  If you want a sorted map with a different order, use
[`sorted-map-by`][doc-sorted-map-by].

[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[doc-sorted-map-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map-by.md

Sorted maps are in most ways similar to unsorted maps created with
[`hash-map`][doc-hash-map], [`array-map`][doc-array-map], or as a
literal, e.g. `{1 "a" 2 "b"}`.

[doc-hash-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/hash-map.md
[doc-array-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/array-map.md

What distinguishes sorted maps is that if you call `seq` on one, it is
guaranteed to return a sequence of the map's key/value pairs in sorted
order, sorted by the key.  Thus any operation based upon `seq` will
also guarantee this order, e.g. `first`, `rest`, `for`, `doseq`, and
many others.

TBD: Add some examples of this behavior that is distinctive to sorted
sets.

You can also call [`subseq`][doc-subseq] or [`rsubseq`][doc-rsubseq]
on a sorted set to create a sequence of all elements in a certain
range of values.  This is implemented efficiently, i.e. in a way such
that it is linear in the number of elements in the range, not the
number of elements in the entire set.

[doc-subseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/subseq.md
[doc-rsubseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/rsubseq.md

TBD: Add examples of subseq and rsubseq here.

Sorted maps are implemented efficiently by maintaining the map's
key/value pairs in sorted order using a persistent red-black tree data
structure.  The efficiency of creating new maps with a single
key/value pair added or removed is `O(log n)`, but the constant
factors involved are likely to be higher than for unsorted maps.

TBD: Link to some info about this data structure.

In Clojure 1.5.1 there is no transient implementation for sorted maps,
but there is for unsorted maps.  See the implementation for `into` for
an example of how to implement a function that uses transients on
collections that support them, but falls back to the slower normal
operations for collections that do not.

TBD: Add link to implementation of `into` in Clojure 1.5.1.
