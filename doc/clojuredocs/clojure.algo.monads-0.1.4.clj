{"namespaces"
 {"clojure.algo.monads"
  [{"ns" "clojure.algo.monads",
    "name" "defmonad",
    "macro" true,
    "line" 47,
    "column" 1,
    "doc"
    "Define a named monad by defining the monad operations. The definitions\n   are written like bindings to the monad operations m-bind and\n   m-result (required) and m-zero and m-plus (optional).",
    "tag" nil,
    "source"
    "(defmacro defmonad\n  \"Define a named monad by defining the monad operations. The definitions\n   are written like bindings to the monad operations m-bind and\n   m-result (required) and m-zero and m-plus (optional).\"\n\n  ([name doc-string operations]\n   (let [doc-name (with-meta name {:doc doc-string})]\n     `(defmonad ~doc-name ~operations)))\n\n  ([name operations]\n   `(def ~name (monad ~operations))))",
    "file" "clojure/algo/monads.clj",
    "arglists"
    [["name" "doc-string" "operations"] ["name" "operations"]]}
   {"arglists" [["f" "mv"]],
    "ns" "clojure.algo.monads",
    "name" "censor",
    "column" 1,
    "line" 517,
    "source" "(defn censor [f mv]\n  (let [[v a] mv] [v (f a)]))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-when-not",
    "macro" true,
    "line" 318,
    "column" 1,
    "doc"
    "If test if logical false, return monadic value m-expr, else return\n   (m-result nil).",
    "tag" nil,
    "source"
    "(defmacro m-when-not\n  \"If test if logical false, return monadic value m-expr, else return\n   (m-result nil).\"\n  [test m-expr]\n  `(if ~test (~'m-result nil) ~m-expr))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["test" "m-expr"]]}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "v"]],
    "ns" "clojure.algo.monads",
    "name" "m+write+m",
    "column" 1,
    "line" 510,
    "source"
    "(defmonadfn write [v]\n  (let [[_ a] (m-result nil)]\n    [nil (writer-m-add a v)]))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "maybe-m",
    "line" 355,
    "column" 1,
    "doc"
    "Monad describing computations with possible failures. Failure is\n    represented by nil, any other value is considered valid. As soon as\n    a step returns nil, the whole computation will yield nil as well.",
    "tag" nil,
    "source"
    "(defmonad maybe-m\n   \"Monad describing computations with possible failures. Failure is\n    represented by nil, any other value is considered valid. As soon as\n    a step returns nil, the whole computation will yield nil as well.\"\n   [m-zero   nil\n    m-result (fn m-result-maybe [v] v)\n    m-bind   (fn m-bind-maybe [mv f]\n               (if (nil? mv) nil (f mv)))\n    m-plus   (fn m-plus-maybe [& mvs]\n               (first (drop-while nil? mvs)))\n    ])",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "maybe-t",
    "line" 578,
    "column" 1,
    "doc"
    "Monad transformer that transforms a monad m into a monad in which\n   the base values can be invalid (represented by nothing, which defaults\n   to nil). The third argument chooses if m-zero and m-plus are inherited\n   from the base monad (use :m-plus-from-base) or adopt maybe-like\n   behaviour (use :m-plus-from-transformer). The default is :m-plus-from-base\n   if the base monad m has a definition for m-plus, and\n   :m-plus-from-transformer otherwise.",
    "tag" nil,
    "source"
    "(defn maybe-t\n  \"Monad transformer that transforms a monad m into a monad in which\n   the base values can be invalid (represented by nothing, which defaults\n   to nil). The third argument chooses if m-zero and m-plus are inherited\n   from the base monad (use :m-plus-from-base) or adopt maybe-like\n   behaviour (use :m-plus-from-transformer). The default is :m-plus-from-base\n   if the base monad m has a definition for m-plus, and\n   :m-plus-from-transformer otherwise.\"\n  ([m] (maybe-t m nil :m-plus-default))\n  ([m nothing] (maybe-t m nothing :m-plus-default))\n  ([m nothing which-m-plus]\n   (monad-transformer m which-m-plus\n     [m-result (with-monad m m-result)\n      m-bind   (with-monad m\n                 (fn m-bind-maybe-t [mv f]\n                   (m-bind mv\n                           (fn [x]\n                             (if (identical? x nothing)\n                               (m-result nothing)\n                               (f x))))))\n      m-zero   (with-monad m (m-result nothing))\n      m-plus   (with-monad m\n                 (fn m-plus-maybe-t [& mvs]\n                   (if (empty? mvs)\n                     (m-result nothing)\n                     (m-bind (first mvs)\n                             (fn [v]\n                               (if (= v nothing)\n                                 (apply m-plus-maybe-t (rest mvs))\n                                 (m-result v)))))))\n      ])))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["m"] ["m" "nothing"] ["m" "nothing" "which-m-plus"]]}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "flatten*",
    "line" 330,
    "column" 1,
    "doc"
    "Like #(apply concat %), but fully lazy: it evaluates each sublist\n   only when it is needed.",
    "tag" nil,
    "source"
    "(defn- flatten*\n  \"Like #(apply concat %), but fully lazy: it evaluates each sublist\n   only when it is needed.\"\n  [ss]\n  (lazy-seq\n   (when-let [s (seq ss)]\n     (concat (first s) (flatten* (rest s))))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["ss"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-map",
    "line" 270,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "'Executes' the sequence of monadic values resulting from mapping\n   f onto the values xs. f must return a monadic value.",
    "tag" nil,
    "source"
    "(defmonadfn m-map\n  \"'Executes' the sequence of monadic values resulting from mapping\n   f onto the values xs. f must return a monadic value.\"\n  [f xs]\n  (m-seq (map f xs)))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "ensure-items",
    "line" 66,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- ensure-items [n steps]\n  \"Ensures there are at least n elements on a list, will fill up with nil\n  values when list is not big enough.\"\n  (take n (concat steps (repeat nil))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["n" "steps"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-result",
    "line" 234,
    "column" 1,
    "symbol-macro" true,
    "tag" nil,
    "source" "(defsymbolmacro m-result m-result)",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-when",
    "macro" true,
    "line" 312,
    "column" 1,
    "doc"
    "If test is logical true, return monadic value m-expr, else return\n   (m-result nil).",
    "tag" nil,
    "source"
    "(defmacro m-when\n  \"If test is logical true, return monadic value m-expr, else return\n   (m-result nil).\"\n  [test m-expr]\n  `(if ~test ~m-expr (~'m-result nil)))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["test" "m-expr"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-until",
    "line" 300,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "While (p x) is false, replace x by the value returned by the\n   monadic computation (f x). Return (m-result x) for the first\n   x for which (p x) is true.",
    "tag" nil,
    "source"
    "(defmonadfn m-until\n  \"While (p x) is false, replace x by the value returned by the\n   monadic computation (f x). Return (m-result x) for the first\n   x for which (p x) is true.\"\n  [p f x]\n  (if (p x)\n    (m-result x)\n    (domonad\n      [y (f x)\n       z (m-until p f y)]\n      z)))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-seq",
    "line" 259,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "'Executes' the monadic values in ms and returns a sequence of the\n   basic values contained in them.",
    "tag" nil,
    "source"
    "(defmonadfn m-seq\n  \"'Executes' the monadic values in ms and returns a sequence of the\n   basic values contained in them.\"\n  [ms]\n  (reduce (fn [q p]\n            (m-bind p (fn [x]\n                        (m-bind q (fn [y]\n                                    (m-result (cons x y)))) )))\n          (m-result '())\n          (reverse ms)))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "steps"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-chain+m",
    "column" 1,
    "line" 276,
    "source"
    "(defmonadfn m-chain\n  \"Chains together monadic computation steps that are each functions\n   of one parameter. Each step is called with the result of the previous\n   step as its argument. (m-chain (step1 step2)) is equivalent to\n   (fn [x] (domonad [r1 (step1 x) r2 (step2 r1)] r2)).\"\n  [steps]\n  (reduce (fn m-chain-link [chain-expr step]\n            (fn [v] (m-bind (chain-expr v) step)))\n          m-result\n          steps))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "f" "m"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-fmap+m",
    "column" 1,
    "line" 254,
    "source"
    "(defmonadfn m-fmap\n  \"Bind the monadic value m to the function returning (f x) for argument x\"\n  [f m]\n  (m-bind m (fn [x] (m-result (f x)))))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-fmap",
    "line" 254,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "Bind the monadic value m to the function returning (f x) for argument x",
    "tag" nil,
    "source"
    "(defmonadfn m-fmap\n  \"Bind the monadic value m to the function returning (f x) for argument x\"\n  [f m]\n  (m-bind m (fn [x] (m-result (f x)))))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "ms"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-seq+m",
    "column" 1,
    "line" 259,
    "source"
    "(defmonadfn m-seq\n  \"'Executes' the monadic values in ms and returns a sequence of the\n   basic values contained in them.\"\n  [ms]\n  (reduce (fn [q p]\n            (m-bind p (fn [x]\n                        (m-bind q (fn [y]\n                                    (m-result (cons x y)))) )))\n          (m-result '())\n          (reverse ms)))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "call-cc",
    "line" 538,
    "column" 1,
    "doc"
    "A computation in the cont monad that calls function f with a single\n   argument representing the current continuation. The function f should\n   return a continuation (which becomes the return value of call-cc),\n   or call the passed-in current continuation to terminate.",
    "tag" nil,
    "source"
    "(defn call-cc\n  \"A computation in the cont monad that calls function f with a single\n   argument representing the current continuation. The function f should\n   return a continuation (which becomes the return value of call-cc),\n   or call the passed-in current continuation to terminate.\"\n  [f]\n  (fn [c]\n    (let [cc (fn cc [a] (fn [_] (c a)))\n          rc (f cc)]\n      (rc c))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["f"]]}
   {"ns" "clojure.algo.monads",
    "name" "monad",
    "macro" true,
    "line" 32,
    "column" 1,
    "doc"
    "Define a monad by defining the monad operations. The definitions\n   are written like bindings to the monad operations m-bind and\n   m-result (required) and m-zero and m-plus (optional).",
    "tag" nil,
    "source"
    "(defmacro monad\n  \"Define a monad by defining the monad operations. The definitions\n   are written like bindings to the monad operations m-bind and\n   m-result (required) and m-zero and m-plus (optional).\"\n  [operations]\n  `(let [~'m-bind   ::undefined\n         ~'m-result ::undefined\n         ~'m-zero   ::undefined\n         ~'m-plus   ::undefined\n         ~@operations]\n     {:m-result ~'m-result\n      :m-bind ~'m-bind \n      :m-zero ~'m-zero\n      :m-plus ~'m-plus}))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["operations"]]}
   {"ns" "clojure.algo.monads",
    "name" "set-m",
    "line" 382,
    "column" 1,
    "doc"
    "Monad describing multi-valued computations, like sequence-m,\n    but returning sets of results instead of sequences of results.",
    "tag" nil,
    "source"
    "(defmonad set-m\n   \"Monad describing multi-valued computations, like sequence-m,\n    but returning sets of results instead of sequences of results.\"\n   [m-result (fn m-result-set [v]\n               #{v})\n    m-bind   (fn m-bind-set [mv f]\n               (apply clojure.set/union (map f mv)))\n    m-zero   #{}\n    m-plus   (fn m-plus-set [& mvs]\n               (apply clojure.set/union mvs))\n    ])",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "state-m-until",
    "line" 458,
    "column" 1,
    "doc"
    "An optimized implementation of m-until for the state monad that\n   replaces recursion by a loop.",
    "tag" nil,
    "source"
    "(defn state-m-until\n  \"An optimized implementation of m-until for the state monad that\n   replaces recursion by a loop.\"\n  [p f x]\n  (letfn [(until [p f x s]\n            (if (p x)\n              [x s]\n              (let [[x s] ((f x) s)]\n                (recur p f x s))))]\n    (fn [s] (until p f x s))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["p" "f" "x"]]}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "merge-cond-branches",
    "line" 101,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- merge-cond-branches [cond-branches]\n  (let [merger (fn [result cond-branch]\n                  (-> result\n                      (conj (first cond-branch))\n                      (conj (second cond-branch))))]\n    (reduce merger [] cond-branches)))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["cond-branches"]]}
   {"ns" "clojure.algo.monads",
    "name" "monad-transformer",
    "macro" true,
    "line" 556,
    "column" 1,
    "doc"
    "Define a monad transforer in terms of the monad operations and the base\n   monad. The argument which-m-plus chooses if m-zero and m-plus are taken\n   from the base monad or from the transformer.",
    "tag" nil,
    "source"
    "(defmacro monad-transformer\n  \"Define a monad transforer in terms of the monad operations and the base\n   monad. The argument which-m-plus chooses if m-zero and m-plus are taken\n   from the base monad or from the transformer.\"\n  [base which-m-plus operations]\n  `(let [which-m-plus# (cond (= ~which-m-plus :m-plus-default)\n                               (if (= ::undefined (with-monad ~base ~'m-plus))\n                                 :m-plus-from-transformer\n                                 :m-plus-from-base)\n                             (or (= ~which-m-plus :m-plus-from-base)\n                                 (= ~which-m-plus :m-plus-from-transformer))\n                               ~which-m-plus\n                             :else\n                               (throw (java.lang.IllegalArgumentException.\n                                       \"undefined m-plus choice\")))\n         combined-monad# (monad ~operations)]\n    (if (= which-m-plus# :m-plus-from-base)\n      (assoc combined-monad#\n        :m-zero (with-monad ~base ~'m-zero)\n        :m-plus (with-monad ~base ~'m-plus))\n      combined-monad#)))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["base" "which-m-plus" "operations"]]}
   {"arglists"
    [["m-bind" "m-result" "m-zero" "m-plus" "f" "mvs"]
     ["m-bind" "m-result" "m-zero" "m-plus" "f" "val" "mvs"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-reduce+m",
    "column" 1,
    "line" 287,
    "source"
    "(defmonadfn m-reduce\n  \"Return the reduction of (m-lift 2 f) over the list of monadic values mvs\n   with initial value (m-result val).\"\n  ([f mvs]\n   (if (empty? mvs)\n     (m-result (f))\n     (let [m-f (m-lift 2 f)]\n       (reduce m-f mvs))))\n  ([f val mvs]\n   (let [m-f    (m-lift 2 f)\n         m-val  (m-result val)]\n     (reduce m-f m-val mvs))))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-reduce",
    "line" 287,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "Return the reduction of (m-lift 2 f) over the list of monadic values mvs\n   with initial value (m-result val).",
    "tag" nil,
    "source"
    "(defmonadfn m-reduce\n  \"Return the reduction of (m-lift 2 f) over the list of monadic values mvs\n   with initial value (m-result val).\"\n  ([f mvs]\n   (if (empty? mvs)\n     (m-result (f))\n     (let [m-f (m-lift 2 f)]\n       (reduce m-f mvs))))\n  ([f val mvs]\n   (let [m-f    (m-lift 2 f)\n         m-val  (m-result val)]\n     (reduce m-f m-val mvs))))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-plus",
    "line" 237,
    "column" 1,
    "symbol-macro" true,
    "tag" nil,
    "source" "(defsymbolmacro m-plus m-plus)",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"arglists" [["mv"]],
    "ns" "clojure.algo.monads",
    "name" "listen",
    "column" 1,
    "line" 514,
    "source" "(defn listen [mv]\n  (let [[v a] mv] [[v a] a]))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "writer-monad-protocol",
    "line" 470,
    "column" 1,
    "doc" "Accumulation of values into containers",
    "tag" nil,
    "source"
    "(defprotocol writer-monad-protocol\n  \"Accumulation of values into containers\"\n  (writer-m-add [container value]\n  \"add value to container, return new container\")\n  (writer-m-combine [container1 container2]\n  \"combine two containers, return new container\"))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "writer-m",
    "line" 495,
    "column" 1,
    "doc"
    "Monad describing computations that accumulate data on the side, e.g. for\n   logging. The monadic values have the structure [value log]. Any of the\n   accumulators from clojure.contrib.accumulators can be used for storing the\n   log data. Its empty value is passed as a parameter.",
    "tag" nil,
    "source"
    "(defn writer-m\n  \"Monad describing computations that accumulate data on the side, e.g. for\n   logging. The monadic values have the structure [value log]. Any of the\n   accumulators from clojure.contrib.accumulators can be used for storing the\n   log data. Its empty value is passed as a parameter.\"\n  [empty-accumulator]\n  (monad\n   [m-result  (fn m-result-writer [v]\n                [v empty-accumulator])\n    m-bind    (fn m-bind-writer [mv f]\n                (let [[v1 a1] mv\n                      [v2 a2] (f v1)]\n                  [v2 (writer-m-combine a1 a2)]))\n    ]))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["empty-accumulator"]]}
   {"ns" "clojure.algo.monads",
    "name" "write",
    "line" 510,
    "column" 1,
    "symbol-macro" true,
    "tag" nil,
    "source"
    "(defmonadfn write [v]\n  (let [[_ a] (m-result nil)]\n    [nil (writer-m-add a v)]))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "defmonadfn",
    "macro" true,
    "line" 196,
    "column" 1,
    "doc"
    "Like defn, but for functions that use monad operations and are used inside\n   a with-monad block.",
    "tag" nil,
    "source"
    "(defmacro defmonadfn\n  \"Like defn, but for functions that use monad operations and are used inside\n   a with-monad block.\"\n  {:arglists '([name docstring? attr-map? args expr]\n               [name docstring? attr-map? (args expr) ...])}\n  [name & options]\n  (let [[name options]  (name-with-attributes name options)\n        fn-name (symbol (str *ns*) (format \"m+%s+m\" (str name)))\n        make-fn-body    (fn [args expr]\n                          (list (vec (concat ['m-bind 'm-result\n                                              'm-zero 'm-plus] args))\n                                (list `with-symbol-macros expr)))]\n    (if (list? (first options))\n      ; multiple arities\n      (let [arglists        (map first options)\n            exprs           (map second options)\n            ]\n        `(do\n           (defsymbolmacro ~name (partial ~fn-name ~'m-bind ~'m-result \n                                                   ~'m-zero ~'m-plus))\n           (defn ~fn-name ~@(map make-fn-body arglists exprs))))\n      ; single arity\n      (let [[args expr] options]\n        `(do\n           (defsymbolmacro ~name (partial ~fn-name ~'m-bind ~'m-result \n                                                   ~'m-zero ~'m-plus))\n           (defn ~fn-name ~@(make-fn-body args expr)))))))",
    "file" "clojure/algo/monads.clj",
    "arglists"
    [["name" "docstring?" "attr-map?" "args" "expr"]
     ["name" "docstring?" "attr-map?" ["args" "expr"] "..."]]}
   {"ns" "clojure.algo.monads",
    "name" "cont-m",
    "line" 522,
    "column" 1,
    "doc"
    "Monad describing computations in continuation-passing style. The monadic\n   values are functions that are called with a single argument representing\n   the continuation of the computation, to which they pass their result.",
    "tag" nil,
    "source"
    "(defmonad cont-m\n  \"Monad describing computations in continuation-passing style. The monadic\n   values are functions that are called with a single argument representing\n   the continuation of the computation, to which they pass their result.\"\n  [m-result   (fn m-result-cont [v]\n                (fn [c] (c v)))\n   m-bind     (fn m-bind-cont [mv f]\n                (fn [c]\n                  (mv (fn [v] ((f v) c)))))\n   ])",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "m-chain",
    "line" 276,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "Chains together monadic computation steps that are each functions\n   of one parameter. Each step is called with the result of the previous\n   step as its argument. (m-chain (step1 step2)) is equivalent to\n   (fn [x] (domonad [r1 (step1 x) r2 (step2 r1)] r2)).",
    "tag" nil,
    "source"
    "(defmonadfn m-chain\n  \"Chains together monadic computation steps that are each functions\n   of one parameter. Each step is called with the result of the previous\n   step as its argument. (m-chain (step1 step2)) is equivalent to\n   (fn [x] (domonad [r1 (step1 x) r2 (step2 r1)] r2)).\"\n  [steps]\n  (reduce (fn m-chain-link [chain-expr step]\n            (fn [v] (m-bind (chain-expr v) step)))\n          m-result\n          steps))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "set-val",
    "line" 440,
    "column" 1,
    "doc"
    "Return a state-monad function that assumes the state to be a map and\n   replaces the value associated with key by val. The old value is returned.",
    "tag" nil,
    "source"
    "(defn set-val\n  \"Return a state-monad function that assumes the state to be a map and\n   replaces the value associated with key by val. The old value is returned.\"\n  [key val]\n  (update-val key (fn [_] val)))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["key" "val"]]}
   {"ns" "clojure.algo.monads",
    "name" "sequence-m",
    "line" 368,
    "column" 1,
    "doc"
    "Monad describing multi-valued computations, i.e. computations\n    that can yield multiple values. Any object implementing the seq\n    protocol can be used as a monadic value.",
    "tag" nil,
    "source"
    "(defmonad sequence-m\n   \"Monad describing multi-valued computations, i.e. computations\n    that can yield multiple values. Any object implementing the seq\n    protocol can be used as a monadic value.\"\n   [m-result (fn m-result-sequence [v]\n               (list v))\n    m-bind   (fn m-bind-sequence [mv f]\n               (flatten* (map f mv)))\n    m-zero   (list)\n    m-plus   (fn m-plus-sequence [& mvs]\n               (flatten* mvs))\n    ])",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "update-state",
    "line" 406,
    "column" 1,
    "doc"
    "Return a state-monad function that replaces the current state by the\n   result of f applied to the current state and that returns the old state.",
    "tag" nil,
    "source"
    "(defn update-state\n  \"Return a state-monad function that replaces the current state by the\n   result of f applied to the current state and that returns the old state.\"\n  [f]\n  (fn [s] [s (f s)]))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["f"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-join",
    "line" 248,
    "column" 1,
    "symbol-macro" true,
    "doc"
    "Converts a monadic value containing a monadic value into a 'simple'\n   monadic value.",
    "tag" nil,
    "source"
    "(defmonadfn m-join\n  \"Converts a monadic value containing a monadic value into a 'simple'\n   monadic value.\"\n  [m]\n  (m-bind m identity))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "m"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-join+m",
    "column" 1,
    "line" 248,
    "source"
    "(defmonadfn m-join\n  \"Converts a monadic value containing a monadic value into a 'simple'\n   monadic value.\"\n  [m]\n  (m-bind m identity))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "with-state-field",
    "line" 446,
    "column" 1,
    "doc"
    "Returns a state-monad function that expects a map as its state and\n   runs statement (another state-monad function) on the state defined by\n   the map entry corresponding to key. The map entry is updated with the\n   new state returned by statement.",
    "tag" nil,
    "source"
    "(defn with-state-field\n  \"Returns a state-monad function that expects a map as its state and\n   runs statement (another state-monad function) on the state defined by\n   the map entry corresponding to key. The map entry is updated with the\n   new state returned by statement.\"\n  [key statement]\n  (fn [s]\n    (let [substate (get s key nil)\n          [result new-substate] (statement substate)\n          new-state (assoc s key new-substate)]\n      [result new-state])))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["key" "statement"]]}
   {"ns" "clojure.algo.monads",
    "name" "run-cont",
    "line" 533,
    "column" 1,
    "doc"
    "Execute the computation c in the cont monad and return its result.",
    "tag" nil,
    "source"
    "(defn run-cont\n  \"Execute the computation c in the cont monad and return its result.\"\n  [c]\n  (c identity))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["c"]]}
   {"ns" "clojure.algo.monads",
    "name" "fetch-val",
    "line" 424,
    "column" 1,
    "doc"
    "Return a state-monad function that assumes the state to be a map and\n   returns the value corresponding to the given key. The state is not modified.",
    "tag" nil,
    "source"
    "(defn fetch-val\n  \"Return a state-monad function that assumes the state to be a map and\n   returns the value corresponding to the given key. The state is not modified.\"\n  [key]\n  (fn [s] [(get s key ) s]))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["key"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-zero",
    "line" 236,
    "column" 1,
    "symbol-macro" true,
    "tag" nil,
    "source" "(defsymbolmacro m-zero m-zero)",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "state-m",
    "line" 395,
    "column" 1,
    "doc"
    "Monad describing stateful computations. The monadic values have the\n    structure (fn [old-state] [result new-state]).",
    "tag" nil,
    "source"
    "(defmonad state-m\n   \"Monad describing stateful computations. The monadic values have the\n    structure (fn [old-state] [result new-state]).\"\n   [m-result  (fn m-result-state [v]\n                (fn [s] [v s]))\n    m-bind    (fn m-bind-state [mv f]\n                (fn [s]\n                  (let [[v ss] (mv s)]\n                    ((f v) ss))))\n   ])",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "set-state",
    "line" 412,
    "column" 1,
    "doc"
    "Return a state-monad function that replaces the current state by s and\n   returns the previous state.",
    "tag" nil,
    "source"
    "(defn set-state\n  \"Return a state-monad function that replaces the current state by s and\n   returns the previous state.\"\n  [s]\n  (update-state (fn [_] s)))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "if-then-else-statement",
    "line" 82,
    "column" 1,
    "doc"
    "Process an :if :then :else steps when adding a new\n  monadic step to the mexrp.",
    "tag" nil,
    "source"
    "(defn- if-then-else-statement\n  \"Process an :if :then :else steps when adding a new\n  monadic step to the mexrp.\"\n  [[[_          else-mexpr]\n    [then-bform then-mexpr]\n    [if-bform   if-conditional]] mexpr continuation]\n    (cond\n      (and (identical? then-bform :then)\n           (identical? if-bform   :if))\n        `(if ~if-conditional\n          ~(reduce continuation\n                   mexpr\n                   (prepare-monadic-steps then-mexpr))\n          ~(reduce continuation\n                   mexpr\n                   (prepare-monadic-steps else-mexpr)))\n      :else\n       (throw (Exception. \"invalid :if without :then and :else\"))))",
    "file" "clojure/algo/monads.clj",
    "arglists"
    [[[["_" "else-mexpr"]
       ["then-bform" "then-mexpr"]
       ["if-bform" "if-conditional"]]
      "mexpr"
      "continuation"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.algo.monads",
    "doc" "add value to container, return new container",
    "arglists" [["container" "value"]],
    "name" "writer-m-add"}
   {"ns" "clojure.algo.monads",
    "name" "fetch-state",
    "line" 418,
    "column" 1,
    "doc"
    "Return a state-monad function that returns the current state and does not\n   modify it.",
    "tag" nil,
    "source"
    "(defn fetch-state\n  \"Return a state-monad function that returns the current state and does not\n   modify it.\"\n  []\n  (update-state identity))",
    "file" "clojure/algo/monads.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "add-monad-step",
    "line" 119,
    "column" 1,
    "doc"
    "Add a monad comprehension step before the already transformed\n   monad comprehension expression mexpr.",
    "tag" nil,
    "source"
    "(defn- add-monad-step\n  \"Add a monad comprehension step before the already transformed\n   monad comprehension expression mexpr.\"\n  [mexpr steps]\n  (let [[[bform expr :as step] & _] steps]\n    (cond\n      (identical? bform :when)  `(if ~expr ~mexpr ~'m-zero)\n      (identical? bform :let)   `(let ~expr ~mexpr)\n      (identical? bform :cond)  (cond-statement expr mexpr add-monad-step)\n      (identical? bform :then)  mexpr\n      ; ^ ignore :then step (processed on the :else step)\n      (identical? bform :if)    mexpr\n      ; ^ ignore :if step (processed on the :else step)\n      (identical? bform :else)\n        (if-then-else-statement steps mexpr add-monad-step)\n      :else\n        (list 'm-bind expr (list 'fn [bform] mexpr)))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["mexpr" "steps"]]}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "each3-steps",
    "line" 71,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- each3-steps [steps]\n  \"Transforms a list in a list of triples following the form:\n   [a b c] => [[a b c] [b c nil] [c nil nil]].\"\n  (let [n (count steps)]\n  (map vector (ensure-items n steps)\n              (ensure-items n (rest steps))\n              (ensure-items n (rest (rest steps))))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["steps"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-lift",
    "macro" true,
    "line" 239,
    "column" 1,
    "doc"
    "Converts a function f of n arguments into a function of n\n  monadic arguments returning a monadic value.",
    "tag" nil,
    "source"
    "(defmacro m-lift\n  \"Converts a function f of n arguments into a function of n\n  monadic arguments returning a monadic value.\"\n  [n f]\n  (let [expr (take n (repeatedly #(gensym \"x_\")))\n        vars (vec (take n (repeatedly #(gensym \"mv_\"))))\n        steps (vec (interleave expr vars))]\n    (list `fn vars (monad-expr steps (cons f expr)))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["n" "f"]]}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "f" "xs"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-map+m",
    "column" 1,
    "line" 270,
    "source"
    "(defmonadfn m-map\n  \"'Executes' the sequence of monadic values resulting from mapping\n   f onto the values xs. f must return a monadic value.\"\n  [f xs]\n  (m-seq (map f xs)))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"arglists" [["m-bind" "m-result" "m-zero" "m-plus" "p" "f" "x"]],
    "ns" "clojure.algo.monads",
    "name" "m+m-until+m",
    "column" 1,
    "line" 300,
    "source"
    "(defmonadfn m-until\n  \"While (p x) is false, replace x by the value returned by the\n   monadic computation (f x). Return (m-result x) for the first\n   x for which (p x) is true.\"\n  [p f x]\n  (if (p x)\n    (m-result x)\n    (domonad\n      [y (f x)\n       z (m-until p f y)]\n      z)))",
    "file" "clojure/algo/monads.clj",
    "tag" nil}
   {"ns" "clojure.algo.monads",
    "name" "sequence-t",
    "line" 610,
    "column" 1,
    "doc"
    "Monad transformer that transforms a monad m into a monad in which\n   the base values are sequences. The argument which-m-plus chooses\n   if m-zero and m-plus are inherited from the base monad\n   (use :m-plus-from-base) or adopt sequence-like\n   behaviour (use :m-plus-from-transformer). The default is :m-plus-from-base\n   if the base monad m has a definition for m-plus, and\n   :m-plus-from-transformer otherwise.",
    "tag" nil,
    "source"
    "(defn sequence-t\n  \"Monad transformer that transforms a monad m into a monad in which\n   the base values are sequences. The argument which-m-plus chooses\n   if m-zero and m-plus are inherited from the base monad\n   (use :m-plus-from-base) or adopt sequence-like\n   behaviour (use :m-plus-from-transformer). The default is :m-plus-from-base\n   if the base monad m has a definition for m-plus, and\n   :m-plus-from-transformer otherwise.\"\n  ([m] (sequence-t m :m-plus-default))\n  ([m which-m-plus]\n   (monad-transformer m which-m-plus\n     [m-result (with-monad m\n                 (fn m-result-sequence-t [v]\n                   (m-result (list v))))\n      m-bind   (with-monad m\n                 (fn m-bind-sequence-t [mv f]\n                   (m-bind mv\n                           (fn [xs]\n                             (m-fmap flatten*\n                                     (m-map f xs))))))\n      m-zero   (with-monad m (m-result (list)))\n      m-plus   (with-monad m\n                 (fn m-plus-sequence-t [& mvs]\n                   (m-reduce concat (list) mvs)))\n      ])))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["m"] ["m" "which-m-plus"]]}
   {"ns" "clojure.algo.monads",
    "name" "with-monad",
    "macro" true,
    "line" 161,
    "column" 1,
    "doc"
    "Evaluates an expression after replacing the keywords defining the\n   monad operations by the functions associated with these keywords\n   in the monad definition given by name.",
    "tag" nil,
    "source"
    "(defmacro with-monad\n  \"Evaluates an expression after replacing the keywords defining the\n   monad operations by the functions associated with these keywords\n   in the monad definition given by name.\"\n  [monad & exprs]\n  `(let [name#      ~monad\n         ~'m-bind   (:m-bind name#)\n         ~'m-result (:m-result name#)\n         ~'m-zero   (:m-zero name#)\n         ~'m-plus   (:m-plus name#)]\n     (with-symbol-macros ~@exprs)))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["monad" "&" "exprs"]]}
   {"ns" "clojure.algo.monads",
    "name" "m-bind",
    "line" 235,
    "column" 1,
    "symbol-macro" true,
    "tag" nil,
    "source" "(defsymbolmacro m-bind m-bind)",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "update-val",
    "line" 430,
    "column" 1,
    "doc"
    "Return a state-monad function that assumes the state to be a map and\n   replaces the value associated with the given key by the return value\n   of f applied to the old value. The old value is returned.",
    "tag" nil,
    "source"
    "(defn update-val\n  \"Return a state-monad function that assumes the state to be a map and\n   replaces the value associated with the given key by the return value\n   of f applied to the old value. The old value is returned.\"\n  [key f]\n  (fn [s]\n    (let [old-val (get s key)\n          new-s   (assoc s key (f old-val))]\n      [old-val new-s])))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["key" "f"]]}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "prepare-monadic-steps",
    "line" 79,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private prepare-monadic-steps\n     #(->> % (partition 2) reverse each3-steps))",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"ns" "clojure.algo.monads",
    "name" "cond-statement",
    "line" 108,
    "column" 1,
    "doc"
    "Process a :cond steps when adding a new monadic step to the mexrp.",
    "tag" nil,
    "source"
    "(defn cond-statement\n  \"Process a :cond steps when adding a new monadic step to the mexrp.\"\n  [expr mexpr continuation]\n  (let [cond-sexps (partition 2 expr)\n        result (for [[cond-sexp monadic-sexp] cond-sexps]\n                     (list cond-sexp\n                           (reduce continuation\n                                   mexpr\n                                   (prepare-monadic-steps monadic-sexp))))]\n      `(cond ~@(merge-cond-branches result))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["expr" "mexpr" "continuation"]]}
   {"ns" "clojure.algo.monads",
    "name" "domonad",
    "macro" true,
    "line" 173,
    "column" 1,
    "doc"
    "Monad comprehension. Takes the name of a monad, a vector of steps\n   given as binding-form/monadic-expression pairs, and a result value\n   specified by expr. The monadic-expression terms can use the binding\n   variables of the previous steps.\n   If the monad contains a definition of m-zero, the step list can also\n   contain conditions of the form :when p, where the predicate p can\n   contain the binding variables from all previous steps.\n   A clause of the form :let [binding-form expr ...], where the bindings\n   are given as a vector as for the use in let, establishes additional\n   bindings that can be used in the following steps.",
    "tag" nil,
    "source"
    "(defmacro domonad\n  \"Monad comprehension. Takes the name of a monad, a vector of steps\n   given as binding-form/monadic-expression pairs, and a result value\n   specified by expr. The monadic-expression terms can use the binding\n   variables of the previous steps.\n   If the monad contains a definition of m-zero, the step list can also\n   contain conditions of the form :when p, where the predicate p can\n   contain the binding variables from all previous steps.\n   A clause of the form :let [binding-form expr ...], where the bindings\n   are given as a vector as for the use in let, establishes additional\n   bindings that can be used in the following steps.\"\n  ([steps expr]\n    (monad-expr steps expr))\n  ([name steps expr]\n    (let [mexpr (monad-expr steps expr)]\n      `(with-monad ~name ~mexpr))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["steps" "expr"] ["name" "steps" "expr"]]}
   {"ns" "clojure.algo.monads",
    "name" "identity-m",
    "line" 345,
    "column" 1,
    "doc"
    "Monad describing plain computations. This monad does in fact nothing\n    at all. It is useful for testing, for combination with monad\n    transformers, and for code that is parameterized with a monad.",
    "tag" nil,
    "source"
    "(defmonad identity-m\n   \"Monad describing plain computations. This monad does in fact nothing\n    at all. It is useful for testing, for combination with monad\n    transformers, and for code that is parameterized with a monad.\"\n  [m-result identity\n   m-bind   (fn m-result-id [mv f]\n              (f mv))\n  ])",
    "file" "clojure/algo/monads.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.algo.monads",
    "name" "monad-expr",
    "line" 137,
    "column" 1,
    "doc"
    "Transforms a monad comprehension, consisting of a list of steps\n   and an expression defining the final value, into an expression\n   chaining together the steps using :bind and returning the final value\n   using :result. The steps are given as a vector of\n   binding-variable/monadic-expression pairs.",
    "tag" nil,
    "source"
    "(defn- monad-expr\n  \"Transforms a monad comprehension, consisting of a list of steps\n   and an expression defining the final value, into an expression\n   chaining together the steps using :bind and returning the final value\n   using :result. The steps are given as a vector of\n   binding-variable/monadic-expression pairs.\"\n  [steps expr]\n  (when (odd? (count steps))\n    (throw (Exception. \"Odd number of elements in monad comprehension steps\")))\n\n  (let [rsteps  (prepare-monadic-steps steps)\n        [[lr ls] & _] (first rsteps)]\n    (if (= lr expr)\n      ; Optimization: if the result expression is equal to the result\n      ; of the last computation step, we can eliminate an m-bind to\n      ; m-result.\n      (reduce add-monad-step\n        ls\n        (rest rsteps))\n      ; The general case.\n      (reduce add-monad-step\n        (list 'm-result expr)\n        rsteps))))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["steps" "expr"]]}
   {"ns" "clojure.algo.monads",
    "name" "state-t",
    "line" 637,
    "column" 1,
    "doc"
    "Monad transformer that transforms a monad m into a monad of stateful\n  computations that have the base monad type as their result.",
    "tag" nil,
    "source"
    "(defn state-t\n  \"Monad transformer that transforms a monad m into a monad of stateful\n  computations that have the base monad type as their result.\"\n  [m]\n  (monad [m-result (with-monad m\n                     (fn m-result-state-t [v]\n                       (fn [s]\n                         (m-result [v s]))))\n          m-bind   (with-monad m\n                     (fn m-bind-state-t [stm f]\n                       (fn [s]\n                         (m-bind (stm s)\n                                 (fn [[v ss]]\n                                   ((f v) ss))))))\n          m-zero   (with-monad m\n                     (if (= ::undefined m-zero)\n                       ::undefined\n                       (fn [s]\n                         m-zero)))\n          m-plus   (with-monad m\n                     (if (= ::undefined m-plus)\n                       ::undefined\n                       (fn [& stms]\n                         (fn [s]\n                           (apply m-plus (map #(% s) stms))))))\n          ]))",
    "file" "clojure/algo/monads.clj",
    "arglists" [["m"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.algo.monads",
    "doc" "combine two containers, return new container",
    "arglists" [["container1" "container2"]],
    "name" "writer-m-combine"}]},
 "description" "algo.monads 0.1.4",
 "version" "0.1.4",
 "name" "clojure.algo.monads",
 "group" "clojure.algo.monads"}
