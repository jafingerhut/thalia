`compare` is the default [comparator][ComparatorsInClojure] used by
Clojure for sorting with [`sort`][doc-sort] and
[`sort-by`][doc-sort-by], for ordering the elements of a
[`sorted-set`][doc-sorted-set], and for ordering the keys of a
[`sorted-map`][doc-sorted-map].

As for all 3-way comparators, it takes two arguments `x` and `y`.  It
returns an `int` that is negative if `x` should come before `y` when
sorting, positive if `x` should come after `y`, or 0 if they are
equal.

[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
[doc-sort]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sort.md
[doc-sort-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sort-by.md
[doc-sorted-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-set.md
[doc-sorted-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map.md

`compare` works for many types of values, ordering values in one
particular way: increasing numeric order for numbers (returning 0 if
two numbers are numerically equal as by `==`, even if [`=`][Equality]
returns false); [lexicographic order][lexicographic] (aka dictionary
order) for strings, symbols, and keywords; shortest-to-longest order
for Clojure vectors, with lexicographic ordering among equal length
vectors.  All Java types implementing the [`Comparable`][Comparable]
interface such as characters, booleans, `File`, `URI`, and `UUID` are
compared via their `compareTo` methods.  Finally, `nil` can be
compared to all values described earlier, and is considered less than
everything else.

[Equality]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/equality.md
[lexicographic]: http://en.wikipedia.org/wiki/Lexicographical_order
[Comparable]: http://docs.oracle.com/javase/6/docs/api/java/lang/Comparable.html

`compare` throws an exception if given two values whose types are "too
different", e.g. it can compare Integers, Longs, and Doubles to each
other, but not strings to keywords or keywords to symbols.  It cannot
compare Clojure lists, sequences, sets, or maps at all.


## Examples

Numbers are sorted in increasing order.  This includes integers of all
sizes (ints, longs, BigIntegers, etc.), floats, doubles, and Clojure
ratios.

```clojure
user> (sort [22/7 2.71828 Double/NEGATIVE_INFINITY 1 55 3N])
(-Infinity 1 2.71828 3N 22/7 55)
```

Strings are sorted in [lexicographic order][lexicographic],
i.e. dictionary order, by their representation as sequences of Unicode
UTF-16 code units.  This is alphabetical order for strings restricted
to the ASCII subset of characters (case-sensitive).  Additional
details are described in the Java documentation for `String`'s
[`compareTo`][StringcompareTo] method.

[StringcompareTo]: http://docs.oracle.com/javase/6/docs/api/java/lang/String.html#compareTo%28java.lang.String%29

```clojure
user> (def sset1 (sorted-set "aardvark" "boo" "a" "Antelope" "bar"))
#'user/sset1
user> sset1
#{"Antelope" "a" "aardvark" "bar" "boo"}
```

Clojure symbols are sorted by their representation as strings, sorting
first by their namespace name, and if they are in the same namespace,
then by their name.  If no namespace is included, those symbols will
be sorted before any symbol with a namespace.

```clojure
user> (def sset2 (sorted-set 'user/foo 'clojure.core/pprint 'bar 'clojure.core/apply 'user/zz))
#'user/sset2
user> sset2
#{bar clojure.core/apply clojure.core/pprint user/foo user/zz}
```

Clojure keywords are sorted similarly to symbols.  The built-in
compare will not let you sort symbols and keywords in the same
collection, though.

```clojure
user> (def smap1 (sorted-map :map-key 10, :amp [3 2 1], :blammo "kaboom"))
#'user/smap1
user> smap1
{:amp [3 2 1], :blammo "kaboom", :map-key 10}
```

Clojure vectors are sorted by their length first, from shortest to
longest, then lexicographically among equal-length vectors.

```clojure
user> (sort [[1 2] [1 -5] [10000] [4 -1 20] [3 2 5]])
([10000] [1 -5] [1 2] [3 2 5] [4 -1 20])
```

You will get an exception if you try to make the default `compare`
function compare values with different types (any two numeric types
listed above can be compared to each other, but not to a non-numeric
type).  See [Comparators in Clojure][ComparatorsInClojure] for
examples of comparators that can be used to compare values of these
different types.

```clojure
user> (sort [5 "a"])
ClassCastException java.lang.Long cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)
user> (sort [:foo 'bar])
ClassCastException clojure.lang.Keyword cannot be cast to clojure.lang.Symbol  clojure.lang.Symbol.compareTo (Symbol.java:106)
```

Implementation detail: Clojure Refs can also be sorted using
`compare`.  They are sorted in the order they were created.  TBD: Why?

You will also get an exception if you try to use the default `compare`
on a Clojure list, set, map, or any other type not mentioned above.
If you wish to sort such values, you must implement your own
comparator for them.  See [Comparators in
Clojure][ComparatorsInClojure] for examples of comparators that can do
this.

```clojure
user> (sort [#{1 2} {2 4}])
ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)
user> (sort [{:a 1 :b 3} {:c -2 :d 4}])
ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)
```

Implementation details: See [Clojure][ClojureGithub] source file
`src/jvm/clojure/lang/Util.java` method `compare`, and `compareTo`
methods used to implement `Comparable` interface spread across several
other Clojure source files.

[ClojureGithub]: http://github.com/clojure/clojure
