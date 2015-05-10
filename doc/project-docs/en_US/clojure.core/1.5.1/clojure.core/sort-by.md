See extended docs for `sort`, all of which applies to `sort-by`.

`sort-by` sorts the items in the input collection `coll`.  The sort
order is determined by calling `keyfn` on each item and comparing the
return values.  `compare` is used if no comparator is given.

Examples:

```clojure
user=> (sort-by count ["lummox" "antidisestablishmentarianism" "a"])
("a" "lummox" "antidisestablishmentarianism")
user=> (sort-by first > [[8.67 -5] [5 1] [-22/7 3.0] [5 0]])
([8.67 -5] [5 1] [5 0] [-22/7 3.0])
```

The example in the `sort` extended docs demonstrating a Java array
being modified applies to `sort-by`, too, including using `aclone` to
copy the array before sorting to avoid that issue.

Performance note: `keyfn` is called on each value every time it is
compared to another value.  If `keyfn` is expensive to compute, you
may be able to sort more quickly by using the
'decorate-sort-undecorate' technique, also sometimes called the
'Schwartzian transform' from its implementation in
Perl. http://en.wikipedia.org/wiki/Schwartzian_transform

An example of the 'decorate-sort-undecorate' technique is given below.
It first 'decorates' each value, i.e. it creates a 2-element vector
containing the result of `keyfn` on the value, followed by the value.
Then it sorts by these decorated values using `first`.  Finally it
'undecorates' each element by using `second` to extract out the
original value, removing the sort keys.

It is true that `first` is called every time two values are compared,
but this is quick, and the assumption here is that `keyfn` is
expensive to compute.

```clojure
user=> (defn myfn [x] (* x x))    ; imagine this is expensive to compute
#'user/myfn
user=> (map second                               ; undecorate
            (sort-by first                       ; sort by the decorated value
                     (map (fn [x] [(myfn x) x])  ; decorate with (myfn x)
                          [-5 4 -1 3 -2])))
(-1 -2 3 4 -5)

;; Equivalent to the above, but written with `->>` so steps are
;; written in the order they are performed.

user=> (->> [-5 4 -1 3 -2]
            (map (fn [x] [(myfn x) x]))  ; decorate with (myfn x)
            (sort-by first)              ; sort by the decorated value
            (map second))                ; undecorate
(-1 -2 3 4 -5)
```

See also: `sort`, `compare`, `(topic Comparators)`
