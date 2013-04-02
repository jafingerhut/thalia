# Comparators in Clojure

## Versions

This document was written while checking the details against Clojure
1.5.1 and Java 6, but most or all of it should apply to other
versions, too.


## Introduction

In Clojure you need comparators for sorting a collection of values, or
for maintaining a collection of values in a desired sorted order,
e.g. a `sorted-map`, `sorted-set`, or [`priority-map`][priority-map]
(also known as a priority queue).

[priority-map]: https://github.com/clojure/data.priority-map

See also: `compare`, `sort`, `sort-by` `sorted-set`, `sorted-set-by`,
`sorted-map`, `sorted-map-by`, `subseq`, `rsubseq`

Here we briefly describe the default sorting order provided by the
function `compare`.  After that we give examples of other comparators,
with some guidelines to follow and mistakes to avoid when writing your
own.

If you don't specify your own comparator, sorting is done by a
built-in function `compare`.  `compare` works for many types of
objects, ordering values in one particular way: increasing numeric
order for numbers; [lexicographic order][lexicographic] (aka
dictionary order) for strings, symbols, and keywords;
shortest-to-longest order for Clojure vectors, with lexicographic
ordering among equal length vectors.  All Java types implementing the
[`Comparable`][Comparable] interface such as characters, booleans,
`File`, `URI`, and `UUID` are compared via their `compareTo` methods.
Finally, `nil` can be compared to all values described earlier, and is
considered less than all of them.  See [`compare`][compare-docs] for
examples and more details.

[Comparable]: http://docs.oracle.com/javase/6/docs/api/java/lang/Comparable.html
[lexicographic]: http://en.wikipedia.org/wiki/Lexicographical_order
[compare-docs]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md

If this built-in sorting order doesn't meet your needs, or doesn't
work at all for objects of a type you wish to sort, you can write your
own comparator and use that instead.  There are a few rules to follow
when writing a comparator that works correctly.


## Writing your own comparators

### Reverse order

If `compare` doesn't sort values in the order you want, then you must
write your own comparator.  The simplest kinds you can write are minor
variations of the built-in `compare`.  To sort numbers in decreasing
order, simply write a comparator that calls `compare` with the
arguments in the opposite order:

```clojure
    user> (sort [4 2 3 1])
    (1 2 3 4)

    user> (defn reverse-cmp [a b]
            (compare b a))
    #'user/reverse-cmp
    user> (sort reverse-cmp [4 2 3 1])
    (4 3 2 1)
```

Such short functions are often written using Clojure's `#()` notation,
where the two arguments are `%1` and `%2`, in that order.

```clojure
    user> (sort #(compare %2 %1) [4 2 3 1])
    (4 3 2 1)
```

`reverse-cmp` will also work for all other types that `compare` works
for.

### Multi-field comparators

Because equal-length Clojure vectors are compared lexicographically,
they can be used to create sort keys for values with multiple fields
like maps or records, with only a small amount of code.  However, this
method only works if the field values to be sorted are already sorted
by `compare` in an order you wish (or you wish them in the reverse of
that order).

TBD: Finish this section.


## Off-the-shelf comparators


### Unicode comparators

TBD: Give links to Unicode string comparators, and any other useful
ones I can find.


### Comparators that work between different types

Sometimes you might wish to sort a collection of values by some key,
but that key is not unique.  You want the values with the same key to
be sorted in some predictable, repeatable order, but you don't care
much what that order is.

As a toy example, you might have a collection of vectors, each with
two elements, where the first element is always a string and the
second is always a number.  You want to sort them by the number value
in increasing order, but you know your data can contain more than one
vector with the same number.  You want to break ties in some way,
consistently across multiple sorts.

This case is easily implemented using a multi-field comparator as
described above.

```clojure
    (defn by-number-then-string [[a-str a-num] [b-str b-num]]
      (compare [a-num a-str]
               [b-num b-str]))
```

If the entire vector values can be compared with `compare`, because
all vectors are equal length, and the type of each corresponding
elements can be compared to each other with `compare`, then you can
also do this, using the entire vector values as the final tie-breaker:

```clojure
    (defn by-number-then-whatever [a-vec b-vec]
      (compare [(second a-vec) a-vec]
               [(second b-vec) b-vec]))
```

However, that will throw an exception if some element position in the
vectors contain types too different for `compare` to work on, and
those vectors have the same second element:

```clojure
    ;; compare throws exception if you try to compare a string and a
    ;; keyword
    user> (sort by-number-then-whatever [["a" 2] ["c" 3] [:b 2]])
    ClassCastException java.lang.String cannot be cast to clojure.lang.Keyword  clojure.lang.Keyword.compareTo (Keyword.java:109)
```

`cc-cmp` ("cross class compare") below may be useful in such cases.
It can compare values of different types, which it orders based on a
string that represents the type of the value.  It isn't simply `(class
x)`, because then numbers like `Integer` and `Long` would not be
sorted in numeric order.

```clojure

;; comparison-class throws exceptions for many types that would be
;; useful to include, e.g. Java arrays, and Clojure records, lists,
;; sets, and maps.  We'll save that for a fancier version.

(defn comparison-class [x]
  (cond (nil? x) ""
        ;; Lump all numbers together since Clojure's compare can
        ;; compare them all to each other sensibly.
        (number? x) "java.lang.Number"
        ;; Similarly lump all vector types together into one.
        (vector? x) "clojure.lang.IPersistentVector"
        ;; Comparable includes Boolean, Character, String, Clojure
        ;; refs, and many others.
        (instance? Comparable x) (.getName (class x))
        :else (throw
               (ex-info (format "cc-cmp does not implement comparison of values with class %s"
                                (.getName (class x)))
                        {:value x}))))

(defn cmp-vec-lexi [cmpf a b]
  (let [a-len (count a)
        b-len (count b)
        len (min a-len b-len)]
    (loop [i 0]
      (if (== i len)
        ;; If all elements 0..(len-1) are same, shorter vector comes
        ;; first.
        (compare a-len b-len) 
        (let [x (cmpf (a i) (b i))]
          (if (zero? x)
            (recur (inc i))
            x))))))

(defn cc-cmp [a b]
  (let [a-cls (comparison-class a)
        b-cls (comparison-class b)
        c (compare a-cls b-cls)]
    (cond (not= c 0) c  ; different classes

          ;; Must use cc-cmp recursively on vector elements, because
          ;; if we used compare we would lose ability to compare
          ;; elements with different types.  While we are making a
          ;; special case, let us implement lexicographic ordering for
          ;; different vector lengths.
          (= c "clojure.lang.IPersistentVector")
          (cmp-vec-lexi cc-cmp a b)
          
          :else (compare a b))))
```

Here is a quick example demonstrating `cc-cmp`'s ability to compare
values of different types.

```clojure
    user> (sort cc-cmp [true false nil Double/MAX_VALUE 10 Integer/MIN_VALUE :a "b" 'c (ref 5) [5 4 3] [5 4] [5]])
    (nil [5] [5 4] [5 4 3] :a #<Ref@6685370c: 5> c false true -2147483648 10 1.7976931348623157E308 "b")
```


## Mistakes to avoid

### Comparators for sorted sets and maps are easy to get wrong

This is accurately stated as "comparators are easy to get wrong", but
it is often more noticeable when you use a bad comparator for sorted
sets and maps, because you see values not getting added to your sorted
collections, or not being able to find value you added to your
collections earlier.

Suppose we want a sorted set containing vectors of two elements.  The
second elements are always numbers, and we want the set sorted by this
number.  We want to allow multiple vectors with the same second
element, but different first elements.  The quickest comparator to
write is to simply compare on the second vector element:

```clojure
    (defn by-2nd [a b]
      (compare (second a) (second b)))
```

But look what happens when we try to add two vectors with the same
number.

```clojure
    user> (sorted-set-by by-2nd [:a 1] [:b 1] [:c 1])
    #{[:a 1]}
```

Only one element is in the set, because `by-2nd` treats all 3 of them
as equal.  Sets should not contain duplicate elements, so the other
elements are not added.

Aside: If you do _not_ want multiple vectors in your set with the same
second element, `by-2nd` is the comparator you should use.  This is
exactly the behavior you want.  (TBD: Are there any caveats here?
Will `sorted-set` ever use `=` to compare elements for any reason, or
only the supplied comparator function?)

Let us continue under the assumption that we want to allow multiple
different vectors with the same second element in our sorted set.  A
common thought in such a case is to use a boolean comparator function
based on `<=` instead of '<', like so:

```clojure
    (defn by-2nd-<= [a b]
      (<= (second a) (second b)))
```

In Clojure, besides writing a comparator that returns a number that is
negative, positive, or 0, you may also write a comparator that returns
true if the first argument should come before the second argument in
the desired sorted order.  Behind the scenes, when such a Clojure
function `boolean-cmp-fn` is "called as a comparator" (see details
below if curious), Clojure runs code that works like this to return an
`int` instead, as callers of a comparator expect.

```clojure
    (if (boolean-cmp-fn x y)
      -1     ; x < y
      (if (boolean-cmp-fn y x)  ; note the reversed argument order
        1    ; x > y
        0))  ; x = y
```

In other words, if you write such a boolean comparator, it should work
like `<` does for numbers, but as we will see, you should not write it
so that it works like `<=` for numbers.

The boolean comparator `by-2nd-<=` seems to work correctly on the
first step of creating the set, but not so well when testing whether
elements are in the set.

```clojure
    user> (def sset (sorted-set-by by-2nd-<= [:a 1] [:b 1] [:c 1]))
    #'user/sset
    user> sset
    #{[:c 1] [:b 1] [:a 1]}
    user> (sset [:c 1])
    nil
    user> (sset [:b 1])
    nil
    user> (sset [:a 1])
    nil
```

The problem here is that `by-2nd-<=` gives inconsistent answers.  If
you ask it whether `[:c 1]` comes before `[:b 1]`, it returns true
(which Clojure's boolean-to-int comparator conversion turns into -1).
If you ask it whether `[:b 1]` comes before `[:c 1]`, again it returns
true (again converted into -1 by Clojure).  One cannot reasonably
expect an implementation of a sorted data structure to provide any
kind of guarantees on its behavior if you give it an inconsistent
comparator.

Implementation details: When I say above that a function is "called as
a comparator", I mean by calling the function's `compare` method
instead of the more usual `invoke` (TBD: or is it more usually
`call`?)  See Clojure source file
`src/jvm/clojure/lang/AFunction.java` method `compare` if you want the
gory details.


### Beware using subtraction in a comparator

Java comparators return a negative `int` value if the first argument
is to be treated as less than the second, a positive `int` value if
the first argument is to be treated as greater than the second, and 0
if they are equal.

```clojure
    user> (compare 10 20)
    -1
    user> (compare 20 10)
    1
    user> (compare 20 20)
    0
```

Because of this, you might be tempted to write a comparator by
subtracting one numeric value from another, like so.

```clojure
    user> (sort #(- %1 %2) [4 2 3 1])
    (1 2 3 4)
```

While this works in many cases, think twice (or three times) before
using this technique.  It is less error-prone to use explicit
conditional checks and return -1, 0, or 1, or to use predicate
comparators.

Why?  Java comparators must return a 32-bit `int` type, so when a
Clojure function is used as a comparator and it returns any type of
number, that number is converted to an `int` behind the scenes using
the Java method [`intValue`][NumberintValue].

[NumberintValue]: http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html#intValue%28%29

For comparing floating point numbers and ratios, this causes numbers
differing by less than 1 to be treated as equal, because a return
value between -1 and 1 is truncated to the `int` 0:

```clojure
    ;; This gives the correct answer
    user> (sort #(- %1 %2) [10.0 9.0 8.0 7.0])
    (7.0 8.0 9.0 10.0)

    ;; but this does not, because all values are treated as equal by
    ;; the bad comparator.
    user> (sort #(- %1 %2) [1.0 0.9 0.8 0.7])
    (1.0 0.9 0.8 0.7)

    ;; .intValue converts all values between -1.0 and 1.0 to 0
    user> (map #(.intValue %) [-1.0 -0.99 -0.1 0.1 0.99 1.0])
    (-1 0 0 0 0 1)
```

This also leads to bugs when comparing integer values that differ by
an amount that changes sign when you truncate it by chopping off all
but its least significant 32 bits.  About half of all pairs of long
values are compared incorrectly by using subtraction as a comparator.

```clojure
    ;; This looks good
    user> (sort #(- %1 %2) [4 2 3 1])
    (1 2 3 4)

    ;; What the heck?
    user> (sort #(- %1 %2) [2147483650 2147483651 2147483652 4 2 3 1])
    (3 4 2147483650 2147483651 2147483652 1 2)

    user> [Integer/MIN_VALUE Integer/MAX_VALUE]
    [-2147483648 2147483647]

    ;; How .intValue truncates a few selected integers.  Note
    ;; especially the first and last ones.
    user> (map #(.intValue %) [-2147483649 -2147483648 -1 0 1 2147483647 2147483648])
    (2147483647 -2147483648 -1 0 1 2147483647 -2147483648)
```

Java itself uses a subtraction comparator for strings and characters,
among others.  This does not cause any problems, because the result of
subtracting an arbitrary pair of 16-bit characters converted to ints
is guaranteed to fit within an `int` without wrapping around.  If your
comparator is not guaranteed to be given such restricted inputs,
better not to risk it.


## TBD

TBD: Is Java sort guaranteed to be stable for array elements
considered equal by the given comparator?  Yes, according to Java docs
it is:
http://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#sort%28java.lang.Object[]%29

TBD: How does Double/NaN compare to other things?  The answer is
included in the Java docs for sorting an array of doubles:
http://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#sort%28double[]%29

TBD: Maybe mention these other Clojure functions that make use of
comparators.  If mentioned nowhere else, at least at the end of the
see also list: `clojure.parallel/pmax`, `clojure.parallel/pmin`,
`clojure.parallel/psummary`, `clojure.parallel/psort`

TBD: Is function `clojure.core/comparator` useful for anything?  It
seems like with `AFunction`'s `compare` method changing boolean return
values to -1, 0, or 1, `comparator` would be unnecessary and perhaps
obsolete.

TBD: Make links for the see also list, and other Clojure functions
mentioned in the text below.
