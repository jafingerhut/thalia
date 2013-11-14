(ns thalia.doc)


(defn alter-doc! [v new-docstring]
  (alter-meta! v assoc :doc new-docstring))


(defn append-doc! [v additional-docstring]
  (let [^String orig-doc (:doc (meta v))]
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
    "compare is the default comparator for sorting with sort and sort-by,
for ordering the elements of a sorted-set, and for ordering the keys
of a sorted-map.  See (topic Comparators).

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
elements or map keys (see example below).  Convert BigIntegers to
BigInt using (bigint x), and floats and doubles to a common type
with (float x) or (double x), to avoid this issue.  This behavior is
by choice: http://dev.clojure.org/jira/browse/CLJ-1036

Examples:

    user=> (def x 8589934588)
    #'user/x
    user=> (= (bigint x) (biginteger x))
    true
    user=> (= (hash (bigint x)) (hash (biginteger x)))
    false         ; hash is not consistent with = for all BigInteger values
    user=> (def s1 (hash-set (bigint x)))
    #'user/s1
    user=> (def s2 (hash-set (biginteger x)))
    #'user/s2
    user=> s1
    #{8589934588N}
    user=> s2
    #{8589934588}     ; s1 and s2 look the same
    user=> (= (first s1) (first s2))
    true              ; their elements are =
    ;; However, the sets are not = because of hash inconsistency.
    user=> (= s1 s2)
    false

    user=> (= (float 1.0e9) (double 1.0e9))
    true
    user=> (= (hash (float 1.0e9)) (hash (double 1.0e9)))
    false       ; hash is not consistent with = for all float/double values

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

See docs for function subs, section 'Memory use warning'."]

   [#'clojure.core/re-matches
    "(re-matches regex s) is the same as (re-find regex s), except that
re-matches only returns a match result if the regex can be matched
against the entire string.  re-find returns a match if the regex can
be matched against any substring of the given string.

    user=> (re-find #\"\\d+\" \"abc123def\")
    \"123\"
    user=> (re-matches #\"\\d+\" \"abc123def\")
    nil
    user=> (re-matches #\"\\d+\" \"123\")
    \"123\"

See the extended docs of re-find for additional examples, and notes on
how the return value is a vector when there are capture groups in the
regex.

See also: re-find, re-seq, re-pattern, clojure.string/replace,
clojure.string/replace-first, re-matcher, re-groups

See docs for function subs, section 'Memory use warning'."]

   [#'clojure.core/re-seq
    "(re-seq regex s) is the same as (re-find regex s), except that re-seq
returns a sequence of all matches, not only the first match.  It
returns nil if there were no matches.  Capture groups are handled the
same way as for re-find.

    user=> (re-seq #\"\\d\" \"Mac OS X 10.6.8\")
    (\"1\" \"0\" \"6\" \"8\")
    user=> (re-seq #\"\\d+\" \"Mac OS X 10.6.8\")
    (\"10\" \"6\" \"8\")
    user=> (re-seq #\"ZZ\" \"Mac OS X 10.6.8\")
    nil

    ;; Capture groups in the regex cause each returned match to be a
    ;; vector of matches.  See re-find for more examples.
    user=> (re-seq #\"\\S+:\\d+\" \" RX pkts:18 err:5 drop:48\")
    (\"pkts:18\" \"err:5\" \"drop:48\")
    user=> (re-seq #\"(\\S+):(\\d+)\" \" RX pkts:18 err:5 drop:48\")
    ([\"pkts:18\" \"pkts\" \"18\"] [\"err:5\" \"err\" \"5\"] [\"drop:48\" \"drop\" \"48\"])

See also: re-find, re-matches, re-pattern, clojure.string/replace,
clojure.string/replace-first, re-matcher, re-groups

See docs for function subs, section 'Memory use warning'."]

   [#'clojure.core/read
    "You *SHOULD NOT* use clojure.core/read or clojure.core/read-string
to read data from untrusted sources.  They were designed only for
reading Clojure code and data from trusted sources (e.g. files that
you know you wrote yourself, and no one else has permission to modify
them).

Instead, either:

1. Use another data serialization format such as JSON, XML, etc. and a
   library for reading them that you trust not to have
   vulnerabilities, or

2. if you want a serialization format that can be read safely and
   looks like Clojure data structures, use edn.  For Clojure 1.3 and
   later, the tools.reader contrib library provides an edn reader.
   There is also clojure.edn/read and clojure.edn/read-string provided
   in Clojure 1.5.

[edn]: https://github.com/edn-format/edn
[tools.reader]: http://github.com/clojure/tools.reader

You definitely should not use clojure.core/read or read-string if
*read-eval* has its default value of true, because an attacker
could cause your application to execute arbitrary code while it is
reading.  Example:

    user> (read-string \"#=(clojure.java.shell/sh \\\"echo\\\" \\\"hi\\\")\")
    {:exit 0, :out \"hi\\n\", :err \"\"}

It is straightforward to modify the example above into more
destructive ones that remove all of your files, copy them to someone
else's computer over the Internet, install Trojans, etc.

Even if you bind *read-eval* to false first, like so:

    (defn read-string-unsafely [s]
      (binding [*read-eval* false]
        (read-string s)))

you may hope you are safe reading untrusted data that way, but in
Clojure 1.4 and earlier, an attacker can send data that causes your
system to execute arbitrary Java constructors.  Most of these are
benign, but it only takes one to ruin your application's day.
Examples that should scare you:

    ;; This causes a socket to be opened, as long as the JVM
    ;; sandboxing allows it.
    (read-string-unsafely \"#java.net.Socket[\\\"www.google.com\\\" 80]\")

    ;; This causes precious-file.txt to be created if it doesn't
    ;; exist, or if it does exist, its contents will be erased (given
    ;; appropriate JVM sandboxing permissions, and underlying OS file
    ;; permissions).
    (read-string-unsafely \"#java.io.FileWriter[\\\"precious-file.txt\\\"]\")

The particular issue of executing arbitrary Java constructors used in
the examples above no longer works in Clojure 1.5 when *read-eval*
is false.  Even so, you *SHOULD NEVER USE* clojure.core/read or
clojure.core/read-string for reading untrusted data.  Use an edn
reader or a different data serialization format.

Why should I do this, you may ask, if Clojure 1.5 closes the Java
constructor hole?  Because clojure.core/read and read-string are
designed to be able to do dangerous things, and they are not
documented nor promised to be safe from unwanted side effects.  If you
use them for reading untrusted data, and a dangerous side effect is
found in the future, you will be told that you are using the wrong
tool for the job.  clojure.edn/read and read-string, and the
tools.reader.edn library, are documented to be safe from unwanted
side effects, and if any bug is found in this area it should get quick
attention and corrected.

If you understand all of the above, and want to use read or
read-string to read data from a _trusted_ source, continue on below.

    ;; read wants its reader arg (or *in*) to be a
    ;; java.io.PushbackReader.  with-open closes r after the with-open
    ;; body is done.  *read-eval* specifies whether to allow #=()
    ;; forms when reading, and evaluate them as a side effect while
    ;; reading.

    (defn read-from-file-with-trusted-contents [filename]
      (with-open [r (java.io.PushbackReader.
                      (clojure.java.io/reader filename))]
        (binding [*read-eval* false]
          (read r))))

    user> (spit \"testfile.txt\" \"{:a 1 :b 2 :c 3}\")
    nil
    user> (read-from-file-with-trusted-contents \"testfile.txt\")
    {:a 1, :b 2, :c 3}"]

   [#'clojure.core/read-string
    "WARNING: You *SHOULD NOT* use clojure.core/read-string to read data
from untrusted sources.  See clojure.core/read docs.  The same
security issues exist for both read and read-string."]

   [#'clojure.core/sort
    "If you supply a comparator, it must implement the Java Comparator
interface, but this includes Clojure functions that implement a 3-way
or boolean comparator.  See (topic Comparators) for details on boolean
comparators.

sort is guaranteed to be stable, since it is implemented using the
sort method of Java's java.util.Arrays class.  This means that if two
values in the input collection are considered equal by the comparator,
they are guaranteed to remain in the same relative order in the output
as they had in the input.

Examples:

    user=> (sort [3 -7 10 8 5.3 9/5 -7.1])
    (-7.1 -7 9/5 3 5.3 8 10)
    user=> (sort #(compare %2 %1) '(apple banana aardvark zebra camel))
    (zebra camel banana apple aardvark)

    user=> (def x (to-array [32 9 11]))
    #'user/x
    user=> (seq x)
    (32 9 11)
    user=> (sort x)   ; returns sorted sequence
    (9 11 32)
    user=> (seq x)    ; but also modifies Java array x
    (9 11 32)
    user=> (sort (aclone x))   ; can avoid this by copying the array
    (9 11 32)
    ;; Such copying is unnecessary for args that are not a Java array

See also: sort-by, compare, (topic Comparators)"]

   [#'clojure.core/sort-by
    "See extended docs for sort, all of which applies to sort-by.

Examples:

    user=> (sort-by count [\"lummox\" \"antidisestablishmentarianism\" \"a\"])
    (\"a\" \"lummox\" \"antidisestablishmentarianism\")
    user=> (sort-by first > [[8.67 -5] [5 1] [-22/7 3.0] [5 0]])
    ([8.67 -5] [5 1] [5 0] [-22/7 3.0])

The example in sort extended docs demonstrating a Java array being
modified applies to sort-by, too, including using aclone to copy the
array before sorting to avoid that issue.

See also: sort, compare, (topic Comparators)"]


;; TBD: Are the sequences returned by subseq and rsubseq lazy?

   [#'clojure.core/sorted-map
"Sorted maps maintain their keys in sorted order, sorted by the
function compare.  Use sorted-map-by to get a different key order.

    ;; function compare sorts keywords alphabetically
    user=> (sorted-map :d 0 :b -5 :a 1)
    {:a 1, :b -5, :d 0}
    user=> (assoc (sorted-map :d 0 :b -5 :a 1) :c 57)
    {:a 1, :b -5, :c 57, :d 0}

Sorted maps are in most ways similar to unsorted maps.  Differences
include:

* seq returns a sequence of the key/value pairs in order, sorted by
  their keys.  This affects all other sequence-based operations upon
  sorted maps, e.g. first, rest, map, for, doseq, and many others.
* rseq returns this same sequence but in reverse order.  It does so
  lazily, unlike (reverse (seq coll)), which must generate the entire
  sequence before it can reverse it.
* You can use subseq or rsubseq on a sorted map to get a sorted
  sequence of all key/value pairs with keys in a specified range.
* Unsorted maps use = to compare keys, but sorted maps use compare or
  a caller-supplied comparator.  A sorted map's comparator can throw
  exceptions if you put incomparable keys in the same map.
* There is no transient version of sorted maps.

Examples:

    user=> (def births
             (sorted-map -428 \"Plato\"      -384 \"Aristotle\" -469 \"Socrates\"
                         -320 \"Euclid\"     -310 \"Aristarchus\" 90 \"Ptolemy\"
                         -570 \"Pythagoras\" -624 \"Thales\"    -410 \"Eudoxus\"))
    #'user/births
    user=> (first births)
    [-624 \"Thales\"]
    user=> (take 4 births)
    ([-624 \"Thales\"] [-570 \"Pythagoras\"] [-469 \"Socrates\"] [-428 \"Plato\"])
    user=> (keys births)
    (-624 -570 -469 -428 -410 -384 -320 -310 90)
    user=> (vals births)   ; returns values in order by sorted keys
    (\"Thales\" \"Pythagoras\" \"Socrates\" \"Plato\" \"Eudoxus\" \"Aristotle\" \"Euclid\" \"Aristarchus\" \"Ptolemy\")

subseq and rsubseq return a sequence of all key/value pairs with a
specified range of keys.  It takes O(log N) to find the first pair,
where N is the size of the whole map, and O(1) time for each
additional pair, so it is more efficient than the O(N) approach of
taking the entire sequence and filtering out the unwanted pairs.

    user=> (subseq births > -400)
    ([-384 \"Aristotle\"] [-320 \"Euclid\"] [-310 \"Aristarchus\"] [90 \"Ptolemy\"])
    user=> (subseq births > -400 < -100)
    ([-384 \"Aristotle\"] [-320 \"Euclid\"] [-310 \"Aristarchus\"])
    user=> (rsubseq births > -400 < -100)
    ([-310 \"Aristarchus\"] [-320 \"Euclid\"] [-384 \"Aristotle\"])

Both unsorted and sorted maps follow the rule of 'first equal key to
be added wins'.  The difference is in what keys they consider to be
equal: unsorted uses =, sorted uses compare or a custom comparator.

    user=> (def m1 (hash-map 1.0 \"floatone\" 1 \"intone\" 1.0M \"bigdecone\"
                             1.5M \"bigdec1.5\" 3/2 \"ratio1.5\"))
    #'user/m1
    user=> m1     ; every key is unique according to =
    {1.0 \"floatone\", 1 \"intone\", 3/2 \"ratio1.5\", 1.5M \"bigdec1.5\",
     1.0M \"bigdecone\"}
    user=> (dissoc m1 1 3/2)
    {1.0 \"floatone\", 1.5M \"bigdec1.5\", 1.0M \"bigdecone\"}

    ;; compare treats 1.0, 1, 1.0M as equal, so first of those keys
    ;; wins.  Similarly for 1.5M and 3/2.  Note that the last *value*
    ;; for any equal key wins, as you should expect when assoc'ing
    ;; key/vals to a map.
    user=> (def m2 (sorted-map 1.0 \"floatone\" 1 \"intone\" 1.0M \"bigdecone\"
                               1.5M \"bigdec1.5\" 3/2 \"ratio1.5\"))
    #'user/m2
    user=> m2
    {1.0 \"bigdecone\", 1.5M \"ratio1.5\"}
    user=> (dissoc m2 1 3/2)
    {}       ; removing a key only needs equality according to compare

You may search an unsorted map for any value with no exception.

    user=> (m1 1)
    \"intone\"
    user=> (m1 \"a\")
    nil     ; no exception, just nil indicating no such key \"a\"

Searching sorted maps calls the comparator with the searched-for value
and some of the keys in the map, which throws an exception if the
comparator does.

    user=> (m2 1)
    \"bigdecone\"
    user=> (m2 \"a\")   ; this gives exception from compare
    ClassCastException java.lang.Double cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)

Sorted maps maintain the key/value pairs in sorted order by key using
a persistent red-black tree data structure.  It takes O(log N) time to
add or remove a key/value pair, but the constant factors involved are
typically larger than for unsorted maps.

See also: sorted-map-by, compare, hash-map, assoc, dissoc, keys, vals,
          subseq, rsubseq"]

   [#'clojure.core/sorted-map-by
    "sorted-map-by returns a sorted map that maintains its keys in sorted
order, as determined by the given comparator function.  See sorted-map
docs for the differences between sorted and unsorted maps.

Be cautious when writing your own comparators, especially for sorted
maps.  Remember that all maps follow the rule of 'first equal key to
be added wins'.  If your comparator function compares two values as
equal, then at most one of them can be a key in a sorted map at one
time.  See the 'Sorted sets and maps' section of (topic
Comparators) (TBD) for more discussion.

Examples:

    user=> (sorted-map-by > 2 \"two\" 3 \"three\" 11 \"eleven\" 5 \"five\" 7 \"seven\")
    {11 \"eleven\", 7 \"seven\", 5 \"five\", 3 \"three\", 2 \"two\"}
    user=> (sorted-map-by #(compare %2 %1)
                          \"aardvark\" \"Orycteropus afer\"
                          \"lion\" \"Panthera leo\"
                          \"platypus\" \"Ornithorhynchus anatinus\")
    {\"platypus\" \"Ornithorhynchus anatinus\",
     \"lion\" \"Panthera leo\",
     \"aardvark\" \"Orycteropus afer\"}

With comparator case-insensitive-cmp below, \"Lion\" is equal to \"lion\"
and not added as a separate key in the map.  The value associated with
the second equal key \"Lion\" does replace the first value.

    user=> (require '[clojure.string :as str])
    nil
    user=> (defn case-insensitive-cmp [s1 s2]
             (compare (str/lower-case s1) (str/lower-case s2)))
    #'user/case-insensitive-cmp
    user=> (sorted-map-by case-insensitive-cmp \"lion\" \"normal lion\"
                                               \"Lion\" \"Orycteropus afer\")
    {\"lion\" \"Orycteropus afer\"}

See also: sorted-map, (topic Comparators)"]

   [#'clojure.core/sorted-set
    "Sorted sets maintain their elements in sorted order, sorted by the
function compare.  Use sorted-set-by to get a different element order.

Sorted sets are in most ways similar to unsorted sets.  Read the docs
for sorted-map to learn how sorted _maps_ differ from unsorted maps.
All of those differences apply equally to how sorted sets differ from
unsorted sets, if you replace 'key/value pairs' with 'elements', and
sorting by keys with sorting by elements.

    user=> (sorted-set 4 2 1)
    #{1 2 4}
    user=> (conj (sorted-set 4 2 1) 3)
    #{1 2 3 4}

    user=> (range 100 0 -5)
    (100 95 90 85 80 75 70 65 60 55 50 45 40 35 30 25 20 15 10 5)
    user=> (def ss (apply sorted-set (range 100 0 -5)))
    #'user/ss
    user=> ss
    #{5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100}
    user=> (first ss)
    5
    user=> (last ss)
    100
    user=> (subseq ss >= 80)
    (80 85 90 95 100)
    user=> (subseq ss > 20 < 60)
    (25 30 35 40 45 50 55)

See also: sorted-set-by, sorted-map, compare, hash-set, conj, disj,
          subseq, rsubseq"]

   [#'clojure.core/sorted-set-by
    "sorted-set-by returns a sorted set that maintains its elements in
sorted order, as determined by the given comparator function.  See
sorted-set docs for the differences between sorted and unsorted sets.

Be cautious when writing your own comparators, especially for sorted
sets.  Remember that all sets follow the rule of 'first equal element
to be added wins'.  If your comparator function compares two values as
equal, then at most one of them can be an element in a sorted set at
one time.  See the 'Sorted sets and maps' section of (topic
Comparators) (TBD) for more discussion.

Examples:

    user=> (sorted-set-by compare \"Food\" \"good\" \"air\" \"My\" \"AiR\" \"My\")
    #{\"AiR\" \"Food\" \"My\" \"air\" \"good\"}

With case-insensitive-cmp, \"AiR\" is a duplicate with \"air\" and
not added to the set, and the order is different.

    user=> (require '[clojure.string :as str])
    nil
    user=> (defn case-insensitive-cmp [s1 s2]
             (compare (str/lower-case s1) (str/lower-case s2)))
    #'user/case-insensitive-cmp
    user=> (sorted-set-by case-insensitive-cmp
                          \"Food\" \"good\" \"air\" \"My\" \"AiR\" \"My\")
    #{\"air\" \"Food\" \"good\" \"My\"}

See also: sorted-set, (topic Comparators)"]

   [#'clojure.core/subs
    "The index of the first character is 0.  An exception will be
thrown if you use negative values -- it will not index characters from
the end of the string like similar functions in some other programming
languages.  If you use non-integer values for start or end, they will
be auto-converted to integers as if by (int x).

Examples:

    user=> (subs \"abcdef\" 1 3)
    \"bc\"
    user=> (subs \"abcdef\" 1)
    \"bcdef\"
    user=> (subs \"abcdef\" 4 6)
    \"ef\"
    user=> (subs \"abcdef\" 4 7)
    StringIndexOutOfBoundsException String index out of range: 7  java.lang.String.substring (String.java:1907)
    user=> (subs \"abcdef\" 5/3 6.28)   ; args converted to ints 1 6
    \"bcdef\"

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
constructor (String. s)."]

   ])


(defn add-extra-docs! []
  (doseq [[var additional-docstring] extra-docs]
    (append-doc! var additional-docstring)))

(println "Call add-extra-docs! to modify doc strings in clojure.core")


(comment
(in-ns 'user) (use 'clojure.repl) (require 'thalia.doc) (thalia.doc/add-extra-docs!)
)
