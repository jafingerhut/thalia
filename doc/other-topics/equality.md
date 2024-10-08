# Equality

A slightly more updated version of this article is available on the
Clojure web site, here:

+ https://clojure.org/guides/equality


This document discusses the concept of equality in Clojure, including the functions `=`, `==`, and `identical?`, and how they differ from Java's `equals` method.  It also has some description of Clojure's `hash`, and how it differs from Java's `hashCode`. The beginning of this guide provides a summary of the most important information for quick reference followed by a much more extensive review of the details.

_Information in this guide describes the behavior of Clojure 1.10.0 unless noted otherwise._

## Summary

Clojure's `=` is true when comparing immutable values that represent
the same value, or when comparing mutable objects that are the
identical object.  As a convenience, `=` also returns true when used
to compare Java collections against each other, or against Clojure's
immutable collections, if their contents are equal.  However, there
are important caveats if you use non-Clojure collections.

Clojure's `=` is true when called with two immutable scalar values, if:

* Both arguments are `nil`, `true`, `false`, the same character, or
  the same string (i.e. the same sequence of characters).
* Both arguments are symbols, or both keywords, with equal namespaces and names.
* Both arguments are numbers in the same 'category', and numerically
  the same, where category is one of:
  * integer or ratio
  * floating point (float or double)
  * [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html).

Clojure's `=` is true when called with two collections, if:

* Both arguments are _sequential_ (sequences, lists, vectors, queues,
  or Java collections implementing `java.util.List`) with `=` elements
  in the same order.
* Both arguments are sets (including Java sets implementing
  `java.util.Set`), with `=` elements, ignoring order.
* Both arguments are maps (including Java maps implementing
  `java.util.Map`), with `=` keys *and* values, ignoring entry order.
* Both arguments are records created with `defrecord`, with `=` keys
  *and* values, ignoring order, _and_ they have the same type.  `=`
  returns `false` when comparing a record to a map, regardless of
  their keys and values, because they do not have the same type.

Clojure's `=` is true when called with two mutable Clojure objects,
i.e. vars, refs, atoms, or agents, or with two "pending" Clojure
objects, i.e. futures, promises, or delays, if:

* Both arguments are the identical object, i.e. `(identical?  x y)` is true.

For all other types:

* Both arguments are the same type defined with `deftype`.  The type's
  `equiv` method is called and its return value becomes the value of
  `(= x y)`.
* For other types, Java's `x.equals(y)` is true.

Clojure's `==` is intended specifically for numerical values:

* `==` can be used with numbers across different number categories (such as integer `0` and floating point `0.0`).
* If any value being compared is not a number, an exception is thrown.

If you call `=` or `==` with more than two arguments, the result will
be true when all consecutive pairs are `=` or `==`.  `hash` is
consistent with `=`, with the exceptions given below.

Exceptions, or possible surprises:

* When using non-Clojure collections in a Clojure hash-based
  collection (as map keys, or set elements), it will not appear equal
  to a similar collection with Clojure counterparts, due to the
  difference in hashing behavior.  (see later section [Equality and
  hash](#equality-and-hash) and
  [CLJ-1372](http://dev.clojure.org/jira/browse/CLJ-1372))
* When comparing collections with `=`, numbers within the collections
  are also compared with `=`, so the three numeric categories above
  are significant.
* 'Not a Number' values `##NaN`, `Float/NaN`, and `Double/NaN` are not
  `=` or `==` to anything, not even themselves.
  _Recommendation:_ Avoid including `##NaN` inside of Clojure data
  structures where you want to compare them to each other using `=`,
  and sometimes get `true` as the result.
* 0.0 is `=` to -0.0
* Clojure regex's, e.g. `#"a.*bc"`, are implemented using Java
  `java.util.regex.Pattern` objects, and Java's `equals` on two
  `Pattern` objects returns `(identical? re1 re2)`, even though they
  are documented as immutable objects.  Thus `(= #"abc" #"abc")`
  returns false, and `=` only returns true if two regex's happen to be
  the same identical object in memory.  _Recommendation:_ Avoid using
  regex instances inside of Clojure data structures where you want to
  compare them to each other using `=`, and get `true` as the result
  even if the regex instances are not identical objects.  If you feel
  the need to, consider converting them to strings first, e.g. `(str
  #"abc")` -> `"abc"` (see
  [CLJ-1182](http://dev.clojure.org/jira/browse/CLJ-1182))
* Clojure persistent queues are never `=` to Java collections
  implementing `java.util.List`, not even if they have `=` elements in
  the same order (see
  [CLJ-1059](http://dev.clojure.org/jira/browse/CLJ-1059))
* Using `=` to compare a sorted map with another map, where `compare`
  throws an exception when comparing their keys to each other because
  they have different types (e.g. keywords vs. numbers), will in some
  cases throw an exception (see
  [CLJ-2325](http://dev.clojure.org/jira/browse/CLJ-2325))

In most cases, `hash` is consistent with `=`, meaning: if `(= x y)`,
then `(= (hash x) (hash y))`.  For any values or objects where this
does not hold, Clojure hash-based collections will not be able to find
or remove those items correctly, i.e. for hash-based sets with those
items as elements, or hash-based maps with those items as keys.

* `hash` is consistent with `=` for numbers, except for special float
  and double values.  _Recommendation:_ Convert floats to doubles with
  `(double x)` to avoid this issue.
* `hash` is not consistent with `=` for immutable Clojure collections
  and their non-Clojure counterparts.  See the "Equality and hash"
  section below for more details.  _Recommendation:_ Convert
  non-Clojure collections to their Clojure immutable counterparts
  before including them in other Clojure data structures.
* `hash` is not consistent with `=` for objects with class `VecSeq`,
  returned from calls like `(seq (vector-of :int 0 1 2))` (see
  [CLJ-1364](http://dev.clojure.org/jira/browse/CLJ-1364))


## Introduction

Equality in Clojure is most often tested using `=`.

```clojure
user> (= 2 (+ 1 1))
true
user> (= (str "fo" "od") "food")
true
```

Unlike Java's `equals` method, Clojure's `=` returns true for many
values that do not have the same type as each other.

```clojure
user> (= (float 314.0) (double 314.0))
true
user> (= 3 3N)
true
```

`=` does *not* always return true when two numbers have the same
numeric value.

```clojure
user> (= 2 2.0)
false
```

If you want to test for numeric equality across different numeric categories, use `==`.  See the section "Numbers" below for details.

Sequential collections (sequences, vectors, lists, and queues) with equal elements in the same order are equal:

```clojure
user> (range 3)
(0 1 2)
user> (= [0 1 2] (range 3))
true
user> (= [0 1 2] '(0 1 2))
true
;; not = because different order
user> (= [0 1 2] [0 2 1])
false
;; not = because different number of elements
user> (= [0 1] [0 1 2])
false
;; not = because 2 and 2.0 are not =
user> (= '(0 1 2) '(0 1 2.0))
false
```

Two sets are equal if they have equal elements.  Sets are normally unordered but even with sorted sets, the sort order is not considered when comparing for equality.

```clojure
user> (def s1 #{1999 2001 3001})
#'user/s1
user> s1
#{2001 1999 3001}
user> (def s2 (sorted-set 1999 2001 3001))
#'user/s2
user> s2
#{1999 2001 3001}
user> (= s1 s2)
true
```

Two maps are equal if they have the same set of keys, and each key
maps to equal values in each map.  As with sets, maps are unordered
and the sort order is not considered for sorted maps.

```clojure
user> (def m1 (sorted-map-by > 3 -7 5 10 15 20))
#'user/m1
user> (def m2 {3 -7, 5 10, 15 20})
#'user/m2
user> m1
{15 20, 5 10, 3 -7}
user> m2
{3 -7, 5 10, 15 20}
user> (= m1 m2)
true
```

Note that while vectors are indexed and possess some map-like qualities, maps
and vectors never compare as `=` in Clojure:

```clojure
user> (def v1 ["a" "b" "c"])
#'user/v1
user> (def m1 {0 "a" 1 "b" 2 "c"})
#'user/m1
user> (v1 0)
"a"
user> (m1 0)
"a"
user> (= v1 m1)
false
```

Any metadata associated with Clojure collections is ignored when
comparing them.

```clojure
user> (def s1 (with-meta #{1 2 3} {:key1 "set 1"}))
#'user/s1
user> (def s2 (with-meta #{1 2 3} {:key1 "set 2 here"}))
#'user/s2
user> (binding [*print-meta* true] (pr-str s1))
"^{:key1 \"set 1\"} #{1 2 3}"
user> (binding [*print-meta* true] (pr-str s2))
"^{:key1 \"set 2 here\"} #{1 2 3}"
user> (= s1 s2)
true
user> (= (meta s1) (meta s2))
false
```

Records created with `defrecord` in many ways behave similarly to
Clojure maps.  However, they are only `=` to other records of the same
type, and only then if they have the same keys and the same values.
They are never equal to maps, even if they have the same keys and
values.

When you define a Clojure record, you are doing so in order to create
a distinct type that can be distinguished from other types -- you want
each type to have its own behavior with Clojure protocols and
multimethods.

```clojure
user=> (defrecord MyRec1 [a b])
user.MyRec1
user=> (def r1 (->MyRec1 1 2))
#'user/r1
user=> r1
#user.MyRec1{:a 1, :b 2}

user=> (defrecord MyRec2 [a b])
user.MyRec2
user=> (def r2 (->MyRec2 1 2))
#'user/r2
user=> r2
#user.MyRec2{:a 1, :b 2}

user=> (def m1 {:a 1 :b 2})
#'user/m1

user=> (= r1 r2)
false             ; r1 and r2 have different types
user=> (= r1 m1)
false             ; r1 and m1 have different types
user=> (into {} r1)
{:a 1, :b 2}      ; this is one way to "convert" a record to a map
user=> (= (into {} r1) m1)
true              ; the resulting map is = to m1
```


Clojure `=` behaves the same as Java's `equals` for all types except
numbers and Clojure collections.

Booleans and characters are straightforward in their equality.

Strings are straightforward, too, except in some cases involving
Unicode where strings that consist of different sequences of Unicode
characters can look the same when displayed, and in some applications
should be treated as equal even though `=` returns false.  See
"Normalization" on the Wikipedia page on [Unicode
equivalence](http://en.wikipedia.org/wiki/Unicode_equivalence) if you
are interested.  There are libraries like
[ICU4J](http://site.icu-project.org) (International Components for
Unicode for Java) that can help if you need to do this.

Two symbols are equal if they have the same namespace and symbol name.
Two keywords are equal given the same conditions.  Clojure makes
equality testing for keywords particularly quick (a simple pointer
comparison).  It achieves this by its `intern` method of the Keyword
class guaranteeing that all keywords with the same namespace and name
will return the same keyword object.


## Numbers

Java `equals` is only true for two numbers if the types and numeric
values are the same.  Thus `equals` is false even for Integer 1 and
Long 1, because they have different types.  Exception: Java `equals`
is also false for two BigDecimal values that are numerically equal if
they have different scales, e.g. 1.50M and 1.500M are not equal.  This
behavior is documented for BigDecimal method
[`equals`](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html#equals-java.lang.Object-).

Clojure `=` is true if the 'category' and numeric values are the same.
Category is one of:

* integer or ratios, where integer includes all Java integer types such as `Byte`, `Short`, `Integer`, `Long`, `BigInteger`, and `clojure.lang.BigInt`, and ratios are represented with the Java type named `clojure.lang.Ratio`.
* floating point: `Float` and `Double`
* decimal: `BigDecimal`

So `(= (int 1) (long 1))` is true because they are in the same integer
category, but `(= 1 1.0)` is false because they are in different
categories (integer vs. floating).  While integers and ratios are
separate types in the Clojure implementation, for the purposes of `=`
they are effectively in the same category.  The results of arithmetic
operations on ratios are auto-converted to integers if they are whole
numbers.  Thus any Clojure number that has type Ratio cannot equal any
integer, so `=` always gives the correct numerical answer (`false`)
when comparing a ratio to an integer.

Clojure also has `==` that is only useful for comparing numbers.  It
returns true whenever `=` does.  It also returns true for numbers that
are numerically equal, even if they are in different categories.  Thus
`(= 1 1.0)` is false, but `(== 1 1.0)` is true.

Why does `=` have different categories for numbers, you might wonder?
It would be difficult (if it is even possible) to make `hash` consistent
with `=` if it behaved like `==` (see section "Equality and hash"
below).  Imagine trying to write `hash` such that it was guaranteed to
return the same hash value for all of `(float 1.5)`, `(double 1.5)`,
BigDecimal values 1.50M, 1.500M, etc. and the ratio `(/ 3 2)`.

Clojure uses `=` to compare values for equality when they are used as
elements in sets, or keys in maps.  Thus Clojure's numeric categories
come into play if you use sets with numeric elements or maps with
numeric keys.


### Floating point numbers are usually approximations

Note that floating point values might behave in ways that surprise
you, if you have not learned of their approximate nature before.  They
are often approximations simply because they are represented with a
fixed number of bits, and thus many values cannot be represented
exactly and must be approximated (or be out of range).  This is true
for floating point numbers in any programming language.

```clojure
user> (def d1 (apply + (repeat 100 0.1)))
#'user/d1
user> d1
9.99999999999998
user> (== d1 10.0)
false
```

There is a whole field called [Numerical
Analysis](https://en.wikipedia.org/wiki/Numerical_analysis) dedicated
to studying algorithms that use numerical approximation.  There are
libraries of Fortran code that are used because their order of
floating point operations is carefully crafted to give guarantees on
the difference between their approximate answers and the exact
answers.  ["What Every Computer Scientist Should Know About
Floating-Point
Arithmetic"](http://docs.oracle.com/cd/E19957-01/806-3568/ncg_goldberg.html)
is good reading if you want quite a few details.

If you want exact answers for at least some kinds of problems, ratios
or BigDecimals might suit your needs.  Realize that these require
variable amounts of memory if the number of digits required grow
(e.g. after many arithmetic operations), and significantly more
computation time.  They also won't help if you want exact values of pi
or the square root of 2.


### Floating point "Not A Number"

Clojure uses the underlying Java double-size floating point numbers
(64-bit) with representation and behavior defined by a standard, IEEE
754.  There is a special value
[`NaN`](http://en.wikipedia.org/wiki/NaN) ("Not A Number") that is not
even equal to itself. Clojure represents this value as
the symbolic value `##NaN`.

```clojure
user> (Math/sqrt -1)
##NaN
user> (= ##NaN ##NaN)
false
user> (== ##NaN ##NaN)
false
```

This leads to some odd behavior if this "value" appears in your data.
While no error occurs when adding `##NaN` as a set element or a key in a
map, you cannot then search for it and find it.  You also cannot
remove it using functions like `disj` or `dissoc`.  It will appear
normally in sequences created from collections containing it.

```clojure
user> (def s1 #{1.0 2.0 ##NaN})
#'user/s1
user> s1
#{2.0 1.0 ##NaN}
user> (s1 1.0)
1.0
user> (s1 1.5)
nil
user> (s1 ##NaN)
nil             ; cannot find ##NaN in a set, because it is not = to itself

user> (disj s1 2.0)
#{1.0 ##NaN}
user> (disj s1 ##NaN)
#{2.0 1.0 ##NaN}    ; ##NaN is still in the result!
```

In many cases, collections that contain `##NaN` will not be `=` to another collection, even if they look like they should be, because `(= ##NaN ##NaN)` is `false`:

```clojure
user> (= [1 ##NaN] [1 ##NaN])
false
```

Oddly enough, there are exceptions where collections contain `##NaN` that look like they should be `=`, and they are, because `(identical? ##NaN ##NaN)` is `true`:

```clojure
user> (def s2 #{##NaN 2.0 1.0})
#'user/s2
user> s2
#{2.0 1.0 ##NaN}
user> (= s1 s2)
true
```

Java has a special case in its `equals` method for floating point
values that makes `##NaN` equal to itself.  Clojure `=` and `==` do not.

```clojure
user> (.equals ##NaN ##NaN)
true
```


## Equality and hash

Java has `equals` to compare pairs of objects for equality.

Java has a method `hashCode` that is _consistent_ with this notion of
equality (or is documented that it should be, at least).  This means
that for any two objects `x` and `y` where `equals` is true,
`x.hashCode()` and `y.hashCode()` are equal, too.

This hash consistency property makes it possible to use `hashCode` to
implement hash-based data structures like maps and sets that use hashing
techniques internally.  For example, a hash table could be used to
implement a set, and it will be guaranteed that objects with different
`hashCode` values can be put into different hash buckets, and objects
in different hash buckets will never be equal to each other.

Clojure has `=` and `hash` for similar reasons.  Since Clojure `=`
considers more pairs of things equal to each other than Java `equals`,
Clojure `hash` must return the same hash value for more pairs of
objects.  For example, `hash` always returns the same value regardless
of whether a sequence of `=` elements is in a sequence, vector, list,
or queue:

```clojure
user> (hash ["a" 5 :c])
1698166287
user> (hash (seq ["a" 5 :c]))
1698166287
user> (hash '("a" 5 :c))
1698166287
user> (hash (conj clojure.lang.PersistentQueue/EMPTY "a" 5 :c))
1698166287
```

However, since `hash` is not consistent with `=` when comparing
Clojure immutable collections with their non-Clojure counterparts,
mixing the two can lead to undesirable behavior, as shown in the
examples below.

```clojure
user=> (def java-list (java.util.ArrayList. [1 2 3]))
#'user/java-list
user=> (def clj-vec [1 2 3])
#'user/clj-vec

;; They are =, even though they are different classes
user=> (= java-list clj-vec)
true
user=> (class java-list)
java.util.ArrayList
user=> (class clj-vec)
clojure.lang.PersistentVector

;; Their hash values are different, though.

user=> (hash java-list)
30817
user=> (hash clj-vec)
736442005

;; If java-list and clj-vec are put into collections that do not use
;; their hash values, like a vector or array-map, then those
;; collections will be equal, too.

user=> (= [java-list] [clj-vec])
true
user=> (class {java-list 5})
clojure.lang.PersistentArrayMap
user=> (= {java-list 5} {clj-vec 5})
true
user=> (assoc {} java-list 5 clj-vec 3)
{[1 2 3] 3}

;; However, if java-list and clj-vec are put into collections that do
;; use their hash values, like a hash-set, or a key in a hash-map,
;; then those collections will not be equal because of the different
;; hash values.

user=> (class (hash-map java-list 5))
clojure.lang.PersistentHashMap
user=> (= (hash-map java-list 5) (hash-map clj-vec 5))
false               ; sorry, not true
user=> (= (hash-set java-list) (hash-set clj-vec))
false               ; also not true

user=> (get (hash-map java-list 5) java-list)
5
user=> (get (hash-map java-list 5) clj-vec)
nil                 ; you were probably hoping for 5

user=> (conj #{} java-list clj-vec)
#{[1 2 3] [1 2 3]}          ; you may have been expecting #{[1 2 3]}
user=> (hash-map java-list 5 clj-vec 3)
{[1 2 3] 5, [1 2 3] 3}      ; I bet you wanted {[1 2 3] 3} instead
```

Most of the time you use maps in Clojure, you do not specify whether
you want an array map or a hash map.  By default array maps are
used if there are at most 8 keys, and hash maps are used if there are
over 8 keys.  Clojure functions choose the implementation for you as
you do operations on the maps.  Thus even if you tried to use array
maps consistently, you are likely to frequently get hash maps as you
create larger maps.

We do _not_ recommend trying to avoid the use of hash-based sets and
maps in Clojure.  They use hashing to help achieve high performance in
their operations.  Instead we would recommend avoiding the use of
non-Clojure collections as parts within Clojure collections.
Primarily this advice is because most such non-Clojure collections are
mutable, and mutability often leads to subtle bugs.  Another reason is
the inconsistency of `hash` with `=`.

Similar behavior occurs for Java collections that implement
`java.util.List`, `java.util.Set`, and `java.util.Map`, and any of the
few kinds of values for which Clojure's `hash` is not consistent with
`=`.

If you use hash-inconsistent values as parts within _any_ Clojure
collection, even as elements in a sequential collection like a list or
vector, those collections become hash-inconsistent with each other,
too.  This occurs because the hash value of collections is calculated
by combining the hash values of their parts.


### Historical notes on hash inconsistency for non-Clojure collections

You are likely wondering _why_ `hash` is not consistent with `=` for
non-Clojure collections.  Non-Clojure collections have used Java's
`hashCode` method long before Clojure existed.  When Clojure was
initially developed, it used the same formula for calculating a hash
function from collection elements as `hashCode` did.

Before the release of Clojure 1.6.0 it was discovered that this use of
`hashCode` for Clojure's `hash` function can lead to many hash
collisions when small collections are used as set elements or map
keys.

For example, imagine a Clojure program that represents the contents of
a 2-dimensional grid with 100 rows and 100 columns using a map with
keys that are vectors of two numbers in the range [0, 99].  There are
10,000 such points in this grid, so 10,000 keys in the map, but
`hashCode` only gives 3,169 different results.

```clojure
user=> (def grid-keys (for [x (range 100), y (range 100)]
                        [x y]))
#'user/grid-keys
user=> (count grid-keys)
10000
user=> (take 5 grid-keys)
([0 0] [0 1] [0 2] [0 3] [0 4])
user=> (take-last 5 grid-keys)
([99 95] [99 96] [99 97] [99 98] [99 99])
user=> (count (group-by #(.hashCode %) grid-keys))
3169
```

Thus there are an average of 10,000 / 3,169 = 3.16 collisions per hash
bucket if the map uses the default Clojure implementation of a
hash-map.

The Clojure developers
[analyzed](https://dev.clojure.org/display/design/Better+hashing)
several alternate hash functions, and chose one based on the Murmur3
hash function, which has been in use since Clojure 1.6.0.  It also
uses a different way than Java's `hashCode` does to combine the hashes
of multiple elements in a collection.

At that time, Clojure could have changed `hash` to use the new
technique for non-Clojure collections as well, but it was judged that
doing so would significantly slow down a Java method called `hasheq`,
used to implement `hash`.  See
[CLJ-1372](http://dev.clojure.org/jira/browse/CLJ-1372) for approaches
that have been considered so far, but as of this time no one has
discovered a competitively fast way to do it.


### Other cases of `hash` inconsistent with `=`

For some Float and Double values that are `=` to each other, their
`hash` values are inconsistent:

```clojure
user> (= (float 1.0e9) (double 1.0e9))
true
user> (map hash [(float 1.0e9) (double 1.0e9)])
(1315859240 1104006501)
user> (hash-map (float 1.0e9) :float-one (double 1.0e9) :oops)
{1.0E9 :oops, 1.0E9 :float-one}
```

You can avoid the `Float` vs `Double` hash inconsistency by
consistently using one or the other types in floating point code.
Clojure defaults to doubles for floating point values, so that may be
the most convenient choice.

Rich Hickey has decided that changing this inconsistency in hash
values for types `Float` and `Double` is out of scope for Clojure
(mentioned in a comment of
[CLJ-1036](http://dev.clojure.org/jira/browse/CLJ-1036)).  Ticket
[CLJ-1649](http://dev.clojure.org/jira/browse/CLJ-1649) has been filed
suggesting a change that `=` always return false when comparing floats
to doubles, which would make `hash` consistent with `=` by eliminating
the restriction on `hash`, but there is no decision on that yet.


See the code of the projects below for examples of how to do this, and much more.  In
particular, the Java methods `equals` and `hashCode` from standard
Java objects, and the Clojure Java methods `equiv` and `hasheq` are
the most relevant for how `=` and `hash` behave.

* [org.clojure/data.priority-map](https://github.com/clojure/data.priority-map)
* [org.flatland/ordered](https://github.com/clj-commons/ordered) but note
  that it needs a change so that its custom ordered map data structure
  is not `=` to any Clojure record:
  [PR #34](https://github.com/clj-commons/ordered/pull/34)

## References

The paper ["Equal Rights for Functional Objects, or, the More Things
Change, The More They Are the
Same"](http://home.pipeline.com/~hbaker1/ObjectIdentity.html) by Henry
Baker includes code written in Common Lisp for a function `EGAL` that
was an inspiration for Clojure's `=`.  The idea of "deep equality"
making sense for immutable values, but not as much sense for mutable
objects (unless the mutable objects are the same object in memory), is
independent of programming language.

Some differences between `EGAL` and Clojure's `=` are described below.
These are fairly esoteric details about the behavior of `EGAL`, and
are not necessary to know for an understanding of Clojure's `=`.

### Comparing mutable collections to other things

`EGAL` is defined to be `false` when comparing mutable objects to
anything else, unless that other thing is the same identical mutable
object in memory.

As a convenience, Clojure's `=` is designed to return `true` in some
cases when comparing Clojure immutable collections to non-Clojure
collections.

There is no Java method to determine whether an arbitrary collection
is mutable or immutable, so it is not possible in Clojure to implement
the intended behavior of `EGAL`, although one might consider `=` "closer"
to `EGAL` if it always returned `false` when one of the arguments was
a non-Clojure collection.

### Lazy and pending values

Baker recommends that `EGAL` force lazy values when comparing them
(see Section 3. J. "Lazy Values" in the "Equal Rights for Functional Objects" paper).  When comparing a lazy sequence to
another sequential thing, Clojure's `=` does force the evaluation of
the lazy sequence, stopping if it reaches a non-`=` sequence element.
Chunked sequences, e.g. as produced by `range`, can cause evaluation
to proceed a little bit further than that point, as is the case for
any event in Clojure that causes evaluation of part of a lazy
sequence.

Clojure's `=` does not `deref` delay, promise, or future objects when
comparing them.  Instead, it compares them via `identical?`, thus
returning `true` only if they are the same identical object in memory,
even if calling `deref` on them would result in values that were `=`.

### Closures

Baker describes in detail how `EGAL` can return `true` in some cases
when comparing
[closures](https://en.wikipedia.org/wiki/Closure_(computer_programming))
to each other (see Section 3. D. "Equality of Functions and
Function-Closures" in the "Equal Rights for Functional Objects" paper).

When given a function or closure as an argument, Clojure's `=` only
returns `true` if they are `identical?` to each other.

Baker appeared to be motivated to define `EGAL` this way because of
the prevalence in some Lisp family languages of using closures to
represent objects, where those objects could contain mutable state, or
immutable values (see the example below).  Given that Clojure has
multiple other ways of creating immutable values and mutable objects
(e.g. records, reify, proxy, deftype), using closures to do so is
uncommon.

```clojure
(defn make-point [init-x init-y]
  (let [x init-x
        y init-y]
    (fn [msg]
      (cond (= msg :get-x) x
            (= msg :get-y) y
	    (= msg :get-both) [x y]
	    :else nil))))

user=> (def p1 (make-point 5 7))
#'user/p1
user=> (def p2 (make-point -3 4))
#'user/p2
user=> (p1 :get-x)
5
user=> (p2 :get-both)
[-3 4]
user=> (= p1 p2)
false             ; We expect this to be false,
                  ; because p1 and p2 have different x, y values
user=> (def p3 (make-point 5 7))
#'user/p3
user=> (= p1 p3)
false             ; Baker's EGAL would return true here.  Clojure
                  ; = returns false because p1 and p3 are not identical?
```

### Clojure implementation details

References to implementation code: For `=`, see the method `equiv` in
Clojure source file Util.java.  For `=` and `==` among numbers and the
three numeric categories, see the Clojure source file Numbers.java.


## Historical notes

* (Clojure 1.5.1) `hash` is not consistent with `=` for some
  BigInteger values.  Convert them to BigInt using `(bigint x)`.
  (fixed in Clojure 1.6.0)
* (Clojure 1.5.1) `=` and `==` are false for BigDecimal values with
  different scales, e.g. `(== 1.50M 1.500M)` is false.  (fixed in
  Clojure 1.6.0)

(Clojure 1.5.1) `hash` is inconsistent with `=` for some BigInteger
values that are `=` to numbers of other types.  This is fixed in
Clojure 1.6.0 with [this
commit](https://github.com/clojure/clojure/commit/96e72517615cd2ccdb4fdbbeb6ffba5ad99dbdac).

```clojure
user> (= (int -1) (long -1) (bigint -1) (biginteger -1))
true

;; Clojure 1.5.1

user> (map hash [(int -1) (long -1) (bigint -1) (biginteger -1)])
(0 0 0 -1)
user> (hash-map (long -1) :minus-one (biginteger -1) :oops)
{-1 :minus-one, -1 :oops}

;; Clojure 1.6.0

user=> (map hash [(int -1) (long -1) (bigint -1) (biginteger -1)])
(1651860712 1651860712 1651860712 1651860712)
user=> (hash-map (long -1) :minus-one (biginteger -1) :much-better)
{-1 :much-better}
```

You can avoid the `BigInteger` issue in Clojure 1.5.1 by not using
values of that type.  You are most likely to encounter them in Clojure
through interop with Java libraries.  In that case, converting them to
`BigInt` via the `bigint` function at the Clojure/Java boundary would
be safest.

Clojure 1.5.1 inherits Java's exception for BigDecimal with the same
numeric value but different scales, i.e. `(= 1.50M 1.500M)` is false.
This was corrected in Clojure 1.6.0 (see
[CLJ-1118](http://dev.clojure.org/jira/browse/CLJ-1118)).


## Things to do

TBD: Other Clojure tickets to mention somewhere in this article,
perhaps:

* [CLJ-750](http://dev.clojure.org/jira/browse/CLJ-750) clojure.lang.MapEntry violates .equals and .hashCode contracts of HashMap.Entry; leads to non-reflexive .equals, etc.
* [CLJ-1242](http://dev.clojure.org/jira/browse/CLJ-1242) = on sorted collections with different key types incorrectly throws
* [CLJ-2089](http://dev.clojure.org/jira/browse/CLJ-2089) Sorted colls with default comparator don't check that first element is Comparable

TBD: Mention Clojure `identity?`, same as Java object identity `==`,
and how it is typically only a good idea to use it in Java interop
cases where you need Java `==`.  It is better to use Clojure `=` value
equality in most cases.

* TODO: +Infinity, -Infinity  (what did Alex think was important to mention about those in this article?)

TBD: Is there any issue with using Clojure mutable objects (vars,
refs, atoms, and agents) as set elements or map keys?

Do mutable objects (vars, refs, atoms, and agents) have consistent
hash values even when their contents change?  The answer should be
"yes", because of the 4 Java classes in clojure.lang Agent, Atom, Ref,
and Var, none of them have a hashCode method, and none of their
superclasses do, until you get to java.lang.Object.  Thus they should
all inherit the implementation of Object's hashCode method.

The article below explains how the default hashCode method is
implemented in OpenJDK and Azul Zing JVMs.  It is basically a number
generated when a JVM Object is created, where pseudo-randomly is one
way, and the number is then recorded as part of the state of the
Object instance and returned by the default hashCode() method
implementation if it is used.  Thus this value remains stable across
the lifetime of the object instance, even if a copying garbage
collector changes its current address in memory.

"How does the default hashCode() work?"
https://srvaroa.github.io/jvm/java/openjdk/biased-locking/2017/01/30/hashCode.html

```java
// Interfaces:
interface Callable  // in java.util.concurrent - no hashCode method
interface Comparable<T>  // in java.lang - no hashCode method
public interface IAtom  // no hashCode method
public interface IAtom2 extends IAtom  // no hashCode method
public interface IDeref  // no hashCode method
public interface IFn extends Callable, Runnable  // no hashCode method
public interface IMeta  // no hashCode method
public interface IObj extends IMeta  // no hashCode method
public interface IPending  // no hashCode method
public interface IRef extends IDeref  // no hashCode method
public interface IReference extends IMeta  // no hashCode method
interface Runnable  // in java.lang  - no hashCode method
public interface Settable  // no hashCode method
interface Serializable  // in java.io - no hashCode method

public class AReference implements IReference
  // class AReference has no hashCode method
  public abstract class ARef extends AReference implements IRef
    // class ARef has no hashCode method
    public class Agent extends ARef
      // class Agent has no hashCode method
    final public class Atom extends ARef implements IAtom2
      // class Atom has no hashCode method
      // so: class Atom should inherit hashCode impl. from class Object
    public class Ref extends ARef implements IFn, Comparable<Ref>, IRef
      // class Ref has no hashCode method
    public final class Var extends ARef implements IFn, IRef, Settable, Serializable
      // class Var has no hashCode method


public class Delay implements IDeref, IPending
  // class Delay has no hashCode method


thalia.core=> (def p1 (promise))
#'thalia.core/p1
thalia.core=> (class p1)
clojure.core$promise$reify__8144
thalia.core=> (supers (class p1))
#{java.lang.Object clojure.lang.IFn java.util.concurrent.Callable java.lang.Runnable clojure.lang.IPending clojure.lang.IObj clojure.lang.IDeref clojure.lang.IBlockingDeref clojure.lang.IMeta}

#{java.lang.Object  // because all Java objects are instances of Object or one of its subclsses
clojure.lang.IFn  // because of reify args in clojure.core/promise
java.util.concurrent.Callable  // because IFn extends Callable
java.lang.Runnable  // because IFn extends Callable
clojure.lang.IPending  // because of reify args in clojure.core/promise
clojure.lang.IObj  // because reify documents that created objects always implement IObj
clojure.lang.IDeref  // because of reify args in clojure.core/promise
clojure.lang.IBlockingDeref  // because of reify args in clojure.core/promise
clojure.lang.IMeta  // because IObj extends IMeta
}

```


TBD: Behavior for types defined via `deftype`.
TBD: Does it ever make sense to define `equiv` for such things?


### Example showing Clojure `=` forcing evaluation of lazy sequences

```clojure
user=> (defn print-it [x]
         (println x)
         x)
#'user/print-it

user=> (def lazy1 (map print-it (range 100)))
#'user/lazy1

;; Because range returns a 'chunked' lazy sequence, doing a 'take 5'
;; on it causes the evaluation of 32 of its elements, not just the
;; first 5.

user=> (take 5 lazy1)
0
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
(0 1 2 3 4)


;; The already-evaluated portion is stored in memory, so doing 'take
;; n' for n up to 32 will get the already-evaluated sequence elements
;; from memory, without re-evaluating them.

user=> (take 32 lazy1)
(0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31)

;; Let us make a version of the lazy sequence that is not chunked,
;; using a function called 'unchunk' that has several variations.
;; This one is copied from the math.combinatorics library:

;; https://github.com/clojure/math.combinatorics/blob/master/src/main/clojure/clojure/math/combinatorics.cljc#L190-L200

user=> (defn unchunk [s]
         (lazy-seq
           (when (seq s)
             (cons (first s) (unchunk (rest s))))))
#'user/unchunk

user=> (def lazy2 (map print-it (unchunk (range 100))))
#'user/lazy2

;; lazy2 only evaluates the sequence elements requested.

user=> (take 5 lazy2)
0
1
2
3
4
(0 1 2 3 4)

user=> (take 4 lazy2)
(0 1 2 3)

user=> (take 10 lazy2)
5
6
7
8
9
(0 1 2 3 4 5 6 7 8 9)

user=> (def lazy3 (map print-it (unchunk (range 100))))
#'user/lazy3

;; This comparison stops after evaluating element 2 of the lazy
;; sequence, since it is not equal to the corresponding element of the
;; vector.

user=> (= lazy3 [0 1 -3])
0
1
2
false

;; If you reverse the order of comparison, the equiv method of class
;; APersistentVector is called instead.  When comparing two vectors,
;; you can quickly tell they are not equal if they have different
;; number of elements, and so that is what the equiv method for that
;; class does.  Finding the count of number of elements in the lazy
;; sequence causes it to be fully evaluated.

;; See method doEquiv in class APersistentVector of the Clojure/Java
;; implementation:
;; https://github.com/clojure/clojure/blob/master/src/jvm/clojure/lang/APersistentVector.java#L89

user=> (= [0 1 -3] lazy3)
3
4
5
[... many lines deleted ...]
97
98
99
false

user=> (def lazy4 (map print-it (unchunk (range 100))))
#'user/lazy4

;; The equiv methods for Clojure queues, lists, and lazy sequences do
;; not use this count check, so will not fully evaluate the lazy
;; sequence unless needed to determine whether the two sequences have
;; equal elements.

user=> (def lazy4 (map print-it (unchunk (range 100))))
#'user/lazy4
user=> (= '(0 1 -3) lazy4)
0
1
2
false

user=> (= (conj clojure.lang.PersistentQueue/EMPTY 0 1 2 3 -4) lazy4)
3
4
false

user=> (= (range 7) lazy4)
5
6
7
false
```
