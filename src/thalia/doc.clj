(ns thalia.doc)


(defn alter-doc! [v new-docstring]
  (alter-meta! v assoc :doc new-docstring))


(defn append-doc! [v additional-docstring]
  (let [orig-doc (:doc (meta v))]
    (alter-doc! v
                (str orig-doc
                     (if (.endsWith orig-doc "\n")
                       ""
                       "\n")
"--------- ^^^ original docs --------- VVV unofficial extra docs ---------\n"
                     additional-docstring))))


(def extra-docs
  [
   [#'clojure.core/=
    "(= x y) is true if x and y are both:

* numbers in the same 'category', and numerically the same, where
  category is one of (integer or ratio), floating point, or
  BigDecimal.  Use == if you want to compare for numerical equality
  between different categories, or you want an exception thrown if
  either value is not a number.
* sequences, lists, vectors, or queues, with equal elements in the
  same order.
* sets, with equal elements, ignoring order.
* maps, with equal key/value pairs, ignoring order.
* symbols, or both keywords, with equal namespaces and names.
* refs, vars, or atoms, and they are the same object, i.e. (identical?
  x y) is true.
* the same type defined with deftype.  The type's equiv method is
  called and its return value becomes the value of (= x y).
* other types, and Java's x.equals(y) is true.  The result should be
  unsurprising for nil, booleans, characters, and strings.

If you call = with more than two arguments, the result will be true
when all consecutive pairs are =.

Exceptions, or possible surprises:

* When comparing collections with =, numbers within the collections
  are also compared with =, so the three numeric categories above
  are significant.
* = is false for BigDecimal values with different scales, e.g. (=
  1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118
* 'Not a Number' values Float/NaN and Double/NaN are not equal to any
  value, not even themselves.  This leads to odd behavior if you use
  them as set elements or map keys.

Examples:

    user=> (= 3 3N)   ; same category integer
    true
    user=> (= 2 2.0)  ; different categories integer and floating point
    false
    user=> (= [0 1 2] '(0 1 2))
    true
    user=> (= '(0 1 2) '(0 1 2.0))   ; 2 and 2.0 are not =
    false

    ;; While this map is similar to the vector in that it maps the
    ;; same integers 0, 1, and 2 to the same values, maps and vectors
    ;; are never = to each other.
    user=> (= [\"a\" \"b\" \"c\"] {0 \"a\" 1 \"b\" 2 \"c\"})
    false

    user=> (= (with-meta #{1 2 3} {:key1 1}) (with-meta #{1 2 3} {:key1 2}))
    true                  ; Metadata is ignored when comparing

    user=> (= Double/NaN Double/NaN)  ; this is normal
    false

    user=> (def s1 #{1.0 2.0 Double/NaN})
    #'user/s1
    user=> s1
    #{2.0 1.0 NaN}
    user=> (contains? s1 1.0)         ; this is expected
    true
    user=> (contains? s1 Double/NaN)  ; this might surprise you
    false"]

   [#'clojure.core/==
    "(== x y) is true if x and y are both numbers, and represent
numerically equal values.  Unlike =, there are no separate
'categories' of numeric values that are treated as always unequal to
each other.  If you call == with more than two arguments, the result
will be true when all consecutive pairs are ==.  An exception is
thrown if any argument is not a numeric value.

Exceptions, or possible surprises:

* == is false for BigDecimal values with different scales, e.g. (==
  1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118
* 'Not a Number' values Float/NaN and Double/NaN are not equal to any
  value, not even themselves.

Examples:

    user=> (= 2 2.0)   ; = has different categories integer and floating point
    false
    user=> (== 2 2.0)  ; but == sees same numeric value
    true
    user=> (== 5 5N (float 5.0) (double 5.0) (biginteger 5))
    true
    user=> (== 5 5.0M) ; this is likely a bug
    false
    user=> (== Double/NaN Double/NaN)  ; this is normal
    false
    user=> (== 2 \"a\")
    ClassCastException java.lang.String cannot be cast to java.lang.Number  clojure.lang.Numbers.equiv (Numbers.java:206)"]

   [#'clojure.core/apply
    "f is a function and the last argument args is a sequence.  Calls f
with the elements of args as its arguments.  If more arguments are
specified (x y ...), they are added to the beginning of args to form
the complete argument list with which f is called.

Examples:

    user=> (apply + [1 2])           ; same as (+ 1 2)
    3
    user=> (apply + 1 2 [3 4 5 6])   ; same as (+ 1 2 3 4 5 6)
    21
    user=> (apply + [])              ; same as (+)
    0
    ;; This doesn't work because and is a macro, not a function
    user=> (apply and [true false true])
    CompilerException java.lang.RuntimeException: Can't take value of a macro: #'clojure.core/and, compiling:(NO_SOURCE_PATH:1:1)"]

   [#'clojure.core/compare
    "compare is the default [comparator][Comparators] for
sorting with sort and sort-by, for ordering the elements of a
sorted-set, and for ordering the keys of a sorted-map.

[Comparators]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md

As for all 3-way comparators, it takes two arguments x and y.  It
returns an int that is negative if x should come before y, positive if
x should come after y, or 0 if they are equal.

compare works for many types of values, ordering values as follows:

* numbers: increasing numeric order, returning 0 if two numbers are
  numerically equal by ==, even if = returns false
* strings, symbols, keywords: lexicographic order (aka dictionary
  order) by their representation as sequences of UTF-16 code units.
  This is alphabetical order (case-sensitive) for strings restricted
  to the ASCII subset.
* vectors: shortest-to-longest, with lexicographic ordering among
  equal length vectors.
* All Java types implementing the Comparable interface such as
  characters, booleans, File, URI, and UUID are compared via their
  compareTo methods.
* nil: can be compared to all values above, and is considered less
  than anything else.

compare throws an exception if given two values whose types are \"too
different\", e.g. it can compare Integers, Longs, and Doubles to each
other, but not strings to keywords or keywords to symbols.  It cannot
compare lists, sequences, sets, or maps.

Examples:

    user=> (sort [22/7 2.71828 Double/NEGATIVE_INFINITY 1 55 3N])
    (-Infinity 1 2.71828 3N 22/7 55)

    user=> (def sset1 (sorted-set \"aardvark\" \"boo\" \"a\"
                                  \"Antelope\" \"bar\"))
    #'user/sset1
    user=> sset1
    #{\"Antelope\" \"a\" \"aardvark\" \"bar\" \"boo\"}

See Java documentation of String's compareTo method for additional
details on String comparison.

Symbols are sorted by their representation as strings, sorting first
by their namespace name, and if they are in the same namespace, then
by their name.  If no namespace is included, those symbols will be
sorted before any symbol with a namespace.  Keywords are sorted
similarly to symbols.

    user=> (def sset2 (sorted-set 'user/foo 'clojure.core/pprint 'bar
                                  'clojure.core/apply 'user/zz))
    #'user/sset2
    user=> sset2
    #{bar clojure.core/apply clojure.core/pprint user/foo user/zz}

    user=> (def smap1 (sorted-map :map-key 10, :amp [3 2 1],
                                  :blammo \"kaboom\"))
    #'user/smap1
    user=> smap1
    {:amp [3 2 1], :blammo \"kaboom\", :map-key 10}

Vectors are sorted by their length first, from shortest to longest,
then lexicographically among equal-length vectors.

    user=> (sort [[1 2] [1 -5] [10000] [4 -1 20] [3 2 5]])
    ([10000] [1 -5] [1 2] [3 2 5] [4 -1 20])

An exception will be thrown if you call compare with different
types (any numeric types above can be compared to each other, but not
to a non-numeric type).  An exception will also be thrown if you use
compare on a list, set, map, or any other type not mentioned above.
You must implement your own comparator if you wish to sort such
values.  See [Comparators in Clojure][Comparators] for examples of
comparators that can do this.

    user=> (sort [5 \"a\"])
    ClassCastException java.lang.Long cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)
    user=> (sort [:foo 'bar])
    ClassCastException clojure.lang.Keyword cannot be cast to clojure.lang.Symbol  clojure.lang.Symbol.compareTo (Symbol.java:106)

    user=> (sort [#{1 2} {2 4}])
    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)
    user=> (sort [{:a 1 :b 3} {:c -2 :d 4}])
    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)

Implementation detail: Clojure Refs can also be sorted using
compare.  They are sorted in the order they were created."]

   [#'clojure.core/hash
    "hash returns a 32-bit integer hash value for any object.  It is
similar to Java's hashCode, but it is consistent with Clojure = (with
a few exceptions -- see below).  hashCode is consistent with Java's
equals method.

When we say a hash function is consistent with =, it means that for
any two values x1 and x2 where (= x1 x2) is true, (= (hash x1)
(hash x2)) is also true.  This is an important property that allows
hash to be used in the implementation of the hash-set and hash-map
data structures.

hash is consistent with =, except for some BigIntegers, Floats, and
Doubles.  This leads to incorrect behavior if you use them as set
elements or map keys.  Convert BigIntegers to BigInt using (bigint x),
and floats and doubles to a common type with (float x) or
(double x), to avoid this issue.  This behavior is by choice:
http://dev.clojure.org/jira/browse/CLJ-1036

See also: (topic Equality)  (TBD)"]

   [#'clojure.core/range
    "Examples:

    user=> (range 11)
    (0 1 2 3 4 5 6 7 8 9 10)
    user=> (range 5 11)
    (5 6 7 8 9 10)
    user=> (range 5 11 2)
    (5 7 9)

    ;; Just as when increasing, when decreasing the final value is not
    ;; included in the result.
    user=> (range 11 0 -1)
    (11 10 9 8 7 6 5 4 3 2 1)
    user=> (range 11 -1 -1)
    (11 10 9 8 7 6 5 4 3 2 1 0)

Be cautious when using float or double values, due to round-off
errors.  This is especially true for range, because these round-off
errors can accumulate and increase over a large number of values.

    user=> (count (range 0 100 1))
    100
    user=> (last (range 0 100 1))
    99
    user=> (count (range 0.0 10.0 0.1))
    101
    user=> (last (range 0.0 10.0 0.1))
    9.99999999999998

Functions like double-range and rangef in namespace thalia.utils may
be closer to what you want in some cases."]

   [#'clojure.core/re-find
    "(re-find regex str) is a pure function that returns the results of
the first match only.  See re-seq if you want a sequence of all
matches.  (re-find matcher) mutates the matcher object.

If there are no parenthesized 'groups' in the regex, re-find either
returns the substring of s that matches, or nil if there is no match.
It also behaves this way if all parenthesized groups do not 'capture',
because they begin with ?:

    user=> (re-find #\"\\d+\" \"abc123def\")
    \"123\"
    user=> (re-find #\"\\d+\" \"abcdef\")
    nil
    user=> (re-find #\"(?:\\d+)\" \"abc123def\")
    \"123\"

If there are capturing groups, then on a match re-find returns a
vector where the first element is the string that matches the entire
regex, and successive vector elements are either strings matching a
capture group, or nil if nothing matched that capture group.  Groups
are ordered in the same way that their left parentheses occur in the
string.

    user=> (def line \" RX packets:1871 errors:5 dropped:48 overruns:9\")
    #'user/line

    user=> (re-find #\"(\\S+):(\\d+)\" line)
    [\"packets:1871\" \"packets\" \"1871\"]

    ;; groups can nest
    user=> (re-find #\"(\\S+:(\\d+)) \\S+:\\d+\" line)
    [\"packets:1871 errors:5\" \"packets:1871\" \"1871\"]

    ;; If there is no match, re-find always returns nil, whether there
    ;; are parenthesized groups or not.
    user=> (re-find #\"(\\S+):(\\d+)\"
                    \":2 numbers but not 1 word-and-colon: before\")
    nil

    ;; A capture group can have nil as its result if it is part of an
    ;; 'or' (separated by | in the regex), and another alternative is
    ;; the one that matches.

    user=> (re-find #\"(\\D+)|(\\d+)\" \"word then number 57\")
    [\"word then number \" \"word then number \" nil]

    user=> (re-find #\"(\\D+)|(\\d+)\" \"57 number then word\")
    [\"57\" nil \"57\"]

    ;; It is also possible for a group to match the empty string.
    user=> (re-find #\"(\\d*)(\\S)\\S+\" \"lots o' digits 123456789\")
    [\"lots\" \"\" \"l\"]

See also: re-seq, re-matches, re-pattern, clojure.string/replace,
clojure.string/replace-first, re-matcher, re-groups

See 'Memory use warning' for function subs."]

   [#'clojure.core/subs
    "The index of the first character is 0.  An exception will be
thrown if you use negative values -- it will not index characters from
the end of the string like similar functions in some other programming
languages.  If you use non-integer values for start or end, they will
be auto-converted to integers as if by (int x).

Memory use warning:

subs, and many other functions that return substrings of a larger
one (e.g. re-find, re-seq, etc.) are based on Java's substring method
in class String.  Before Java version 7u6, this was implemented in
O(1) time by creating a String object that referred to an offset and
length within the original String object, thus retaining a reference
to the original as long as the substrings were referenced.  This can
cause unintentionally large memory use if you create large strings,
and then create small substrings of them with subs and similar
functions.  The large strings cannot be garbage collected because of
the references to them from the substrings.

In Java version 7u6, Java's substring() method behavior changed to
copy the desired substring into a new String object, so no references
are kept to the original.

    http://www.javaadvent.com/2012/12/changes-to-stringsubstring-in-java-7.html

If you wish to force the copying behavior, you can use the String
constructor (String. s).

Examples:

    user=> (subs \"abcdef\" 1 3)
    \"bc\"
    user=> (subs \"abcdef\" 1)
    \"bcdef\"
    user=> (subs \"abcdef\" 4 6)
    \"ef\"
    user=> (subs \"abcdef\" 4 7)
    StringIndexOutOfBoundsException String index out of range: 7  java.lang.String.substring (String.java:1907)
    user=> (subs \"abcdef\" 5/3 6.28)   ; args converted to ints
    \"bcdef\""]

   ])


(defn add-extra-docs! []
  (doseq [[var additional-docstring] extra-docs]
    (append-doc! var additional-docstring)))

(println "Call add-extra-docs! to modify doc strings in clojure.core")
