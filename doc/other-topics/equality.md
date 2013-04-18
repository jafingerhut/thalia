# Equality


## Version info

This document discusses the behavior of equality in Clojure 1.5.1,
including the functions `=`, `==`, and `identical?`, and how they
differ from Java's `equals` method.  It also has some description of
Clojure's `hash`, and how it differs from Java's `hashCode`.  Some of
the behavior here has definitely changed since Clojure 1.3, and
perhaps even since Clojure 1.4.  No attempt has yet been made to
document these differences.


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

If you want to test for numeric equality, `==` is probably what you
want.  See the section "Numbers" below for details.

Sequences, vectors, lists, and queues with equal elements in the same
order are equal:

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

This is so even though sequences, vectors, etc. are not the same type.
There are some functions like `conj` where you can give them arguments
that are all equal, but the return values are not equal:

```clojure
    user> (def s1 (range 3))
    #'user/s1
    user> (def v1 [0 1 2])
    #'user/v1
    user> (= s1 v1)
    true
    user> (= (conj s1 4) (conj v1 4))
    false
    user> (conj s1 4)
    (4 0 1 2)
    user> (conj v1 4)
    [0 1 2 4]
```

This property of `=` has been true for a long time in Clojure (TBD:
since version 1.0 or even before?), and can be a significant
convenience given the prevalence of sequences and vectors.

Two sets are equal if they have equal elements.  The order of the
elements is not considered, nor is whether the sets are sorted.

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
maps to equal values in each map.  The order of the key/value pairs is
not considered, nor is whether the maps are sorted.

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

Note that while it is possible to create a map that maps integers to
values, and do so in a way very similar to a vector, these are not
considered `=` in Clojure:

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
equivalence][UnicodeEquivalence] if you are interested.  There are
libraries like [ICU4J][ICU] (International Components for Unicode for
Java) that can help if you need to do this.

[UnicodeEquivalence]: http://en.wikipedia.org/wiki/Unicode_equivalence
[ICU]: http://site.icu-project.org/

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
[`equals`][Java-BigDecimal-equals].

[Java-BigDecimal-equals]: http://docs.oracle.com/javase/6/docs/api/java/math/BigDecimal.html#equals%28java.lang.Object%29

Clojure `=` is true if the 'category' and numeric values are the same.
Category is one of:

* integer: all integer types including BigInteger and BigInt
* floating: Float and Double
* ratio: Ratio
* decimal: BigDecimal

So `(= (int 1) (long 1))` is true because they are in the same integer
category, but `(= 1 1.0)` is false because they are in different
categories (integer and floating).  While it might seem unfortunate
that `=` always returns false when comparing integers and ratios, this
is always the correct numerical answer, because Clojure auto-converts
arithmetic results on ratios to BigInts if they are integers.

Clojure 1.5.1 inherits Java's exception for BigDecimal with the same
numeric value but different scales, i.e. `(= 1.50M 1.500M)` is false.
Ticket [CLJ-1118][CLJ-1118] might change this in a later version of
Clojure.

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
elements in sets, or keys in maps.  Thus Clojure's four numeric
categories come into play if you use sets with numeric elements or
maps with numeric keys.


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

There is a whole field called [Numerical Analysis][NumericalAnalysis]
dedicated to studying algorithms that use numerical approximation.
There are libraries of Fortran code that are used because their order
of floating point operations is carefully crafted to give guarantees
on the difference between their approximate answers and the exact
answers.

[NumericalAnalysis]: https://en.wikipedia.org/wiki/Numerical_analysis

If you want exact answers for at least some kinds of problems, ratios
or BigDecimals might suit your needs.  Realize that these require
variable amounts of memory if the number of digits required grow
(e.g. after many arithmetic operations), and significantly more
computation time.  They also won't help if you want exact values of pi
or the square root of 2.


### Floating point "Not A Number"

Clojure uses the underlying Java double-size floating point numbers
(64-bit) with representation and behavior defined by a standard, IEEE
754.  There is a special value [`NaN`][IEEE754NaN] ("Not A Number")
that is not even equal to itself.

[IEEE754NaN]: http://en.wikipedia.org/wiki/NaN

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
equality, meaning that for any two objects `x` and `y` where they
`equals` is true, `x.hashCode()` and `y.hashCode()` are equal, too.

This hash consistency property makes it possible to use `hashCode` to
implement hash-based data structures like maps and sets using hashing
techniques internally.  For example, a hash table could be used to
implement a set, and it will be guaranteed that objects with different
`hashCode` values can be put into different hash buckets, and objects
in different hash buckets will never be equal to each other.

Clojure has `=` and `hash` for similar reasons.  Since Clojure `=`
considers more pairs of things equal to each other than Java `equals`,
Clojure `hash` must return the same hash value for more pairs of
objects.  For example, `hash` always returns the same value regardless
of whether a sequence of `=` elements is in a sequence, vector, or
list:

```clojure
    user> (hash ["a" 5 :c])
    1014033862
    user> (hash (seq ["a" 5 :c]))
    1014033862
    user> (hash '("a" 5 :c))
    1014033862
```


### Possible bugs in Clojure 1.5.1

[CLJ-1118][CLJ-1118] mentioned above.

[CLJ-1036][CLJ-1036]: For some BigInteger values that are `=` to
numbers of other types, their `hash` values are inconsistent.  This is
also the case for some Float and Double values that are `=` to each
other:

```clojure
    user> (= (int -1) (long -1) (bigint -1) (biginteger -1))
    true
    user> (map hash [(int -1) (long -1) (bigint -1) (biginteger -1)])
    (0 0 0 -1)
    user> (hash-map (long -1) :minus-one (biginteger -1) :oops)
    {-1 :minus-one, -1 :oops}

    user> (= (float 1.0e9) (double 1.0e9))
    true
    user> (map hash [(float 1.0e9) (double 1.0e9)])
    (1315859240 1104006501)
    user> (hash-map (float 1.0e9) :float-one (double 1.0e9) :oops)
    {1.0E9 :oops, 1.0E9 :float-one}
```

Until this is fixed, you may be able to work around it by using
explicit conversion of `BigInteger`s to `BigInt`s and floats to
doubles in your code.

[CLJ-1118]: http://dev.clojure.org/jira/browse/CLJ-1118
[CLJ-1036]: http://dev.clojure.org/jira/browse/CLJ-1036


## Implementation details

References to implementation code: For `=`, Util.java `equiv`,
Numbers.java `equal` and `category`.  For `==`, Numbers.java `equiv`.


## Defining equality for your own types

See these for examples on how to do this, and much more:

* [data.priority-map][data.priority-map]
* [flatland/ordered-set][flatland-ordered-set]

[data.priority-map]: https://github.com/clojure/data.priority-map
[flatland-ordered-set]: https://github.com/flatland/ordered


## References

The paper ["Equal Rights for Functional Objects, or, the More Things
Change, The More They Are the Same"][BakerObjectIdentity] by Henry
Baker includes code written in Common Lisp, but the idea of equality
making sense for immutable values, and not so much for mutable
objects, is independent of programming language:

[BakerObjectIdentity]: http://home.pipeline.com/~hbaker1/ObjectIdentity.html


TBD: Mention Clojure `identity?`, same as Java object identity `==`,
and how it is typically only a good idea to use it in Java interop
cases where you need Java `==`.  It is better to use Clojure `=` value
equality in most cases.
