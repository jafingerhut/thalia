# Summary

TBD

# About sequences

TBD


## Sequence Order

The order of values in a sequence returned by `seq` is usually
straightforward.  For Clojure vectors, lists, and queues, `seq`
returns their elements in the normal first-to-last order natural to
that data structure.  The same goes for Java arrays and Strings.  For
any Java object that implements the `Iterable` interface, the
implementation of the Iterator controls the order of values.

The order is also well specified for Clojure's `sorted-set` and
`sorted-map` data structures: values are returned sorted by the
comparator function for the collection (key/value pairs of a
`sorted-map` are sorted by the value of their keys).

For unsorted sets and maps, and Clojure records, there is no promised
order in which `seq` will return their values.  It is common for
developers to sort the values of sets and maps when they use them, if
they care about having them in a particular order, e.g. `(sort
my-comparator-fn myset)`.

The implementation of `seq` and these data structures in Clojure 1.5.1
does have some ordering properties that you may take advantage of, if
you wish, but realize that these are not documented promises.  It does
seem unlikely for these ordering properties to change in future
versions of Clojure.

Calling `seq` on the same _identical_ map more than once will return
key/value pairs in the same order.  Since `keys` and `vals` are
implemented similarly to `seq` for maps, all of them will return
sequences in a consistent order with each other for the same identical
map.  That is, these expressions are true for all maps `m`:

```clojure
;; The seq calls are only needed to make these expressions true when m
;; is {}.

(= (keys m) (seq (map key m)))
(= (vals m) (seq (map val m)))
(= m (zipmap (keys m) (vals m)))
```

Is it guaranteed that for two equal maps `m1` and `m2`, `(seq m1)` and
`(seq m2)` contain key/value pairs in the same order?  Definitely
_not_:

```clojure
user> (def m1 (into {} [[:a 1] [:b 2]]))
#'user/m1
user> (def m2 (into {} [[:b 2] [:a 1]]))
#'user/m2
user> (= m1 m2)
true
user> (= (seq m1) (seq m2))
false
user> (seq m1)
([:a 1] [:b 2])
user> (seq m2)
([:b 2] [:a 1])
```

If you do operations like `assoc` or `dissoc` on a map to add or
remove key/value pairs, each operation returns a _new_ map, so even if
you end up with a map equal to the one you started with, it is not the
same identical map, and `seq` may return key/value pairs in a
different order, as above.

```clojure
user> (def m1 (into {} [[:a 1] [:b 2]]))
#'user/m1
user> (def m2 (-> m1 (dissoc :b) (assoc :c 3)))
#'user/m2
user> m2
{:c 3, :a 1}
user> (def m3 (-> m2 (assoc :b 2) (dissoc :c)))
#'user/m3
user> (= m1 m3)
true
user> (= (seq m1) (seq m3))
false
user> (seq m1)
([:a 1] [:b 2])
user> (seq m3)
([:b 2] [:a 1])
```

Implementation details: The methods `clojure.lang.RT.keys()` and
`clojure.lang.RT.vals()` are both based on `clojure.lang.RT.seq()`,
and both return the keys/values in the same order that `seq` on the
map returns the key/value pairs.

Unsorted sets do not support the functions `keys`, `vals`, or `zipmap`
like maps do, but everything else about unsorted maps above applies to
unsorted sets, too.  In particular, two non-identical sets that are
equal may return different orders of their elements when you call
`seq` on them:

```clojure
user> (def s1 (hash-set 4294967297000000 0 21474836485))
#'user/s1
user> (def s2 (hash-set 21474836485 0 4294967297000000))
#'user/s2
user> (= s1 s2)
true
user> (= (seq s1) (seq s2))
false
user> (seq s1)
(4294967297000000 0 21474836485)
user> (seq s2)
(21474836485 0 4294967297000000)
```


## Chunked sequences

Links to relevnt StackOverflow questions with answers:

    http://stackoverflow.com/questions/3407876/how-do-i-avoid-clojures-chunking-behavior-for-lazy-seqs-that-i-want-to-short-ci
    http://blog.fogus.me/2010/01/22/de-chunkifying-sequences-in-clojure/
    http://www.reddit.com/r/programming/comments/afyav/clojure_11_rc1_out_cuts_some_overhead_of/c0headd

An example of a potentially unexpected issue is ticket MCOMB-2, and
how unchunk in the right place can help reduce memory use:

    http://dev.clojure.org/jira/browse/MCOMB-2
