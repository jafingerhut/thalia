`sorted-set-by` returns a new sorted set with the given elements.  Its
elements are maintained in sorted order using the
[comparator][ComparatorsInClojure] function given to it.  See
[`sorted-set`][doc-sorted-set] for the differences between sorted and
unsorted sets.

[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
[doc-sorted-set]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-set.md

Be cautious when writing your own comparators, especially for sorted
sets.  In particular, read the warnings and suggestions in [this
section][ComparatorsInClojureSortedSets] of the Comparators in Clojure
document.

[ComparatorsInClojureSortedSets]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md#comparators-for-sorted-sets-and-maps-are-easy-to-get-wrong

Examples:

```clojure
user> (def s1 (sorted-set "Food" "good" "air" "My" "AiR" "My"))
#'user/s1
user> s1
#{"AiR" "Food" "My" "air" "good"}
```

With `case-insensitive-cmp`, `"AiR"` is a duplicate with `"air"` and
not added to the set, and the order is different.

```clojure
user> (require '[clojure.string :as str])
nil
user> (defn case-insensitive-cmp [s1 s2]
        (compare (str/lower-case s1) (str/lower-case s2)))
#'user/case-insensitive-cmp
user> (def s2 (sorted-set-by case-insensitive-cmp "Food" "good" "air" "My" "AiR" "My"))
#'user/s2
user> s2
#{"air" "Food" "good" "My"}
```
