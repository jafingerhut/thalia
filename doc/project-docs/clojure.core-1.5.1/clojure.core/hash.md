`hash` returns a 32-bit integer hash value for any object.  It is
similar to Java's `hashCode`.  However, `hash` is consistent with
Clojure `=` (with a few exceptions documented in the
[Equality][Equality] document), whereas `hashCode` is consistent with
Java's `equals`.

When we say a hash function is consistent with `=`, it means that for
any two values `x1` and `x2` where `(= x1 x2)` is true, `(= (hash x1)
(hash x2))` is also true.  This is an important property that allows
`hash` to be used in the implementation of data structures such as
Clojure's `hash-set` and `hash-map`.

See [Equality][Equality] for more documentation on `hash`, including
some exceptions to the consistency property in Clojure 1.5.1.

[Equality]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/equality.md
