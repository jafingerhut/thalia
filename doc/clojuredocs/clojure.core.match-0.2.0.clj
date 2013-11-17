{"namespaces"
 {"clojure.core.match"
  [{"arglists" [["pat" "vars" "nvars" "rownum"]],
    "ns" "clojure.core.match",
    "name" "check-pattern",
    "column" 1,
    "line" 1830,
    "source"
    "(defn check-pattern [pat vars nvars rownum]\n  (let [pat (group-keywords pat)]\n    (when (not (vector? pat))\n      (throw\n        (AssertionError. \n          (str \"Pattern row \" rownum\n            \": Pattern rows must be wrapped in [].\"\n            \" Try changing \" pat \" to [\" pat \"].\" \n            (when (list? pat)\n              (str \" Note: pattern rows are not patterns.\"\n                \" They cannot be wrapped in a :when guard, for example\"))))))\n    (when (not= (count pat) nvars)\n      (throw\n        (AssertionError.\n          (str \"Pattern row \" rownum\n            \": Pattern row has differing number of patterns. \"\n            pat \" has \" (count pat) \" pattern/s, expecting \"\n            nvars \" for occurrences \" vars))))\n    (when-let [duplicates (seq (find-duplicate-wildcards pat))]\n      (throw\n        (AssertionError.\n          (str \"Pattern row \" rownum\n            \": Pattern row reuses wildcards in \" pat\n            \".  The following wildcards are ambiguous: \"\n            (apply str (interpose \", \" duplicates))\n            \".  There's no guarantee that the matched values will be same.\"\n            \"  Rename the occurrences uniquely.\"))))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "to-pattern-row",
    "line" 1774,
    "column" 1,
    "doc"
    "Take an unprocessed pattern expression and an action expression and return\n   a pattern row of the processed pattern expression plus the action epxression.",
    "tag" nil,
    "source"
    "(defn to-pattern-row\n  \"Take an unprocessed pattern expression and an action expression and return\n   a pattern row of the processed pattern expression plus the action epxression.\"\n  [pat action]\n  (let [ps (map emit-pattern (group-keywords pat))]\n    (pattern-row ps action)))",
    "file" "clojure/core/match.clj",
    "arglists" [["pat" "action"]]}
   {"arglists" [["&" "body"]],
    "ns" "clojure.core.match",
    "name" "catch-error",
    "column" 1,
    "line" 419,
    "source"
    "(defn catch-error [& body]\n  (let [err-sym (if *clojurescript* 'js/Error 'Exception)]\n    `(catch ~err-sym e#\n       (if (identical? e# ~(backtrack-sym))\n         (do\n           ~@body)\n         (throw e#)))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "*line*",
    "line" 52,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *line*)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["ocrs" "focr"]],
    "ns" "clojure.core.match",
    "name" "seq-pattern-matrix-ocrs",
    "column" 1,
    "line" 962,
    "source"
    "(defn seq-pattern-matrix-ocrs [ocrs focr]\n  (let [seq-sym (or (-> focr meta :seq-sym) focr)\n        sym-meta {:occurrence-type :seq\n                  :seq-sym focr}\n        hsym (gensym (str (name seq-sym) \"_head__\"))\n        hsym (with-meta hsym\n               (assoc sym-meta :bind-expr `(first ~focr)))\n        tsym (gensym (str (name seq-sym) \"_tail__\"))\n        tsym (with-meta tsym\n               (assoc sym-meta :bind-expr `(rest ~focr)))]\n    (into [hsym tsym] (drop-nth ocrs 0))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["f" "ocrs"]],
    "ns" "clojure.core.match",
    "name" "row-bindings",
    "column" 1,
    "line" 512,
    "source"
    "(defn row-bindings [f ocrs]\n  (concat (:bindings f)\n    (->> (map vector (:ps f) ocrs)\n      (filter (fn [[p ocr]] (named-wildcard-pattern? p)))\n      (map (fn [[p ocr]] [(:sym p) (leaf-bind-expr ocr)])))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "choose-column",
    "column" 1,
    "line" 754,
    "source"
    "(defn choose-column [matrix]\n  (necessary-column matrix))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["rows" "pat" "ps"]],
    "ns" "clojure.core.match",
    "name" "specialize-or-pattern-matrix",
    "column" 1,
    "line" 1387,
    "source"
    "(defn specialize-or-pattern-matrix [rows pat ps]\n  (vec (apply concat\n         (map #(specialize-or-pattern-row % pat ps) rows))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "map->MapKeyPattern",
    "line" 1054,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.MapKeyPattern, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord MapKeyPattern [p]\n  IExistentialPattern\n\n  IPatternCompile\n  (to-source* [this ocr]\n    `(not= ~ocr ::not-found))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows  (rows matrix)\n          ocrs  (occurrences matrix)\n          nrows (specialize-map-key-pattern-matrix rows)]\n      (pattern-matrix nrows ocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.core.match",
    "name" "process-vars",
    "line" 1890,
    "column" 1,
    "doc"
    "Process the vars for the pattern matrix. If user provides an\n   expression, create a var and annotate via metadata with the\n   original expression.",
    "tag" nil,
    "source"
    "(defn process-vars\n  \"Process the vars for the pattern matrix. If user provides an\n   expression, create a var and annotate via metadata with the\n   original expression.\"\n  [vars]\n  (letfn [(process-var [var]\n            (if-not (symbol? var)\n              (let [nsym (gensym \"ocr-\")]\n                (with-meta nsym {:ocr-expr var}))\n              var))]\n    (vec (map process-var vars))))",
    "file" "clojure/core/match.clj",
    "arglists" [["vars"]]}
   {"arglists" [["&" "args"]],
    "ns" "clojure.core.match",
    "name" "val-at-expr",
    "column" 1,
    "line" 107,
    "source"
    "(defn val-at-expr [& args]\n  (if *clojurescript*\n    `(get ~@args)\n    ;;If not ClojureScript, defer to val-at*\n    `(if (instance? clojure.lang.ILookup ~(first args))\n       (get ~@args)\n       (val-at* ~@args))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["s"]],
    "ns" "clojure.core.match",
    "name" "seq-pattern",
    "column" 1,
    "line" 1012,
    "source"
    "(defn ^SeqPattern seq-pattern [s]\n  {:pre [(sequential? s)\n         (not (empty? s))]}\n  (SeqPattern. s nil))",
    "file" "clojure/core/match.clj",
    "tag" "clojure.core.match.SeqPattern"}
   {"ns" "clojure.core.match",
    "name" "as-pattern",
    "line" 1674,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare emit-pattern-for-syntax or-pattern as-pattern guard-pattern\n         predicate-pattern vector-pattern)",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "*backtrack-stack*",
    "line" 83,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *backtrack-stack* ())",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "map->RestPattern",
    "line" 1030,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.RestPattern, taking a map of keywords to field values.",
    "tag" nil,
    "source" "(defrecord RestPattern [p])",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.core.match",
    "name" "or-pattern",
    "line" 1674,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare emit-pattern-for-syntax or-pattern as-pattern guard-pattern\n         predicate-pattern vector-pattern)",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "first-column-chosen-case",
    "line" 724,
    "column" 1,
    "doc"
    "Case 3a: The first column is chosen. Compute and return a\n  switch/bind node with a default matrix case",
    "tag" nil,
    "source"
    "(defn first-column-chosen-case \n  \"Case 3a: The first column is chosen. Compute and return a\n  switch/bind node with a default matrix case\"\n  [matrix col ocrs]\n  (let [expanded        (expand-matrix matrix col)\n        [S D :as split] (split-matrix expanded)]\n    (if-not *recur-present*\n      (switch-node (ocrs col)\n        (cases S)\n        (default-case D))\n      (let [new-stack (last split)]\n        (switch-node (ocrs col)\n          (if-not (identical? *backtrack-stack* new-stack)\n            (binding [*backtrack-stack* new-stack]\n              (cases S))\n            (cases S))\n          (if (and (seq *backtrack-stack*)\n                   (identical? (peek *backtrack-stack*) D))\n            (binding [*backtrack-stack* (pop *backtrack-stack*)]\n              (default-case D))\n            (default-case D)))))))",
    "file" "clojure/core/match.clj",
    "arglists" [["matrix" "col" "ocrs"]]}
   {"ns" "clojure.core.match",
    "name" "->OrPattern",
    "line" 1391,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.OrPattern.",
    "tag" nil,
    "source"
    "(deftype OrPattern [ps _meta]\n  Object\n  (toString [this]\n    (str ps))\n  (equals [_ other]\n    (and (instance? OrPattern other) (= ps (:ps other))))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (OrPattern. ps new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :ps ps\n      not-found))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows  (rows matrix)\n          ocrs  (occurrences matrix)\n          nrows (specialize-or-pattern-matrix rows this ps)]\n      (pattern-matrix nrows ocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["ps" "_meta"]]}
   {"arglists" [["row" [["keys" ["focr" "min-size"]]]]],
    "ns" "clojure.core.match",
    "name" "specialize-vector-pattern-row-non-rest",
    "column" 1,
    "line" 1236,
    "source"
    "(defn specialize-vector-pattern-row-non-rest\n  [row {:keys [focr min-size]}]\n  (let [p  (first row)\n        ps (if (vector-pattern? p)\n             (reverse (:v p))\n             (repeatedly min-size wildcard-pattern))]\n    (reduce prepend (drop-nth-bind row 0 focr) ps)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["pm"]],
    "ns" "clojure.core.match",
    "name" "select",
    "column" 1,
    "line" 566,
    "source" "(defn select [pm]\n  (swap pm (necessary-column pm)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["i" "col"]],
    "ns" "clojure.core.match",
    "name" "score-column",
    "column" 1,
    "line" 478,
    "source" "(defn score-column [i col]\n  [i (reduce + 0 col)])",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "defpred",
    "macro" true,
    "line" 1524,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro defpred\n  ([name]\n     (swap! preds assoc name name))\n  ([name f]\n     (swap! preds assoc name f)))",
    "file" "clojure/core/match.clj",
    "arglists" [["name"] ["name" "f"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "array-tag",
    "column" 1,
    "line" 1279,
    "source"
    "(defn array-tag [x]\n  (get '{bytes   ::array\n         shorts  ::shorts\n         ints    ::ints\n         longs   ::longs\n         doubles ::doubles\n         objects ::objects}\n    (-> x meta :tag)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->GuardPattern",
    "line" 1460,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.GuardPattern.",
    "tag" nil,
    "source"
    "(deftype GuardPattern [p gs _meta]\n  Object\n  (toString [this]\n    (str p \" :guard \" gs))\n  (equals [_ other]\n    (and (instance? GuardPattern other)\n         (= p (:p other))\n         (= gs (:gs other))))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (GuardPattern. p gs new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :p p\n      :gs gs\n      not-found))\n\n  IPatternCompile\n  (to-source* [this ocr]\n    `(and ~@(map (fn [expr ocr]\n                   (list expr ocr))\n                 gs (repeat ocr))))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows  (rows matrix)\n          ocrs  (occurrences matrix)\n          nrows (specialize-guard-pattern-matrix rows)]\n      (pattern-matrix nrows ocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["p" "gs" "_meta"]]}
   {"ns" "clojure.core.match",
    "name" "->MapKeyPattern",
    "line" 1054,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.MapKeyPattern.",
    "tag" nil,
    "source"
    "(defrecord MapKeyPattern [p]\n  IExistentialPattern\n\n  IPatternCompile\n  (to-source* [this ocr]\n    `(not= ~ocr ::not-found))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows  (rows matrix)\n          ocrs  (occurrences matrix)\n          nrows (specialize-map-key-pattern-matrix rows)]\n      (pattern-matrix nrows ocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["p"]]}
   {"arglists" [["vars" "clauses"]],
    "ns" "clojure.core.match",
    "name" "clj-form",
    "column" 1,
    "line" 1946,
    "source"
    "(defn clj-form [vars clauses]\n  (when @*syntax-check* (check-matrix-args vars clauses))\n  (let [actions (map second (partition 2 clauses))\n        recur-present? (recur-present? actions)]\n    ;; TODO: this is naive, recur-present? need ignore\n    ;; recur internal to an action - David\n    (assert (not (and *no-backtrack* recur-present?))\n      \"Recur form present yet *no-backtrack* set to true\")\n    (binding [*recur-present* (or *recur-present*\n                                  recur-present?\n                                  *no-backtrack*)]\n      (-> (emit-matrix vars clauses)\n        compile\n        executable-form))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[[["rows" "rows"]]]],
    "ns" "clojure.core.match",
    "name" "height",
    "column" 1,
    "line" 486,
    "source" "(defn height [{rows :rows}]\n  (count rows))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["ocr"]],
    "ns" "clojure.core.match",
    "name" "expression?",
    "column" 1,
    "line" 661,
    "source"
    "(defn expression? [ocr]\n  (contains? (meta ocr) :ocr-expr))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti tag (fn [t] t))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "tag",
    "column" 1,
    "line" 127,
    "file" "clojure/core/match.clj"}
   {"arglists" [["vars" "clauses"]],
    "ns" "clojure.core.match",
    "name" "check-matrix-args",
    "column" 1,
    "line" 1861,
    "source"
    "(defn check-matrix-args [vars clauses]\n  (when (symbol? vars)\n    (throw\n      (AssertionError.\n        (str \"Occurrences must be in a vector.\"\n          \" Try changing \" vars \" to [\" vars \"]\"))))\n  (when (not (vector? vars))\n    (throw\n      (AssertionError.\n        (str \"Occurrences must be in a vector. \"\n          vars \" is not a vector\"))))\n  (let [nvars (count vars)\n        cls   (partition 2 clauses)]\n    (doseq [[[pat _] rownum] (map vector (butlast cls) (rest (range)))]\n      (when (= :else pat)\n        (throw\n          (AssertionError.\n            (str \"Pattern row \" rownum\n              \": :else form only allowed on final pattern row\"))))\n      (check-pattern pat vars nvars rownum))\n    (when-let [[pat _] (last cls)]\n      (when-not (= :else pat)\n        (check-pattern pat vars nvars (count cls)))))\n  (when (odd? (count clauses)) \n    (throw\n      (AssertionError.\n        (str \"Uneven number of Pattern Rows. The last form `\"\n          (last clauses) \"` seems out of place.\")))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "empty-rows-case",
    "line" 687,
    "column" 1,
    "doc"
    "Case 1: If there are no pattern rows to match, then matching always fails",
    "tag" nil,
    "source"
    "(defn empty-rows-case \n  \"Case 1: If there are no pattern rows to match, then matching always fails\"\n  []\n  (fail-node))",
    "file" "clojure/core/match.clj",
    "arglists" [[]]}
   {"arglists" [["ocr" "k"]],
    "ns" "clojure.core.match",
    "name" "gen-map-pattern-ocr",
    "column" 1,
    "line" 1134,
    "source"
    "(defn gen-map-pattern-ocr [ocr k]\n  (gensym (str (name ocr) \"_\" (name k) \"__\")))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["ocrs" "env"]],
    "ns" "clojure.core.match",
    "name" "map-pattern-matrix-ocrs",
    "column" 1,
    "line" 1146,
    "source"
    "(defn map-pattern-matrix-ocrs [ocrs env]\n  (let [focr  (:focr env)\n        mocrs (map #(map-pattern-matrix-ocr-sym % env)\n                (:all-keys env))\n        mocrs (vec\n                (if @(:only? env)\n                  (cons focr mocrs)\n                  mocrs))]\n    (into mocrs (drop-nth ocrs 0))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "emit-pattern-for-syntax",
    "line" 1685,
    "column" 1,
    "doc"
    "Handles patterns wrapped in the special list syntax. Dispatches\n  on the first or second keyword in the list. For example, the pattern \n  `(:or 1 ...) is dispatches as :or, and `(1 :as a)` is dispatched by :as.",
    "tag" nil,
    "source"
    "(defmulti emit-pattern-for-syntax \n  \"Handles patterns wrapped in the special list syntax. Dispatches\n  on the first or second keyword in the list. For example, the pattern \n  `(:or 1 ...) is dispatches as :or, and `(1 :as a)` is dispatched by :as.\"\n  (fn [[f s]]\n    (if (keyword? f)\n      [f :default]\n      [:default s])))",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "*recur-present*",
    "line" 62,
    "column" 1,
    "doc"
    "In the presence of recur we cannot apply code size optimizations",
    "tag" nil,
    "source"
    "(def ^{:dynamic true\n       :doc \"In the presence of recur we cannot apply code size optimizations\"}\n  *recur-present* false)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" nil,
    "ns" "clojure.core.match",
    "name" "preds",
    "column" 1,
    "line" 1522,
    "source" "(def preds (atom {}))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["row" [["keys" ["focr" "min-size"]]]]],
    "ns" "clojure.core.match",
    "name" "specialize-vector-pattern-row",
    "column" 1,
    "line" 1228,
    "source"
    "(defn specialize-vector-pattern-row\n  [row {:keys [focr min-size]}]\n  (let [p  (first row)\n        ps (cond\n             (vector-pattern? p) (split p min-size)\n             :else [(wildcard-pattern) (wildcard-pattern)])]\n    (reduce prepend (drop-nth-bind row 0 focr) (reverse ps))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["l"]],
    "ns" "clojure.core.match",
    "name" "literal-pattern",
    "column" 1,
    "line" 914,
    "source"
    "(defn literal-pattern [l] \n  (LiteralPattern. l (meta l)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["row"]],
    "ns" "clojure.core.match",
    "name" "empty-row?",
    "column" 1,
    "line" 473,
    "source"
    "(defn empty-row? [row]\n  (let [ps (:ps row)]\n    (and (not (nil? ps))\n         (empty? ps))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "wildcards-and-duplicates",
    "line" 1781,
    "column" 1,
    "doc"
    "Returns a vector of two elements: the set of all wildcards and the \n   set of duplicate wildcards.  The underbar _ is excluded from both.",
    "tag" nil,
    "source"
    "(defn wildcards-and-duplicates\n  \"Returns a vector of two elements: the set of all wildcards and the \n   set of duplicate wildcards.  The underbar _ is excluded from both.\"\n  [patterns]\n  (loop [remaining patterns seen #{} dups #{}]\n    (if-let [patterns (seq remaining)]\n      (let [pat (first patterns)\n            pats (rest patterns)]\n        (cond\n          (or (= pat '_) (= pat '&))\n          (recur pats seen dups)\n\n          (symbol? pat)\n          (if (contains? seen pat)\n            (recur pats seen (conj dups pat))\n            (recur pats (conj seen pat) dups))\n          \n          (vector? pat)\n          (recur (concat pats pat) seen dups)\n\n          (map? pat)\n          (recur (concat pats (vals pat)) seen dups)\n\n          (seq? pat)\n          (cond\n            (= (first pat) 'quote)\n            (recur pats seen dups)\n\n            (= (first pat) :or)\n            (let [wds (map wildcards-and-duplicates\n                        (map list (take-nth 2 pat)))\n                   mseen (apply set/union (map first wds))]\n              (recur pats (set/union seen mseen)\n                (apply set/union dups\n                  (set/intersection seen mseen)\n                  (map second wds))))\n            \n            (= (second pat) :as)\n            (recur (concat pats (take-nth 2 pat)) seen dups)\n\n            :else\n            (recur (conj pats (first pat)) seen dups))\n          :else\n          (recur pats seen dups)))\n      [seen dups])))",
    "file" "clojure/core/match.clj",
    "arglists" [["patterns"]]}
   {"ns" "clojure.core.match",
    "name" "->LiteralPattern",
    "line" 871,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.LiteralPattern.",
    "tag" nil,
    "source"
    "(deftype LiteralPattern [l _meta]\n  Object\n  (toString [_]\n    (if (nil? l)\n      \"nil\"\n      (pr-str l)))\n  (equals [_ other]\n    (and (instance? LiteralPattern other) (= l (:l other))))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n\n  (withMeta [_ new-meta]\n    (LiteralPattern. l new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :l l\n      not-found))\n\n  IPatternCompile\n  (to-source* [this ocr]\n    (cond\n     (= l ())\n     `(empty? ~ocr)\n\n     (and (symbol? l) (not (-> l meta :local)))\n     `(= ~ocr '~l)\n\n     (and *clojurescript*\n         (or (number? l) (string? l)\n             (true? l) (false? l)\n             (nil? l)))\n     `(identical? ~ocr ~l) \n      \n     (and *clojurescript* (keyword? l))\n     `(cljs.core/keyword-identical? ~ocr ~l)\n     \n      :else `(= ~ocr ~l))))",
    "file" "clojure/core/match.clj",
    "arglists" [["l" "_meta"]]}
   {"ns" "clojure.core.match",
    "name" "->MapPattern",
    "line" 1156,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.MapPattern.",
    "tag" nil,
    "source"
    "(deftype MapPattern [m _meta]\n  Object\n  (toString [_]\n    (str m \" :only \" (or (:only _meta) [])))\n  (equals [_ other]\n    (and (instance? MapPattern other) (= m (:m other))))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (MapPattern. m new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :m m\n      not-found))\n\n  IPatternCompile\n  (to-source* [this ocr]\n    (if *clojurescript*\n      `(satisfies? cljs.core/ILookup ~ocr)\n      `(or (instance? clojure.lang.ILookup ~ocr) (satisfies? IMatchLookup ~ocr))))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows     (rows matrix)\n          ocrs     (occurrences matrix)\n          focr     (first ocrs)\n          env      {:focr focr\n                    :only? (atom false)}\n          all-keys (get-all-keys rows env)\n          env'     (assoc env\n                     :all-keys all-keys\n                     :wc-map (zipmap all-keys (repeatedly wildcard-pattern))\n                     :ocrs-map (zipmap all-keys\n                                 (map #(gen-map-pattern-ocr focr %)\n                                   all-keys)))\n          nrows    (specialize-map-pattern-matrix rows env')\n          nocrs    (map-pattern-matrix-ocrs ocrs env')]\n      (pattern-matrix nrows nocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["m" "_meta"]]}
   {"arglists" [["rows" "env"]],
    "ns" "clojure.core.match",
    "name" "specialize-vector-pattern-matrix",
    "column" 1,
    "line" 1244,
    "source"
    "(defn specialize-vector-pattern-matrix [rows env]\n  (if (:rest? env)\n    (vec (map #(specialize-vector-pattern-row % env) rows))\n    (vec (map #(specialize-vector-pattern-row-non-rest % env) rows))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "emit-pattern",
    "line" 1624,
    "column" 1,
    "doc"
    "Returns the corresponding pattern for the given syntax. Dispatches\n  on the class of its argument. For example, `[(:or 1 2) 2]` is dispatched\n  as clojure.lang.IPersistentVector",
    "tag" nil,
    "source"
    "(defmulti emit-pattern \n  \"Returns the corresponding pattern for the given syntax. Dispatches\n  on the class of its argument. For example, `[(:or 1 2) 2]` is dispatched\n  as clojure.lang.IPersistentVector\"\n  class)",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "->RestPattern",
    "line" 1030,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.RestPattern.",
    "tag" nil,
    "source" "(defrecord RestPattern [p])",
    "file" "clojure/core/match.clj",
    "arglists" [["p"]]}
   {"source"
    "  (defn regroup-keywords [pattern]\n    (cond\n      (vector? pattern)\n      (first\n        (reduce\n          (fn [[result p q] r]\n            (cond\n              (void? p) [result q r]\n              (and (not (void? r)) (infix-keyword? q))\n              [(conj result (list (regroup-keywords p) q r)) void void]\n              :else [(conj result (regroup-keywords p)) q r]))\n          [[] void void]\n          (conj pattern void void)))\n\n      (seq? pattern)\n      (cons (regroup-keywords (first pattern)) (rest pattern))\n\n      :else pattern))",
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "regroup-keywords",
    "arglists" [["pattern"]],
    "column" 3,
    "line" 1747,
    "file" "clojure/core/match.clj"}
   {"arglists" [["rows" "env"]],
    "ns" "clojure.core.match",
    "name" "specialize-map-pattern-matrix",
    "column" 1,
    "line" 1131,
    "source"
    "(defn specialize-map-pattern-matrix [rows env]\n  (vec (map #(specialize-map-pattern-row % env) rows)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["m" "k"] ["m" "k" "not-found"]],
    "ns" "clojure.core.match",
    "name" "val-at*",
    "column" 1,
    "line" 103,
    "source"
    "(defn val-at*\n  ([m k] (val-at m k ::not-found))\n  ([m k not-found] (val-at m k not-found)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->PredicatePattern",
    "line" 1540,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.PredicatePattern.",
    "tag" nil,
    "source"
    "(deftype PredicatePattern [p gs _meta]\n  Object\n  (toString [this]\n    (str p \" :when \" gs))\n  (equals [_ other]\n    (and (instance? PredicatePattern other)\n         (= p (:p other))\n         (= gs (:gs other))))  \n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (PredicatePattern. p gs new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :p p\n      :gs gs\n      not-found))\n\n  IPatternCompile\n  (to-source* [this ocr]\n    `(and ~@(map (fn [expr ocr]\n                   (list expr ocr))\n                 gs (repeat ocr))))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows  (rows matrix)\n          ocrs  (occurrences matrix)\n          nrows (specialize-predicate-pattern-matrix rows)]\n      (pattern-matrix nrows ocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["p" "gs" "_meta"]]}
   {"arglists" [["ocrs" "focr"]],
    "ns" "clojure.core.match",
    "name" "seq-pattern-matrix-rest-ocrs",
    "column" 1,
    "line" 943,
    "source" "(defn seq-pattern-matrix-rest-ocrs [ocrs focr] ocrs)",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "map->LeafNode",
    "line" 350,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.LeafNode, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord LeafNode [value bindings]\n  INodeCompile\n  (n-to-clj [this]\n    (if (not (empty? bindings))\n      (let [bindings (remove (fn [[sym _]] (= sym '_))\n                             bindings)]\n       `(let [~@(apply concat bindings)]\n          ~value))\n      value)))",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.core.match",
    "name" "first-row-empty-case",
    "line" 692,
    "column" 1,
    "doc"
    "Case 2: If the first row is empty then matching always succeeds \n  and yields the first action.",
    "tag" nil,
    "source"
    "(defn first-row-empty-case \n  \"Case 2: If the first row is empty then matching always succeeds \n  and yields the first action.\"\n  [rows ocr]\n  (let [f (first rows)\n        a (:action f)\n        bs (:bindings f)]\n    ;; FIXME: the first row is an infinite list of nil - David\n    (leaf-node a bs)))",
    "file" "clojure/core/match.clj",
    "arglists" [["rows" "ocr"]]}
   {"ns" "clojure.core.match",
    "name" "to-source",
    "line" 1617,
    "column" 1,
    "doc"
    "Returns a Clojure form that, when executed, is truthy if the\n  pattern matches the occurrence. Dispatches on the `type` of the\n  pattern. For instance, a literal pattern might return `(= ~(:pattern\n  pattern) ~ocr)`, using `=` to test for a match.",
    "tag" nil,
    "source"
    "(defmulti to-source \n  \"Returns a Clojure form that, when executed, is truthy if the\n  pattern matches the occurrence. Dispatches on the `type` of the\n  pattern. For instance, a literal pattern might return `(= ~(:pattern\n  pattern) ~ocr)`, using `=` to test for a match.\"\n  (fn [pattern ocr] (type pattern)))",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "->PatternMatrix",
    "line" 778,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.PatternMatrix.",
    "tag" nil,
    "source"
    "(defrecord PatternMatrix [rows ocrs]\n  IVecMod\n  (drop-nth [_ i]\n    (let [nrows (vec (map #(drop-nth % i) rows))]\n      (PatternMatrix. nrows ocrs)))\n\n  ;; Swap column number idx with the first column\n  (swap [_ idx]\n    (let [nrows (vec (map #(swap % idx) rows))]\n      (PatternMatrix. nrows (swap ocrs idx)))))",
    "file" "clojure/core/match.clj",
    "arglists" [["rows" "ocrs"]]}
   {"arglists" [["p"]],
    "ns" "clojure.core.match",
    "name" "rest-pattern",
    "column" 1,
    "line" 1032,
    "source" "(defn rest-pattern [p]\n  (RestPattern. p))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti count-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "count-inline",
    "column" 1,
    "line" 131,
    "file" "clojure/core/match.clj"}
   {"ns" "clojure.core.match",
    "name" "map->SwitchNode",
    "line" 427,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.SwitchNode, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord SwitchNode [occurrence cases default]\n  INodeCompile\n  (n-to-clj [this]\n    (let [clauses (doall\n                    (mapcat (partial apply dag-clause-to-clj occurrence)\n                      cases))\n          bind-expr (-> occurrence meta :bind-expr)\n          cond-expr\n          (if *recur-present*\n            (doall\n              (concat\n                `(cond ~@clauses)\n                `(:else ~(n-to-clj default))))\n            (doall\n              (concat\n                `(cond ~@clauses)\n                `(:else\n                   ~(backtrack-expr)))))]\n      (if *recur-present*\n        (if bind-expr\n          `~(doall\n              (concat\n                `(let [~occurrence ~bind-expr])\n                (list cond-expr)))\n          `~cond-expr)\n        (if bind-expr\n          `(try\n             ~(doall\n                (concat\n                  `(let [~occurrence ~bind-expr])\n                  (list cond-expr)))\n             ~(catch-error (n-to-clj default)))\n          `(try\n             ~cond-expr\n             ~(catch-error (n-to-clj default))))))))",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"arglists" [["pm"]],
    "ns" "clojure.core.match",
    "name" "necessary-column",
    "column" 1,
    "line" 556,
    "source"
    "(defn necessary-column [pm]\n  (->> (apply map vector (useful-matrix pm))\n    (map-indexed score-column)\n    (reduce\n      (fn [[col score :as curr]\n           [ocol oscore :as cand]]\n        (if (> oscore score) cand curr))\n      [0 0])\n    first))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "named-wildcard-pattern?",
    "column" 1,
    "line" 857,
    "source"
    "(defn named-wildcard-pattern? [x]\n  (and (instance? WildcardPattern x) (:named x)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "cases",
    "column" 1,
    "line" 649,
    "source"
    "(defn cases [matrix]\n  (if (vector? matrix)\n    ;; grouped literal case\n    (->> matrix\n      (map (fn [[c m]]\n             [c (-> m (specialize c) compile)]))\n      vec)\n    ;; normal case\n    (let [rows (rows matrix)\n          c    (ffirst rows)]\n      [[c (-> matrix (specialize c) compile)]])))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "map-key-pattern?",
    "column" 1,
    "line" 1071,
    "source"
    "(defn map-key-pattern? [x]\n  (instance? MapKeyPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "literal-case-matrix-splitter",
    "column" 1,
    "line" 620,
    "source"
    "(defn literal-case-matrix-splitter [matrix]\n  (let [ocrs  (occurrences matrix)\n        rows  (rows matrix)\n        lrows (loop [rows (seq rows) res [] lits #{}]\n                ;; a bit hacky but lit patterns hash differently we\n                ;; store the literal value directly in lits set\n                (if rows\n                  (let [[p :as row] (first rows)]\n                    (if (and (non-local-literal-pattern? p)\n                             (not (contains? lits (:l p))))\n                      (recur (next rows) (conj res row)\n                        (if (non-local-literal-pattern? p)\n                          (conj lits (:l p))\n                          lits))\n                      res))\n                  res))\n        S     (->> lrows\n                (group-rows (map first lrows))\n                (map (fn [[c rows]]\n                       [c (pattern-matrix rows ocrs)]))\n                vec)\n        D     (pattern-matrix (drop (count lrows) rows) ocrs)]\n    (return-split S D)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "clojure.core.match",
    "name" "non-local-literal-pattern?",
    "column" 1,
    "line" 616,
    "source"
    "(defn non-local-literal-pattern? [p]\n  (and (literal-pattern? p)\n       (not (-> p :l meta :local))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->SeqPattern",
    "line" 974,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.SeqPattern.",
    "tag" nil,
    "source"
    "(deftype SeqPattern [s _meta]\n  Object\n  (toString [_]\n    (str s))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (SeqPattern. s new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :s s\n      not-found))\n  \n  IPatternCompile\n  (to-source* [this ocr]\n    (if (and (>= (count s) 1)\n             (not (rest-pattern? (first s))))\n      `(and (or (seq? ~ocr) (sequential? ~ocr)) (seq ~ocr))\n      `(or (seq? ~ocr) (sequential? ~ocr))))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows  (rows matrix)\n          ocrs  (occurrences matrix)\n          focr  (first ocrs)]\n      (if-not (rest-pattern? (first s))\n        (let [nrows (specialize-seq-pattern-matrix rows focr)\n              nocrs (seq-pattern-matrix-ocrs ocrs focr)]\n          (pattern-matrix nrows nocrs))\n        (let [nrows (specialize-seq-pattern-rest-matrix rows focr)\n              nocrs (seq-pattern-matrix-rest-ocrs ocrs focr)]\n          (pattern-matrix nrows nocrs))))))",
    "file" "clojure/core/match.clj",
    "arglists" [["s" "_meta"]]}
   {"arglists" [["pm" "i" "j"]],
    "ns" "clojure.core.match",
    "name" "pattern-score",
    "column" 1,
    "line" 535,
    "source"
    "(defn pattern-score [pm i j]\n  (let [p (pattern-at pm i j)]\n    (cond\n      (or (wildcard-pattern? p)\n          (not (constructors-above? pm i j))) 0\n      (existential-pattern? p) 1\n      :else 2)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "wildcard-pattern?",
    "column" 1,
    "line" 851,
    "source"
    "(defn wildcard-pattern? [x]\n  (instance? WildcardPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["pm"]],
    "ns" "clojure.core.match",
    "name" "occurrences",
    "column" 1,
    "line" 509,
    "source" "(defn occurrences [pm] (:ocrs pm))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"] ["matrix" "p"]],
    "ns" "clojure.core.match",
    "name" "specialize",
    "column" 1,
    "line" 571,
    "source"
    "(defn specialize\n  ([matrix]\n    (specialize matrix (ffirst (rows matrix))))\n  ([matrix p]\n    (if (satisfies? ISpecializeMatrix p)\n      (specialize-matrix p matrix)\n      (default-specialize-matrix p matrix))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->FailNode",
    "line" 383,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.FailNode.",
    "tag" nil,
    "source"
    "(defrecord FailNode []\n  INodeCompile\n  (n-to-clj [this]\n    (if *recur-present*\n      `(throw\n         ~(if *clojurescript*\n            `(js/Error. (str \"No match found.\"))\n            `(Exception. (str \"No match found.\"))))\n      (backtrack-expr))))",
    "file" "clojure/core/match.clj",
    "arglists" [[]]}
   {"ns" "clojure.core.match",
    "name" "other-column-chosen-case",
    "line" 746,
    "column" 1,
    "doc"
    "Case 3b: A column other than the first is chosen. Swap column \ncol with the first column and compile the result",
    "tag" nil,
    "source"
    "(defn other-column-chosen-case \n  \"Case 3b: A column other than the first is chosen. Swap column \ncol with the first column and compile the result\"\n  [matrix col]\n  (compile (swap matrix col)))",
    "file" "clojure/core/match.clj",
    "arglists" [["matrix" "col"]]}
   {"arglists" [["occurrence" "cases" "default"]],
    "ns" "clojure.core.match",
    "name" "switch-node",
    "column" 1,
    "line" 463,
    "source"
    "(defn switch-node\n  ([occurrence cases default]\n   {:pre [(sequential? cases)]}\n   (SwitchNode. occurrence cases default)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "emit-matrix",
    "line" 1902,
    "column" 1,
    "doc"
    "Take the list of vars and sequence of unprocessed clauses and\n   return the pattern matrix. The pattern matrix contains the processed\n   pattern rows and the list of vars originally specified. Inserts\n   a last match - :else if provided by the user or a default match that\n   throws.",
    "tag" nil,
    "source"
    "(defn emit-matrix\n  \"Take the list of vars and sequence of unprocessed clauses and\n   return the pattern matrix. The pattern matrix contains the processed\n   pattern rows and the list of vars originally specified. Inserts\n   a last match - :else if provided by the user or a default match that\n   throws.\"\n  ([vars clauses]\n    (emit-matrix vars clauses true))\n  ([vars clauses default]\n    (let [cs (partition 2 clauses)\n          vs (process-vars vars)\n          cs (let [[p a] (last cs)\n                   last-match (vec (repeat (count vars) '_))]\n               (if (= :else p)\n                 (conj (vec (butlast cs)) [last-match a])\n                 ;; TODO: throw an exception if :else line not provided - David\n                 (if default\n                   (conj (vec cs)\n                     [last-match\n                       (if *clojurescript*\n                         `(throw\n                            (js/Error.\n                              (str \"No matching clause: \" ~@(interpose \" \" vs))))\n                         `(throw\n                            (IllegalArgumentException.\n                              (str \"No matching clause: \" ~@(interpose \" \" vs)))))])\n                   cs)))]\n      (pattern-matrix\n        (vec (map #(apply to-pattern-row %) cs))\n        (process-vars vs)))))",
    "file" "clojure/core/match.clj",
    "arglists" [["vars" "clauses"] ["vars" "clauses" "default"]]}
   {"arglists" [[[["rows" "rows"]]]],
    "ns" "clojure.core.match",
    "name" "width",
    "column" 1,
    "line" 481,
    "source"
    "(defn width [{rows :rows}]\n  (if (not (empty? rows))\n    (count (rows 0))\n    0))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti nthnext-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "nthnext-inline",
    "column" 1,
    "line" 135,
    "file" "clojure/core/match.clj"}
   {"arglists" [["value"] ["value" "bindings"]],
    "ns" "clojure.core.match",
    "name" "leaf-node",
    "column" 1,
    "line" 361,
    "source"
    "(defn leaf-node\n  ([value] (LeafNode. value []))\n  ([value bindings] (LeafNode. value bindings)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["k" "env"]],
    "ns" "clojure.core.match",
    "name" "map-pattern-matrix-ocr-sym",
    "column" 1,
    "line" 1137,
    "source"
    "(defn map-pattern-matrix-ocr-sym [k env]\n  (let [focr (:focr env)\n        ocr  (get-in env [:ocrs-map k])]\n    (with-meta ocr\n      {:occurrence-type :map\n       :key k\n       :map-sym focr\n       :bind-expr (val-at-expr focr k ::not-found)})))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "predicate-pattern",
    "line" 1674,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare emit-pattern-for-syntax or-pattern as-pattern guard-pattern\n         predicate-pattern vector-pattern)",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"arglists" [[[["rows" "rows"]] "i" "j"]],
    "ns" "clojure.core.match",
    "name" "pattern-at",
    "column" 1,
    "line" 503,
    "source" "(defn pattern-at [{rows :rows} i j]\n  ((rows j) i))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["p" "matrix"]],
    "ns" "clojure.core.match",
    "name" "default-specialize-matrix",
    "column" 1,
    "line" 799,
    "source"
    "(defn default-specialize-matrix [p matrix]\n  (let [rows (rows matrix)\n        ocrs (occurrences matrix)\n        focr (first ocrs)\n        nrows (->> rows\n                (map #(drop-nth-bind % 0 focr))\n                vec)\n        nocrs (drop-nth ocrs 0)]\n    (pattern-matrix nrows nocrs)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "seq-pattern?",
    "column" 1,
    "line" 1017,
    "source" "(defn seq-pattern? [x]\n  (instance? SeqPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["focr" "row"]],
    "ns" "clojure.core.match",
    "name" "specialize-seq-pattern-row",
    "column" 1,
    "line" 945,
    "source"
    "(defn specialize-seq-pattern-row [focr row]\n  (let [p (first row)\n        [h t] (if (seq-pattern? p)\n                (let [[h & t] (:s p)\n                      t (cond\n                          (empty? t) (literal-pattern ())\n                          (rest-pattern? (first t)) (:p (first t))\n                          :else (seq-pattern t))]\n                  [h t])\n                [(wildcard-pattern) (wildcard-pattern)])]\n    (reduce prepend (drop-nth-bind row 0 focr) [t h])))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["rows" "focr"]],
    "ns" "clojure.core.match",
    "name" "specialize-seq-pattern-rest-matrix",
    "column" 1,
    "line" 938,
    "source"
    "(defn specialize-seq-pattern-rest-matrix [rows focr]\n  (->> rows\n    (map (partial specialize-seq-pattern-rest-row focr))\n    vec))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["col"]],
    "ns" "clojure.core.match",
    "name" "column-splitter",
    "column" 1,
    "line" 582,
    "source"
    "(defn column-splitter [col]\n  (let [f (first col)\n        [top bottom] (split-with #(groupable? f %) (rest col))]\n    [(cons f top) bottom]))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.match",
    "name" "backtrack-expr",
    "column" 1,
    "line" 73,
    "source"
    "(defn backtrack-expr []\n  (if *clojurescript*\n    `(throw cljs.core.match/backtrack)\n    `(throw clojure.core.match/backtrack)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["t" "ocr"]],
    "ns" "clojure.core.match",
    "name" "with-tag",
    "column" 1,
    "line" 146,
    "source"
    "(defn with-tag [t ocr]\n  (let [the-tag (tag t)\n        the-tag (if (and (class? the-tag)\n                         (.isArray ^Class the-tag))\n                  (.getName ^Class the-tag)\n                  the-tag)]\n    (vary-meta ocr assoc :tag the-tag)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti pseudo-pattern? type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "pseudo-pattern?",
    "column" 1,
    "line" 1435,
    "file" "clojure/core/match.clj"}
   {"ns" "clojure.core.match",
    "name" "match-let",
    "macro" true,
    "line" 1996,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro match-let [bindings & body]\n  (let [bindvars# (take-nth 2 bindings)]\n    `(let ~bindings\n       (match [~@bindvars#]\n         ~@body))))",
    "file" "clojure/core/match.clj",
    "arglists" [["bindings" "&" "body"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "vector-pattern?",
    "column" 1,
    "line" 1371,
    "source"
    "(defn vector-pattern? [x]\n  (instance? VectorPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "backtrack",
    "line" 70,
    "column" 1,
    "doc" "Pre-allocated exception used for backtracing",
    "tag" nil,
    "source"
    "(def ^{:doc \"Pre-allocated exception used for backtracing\"}\n  backtrack (Exception. \"Could not find match.\"))",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"arglists" [["rows" "focr"]],
    "ns" "clojure.core.match",
    "name" "specialize-seq-pattern-matrix",
    "column" 1,
    "line" 957,
    "source"
    "(defn specialize-seq-pattern-matrix [rows focr]\n  (->> rows\n    (map (partial specialize-seq-pattern-row focr))\n    vec))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "match",
    "macro" true,
    "line" 1964,
    "column" 1,
    "doc"
    "Pattern match a row of occurrences. Take a vector of occurrences, vars.\n  Clause question-answer syntax is like `cond`. Questions must be\n  wrapped in a vector, with same arity as vars. Last question can be :else,\n  which expands to a row of wildcards.\n  \n  Example:\n  (let [x 1\n        y 2]\n    (match [x y 3]\n      [1 2 3] :answer1\n      :else :default-answer))",
    "tag" nil,
    "source"
    "(defmacro match \n  \"Pattern match a row of occurrences. Take a vector of occurrences, vars.\n  Clause question-answer syntax is like `cond`. Questions must be\n  wrapped in a vector, with same arity as vars. Last question can be :else,\n  which expands to a row of wildcards.\n  \n  Example:\n  (let [x 1\n        y 2]\n    (match [x y 3]\n      [1 2 3] :answer1\n      :else :default-answer))\"\n  [vars & clauses]\n  (let [[vars clauses]\n        (if (vector? vars)\n          [vars clauses]\n          [(vector vars)\n            (mapcat (fn [[c a]]\n                      [(if (not= c :else) (vector c) c) a])\n              (partition 2 clauses))])]\n   (binding [*line* (-> &form meta :line)\n             *locals* (dissoc &env '_)\n             *warned* (atom false)]\n     `~(clj-form vars clauses))))",
    "file" "clojure/core/match.clj",
    "arglists" [["vars" "&" "clauses"]]}
   {"arglists" [["bindings" "node"]],
    "ns" "clojure.core.match",
    "name" "bind-node",
    "column" 1,
    "line" 405,
    "source"
    "(defn bind-node [bindings node]\n  (BindNode. bindings node))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["row" "pat" "ps"]],
    "ns" "clojure.core.match",
    "name" "specialize-or-pattern-row",
    "column" 1,
    "line" 1380,
    "source"
    "(defn specialize-or-pattern-row [row pat ps]\n  (let [p (first row)]\n    ;; NOTE: hmm why can't we remove this - David\n    (if (and (groupable? pat p)\n             (not (wildcard-pattern? p)))\n      (map (fn [p] (update-pattern row 0 p)) ps) [row])))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix" "i"]],
    "ns" "clojure.core.match",
    "name" "pseudo-patterns",
    "column" 1,
    "line" 579,
    "source"
    "(defn pseudo-patterns [matrix i]\n  (filter pseudo-pattern? (column matrix i)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "map->BindNode",
    "line" 399,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.BindNode, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord BindNode [bindings node]\n  INodeCompile\n  (n-to-clj [this]\n    `(let [~@bindings]\n       ~(n-to-clj node))))",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"arglists" [["p" [["keys" ["only" "all-keys" "wc-map"]]]]],
    "ns" "clojure.core.match",
    "name" "get-ocr-map",
    "column" 1,
    "line" 1102,
    "source"
    "(defn get-ocr-map\n  [p {:keys [only all-keys wc-map]}]\n  (if (map-pattern? p)\n    (merge\n      (when only\n        (zipmap all-keys\n          (repeat (literal-pattern ::not-found))))\n      wc-map (wrap-values (:m p)))\n    wc-map))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "wildcard-or-existential?",
    "column" 1,
    "line" 521,
    "source"
    "(defn wildcard-or-existential? [x]\n  (or (wildcard-pattern? x)\n      (existential-pattern? x)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["i"]],
    "ns" "clojure.core.match",
    "name" "first-column?",
    "column" 1,
    "line" 471,
    "source" "(defn first-column? [i] (zero? i))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["prow" "i" "p"]],
    "ns" "clojure.core.match",
    "name" "update-pattern",
    "column" 1,
    "line" 323,
    "source"
    "(defn update-pattern [prow i p]\n  (pattern-row (assoc (:ps prow) i p) (:action prow) (:bindings prow)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["rows" "env"]],
    "ns" "clojure.core.match",
    "name" "get-all-keys",
    "column" 1,
    "line" 1088,
    "source"
    "(defn get-all-keys [rows env]\n  (->> rows\n    (remove (comp wildcard-pattern? first))\n    (map #(row-keys % env))\n    (reduce concat)\n    (reduce set/union #{})))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "literal-pattern?",
    "column" 1,
    "line" 917,
    "source"
    "(defn literal-pattern? [x]\n  (instance? LiteralPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "first-row-wildcards-case",
    "line" 702,
    "column" 1,
    "doc"
    "Case 2: If the first row is constituted by wildcards then matching\n  matching always succeeds and yields the first action.",
    "tag" nil,
    "source"
    "(defn first-row-wildcards-case \n  \"Case 2: If the first row is constituted by wildcards then matching\n  matching always succeeds and yields the first action.\"\n  [rows ocrs]\n  (let [f (first rows)\n        a (:action f)\n        bs (row-bindings f ocrs)]\n    (leaf-node a bs)))",
    "file" "clojure/core/match.clj",
    "arglists" [["rows" "ocrs"]]}
   {"source" "(defmulti test-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "test-inline",
    "column" 1,
    "line" 128,
    "file" "clojure/core/match.clj"}
   {"arglists" [["pm"]],
    "ns" "clojure.core.match",
    "name" "dim",
    "column" 1,
    "line" 489,
    "source" "(defn dim [pm]\n  [(width pm) (height pm)])",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "*syntax-check*",
    "line" 45,
    "column" 1,
    "doc" "Enable syntax check of match macros",
    "tag" nil,
    "source"
    "(def ^{:dynamic true\n       :doc \"Enable syntax check of match macros\"} \n  *syntax-check* (atom true))",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "rest-pattern?",
    "column" 1,
    "line" 1035,
    "source" "(defn rest-pattern? [x]\n  (instance? RestPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "*no-backtrack*",
    "line" 66,
    "column" 1,
    "doc" "Flag to optimize performance over code size.",
    "tag" nil,
    "source"
    "(def ^{:dynamic true\n       :doc \"Flag to optimize performance over code size.\"}\n  *no-backtrack* false)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["prow" "n" "ocr"]],
    "ns" "clojure.core.match",
    "name" "drop-nth-bind",
    "column" 1,
    "line" 329,
    "source"
    "(defn drop-nth-bind [prow n ocr]\n  (let [ps        (:ps prow)\n        p         (ps n)\n        action    (:action prow)\n        bind-expr (leaf-bind-expr ocr)\n        as        (-> p meta :as)\n        bindings  (or (:bindings prow) [])\n        bindings  (if as\n                    (conj bindings [as bind-expr])\n                    bindings)\n        bindings  (if (named-wildcard-pattern? p)\n                    (conj bindings [(:sym p) bind-expr])\n                    bindings)]\n    (pattern-row (drop-nth ps n) action bindings)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "default-case",
    "column" 1,
    "line" 644,
    "source"
    "(defn default-case [matrix]\n  (if-not (empty-matrix? matrix)\n    (compile matrix)\n    (fail-node)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "clojure.core.match",
    "name" "map-key-pattern",
    "column" 1,
    "line" 1068,
    "source" "(defn map-key-pattern [p]\n  (MapKeyPattern. p))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists"
    [["row" [["keys" ["all-keys" "only?" "focr"]] ["as" "env"]]]],
    "ns" "clojure.core.match",
    "name" "specialize-map-pattern-row",
    "column" 1,
    "line" 1112,
    "source"
    "(defn specialize-map-pattern-row\n  [row {:keys [all-keys only? focr] :as env}]\n  (let [p       (first row)\n        only    (seq (-> p meta :only))\n        ocr-map (get-ocr-map p (assoc env :only only))\n        ps      (doall (map ocr-map all-keys))\n        ps      (if @only?\n                  (if only\n                    (let [a (with-meta (gensym) {:tag 'java.util.Map})]\n                      (cons\n                        (guard-pattern (wildcard-pattern)\n                          (set [(if *clojurescript*\n                                  `(fn [~a] (= (set (keys ~a)) #{~@only}))\n                                  `(fn [~a] (= (.keySet ~a) #{~@only})))]))\n                        ps))\n                    (cons (wildcard-pattern) ps))\n                  ps)]\n    (reduce prepend (drop-nth-bind row 0 focr) (reverse ps))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "root-bind-node",
    "column" 1,
    "line" 673,
    "source"
    "(defn root-bind-node [matrix]\n  (let [ocrs (occurrences matrix)\n        node (compile matrix)]\n    (if (some expression? ocrs)\n      (bind-node (bind-variables ocrs) node)\n      node)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["cs" "rows"]],
    "ns" "clojure.core.match",
    "name" "group-rows",
    "column" 1,
    "line" 604,
    "source"
    "(defn group-rows [cs rows]\n  (reduce\n    (fn [res row]\n      (let [[c rows] (peek res)\n             c'      (first row)]\n        (if (groupable? c c')\n          (conj (pop res) [c (conj rows row)])\n          (conj res [c' [row]]))))\n    [[(first cs) [(first rows)]]] (rest rows)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists"
    [["ocrs"
      [["keys" ["focr" "tag" "min-size" "rest?"]] ["as" "env"]]]],
    "ns" "clojure.core.match",
    "name" "vector-pattern-matrix-ocrs",
    "column" 1,
    "line" 1261,
    "source"
    "(defn vector-pattern-matrix-ocrs\n  [ocrs {:keys [focr tag min-size rest?] :as env}]\n  (if rest?\n    (let [ocr-meta {:occurrence-type tag\n                    :vec-sym focr}\n          vl-ocr (gensym (str (name focr) \"_left__\"))\n          vl-ocr (with-meta vl-ocr\n                   (assoc ocr-meta :bind-expr\n                     (subvec-inline tag (with-tag tag focr) 0 min-size )))\n          vr-ocr (gensym (str (name focr) \"_right__\"))\n          vr-ocr (with-meta vr-ocr\n                   (assoc ocr-meta :bind-expr\n                     (subvec-inline tag (with-tag tag focr) min-size)))]\n      (into [vl-ocr vr-ocr] (drop-nth ocrs 0)))\n    (concat\n      (map (partial vector-pattern-ocr-sym env) (range min-size))\n      (drop-nth ocrs 0))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["ocrs"]],
    "ns" "clojure.core.match",
    "name" "bind-variables",
    "column" 1,
    "line" 664,
    "source"
    "(defn bind-variables [ocrs] \n  (mapcat\n    (fn [ocr]\n      (let [bind-expr (get (meta ocr) :ocr-expr ::not-found)]\n        (if (not= bind-expr ::not-found)\n          [ocr bind-expr]\n          [ocr ocr])))\n    ocrs))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["node"]],
    "ns" "clojure.core.match",
    "name" "executable-form",
    "column" 1,
    "line" 1933,
    "source" "(defn executable-form [node]\n  (n-to-clj node))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->SwitchNode",
    "line" 427,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.SwitchNode.",
    "tag" nil,
    "source"
    "(defrecord SwitchNode [occurrence cases default]\n  INodeCompile\n  (n-to-clj [this]\n    (let [clauses (doall\n                    (mapcat (partial apply dag-clause-to-clj occurrence)\n                      cases))\n          bind-expr (-> occurrence meta :bind-expr)\n          cond-expr\n          (if *recur-present*\n            (doall\n              (concat\n                `(cond ~@clauses)\n                `(:else ~(n-to-clj default))))\n            (doall\n              (concat\n                `(cond ~@clauses)\n                `(:else\n                   ~(backtrack-expr)))))]\n      (if *recur-present*\n        (if bind-expr\n          `~(doall\n              (concat\n                `(let [~occurrence ~bind-expr])\n                (list cond-expr)))\n          `~cond-expr)\n        (if bind-expr\n          `(try\n             ~(doall\n                (concat\n                  `(let [~occurrence ~bind-expr])\n                  (list cond-expr)))\n             ~(catch-error (n-to-clj default)))\n          `(try\n             ~cond-expr\n             ~(catch-error (n-to-clj default))))))))",
    "file" "clojure/core/match.clj",
    "arglists" [["occurrence" "cases" "default"]]}
   {"arglists" [["S" "D"]],
    "ns" "clojure.core.match",
    "name" "return-split",
    "column" 1,
    "line" 589,
    "source"
    "(defn return-split [S D]\n  (if *recur-present*\n    (if (and (empty-matrix? D) (seq *backtrack-stack*))\n      [S (peek *backtrack-stack*) *backtrack-stack*]\n      [S D (conj *backtrack-stack* D)])\n    [S D]))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->BindNode",
    "line" 399,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.BindNode.",
    "tag" nil,
    "source"
    "(defrecord BindNode [bindings node]\n  INodeCompile\n  (n-to-clj [this]\n    `(let [~@bindings]\n       ~(n-to-clj node))))",
    "file" "clojure/core/match.clj",
    "arglists" [["bindings" "node"]]}
   {"arglists" [[[["rows" "rows"]] "j"]],
    "ns" "clojure.core.match",
    "name" "row",
    "column" 1,
    "line" 498,
    "source" "(defn row [{rows :rows} j]\n  (nth rows j))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.match",
    "name" "backtrack-sym",
    "column" 1,
    "line" 78,
    "source"
    "(defn backtrack-sym []\n  (if *clojurescript*\n    'cljs.core.match/backtrack\n    'clojure.core.match/backtrack))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "or-pattern?",
    "column" 1,
    "line" 1422,
    "source" "(defn or-pattern? [x]\n  (instance? OrPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[[["rows" "rows"]]]],
    "ns" "clojure.core.match",
    "name" "rows",
    "column" 1,
    "line" 501,
    "source" "(defn rows [{rows :rows}] rows)",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "map->PatternMatrix",
    "line" 778,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.PatternMatrix, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord PatternMatrix [rows ocrs]\n  IVecMod\n  (drop-nth [_ i]\n    (let [nrows (vec (map #(drop-nth % i) rows))]\n      (PatternMatrix. nrows ocrs)))\n\n  ;; Swap column number idx with the first column\n  (swap [_ idx]\n    (let [nrows (vec (map #(swap % idx) rows))]\n      (PatternMatrix. nrows (swap ocrs idx)))))",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"source" "(defmulti nth-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "nth-inline",
    "column" 1,
    "line" 132,
    "file" "clojure/core/match.clj"}
   {"arglists" [["focr" "row"]],
    "ns" "clojure.core.match",
    "name" "specialize-seq-pattern-rest-row",
    "column" 1,
    "line" 931,
    "source"
    "(defn specialize-seq-pattern-rest-row [focr row]\n  (let [p (first row)\n        p (if (seq-pattern? p)\n            (:p (first (:s p))) ;; unwrap rest pattern\n            (wildcard-pattern))]\n    (prepend (drop-nth-bind row 0 focr) p)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti test-with-min-size-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "test-with-min-size-inline",
    "column" 1,
    "line" 130,
    "file" "clojure/core/match.clj"}
   {"arglists" [[[["keys" ["rows" "ocrs"]] ["as" "pm"]]]],
    "ns" "clojure.core.match",
    "name" "compile",
    "column" 1,
    "line" 757,
    "source"
    "(defn compile [{:keys [rows ocrs] :as pm}]\n  (cond\n    *root*\n    (binding [*root* false]\n      (root-bind-node pm))\n\n    (empty? rows)\n    (empty-rows-case)\n\n    (empty-row? (first rows))\n    (first-row-empty-case rows (first ocrs))\n\n    (all-wildcards? (first rows))\n    (first-row-wildcards-case rows ocrs)\n\n    :else\n    (let [col (choose-column pm)]\n      (if (first-column? col)\n        (first-column-chosen-case pm col ocrs)\n        (other-column-chosen-case pm col)))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti check-size? identity)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "check-size?",
    "column" 1,
    "line" 126,
    "file" "clojure/core/match.clj"}
   {"arglists" [["rows"]],
    "ns" "clojure.core.match",
    "name" "specialize-guard-pattern-matrix",
    "column" 1,
    "line" 1452,
    "source"
    "(defn specialize-guard-pattern-matrix [rows]\n  (->> rows\n    (map (fn [[p :as row]]\n           (if (guard-pattern? p)\n             (update-pattern row 0 (:p p))\n             row)))\n    vec))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->WildcardPattern",
    "line" 820,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.WildcardPattern.",
    "tag" nil,
    "source"
    "(deftype WildcardPattern [sym named _meta]\n  Object\n  (equals [_ other]\n    (and (instance? WildcardPattern other)\n         (if named\n           (= sym (:sym other))\n           (not (:named other)))))\n\n  clojure.lang.IObj\n  (withMeta [_ new-meta]\n    (WildcardPattern. sym named new-meta))\n  (meta [_]\n    _meta)\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :sym sym\n      :named named\n      not-found)))",
    "file" "clojure/core/match.clj",
    "arglists" [["sym" "named" "_meta"]]}
   {"arglists" [["prow"]],
    "ns" "clojure.core.match",
    "name" "all-wildcards?",
    "column" 1,
    "line" 326,
    "source"
    "(defn all-wildcards? [prow]\n  (every? wildcard-pattern? (:ps prow)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "*locals*",
    "line" 53,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *locals* nil)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [[[["rows" "rows"]] "j"]],
    "ns" "clojure.core.match",
    "name" "action-for-row",
    "column" 1,
    "line" 506,
    "source"
    "(defn action-for-row [{rows :rows} j]\n  (:action (rows j)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti subvec-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "subvec-inline",
    "column" 1,
    "line" 134,
    "file" "clojure/core/match.clj"}
   {"arglists" [["pm"]],
    "ns" "clojure.core.match",
    "name" "useful-matrix",
    "column" 1,
    "line" 548,
    "source"
    "(defn useful-matrix [pm]\n  (->> (for [j (range (height pm))\n             i (range (width pm))]\n         (pattern-score pm i j))\n    (partition (width pm))\n    (map vec)\n    vec))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source"
    "(defmulti leaf-bind-expr (fn [ocr] (-> ocr meta :occurrence-type)))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "leaf-bind-expr",
    "column" 1,
    "line" 365,
    "file" "clojure/core/match.clj"}
   {"arglists" [["rows" "env"]],
    "ns" "clojure.core.match",
    "name" "calc-rest?-and-min-size",
    "column" 1,
    "line" 1219,
    "source"
    "(defn calc-rest?-and-min-size [rows env]\n  (reduce\n    (fn [[rest? min-size] [p & ps]]\n      (if (vector-pattern? p)\n        [(or rest? (:rest? p))\n         (min min-size (:size p))]\n        [rest? min-size]))\n    [false (-> env :fp :size)] rows))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[[["rows" "rows"]] "i"]],
    "ns" "clojure.core.match",
    "name" "column",
    "column" 1,
    "line" 495,
    "source"
    "(defn column [{rows :rows} i]\n  (vec (map #(nth % i) rows)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["pm" "i" "j"]],
    "ns" "clojure.core.match",
    "name" "constructors-above?",
    "column" 1,
    "line" 525,
    "source"
    "(defn constructors-above? [pm i j]\n  (every?\n    (comp not wildcard-or-existential?)\n    (take j (column pm i))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "guard-pattern?",
    "column" 1,
    "line" 1500,
    "source" "(defn guard-pattern? [x]\n  (instance? GuardPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "map-pattern?",
    "column" 1,
    "line" 1205,
    "source" "(defn map-pattern? [x]\n  (instance? MapPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["occurrence" "pattern" "action"]],
    "ns" "clojure.core.match",
    "name" "dag-clause-to-clj",
    "column" 1,
    "line" 413,
    "source"
    "(defn dag-clause-to-clj [occurrence pattern action]\n  (let [test (if (instance? clojure.core.match.protocols.IPatternCompile pattern)\n               (to-source* pattern occurrence) \n               (to-source pattern occurrence))]\n    [test (n-to-clj action)]))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "map->FailNode",
    "line" 383,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.FailNode, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord FailNode []\n  INodeCompile\n  (n-to-clj [this]\n    (if *recur-present*\n      `(throw\n         ~(if *clojurescript*\n            `(js/Error. (str \"No match found.\"))\n            `(Exception. (str \"No match found.\"))))\n      (backtrack-expr))))",
    "file" "clojure/core/match.clj",
    "arglists" [["m__5818__auto__"]]}
   {"arglists" [["matrix" "col"]],
    "ns" "clojure.core.match",
    "name" "expand-matrix",
    "column" 1,
    "line" 711,
    "source"
    "(defn expand-matrix [matrix col]\n  (reduce\n    (fn [matrix p]\n      (specialize matrix p))\n    matrix (pseudo-patterns matrix col)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "split-matrix",
    "column" 1,
    "line" 717,
    "source"
    "(defn split-matrix [matrix]\n  (if (non-local-literal-pattern? (ffirst (rows matrix)))\n    ;; literal testing based on equality can do w/o\n    ;; backtracking for all adjacent literal ctors in a column\n    (literal-case-matrix-splitter matrix)\n    (matrix-splitter matrix)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["pattern"]],
    "ns" "clojure.core.match",
    "name" "find-duplicate-wildcards",
    "column" 1,
    "line" 1827,
    "source"
    "(defn find-duplicate-wildcards [pattern]\n  (second (wildcards-and-duplicates pattern)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["rows"]],
    "ns" "clojure.core.match",
    "name" "specialize-map-key-pattern-matrix",
    "column" 1,
    "line" 1047,
    "source"
    "(defn specialize-map-key-pattern-matrix [rows]\n  (let [p (:p (ffirst rows))]\n    (->> rows\n      (map #(drop-nth % 0))\n      (map #(prepend % p))\n      vec)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti nth-offset-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "nth-offset-inline",
    "column" 1,
    "line" 133,
    "file" "clojure/core/match.clj"}
   {"ns" "clojure.core.match",
    "name" "guard-pattern",
    "line" 1674,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare emit-pattern-for-syntax or-pattern as-pattern guard-pattern\n         predicate-pattern vector-pattern)",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "*warned*",
    "line" 54,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *warned*)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.core.match",
    "name" "->PatternRow",
    "line" 244,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.PatternRow.",
    "tag" nil,
    "source"
    "(deftype PatternRow [ps action bindings]\n  Object\n  (equals [_ other]\n    (and (instance? PatternRow other)\n         (= ps (:ps other))\n         (= action (:action other))\n         (= bindings (:bindings other))))\n  \n  IVecMod\n  (drop-nth [_ n]\n    (PatternRow. (drop-nth ps n) action bindings))\n\n  (prepend [_ x]\n    (PatternRow. (into [x] ps) action bindings))\n\n  (swap [_ n]\n    (PatternRow. (swap ps n) action bindings))\n\n  clojure.lang.Associative\n  (assoc [this k v]\n    (PatternRow. (assoc ps k v) action bindings))\n\n  clojure.lang.Indexed\n  (nth [_ i]\n    (nth ps i))\n  (nth [_ i x]\n    (nth ps i x))\n\n  clojure.lang.ISeq\n  (first [_] (first ps))\n\n  (next [_]\n    (if-let [nps (next ps)]\n      (PatternRow. nps action bindings)\n      (PatternRow. [] action bindings)))\n\n  (more [_]\n    (if (empty? ps)\n      nil\n      (let [nps (rest ps)]\n        (PatternRow. nps action bindings))))\n\n  (seq [this]\n    (seq ps))\n\n  (count [_]\n    (count ps))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :ps ps\n      :action action\n      :bindings bindings\n      not-found))\n\n  clojure.lang.IFn\n  (invoke [_ n]\n    (nth ps n))\n\n  clojure.lang.IPersistentCollection\n  (cons [_ x]\n    (PatternRow. (conj ps x) action bindings))\n  (equiv [this other]\n    (.equals this other)))",
    "file" "clojure/core/match.clj",
    "arglists" [["ps" "action" "bindings"]]}
   {"arglists" [["msg"]],
    "ns" "clojure.core.match",
    "name" "warn",
    "column" 1,
    "line" 86,
    "source"
    "(defn warn [msg]\n  (if (not @*warned*)\n    (do\n      (binding [*out* *err*] \n        (println \"WARNING:\"\n                 (str *ns* \", line \" *line* \":\") \n                 msg))\n      (reset! *warned* true))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "existential-pattern?",
    "column" 1,
    "line" 518,
    "source"
    "(defn existential-pattern? [x]\n  (instance? IExistentialPattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "matchv",
    "macro" true,
    "line" 1989,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro matchv [type vars & clauses]\n  (binding [*vector-type* type\n            *line* (-> &form meta :line)\n            *locals* (dissoc &env '_)\n            *warned* (atom false)]\n    `~(clj-form vars clauses)))",
    "file" "clojure/core/match.clj",
    "arglists" [["type" "vars" "&" "clauses"]]}
   {"arglists" [["rows"]],
    "ns" "clojure.core.match",
    "name" "specialize-predicate-pattern-matrix",
    "column" 1,
    "line" 1532,
    "source"
    "(defn specialize-predicate-pattern-matrix [rows]\n  (->> rows\n    (map (fn [[p :as row]]\n           (if (predicate-pattern? p)\n             (update-pattern row 0 (:p p))\n             row)))\n    vec))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[[["keys" ["pat" "focr" "tag"]]] "i"]],
    "ns" "clojure.core.match",
    "name" "vector-pattern-ocr-sym",
    "column" 1,
    "line" 1249,
    "source"
    "(defn vector-pattern-ocr-sym\n  [{:keys [pat focr tag]} i]\n  (let [ocr (gensym (str (name focr) \"_\" i \"__\"))]\n    (with-meta ocr\n      {:occurrence-type tag\n       :vec-sym focr\n       :index i\n       :bind-expr\n       (if-let [offset (:offset pat)]\n         (nth-offset-inline tag (with-tag tag focr) i offset)\n         (nth-inline tag (with-tag tag focr) i))})))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "vector-pattern",
    "line" 1674,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare emit-pattern-for-syntax or-pattern as-pattern guard-pattern\n         predicate-pattern vector-pattern)",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"arglists" [[]],
    "ns" "clojure.core.match",
    "name" "fail-node",
    "column" 1,
    "line" 393,
    "source" "(defn fail-node []\n  (FailNode.))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "->LeafNode",
    "line" 350,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.LeafNode.",
    "tag" nil,
    "source"
    "(defrecord LeafNode [value bindings]\n  INodeCompile\n  (n-to-clj [this]\n    (if (not (empty? bindings))\n      (let [bindings (remove (fn [[sym _]] (= sym '_))\n                             bindings)]\n       `(let [~@(apply concat bindings)]\n          ~value))\n      value)))",
    "file" "clojure/core/match.clj",
    "arglists" [["value" "bindings"]]}
   {"ns" "clojure.core.match",
    "name" "->VectorPattern",
    "line" 1293,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.VectorPattern.",
    "tag" nil,
    "source"
    "(deftype VectorPattern [v t size offset rest? _meta]\n  Object\n  (toString [_]\n    (str v \" \" t))\n  (equals [_ other]\n    (and (instance? VectorPattern other)\n         (= [v t size offset rest?]\n            (map #(% other) [:v :t :size :offset :rest?]))))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (VectorPattern. v t size offset rest? new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :v v\n      :t t\n      :size size\n      :offset offset\n      :rest? rest?\n      not-found))\n\n  IPatternCompile\n  (to-source* [this ocr]\n    (let [t (or (array-tag ocr) t)]\n      (if (check-size? t)\n        (if rest?\n          (test-with-min-size-inline t ocr size)\n          (test-with-size-inline t ocr size))\n        (test-inline t ocr))))\n\n  IContainsRestPattern\n  (contains-rest-pattern? [_] rest?)\n\n  IVectorPattern\n  (split [this n]\n    (let [lv (subvec v 0 n)\n          rv (subvec v n)\n          pl (VectorPattern. lv t n offset false _meta)\n          pr (if (rest-pattern? (first rv))\n               (:p (first rv))\n               (let [rest? (some rest-pattern? rv)\n                     rvc (count rv)\n                     size (if rest? (dec rvc) rvc)]\n                 (VectorPattern. rv t size n rest? _meta)))]\n      [pl pr]))\n\n  ISpecializeMatrix\n  (specialize-matrix [this matrix]\n    (let [rows (rows matrix)\n          ocrs (occurrences matrix)\n          focr (first ocrs)\n          env  {:focr focr\n                :fp   (ffirst rows)\n                :pat  this}\n          [rest? min-size] (calc-rest?-and-min-size rows env)\n          env' (assoc env\n                 :rest? rest? :min-size min-size\n                 :tag (or (array-tag focr) (:t this)))\n          nrows (specialize-vector-pattern-matrix rows env')\n          nocrs (vector-pattern-matrix-ocrs ocrs env')]\n      (pattern-matrix nrows nocrs))))",
    "file" "clojure/core/match.clj",
    "arglists" [["v" "t" "size" "offset" "rest?" "_meta"]]}
   {"ns" "clojure.core.match",
    "name" "groupable?",
    "line" 226,
    "column" 1,
    "doc"
    "Determine if two patterns may be grouped together for simultaneous\n   testing.",
    "tag" nil,
    "source"
    "(defmulti groupable?\n  \"Determine if two patterns may be grouped together for simultaneous\n   testing.\"\n  (fn [a b] [(type a) (type b)]))",
    "file" "clojure/core/match.clj",
    "arglists" nil}
   {"arglists" [["row" "env"]],
    "ns" "clojure.core.match",
    "name" "row-keys",
    "column" 1,
    "line" 1079,
    "source"
    "(defn row-keys [row env]\n  (let [p    (first row)\n        only (-> p meta :only)]\n    (when (and (not @(:only? env))\n               (seq only))\n      (reset! (:only? env) true))\n    [(set (keys (:m p)))\n     (set only)]))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[] ["sym"]],
    "ns" "clojure.core.match",
    "name" "wildcard-pattern",
    "column" 1,
    "line" 843,
    "source"
    "(defn wildcard-pattern\n  ([] (wildcard-pattern '_))\n  ([sym] \n    {:pre [(symbol? sym)]}\n    (if (= sym '_)\n      (WildcardPattern. (gensym) false nil)\n      (WildcardPattern. sym true nil))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["ps" "t"] ["ps" "t" "v"]],
    "ns" "clojure.core.match",
    "name" "emit-patterns",
    "column" 1,
    "line" 1633,
    "source"
    "(defn emit-patterns\n  ([ps t] (emit-patterns ps t []))\n  ([ps t v]\n     (if (empty? ps)\n       v\n       (let [p (first ps)]\n         (cond\n          (= p '&)\n          (let [p (second ps)\n                rp (if (and (vector? p) (= t :seq))\n                     (seq-pattern (emit-patterns p t))\n                     (emit-pattern p))]\n            (recur (nnext ps) t (conj v (rest-pattern rp)))) \n\n          :else\n          (recur (next ps) t (conj v (emit-pattern (first ps)))))))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "*vector-type*",
    "line" 56,
    "column" 1,
    "doc"
    "Default vector type. Can be rebound allowing emission of\n             custom inline code for vector patterns, for example\n             type-hinted primitive array operations",
    "tag" nil,
    "source"
    "(def ^{:dynamic true\n       :doc \"Default vector type. Can be rebound allowing emission of\n             custom inline code for vector patterns, for example\n             type-hinted primitive array operations\"}\n  *vector-type* ::vector)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["ps" "action"] ["ps" "action" "bindings"]],
    "ns" "clojure.core.match",
    "name" "pattern-row",
    "column" 1,
    "line" 312,
    "source"
    "(defn pattern-row\n  ([ps action] \n    (pattern-row ps action []))\n  ([ps action bindings]\n    (let [ps (if (vector? ps) ps (into [] ps))]\n      (PatternRow. ps action bindings))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["pm"]],
    "ns" "clojure.core.match",
    "name" "empty-matrix?",
    "column" 1,
    "line" 492,
    "source" "(defn empty-matrix? [pm]\n  (= (dim pm) [0 0]))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"source" "(defmulti test-with-size-inline vector-type)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.core.match",
    "name" "test-with-size-inline",
    "column" 1,
    "line" 129,
    "file" "clojure/core/match.clj"}
   {"arglists" [["matrix"]],
    "ns" "clojure.core.match",
    "name" "matrix-splitter",
    "column" 1,
    "line" 596,
    "source"
    "(defn matrix-splitter [matrix]\n  (let [rows (rows matrix)\n        n    (count (first (column-splitter (map first rows))))\n        ocrs (occurrences matrix)\n        S    (pattern-matrix (take n rows) ocrs)\n        D    (pattern-matrix (drop n rows) ocrs)]\n    (return-split S D)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["actions"]],
    "ns" "clojure.core.match",
    "name" "recur-present?",
    "column" 1,
    "line" 1939,
    "source"
    "(defn recur-present? [actions]\n  (letfn [(analyze-action [action]\n            (if (and (sequential? action)\n                     (some '#{recur} (flatten action)))\n              {:recur-present true} {}))]\n    (some :recur-present (map analyze-action actions))))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["t" "&" "r"]],
    "ns" "clojure.core.match",
    "name" "vector-type",
    "column" 1,
    "line" 124,
    "source" "(defn vector-type [t & r] t)",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["rows" "ocrs"]],
    "ns" "clojure.core.match",
    "name" "pattern-matrix",
    "column" 1,
    "line" 789,
    "source"
    "(defn pattern-matrix [rows ocrs]  \n  (let [rows (if (vector? rows) rows (into [] rows))\n        ocrs (if (vector? ocrs) ocrs (into [] ocrs))]\n    (PatternMatrix. rows ocrs)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "*root*",
    "line" 84,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *root* true)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.match",
    "name" "predicate-pattern?",
    "column" 1,
    "line" 1580,
    "source"
    "(defn predicate-pattern? [x]\n  (instance? PredicatePattern x))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"ns" "clojure.core.match",
    "name" "group-keywords",
    "line" 1766,
    "column" 2,
    "doc"
    "Returns a pattern with pattern-keywords (:when and :as) properly\n    grouped.  The original pattern may use the 'flattened' syntax.\n    For example, a 'flattened' pattern row like [a b :when even?] is\n    grouped as [a (b :when even?)].",
    "tag" nil,
    "source"
    " (defn group-keywords \n   \"Returns a pattern with pattern-keywords (:when and :as) properly\n    grouped.  The original pattern may use the 'flattened' syntax.\n    For example, a 'flattened' pattern row like [a b :when even?] is\n    grouped as [a (b :when even?)].\"\n  [pattern]\n  (if (vector? pattern) (regroup-keywords pattern) pattern))",
    "file" "clojure/core/match.clj",
    "arglists" [["pattern"]]}
   {"ns" "clojure.core.match",
    "name" "*clojurescript*",
    "line" 49,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true}\n  *clojurescript* false)",
    "file" "clojure/core/match.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["m"]],
    "ns" "clojure.core.match",
    "name" "wrap-values",
    "column" 1,
    "line" 1095,
    "source"
    "(defn wrap-values [m]\n  (->> m\n    (map (fn [[k v]]\n           [k (if (wildcard-pattern? v)\n                (map-key-pattern v) v)]))\n    (into {})))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "clojure.core.match",
    "name" "constructor?",
    "column" 1,
    "line" 218,
    "source" "(defn constructor? [p]\n  (not (wildcard-pattern? p)))",
    "file" "clojure/core/match.clj",
    "tag" nil}
   {"arglists" [[] ["m"]],
    "ns" "clojure.core.match",
    "name" "map-pattern",
    "column" 1,
    "line" 1200,
    "source"
    "(defn map-pattern\n  ([] (MapPattern. {} nil))\n  ([m] {:pre [(map? m)]}\n     (MapPattern. m nil)))",
    "file" "clojure/core/match.clj",
    "tag" nil}],
  "clojure.core.match.bench" [],
  "cljs.core.match.macros"
  [{"ns" "cljs.core.match.macros",
    "name" "match*",
    "macro" true,
    "line" 26,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro match*\n  [vars & clauses]\n  (let [[vars clauses]\n        (if (vector? vars)\n          [vars clauses]\n          [(vector vars)\n            (mapcat (fn [[c a]]\n                      [(if (not= c :else) (vector c) c) a])\n              (partition 2 clauses))])]\n   (binding [*clojurescript* true\n             *line* (-> &form meta :line)\n             *locals* (dissoc (:locals &env) '_)\n             *warned* (atom false)\n             *no-backtrack* true]\n     `~(clj-form vars clauses))))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["vars" "&" "clauses"]]}
   {"ns" "cljs.core.match.macros",
    "name" "matchv",
    "macro" true,
    "line" 42,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro matchv [type vars & clauses]\n  (binding [*clojurescript* true\n            *vector-type* type\n            *line* (-> &form meta :line)\n            *locals* (dissoc (:locals &env) '_)\n            *warned* (atom false)]\n    `~(clj-form vars clauses)))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["type" "vars" "&" "clauses"]]}
   {"ns" "cljs.core.match.macros",
    "name" "asets",
    "macro" true,
    "line" 5,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro asets [a vs]\n  `(do\n     ~@(map (fn [a b c] (concat a (list b c)))\n         (repeat `(aset ~a)) (range (count vs)) vs)\n     ~a))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["a" "vs"]]}
   {"ns" "cljs.core.match.macros",
    "name" "match",
    "macro" true,
    "line" 11,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro match \n  [vars & clauses]\n  (let [[vars clauses]\n        (if (vector? vars)\n          [vars clauses]\n          [(vector vars)\n            (mapcat (fn [[c a]]\n                      [(if (not= c :else) (vector c) c) a])\n              (partition 2 clauses))])]\n   (binding [*clojurescript* true\n             *line* (-> &form meta :line)\n             *locals* (dissoc (:locals &env) '_)\n             *warned* (atom false)]\n     `~(clj-form vars clauses))))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["vars" "&" "clauses"]]}
   {"ns" "cljs.core.match.macros",
    "name" "match-let",
    "macro" true,
    "line" 59,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro match-let [bindings & body]\n  (let [bindvars# (take-nth 2 bindings)]\n    `(let ~bindings\n       (match [~@bindvars#]\n         ~@body))))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["bindings" "&" "body"]]}
   {"ns" "cljs.core.match.macros",
    "name" "matchv*",
    "macro" true,
    "line" 50,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro matchv* [type vars & clauses]\n  (binding [*clojurescript* true\n            *vector-type* type\n            *line* (-> &form meta :line)\n            *locals* (dissoc (:locals &env) '_)\n            *warned* (atom false)\n            *no-backtrack* true]\n    `~(clj-form vars clauses)))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["type" "vars" "&" "clauses"]]}
   {"ns" "cljs.core.match.macros",
    "name" "match-let*",
    "macro" true,
    "line" 65,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro match-let* [bindings & body]\n  (let [bindvars# (take-nth 2 bindings)]\n    `(let ~bindings\n       (match* [~@bindvars#]\n         ~@body))))",
    "file" "cljs/core/match/macros.clj",
    "arglists" [["bindings" "&" "body"]]}],
  "clojure.core.match.debug"
  [{"arglists" [["pm"] ["pm" "col-width"]],
    "ns" "clojure.core.match.debug",
    "name" "pprint-matrix",
    "column" 1,
    "line" 45,
    "source"
    "(defn pprint-matrix\n  ([pm] (pprint-matrix pm 4))\n  ([pm col-width]\n     (binding [*out* (pp/get-pretty-writer *out*)]\n       (print \"|\")\n       (doseq [o (occurrences pm)]\n         (pp/cl-format true \"~4D~7,vT\" o col-width))\n       (print \"|\")\n       (prn)\n       (doseq [[i row] (map-indexed (fn [p i] [p i]) (rows pm))]\n         (print \"|\")\n         (doseq [p (:ps row)]\n           (pp/cl-format true \"~4D~7,vT\" (str p) col-width))\n         (print \"|\")\n         (print \" \" (action-for-row pm i))\n         (prn))\n       (println))))",
    "file" "clojure/core/match/debug.clj",
    "tag" nil}
   {"ns" "clojure.core.match.debug",
    "name" "build-matrix",
    "macro" true,
    "line" 19,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro build-matrix [vars & clauses]\n  `(emit-matrix '~vars '~clauses false))",
    "file" "clojure/core/match/debug.clj",
    "arglists" [["vars" "&" "clauses"]]}
   {"ns" "clojure.core.match.debug",
    "name" "with-recur",
    "macro" true,
    "line" 15,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro with-recur [form]\n  `(binding [clojure.core.match/*recur-present* true]\n     ~form))",
    "file" "clojure/core/match/debug.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.core.match.debug",
    "name" "m-to-clj",
    "macro" true,
    "line" 34,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro m-to-clj [vars & clauses]\n  (binding [clojure.core.match/*line* (-> &form meta :line)\n            clojure.core.match/*locals* &env\n            clojure.core.match/*warned* (atom false)]\n    (try \n      (-> (clj-form vars clauses)\n        source-pprint)\n      (catch AssertionError e\n        `(throw (AssertionError. ~(.getMessage e)))))))",
    "file" "clojure/core/match/debug.clj",
    "arglists" [["vars" "&" "clauses"]]}
   {"arglists" [["source"]],
    "ns" "clojure.core.match.debug",
    "name" "source-pprint",
    "column" 1,
    "line" 10,
    "source"
    "(defn source-pprint [source]\n  (binding [pp/*print-pprint-dispatch* pp/code-dispatch\n            pp/*print-suppress-namespaces* true]\n    (pp/pprint source)))",
    "file" "clojure/core/match/debug.clj",
    "tag" nil}
   {"ns" "clojure.core.match.debug",
    "name" "m-to-dag",
    "macro" true,
    "line" 26,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro m-to-dag [vars & clauses]\n  (binding [clojure.core.match/*line* (-> &form meta :line)\n            clojure.core.match/*locals* &env\n            clojure.core.match/*warned* (atom false)]\n    `~(-> (emit-matrix vars clauses)\n        compile\n        pp/pprint)))",
    "file" "clojure/core/match/debug.clj",
    "arglists" [["vars" "&" "clauses"]]}
   {"ns" "clojure.core.match.debug",
    "name" "m-to-matrix",
    "macro" true,
    "line" 22,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro m-to-matrix [vars & clauses]\n  `(-> (build-matrix ~vars ~@clauses)\n     pprint-matrix))",
    "file" "clojure/core/match/debug.clj",
    "arglists" [["vars" "&" "clauses"]]}],
  "clojure.core.match.array" [],
  "clojure.core.match.protocols"
  [{"ns" "clojure.core.match.protocols",
    "name" "IVecMod",
    "line" 26,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IVecMod\n  (prepend [this x])\n  (drop-nth [this n])\n  (swap [this n]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.match.protocols",
    "name" "IContainsRestPattern",
    "line" 9,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IContainsRestPattern\n  (contains-rest-pattern? [this]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.match.protocols",
    "name" "INodeCompile",
    "line" 20,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol INodeCompile\n  (n-to-clj [this]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "n"]],
    "name" "split"}
   {"ns" "clojure.core.match.protocols",
    "name" "IPatternCompile",
    "line" 23,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IPatternCompile\n  (to-source* [this ocr]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.match.protocols",
    "name" "IMatchLookup",
    "line" 12,
    "column" 1,
    "doc"
    "Allows arbitrary objects to act like a map-like object when pattern\n  matched. Avoid extending this directly for Java Beans, see\n  `match.java/bean-match`.",
    "tag" nil,
    "source"
    "(defprotocol IMatchLookup\n  \"Allows arbitrary objects to act like a map-like object when pattern\n  matched. Avoid extending this directly for Java Beans, see\n  `match.java/bean-match`.\"\n  (val-at [this k not-found]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "n"]],
    "name" "swap"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "k" "not-found"]],
    "name" "val-at"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this"]],
    "name" "n-to-clj"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "x"]],
    "name" "prepend"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this"]],
    "name" "contains-rest-pattern?"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "ocr"]],
    "name" "to-source*"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "n"]],
    "name" "drop-nth"}
   {"ns" "clojure.core.match.protocols",
    "name" "IVectorPattern",
    "line" 31,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IVectorPattern\n  (split [this n]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.match.protocols",
    "doc" nil,
    "arglists" [["this" "matrix"]],
    "name" "specialize-matrix"}
   {"ns" "clojure.core.match.protocols",
    "name" "ISpecializeMatrix",
    "line" 6,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol ISpecializeMatrix\n  (specialize-matrix [this matrix]))",
    "file" "clojure/core/match/protocols.clj",
    "arglists" nil}],
  "clojure.core.match.binary" [],
  "clojure.core.match.java"
  [{"private" true,
    "ns" "clojure.core.match.java",
    "name" "method-name-pattern",
    "line" 6,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private method-name-pattern #\"^(is|get)([A-Z].*)$\")",
    "file" "clojure/core/match/java.clj",
    "arglists" nil}
   {"ns" "clojure.core.match.java",
    "name" "bean-match",
    "macro" true,
    "line" 21,
    "column" 1,
    "doc"
    "Generate an implementation of match.core/IMatchLookup for a Java bean.\n  Accessor method names are mapped to keys like this:\n  \n    isVisible       -> :visible?\n    getText         -> :text\n    getAbsolutePath -> :absolute-path \n    isFUD           -> :fud?\n    getFUDFactor    -> :fud-factor\n\n  ",
    "tag" nil,
    "source"
    "(defmacro bean-match\n  \"Generate an implementation of match.core/IMatchLookup for a Java bean.\n  Accessor method names are mapped to keys like this:\n  \n    isVisible       -> :visible?\n    getText         -> :text\n    getAbsolutePath -> :absolute-path \n    isFUD           -> :fud?\n    getFUDFactor    -> :fud-factor\n\n  \"\n  [class] \n  (let [method-names (->> (.getMethods ^Class (resolve class))\n                       ; Methods that have is/get naming, no args and non-void return\n                       (filter (fn [^java.lang.reflect.Method m] \n                                 (and (re-find method-name-pattern (.getName m))\n                                      (= 0 (count (.getParameterTypes m)))\n                                      (not= Void (.getReturnType m))))) \n                       ; Grab name as a symbol\n                       (map    (fn [^java.lang.reflect.Method m] \n                                 (.getName m))))\n        this (gensym \"this\")]\n    `(extend-type ~class\n       IMatchLookup\n       (~'val-at [~this k# not-found#]\n          (case k#\n            ~@(mapcat \n                (fn [n] [(keywordize n) `(. ~this (~(symbol n)))]) \n                method-names)\n            not-found#)))))",
    "file" "clojure/core/match/java.clj",
    "arglists" [["class"]]}
   {"private" true,
    "ns" "clojure.core.match.java",
    "name" "dash-case",
    "line" 8,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- dash-case \n  [^String s] \n  (let [gsub (fn [s re sub] (.replaceAll (re-matcher re s) sub))] \n    (-> s\n      (gsub #\"([A-Z]+)([A-Z][a-z])\" \"$1-$2\")    \n      (gsub #\"([a-z]+)([A-Z])\" \"$1-$2\")\n      (lower-case))))",
    "file" "clojure/core/match/java.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.core.match.java",
    "name" "keywordize",
    "line" 16,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- keywordize \n  [^String s]\n  (let [[_ pre n] (re-find (re-matcher method-name-pattern s))]\n    (-> n dash-case (str (if (= pre \"is\") \"?\")) keyword)))",
    "file" "clojure/core/match/java.clj",
    "arglists" [["s"]]}],
  "clojure.core.match.regex"
  [{"ns" "clojure.core.match.regex",
    "name" "->RegexPattern",
    "line" 10,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.match.regex.RegexPattern.",
    "tag" nil,
    "source" "(defrecord RegexPattern [regex])",
    "file" "clojure/core/match/regex.clj",
    "arglists" [["regex"]]}
   {"ns" "clojure.core.match.regex",
    "name" "map->RegexPattern",
    "line" 10,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.match.regex.RegexPattern, taking a map of keywords to field values.",
    "tag" nil,
    "source" "(defrecord RegexPattern [regex])",
    "file" "clojure/core/match/regex.clj",
    "arglists" [["m__5818__auto__"]]}],
  "clojure.core.match.date" []},
 "description" "core.match 0.2.0",
 "version" "0.2.0",
 "name" "clojure.core.match",
 "group" "clojure.core.match"}
