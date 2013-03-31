{"namespaces"
 {"clojure.jvm.tools.analyzer.examples.side-effect"
  [{"arglists" [["exp"]],
    "ns" "clojure.jvm.tools.analyzer.examples.side-effect",
    "name" "warn-on-side-effect",
    "column" 1,
    "line" 12,
    "source"
    "(defn warn-on-side-effect [exp]\n  (when (= :set! (:op exp))\n    (binding [*out* *err*]\n      (println \"WARNING: Side effect in transaction\")))\n  (doseq [child-exp (:children exp)]\n    (warn-on-side-effect child-exp)))",
    "file" "clojure/jvm/tools/analyzer/examples/side_effect.clj",
    "tag" nil}
   {"ns" "clojure.jvm.tools.analyzer.examples.side-effect",
    "name" "transaction-method",
    "line" 7,
    "column" 1,
    "doc" "dosync reduces to a call to this method",
    "tag" nil,
    "source"
    "(def transaction-method\n  \"dosync reduces to a call to this method\"\n  (let [membrs (-> (reflect/reflect clojure.lang.LockingTransaction) :members)]\n    (first (filter #(= 'runInTransaction (:name %)) membrs))))",
    "file" "clojure/jvm/tools/analyzer/examples/side_effect.clj",
    "arglists" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.jvm.tools.analyzer.examples.side-effect",
    "name" "forbid-side-effects-in-transaction",
    "column" 1,
    "line" 19,
    "source"
    "(defn forbid-side-effects-in-transaction [exp]\n  (when (and (= :static-method (:op exp))\n             (= transaction-method (:method exp)))\n    (warn-on-side-effect (first (:args exp))))\n  (doseq [child-exp (:children exp)]\n    (forbid-side-effects-in-transaction child-exp)))",
    "file" "clojure/jvm/tools/analyzer/examples/side_effect.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.emit-form"
  [{"source"
    "(defmulti map->form (fn [expr mode]\n                      [(:op expr) mode]))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.jvm.tools.analyzer.emit-form",
    "name" "map->form",
    "column" 1,
    "line" 16,
    "file" "clojure/jvm/tools/analyzer/emit_form.clj"}
   {"private" true,
    "ns" "clojure.jvm.tools.analyzer.emit-form",
    "name" "var->symbol",
    "line" 44,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- var->symbol [var]\n  (symbol (str (ns-name (.ns var))) (str (.sym var))))",
    "file" "clojure/jvm/tools/analyzer/emit_form.clj",
    "arglists" [["var"]]}
   {"arglists" nil,
    "ns" "clojure.jvm.tools.analyzer.emit-form",
    "name" "emit-default",
    "column" 1,
    "line" 4,
    "source" "(def emit-default ::emit-default)",
    "file" "clojure/jvm/tools/analyzer/emit_form.clj",
    "tag" nil}
   {"arglists" [["tag"]],
    "ns" "clojure.jvm.tools.analyzer.emit-form",
    "name" "derive-emit-default",
    "column" 1,
    "line" 6,
    "source"
    "(defn derive-emit-default [tag]\n  (derive tag emit-default))",
    "file" "clojure/jvm/tools/analyzer/emit_form.clj",
    "tag" nil}
   {"ns" "clojure.jvm.tools.analyzer.emit-form",
    "name" "emit-form",
    "line" 11,
    "column" 1,
    "doc" "Return the form represented by the given AST.",
    "tag" nil,
    "source"
    "(defn emit-form \n  \"Return the form represented by the given AST.\"\n  [expr]\n  (map->form expr ::emit-default))",
    "file" "clojure/jvm/tools/analyzer/emit_form.clj",
    "arglists" [["expr"]]}],
  "clojure.jvm.tools.analyzer.util"
  [{"ns" "clojure.jvm.tools.analyzer.util",
    "name" "expr-seq",
    "line" 19,
    "column" 1,
    "doc"
    "Given an expression, returns a lazy sequence of the expressions\n  followed by its children (in a depth first manner)",
    "tag" nil,
    "source"
    "(defn expr-seq\n  \"Given an expression, returns a lazy sequence of the expressions\n  followed by its children (in a depth first manner)\"\n  [expr]\n  (tree-seq :children\n            :children\n            expr))",
    "file" "clojure/jvm/tools/analyzer/util.clj",
    "arglists" [["expr"]]}
   {"ns" "clojure.jvm.tools.analyzer.util",
    "name" "print-expr",
    "line" 13,
    "column" 1,
    "doc"
    "Pretty-prints expr, excluding supplied keys.\n  Example: (print-expr expr :children :env)",
    "tag" nil,
    "source"
    "(defn print-expr\n  \"Pretty-prints expr, excluding supplied keys.\n  Example: (print-expr expr :children :env)\"\n  [expr & exclusions]\n  (pp/pprint (apply dissoc-rec expr exclusions)))",
    "file" "clojure/jvm/tools/analyzer/util.clj",
    "arglists" [["expr" "&" "exclusions"]]}
   {"private" true,
    "ns" "clojure.jvm.tools.analyzer.util",
    "name" "dissoc-rec",
    "line" 4,
    "column" 1,
    "doc" "Return expr with the keys dissociated",
    "tag" nil,
    "source"
    "(defn- dissoc-rec \n  \"Return expr with the keys dissociated\"\n  [obj & keys]\n  (cond\n   (map? obj) (into {} (for [[key val] (apply dissoc obj keys)]\n                         [key (apply dissoc-rec val keys)]))\n   (sequential? obj) (map #(apply dissoc-rec % keys) obj)\n   :else obj))",
    "file" "clojure/jvm/tools/analyzer/util.clj",
    "arglists" [["obj" "&" "keys"]]}],
  "clojure.jvm.tools.analyzer.examples.docstring"
  [{"arglists" [["exp"]],
    "ns" "clojure.jvm.tools.analyzer.examples.docstring",
    "name" "check-def",
    "column" 1,
    "line" 6,
    "source"
    "(defn check-def [exp]\n  (when (= :fn-expr (-> exp :init :op))\n    (doseq [method (-> exp :init :methods)]\n      (let [body (:body method)]\n        (when (and (= :do (:op body))\n                   (< 1 (count (-> body :exprs))))\n          (let [first-exp (-> body :exprs first)]\n            (when (= :string (:op first-exp))\n              (binding [*out* *err*]\n                (println \"WARNING: Suspicious string, possibly misplaced docstring,\" (-> exp :var))))))))))",
    "file" "clojure/jvm/tools/analyzer/examples/docstring.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.jvm.tools.analyzer.examples.docstring",
    "name" "find-and-check-defs",
    "column" 1,
    "line" 17,
    "source"
    "(defn find-and-check-defs [exp]\n  (when (= :def (:op exp))\n    (check-def exp))\n  (doseq [child-exp (:children exp)]\n    (find-and-check-defs child-exp)))",
    "file" "clojure/jvm/tools/analyzer/examples/docstring.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.examples.nsforms"
  [{"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.nsforms",
    "name" "find-and-analyze-use-forms",
    "column" 1,
    "line" 14,
    "source"
    "(defn find-and-analyze-use-forms [expr]\n  (when (use? expr)\n    (warn-on-naked-use expr))\n  (doseq [child-expr (:children expr)]\n    (find-and-analyze-use-forms child-expr)))",
    "file" "clojure/jvm/tools/analyzer/examples/nsforms.clj",
    "tag" nil}
   {"arglists" [["use-expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.nsforms",
    "name" "warn-on-naked-use",
    "column" 1,
    "line" 4,
    "source"
    "(defn warn-on-naked-use [use-expr]\n  (doseq [s (map :val (:args use-expr))\n          :when (symbol? s)]\n    (println \"Warning: Naked use of\" (name s) \"in\" (-> use-expr :env :ns :name))))",
    "file" "clojure/jvm/tools/analyzer/examples/nsforms.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.nsforms",
    "name" "use?",
    "column" 1,
    "line" 9,
    "source"
    "(defn use? [expr]\n  (and (= :invoke (:op expr))\n       (= :var (-> expr :fexpr :op))\n       (= 'use (-> expr :fexpr :var meta :name))))",
    "file" "clojure/jvm/tools/analyzer/examples/nsforms.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.examples.privatevars"
  [{"private" true,
    "ns" "clojure.jvm.tools.analyzer.examples.privatevars",
    "name" "unused-fn",
    "line" 6,
    "column" 1,
    "tag" nil,
    "source" "(defn- unused-fn [] nil)",
    "file" "clojure/jvm/tools/analyzer/examples/privatevars.clj",
    "arglists" [[]]}
   {"arglists" [["exprs"]],
    "ns" "clojure.jvm.tools.analyzer.examples.privatevars",
    "name" "check-usage-of-private-vars",
    "column" 1,
    "line" 24,
    "source"
    "(defn check-usage-of-private-vars [exprs]\n  (let [v-count (apply merge-with + (map var-count exprs))]\n    (doseq [pvar (mapcat private-defs exprs)]\n      (when-not (get v-count pvar)\n        (println \"Private variable\" pvar \"is never used\")))))",
    "file" "clojure/jvm/tools/analyzer/examples/privatevars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.privatevars",
    "name" "var-count",
    "column" 1,
    "line" 18,
    "source"
    "(defn var-count [expr]\n  (if (= :var (:op expr))\n    {(:var expr) 1}\n    (apply merge-with +\n           (map var-count (:children expr)))))",
    "file" "clojure/jvm/tools/analyzer/examples/privatevars.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.jvm.tools.analyzer.examples.privatevars",
    "name" "unused-var",
    "line" 7,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private unused-var 0)",
    "file" "clojure/jvm/tools/analyzer/examples/privatevars.clj",
    "arglists" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.privatevars",
    "name" "defs",
    "column" 1,
    "line" 9,
    "source"
    "(defn defs [expr]\n  (apply concat\n         (when (= :def (:op expr)) [(:var expr)])\n         (map defs (:children expr))))",
    "file" "clojure/jvm/tools/analyzer/examples/privatevars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.privatevars",
    "name" "private-defs",
    "column" 1,
    "line" 14,
    "source"
    "(defn private-defs [expr]\n  (filter #(:private (meta %))\n          (defs expr)))",
    "file" "clojure/jvm/tools/analyzer/examples/privatevars.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.examples.tail-recursion"
  [{"private" true,
    "ns" "clojure.jvm.tools.analyzer.examples.tail-recursion",
    "name" "safe-mapcat",
    "line" 6,
    "column" 1,
    "doc"
    "Like `mapcat`, but works if the returned values aren't sequences.",
    "tag" nil,
    "source"
    "(defn- safe-mapcat\n  \"Like `mapcat`, but works if the returned values aren't sequences.\"\n  [f & colls]\n  (apply concat (map #(if (seq? %) % [%]) (apply map f colls))))",
    "file" "clojure/jvm/tools/analyzer/examples/tail_recursion.clj",
    "arglists" [["f" "&" "colls"]]}
   {"ns" "clojure.jvm.tools.analyzer.examples.tail-recursion",
    "name" "tail-recursive?",
    "line" 30,
    "column" 1,
    "doc"
    "Returns `true` if there is a call to the function being defined\n   in a tail position.  This does not necessarily mean that the tail call\n   can be replaced with `recur`, since that does not work with functions of\n   different arity, or across `try`.",
    "tag" nil,
    "source"
    "(defn tail-recursive?\n  \"Returns `true` if there is a call to the function being defined\n   in a tail position.  This does not necessarily mean that the tail call\n   can be replaced with `recur`, since that does not work with functions of\n   different arity, or across `try`.\"\n  [fn-tree]\n  (let [fn-name (or (-> fn-tree :name) (-> fn-tree :var))\n        tail-ops (find-tail-ops fn-tree)]\n    (boolean (when fn-name (some (partial = fn-name) tail-ops)))))",
    "file" "clojure/jvm/tools/analyzer/examples/tail_recursion.clj",
    "arglists" [["fn-tree"]]}
   {"ns" "clojure.jvm.tools.analyzer.examples.tail-recursion",
    "name" "find-tail-ops",
    "line" 13,
    "column" 1,
    "doc"
    "Returns a list of the function calls that are in tail position.",
    "tag" nil,
    "source"
    "(defn find-tail-ops\n  \"Returns a list of the function calls that are in tail position.\"\n  [tree]\n  (case (:op tree)\n    :def (safe-mapcat find-tail-ops (rest (:children tree)))\n    :do (recur (last (:children tree)))\n    :fn-expr (safe-mapcat find-tail-ops (:methods tree))\n    :fn-method (recur (:body tree))\n\n    :invoke\n    (or (-> tree :fexpr :local-binding :sym)\n        (-> tree :fexpr :var))\n\n    :let (recur (:body tree))\n    :if (map find-tail-ops [(:then tree) (:else tree)])\n    nil))",
    "file" "clojure/jvm/tools/analyzer/examples/tail_recursion.clj",
    "arglists" [["tree"]]}],
  "clojure.jvm.tools.analyzer.hygienic"
  [{"ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "emit-hy",
    "line" 16,
    "column" 1,
    "doc"
    "Emit an already-hygienic AST as a form.\n\n  eg. (-> (ast ...) ast-hy emit-hy)",
    "tag" nil,
    "source"
    "(defn emit-hy \n  \"Emit an already-hygienic AST as a form.\n\n  eg. (-> (ast ...) ast-hy emit-hy)\"\n  [expr]\n  (map->form expr hygienic-emit))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "arglists" [["expr"]]}
   {"arglists"
    [[[["keys" ["op" "init" "sym"]] ["as" "local-binding"]]
      "scope"
      "new-sym?"]],
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hygienic-local-binding",
    "column" 1,
    "line" 64,
    "source"
    "(defn hygienic-local-binding [{:keys [op init sym] :as local-binding} scope new-sym?]\n  {:pre [(= :local-binding op)]}\n  (let [hy-init (when init\n                  (hygienic-ast init scope))\n        hy-sym (if new-sym?\n                 (hygienic-sym scope sym)\n                 (scope sym))\n        _ (assert hy-sym (str \"Local \" sym \" not in scope.\"))]\n    (assoc local-binding\n           :init hy-init\n           hsym-key hy-sym)))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["lbs" "scope"]],
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hygienic-lbs",
    "column" 1,
    "line" 126,
    "source"
    "(defn hygienic-lbs [lbs scope]\n  (reduce (fn [[hy-lbs scope] {:keys [sym] :as local-binding}]\n            {:pre [(vector? hy-lbs)]}\n            (let [hy-local-binding (hygienic-local-binding local-binding scope true)\n                  hy-sym (hsym-key hy-local-binding)\n                  new-scope (add-scope scope sym hy-sym)]\n              [(conj hy-lbs hy-local-binding) new-scope]))\n          [[] scope] lbs))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hsym-key",
    "column" 1,
    "line" 23,
    "source" "(def hsym-key ::hygienic-sym)",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["name" "scope"]],
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hygienic-name",
    "column" 1,
    "line" 107,
    "source"
    "(defn hygienic-name [name scope]\n  (let [hy-name (when name\n                  (hygienic-sym scope name))\n        new-scope (if hy-name\n                    (add-scope scope name hy-name)\n                    scope)]\n    [hy-name new-scope]))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["scope" "sym" "hy-sym"]],
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "add-scope",
    "column" 1,
    "line" 77,
    "source"
    "(defn add-scope [scope sym hy-sym]\n  {:pre [sym hy-sym scope]}\n  (assoc scope sym hy-sym))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hygienic-emit",
    "column" 1,
    "line" 28,
    "source" "(def hygienic-emit ::hygienic-emit)",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "ast-hy",
    "line" 9,
    "column" 1,
    "doc"
    "Perform hygienic transformation on an AST\n\n  eg. (-> (ast ...) ast-hy)",
    "tag" nil,
    "source"
    "(defn ast-hy \n  \"Perform hygienic transformation on an AST\n\n  eg. (-> (ast ...) ast-hy)\"\n  [expr]\n  (hygienic-ast expr {}))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "arglists" [["expr"]]}
   {"arglists" [["scope" "sym"]],
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hygienic-sym",
    "column" 1,
    "line" 58,
    "source"
    "(defn hygienic-sym [scope sym]\n  ;only generate unique when shadowing\n  (if (scope sym)\n    (gensym sym)\n    sym))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["expr" "scope"]],
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hygienic-ast",
    "column" 1,
    "line" 50,
    "source"
    "(defn hygienic-ast [expr scope]\n  (assert expr)\n  (assert scope)\n  (fold-expr ::hygienic\n    {:expr-rec #(hygienic-ast % scope)\n     :locals {::scope scope}}\n    expr))",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.jvm.tools.analyzer.hygienic",
    "name" "hname-key",
    "column" 1,
    "line" 24,
    "source" "(def hname-key ::hygienic-name)",
    "file" "clojure/jvm/tools/analyzer/hygienic.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.examples.dynvars"
  [{"arglists" [["sym"]],
    "ns" "clojure.jvm.tools.analyzer.examples.dynvars",
    "name" "earmuffed?",
    "column" 1,
    "line" 4,
    "source"
    "(defn earmuffed? [sym]\n  (let [s (name sym)]\n    (and (< 2 (count s))\n         (.startsWith s \"*\")\n         (.endsWith s \"*\"))))",
    "file" "clojure/jvm/tools/analyzer/examples/dynvars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.dynvars",
    "name" "check-def",
    "column" 1,
    "line" 10,
    "source"
    "(defn check-def [expr]\n  (let [v (:var expr)\n        s (.sym v)]\n    (when (and (earmuffed? s)\n               (not (:is-dynamic expr)))\n      (println \"WARNING: Should\" v \"be marked dynamic?\"))))",
    "file" "clojure/jvm/tools/analyzer/examples/dynvars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.jvm.tools.analyzer.examples.dynvars",
    "name" "find-and-check-defs",
    "column" 1,
    "line" 17,
    "source"
    "(defn find-and-check-defs [expr]\n  (when (= :def (:op expr))\n    (check-def expr))\n  (doseq [child-expr (:children expr)]\n    (find-and-check-defs child-expr)))",
    "file" "clojure/jvm/tools/analyzer/examples/dynvars.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.fold"
  [{"arglists" [["a" "&" "_"]],
    "ns" "clojure.jvm.tools.analyzer.fold",
    "name" "return-first",
    "column" 1,
    "line" 26,
    "source" "(defn return-first [a & _] a)",
    "file" "clojure/jvm/tools/analyzer/fold.clj",
    "tag" nil}
   {"ns" "clojure.jvm.tools.analyzer.fold",
    "name" "add-fold-case",
    "macro" true,
    "line" 11,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro add-fold-case [mode op fld-fn]\n  `(defmethod fold-expr [~mode ~op]\n     [mode# options# expr#]\n     (let [~'[expr-rec]\n           (map #(or (% options#)\n                     (partial fold-expr mode# options#))\n                [:expr-rec])\n           ~'map-expr-rec #(if % (map ~'expr-rec %) %)\n           ~'if-expr-rec #(if % (~'expr-rec %) %)\n           fld-fn# ~fld-fn]\n       (fld-fn# expr# options#))))",
    "file" "clojure/jvm/tools/analyzer/fold.clj",
    "arglists" [["mode" "op" "fld-fn"]]}
   {"arglists" nil,
    "ns" "clojure.jvm.tools.analyzer.fold",
    "name" "fold-expr-default",
    "column" 1,
    "line" 3,
    "source" "(def fold-expr-default ::fold-expr)",
    "file" "clojure/jvm/tools/analyzer/fold.clj",
    "tag" nil}
   {"ns" "clojure.jvm.tools.analyzer.fold",
    "name" "add-default-fold-case",
    "macro" true,
    "line" 23,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro add-default-fold-case [ty fld-fn]\n  `(add-fold-case fold-expr-default ~ty ~fld-fn))",
    "file" "clojure/jvm/tools/analyzer/fold.clj",
    "arglists" [["ty" "fld-fn"]]}
   {"source"
    "(defmulti fold-expr (fn [mode options expr]\n                      [mode (:op expr)]))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.jvm.tools.analyzer.fold",
    "name" "fold-expr",
    "column" 1,
    "line" 8,
    "file" "clojure/jvm/tools/analyzer/fold.clj"}
   {"arglists" [["tag"]],
    "ns" "clojure.jvm.tools.analyzer.fold",
    "name" "derive-default-fold",
    "column" 1,
    "line" 5,
    "source"
    "(defn derive-default-fold [tag]\n  (derive tag fold-expr-default))",
    "file" "clojure/jvm/tools/analyzer/fold.clj",
    "tag" nil}],
  "clojure.jvm.tools.analyzer.examples.load-core" []},
 "description" "jvm.tools.analyzer 0.3.1",
 "version" "0.3.1",
 "name" "clojure.jvm.tools.analyzer",
 "group" "clojure.jvm.tools.analyzer"}
