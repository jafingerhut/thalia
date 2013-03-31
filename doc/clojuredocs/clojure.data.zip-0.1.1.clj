{"namespaces"
 {"clojure.data.zip.xml"
  [{"ns" "clojure.data.zip.xml",
    "name" "attr=",
    "line" 22,
    "column" 1,
    "doc"
    "Returns a query predicate that matches a node when it has an\n  attribute named attrname whose value is attrval.",
    "tag" nil,
    "source"
    "(defn attr=\n  \"Returns a query predicate that matches a node when it has an\n  attribute named attrname whose value is attrval.\"\n  [attrname attrval] (fn [loc] (= attrval (attr loc attrname))))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["attrname" "attrval"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "text=",
    "line" 45,
    "column" 1,
    "doc"
    "Returns a query predicate that matches a node when its textual\n  content equals s.",
    "tag" nil,
    "source"
    "(defn text=\n  \"Returns a query predicate that matches a node when its textual\n  content equals s.\"\n  [s] (fn [loc] (= (text loc) s)))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "xml->",
    "line" 56,
    "column" 1,
    "doc"
    "The loc is passed to the first predicate.  If the predicate returns\n  a collection, each value of the collection is passed to the next\n  predicate.  If it returns a location, the location is passed to the\n  next predicate.  If it returns true, the input location is passed to\n  the next predicate.  If it returns false or nil, the next predicate\n  is not called.\n\n  This process is repeated, passing the processed results of each\n  predicate to the next predicate.  xml-> returns the final sequence.\n  The entire chain is evaluated lazily.\n\n  There are also special predicates: keywords are converted to tag=,\n  strings to text=, and vectors to sub-queries that return true if\n  they match.\n\n  See the footer of zip-query.clj for examples.",
    "tag" nil,
    "source"
    "(defn xml->\n  \"The loc is passed to the first predicate.  If the predicate returns\n  a collection, each value of the collection is passed to the next\n  predicate.  If it returns a location, the location is passed to the\n  next predicate.  If it returns true, the input location is passed to\n  the next predicate.  If it returns false or nil, the next predicate\n  is not called.\n\n  This process is repeated, passing the processed results of each\n  predicate to the next predicate.  xml-> returns the final sequence.\n  The entire chain is evaluated lazily.\n\n  There are also special predicates: keywords are converted to tag=,\n  strings to text=, and vectors to sub-queries that return true if\n  they match.\n\n  See the footer of zip-query.clj for examples.\"\n  [loc & preds]\n  (zf/mapcat-chain loc preds\n                   #(cond (keyword? %) (tag= %)\n                          (string?  %) (text= %)\n                          (vector?  %) (seq-test %))))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["loc" "&" "preds"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "tag=",
    "line" 27,
    "column" 1,
    "doc"
    "Returns a query predicate that matches a node when its is a tag\n  named tagname.",
    "tag" nil,
    "source"
    "(defn tag=\n  \"Returns a query predicate that matches a node when its is a tag\n  named tagname.\"\n  [tagname]\n  (fn [loc]\n    (filter #(and (zip/branch? %) (= tagname (:tag (zip/node %))))\n            (if (zf/auto? loc)\n              (zf/children-auto loc)\n              (list (zf/auto true loc))))))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["tagname"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "text",
    "line" 37,
    "column" 1,
    "doc"
    "Returns the textual contents of the given location, similar to\n  xpaths's value-of",
    "tag" nil,
    "source"
    "(defn text\n  \"Returns the textual contents of the given location, similar to\n  xpaths's value-of\"\n  [loc]\n  (.replaceAll\n   ^String (apply str (xml-> loc zf/descendants zip/node string?))\n   (str \"[\\\\s\" (char 160) \"]+\") \" \"))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["loc"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "seq-test",
    "line" 50,
    "column" 1,
    "doc"
    "Returns a query predicate that matches a node when its xml content\n  matches the query expresions given.",
    "tag" nil,
    "source"
    "(defn seq-test\n  \"Returns a query predicate that matches a node when its xml content\n  matches the query expresions given.\"\n  ^{:private true}\n  [preds] (fn [loc] (and (seq (apply xml-> loc preds)) (list loc))))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["preds"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "xml1->",
    "line" 79,
    "column" 1,
    "doc"
    "Returns the first item from loc based on the query predicates\n  given.  See xml->",
    "tag" nil,
    "source"
    "(defn xml1->\n  \"Returns the first item from loc based on the query predicates\n  given.  See xml->\"\n  [loc & preds] (first (apply xml-> loc preds)))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["loc" "&" "preds"]]}
   {"ns" "clojure.data.zip.xml",
    "name" "attr",
    "line" 17,
    "column" 1,
    "doc"
    "Returns the xml attribute named attrname, of the xml node at location loc.",
    "tag" nil,
    "source"
    "(defn attr\n  \"Returns the xml attribute named attrname, of the xml node at location loc.\"\n  ([attrname]     (fn [loc] (attr loc attrname)))\n  ([loc attrname] (when (zip/branch? loc) (-> loc zip/node :attrs attrname))))",
    "file" "clojure/data/zip/xml.clj",
    "arglists" [["attrname"] ["loc" "attrname"]]}],
  "clojure.data.zip"
  [{"ns" "clojure.data.zip",
    "name" "leftmost?",
    "line" 34,
    "column" 1,
    "doc"
    "Returns true if there are no more nodes to the left of location loc.",
    "tag" nil,
    "source"
    "(defn leftmost?\n  \"Returns true if there are no more nodes to the left of location loc.\"\n  [loc] (nil? (zip/left loc)))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"arglists" [["x"]],
    "ns" "clojure.data.zip",
    "name" "auto?",
    "column" 1,
    "line" 23,
    "source"
    "(defn auto?\n  [x] (not (:zip-filter/no-auto? (meta x))))",
    "file" "clojure/data/zip.clj",
    "tag" nil}
   {"ns" "clojure.data.zip",
    "name" "children",
    "line" 42,
    "column" 1,
    "doc"
    "Returns a lazy sequence of all immediate children of location loc,\n  left-to-right.",
    "tag" nil,
    "source"
    "(defn children\n  \"Returns a lazy sequence of all immediate children of location loc,\n  left-to-right.\"\n  [loc]\n  (when (zip/branch? loc)\n    (map #(auto false %) (right-locs (zip/down loc)))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"ns" "clojure.data.zip",
    "name" "descendants",
    "line" 57,
    "column" 1,
    "doc"
    "Returns a lazy sequence of all descendants of location loc, in\n  depth-first order, left-to-right, starting with loc.",
    "tag" nil,
    "source"
    "(defn descendants\n  \"Returns a lazy sequence of all descendants of location loc, in\n  depth-first order, left-to-right, starting with loc.\"\n  [loc] (lazy-seq (cons (auto false loc) (mapcat descendants (children loc)))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"arglists" [["loc" "preds" "mkpred"]],
    "ns" "clojure.data.zip",
    "name" "mapcat-chain",
    "column" 1,
    "line" 81,
    "source"
    "(defn mapcat-chain\n  ^{:private true}\n  [loc preds mkpred]\n  (reduce (fn [prevseq expr]\n            (mapcat #(fixup-apply (or (mkpred expr) expr) %) prevseq))\n          (list (with-meta loc (assoc (meta loc) :zip-filter/is-node? true)))\n          preds))",
    "file" "clojure/data/zip.clj",
    "tag" nil}
   {"arglists" [["v" "x"]],
    "ns" "clojure.data.zip",
    "name" "auto",
    "column" 1,
    "line" 20,
    "source"
    "(defn auto\n  [v x] (with-meta x ((if v dissoc assoc) (meta x) :zip-filter/no-auto? true)))",
    "file" "clojure/data/zip.clj",
    "tag" nil}
   {"ns" "clojure.data.zip",
    "name" "right-locs",
    "line" 26,
    "column" 1,
    "doc"
    "Returns a lazy sequence of locations to the right of loc, starting with loc.",
    "tag" nil,
    "source"
    "(defn right-locs\n  \"Returns a lazy sequence of locations to the right of loc, starting with loc.\"\n  [loc] (lazy-seq (when loc (cons (auto false loc) (right-locs (zip/right loc))))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"ns" "clojure.data.zip",
    "name" "children-auto",
    "line" 49,
    "column" 1,
    "doc"
    "Returns a lazy sequence of all immediate children of location loc,\n  left-to-right, marked so that a following tag= predicate will auto-descend.",
    "tag" nil,
    "source"
    "(defn children-auto\n  \"Returns a lazy sequence of all immediate children of location loc,\n  left-to-right, marked so that a following tag= predicate will auto-descend.\"\n  ^{:private true}\n  [loc]\n  (when (zip/branch? loc)\n    (map #(auto true %) (right-locs (zip/down loc)))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"ns" "clojure.data.zip",
    "name" "ancestors",
    "line" 62,
    "column" 1,
    "doc"
    "Returns a lazy sequence of all ancestors of location loc, starting\n  with loc and proceeding to loc's parent node and on through to the\n  root of the tree.",
    "tag" nil,
    "source"
    "(defn ancestors\n  \"Returns a lazy sequence of all ancestors of location loc, starting\n  with loc and proceeding to loc's parent node and on through to the\n  root of the tree.\"\n  [loc] (lazy-seq (when loc (cons (auto false loc) (ancestors (zip/up loc))))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"ns" "clojure.data.zip",
    "name" "rightmost?",
    "line" 38,
    "column" 1,
    "doc"
    "Returns true if there are no more nodes to the right of location loc.",
    "tag" nil,
    "source"
    "(defn rightmost?\n  \"Returns true if there are no more nodes to the right of location loc.\"\n  [loc] (nil? (zip/right loc)))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}
   {"private" true,
    "ns" "clojure.data.zip",
    "name" "fixup-apply",
    "line" 68,
    "column" 1,
    "doc"
    "Calls (pred loc), and then converts the result to the 'appropriate'\n  sequence.",
    "tag" nil,
    "source"
    "(defn- fixup-apply\n  \"Calls (pred loc), and then converts the result to the 'appropriate'\n  sequence.\"\n  ^{:private true}\n  [pred loc]\n  (let [rtn (pred loc)]\n    (cond (and (map? (meta rtn)) (:zip-filter/is-node? (meta rtn))) (list rtn)\n          (= rtn true)                (list loc)\n          (= rtn false)               nil\n          (nil? rtn)                  nil\n          (sequential? rtn)           rtn\n          :else                       (list rtn))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["pred" "loc"]]}
   {"ns" "clojure.data.zip",
    "name" "left-locs",
    "line" 30,
    "column" 1,
    "doc"
    "Returns a lazy sequence of locations to the left of loc, starting with loc.",
    "tag" nil,
    "source"
    "(defn left-locs\n  \"Returns a lazy sequence of locations to the left of loc, starting with loc.\"\n  [loc] (lazy-seq (when loc (cons (auto false loc) (left-locs (zip/left loc))))))",
    "file" "clojure/data/zip.clj",
    "arglists" [["loc"]]}]},
 "description" "data.zip 0.1.1",
 "version" "0.1.1",
 "name" "clojure.data.zip",
 "group" "clojure.data.zip"}
