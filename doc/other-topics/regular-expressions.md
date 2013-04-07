# Regular expressions in Clojure

TBD: Any way with github Markdown to include an auto-generated table
of contents, i.e. of headers?

See also: `re-pattern`, `re-matcher`, `re-matches`, `re-seq`,
`re-groups`, `clojure.string/replace`, `clojure.string/replace-first`

What to find/ | Find      | Find     | Replace | Replace
or replace    | first     | all      | first   | all
--------------|-----------|----------|---------|----------
character     |           |          | `(str/replace-first s old-ch new-ch)` | `(str/replace s old-ch new-ch)`
string        |           |          | `(str/replace-first s old-s new-s)`   | `(str/replace s old-s new-s)`
regex         | `(re-find regex s)` | `(re-seq regex s)` | `(str/replace-first s regex new-s)`   | `(str/replace s regex new-s)`

TBD: Some discussion on the different between specifying regex with
#"" syntax vs. as a string using re-pattern.

TBD: How to take a string and uses it as part of a regex, matching its
exact contents literally, with any special regex characters _not_
treated specially.

TBD: Storing and reading back regexes.
