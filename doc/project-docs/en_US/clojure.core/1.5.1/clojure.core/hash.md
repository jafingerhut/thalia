`hash` returns a 32-bit integer hash value for any object.  It is
similar to Java's `hashCode`, but it is consistent with Clojure `=`
(with a few exceptions, below).  `hashCode` is consistent with Java's
`equals` method.

When we say a hash function is consistent with `=`, it means that for
any two values `v1` and `v2` where `(= v1 v2)` is true, `(= (hash v1)
(hash v2))` is also true.  This is an important property that allows
`hash` to be used in the implementation of the `hash-set` and
`hash-map` data structures.

`hash` is consistent with `=`, with the exceptions listed here.  The
examples below show how hash values inconsistent with `=` make it a
bad idea to use such values as keys in maps, or set elements.

* (Clojure 1.6.0) `hash` is not consistent for immutable Clojure
  collections and their mutable Java counterparts.  Comparing a
  Clojure immutable set to a non-Clojure Java object implementing
  `java.util.Set` with `=` elements will be `=`, but their `hash`
  values will usually be different.  `hash` was consistent for these
  two kinds of collections in Clojure 1.5.1, before `hash` was
  enhanced in Clojure
  1.6.0. http://dev.clojure.org/jira/browse/CLJ-1372
* `hash` is not consistent for some `=` float and double values.
  Convert floats and doubles to a common type with `(float x)` or
  `(double x)`, to avoid this issue.  This behavior is by choice for
  floats and doubles. http://dev.clojure.org/jira/browse/CLJ-1036
  http://dev.clojure.org/jira/browse/CLJ-1649
* `hash` is not consistent for objects with class `VecSeq`, returned
  from calls like `(seq (vector-of :int 0 1 2))`
  http://dev.clojure.org/jira/browse/CLJ-1364
* (Clojure 1.5.1) `hash` is not consistent for some `BigInteger`
  values.  Convert them to BigInt using `(bigint x)`.  (fixed in
  Clojure 1.6.0)

Examples:

```clojure
;; The return values below are for Clojure 1.6.0.  Comments show which
;; of these differ from Clojure 1.5.1.

user=> (= (float 1.0e9) (double 1.0e9))
true
user=> (= (hash (float 1.0e9)) (hash (double 1.0e9)))
false       ; hash is inconsistent for some float/double values

user=> (def java-list (java.util.ArrayList. [1 2 3]))
#'user/java-list
user=> (def clj-vec [1 2 3])
#'user/clj-vec

;; They are `=`, even though they are different classes
user=> (= java-list clj-vec)
true
user=> (class java-list)
java.util.ArrayList
user=> (class clj-vec)
clojure.lang.PersistentVector

;; Their hash values were the same in 1.5.1, but are different in
;; 1.6.0.  `hash` was changed to avoid some common cases with too many
;; hash collisions in Clojure 1.5.1.

user=> (hash java-list)
30817
user=> (hash clj-vec)
736442005                           ; was 30817 in Clojure 1.5.1

;; If java-list and clj-vec are put into collections that do not use
;; their hash values, like a vector or array-map, then those
;; collections will be equal, too.

user=> (= [java-list] [clj-vec])
true
user=> (class {java-list 5})
clojure.lang.PersistentArrayMap
user=> (= {java-list 5} {clj-vec 5})
true
user=> (assoc {} java-list 5 clj-vec 3)
{[1 2 3] 3}

;; However, if java-list and clj-vec are put into collections that do
;; use their hash values, like a hash-set, or a key in a hash-map,
;; then those collections will not be equal because of the different
;; hash values.

user=> (class (hash-map java-list 5))
clojure.lang.PersistentHashMap
user=> (= (hash-map java-list 5) (hash-map clj-vec 5))
false                               ; was true in Clojure 1.5.1
user=> (= (hash-set java-list) (hash-set clj-vec))
false                               ; was true in Clojure 1.5.1

user=> (get (hash-map java-list 5) java-list)
5
user=> (get (hash-map java-list 5) clj-vec)
nil                                 ; was 5 in Clojure 1.5.1

user=> (conj #{} java-list clj-vec)
#{[1 2 3] [1 2 3]}                  ; was #{[1 2 3]} in Clojure 1.5.1
user=> (hash-map java-list 5 clj-vec 3)
{[1 2 3] 5, [1 2 3] 3}              ; was {[1 2 3] 3} in Clojure 1.5.1
```

Similar behavior occurs for Java collections that implement
`java.util.List`, `java.util.Set`, and `java.util.Map`.  It also
occurs for any values for which Clojure's `hash` is not consistent
with `=`.

See also: (topic Equality)  (TBD)
