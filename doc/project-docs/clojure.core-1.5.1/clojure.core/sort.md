If you supply a comparator, it must implement the Java Comparator
interface, but you may use a Clojure function that implements a 3-way
comparator, or a boolean comparator.  See the document on [Comparators
in Clojure][ComparatorsInClojure-boolean] for details on boolean
comparators.

[ComparatorsInClojure-boolean]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md#boolean-comparators

`sort` is guaranteed to be _stable_, since it uses Java's
[`java.util.Arrays sort`][Java-Arrays-sort-method] method in its
implementation.  This means that if two values in the input collection
are considered equal by the comparator, they are guaranteed to remain
in the same relative order in the output as they had in the input.

[Java-Arrays-sort-method]: http://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#sort%28java.lang.Object[]%29

Warning: You can sort a Java array and get back a sorted immutable
Clojure data structure, but it will also change the input Java array,
by sorting it.  Copy the array before sorting if you want to avoid
this.

```clojure
    user=> (def x (to-array [32 11]))
    #'user/x

    user=> (seq x)
    (32 11)

    user=> (def y (sort x))
    #'user/y

    ;; Return sorted sequence
    user=> y
    (11 32)

    user=> (class y)
    clojure.lang.ArraySeq

    ;; but also modifies x, because it used the array to do the
    ;; sorting.
    user=> (seq x)
    (11 32)

    ;; One way to avoid this is copying the array before sorting:
    user=> (def y (sort (aclone x)))
    #'user/y
```

See also:
[`sort-by`][doc-sort-by]
[`compare`][doc-compare]
[Comparators in Clojure][ComparatorsInClojure]

[doc-sort-by]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sort-by.md
[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
