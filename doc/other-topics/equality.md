# Equality

This document discusses the concept of equality in Clojure, including the functions `=`, `==`, and `identical?`, and how they differ from Java's `equals` method.  It also has some description of Clojure's `hash`, and how it differs from Java's `hashCode`. The beginning of this guide provides a summary of the most important information for quick reference followed by a much more extensive review of the details.

_Information in this guide describes the behavior of Clojure 1.9.0 unless noted otherwise._


## Summary

Clojure's `=` is true when called with two values, if:

* Both arguments are numbers in the same 'category', and numerically
  the same, where category is one of (integer or ratio), floating
  point, or BigDecimal.
* Both arguments are _sequential_ (sequences, lists, vectors, queues, or
  Java collections implementing `java.util.List`) with `=`
  elements in the same order.
* Both arguments are sets (including Java sets implementing `java.util.Set`),
  with `=` elements, ignoring order.
* Both arguments are maps (including Java maps implementing `java.util.Map`),
  with `=` keys *and* values, ignoring entry order.
* Both arguments are symbols, or both keywords, with equal namespaces and names.
* Both arguments are the same type defined with `deftype`.  The type's
  `equiv` method is called and its return value becomes the value of
  `(= x y)`.
* Both arguments are refs, vars, or atoms, and they are the identical
  object, i.e. `(identical?  x y)` is true.
* For other types, Java's `x.equals(y)` is true.  The result should be
  unsurprising for `nil`, booleans, characters, and strings.

Clojure's `==` is intended specifically for numerical values:

* `==` can be used with numbers across different number categories (such as `0` and `0.0`).
* If any value being compared is not a number, an exception is thrown.

If you call `=` or `==` with more than two arguments, the result will
be true when all consecutive pairs are `=` or `==`.  `hash` is
consistent with `=`, with the exceptions given below.

Exceptions, or possible surprises:

* When comparing collections with `=`, numbers within the collections
  are also compared with `=`, so the three numeric categories above
  are significant.
* `hash` is consistent with `=` for numbers, except for special float and
  double values.  This leads to odd behavior if you use them as set
  elements or map keys.  Convert floats to doubles with `(double x)` to avoid this issue.
* 'Not a Number' values `Float/NaN` and `Double/NaN` are not `=` or
  `==` to anything, not even themselves.  This leads to odd behavior
  if you use them as set elements or map keys.
* TODO: -0.0 / 0.0 and +Infinity, -Infinity
* Clojure regex's, e.g. #"a.*bc", are implemented using Java
  `java.util.regex.Pattern` objects, and Java's `equals` on two
  `Pattern` objects returns `(identical? re1 re2)`.  Thus `(= #"abc"
  #"abc")` returns false, and only returns true if two regex's happen
  to be the same identical object in memory.
  _Recommendation:_ Don't use regex instances as set elements or keys.  If you feel the need to,
  consider converting them to strings first, e.g. `(str #"abc")` -> "abc".
* `hash` is not consistent with `=` for immutable Clojure collections
  and their mutable Java counterparts.  Comparing a Clojure immutable
  set to a Java object implementing `java.util.Set` with equal
  elements will be `=`, but their `hash` values will usually be
  different (see
  [CLJ-1372](http://dev.clojure.org/jira/browse/CLJ-1372)).
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

If you want to test for numeric equality across numeric partitions, use `==`.  See the section "Numbers" below for details.

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
user> (def s1 (hash-set 1 2000 30000))
#'user/s1
user> s1
#{1 30000 2000}
user> (def s2 (sorted-set 30000 2000 1))
#'user/s2
user> s2
#{1 2000 30000}
user> (= s1 s2)
true
```

Two maps are equal if they have the same set of keys, and each key
maps to equal values in each map. As with sets, maps are unordered
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

Clojure `=` behaves the same as Java's `equals` for all types except
numbers and Clojure collections.

TBD: Behavior for records defined via `defrecord`.
TBD: Behavior for types defined via `deftype`.
TBD: Does it ever make sense to define `equiv` for such things?

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

Like keywords, Clojure refs, vars, and atoms are only equal to others
if they are the same object.  Unlike keywords, these types are
mutable.

TBD: Is there any issue with using them as set elements or map keys?
Do they have consistent hash values even when their contents change?
If so, how?


## Numbers

Java `equals` is only true for two numbers if the types and numeric
values are the same.  Thus `equals` is false even for Integer 1 and
Long 1, because they have different types.  Exception: Java `equals`
is also false for two BigDecimal values that are numerically equal if
they have different scales, e.g. 1.50M and 1.500M are not equal.  This
behavior is documented for BigDecimal method
[`equals`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-).

Clojure `=` is true if the 'category' and numeric values are the same.
Category is one of:

* integer: all integer types including BigInteger and BigInt, or ratios (Java type Ratio)
* floating: Float and Double
* decimal: BigDecimal

So `(= (int 1) (long 1))` is true because they are in the same integer
category, but `(= 1 1.0)` is false because they are in different
categories (integer and floating).  While integers and ratios are in
separate categories in the Clojure implementation, for the purposes of
`=` they are effectively in the same category.  This is because ratios
are auto-converted to BigInts if they are whole numbers.  Thus any
Clojure number that is a still a ratio cannot equal any integer, so
`=` always gives the correct numerical answer when comparing two such
numbers (false).

Clojure also has `==` that is only useful for comparing numbers.  It
returns true whenever `=` does.  It also returns true for numbers that
are numerically equal, even if they are in different categories.  Thus
`(= 1 1.0)` is false, but `(== 1 1.0)` is true.

Why does `=` have different categories for numbers, you might wonder?
Best educated guess, unconfirmed: It would not be easy to make `hash`
consistent with `=` if it behaved like `==` (see section "Equality and
hash" below).  Imagine trying to write `hash` such that it was
guaranteed to return the same hash value for all of `(float 1.5)`,
`(double 1.5)`, BigDecimal values 1.50M, 1.500M, etc. and the ratio
`(/ 3 2)`.

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
even equal to itself.

```clojure
user> (Math/sqrt -1)
Double/NaN
user> (= Double/NaN Double/NaN)
false
user> (== Double/NaN Double/NaN)
false
```

This leads to some odd behavior if this "value" appears in your data.
While there is no error adding `NaN` as a set element or a key in a
map, you cannot then search for it and find it.  You also cannot
remove it using functions like `disj` or `dissoc`.  It will appear
normally in sequences of those collections.

```clojure
user> (def s1 #{1.0 2.0 Double/NaN})
#'user/s1
user> s1
#{2.0 1.0 Double/NaN}
user> (s1 1.0)
1.0
user> (s1 1.5)
nil
user> (s1 Double/NaN)
nil

user> (disj s1 2.0)
#{1.0 Double/NaN}
user> (disj s1 Double/NaN)
#{2.0 1.0 Double/NaN}
```

This also means that two sets with the same elements will not be `=`,
if they contain `NaN`:

```clojure
user> (def s2 #{Double/NaN 2.0 1.0})
#'user/s2
user> s2
#{2.0 1.0 Double/NaN}
user> (= s1 s2)
false
```

Similar issues exist if you create a map containing `NaN` as a key or
value.

Java has a special case in its `equals` method for floating point
values that makes `NaN` equal to itself.  Clojure `=` and `==` do not.

```clojure
user> (.equals Double/NaN Double/NaN)
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
1014033862
user> (hash (seq ["a" 5 :c]))
1014033862
user> (hash '("a" 5 :c))
1014033862
```

However, since `hash` is not consistent with `=` in Clojure 1.6.0 when
comparing Clojure immutable collections with Java mutable collections,
mixing the two can lead to undesirable behavior, as shown in the
examples below.

```clojure
;; The return values below are for Clojure 1.6.0.  Comments show which
;; of these differ from Clojure 1.5.1.

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

;; Their hash values were the same with 1.5.1, but are different in
;; 1.6.0.  `hash` was changed to avoid some common cases with too many
;; hash collisions in Clojure 1.5.1.

user=> (hash java-list)
30817
user=> (hash clj-vec)
736442005                           ; was 30817 with Clojure 1.5.1

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
false                               ; was true with Clojure 1.5.1
user=> (= (hash-set java-list) (hash-set clj-vec))
false                               ; was true with Clojure 1.5.1

user=> (get (hash-map java-list 5) java-list)
5
user=> (get (hash-map java-list 5) clj-vec)
nil                                 ; was 5 with Clojure 1.5.1

user=> (conj #{} java-list clj-vec)
#{[1 2 3] [1 2 3]}                  ; was #{[1 2 3]} with Clojure 1.5.1
user=> (hash-map java-list 5 clj-vec 3)
{[1 2 3] 5, [1 2 3] 3}              ; was {[1 2 3] 3} with Clojure 1.5.1
```

Similar behavior occurs for Java collections that implement
`java.util.List`, `java.util.Set`, and `java.util.Map`.  It also
occurs for any values for which Clojure's `hash` is not consistent
with `=`.


### Bugs

(Clojure 1.6.0 through Clojure 1.9.0)
[CLJ-1372](http://dev.clojure.org/jira/browse/CLJ-1372) described with
examples above.

(Clojure 1.6.0 through Clojure 1.9.0)
[CLJ-1649](http://dev.clojure.org/jira/browse/CLJ-1649): For some
Float and Double values that are `=` to each other, their `hash`
values are inconsistent:

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


## Implementation details

References to implementation code: For `=`, Util.java `equiv`,
Numbers.java `equal` and `category`.  For `==`, Numbers.java `equiv`.


## Defining equality for your own types

See these for examples on how to do this, and much more:

* [data.priority-map](https://github.com/clojure/data.priority-map)
* [flatland/ordered-set](https://github.com/flatland/ordered)


## References

The paper ["Equal Rights for Functional Objects, or, the More Things
Change, The More They Are the
Same"](http://home.pipeline.com/~hbaker1/ObjectIdentity.html) by Henry
Baker includes code written in Common Lisp, but the idea of equality
making sense for immutable values, and not as much sense for mutable
objects, is independent of programming language:


TBD: Other Clojure tickets to mention somewhere in this article,
perhaps:

* [CLJ-750][CLJ-750] clojure.lang.MapEntry violates .equals and .hashCode contracts of HashMap.Entry; leads to non-reflexive .equals, etc.
* [CLJ-1059][CLJ-1059] PersistentQueue doesn't implement java.util.List, causing nontransitive equality
* [CLJ-1242][CLJ-1242] = on sorted collections with different key types incorrectly throws
* [CLJ-1860][CLJ-1860] 0.0 and -0.0 compare equal but have different hash values

[CLJ-750]: http://dev.clojure.org/jira/browse/CLJ-750
[CLJ-1059]: http://dev.clojure.org/jira/browse/CLJ-1059
[CLJ-1242]: http://dev.clojure.org/jira/browse/CLJ-1242
[CLJ-1860]: http://dev.clojure.org/jira/browse/CLJ-1860

TBD: Mention Clojure `identity?`, same as Java object identity `==`,
and how it is typically only a good idea to use it in Java interop
cases where you need Java `==`.  It is better to use Clojure `=` value
equality in most cases.


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
