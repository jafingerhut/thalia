`sort-by` sorts the items in the input collection `coll`.  The sort
order is determined by calling `keyfn` on each item and comparing the
return values.  `compare` is used if no comparator is given.

See [`sort`][doc-sort] for an example showing it modifying a Java
array in place.  `sort-by` will also do this.  `sort-by` is guaranteed
to be _stable_, as `sort` is.

Performance note: `keyfn` is called on each value every time it is
compared to another value (example below).  If `keyfn` is expensive to
compute, you may be able to sort more quickly by using the
"decorate-sort-undecorate" technique, also sometimes called the
[Schwartzian transform][Schwartzian_transform] from its implementation
in Perl.

[Schwartzian_transform]: http://en.wikipedia.org/wiki/Schwartzian_transform

Demonstration that `sort-by` calls `keyfn` multiple times per value:

```clojure
user> (defn square [x]
        (println "square: x=" x)
        (* x x))
#'user/square

user> (sort-by square [-5 4 -1 3 -2])
square: x= 4
square: x= -5
square: x= -1
square: x= 4
square: x= 3
square: x= -1
square: x= 3
square: x= 4
square: x= 3
square: x= -1
square: x= -2
square: x= 4
square: x= -2
square: x= 3
square: x= -2
square: x= -1
(-1 -2 3 4 -5)
```

Below is one way to compute `keyfn` exactly once per value.  It first
"decorates" each value, i.e. it creates a 2-element vector containing
the result of `keyfn` on the value, followed by the value.  Then it
sorts by these decorated values using `first`.  Finally it
"undecorates" each element by using `second` to extract out the
original value, removing the sort keys.

It is true that `first` is called every time two values are compared,
but this is quick, and the assumption here is that `keyfn` is
expensive to compute.

```clojure
user> (map second                                 ; undecorate
           (sort-by first                         ; sort by the decorated value
                    (map (fn [x] [(square x) x])  ; decorate with (square x)
                         [-5 4 -1 3 -2])))
square: x= -5
square: x= 4
square: x= -1
square: x= 3
square: x= -2
(-1 -2 3 4 -5)
```

Below is another way to write the same code, except this time using
the threading operator `->>` so that the computation steps can be
written in the order they occur.

```clojure
user> (->> [-5 4 -1 3 -2]
           (map (fn [x] [(square x) x]))  ; decorate with (square x)
           (sort-by first)                ; sort by the decorated value
           (map second))                  ; undecorate
square: x= -5
square: x= 4
square: x= -1
square: x= 3
square: x= -2
(-1 -2 3 4 -5)
```

See also:
[`sort`][doc-sort]
[`compare`][doc-compare]
[Comparators in Clojure][ComparatorsInClojure]

[doc-sort]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sort.md
[doc-compare]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/compare.md
[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
