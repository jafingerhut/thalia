# Comparators in Clojure

A slightly more updated version of this article is available on the
Clojure web site, here:

+ https://clojure.org/guides/comparators


## Versions

This document was written while checking the details against Clojure
1.5.1 and Java 6, but most or all of it should apply to other
versions, too.


## Summary

A comparator is a function that takes two arguments `x` and `y`, and
returns a value indicating the relative order in which `x` and `y`
should be sorted.  It can be a 3-way comparator returning an integer,
or a 2-way comparator returning a boolean.  See the DOs below for what
the return values should be, depending upon the order of `x` and `y`.

In Clojure you need comparators for sorting a collection of values, or
for maintaining a collection of values in a desired sorted order,
e.g. a [`sorted-map`][doc-sorted-map], [`sorted-set`][doc-sorted-set],
or [`priority-map`][priority-map] (also known as a priority queue).

[priority-map]: https://github.com/clojure/data.priority-map

The default comparator [`compare`][doc-compare] works well for sorting
numbers in increasing order, or strings, keywords, or symbols in
[lexicographic][lexicographic] (i.e. dictionary) order, and a few
other cases.  See [`compare`][doc-compare] for examples and more
details.

If `compare` does not do what you want, you must provide your own
comparator that does.  Each of the recommendations below is explained
in more detail later in this document.

DOs:

* Ensure that your comparators are based on a [_total
  order_][Total_order] over the values you want to compare.  It should
  be able to compare any pair of values that can appear in your data
  set, and determine which value should come first (or that they are
  equal).
* Write either a 3-way comparator or a boolean comparator:
 * A 3-way comparator takes 2 values, `x` and `y`, and returns a Java
   32-bit `int` that is negative if `x` comes before `y`, positive if
   `x` comes after `y`, or 0 if they are equal.  Use the values -1, 0,
   and 1 if you have no reason to prefer other return values.
 * A boolean comparator takes 2 values, `x` and `y`, and returns true
   if `x` comes before `y`, or false otherwise (including if `x` and
   `y` are equal).  `<` and `>` are good examples.  `<=` and `>=` are
   not.  Performance note: your boolean comparator may be called twice
   to distinguish between the "comes after" or "equals" cases.
* Reverse the sort order by reversing the order that you give the
  arguments to an existing comparator.
* Compare equal-length Clojure vectors containing "sort keys" in order
  to do a multi-field comparison between values.

DO NOTs:

* Do not write a boolean comparator that returns true if the values
  are equal.  Such a comparator is inconsistent.  It will cause sorted
  collections to behave incorrectly, and sorting to give unpredictable
  orders.
* Do not use comparators for sorted sets and maps that treat two
  values as equal, unless you want at most one of those two values to
  appear in the sorted collection.
* Do not use subtraction when writing a 3-way comparator, unless you
  really know what you are doing.

See also:
[`compare`][doc-compare]
[`sort`][doc-sort]
[`sort-by`][doc-sort-by]
[`sorted-set`][doc-sorted-set]
[`sorted-set-by`][doc-sorted-set-by]
[`sorted-map`][doc-sorted-map]
[`sorted-map-by`][doc-sorted-map-by]
[`subseq`][doc-subseq]
[`rsubseq`][doc-rsubseq]


## Introduction

[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[doc-sort]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sort.md
[doc-sort-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sort-by.md
[doc-sorted-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-set.md
[doc-sorted-set-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-set-by.md
[doc-sorted-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map.md
[doc-sorted-map-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map-by.md
[doc-subseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/subseq.md
[doc-rsubseq]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/rsubseq.md

Here we briefly describe the default sorting order provided by the
function `compare`.  After that we give examples of other comparators,
with some guidelines to follow and mistakes to avoid when writing your
own.

If you do not specify your own comparator, sorting is done by a
built-in function `compare`.  `compare` works for many types of
values, ordering them in one particular way: increasing numeric order
for numbers; [lexicographic order][lexicographic] (aka dictionary
order) for strings, symbols, and keywords; shortest-to-longest order
for Clojure vectors, with lexicographic ordering among equal length
vectors.  All Java types implementing the [`Comparable`][Comparable]
interface such as characters, booleans, `File`, `URI`, and `UUID` are
compared via their `compareTo` methods.  Finally, `nil` can be
compared to all values described earlier, and is considered less than
everything else.  See [`compare`][doc-compare] for examples and more
details.

[Comparable]: http://docs.oracle.com/javase/6/docs/api/java/lang/Comparable.html
[lexicographic]: http://en.wikipedia.org/wiki/Lexicographical_order

If this built-in sorting order does not meet your needs, or does not
work at all for values of a type you wish to sort, you can write your
own comparator and use that instead.  There are a few rules to follow
when writing a comparator that works correctly.


## Off-the-shelf comparators

First consider using well-tested comparators developed by others,
especially if they are complex.

A perfect example of this would be sorting Unicode strings in
different languages in orders specific to different locales.  The Java
[Collator][Java-Collator] class and [ICU][ICU] (International
Components for Unicode) provide libraries for this.

[Java-Collator]: http://docs.oracle.com/javase/6/docs/api/java/text/Collator.html
[ICU]: http://site.icu-project.org/home#TOC-What-is-ICU-


## Writing your own comparators

### Reverse order

To sort numbers in decreasing order, simply write a comparator that
calls `compare` with the arguments in the opposite order:

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
they can be used to do multi-field sorting on values like maps or
records.  This only works if the fields are already sorted by
`compare` in the order you wish (or the reverse of that).

First we will show a way to do it that does not compare vectors.

```clojure
(def john1 {:name "John", :salary 35000.00, :company "Acme" })
(def mary  {:name "Mary", :salary 35000.00, :company "Mars Inc" })
(def john2 {:name "John", :salary 40000.00, :company "Venus Co" })
(def john3 {:name "John", :salary 30000.00, :company "Asteroids-R-Us" })
(def people [john1 mary john2 john3])

(defn by-salary-name-co [x y]
  ;; :salary values sorted in decreasing order because x and y
  ;; swapped in this compare.
  (let [c (compare (:salary y) (:salary x))]
    (if (not= c 0)
      c
      ;; :name and :company are sorted in increasing order
      (let [c (compare (:name x) (:name y))]
        (if (not= c 0)
          c
          (let [c (compare (:company x) (:company y))]
            c))))))

user> (pprint (sort by-salary-name-co people))
({:name "John", :salary 40000.0, :company "Venus Co"}
 {:name "John", :salary 35000.0, :company "Acme"}
 {:name "Mary", :salary 35000.0, :company "Mars Inc"}
 {:name "John", :salary 30000.0, :company "Asteroids-R-Us"})
```

Below is the shorter way, by comparing Clojure vectors.  It behaves
exactly the same as above.  Note that as above, the field `:salary` is
sorted in descending order because `x` and `y` are swapped.

```clojure
(defn by-salary-name-co2 [x y]
  (compare [(:salary y) (:name x) (:company x)]
           [(:salary x) (:name y) (:company y)]))

user> (pprint (sort by-salary-name-co2 people))
({:name "John", :salary 40000.0, :company "Venus Co"}
 {:name "John", :salary 35000.0, :company "Acme"}
 {:name "Mary", :salary 35000.0, :company "Mars Inc"}
 {:name "John", :salary 30000.0, :company "Asteroids-R-Us"})
```

The above is fine for key values that are inexpensive to compute from
the values being sorted.  If they key values are expensive to compute,
it is better to calculate them once for each value.  See the
"decorate-sort-undecorate" technique described in the documentation
for [`sort-by`][doc-sort-by].


### Boolean comparators

Java comparators are all 3-way, meaning they return a negative, 0, or
positive integer depending upon whether the first argument should be
considered less than, equal to, or greater than the second argument.

In Clojure, you may also use boolean comparators that return `true` if
the first argument should come before the second argument, or `false`
otherwise (i.e. should come after, _or_ it is equal).  The function
`<` is a perfect example, as long as you only need to compare numbers.
`>` works for sorting numbers in decreasing order.

Behind the scenes, when such a Clojure function `bool-cmp-fn` is
"called as a comparator", Clojure runs code that works like this to
return an `int` instead:

```clojure
(if (bool-cmp-fn x y)
  -1     ; x < y
  (if (bool-cmp-fn y x)  ; note the reversed argument order
    1    ; x > y
    0))  ; x = y
```

You can see this by calling the `compare` method of any Clojure
function.  Below is an example with a custom version `my-<` of `<`
that prints its arguments when it is called, so you can see the cases
where it is called more than once:

```clojure
user> (defn my-< [a b]
        (println "(my-<" a b ") returns " (< a b))
        (< a b))
#'user/my-<

;; (. o (compare a b)) calls the method named compare for object
;; o, with arguments a and b.  In this case the object is the
;; Clojure function my-<
user> (. my-< (compare 1 2))
(my-< 1 2 ) returns  true
-1
user> (. my-< (compare 2 1))
(my-< 2 1 ) returns  false
(my-< 1 2 ) returns  true
1
user> (. my-< (compare 1 1))
(my-< 1 1 ) returns  false
(my-< 1 1 ) returns  false
0

;; Calling a Clojure function in the normal way uses its invoke
;; method, not compare.
user> (. my-< (invoke 2 1))
(my-< 2 1 ) returns  false
false
```

See Clojure source file
[`src/jvm/clojure/lang/AFunction.java`][Clojure-AFunction-compare]
method `compare` if you want all the details.

[Clojure-AFunction-compare]: https://github.com/clojure/clojure/blob/clojure-1.5.1/src/jvm/clojure/lang/AFunction.java#L46


### General rules for comparators

Any comparator, whether 3-way or boolean, should return answers
consistent with a [_total order_][Total_order] on the values you want
to compare.

[Total_order]: http://en.wikipedia.org/wiki/Total_order

A total order is simply an ordering of all values from smallest to
largest, where some groups of values can all be equal to each other.
Every pair of values must be comparable to each other (i.e. no "I do
not know how to compare them" answers from the comparator).

For example, you can order all fractions written in the form `m/n` for
integers `m` and `n` from smallest to largest, in the usual way this
is done in mathematics.  Many of the fractions would be equal to each
other, e.g. `1/2 = 2/4 = 3/6`.  A comparator implementing that total
order should behave as if they are all the same.

A 3-way comparator `(cmp a b)` should return a negative, positive, or
0 `int` if `a` is before, after, or is considered equal to `b` in the
total order, respectively.

A boolean comparator `(cmp a b)` should return true if `a` is before
`b` in the total order, or false if `a` is after or considered equal
to `b`.  That is, it should work like `<` does for numbers.  As
explained later, it should _not_ behave like `<=` for numbers (see
section "Comparators for sorted sets and maps are easy to get wrong").


## Mistakes to avoid

### Comparators for sorted sets and maps are easy to get wrong

This is just as accurately stated as "comparators are easy to get
wrong", but it is often more noticeable when you use a bad comparator
for sorted sets and maps.  If you write the kinds of bad comparators
in this section and use them to call `sort`, usually little or nothing
will go wrong (although inconsistent comparators are not good for
sorting, either).  With sorted sets and maps, these bad comparators
can cause values not to be added to your sorted collections, or to be
added but not be found when you search for them.

Suppose you want a sorted set containing vectors of two elements,
where each is a string followed by a number, e.g. `["a" 5]`.  You want
the set sorted by the number, and to allow multiple vectors with the
same number but different strings.  Your first try might be to write
something like `by-2nd`:

```clojure
(defn by-2nd [a b]
  (compare (second a) (second b)))
```

But look what happens when you try to add multiple vectors with the
same number.

```clojure
user> (sorted-set-by by-2nd ["a" 1] ["b" 1] ["c" 1])
#{["a" 1]}
```

Only one element is in the set, because `by-2nd` treats all three of
the vectors as equal.  Sets should not contain duplicate elements, so
the other elements are not added.

A common thought in such a case is to use a boolean comparator
function based on `<=` instead of `<`:

```clojure
(defn by-2nd-<= [a b]
  (<= (second a) (second b)))
```

The boolean comparator `by-2nd-<=` seems to work correctly on the
first step of creating the set, but fails when testing whether
elements are in the set.

```clojure
user> (def sset (sorted-set-by by-2nd-<= ["a" 1] ["b" 1] ["c" 1]))
#'user/sset
user> sset
#{["c" 1] ["b" 1] ["a" 1]}
user> (sset ["c" 1])
nil
user> (sset ["b" 1])
nil
user> (sset ["a" 1])
nil
```

The problem here is that `by-2nd-<=` gives inconsistent answers.  If
you ask it whether `["c" 1]` comes before `["b" 1]`, it returns true
(which Clojure's boolean-to-int comparator conversion turns into -1).
If you ask it whether `["b" 1]` comes before `["c" 1]`, again it returns
true (again converted into -1 by Clojure).  One cannot reasonably
expect an implementation of a sorted data structure to provide any
kind of guarantees on its behavior if you give it an inconsistent
comparator.

The techniques described in "Multi-field comparators" above provide
correct comparators for this example.  In general, be wary of
comparing only parts of values to each other.  Consider having some
kind of tie-breaking condition after all of the fields of interest to
you have been compared.

Aside: If you do _not_ want multiple vectors in your set with the same
number, `by-2nd` is the comparator you should use.  It gives exactly
the behavior you want.  (TBD: Are there any caveats here?  Will
`sorted-set` ever use `=` to compare elements for any reason, or only
the supplied comparator function?)


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
conditional checks and return -1, 0, or 1, or to use boolean
comparators.

Why?  Java comparators must return a 32-bit `int` type, so when a
Clojure function is used as a comparator and it returns any type of
number, that number is converted to an `int` behind the scenes using
the Java method [`intValue`][NumberintValue].  See Clojure source file
[`src/jvm/clojure/lang/AFunction.java`][Clojure-AFunction-compare]
method `compare` if you want the details.

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
amounts that change sign when you truncate it to a 32-bit `int` (by
discarding all but its least significant 32 bits).  About half of all
pairs of long values are compared incorrectly by using subtraction as
a comparator.

```clojure
;; This looks good
user> (sort #(- %1 %2) [4 2 3 1])
(1 2 3 4)

;; What the heck?
user> (sort #(- %1 %2) [2147483650 2147483651 2147483652 4 2 3 1])
(3 4 2147483650 2147483651 2147483652 1 2)

user> [Integer/MIN_VALUE Integer/MAX_VALUE]
[-2147483648 2147483647]

;; How .intValue truncates a few selected values.  Note especially
;; the first and last ones.
user> (map #(.intValue %) [-2147483649 -2147483648 -1 0 1
                            2147483647  2147483648])
(2147483647 -2147483648 -1 0 1 2147483647 -2147483648)
```

Java itself uses a subtraction comparator for strings and characters,
among others.  This does not cause any problems, because the result of
subtracting an arbitrary pair of 16-bit characters converted to ints
is guaranteed to fit within an `int` without wrapping around.  If your
comparator is not guaranteed to be given such restricted inputs,
better not to risk it.


## Comparators that work between different types

Sometimes you might wish to sort a collection of values by some key,
but that key is not unique.  You want the values with the same key to
be sorted in some predictable, repeatable order, but you do not care
much what that order is.

As a toy example, you might have a collection of vectors, each with
two elements, where the first element is always a string and the
second is always a number.  You want to sort them by the number value
in increasing order, but you know your data can contain more than one
vector with the same number.  You want to break ties in some way,
consistently across multiple sorts.

This case is easily implemented using a multi-field comparator as
described in an earlier section.

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
string that represents the type of the value.  It is not simply
`(class x)`, because then numbers like `Integer` and `Long` would not
be sorted in numeric order.

```clojure
;; comparison-class throws exceptions for some types that might be
;; useful to include.

(defn comparison-class [x]
  (cond (nil? x) ""
        ;; Lump all numbers together since Clojure's compare can
        ;; compare them all to each other sensibly.
        (number? x) "java.lang.Number"

        ;; sequential? includes lists, conses, vectors, and seqs of
        ;; just about any collection, although it is recommended not
        ;; to use this to compare seqs of unordered collections like
        ;; sets or maps (vectors should be OK).  This should be
        ;; everything we would want to compare using cmp-seq-lexi
        ;; below.  TBD: Does it leave anything out?  Include anything
        ;; it should not?
        (sequential? x) "clojure.lang.Sequential"

        (set? x) "clojure.lang.IPersistentSet"
        (map? x) "clojure.lang.IPersistentMap"
        (.isArray (class x)) "java.util.Arrays"

        ;; Comparable includes Boolean, Character, String, Clojure
        ;; refs, and many others.
        (instance? Comparable x) (.getName (class x))
        :else (throw
               (ex-info (format "cc-cmp does not implement comparison of values with class %s"
                                (.getName (class x)))
                        {:value x}))))

(defn cmp-seq-lexi
  [cmpf x y]
  (loop [x x
         y y]
    (if (seq x)
      (if (seq y)
        (let [c (cmpf (first x) (first y))]
          (if (zero? c)
            (recur (rest x) (rest y))
            c))
        ;; else we reached end of y first, so x > y
        1)
      (if (seq y)
        ;; we reached end of x first, so x < y
        -1
        ;; Sequences contain same elements.  x = y
        0))))

;; The same result can be obtained by calling cmp-seq-lexi on two
;; vectors, but cmp-vec-lexi should allocate less memory comparing
;; vectors.
(defn cmp-vec-lexi
  [cmpf x y]
  (let [x-len (count x)
        y-len (count y)
        len (min x-len y-len)]
    (loop [i 0]
      (if (== i len)
        ;; If all elements 0..(len-1) are same, shorter vector comes
        ;; first.
        (compare x-len y-len) 
        (let [c (cmpf (x i) (y i))]
          (if (zero? c)
            (recur (inc i))
            c))))))

(defn cmp-array-lexi
  [cmpf x y]
  (let [x-len (alength x)
        y-len (alength y)
        len (min x-len y-len)]
    (loop [i 0]
      (if (== i len)
        ;; If all elements 0..(len-1) are same, shorter array comes
        ;; first.
        (compare x-len y-len) 
        (let [c (cmpf (aget x i) (aget y i))]
          (if (zero? c)
            (recur (inc i))
            c))))))


(defn cc-cmp
  [x y]
  (let [x-cls (comparison-class x)
        y-cls (comparison-class y)
        c (compare x-cls y-cls)]
    (cond (not= c 0) c  ; different classes

          ;; Compare sets to each other as sequences, with elements in
          ;; sorted order.
          (= x-cls "clojure.lang.IPersistentSet")
          (cmp-seq-lexi cc-cmp (sort cc-cmp x) (sort cc-cmp y))

          ;; Compare maps to each other as sequences of [key val]
          ;; pairs, with pairs in order sorted by key.
          (= x-cls "clojure.lang.IPersistentMap")
          (cmp-seq-lexi cc-cmp
                        (sort-by key cc-cmp (seq x))
                        (sort-by key cc-cmp (seq y)))

          (= x-cls "java.util.Arrays")
          (cmp-array-lexi cc-cmp x y)

          ;; Make a special check for two vectors, since cmp-vec-lexi
          ;; should allocate less memory comparing them than
          ;; cmp-seq-lexi.  Both here and for comparing sequences, we
          ;; must use cc-cmp recursively on the elements, because if
          ;; we used compare we would lose the ability to compare
          ;; elements with different types.
          (and (vector? x) (vector? y)) (cmp-vec-lexi cc-cmp x y)

          ;; This will compare any two sequences, if they are not both
          ;; vectors, e.g. a vector and a list will be compared here.
          (= x-cls "clojure.lang.Sequential")
          (cmp-seq-lexi cc-cmp x y)
          
          :else (compare x y))))
```

Here is a quick example demonstrating `cc-cmp`'s ability to compare
values of different types.

```clojure
user> (pprint (sort cc-cmp [true false nil Double/MAX_VALUE 10
                            Integer/MIN_VALUE :a "b" 'c (ref 5)
                            [5 4 3] '(5 4) (seq [5]) (cons 6 '(1))
                            #{1 2 3} #{2 1}
                            {:a 1, :b 2} {:a 1, :b -2}
                            (object-array [1 2 3 4])]))
(nil
 {:a 1, :b -2}
 {:a 1, :b 2}
 #{1 2}
 #{1 2 3}
 :a
 #<Ref@1493d9b3: 5>
 (5)
 (5 4)
 [5 4 3]
 (6 1)
 c
 false
 true
 -2147483648
 10
 1.7976931348623157E308
 "b"
 [1, 2, 3, 4])
nil
```


## TBD

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
