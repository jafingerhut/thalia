# Comparators in Clojure

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


## Versions

This document was written while checking the details against Clojure
1.5.1 and Java 6, but most or all of it should apply to other
versions, too.


## Introduction

In Clojure you need comparators for sorting a collection of values, or
for maintaining a sorted collection such as a `sorted-map`,
`sorted-set`, or [`priority-map`][priority-map] (also known as a
priority queue) in a particular sorted order.

[priority-map]: https://github.com/clojure/data.priority-map

See also: `compare`, `sort`, `sort-by` `sorted-set`, `sorted-set-by`,
`sorted-map`, `sorted-map-by`, `subseq`, `rsubseq`

TBD: Make links for the see also list, and other Clojure functions
mentioned in the text below.

Here we briefly describe the default sorting order provided by the
function `compare`.  After that we give examples of custom
comparators, with some recommended guidelines and mistakes to avoid
when writing your own.

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

Because equal-length Clojure vectors are compared lexicographically,
they can be used to create sort keys for values with multiple fields
like maps or records, with only a small amount of code.  However, this
method only works if the field values to be sorted are already sorted
by `compare` in an order you wish (or you wish them in the reverse of
that order).


## Off-the-shelf comparators

TBD: Give links to Unicode string comparators, and any other useful
ones I can find.


## Pitfalls to avoid

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

```clojure
(defn comparison-class
  [obj]
  (cond (nil? x) nil  ; special case
        ;; Lump all numbers together since Clojure's compare can
        ;; compare them all to each other sensibly.
        (number? x) (.getName java.lang.Number)

        ;; Note: This returns false for Java arrays, among many other
        ;; things.  It would be easy to write a lexicographic
        ;; comparator for 1-dimensional Java arrays, but what
        ;; about for multi-dimensional Java arrays?  I suppose they
        ;; could be compared lexicographically as Object arrays,
        ;; containing either arrays or primitive elements themselves.
        ;; That could get messy handling all possible cases, given
        ;; that some of the arrays could be arrays of Objects, while
        ;; others are arrays of primitive types.

        (instance? Comparable x) (.getName (class x))

```


Every Clojure function taking two arguments and returning a boolean or
number can be used as a comparator.

Any Clojure function taking two arguments and returning a boolean or
number is made to automatically implement the `java.util.Comparator`
interface.  This is done by the `compare` method in file
`src/jvm/clojure/lang/AFunction.java` of the Clojure source code,
shown below.

```Java
    public int compare(Object o1, Object o2)
    {
        try {
            Object o = invoke(o1, o2);

            if (o instanceof Boolean) {
                if (RT.booleanCast(o))
                    return -1;
                return RT.booleanCast(invoke(o2,o1)) ? 1 : 0;
            }

            Number n = (Number) o;
            return n.intValue();
        }
        catch (Exception e) {
            throw Util.sneakyThrow(e);
        }
    }
```

TBD: Document examples where returning a boolean can cause the
comparator to be incorrect in subtle ways.  For example, using <= for
numbers means that the resulting compare function will never return 0
for equal items.  If two equal numbers are compared, the first will be
declared smaller than the second because <= returns true, causing
compare to return -1.



## Original text of a document I wrote about compare functions

It is probably also too long and needs summarizing or just plain
cutting out.


See also compare-functions.clj for shorter examples without all the
explanation.


Why does using function second-< as a comparator for sorted-set-by
treat [:a 1] and [:b 1] as equal?

One way that Clojure lets you provide a comparator is by the Java way
of providing a "3-way" comparator: you write a function of two
arguments, which I'll call x and y, and you return a negative number
if x is smaller than y, 0 if they are equal, or a positive number if x
is larger than y.

Clojure also lets you write a "2-way" comparator: a function of two
arguments that returns a boolean value.  The basic idea is that the
function should return true if the first argument x should come before
the second argument y.

The underlying Java code implementing the sorted sets needs a 3-way
compare function as described above.  What does Clojure do in this
case?  It adds a little code around the 2-way comparator to convert
the boolean into an integer.  "Wait!"  you say.  "There are only 2
boolean values, but 3 possible comparison results.  How can this be?"
Good catch.  Clojure does this by sometimes calling the function
twice, like this:

(if (cmp-2-way x y)
  -1     ; x < y
  (if (cmp-2-way y x)  ; note the reversed argument order
    1    ; x > y
    0))  ; x = y

(The code to do this is in the method 'compare' in the Clojure's
source file AFunction.java.  It is written in in Java, not Clojure,
but it does implement the behavior above.)

So, if you write a function that returns true exactly when the first
argument should come earlier in the sort order, and false if it is
equal or should come later in the sort order, it will do what you
want.  But 'equal' here means *completely equal*, not just equal in
the part that you are interested in comparing.

For example:

;; Give a name to our anonymous function #(< (second %) (second %2))
user=> (defn second-< [x y]
         (< (second x) (second y)))

user=> (sorted-set-by second-< [:a 1] [:b 1] [:c 1])
#{[:a 1]}

What happened here?  First [:a 1] was added to the set being
constructed.  Then at some point a comparison was done between [:a 1]
and [:b 1] by calling second-<, which went like this:

user=> (second-< [:a 1] [:b 1])
false   ; 1 < 1 is false
user=> (second-< [:b 1] [:a 1])
false   ; 1 < 1 is still false

Since neither is less than the other, they must be equal.  Two equal
items should not be in the same set, so don't add [:b 1] to the set.
The same happens when we test whether to add [:c 1] to the set.

At this point you might be thinking of using this comparator instead:

user=> (defn second-<= [x y]
         (<= (second x) (second y)))

user=> (sorted-set-by second-<= [:a 1] [:b 1] [:c 1])
#{[:c 1] [:b 1] [:a 1]}

That certainly looks better.  But there is still trouble afoot:

user=> (def s1 (sorted-set-by second-<= [:a 1] [:b 1] [:c 1]))
#'user/s1
user=> (contains? s1 [:a 1])
false
user=> (contains? s1 [:b 1])
false

It doesn't seem to contain any of the elements when you test if they
are in the set!  Now what is going on?

The function second-<= is a bad comparator.  To see why, suppose the
sorted-set code compares [:a 1] and [:b 1] at some point.  second-<=
will return true because 1 <= 1, and so the 3-way comparison will
return -1, indicating that [:a 1] and [:b 1] are different and [:a 1]
should come earlier.  Fine, you think.  You didn't really care which
of [:a 1] and [:b 1] is earlier in the sorted order, as long as they
are both in the set.

But wait.  During a later operation the sorted-set might compare them
in the opposite order, [:b 1] and [:a 1].  second-<= will return true
again, and so the 3-way comparison will return -1, indicating that [:b
1] and [:a 1] are different and [:b 1] should come earlier.  This is
inconsistent with the earlier return value, and so all bets are off as
to how the sorted set using second-<= will behave.  We were lucky we
didn't give it to a function like sort and have it go into an infinite
loop.

See (Note 1) below if you are interested in some details on how one
can write a good 2-way comparator in Clojure.  2-way comparators are
best if they are something simple like < or >.  As soon as you start
trying to compare pieces of a data structure to each other (e.g. the
values of some keys in maps, but not all of them), you can easily
create problems like the ones in the examples above.

It is often more straightforward to write a 3-way comparator that
returns an integer, following these rules;

    If (mycmp x y) return a negative number, (mycmp y x) must return a
    positive number.  (x < y implies y > x)

    If (mycmp x y) return a positive number, (mycmp y x) must return a
    negative number.  (x > y implies y < x)

    If (mycmp x y) returns 0, (mycmp y x) must return 0.  (x = y
    implies y = x)

In addition to those rules, it must satisfy the normal rules of being
a total ordering on the values being compared, like < is a total
ordering for the integers or real numbers.

Clojure has a built-in 3-way comparator 'compare' that can make it
easier to write your own 3-way comparators.  It can compare many types
of Clojure objects, and for all the types on which it works, it
satisfies the rules for being a good 3-way comparator.  It is unlikely
be the order you want all by itself, so you should use it in a way
that compares the parts of things you are interested in sorting, or
that breaks ties when yo do not care about the relative order.

Example:

(defn second-<-with-tie-break [x y]
  (let [c (compare (second x) (second y))]
    (if (not= c 0)
      ;; (second x) and (second y) are different, so their comparison
      ;; result decides the order.
      c
      ;; Otherwise we don't care as long as ties are broken
      ;; consistently.
      (compare x y))))


Clojure even lets you write a compairson function that sometimes
returns an integer, and sometimes a boolean.  That is even less
recommended than writing a 2-way comparator.



(Note 1)

If you want to write a good comparator `mycmp`, one way is to write a
2-way comparator that returns a boolean, and be sure that it follows
these rules:

    If (mycmp x y) returns true, (mycmp y x) must return false.
    (second-<= violates this rule when (second x) and (second y) are
    equal.)

    If x and y are the same value, both (mycmp x y) and (mycmp y x)
    must return false.  (second-<= violates this rule, too)

    If (mycmp x y) returns false, and x and y are the not the same
    value, (mycmp y x) must return true.  (second-< violates this
    rule)

That can be tricky to get right.  It can be so tricky to get right,
I'm not even going to try to write an example of a good comparator for
the 2-element vector example that we started with, for fear of getting
it wrong.
