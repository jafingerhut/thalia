{"namespaces"
 {"clojure.tools.macro"
  [{"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-reify",
    "line" 163,
    "column" 1,
    "doc" "Handle reify* forms.",
    "tag" nil,
    "source"
    "(defn- expand-reify\n  \"Handle reify* forms.\"\n  [[symbol interfaces & methods]]\n  (let [expanded-methods (map expand-method methods)]\n    (cons symbol (cons interfaces expanded-methods))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [[["symbol" "interfaces" "&" "methods"]]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-method",
    "line" 149,
    "column" 1,
    "doc" "Handle a method in a deftype* or reify* form.",
    "tag" nil,
    "source"
    "(defn- expand-method\n  \"Handle a method in a deftype* or reify* form.\"\n  [m]\n  (rest (expand-fn (cons 'fn* m))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["m"]]}
   {"ns" "clojure.tools.macro",
    "name" "macrolet",
    "macro" true,
    "line" 202,
    "column" 1,
    "doc"
    "Define local macros that are used in the expansion of exprs. The\n   syntax is the same as for letfn forms.",
    "tag" nil,
    "source"
    "(defmacro macrolet\n  \"Define local macros that are used in the expansion of exprs. The\n   syntax is the same as for letfn forms.\"\n  [fn-bindings & exprs]\n  (let [names      (map first fn-bindings)\n\tname-map   (into {} (map (fn [n] [(list 'quote n) n]) names))\n\tmacro-map  (eval `(letfn ~fn-bindings ~name-map))]\n    (binding [macro-fns     (merge macro-fns macro-map)\n\t      macro-symbols (apply dissoc macro-symbols names)]\n      `(do ~@(doall (map expand-all exprs))))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["fn-bindings" "&" "exprs"]]}
   {"ns" "clojure.tools.macro",
    "name" "name-with-attributes",
    "line" 273,
    "column" 1,
    "doc"
    "To be used in macro definitions.\n   Handles optional docstrings and attribute maps for a name to be defined\n   in a list of macro arguments. If the first macro argument is a string,\n   it is added as a docstring to name and removed from the macro argument\n   list. If afterwards the first macro argument is a map, its entries are\n   added to the name's metadata map and the map is removed from the\n   macro argument list. The return value is a vector containing the name\n   with its extended metadata map and the list of unprocessed macro\n   arguments.",
    "tag" nil,
    "source"
    "(defn name-with-attributes\n  \"To be used in macro definitions.\n   Handles optional docstrings and attribute maps for a name to be defined\n   in a list of macro arguments. If the first macro argument is a string,\n   it is added as a docstring to name and removed from the macro argument\n   list. If afterwards the first macro argument is a map, its entries are\n   added to the name's metadata map and the map is removed from the\n   macro argument list. The return value is a vector containing the name\n   with its extended metadata map and the list of unprocessed macro\n   arguments.\"\n  [name macro-args]\n  (let [[docstring macro-args] (if (string? (first macro-args))\n                                 [(first macro-args) (next macro-args)]\n                                 [nil macro-args])\n    [attr macro-args]          (if (map? (first macro-args))\n                                 [(first macro-args) (next macro-args)]\n                                 [{} macro-args])\n    attr                       (if docstring\n                                 (assoc attr :doc docstring)\n                                 attr)\n    attr                       (if (meta name)\n                                 (conj (meta name) attr)\n                                 attr)]\n    [(with-meta name attr) macro-args]))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["name" "macro-args"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-list",
    "line" 182,
    "column" 1,
    "doc" "Recursively expand a form that is a list or a cons.",
    "tag" nil,
    "source"
    "(defn- expand-list\n  \"Recursively expand a form that is a list or a cons.\"\n  [form]\n  (let [f (first form)]\n    (if (symbol? f)\n      (if (contains? special-forms f)\n\t((get special-form-handlers f expand-args) form)\n\t(expand-args form))\n      (doall (map expand-all form)))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "special-forms",
    "line" 31,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:private true} special-forms\n  (into #{} (keys clojure.lang.Compiler/specials)))",
    "file" "clojure/tools/macro.clj",
    "arglists" nil}
   {"ns" "clojure.tools.macro",
    "name" "mexpand-all",
    "line" 265,
    "column" 1,
    "doc" "Perform a full recursive macro expansion of a form.",
    "tag" nil,
    "source"
    "(defn mexpand-all\n  \"Perform a full recursive macro expansion of a form.\"\n  [form]\n  (binding [macro-fns {}\n\t    macro-symbols {}\n\t    protected-symbols #{}]\n    (expand-all form)))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand",
    "line" 89,
    "column" 1,
    "doc"
    "Perform repeated non-recursive macro expansion of form, until it no\n   longer changes.",
    "tag" nil,
    "source"
    "(defn- expand\n  \"Perform repeated non-recursive macro expansion of form, until it no\n   longer changes.\"\n  [form]\n  (let [ex (expand-1 form)]\n    (if (identical? ex form)\n      form\n      (recur ex))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.macro",
    "name" "deftemplate",
    "macro" true,
    "line" 237,
    "column" 1,
    "doc"
    "Define a macro that expands into forms after replacing the\n   symbols in params (a vector) by the corresponding parameters\n   given in the macro call.",
    "tag" nil,
    "source"
    "(defmacro deftemplate\n  \"Define a macro that expands into forms after replacing the\n   symbols in params (a vector) by the corresponding parameters\n   given in the macro call.\"\n  [name params & forms]\n  (let [param-map (for [p params] (list (list 'quote p) (gensym)))\n\ttemplate-params (vec (map second param-map))\n\tparam-map (vec (apply concat param-map))\n\texpansion (list 'list (list 'quote `symbol-macrolet) param-map\n\t\t\t(list 'quote (cons 'do forms)))]\n    `(defmacro ~name ~template-params ~expansion)))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["name" "params" "&" "forms"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-symbol",
    "line" 55,
    "column" 1,
    "doc" "Expand symbol macros",
    "tag" nil,
    "source"
    "(defn- expand-symbol\n  \"Expand symbol macros\"\n  [symbol]\n  (cond (protected? symbol)                   symbol\n\t(contains? macro-symbols symbol)     (get macro-symbols symbol)\n\t:else (let [v (resolve symbol)\n\t\t    m (meta v)]\n\t\t(if (:symbol-macro m)\n\t\t  (var-get v)\n\t\t  symbol))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["symbol"]]}
   {"ns" "clojure.tools.macro",
    "name" "mexpand-1",
    "line" 249,
    "column" 1,
    "doc"
    "Like clojure.core/macroexpand-1, but takes into account symbol macros.",
    "tag" nil,
    "source"
    "(defn mexpand-1\n  \"Like clojure.core/macroexpand-1, but takes into account symbol macros.\"\n  [form]\n  (binding [macro-fns {}\n\t    macro-symbols {}\n\t    protected-symbols #{}]\n    (expand-1 form)))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "macro-fns",
    "line" 40,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} macro-fns {})",
    "file" "clojure/tools/macro.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-with-bindings",
    "line" 117,
    "column" 1,
    "doc"
    "Handle let* and loop* forms. The symbols defined in them are protected\n   from symbol macro expansion, the definitions and the body expressions\n   are expanded recursively.",
    "tag" nil,
    "source"
    "(defn- expand-with-bindings\n  \"Handle let* and loop* forms. The symbols defined in them are protected\n   from symbol macro expansion, the definitions and the body expressions\n   are expanded recursively.\"\n  [form]\n  (let [f        (first form)\n\tbindings (partition 2 (second form))\n\texprs    (rest (rest form))\n\texpanded (expand-bindings bindings exprs)\n\tbindings (vec (apply concat (butlast expanded)))\n\texprs    (last expanded)]\n    (cons f (cons bindings exprs))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.macro",
    "name" "mexpand",
    "line" 257,
    "column" 1,
    "doc"
    "Like clojure.core/macroexpand, but takes into account symbol macros.",
    "tag" nil,
    "source"
    "(defn mexpand\n  \"Like clojure.core/macroexpand, but takes into account symbol macros.\"\n  [form]\n  (binding [macro-fns {}\n\t    macro-symbols {}\n\t    protected-symbols #{}]\n    (expand form)))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-deftype",
    "line" 154,
    "column" 1,
    "doc" "Handle deftype* forms.",
    "tag" nil,
    "source"
    "(defn- expand-deftype\n  \"Handle deftype* forms.\"\n  [[symbol typename classname fields implements interfaces & methods]]\n  (assert (= implements :implements))\n  (let [expanded-methods (map expand-method methods)]\n    (concat\n     (list symbol typename classname fields implements interfaces)\n     expanded-methods)))",
    "file" "clojure/tools/macro.clj",
    "arglists"
    [[["symbol"
       "typename"
       "classname"
       "fields"
       "implements"
       "interfaces"
       "&"
       "methods"]]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-bindings",
    "line" 108,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- expand-bindings\n  [bindings exprs]\n  (if (empty? bindings)\n    (list (doall (map expand-all exprs)))\n    (let [[[s b] & bindings] bindings]\n      (let [b (expand-all b)]\n\t(binding [protected-symbols (conj protected-symbols s)]\n\t  (doall (cons [s b] (expand-bindings bindings exprs))))))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["bindings" "exprs"]]}
   {"ns" "clojure.tools.macro",
    "name" "defsymbolmacro",
    "macro" true,
    "line" 223,
    "column" 1,
    "doc"
    "Define a symbol macro. Because symbol macros are not part of\n   Clojure's built-in macro expansion system, they can be used only\n   inside a with-symbol-macros form.",
    "tag" nil,
    "source"
    "(defmacro defsymbolmacro\n  \"Define a symbol macro. Because symbol macros are not part of\n   Clojure's built-in macro expansion system, they can be used only\n   inside a with-symbol-macros form.\"\n  [symbol expansion]\n  (let [meta-map (if (meta symbol) (meta symbol) {})\n\tmeta-map (assoc meta-map :symbol-macro true)]\n  `(def ~(with-meta symbol meta-map) (quote ~expansion))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["symbol" "expansion"]]}
   {"ns" "clojure.tools.macro",
    "name" "with-symbol-macros",
    "macro" true,
    "line" 232,
    "column" 1,
    "doc" "Fully expand exprs, including symbol macros.",
    "tag" nil,
    "source"
    "(defmacro with-symbol-macros\n  \"Fully expand exprs, including symbol macros.\"\n  [& exprs]\n  `(do ~@(doall (map expand-all exprs))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["&" "exprs"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-fn",
    "line" 136,
    "column" 1,
    "doc"
    "Handle fn* forms. The arguments are protected from symbol macro\n   expansion, the bodies are expanded recursively.",
    "tag" nil,
    "source"
    "(defn- expand-fn\n  \"Handle fn* forms. The arguments are protected from symbol macro\n   expansion, the bodies are expanded recursively.\"\n  [form]\n  (let [[f & bodies] form\n\tname         (when (symbol? (first bodies)) (first bodies))\n\tbodies       (if (symbol? (first bodies)) (rest bodies) bodies)\n\tbodies       (if (vector? (first bodies)) (list bodies) bodies)\n\tbodies       (doall (map expand-fn-body bodies))]\n    (if (nil? name)\n      (cons f bodies)\n      (cons f (cons name bodies)))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "protected-symbols",
    "line" 44,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:dynamic true :private true} protected-symbols #{})",
    "file" "clojure/tools/macro.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "macro-symbols",
    "line" 42,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} macro-symbols {})",
    "file" "clojure/tools/macro.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-args",
    "line" 100,
    "column" 1,
    "doc"
    "Recursively expand the arguments of form, leaving its first\n   n elements unchanged.",
    "tag" nil,
    "source"
    "(defn- expand-args\n  \"Recursively expand the arguments of form, leaving its first\n   n elements unchanged.\"\n  ([form]\n   (expand-args form 1))\n  ([form n]\n   (doall (concat (take n form) (map expand-all (drop n form))))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"] ["form" "n"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "special-form-handlers",
    "line" 171,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:private true} special-form-handlers\n  {'quote \t  identity\n   'var   \t  identity\n   'def   \t  #(expand-args % 2)\n   'new           #(expand-args % 2)\n   'let*          expand-with-bindings\n   'loop*         expand-with-bindings\n   'fn*           expand-fn\n   'deftype*      expand-deftype\n   'reify*        expand-reify})",
    "file" "clojure/tools/macro.clj",
    "arglists" nil}
   {"ns" "clojure.tools.macro",
    "name" "symbol-macrolet",
    "macro" true,
    "line" 213,
    "column" 1,
    "doc"
    "Define local symbol macros that are used in the expansion of exprs.\n   The syntax is the same as for let forms.",
    "tag" nil,
    "source"
    "(defmacro symbol-macrolet\n  \"Define local symbol macros that are used in the expansion of exprs.\n   The syntax is the same as for let forms.\"\n  [symbol-bindings & exprs]\n  (let [symbol-map (into {} (map vec (partition 2 symbol-bindings)))\n\tnames      (keys symbol-map)]\n    (binding [macro-fns     (apply dissoc macro-fns names)\n\t      macro-symbols (merge macro-symbols symbol-map)]\n      `(do ~@(doall (map expand-all exprs))))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["symbol-bindings" "&" "exprs"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-1",
    "line" 66,
    "column" 1,
    "doc" "Perform a single non-recursive macro expansion of form.",
    "tag" nil,
    "source"
    "(defn- expand-1\n  \"Perform a single non-recursive macro expansion of form.\"\n  [form]\n  (cond\n    (seq? form)\n      (let [f (first form)]\n        (cond (contains? special-forms f) form\n\t      (contains? macro-fns f)     (apply (get macro-fns f) (rest form))\n\t      (symbol? f)  (cond\n\t\t\t    (protected? f) form\n\t\t\t    ; macroexpand-1 fails if f names a class\n\t\t\t    (class? (ns-resolve *ns* f)) form\n\t\t\t    :else    (let [exp (expand-symbol f)]\n\t\t\t\t       (if (= exp f)\n\t\t\t\t\t (clojure.core/macroexpand-1 form)\n\t\t\t\t\t (cons exp (rest form)))))\n\t      ; handle defmacro macros and Java method special forms\n\t      :else (clojure.core/macroexpand-1 form)))\n    (symbol? form)\n      (expand-symbol form)\n     :else\n       form))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-fn-body",
    "line" 130,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- expand-fn-body\n  [[args & exprs]]\n  (binding [protected-symbols (reduce conj protected-symbols\n\t\t\t\t     (filter #(not (= % '&)) args))]\n    (cons args (doall (map expand-all exprs)))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [[["args" "&" "exprs"]]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "expand-all",
    "line" 192,
    "column" 1,
    "doc" "Expand a form recursively.",
    "tag" nil,
    "source"
    "(defn- expand-all\n  \"Expand a form recursively.\"\n  [form]\n  (let [exp (expand form)]\n    (cond (symbol? exp) exp\n\t  (seq? exp) (expand-list exp)\n\t  (vector? exp) (into [] (map expand-all exp))\n\t  (map? exp) (into {} (map expand-all (seq exp)))\n\t  :else exp)))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.macro",
    "name" "protected?",
    "line" 46,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- protected?\n  [symbol]\n  \"Return true if symbol is a reserved symbol (starting or ending with a dot)\n   or a symbol bound in a surrounding let form or as a function argument.\"\n  (or (contains? protected-symbols symbol)\n      (let [s (str symbol)]\n\t(or (= \".\" (subs s 0 1))\n\t    (= \".\" (subs s (dec (count s))))))))",
    "file" "clojure/tools/macro.clj",
    "arglists" [["symbol"]]}]},
 "description" "tools.macro 0.1.5",
 "version" "0.1.5",
 "name" "clojure.tools.macro",
 "group" "clojure.tools.macro"}
