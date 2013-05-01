`sorted-map-by` returns a new sorted map with the given key/value
pairs.  Its keys are maintained in sorted order using the
[comparator][ComparatorsInClojure] function given to it.  See
[`sorted-map`][doc-sorted-map] for the differences between sorted and
unsorted maps.

[ComparatorsInClojure]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md
[doc-sorted-map]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/sorted-map.md

```clojure
user=> (sorted-map-by > 2 "two" 3 "three" 11 "eleven" 5 "five" 7 "seven")
{11 "eleven", 7 "seven", 5 "five", 3 "three", 2 "two"}
user> (def reverse-alpha (sorted-map-by #(compare %2 %1)
                                        "aardvark" "Orycteropus afer"
                                        "lion" "Panthera leo"
                                        "emperor penguin" "Aptenodytes forsteri"
                                        "platypus" "Ornithorhynchus anatinus"))
#'user/reverse-alpha
user=> (pprint reverse-alpha)
{"platypus" "Ornithorhynchus anatinus",
 "lion" "Panthera leo",
 "emperor penguin" "Aptenodytes forsteri",
 "aardvark" "Orycteropus afer"}
nil
```

Be cautious when writing your own comparators, especially for sorted
maps.  In particular, read the warnings and suggestions in [this
section][ComparatorsInClojureSortedSets] of the Comparators in Clojure
document.

[ComparatorsInClojureSortedSets]: https://github.com/jafingerhut/thalia/blob/master/doc/other-topics/comparators.md#comparators-for-sorted-sets-and-maps-are-easy-to-get-wrong
