# Comparison functions in Clojure and Java

TBD: Is Java sort guaranteed to be stable for array elements
considered equal by the given comparison function?

TBD: Does the above matter for Clojure, except for sorting
vectors/arrays?  I don't see how it would matter for sorted sets or
sorted maps, but think about it a bit more.

TBD: How does Double/NaN compare to other things?

TBD: Maybe mention these other Clojure functions that make use of
comparison functions.  If mentioned nowhere else, at least at the end
of the see also list: `clojure.parallel/pmax`,
`clojure.parallel/pmin`, `clojure.parallel/psummary`,
`clojure.parallel/psort`

TBD: Is function `clojure.core/comparator` useful for anything?  It
seems like with `AFunction`'s `compare` method changing boolean return
values to -1, 0, or 1, `comparator` would be unnecessary and perhaps
obsolete.


## Versions

This document was written while checking the details against Clojure
1.5.1 and Java 6, but most or all of it should apply to other
versions, too.


## Introduction

In Clojure you need comparison functions for sorting a collection of
values, or for maintaining a sorted collection such as a sorted map,
sorted set, or priority queue in a particular sorted order.

Here we describe the default sorting order provided by Clojure via the
function `compare`.  We also give examples of custom comparison
functions, with some recommended guidelines and mistakes to avoid when
writing your own.

See also: `sort`, `sort-by` `sorted-set`, `sorted-set-by`,
`sorted-map`, `sorted-map-by`, `subseq`, `rsubseq`, `compare`

TBD: Make links for the see also list, and other Clojure functions
mentioned in the text below.

If you don't specify your own comparison function, sorting is done by
a built-in function `compare`.  `compare` works for many types of
objects, ordering values in a particular way:

1. Increasing numeric order for number types.

2. [Lexicographic order][lexicographic] for strings, i.e. dictionary
   order.  This is the default order that Java sorts strings, too.

[lexicographic]: http://en.wikipedia.org/wiki/Lexicographical_order

3. String order for Clojure symbols and keywords, using the names of
   the symbols/keywords as the strings to compare.

4. Shortest-to-longest order for Clojure vectors, with lexicographic
   ordering among equal length vectors.

5. Any Java types that implement the [`Comparable`][Comparable]
   interface.  See "All Known Implementing Classes" there.

[Comparable]: http://docs.oracle.com/javase/6/docs/api/java/lang/Comparable.html

6. `nil` can be compared to any value, and is less than all other
   values (except `nil`, which it is equal to).

If this built-in sorting order doesn't meet your needs, or doesn't
work at all for objects of a type you wish to sort, you can write your
own comparison function and use that instead.  There are a few rules
to follow when writing a comparison function that works correctly.


## Built-in `compare`

Numbers are sorted in increasing order.  This includes integers of all
sizes (ints, longs, BigIntegers, etc.), floats, doubles, and Clojure
ratios.

    user> (sort [22/7 2.71828 Double/NEGATIVE_INFINITY 1 55 3N])
    (-Infinity 1 2.71828 3N 22/7 55)

Strings are sorted in lexicographic order, i.e. dictionary order, by
their representation as sequences of Unicode UTF-16 code units.  This
is alphabetical order for strings restricted to the ASCII subset of
characters (case-sensitive).  Additional details provided by the Java
documentation for `String`'s [`compareTo`][StringcompareTo] method.

[StringcompareTo]: http://docs.oracle.com/javase/6/docs/api/java/lang/String.html#compareTo%28java.lang.String%29

    user> (def sset1 (sorted-set "aardvark" "boo" "a" "Antelope" "bar"))
    #'user/sset1
    user> sset1
    #{"Antelope" "a" "aardvark" "bar" "boo"}

Clojure symbols are sorted by their representation as strings, sorting
first by their namespace name, and if they are in the same namespace,
then by their name.  If no namespace is included, those symbols will
be sorted before any symbol with a namespace.

    user> (def sset2 (sorted-set 'user/foo 'clojure.core/pprint 'bar 'clojure.core/apply 'user/zz))
    #'user/sset2
    user> sset2
    #{bar clojure.core/apply clojure.core/pprint user/foo user/zz}

Clojure keywords are sorted similarly to symbols.  The built-in
compare will not let you sort symbols and keywords in the same
collection, though.

    user> (def smap1 (sorted-map :map-key 10, :amp [3 2 1], :blammo "kaboom"))
    #'user/smap1
    user> smap1
    {:amp [3 2 1], :blammo "kaboom", :map-key 10}

Clojure vectors are sorted by their length first, from shortest to
longest, then lexicographically among equal-length vectors.

    user> (sort [[1 2] [1 -5] [10000] [4 -1 20] [3 2 5]])
    ([10000] [1 -5] [1 2] [3 2 5] [4 -1 20])

You will get an exception if you try to make the default `compare`
function compare objects with different types (any two numeric types
listed above can be compared to each other, but not to a non-numeric
type).

    user> (sort [5 "a"])
    ClassCastException java.lang.Long cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)
    user> (sort [:foo 'bar])
    ClassCastException clojure.lang.Keyword cannot be cast to clojure.lang.Symbol  clojure.lang.Symbol.compareTo (Symbol.java:106)

Implementation detail: Clojure Refs can also be sorted using
`compare`.  They are sorted in the order they were created.  TBD: Why?

You will also get an exception if you try to use the default `compare`
on a Clojure list, set, map, or any other type not mentioned above.
If you wish to sort such values, you must implement your own
comparison function for them.

    user> (sort [#{1 2} {2 4}])
    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)
    user> (sort [{:a 1 :b 3} {:c -2 :d 4}])
    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)

Implementation details: See Clojure source file
`src/jvm/clojure/lang/Util.java` method `compare`, and `compareTo`
methods used to implement `Comparable` interface in several Java
source files in Clojure's implementation.


## Writing your own comparison functions


----------------------------------------------------------------------
Every Clojure function taking two arguments and returning a boolean or
number can be used as a comparison function.
----------------------------------------------------------------------

Any Clojure function taking two arguments and returning a boolean or
number is made to automatically implement the java.util.Comparator
interface.  This is done by the compare method in file
src/jvm/clojure/lang/AFunction.java of the Clojure source code.

===== AFunction.java ===================
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
========================================

TBD: Document examples where returning a boolean can cause the
comparison function to be incorrect in subtle ways.  For example,
using <= for numbers means that the resulting compare function will
never return 0 for equal items.  If two equal numbers are compared,
the first will be declared smaller than the second because <= returns
true, causing compare to return -1.

TBD: Document examples where the intValue() call can cause unexpected
things to happen, e.g. if the function returns a Double or Long, and
intValue() truncates the value in ways that changes the expected
behavior.

    Example 1: Using #(- %1 %2) for comparing float or double values
    will cause values less than 0.5 apart to be treated as equal,
    which is likely not what you want.  (TBD if that is the precise
    condition.  Verify.)

    Example 2: Using #(- %1 %2) for comparing numbers outside of the
    range of an Integer (32-bit 2's complement signed value) can cause
    comparison results that you likely do not want, because the upper
    bits of the subtraction will be truncated, and only the least
    significant 32 bits will be used to decide whether the value is
    negative, positive, or 0.  This can reverse the order of the
    comparison.


----------------------------------------------------------------------

See also compare-functions.clj for shorter examples without all the
explanation.


Why does using function second-< as a comparison function for
sorted-set-by treat [:a 1] and [:b 1] as equal?

One way that Clojure lets you provide a comparison function is by the
Java way of providing a "3-way" comparison function: you write a
function of two arguments, which I'll call x and y, and you return a
negative number if x is smaller than y, 0 if they are equal, or a
positive number if x is larger than y.

Clojure also lets you write a "2-way" comparison function: a function
of two arguments that returns a boolean value.  The basic idea is that
the function should return true if the first argument x should come
before the second argument y.

The underlying Java code implementing the sorted sets needs a 3-way
compare function as described above.  What does Clojure do in this
case?  It adds a little code around the 2-way comparison function to
convert the boolean into an integer.  "Wait!"  you say.  "There are
only 2 boolean values, but 3 possible comparison results.  How can
this be?"  Good catch.  Clojure does this by sometimes calling the
function twice, like this:

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

At this point you might be thinking of using this comparison function
instead:

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

The function second-<= is a bad comparison function.  To see why,
suppose the sorted-set code compares [:a 1] and [:b 1] at some point.
second-<= will return true because 1 <= 1, and so the 3-way comparison
will return -1, indicating that [:a 1] and [:b 1] are different and
[:a 1] should come earlier.  Fine, you think.  You didn't really care
which of [:a 1] and [:b 1] is earlier in the sorted order, as long as
they are both in the set.

But wait.  During a later operation the sorted-set might compare them
in the opposite order, [:b 1] and [:a 1].  second-<= will return true
again, and so the 3-way comparison will return -1, indicating that [:b
1] and [:a 1] are different and [:b 1] should come earlier.  This is
inconsistent with the earlier return value, and so all bets are off as
to how the sorted set using second-<= will behave.  We were lucky we
didn't give it to a function like sort and have it go into an infinite
loop.

See (Note 1) below if you are interested in some details on how one
can write a good 2-way comparison function in Clojure.  2-way
comparison functions are best if they are something simple like < or
>.  As soon as you start trying to compare pieces of a data structure
to each other (e.g. the values of some keys in maps, but not all of
them), you can easily create problems like the ones in the examples
above.

It is often more straightforward to write a 3-way comparison function
that returns an integer, following these rules;

    If (mycmp x y) return a negative number, (mycmp y x) must return a
    positive number.  (x < y implies y > x)

    If (mycmp x y) return a positive number, (mycmp y x) must return a
    negative number.  (x > y implies y < x)

    If (mycmp x y) returns 0, (mycmp y x) must return 0.  (x = y
    implies y = x)

In addition to those rules, it must satisfy the normal rules of being
a total ordering on the values being compared, like < is a total
ordering for the integers or real numbers.

Clojure has a built-in 3-way comparison function 'compare' that can
make it easier to write your own 3-way comparison functions.  It can
compare many types of Clojure objects, and for all the types on which
it works, it satisfies the rules for being a good 3-way comparison
function.  It is unlikely be the order you want all by itself, so you
should use it in a way that compares the parts of things you are
interested in sorting, or that breaks ties when yo do not care about
the relative order.

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
recommended than writing a 2-way comparison function.



(Note 1)

If you want to write a good comparison function mycmp, one way is to
write a 2-way comparison function that returns a boolean, and be sure
that it follows these rules:

    If (mycmp x y) returns true, (mycmp y x) must return false.
    (second-<= violates this rule when (second x) and (second y) are
    equal.)

    If x and y are the same value, both (mycmp x y) and (mycmp y x)
    must return false.  (second-<= violates this rule, too)

    If (mycmp x y) returns false, and x and y are the not the same
    value, (mycmp y x) must return true.  (second-< violates this
    rule)

That can be tricky to get right.  It can be so tricky to get right,
I'm not even going to try to write an example of a good comparison
function for the 2-element vector example that we started with, for
fear of getting it wrong.
