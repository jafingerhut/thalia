`sorted-map-by` returns a new sorted map with the given key/value
pairs.  Its keys are maintained in sorted order using the
[comparator][ComparatorsInClojure] function given to it.  See
[`sorted-map`][doc-sorted-map] for the differences between sorted and
unsorted maps.

[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
[doc-sorted-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map.md

Be cautious when writing your own comparators, especially for sorted
maps.  In particular, read the warnings and suggestions in [this
section][ComparatorsInClojureSortedSets] of the Comparators in Clojure
document.

[ComparatorsInClojureSortedSets]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md#comparators-for-sorted-sets-and-maps-are-easy-to-get-wrong
