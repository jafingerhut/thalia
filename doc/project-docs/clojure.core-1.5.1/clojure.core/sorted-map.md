# Summary

`sorted-map` returns a new sorted map.  Its keys are maintained in
sorted order using the function [`compare`][doc-compare] to compare
keys.  If you want a sorted map with a different order, use
[`sorted-map-by`][doc-sorted-map-by].

[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[doc-sorted-map-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map-by.md

```clojure
user> (sorted-map :d 4 :b 2 :a 1)
{:a 1, :b 2, :d 4}
user> (assoc (sorted-map :d 4 :b 2 :a 1) :c 3)
{:a 1, :b 2, :c 3, :d 4}
```

Sorted maps are in most ways similar to unsorted maps created with
[`hash-map`][doc-hash-map], [`array-map`][doc-array-map], or as a
literal, e.g. `{1 "a" 2 "b"}`.  Here is a summary of the differences:

* [`seq`][doc-seq] returns a sequence of the key/value pairs in order,
  sorted by their keys.  This affects all other sequence-based
  operations upon sorted maps, e.g. `first`, `for`, etc.

* [`rseq`][doc-rseq] returns this same sequence but in reverse order,
  lazily, unlike `(reverse (seq coll))` which must generate the entire
  sequence before it can reverse it.

* You can call [`subseq`][doc-subseq] or [`rsubseq`][doc-rsubseq] on a
  sorted map to get a sorted sequence of all key/value pairs with keys
  in a specified range.

* Unsorted maps use [`=`][Equality] to compare keys, but sorted maps
  use [`compare`][doc-compare] or a caller-supplied comparator.  Thus
  unsorted maps treat several "categories" of numbers as different
  keys, whereas a sorted map using `compare` will treat them as the
  same.  A sorted map's comparator can throw exceptions if you perform
  operations with incomparable keys.

* There is no transient version of sorted maps.

TBD: Add link to an other-topics page on transients, when I create
one.

[doc-hash-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/hash-map.md
[doc-array-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/array-map.md
[doc-seq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/seq.md
[doc-rseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/rseq.md
[doc-subseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/subseq.md
[doc-rsubseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/rsubseq.md
[Equality]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/equality.md
[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md


# Examples

The main property of sorted maps is: if you call `seq` on one, it is
guaranteed to return a sequence of the map's key/value pairs in sorted
order, sorted by the key.  Thus any operation based upon `seq` will
also guarantee this order, e.g. `first`, `rest`, `for`, `doseq`, and
many others.

```clojure
;; TBD: Add some examples of this behavior that is distinctive to sorted maps.

;; One possibly interesting idea: Return a list of the largest 5 and
;; smallest 5 *unique* numbers in an input sequence using a sorted
;; map.

```

You can also call [`subseq`][doc-subseq] or [`rsubseq`][doc-rsubseq]
on a sorted map to create a sequence of all key/value pairs in a
certain range of values.  This is implemented efficiently, i.e. in a
way such that it is linear in the number of key/value pairs in the
range, not the number of key/value pairs in the entire map.

```clojure
;; TBD: Add examples of subseq and rsubseq here.

```

TBD: Are the sequences returned by subseq and rsubseq lazy?

Here are some examples demonstrating the difference between an
unsorted map's use of `=` to compare for equal keys, with its
different numeric categories as explained in the [Equality][Equality]
document, and a sorted map's use of [`compare`][doc-compare].

No pair of these keys are `=` to each other, so they can all be keys
in an unsorted map together.

```clojure
user=> (def unsorted (hash-map 1.0 "floatone" 1 "intone" 1.0M "bigdecone"
                               1.5M "bigdec1.5" 3/2 "ratio1.5"))
#'user/unsorted
user=> unsorted
{1.0 "floatone", 1 "intone", 3/2 "ratio1.5", 1.5M "bigdec1.5", 1.0M "bigdecone"}
user=> (dissoc unsorted 1 3/2)
{1.0 "floatone", 1.5M "bigdec1.5", 1.0M "bigdecone"}
```

`(compare 1.0 1)` is 0, so they are treated as equal keys in a sorted
map with compare as its comparator.  Similarly for `1.5M` and `3/2`.
`assoc` on both unsorted and sorted maps keeps the existing key if you
try to add a new one that is considered equal, but the associated
value is replaced.  That is why you end up with the key `1.0` but the
value `"bigdecone"` in this example:

```clojure
user=> (def sorted (sorted-map 1.0 "floatone" 1 "intone" 1.0M "bigdecone"
                               1.5M "bigdec1.5" 3/2 "ratio1.5"))
#'user/sorted
user=> sorted
{1.0 "bigdecone", 1.5M "ratio1.5"}
user=> (dissoc sorted 1 3/2)
{}
```

You may search an unsorted map for any value with no exception.

```clojure
user=> (unsorted "a")
nil
user=> (unsorted "a" :not-found)
:not-found
```

Searching sorted maps calls the comparator with the searched-for value
and some of the keys in the map, which may throw an exception if they
are not comparable.

```clojure
user=> (sorted "a")
ClassCastException java.lang.Double cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)

user=> (sorted "a" :not-found)
ClassCastException java.lang.Double cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)

```

There is no transient implementation for sorted maps in Clojure 1.5.1
or earlier, but there is for unsorted maps.  See the implementation
for `into` for an example of how to implement a function that uses
transients on collections that support them, but falls back to the
slower normal operations for collections that do not.

TBD: Add link to implementation of `into` in Clojure 1.5.1.

Sorted maps are implemented efficiently by maintaining the map's
key/value pairs in sorted order using a persistent red-black tree data
structure.  The efficiency of creating new maps with a single
key/value pair added or removed is `O(log n)`, but the constant
factors involved are likely to be higher than for unsorted maps.

TBD: Link to some info about this data structure.
