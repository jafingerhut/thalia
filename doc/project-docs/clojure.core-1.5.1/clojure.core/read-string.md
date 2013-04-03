WARNING: You *SHOULD NOT* use `clojure.core/read-string` to read data
from untrusted sources.  See the [docs for
`clojure.core/read`][doc-read], because the same security issues exist
for both `read` and `read-string`.

[doc-read]: https://github.com/jafingerhut/thalia/blob/master/doc/project-docs/clojure.core-1.5.1/clojure.core/read.md
