# Referential transparency, with examples in Clojure and Haskell

Note: I am not familiar enough with the literature and meaning of some
of the terms mentioned in here to be authoritative on these matters.
I am writing this as a way of learning.  Comments and corrections
welcome at the author's email address in the Background section.

At least some people seem to take the view that referential
transparency in a programming language means something similar to
this:

    A language is referentially transparent if, whenever two values
    `x` and `y` are equal, one can be substituted for the other in any
    expression where they appear, and the resulting expressions will
    evaluate to equal values.

Or, perhaps referential transparency is a more general property, with
the above property as a special case.

Note that this issue is similar to ensuring that a hash function is
consistent with equals in a language, i.e. if two values `x` and `y`
are equal, then `hash(x)` is equal to `hash(y)`.  This property is
necessary to ensure that hash table implementations using those values
as keys will work correctly.  Referential transparency is like saying
"and do that not only for the hash function, but for all functions".

Among other things, referential transparency for at least a particular
function `foo` is necessary to
[memoize](http://en.wikipedia.org/wiki/Memoization) that function.

The rough definition above is enough to understand the rest of this
document.  The links below in this section have more definitions and
discussion about referential transparency, and are here only for those
interested in diving deeper into the origins of the term.

[This link](http://www.cas.mcmaster.ca/~kahl/reftrans.html) has a few
descriptions given of referential transparency from several books on
functional programming.

[This
link](http://stackoverflow.com/questions/210835/what-is-referential-transparency)
has several more answers to the question "What is referential
transparency?", going back to the origins of the term in analytical
philosophy, and their influence on Landin and Strachey when they were
defining semantics of programming languages.

[Here](http://www.reddit.com/r/haskell/comments/xgq27/uday_reddy_sharpens_up_referential_transparency/)
is a discussion on the Haskell sub-Reddit with people responding to
Uday Reddy, a frequent commenter to the Stack Overflow question linked
above.


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

I have read Henry Baker's paper on
[egal](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.23.9999),
"Equal Rights for Functional Objects or, The More Things Change, The
More They Are the Same", but cannot claim to understand every one of
its nuances.

I have heard of denotational and operational semantics, but have not
studied them.  Similarly for monads.

Another paper on the topic of equality and what it could and/or should
mean is [The Left Hand of Equals](http://web.cecs.pdx.edu/~black/publications/egal.pdf).
[Here](http://lambda-the-ultimate.org/node/5509) is a discussion of
that paper on a programming language theory discussion site.


## Motivation for writing this

Occasionally on the Clojure Google group a person will express
displeasure that Clojure's definition of `=` returns true for some
pair of values `v1` and `v2`, but other functions return different
(i.e. not `=`) values when you replace `v1` with `v2` as an argument.

Below are several cases of this.  I find example 3 to be the most
interesting, since it is more subtle than the others, appears to exist
in Haskell as well as in Clojure.  I am not saying there is anything
wrong with Haskell -- it is simply that if any programmers had thought
about this issue deeply, it would probably be someone knowledgeable of
Haskell.


# Example 1: Interaction between Clojure's `conj` and `=`

The most common example raised is that `conj` adds elements to lists
at the beginning, but to vectors at the end.  Despite this difference,
`=` returns true if given a list and a vector, the items in them are
equal, and in the same order.

```clojure

;; The behavior below has been consistent for many versions of
;; Clojure, and seems unlikely to change.  In case it does change,
;; here is the version used for this session.

user=> (clojure-version)
"1.12.0"

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

;; conj is documented to add items at different 'places' for different
;; types of collections.  At the beginning for lists, at the end for
;; vectors.

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
different types an unpleasant hack.  Unless one created a [sum
type](https://www.fpcomplete.com/school/to-infinity-and-beyond/pick-of-the-week/sum-types)
that included both lists and vectors, one would not be able to
implement this in Haskell at all.

The root of this behavior: Clojure's `=` is ignoring the type of its
arguments when comparing lists, vectors, and sequences, by design
choice.


# Example 2: Interaction between Clojure's metadata and `=`

Clojure immutable collection values can have metadata associated with
them [1].  Metadata is a Clojure map value that is 'attached' to a
collection value.  It is ignored when comparing collections or dealing
with their values in almost any way, except for a few functions like
`meta` and `vary-meta` that can retrieve the metadata, or return a new
collection with new metadata altered in a specified way from the
original.

[1] Other things besides collection values can have metadata, too, but
we will ignore that here.

```clojure

;; The behavior below has been consistent for many versions of
;; Clojure, and seems unlikely to change.  In case it does change,
;; here is the version used for this session.

user=> (clojure-version)
"1.12.0"

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


# Example 3: Interaction between Clojure's hash sets, `=`, and `seq`

The following example is a bit more subtle, and it has a corresponding
data structure implementation in Haskell with similar behavior.

```clojure
;; Showing the Clojure version, since the function `hash` changed from
;; version 1.5.1 to 1.6.0, and it may change again in a future
;; version.

user=> (clojure-version)
"1.12.0"

;; Demonstrate two particular integers that have equal hash values

user=> (hash 0)
0
user=> (hash 92612215)
0

;; Create 2 hash sets with these integers added to them via `conj`,
;; but in opposite orders.

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

;; The implementation of hash-set is roughly a trie based on the
;; element's hash value, and if there are multiple elements with the
;; same hash value, a list of those elements at the trie leaf.  The
;; order of the elements in that list depends upon the order that the
;; elements were added to the set.

;; Thus sets equal according to `=` may be 'distinguishable' by `seq`,
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

Haskell's
[Data.HashSet](https://hackage.haskell.org/package/hashmap-1.0.0.2/docs/Data-HashSet.html)
is similar.  The corresponding names are:

* `hash-set` -> `HashSet`
* `conj` -> `insert`
* `seq` -> `toList`
* `=` -> `==`

The particular integers used in the example below are different
because Haskell's hash function is different than Clojure's.

```bash
# Install steps tested on Ubuntu 24.04 aarch64 Linux system on 2024-Oct-02:
sudo apt-get install ghc libghc-unordered-containers-dev libghc-unordered-containers-doc libghc-random-dev libghc-random-doc
```

Aside: It appears that the Haskell function `hash` in package
`Data.Hashable` returns `x` for integers `x` in the range `[0,
2^63-1]`.  If this is true, we can quickly find two integers with the
same hash value by simply calcalating the hash of an integer L >=
2^63, and if `hash L = H` is in the range `[0, 2^63-1]`, then `hash L
= hash H`.

Find two values with same hash:
```haskell
$ ghci
GHCi, version 9.4.7: https://www.haskell.org/ghc/  :? for help
ghci> hash 9223372036854775808
6273985623559904276
ghci> hash 6273985623559904276
6273985623559904276
ghci> hash 9223372036854775808 == hash 6273985623559904276
True
```

Create two `HashSet` values, inserting the two values with the same
hash in a different order:

```haskell
$ ghci
GHCi, version 9.4.7: https://www.haskell.org/ghc/  :? for help
ghci> import qualified Data.HashSet as HashSet
ghci> import Data.Hashable

-- Create two HashSets, adding those two integers in a different order
-- for each one.

ghci> let s1=HashSet.fromList[6273985623559904276,9223372036854775808]
ghci> let s2=HashSet.fromList[9223372036854775808,6273985623559904276]
ghci> s1
fromList [6273985623559904276,9223372036854775808]
ghci> s2
fromList [9223372036854775808,6273985623559904276]

-- They are equal according to the == defined for Data.HashSet

ghci> s1 == s2
True

-- but toList returns the integers in a different order for each one

ghci> HashSet.toList s1
[6273985623559904276,9223372036854775808]
ghci> HashSet.toList s2
[9223372036854775808,6273985623559904276]

-- and of course lists with items in different orders are not equal in
-- Haskell.

ghci> HashSet.toList s1 == HashSet.toList s2
False
```

The root of this behavior is a combination of the following facts.

1. The implementation relies only on hashing and equality of set
   elements, not on anything more, e.g. a total order defined for the
   elements, like `<=` on numbers.
2. `=` is defined to be true for sets with the same mathematical set
   of elements, regardless of the order that elements were added.
3. `seq` returns an ordered sequence of all of a set's elements.

Below we discuss alternatives to each of these.


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
different orders in the lists.  This is a very natural desire for a
definition of `=` for sets.

One could imagine instead defining `structural-=`, a function that
returns false for two hash sets when colliding elements are in lists
in different orders, even though the sets contain the same elements.

I am not arguing that the proposed `structural-=` would be a useful
definition of `=`, but it does have the property that if two sets are
`structural-=`, `seq` can easily be implemented to return the same
list for both of them (one only needs to traverse all element hash
values in some total order, e.g. increasing integer order).


In the Haskell library Data.HashSet, it defines `==` similar to
Clojure's `=` for hash sets.  That is, two hash sets are equal if they
contain equal elements, regardless of the order they were added.

There is also what I think is called 'definitional equality' or
'structural equality' in Haskell, which is `=`.  At least some Haskell
developers say that referential transparency only applies to values
that are `=` to each other, not for values that are `==` to each
other.  This is at least implied in the following paragraph from [A
Gentle Introduction to
Haskell](https://www.haskell.org/tutorial/stdclasses.html) (especially
the last sentence):

    In fact, we should provide our own definitions of equality and
    ordering predicates only with some trepidation, being careful to
    maintain the expected algebraic properties of equivalence
    relations and total orders.  [ ... ] Nevertheless, it is sometimes
    necessary to provide Eq or Ord instances different from those that
    would be derived; probably the most important example is that of
    an abstract data type in which different concrete values may
    represent the same abstract value.

The last sentence is true of Data.HashSet.  Multiple different
concrete values, meaning different hash sets represented with
colliding set elements in different orders, represent the same
abstract value, that of the 'mathematical set' that has no notion of
order.

For data structure implementations based on binary trees, in any
programming language, it is common to have many different binary tree
structures that represent the same abstract value.  Haskell's `=`
(structural equality) is only true between trees with the same
structure, but `==` is often defined to be true for many different
tree structures, as long as it makes sense for the semantics of the
desired data type.

It also seems to be common to do one of the following two things with
such a Haskell type:

(2a) Define it in a module, and avoid exporting any functions that
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
[Edison](http://hackage.haskell.org/package/EdisonAPI-1.2.1/docs/Data-Edison.html)
library by Chris Okasaki.  The `toSeq` function for collections where
the elements can be observed is [documented to be
ambiguous](http://hackage.haskell.org/package/EdisonAPI-1.2.1/docs/Data-Edison-Coll.html),
by which Okasaki means:

    "For ambiguous functions, the result of applying the function may
    depend on otherwise unobservable internal state of the data
    structure, such as the actual shape of a balanced tree."

More details on how Okasaki uses the terms ambiguous and unambiguous
for the Edison library are [here](https://hackage.haskell.org/package/EdisonAPI-1.3/candidate/docs/Data-Edison.html).


(3)

The function `seq` returns an ordered sequence of a set's elements.

One alternative is not to implement any function that returns a set's
elements in any way whatsoever.  If no such function were implemented
for hash sets, and no other function defined for hash sets made two
Clojure `=` (Haskell `==`) sets distinguishable from each other by
their return values, then this particular issue would not exist.

The disadvantage is obvious: set types are much less useful if there
is no way to enumerate their elements.

There is another suggested alternative that I do not understand yet.
I may study enough about monads some day to grasp it.  Feel free to
contact me (andy_fingerhut@alum.wustl.edu) if you like spending time
teaching people about such things, but I can live happy not
understanding it, too.

It is the last statement from user "ski" on the Haskell IRC channel
below.  (Excerpted from [this conversation
log](http://tunes.org/~nef/logs/old/haskell-15.zip) -- search for
'andyf' in all files inside that zip file).



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


# Conclusion

In hindsight after writing this, it now seems clear to me that if one
wants to maintain the property "x is equal to y implies f(x) is equal
to f(y)" for all functions f, then it is pretty important to be clear
on what "equal" means.

As a silly example, if one gets to define their own equals for lists,
and you decide to define lists as equal if they contain the same
number of items, e.g. the list (1 2 3) is equal to the list (4 5 6)
because they both have 3 items, then you are easily going to violate
the property.

Example 3 highlights this point: one can create implementations of
data structures using only pure functions, and a 'reasonable'
definition of equality, where the property can be violated.  The root
of this issue is: sometimes reasonable definitions of equality regard
two values as equal, intentionally ignoring internal implementation
details of the data structure, but those differences ignored by equal
can be made observable by other functions you implement on the data
structure (like `seq` / `toList`).

I like the view taken by Chris Okasaki in his Edison library: do not
omit functions that violate the property, if they are useful to have,
but document them as such.


# Consequences on memoization

Memoization may not be the most important reason to think about this
property, but it can help demonstrate odd things that can happen if a
function does not satisfy it.  I realize that no one would ever want
to memoize the particular functions in the examples below, since the
functions given are very fast already, and memoization likely only
makes them slower.

We will go through the examples given earlier, and describe what would
happen if you tried to memoize the particular function that violates
the property.  Memoization compares arguments for equality with
`clojure.core/=` with any arguments given previously and stored in a
cache, and if it finds a match, returns the value saved in the cache
that was returned before.

All of the transcripts below were created using Clojure 1.6.0 and the
`core.memoize` library, version 0.5.7.


## Example 1: Memoizing `conj`

```clojure
user=> (require '[clojure.core.memoize :as m])
nil
user=> (def memoized-conj (m/lu conj :lu/threshold 100))
#'user/memoized-conj
user=> (def s1 '(4 5 6))
#'user/s1
user=> (def s2 [4 5 6])
#'user/s2
user=> (memoized-conj s1 0)
(0 4 5 6)

;; The above is correct, because the original conj was called.  The
;; result below is wrong because (= s2 s1) causes the cached value to
;; be used.

user=> (memoized-conj s2 0)
(0 4 5 6)
user=> (conj s2 0)
[4 5 6 0]
```

## Example 2: Memoizing `meta`

It should not surprise you that trying to memoize `meta` will not
work, since metadata is ignored by `=`, and that is what the
memoization cache uses to match arguments to the memoized function.

```clojure
user=> (def memoized-meta (m/lu meta :lu/threshold 100))
#'user/memoized-meta
user=> (def v1 (with-meta [1 2 3] {:key1 "value1"}))
#'user/v1
user=> (def v2 (with-meta [1 2 3] {:key2 "value2"}))
#'user/v2
user=> (memoized-meta v1)
{:key1 "value1"}

;; The above is correct, because the original meta was called.  The
;; result below is wrong because (= v2 v1) causes the cached value to
;; be used.

user=> (memoized-meta v2)
{:key1 "value1"}
```

## Example 3: Memoizing `seq`

```clojure
user=> (def memoized-seq (m/lu seq :lu/threshold 100))
#'user/memoized-seq
user=> (def s1 (conj (hash-set) 0 92612215))
#'user/s1
user=> (def s2 (conj (hash-set) 92612215 0))
#'user/s2
user=> (memoized-seq s1)
(0 92612215)

;; The above is correct, because the original seq was called.  The
;; result below is 'wrong' in that it is not equal to what is returned
;; by (seq s2), but read further below.

user=> (memoized-seq s2)
(0 92612215)
user=> (seq s2)
(92612215 0)
```

Note that even though `memoized-seq` returns a value not equal to the
one returned by `seq`, because of the memoization, in this case I
would argue that it is a difference you are unlikely to care about,
since you simply got the elements back in a different order for an
unordered set.

You can get incorrect results from `memoized-seq` if you mix using it
on sorted and unsorted sets, though, as shown below.  This is because
`=` between sorted and unsorted sets is true if the sets contain the
same elements, ignoring order.

```clojure
user=> (def s3 (conj (hash-set) 5 4 3 2 1))
#'user/s3
user=> (def s4 (conj (sorted-set) 5 4 3 2 1))
#'user/s4
user=> (memoized-seq s3)
(1 4 3 2 5)

;; The above is a correct answer for hash-set s3, but the memoized-seq
;; result below is wrong for sorted-set s4.

user=> (memoized-seq s4)
(1 4 3 2 5)
user=> (seq s4)
(1 2 3 4 5)
```


# Other References

Published academic papers that user 'ski' on the #haskell IRC channel
recommended reading.

"Referential Transparency, Definiteness and Unfoldability", Harald
Søndergaard and Peter Sestoft, Acta Informatica 27, pp. 505-517 (1990)
http://www.cs.tufts.edu/~nr/cs257/archive/peter-sestoft/ref-trans.pdf

This page has what appears to be a summary of the paper above:
http://userpage.fu-berlin.de/~ram/pub/pub_jf47ht81Ht/referential_transparency
A local copy of this page can be found
[here](referential-transparency-local-copies/Referential-Transparency.html)

I skimmed quickly through the paper below, and it seems to require
more knowledge of lambda calculus than I have now, and might ever
choose to learn.

"What is a Purely Functional Language?", Amr Sabry, J. Functional
Programming, vol. 1 no. 1, January 1993, Cambridge University Press
https://www.cs.indiana.edu/~sabry/papers/purelyFunctional.ps

This Reddit discussion topic references Sabry's paper above.  I have
skimmed through it, but nothing jumped out at me as quickly clarifying
for my current context of knowledge.
https://www.reddit.com/r/haskell/comments/21y560/purity_and_referential_transparency_are_different/

The Wikipedia page for referential transparency is: https://en.wikipedia.org/wiki/Referential_transparency

It references this p. 78 of the book "Concepts in Programming
Languages", John C. Mitchell, 2002, Cambridge University Press

The section page includes this text (as well as other text left out of
this quote):

    In some of the academic literature on programming languages,
    including some textbooks on programming language semantics, the
    concept that is used to distinguish declarative from imperative
    programming language semantics is called _referential
    transparency_.  Although it is easy to define this phrase, it is a
    bit tricky to use it correctly to distinguish one programming
    language from another.

and later:

    Returning to programming languages, it is traditional to say that
    a language is referentially transparent if we may replace one
    expression with another of equal value anywhere in a program
    without changing the meaning of the program.  This is a property
    of pure functional languages.

    The reason referential transparency is subtle is that it depends
    on the value we associate with expressions.  In imperative
    programming languages, we can say that a variable x refer to its
    value or to its location.  If we say that a variable refers to its
    location in memory, then imperative languages _are_ referentially
    transparent, as replacing one variable with another that names the
    same memory location will not change the meaning of the program.
    On the other hand, if we say that a variable refers to the value
    stored in that location, then imperative languages are not
    referentially transparent, as the value of a variable may change
    as the result of assignment.

Here is a January 2019 post that discusses some of these issues, and
gives links to other on-line articles, including this one:

"Abstract types are more equal", January 2, 2019,
https://giacomociti.github.io/2019/01/02/Abstract-types-are-more-equal.html


# What is the definition of "referential transparency" applied to programming?

Or a related question: Is there any widely agreed upon definition of
what referential transparency means in the domain of programming
languages?

It seems that among specialists in studying the semantics of
programming languages, there might be a smaller variety of definitions
of the term "referential transparency", but I do not know if I will
ever be interested enough to learn about this field to confirm or
refute that guess.

It does seem that among those who are not specialists in semantics of
programming languages, but who are developers using languages that
focus on pure functions, e.g. Haskell, OCaml, F#, etc., that there are
many different descriptions of what referential transparency means.  I
suppose that this should not be too surprising for _any_ technical
term that has subtleties in its meaning.


## Uses of referential transparency in the book "Structure and Interpretation of Computer Programs"

In particular, the edition that was published at the link below as I
retrieved it on 2021-Jan-23.

    https://mitpress.mit.edu/sites/default/files/sicp/full-text/book/book.html

Section 1.1.5 "The Substitution Model for Procedure application"
introduces a reasonably simple substitution model as a way of
understanding what procedure application means, and how expressions
can be evaluated in order to determine their values.

It also names and demonstrates with a simple example the difference
between applicative order and normal order.  Exercise 1.5 gives a
small program showing how these two order of evaluation, involving a
procedure that goes into an infinite loop.

Quote:

    This alternative ``fully expand and then reduce'' evaluation
    method is known as normal-order evaluation, in contrast to the
    ``evaluate the arguments and then apply'' method that the
    interpreter actually uses, which is called applicative-order
    evaluation. It can be shown that, for procedure applications that
    can be modeled using substitution (including all the procedures in
    the first two chapters of this book) and that yield legitimate
    values, normal-order and applicative-order evaluation produce the
    same value. (See exercise 1.5 for an instance of an
    ``illegitimate'' value where normal-order and applicative-order
    evaluation do not give the same result.)

    Lisp uses applicative-order evaluation, partly because of the
    additional efficiency obtained from avoiding multiple evaluations
    of expressions such as those illustrated with (+ 5 1) and (* 5 2)
    above and, more significantly, because normal-order evaluation
    becomes much more complicated to deal with when we leave the realm
    of procedures that can be modeled by substitution. On the other
    hand, normal-order evaluation can be an extremely valuable tool,
    and we will investigate some of its implications in chapters 3 and
    4.16

The phrase "the realm of procedures that can be modeled by
substitution" sounds likely to be related to referential transparency,
pure functions, or both.

Section 3.1 "Assignment and Local State":

    https://mitpress.mit.edu/sites/default/files/sicp/full-text/book/book-Z-H-20.html#%_sec_3.1

This section introduces the idea of objects in a computer program that
have local state, and procedures that when called multiple times with
the same parameters, return different values, depending upon the
history of what calls have been made before.  It also introduces
assignment using Scheme `set!`.

Very shortly after doing this, it immediately points out something
very different about such procedures:

    Combining `set!` with local variables is the general programming
    technique we will use for constructing computational objects with
    local state.  Unfortunately, using this technique raises a serious
    problem: When we first introduced procedures, we also introduced
    the substitution model of evaluation (section 1.1.5) to provide an
    interpretation of what procedure application means.  We said that
    applying a procedure should be interpreted as evaluating the body
    of the procedure with the formal parameters replaced by their
    values.  The trouble is that, as soon as we introduce assignment
    into our language, substitution is no longer an adequate model of
    procedure application.  (We will see why this is so in section
    3.1.3.)  As a consequence, we technically have at this point no
    way to understand why the new-withdraw procedure behaves as
    claimed above.  In order to really understand a procedure such as
    new-withdraw, we will need to develop a new model of procedure
    application.  In section 3.2 we will introduce such a model,
    together with an explanation of `set!` and local variables.

Section 3.1.2 describes a benefit of introducing assignment, by
showing how a small program written that calls a procedure to return a
sequence of pseudo-random numbers can be written, and how much
"messier" that program becomes if you are only allowed to write it
using pure functions.

Section 3.1.3 "The Costs of Introducing Assignment" is the first one
of the book to mention the term "referential transparency", here:

    A language that supports the concept that ``equals can be
    substituted for equals'' in an expression without changing the
    value of the expression is said to be _referentially transparent_.
    Referential transparency is violated when we include `set!` in our
    computer language.  This makes it tricky to determine when we can
    simplify expressions by substituting equivalent expressions.
    Consequently, reasoning about programs that use assignment becomes
    drastically more difficult.

    Once we forgo referential transparency, the notion of what it
    means for computational objects to be ``the same'' becomes
    difficult to capture in a formal way.  Indeed, the meaning of
    ``same'' in the real world that our programs model is hardly clear
    in itself.  In general, we can determine that two apparently
    identical objects are indeed ``the same one'' only by modifying
    one object and then observing whether the other object has changed
    in the same way.  But how can we tell if an object has ``changed''
    other than by observing the ``same'' object twice and seeing
    whether some property of the object differs from one observation
    to the next? Thus, we cannot determine ``change'' without some a
    priori notion of ``sameness,'' and we cannot determine sameness
    without observing the effects of change.

Section 3.2 "The Environment Model of Evaluation" introduces a new
model of evaluation, more general than the substitution model of
evaluation from Section 1.1.5, that can support `set!`.

The second mention of referential transparency in the entire book is
in Section 3.4 "Concurrency: Time Is of the Essence":

    We've seen the power of computational objects with local state as
    tools for modeling. Yet, as section 3.1.3 warned, this power
    extracts a price: the loss of referential transparency, giving
    rise to a thicket of questions about sameness and change, and the
    need to abandon the substitution model of evaluation in favor of
    the more intricate environment model.

No mentions of referential transparency anywhere in Chapters 4 or 5.

All occurrences of referential transparency in the index point at the
first occurrence of the term in the book in Section 3.1.3.


## Uses of referential transparency in the book "Introduction to Functional Programming"

Richard Bird, Philip Wadler, "Introduction to Functional Programming",
1988, Prentice Hall International Series in Computer Science

The introduction of this book says it is an introductory textbook on
programming in general and functional programming in particular.  The
authors consider it suitable for teaching a course in programming to
first-year undergraduates.  It is definitely not a book targeted at
specialists in the semantics of programming languages.

The only occurrence of the term "referential transparency" in the
entire book occurs in Section 1.2 "Expressions and values":

    As we have seen, the notion of an expression is central in
    functional programming.  There are many kinds of mathematical
    expression, not all of which are permitted in the notation we
    shall describe, but all possess certain characteristics in common.
    The most important feature of mathematical notation is that an
    expression is used solely to describe (or _denote_) a _value_.  In
    other words, the meaning of an expression _is_ its value and there
    are no other effects, hidden or otherwise, in any procedure for
    actually obtaining it.  Furthermore, the value of an expression
    depends only on the the values of its constituent expressions (if
    any) and these subexpressions may be replaced freely by others
    possessing the same value.  An expression may contain certain
    'names' which stand for unknown quantities, but it is normal in
    mathematical notation to presume that different occurrences of the
    same name refer to the same unknown quantity (within obvious
    syntactic limits).  Such names are usually called 'variables', but
    every mathematician understands that variables do _not_ vary: they
    always denote the same quantity, provided we remain within the
    same context of the definitions associated with them.  The
    characteristic property of mathematical expressions described here
    is called _referential transparency_.


## Uses of referential transparency in the book "Concepts in Programming Languages"

John C. Mitchell, "Concepts in Programming Languages", 2003, Cambridge
University Press

The introduction says this book is intended for undergraduate and
beginning graduate students, and explains central concepts in modern
programming languages, including purely functional languages as well
as other such as Simula, C++, Java.

    The author presents foundational topics, such as lambda calculus
    and denotational semantics, in an easy-to-read, informal style,
    focusing on the main insights provided by these theories.

    ...

    His graduate textbook, "Foundations for Programming Languages"
    covers lambda calculus, type systems, logic for program
    verification, and mathematical semantics of programming languages.

So while this book was written by someone who may be an expert on
semantics of programming languages, it is not the focus of this book.
The other textbook mentioned above might not mention referential
transparency at all.  See elsewhere in this document for the title of
that book and my notes on it.

The only mention of "referential transparency" in the entire book is
in Section 4.4 "Functional and Imperative Languages".  Here are all
sections within Chapter 4 "Fundamentals":

+ 4.1 Compilers and Syntax
+ 4.1.1 Structure of a Simple Compiler
+ 4.1.2 Grammars and Parse Trees
+ 4.1.3 Parsing and Precedence
+ 4.2 Lambda Calculus
+ 4.2.1 Functions and Function Expressions
+ 4.2.2 Lambda Expressions
+ 4.2.3 Programming in Lambda Calculus
+ 4.2.4 Reduction, Confluence, and Normal Forms
+ 4.2.5 Important Properties of Lambda Calculus
+ 4.3 Denotational Semantics
+ 4.3.1 Object Language and Metalanguage
+ 4.3.2 Denotational Semantics of Binary Numbers
+ 4.3.3 Denotational Semantics of While Programs
+ 4.3.4 Perspective and Nonstandard Semantics
+ 4.4 Functional and Imperative Languages
+ 4.4.1 Imperative and Declarative Sentences
+ 4.4.2 Functional versus Imperative Programs


## Books/article I have checked that make no mention of "referential transparency"

### Winskel, 1994

I have searched the full text of this book, and it contains no
occurrences of the word "referential", and no occurrences of the word
"transparency":

+ Glynn Winskel, "The Formal Semantics of Programming Languages: An
  Introduction", 1994, MIT Press


# Mitchell, 1996

In this book:

+ John C. Mitchell, "Foundations for Programming Languages", 1996, MIT
  Press

I have checked the index, retrieved from this page:
https://theory.stanford.edu/people/jcm/books.html, but not the full
text, for the following terms, and found none of them:

+ equational reasoning
+ pure function
+ referential
+ transparency

It does have several occurrences of "substitution" on various kinds of
things.


# Some terms, and how they might be related to each other

My guess is that some of these things can be proven true, perhaps with
some additional qualifications that I am not yet aware of.

+ If you use a language that enables you to write programs with no
  I/O, no assignments, and they terminate, it is always possible to
  describe their behavior using the substitution model of evaluation.


## substitution model of evaluation

As described in SICP and other books on other programming languages
than Scheme, many of which I suspect were influenced by their authors
learning it from SICP.


## pure functions



## referential transparency



## substituting equals for equals / substitution law / substitution rule



## equational reasoning
