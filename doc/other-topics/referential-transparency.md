# Referential transparency, with examples in Clojure and Haskell

Note: I am not familiar enough with the literature and meaning of some
of the terms mentioned in here to be authoritative on these matters.
I am writing this as a way of learning.

Here are a few descriptions given of referential transparency from
several books on functional programming:

    http://www.cas.mcmaster.ca/~kahl/reftrans.html

The link below has several more answers to the question "What is
referential transparency?", going back to the origins of the term in
analytical philosophy, and their influence on Landin and Strachey when
they were defining semantics of programming languages:

    http://stackoverflow.com/questions/210835/what-is-referential-transparency

At least some people seem to take the view that referential
transparency means something similar to this:

    A language is referentially transparent if, whenever two values
    `x` and `y` are equal, one can be substituted for the other in any
    expression where they appear, and the resulting expressions will
    evaluate to equal values.

Or, perhaps that referential transparency is a more general property,
with the above property as a special case.


# Background

## The author's knowledge

Author: Andy Fingerhut (andy_fingerhut@alum.wustl.edu)

I am quite familiar with Clojure, but only familiar with a small
portion of Haskell.  I know what immutable values and persistent data
structures are, and I _think_ I know what a pure function is.  I would
describe it informally as: the return value of a pure function is a
deterministic function of its arguments (function in the mathematical
sense of the word), depending upon nothing except the values of the
arguments, and perhaps other pure functions defined earlier.

I have read Henry Baker's paper on egal, but can't claim to understand
every one of its nuances.

I have heard of denotational and operational semantics, but haven't
studied them.  Similarly for monads.

; comment, probably to be removed
; The notation below intentionally looks more like Clojure than Haskell.
; `(some-fn arg1 arg2)` represents a function call to a function named
; `some-fn`.  I have avoided using the standard Clojure names for
; functions, to avoid any confusion between how they are defined, and
; how the functions described here are defined.  If I want to refer to a
; particular Clojure function, I will qualify it with its full namespace
; name, e.g. `clojure.core/contains?` (except in section titles, where
; they are always in namespace `clojure.core`).


# Motivation

Occasionally on the Clojure Google group a person will express
displeasure that Clojure's definition of `=` returns true for some
pair of values `v1` and `v2`, but other functions return different
(i.e. not `=`) values when you replace `v1` with `v2` as an argument.

Below are several cases of this.  I find example 3 to be the most
interesting, since it is more subtle than the others, appears to exist
in Haskell as well as in Clojure, and hopefully there is more to be
learned from it.


## Example 1: Interaction between Clojure's `conj` and `=`

The most common example raised is that `conj` adds elements to lists
at the beginning, but to vectors at the end.  Despite this difference,
`=` returns true if given a list and a vector, if the items in them
are equal, and in the same order.

```clojure

;; The behavior below has been consistent for many versions of
;; Clojure, and seems unlikely to change.  In case it does change,
;; here is the version used for this session.

user=> (clojure-version)
"1.6.0"

;; Lists and vectors are different types.  Vectors provide O(log n)
;; time access to any item given an integer index (base 32 log), and
;; update of the value at any index, but lists do not.

user=> (class '(1 2 3))
clojure.lang.PersistentList
user=> (class [1 2 3])
clojure.lang.PersistentVector

;; Despite the differences in these types, Clojure's = has long
;; returned true when comparing lists and vectors, if they contain
;; equal items in the same order.

user=> (= '(1 2 3) [1 2 3])
true

;; conj is documented to add items at different 'places' for
;; collections of different types.  At the beginning for lists, at the
;; end for vectors.

user=> (conj '(1 2 3) 0)
(0 1 2 3)
user=> (conj [1 2 3] 0)
[1 2 3 0]

;; Unsurprisingly, the return values of these two calls are unequal
;; according to =

user=> (= (conj '(1 2 3) 0) (conj [1 2 3] 0))
false
```

This occurs due to an explicit design choice in Clojure.  `conj` pays
attention to the particular type of collection it is working on, and
behaves differently for different types.  `=` was designed to be able
to return true when comparing lists and vectors, I believe because it
was considered useful and convenient.  Many Clojure functions like
`map` operate on vectors and lists, but return sequences.  It would be
inconvenient when comparing return values to do explicit type
conversions, or use a different equality function depending upon the
types of its arguments.

I am not aware of anything like the example above in Haskell.  I would
guess most Haskell developers would consider allowing equality between
different types an unpleasant hack, and I think that unless one
created a union type that included both lists and vectors, one would
not be able to implement this in Haskell at all.

The root of this behavior: `=` is ignoring the type of its arguments
when comparing lists, vectors, and sequences, by design choice.


## Example 2: Interaction between Clojure's metadata and `=`

Clojure immutable collection values can have metadata associated with
them [1].  Metadata is a Clojure map value that is 'attached' to a
collection value.  It is ignored when comparing collections or dealing
with their values in almost any way, except for a few functions like
`meta` and `vary-meta` that can retrieve the metadata, or return a new
collection with new metadata altered in a specified way from the
original.  The returned collection is equal to the original according
to `=`.

[1] Other things besides collection values can have metadata, too, but
we will ignore that here.

```clojure

;; The behavior below has been consistent for many versions of
;; Clojure, and seems unlikely to change.  In case it does change,
;; here is the version used for this session.

user=> (clojure-version)
"1.6.0"

;; Create a vector with items 1, 2, 3, and metadata as a map with one
;; key :key1, and its associated value "value1"

user=> (def v1 (with-meta [1 2 3] {:key1 "value1"}))
#'user/v1

;; v2 is a vector with the same items, but with different metadata.

user=> (def v2 (with-meta [1 2 3] {:key2 "value2"}))
#'user/v2

;; They look the same, since by default metadata is not shown, and
;; they are equal according to =

user=> v1
[1 2 3]
user=> v2
[1 2 3]
user=> (= v1 v2)
true

;; But meta, which returns only the metadata associated with its
;; argument, returns different values.

user=> (meta v1)
{:key1 "value1"}
user=> (meta v2)
{:key2 "value2"}
user=> (= (meta v1) (meta v2))
false
```

The root of this behavior: `=` is ignoring the metadata of its
arguments when comparing collections, by design choice.

I am not aware of anything like metadata in Haskell.  It would not be
surprising if someone had developed something similar.


## Example 3: Interaction between Clojure's hash sets, `=`, and `seq`

The following example is a bit more subtle, and I think it has a
corresponding data structure implementation in Haskell with similar
behavior.

```clojure

;; Showing the Clojure version, since the function hash changed from
;; version 1.5.1 to 1.6.0, and it may change again in a future
;; version.

user=> (clojure-version)
"1.6.0"

;; Demonstrate two particular integers that have equal hash values
;; according to the function hash

user=> (hash 0)
0
user=> (hash 92612215)
0

;; Create 2 hash sets with these integers added to them via conj, but
;; in opposite orders.

user=> (def empty-hash-set (hash-set))
#'user/empty-hash-set
user=> empty-hash-set
#{}
user=> (def s1 (conj empty-hash-set 0 92612215))
#'user/s1
user=> s1
#{0 92612215}
user=> (def s2 (conj empty-hash-set 92612215 0))
#'user/s2
user=> s2
#{92612215 0}

;; The sets are considered equal by =, because they both contain the
;; same elements.

user=> (= s1 s2)
true

;; The implementation of hash-set is roughly a trie lookup based on
;; the element's hash value, and if there are multiple elements with
;; the same hash value, a linked list of those elements.  The order of
;; the elements in that linked list depends upon the order that the
;; elements were added to the set.

;; Thus sets equal according to = may be 'distinguishable' by seq,
;; which returns a sequence of a set's elements in some order.

user=> (seq s1)
(0 92612215)
user=> (seq s2)
(92612215 0)

;; As you might expect, sequences with elements in different orders
;; are not equal, according to =

user=> (= (seq s1) (seq s2))
false
```

Haskell's Data.HashSet [2] is similar.  The corresponding names are:

* `hash-set` -> `HashSet`
* `conj` -> `insert`
* `seq` -> `toList`
* `=` -> `==`

The particular integers used in the example below are different
because Haskell's hash function is different than Clojure's.

[2] https://hackage.haskell.org/package/hashmap-1.0.0.2/docs/Data-HashSet.html

```haskell
% ghci
GHCi, version 7.8.3: http://www.haskell.org/ghc/  :? for help
Loading package ghc-prim ... linking ... done.
Loading package integer-gmp ... linking ... done.
Loading package base ... linking ... done.

Prelude> import Data.Hashable
Prelude Data.Hashable> import Data.HashSet

-- Found two integers that have the same hash function

Prelude Data.Hashable Data.HashSet> hash 6860296727119291693
Loading package array-0.5.0.0 ... linking ... done.
Loading package deepseq-1.3.0.2 ... linking ... done.
Loading package bytestring-0.10.4.0 ... linking ... done.
Loading package text-1.1.0.0 ... linking ... done.
Loading package hashable-1.2.3.2 ... linking ... done.
6860296727119291693
Prelude Data.Hashable Data.HashSet> hash 9223372036854775808
6860296727119291693
Prelude Data.Hashable Data.HashSet> hash 6860296727119291693 == hash 9223372036854775808
True

-- Create two HashSets, adding those two integers in a different order
-- for each one.

Prelude Data.Hashable Data.HashSet> let s1=fromList[6860296727119291693,9223372036854775808]
Loading package unordered-containers-0.2.5.1 ... linking ... done.
Prelude Data.Hashable Data.HashSet> let s2=fromList[9223372036854775808,6860296727119291693]
Prelude Data.Hashable Data.HashSet> s1
fromList [6860296727119291693,9223372036854775808]
Prelude Data.Hashable Data.HashSet> s2
fromList [9223372036854775808,6860296727119291693]

-- They are equal according to the == defined for Data.HashSet

Prelude Data.Hashable Data.HashSet> s1 == s2
True

-- but toList returns the integers in a different order for each one

Prelude Data.Hashable Data.HashSet> toList s1
[6860296727119291693,9223372036854775808]
Prelude Data.Hashable Data.HashSet> toList s2
[9223372036854775808,6860296727119291693]

-- and of course lists with items in different orders are not equal in
-- Haskell, any more than they are in Clojure.

Prelude Data.Hashable Data.HashSet> toList s1 == toList s2
False
```

The root of this behavior is a combination of the following facts.

1. The implementation relies only on hashing and equality of set
   elements, not on anything more, e.g. a total order defined for the
   elements.

2. `=` is defined to be true for sets with the same mathematical set
   of elements, regardless of the order that elements were added.

3. `seq` returns an ordered sequence of all of a set's elements.

Below we discusss alternatives to each of these.


(1)

The implementation relies only on equality of elements, and a hash
function consistent with equality for those elements.  It does not
require or use a total order for the set elements.  Without such a
total order, there does not seem to be anything an implementation can
do to return a consistent element order for `seq` across `=` sets,
regardless of the order that elements were added.

If a total order were supplied for the set elements, it would be easy
to make `seq` return set elements in increasing order (or at least
increasing order among colliding elements), and `=` sets would always
return the same value.

(2)

The definition of `=` in Clojure returns true for two sets with the
same elements, even if the implementation has colliding elements in
different orders in the linked lists.  This is a very natural desire
for a definition of `=` for sets.

One could imagine instead defining `structural-=`, a function that
returns false for two hash sets when colliding elements are in linked
lists in different orders, even though the sets contain the same
elements.

I am not arguing that the proposed `structural-=` would be a useful
definition of `=`, but it does have the property that if two sets are
`structural-=`, `seq` can easily be implemented to return the same
list for both of them (one only needs to traverse all element hash
values in some total order, e.g. increasing integer order).


In the Haskell library Data.HashSet, it defines `==` similar to
Clojure's `=` for hash sets.  That is, two hash sets are equal if they
contain equal elements, regardless of the order they were added.

There is also what I think is called 'definitional equality' in
Haskell, which is `=`.  At least some Haskell developers say that
referential transparency only applies to values that are `=` to each
other, not for values that are `==` to each other.  This is at least
implied in the following paragraph from [3] (especially the last
sentence):

    In fact, we should provide our own definitions of equality and
    ordering predicates only with some trepidation, being careful to
    maintain the expected algebraic properties of equivalence
    relations and total orders.  An intransitive (==) predicate, for
    example, could be disastrous, confusing readers of the program and
    confounding manual or automatic program transformations that rely
    on the (==) predicate's being an approximation to definitional
    equality.  Nevertheless, it is sometimes necessary to provide Eq
    or Ord instances different from those that would be derived;
    probably the most important example is that of an abstract data
    type in which different concrete values may represent the same
    abstract value.

In particular, I think the last sentence is true of Data.HashSet.
Multiple different concrete values, meaning different hash sets
represented with colliding set elements in different orders, represent
the same abstract value, that of the 'mathematical set' that has no
notion of order.

Similarly, it is common for Haskell implementations of various
abstract data types to have many different binary tree structures (for
example) that represent the same abstract value.  `=` is only true
between trees with the same structure, but `==` is often defined to be
true for many different tree structures, as long as it makes sense for
the semantics of the desired data type.

It also seems to be common to do one of the following two things with
such a Haskell type:

(2a) Define it in a module, and avoid exporting` any functions that
make it visible to outside observers that two not-structurally-equal
values (i.e. values `x` and `y` for which `x=y` is false), but
abstract-equal values (i.e. `x == y` is true), have different return
values.  That is, hide the structural differences as internal
implementation details.  This gives referential transparency for both
`=` and `==` for the data type.

Someone with this preference for a library would avoid exporting a
function like `toList` for a set implementation that has no total
order on the elements.

(2b) Define it in a module, and explicitly document for each exported
function whether it can permit internal structural differences between
`==` values to 'leak out'.

This appears to be the approach for a few functions exported by the
Edison library by Chris Okasaki [5].  The `toSeq` function for
collections where the elements can be observed is documented to be
'ambiguous' [6], by which Okasaki means:

    "For ambiguous functions, the result of applying the function may
    depend on otherwise unobservable internal state of the data
    structure, such as the actual shape of a balanced tree." [5]


(3)

The function `seq` returns an ordered sequence of a set's elements.

If no such function were implemented for hash sets, and no other
function defined for hash sets made two `=` sets distinguishable from
each other by their return values, then this particular issue would
not exist.

The disadvantage is obvious: set types are much less useful if there
is no way to enumerate their elements.

(4)

This one I do not understand yet.  I would greatly appreciate anyone
knowledgeable enough and interested to take the time to attempt to
explain it to me.  It is the last statement from user "ski" on the
Haskell IRC channel below that I do not understand.  (Excerpted from
conversation at [4]).

    ski: I would consider "a hash set type that has an (==) defined to
    be true when two sets contain the same set of elements, even if a
    function like toList could return differently ordered lists of
    items for two (==) sets x and y" broken

    andyf: A couple of hours ago, some people believed that
    Data.HashSet was broken in that way.

    andyf: So you would prefer a hash set that had nothing like a
    toList function defined for it?  Doesn't that make the set fairly
    unuseful if you have no way to enumerate its elements?

    ski: You could have `toAscList :: Ord a => HashSet a -> [a]' as
    well as `getList :: HashSet a -> NonDet a', where `NonDet' would
    be a monad expressing the indeterminacy of the operation (there
    would be an operation `selectNonDet :: NonDet a -> IO a', and
    perhaps an `unsafeSelectNonDet :: NonDet a -> a')


References:

[3] https://www.haskell.org/tutorial/stdclasses.html

Some IRC discussion on the #haskell IRC channel (search for "andyf"
and replies to me) about Example 3 below are at [4].

[4] http://ircbrowse.net/day/haskell/2015/04/20

[5] http://hackage.haskell.org/package/EdisonAPI-1.2.1/docs/Data-Edison.html

[6] http://hackage.haskell.org/package/EdisonAPI-1.2.1/docs/Data-Edison-Coll.html

Published academic papers that user 'ski' on the #haskell IRC channel
recommended reading.

[7] "Referential Transparency, Definiteness and Unfoldability", Harald
Søndergaard and Peter Sestoft, Acta Informatica 27, pp. 505-517 (1990)
http://www.cs.tufts.edu/~nr/cs257/archive/peter-sestoft/ref-trans.pdf

I scanned the paper below, and it seems to require more knowledge of
lambda calculus and proof techniques related to them than I am
familiar with.  Probably requires significant time to gain
understanding of it.

[8] "What is a Purely Functional Language?", Amr Sabry, J. Functional
Programming, vol. 1 no. 1, January 1993, Cambridge University Press
https://www.cs.indiana.edu/~sabry/papers/purelyFunctional.ps
