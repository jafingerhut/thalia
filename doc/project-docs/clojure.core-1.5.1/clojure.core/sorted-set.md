`sorted-set` returns a new sorted set containing the given elements.
Its elements are maintained in sorted order using the function
[`compare`][doc-compare] to compare elements.  If you want a sorted
set with a different order, use [`sorted-set-by`][doc-sorted-set-by].

[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[doc-sorted-set-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-set-by.md

Sorted sets are in most ways similar to unsorted sets created with
`hash-set`, `set`, or as a literal, e.g. `#{1 2 3}`.

[doc-hash-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/hash-set.md
[doc-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/set.md

What distinguishes sorted sets is that if you call `seq` on one, it is
guaranteed to return a sequence of the set's elements in sorted order.
Thus any operation based upon `seq` will also guarantee this order,
e.g. `first`, `rest`, `for`, `doseq`, and many others.

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

Sorted sets are implemented efficiently by maintaining the set's
elements in sorted order using a persistent red-black tree data
structure.  The efficiency of creating new sets with a single element
added or removed is `O(log n)`, but the constant factors involved are
likely to be higher than for unsorted sets.

TBD: Link to some info about this data structure.

In Clojure 1.5.1 there is no transient implementation for sorted sets,
but there is for unsorted sets.  See the implementation for `into` for
an example of how to implement a function that uses transients on
collections that support them, but falls back to the slower normal
operations for collections that do not.

TBD: Add link to implementation of `into` in Clojure 1.5.1.
