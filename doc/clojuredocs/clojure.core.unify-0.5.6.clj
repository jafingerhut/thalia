{"namespaces"
 {"clojure.core.unify"
  [{"ns" "clojure.core.unify",
    "name" "extract-lvars",
    "line" 22,
    "column" 1,
    "doc"
    "Takes a datastructure and returns a distinct set of the logical\n   variables found within.",
    "tag" nil,
    "source"
    "(defn extract-lvars\n  \"Takes a datastructure and returns a distinct set of the logical\n   variables found within.\"\n  ([form]\n     (extract-lvars lvar? form))\n  ([lv-fn form]\n     (set\n      (walk/walk #(when (lv-fn %) %)\n                 #(keep identity %)\n                 form))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["form"] ["lv-fn" "form"]]}
   {"ns" "clojure.core.unify",
    "name" "subst",
    "line" 198,
    "column" 1,
    "doc"
    "Attempts to substitute the bindings in the appropriate locations in the given expression.",
    "tag" nil,
    "source"
    "(def ^{:doc      (:doc (meta #'try-subst))\n       :arglists '([expression bindings])}\n  subst   (make-occurs-subst-fn lvar?))",
    "file" "clojure/core/unify.clj",
    "arglists" [["expression" "bindings"]]}
   {"ns" "clojure.core.unify",
    "name" "unify-",
    "line" 234,
    "column" 1,
    "doc"
    "Attempt to unify x and y with the given bindings (if any). Potentially returns a map of the \n   unifiers (bindings) found.  Will throw an `IllegalStateException` if the expressions\n   contain a cycle relationship.  Will also throw an `IllegalArgumentException` if the\n   sub-expressions clash.  Note: This function is implemented **without** an occurs-check.",
    "tag" nil,
    "source"
    "(def ^{:doc      (str (:doc (meta #'garner-unifiers))\n                      \"  Note: This function is implemented **without** an occurs-check.\")\n       :arglists '([expression1 expression2])}\n  unify-   (make-unify-fn lvar?))",
    "file" "clojure/core/unify.clj",
    "arglists" [["expression1" "expression2"]]}
   {"ns" "clojure.core.unify",
    "name" "unifier",
    "line" 202,
    "column" 1,
    "doc"
    "Attempts the entire unification process from garnering the bindings to substituting\n   the appropriate bindings.  Note: This function is implemented with an occurs-check.",
    "tag" nil,
    "source"
    "(def ^{:doc      (str (:doc (meta #'unifier*))\n                      \"  Note: This function is implemented with an occurs-check.\")\n       :arglists '([expression1 expression2])}\n  unifier (make-occurs-unifier-fn lvar?))",
    "file" "clojure/core/unify.clj",
    "arglists" [["expression1" "expression2"]]}
   {"ns" "clojure.core.unify",
    "name" "make-occurs-subst-fn",
    "line" 178,
    "column" 1,
    "doc"
    "Given a function to recognize unification variables, returns a function that\n   will attempt to substitute unification bindings between two expressions.\n   This function uses an 'occurs check' methodology for detecting cycles.",
    "tag" nil,
    "source"
    "(defn make-occurs-subst-fn\n  \"Given a function to recognize unification variables, returns a function that\n   will attempt to substitute unification bindings between two expressions.\n   This function uses an 'occurs check' methodology for detecting cycles.\"\n  [variable-fn]\n  (partial try-subst variable-fn))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable-fn"]]}
   {"ns" "clojure.core.unify",
    "name" "make-occurs-unifier-fn",
    "line" 185,
    "column" 1,
    "doc"
    "Given a function to recognize unification variables, returns a function to\n   perform the unification of two expressions. This function uses an 'occurs check'\n   methodology for detecting cycles.",
    "tag" nil,
    "source"
    "(defn make-occurs-unifier-fn\n  \"Given a function to recognize unification variables, returns a function to\n   perform the unification of two expressions. This function uses an 'occurs check'\n   methodology for detecting cycles.\"\n  [variable-fn]\n  (partial unifier* variable-fn))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable-fn"]]}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "unify-variable-",
    "line" 97,
    "column" 1,
    "doc"
    "Unify the variable v with expr.  Uses the bindings supplied and possibly returns an extended bindings map.",
    "tag" nil,
    "source"
    "(def ^{:doc \"Unify the variable v with expr.  Uses the bindings supplied and possibly returns an extended bindings map.\"\n       :private true}\n  unify-variable- (create-var-unification-fn false))",
    "file" "clojure/core/unify.clj",
    "arglists" nil}
   {"ns" "clojure.core.unify",
    "name" "make-subst-fn",
    "line" 217,
    "column" 1,
    "doc"
    "Given a function to recognize unification variables, returns a function that\n   will attempt to substitute unification bindings between two expressions.",
    "tag" nil,
    "source"
    "(defn make-subst-fn\n  \"Given a function to recognize unification variables, returns a function that\n   will attempt to substitute unification bindings between two expressions.\"\n  [variable-fn]\n  (partial try-subst variable-fn))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable-fn"]]}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "composite?",
    "line" 33,
    "column" 1,
    "doc"
    "Taken from the old `contrib.core/seqable?`. Since the meaning of 'seqable' is\n   questionable, I will work on phasing it out and using a more meaningful\n   predicate.  At the moment, the only meaning of `composite?` is:\n   Returns true if `(seq x)` will succeed, false otherwise.",
    "tag" nil,
    "source"
    "(defn- composite?\n  \"Taken from the old `contrib.core/seqable?`. Since the meaning of 'seqable' is\n   questionable, I will work on phasing it out and using a more meaningful\n   predicate.  At the moment, the only meaning of `composite?` is:\n   Returns true if `(seq x)` will succeed, false otherwise.\" \n  [x]\n  (or (coll? x)\n      (nil? x) \n      (instance? Iterable x)\n      (-> x class .isArray)\n      (string? x)\n      (instance? java.util.Map x)))",
    "file" "clojure/core/unify.clj",
    "arglists" [["x"]]}
   {"arglists" [["form"]],
    "ns" "clojure.core.unify",
    "name" "wildcard?",
    "column" 1,
    "line" 101,
    "source"
    "(defn wildcard? [form]\n  (and (composite? form)\n       (#{'&} (first form))))",
    "file" "clojure/core/unify.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "occurs?",
    "line" 48,
    "column" 1,
    "doc" "Does v occur anywhere inside expr?",
    "tag" nil,
    "source"
    "(defn- occurs?\n  \"Does v occur anywhere inside expr?\"\n  [variable? v expr binds]\n  (loop [z (zip/zipper composite? seq #(do % %2) [expr])]\n    (let [current (zip/node z)]\n      (cond \n        (zip/end? z) false\n        (= v current) true\n        (and (variable? current)\n             (find binds current))\n        (recur (zip/next (zip/insert-right z (binds current))))\n        (zip/end? z) false\n        :else (recur (zip/next z))))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable?" "v" "expr" "binds"]]}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "determine-occursness",
    "line" 70,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- determine-occursness\n  [want-occurs? variable? v expr binds]\n  (if want-occurs?\n    `(if (occurs? ~variable? ~v ~expr ~binds)\n       (throw (IllegalStateException. (str \"Cycle found in the path \" ~expr)))\n       (bind-phase ~binds ~v ~expr))\n    `(bind-phase ~binds ~v ~expr)))",
    "file" "clojure/core/unify.clj",
    "arglists" [["want-occurs?" "variable?" "v" "expr" "binds"]]}
   {"ns" "clojure.core.unify",
    "name" "unify",
    "line" 193,
    "column" 1,
    "doc"
    "Attempt to unify x and y with the given bindings (if any). Potentially returns a map of the \n   unifiers (bindings) found.  Will throw an `IllegalStateException` if the expressions\n   contain a cycle relationship.  Will also throw an `IllegalArgumentException` if the\n   sub-expressions clash.  Note: This function is implemented with an occurs-check.",
    "tag" nil,
    "source"
    "(def ^{:doc      (str (:doc (meta #'garner-unifiers))\n                      \"  Note: This function is implemented with an occurs-check.\")\n       :arglists '([expression1 expression2])}\n  unify   (make-occurs-unify-fn lvar?))",
    "file" "clojure/core/unify.clj",
    "arglists" [["expression1" "expression2"]]}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "unify-variable",
    "line" 93,
    "column" 1,
    "doc"
    "Unify the variable v with expr.  Uses the bindings supplied and possibly returns an extended bindings map.",
    "tag" nil,
    "source"
    "(def ^{:doc \"Unify the variable v with expr.  Uses the bindings supplied and possibly returns an extended bindings map.\"\n       :private true}\n  unify-variable (create-var-unification-fn true))",
    "file" "clojure/core/unify.clj",
    "arglists" nil}
   {"ns" "clojure.core.unify",
    "name" "make-unify-fn",
    "line" 209,
    "column" 1,
    "doc"
    "Given a function to recognize unification variables, returns a function to\n   return a bindings map for two expressions.",
    "tag" nil,
    "source"
    "(defn make-unify-fn\n  \"Given a function to recognize unification variables, returns a function to\n   return a bindings map for two expressions.\"\n  [variable-fn]\n  (fn\n    ([x y] (garner-unifiers unify-variable- variable-fn x y {}))\n    ([x y binds] (garner-unifiers unify-variable- variable-fn x y binds))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable-fn"]]}
   {"ns" "clojure.core.unify",
    "name" "flatten-bindings",
    "line" 131,
    "column" 1,
    "doc"
    "Flattens recursive bindings in the given map to the same ground (if possible).",
    "tag" nil,
    "source"
    "(defn flatten-bindings\n  \"Flattens recursive bindings in the given map to the same ground (if possible).\"\n  ([binds] (flatten-bindings lvar? binds))\n  ([variable? binds]\n     (into {}\n           (remove (comp nil? second)\n                   (map (fn [[k v]]\n                          [k (loop [v v]\n                               (if (variable? v)\n                                 (recur (binds v))\n                                 v))])\n                        binds)))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["binds"] ["variable?" "binds"]]}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "unifier*",
    "line" 154,
    "column" 1,
    "doc"
    "Attempts the entire unification process from garnering the bindings to substituting\n   the appropriate bindings.",
    "tag" nil,
    "source"
    "(defn- unifier*\n  \"Attempts the entire unification process from garnering the bindings to substituting\n   the appropriate bindings.\"\n  ([x y] (unifier* lvar? x y))\n  ([variable? x y]\n     (unifier* variable? x y (garner-unifiers variable? x y)))\n  ([variable? x y binds]\n     (->> binds\n          (flatten-bindings variable?)\n          (try-subst variable? y))))",
    "file" "clojure/core/unify.clj",
    "arglists"
    [["x" "y"] ["variable?" "x" "y"] ["variable?" "x" "y" "binds"]]}
   {"ns" "clojure.core.unify",
    "name" "unifier-",
    "line" 240,
    "column" 1,
    "doc"
    "Attempts the entire unification process from garnering the bindings to substituting\n   the appropriate bindings.  Note: This function is implemented **without** an occurs-check.",
    "tag" nil,
    "source"
    "(def ^{:doc      (str (:doc (meta #'unifier*))\n                      \"  Note: This function is implemented **without** an occurs-check.\")\n       :arglists '([expression1 expression2])}\n  unifier- (make-unifier-fn lvar?))",
    "file" "clojure/core/unify.clj",
    "arglists" [["expression1" "expression2"]]}
   {"arglists" [["sym"]],
    "ns" "clojure.core.unify",
    "name" "ignore-variable?",
    "column" 1,
    "line" 15,
    "source" "(defn ignore-variable? [sym] (= '_ sym))",
    "file" "clojure/core/unify.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "try-subst",
    "line" 144,
    "column" 1,
    "doc"
    "Attempts to substitute the bindings in the appropriate locations in the given expression.",
    "tag" nil,
    "source"
    "(defn- try-subst\n  \"Attempts to substitute the bindings in the appropriate locations in the given expression.\"\n  [variable? x binds]\n  {:pre [(map? binds) (fn? variable?)]}\n  (walk/prewalk (fn [expr] \n                  (if (variable? expr)\n                    (or (binds expr) expr) \n                    expr)) \n                x))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable?" "x" "binds"]]}
   {"arglists" nil,
    "ns" "clojure.core.unify",
    "name" "lvar?",
    "column" 1,
    "line" 19,
    "source"
    "(def lvar? #(or (ignore-variable? %)\n                (and (symbol? %) (re-matches #\"^\\?.*\" (name %)))))",
    "file" "clojure/core/unify.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "bind-phase",
    "line" 63,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- bind-phase\n  [binds variable expr]\n  (if (or (nil? expr)\n          (ignore-variable? variable))\n    binds\n    (assoc binds variable expr)))",
    "file" "clojure/core/unify.clj",
    "arglists" [["binds" "variable" "expr"]]}
   {"ns" "clojure.core.unify",
    "name" "make-unifier-fn",
    "line" 223,
    "column" 1,
    "doc"
    "Given a function to recognize unification variables, returns a function to\n   perform the unification of two expressions.",
    "tag" nil,
    "source"
    "(defn make-unifier-fn\n  \"Given a function to recognize unification variables, returns a function to\n   perform the unification of two expressions.\"\n  [variable-fn]\n  (fn [x y]\n    (unifier* variable-fn\n              x\n              y\n              (garner-unifiers unify-variable- variable-fn x y {}))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable-fn"]]}
   {"ns" "clojure.core.unify",
    "name" "create-var-unification-fn",
    "macro" true,
    "line" 78,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro create-var-unification-fn\n  [want-occurs?]\n  (let [varp  (gensym)\n        v     (gensym)\n        expr  (gensym)\n        binds (gensym)]\n    `(fn var-unify\n       [~varp ~v ~expr ~binds]\n       (if-let [vb# (~binds ~v)] \n         (garner-unifiers ~varp vb# ~expr ~binds)\n         (if-let [vexpr# (and (~varp ~expr) (~binds ~expr))]\n           (garner-unifiers ~varp ~v vexpr# ~binds)\n           ~(determine-occursness want-occurs? varp v expr binds))))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["want-occurs?"]]}
   {"private" true,
    "ns" "clojure.core.unify",
    "name" "garner-unifiers",
    "line" 105,
    "column" 1,
    "doc"
    "Attempt to unify x and y with the given bindings (if any). Potentially returns a map of the \n   unifiers (bindings) found.  Will throw an `IllegalStateException` if the expressions\n   contain a cycle relationship.  Will also throw an `IllegalArgumentException` if the\n   sub-expressions clash.",
    "tag" nil,
    "source"
    "(defn- garner-unifiers\n  \"Attempt to unify x and y with the given bindings (if any). Potentially returns a map of the \n   unifiers (bindings) found.  Will throw an `IllegalStateException` if the expressions\n   contain a cycle relationship.  Will also throw an `IllegalArgumentException` if the\n   sub-expressions clash.\"\n  ([x y]                 (garner-unifiers unify-variable lvar? x y {}))\n  ([variable? x y]       (garner-unifiers unify-variable variable? x y {}))\n  ([variable? x y binds] (garner-unifiers unify-variable variable? x y binds))\n  ([uv-fn variable? x y binds]\n     (cond\n      (not binds)               nil\n      (= x y)                   binds\n      (variable? x)             (uv-fn variable? x y binds)\n      (variable? y)             (uv-fn variable? y x binds)\n      (wildcard? x)             (uv-fn variable? (second x) (seq y) binds)\n      (wildcard? y)             (uv-fn variable? (second y) (seq x) binds)\n      (every? composite? [x y]) (garner-unifiers uv-fn\n                                                 variable?\n                                                 (rest x) \n                                                 (rest y)\n                                                 (garner-unifiers uv-fn\n                                                                  variable?\n                                                                  (first x)\n                                                                  (first y) \n                                                                  binds)))))",
    "file" "clojure/core/unify.clj",
    "arglists"
    [["x" "y"]
     ["variable?" "x" "y"]
     ["variable?" "x" "y" "binds"]
     ["uv-fn" "variable?" "x" "y" "binds"]]}
   {"ns" "clojure.core.unify",
    "name" "make-occurs-unify-fn",
    "line" 169,
    "column" 1,
    "doc"
    "Given a function to recognize unification variables, returns a function to\n   return a bindings map for two expressions.  This function uses an 'occurs check'\n   methodology for detecting cycles.",
    "tag" nil,
    "source"
    "(defn make-occurs-unify-fn\n  \"Given a function to recognize unification variables, returns a function to\n   return a bindings map for two expressions.  This function uses an 'occurs check'\n   methodology for detecting cycles.\"\n  [variable-fn]\n  (fn\n    ([x y] (garner-unifiers unify-variable variable-fn x y {}))\n    ([x y binds] (garner-unifiers unify-variable variable-fn x y binds))))",
    "file" "clojure/core/unify.clj",
    "arglists" [["variable-fn"]]}]},
 "description" "core.unify 0.5.6",
 "version" "0.5.6",
 "name" "clojure.core.unify",
 "group" "clojure.core.unify"}
