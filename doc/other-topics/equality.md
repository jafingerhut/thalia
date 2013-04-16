# Equality

Equality in Clojure is most often tested using `=`.  It returns true
for many values that don't have the same type as each other, by
design.

```clojure
    user> (= 2 (+ 1 1))
    true
    user> (= (str "fo" "od") "food")
    true
```

Some numbers of different types are not `=` to each other.  See the
section "Numbers" below for details.

```clojure
    user=> (= 2 2.0)
    false
```

There is only one instance of each keyword, which is ensured by the
implementation of the `Keyword.intern()` method..  Clojure `=`
defaults to Java's `equals` for all types except numbers and Clojure
collections, and Java's `equals` defaults to object identity
(reference to same object).  Like keywords, Clojure refs, vars, and
atoms are only equal to others if they are the same object.

Symbols can only be equal to other symbols, and only if they have the
same namespace name and symbol name, stored as interned strings.  The
strings being interned means that these equality comparisons can be
via Java's object identity, with no need to compare the entire string
names.


Sequences, vectors, lists, and queues with equal elements in the same
order are equal, even though they don't behave the same when used with
other functions.  This has been true for a long time in Clojure, and
is considered a convenience given the prevalence of seqeunces.

```clojure
    user=> (range 3)
    (0 1 2)
    user=> (= (range 3) [0 1 2])
    true
    user=> (conj (range 3) 4)
    (4 0 1 2)
    user=> (conj [0 1 2] 4)
    [0 1 2 4]
```

Two sets are equal if they have equal elements, regardless of the
order they were added, and whether the sets are sorted or not.

```clojure
    user=> (def s1 (hash-set 1 2000 30000))
    #'user/s1
    user=> s1
    #{1 30000 2000}
    user=> (def s2 (sorted-set 30000 2000 1))
    #'user/s2
    user=> s2
    #{1 2000 30000}
    user=> (= s1 s2)
    true
```

Two maps are equal if they have the same set of keys, and each key
maps to equal values in each map.  The order that keys were added, and
whether the maps are sorted, is irrelevant.

```clojure

```

Java has `equals` to compare pairs of objects for equality.

Java has a hash function `hashCode` that is _consistent_ with this
notion of equality, meaning that for any two objects `x` and `y` where
`equals(x,y)` is `true`, `hash(x)` and `hash(y)` are equal, too.  This
hash consistency property makes it possible to use `hashCode` to
implement hash-based data structures like maps and sets using hashing
techniques internally.  For example, a hash table could be used to
implement a set, and it will be guaranteed that objects with different
`hashcode` values can be put into different hash buckets, and objects
in different hash buckets will never be equal to each other.

Clojure has `=` and `hash` for similar reasons that Java has `equals`
and `hashCode`.  However, Clojure strives for considering two objects
to be equal if their values are equal, whereas Java typically
considers two objects to be equal if their types and values are equal.


## Numbers

Java `equals` is only true if the types and numeric values are the
same.  Thus equals is false even for Integer 1 and Long 1, because
they have different types.

Exception: Java equals is also false for two BigDecimal values that
are numerically equal if they have different scales, e.g. 1.50M and
1.500M are not equal.  This is behavior is documented for BigDecimal
method equals.

Clojure `=` is true if the 'category' and numeric values are the same.
Category is one of:

* integer: all integer types including BigInteger and BigInt
* floating: Float and Double
* ratio: Ratio
* decimal (BigDecimal)

So `(= (int 1) (long 1))` is true because they are in the same integer
category, but `(= (long 1) (double 1.0))` is false because they are in
different categories (integer and floating).  Note: If arithmetic
involving Clojure ratios results in an integer answer, it is
auto-converted to a BigInt, so even though it might seem unfortunate
that `=` always returns false when comparing integers and ratios, such
values can never be numerically equal, anyway.

Clojure 1.5.1 inherits Java's exception for BigDecimal with equal
numeric value but different scales, i.e. `(= 1.50M 1.500M)` is false.
Ticket CLJ-1118 might change this.

Clojure also has `==` that is only useful for comparing numbers.  It
returns `true` whenever `=` does, but also for numbers that are
numerically equal, even if they are in different categories.  Thus `(=
(long 1) (double 1.0))` is false, but `(== (long 1) (double 1.0))` is
true.

Why does `=` have different categories for numbers, you might wonder?
Best educated guess, unconfirmed: It would not be easy to make `hash`
consistent with `=` if it behaved like `==`.  Imagine trying to write
`hash` such that it was guaranteed to return the same hash value for
all of `(float 1.5)`, `(double 1.5)`, BigDecimal values 1.50M, 1.500M,
etc. and Ratio `(/ 3 2)`.

Clojure uses `=` to compare values for equality when they are used as
elements in sets, or keys in maps.  Thus Clojure's four numeric
categories come into play if you use sets with numeric elements or
maps with numeric keys.

### Floating point numbers are usually approximations

Note that floating point values might behave in ways that surprise
you, if you have not learned of their approximate nature before.  This
is simply because they are represented with a fixed number of bits,
and thus many values cannot be represented exactly and must be
approximated (or be out of range).  This is true for floating point
numbers in any programming language.

    user> (def d1 (apply + (repeat 100 0.1)))
    #'user/d1
    user> d1
    9.99999999999998
    user> (== d1 10.0)
    false

There is a whole field called [Numerical Analysis][NumericalAnalysis]
dedicated to studying algorithms that use numerical approximation, and
libraries of Fortran code that are in heavy use because their order of
floating point operations is carefully crafted to give guarantees on
the difference of their approximate answers from the exact answers.

[NumericalAnalysis]: https://en.wikipedia.org/wiki/Numerical_analysis

### Floating point "Not A Number"

Clojure uses the underlying Java double-size (64-bit) floating point
numbers with representation and behavior defined by a standard, IEEE
754.  By this definition there is a special value "Not A Number"
[IEEE754NaN], Double/NaN, that is not even equal to itself.

[IEEE754NaN]: http://en.wikipedia.org/wiki/NaN

```clojure
    user=> (Math/sqrt -1)
    Double/NaN
    user=> (= Double/NaN Double/NaN)
    false
    user=> (== Double/NaN Double/NaN)
    false

```

This leads to some odd behavior.  While there is no error adding `NaN`
as a set element or a key in a map, you cannot then search for it and
find it, or remove it using the normal means.  It will still show up
in sequences of those collections.

```clojure
    user=> (def s1 #{1.0 2.0 Double/NaN})
    #'user/s1
    user=> s1
    #{2.0 1.0 Double/NaN}
    user=> (s1 1.0)
    1.0
    user=> (s1 1.5)
    nil
    user=> (s1 Double/NaN)
    nil

    user=> (disj s1 2.0)
    #{1.0 Double/NaN}
    user=> (disj s1 Double/NaN)
    #{2.0 1.0 Double/NaN}
```

This also means that two sets that look like they have the same set of
elements will not compare as equal, if they contain `NaN`:

```clojure
    user=> (def s2 #{Double/NaN 2.0 1.0})
    #'user/s2
    user=> s2
    #{2.0 1.0 Double/NaN}
    user=> (= s1 s2)
    false
```

Java has a special case in its `equals` method for doubles that makes
`NaN` equal to itself.

```clojure
    user=> (.equals Double/NaN Double/NaN)
    true
```

References to implementation code: For `=`, Util.java `equiv`,
Numbers.java `equal` and `category`.  For `==`, Numbers.java `equiv`.

### Possible bugs in Clojure 1.5.1

[CLJ-1118][CLJ-1118] mentioned above.

[CLJ-1036][CLJ-1036]: For some BigInteger values that are `=` to
numbers of other types, their `hash` values are inconsistent.  This is
also the case for some Float and Double values that are `=` to each
other:

```clojure
    user=> (= (int -1) (long -1) (bigint -1) (biginteger -1))
    true
    user=> (map hash [(int -1) (long -1) (bigint -1) (biginteger -1)])
    (0 0 0 -1)
    user=> (hash-map (long -1) :minus-one (biginteger -1) :oops)
    {-1 :minus-one, -1 :oops}

    user=> (= (float 1.0e9) (double 1.0e9))
    true
    user=> (map hash [(float 1.0e9) (double 1.0e9)])
    (1315859240 1104006501)
    user=> (hash-map (float 1.0e9) :float-one (double 1.0e9) :oops)
    {1.0E9 :oops, 1.0E9 :float-one}
```

[CLJ-1118]: http://dev.clojure.org/jira/browse/CLJ-1118
[CLJ-1036]: http://dev.clojure.org/jira/browse/CLJ-1036


## Strings, characters, booleans, symbols, keywords, nil

TBD


## Defining equality for your own types

TBD: Give examples from data.priority-map or flatland/ordered-set


TBD: Vectors and maps are both associative.  Is a map of integers to
values equal to a vector with the same values at the same indices?
Probably not.

TBD: Link to Henry Baker's paper on egal
