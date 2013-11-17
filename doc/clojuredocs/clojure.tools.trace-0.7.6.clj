{"namespaces"
 {"clojure.tools.trace"
  [{"ns" "clojure.tools.trace",
    "name" "trace-form",
    "line" 298,
    "column" 1,
    "doc"
    "Trace the given form avoiding try catch when recur is present in the form.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} trace-form\n  \"Trace the given form avoiding try catch when recur is present in the form.\"\n  [form]\n  (if (recurs? form)\n    (trace-form* form)\n    `(try\n       ~(trace-form* form)\n       (catch Throwable e#\n         (throw (trace-compose-throwable e# (format \"  Form failed: %s\" (with-out-str (pprint '~form)))))))))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "tracer",
    "line" 71,
    "column" 1,
    "doc"
    "This function is called by trace. Prints to standard output, but\nmay be rebound to do anything you like. 'name' is optional.",
    "tag" nil,
    "source"
    "(defn ^{:private true} tracer\n  \"This function is called by trace. Prints to standard output, but\nmay be rebound to do anything you like. 'name' is optional.\"\n  [name value]\n  (println (str \"TRACE\" (when name (str \" \" name)) \": \" value)))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["name" "value"]]}
   {"ns" "clojure.tools.trace",
    "name" "trace-ns",
    "macro" true,
    "line" 384,
    "column" 1,
    "doc" "Trace all fns in the given name space.",
    "tag" nil,
    "source"
    "(defmacro trace-ns\n  \"Trace all fns in the given name space.\"\n  [ns]\n  `(trace-ns* ~ns))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["ns"]]}
   {"ns" "clojure.tools.trace",
    "name" "trace-forms",
    "macro" true,
    "line" 308,
    "column" 1,
    "doc"
    "Trace all the forms in the given body. Returns any underlying uncaught exceptions that may make the forms fail.",
    "tag" nil,
    "source"
    "(defmacro trace-forms\n  \"Trace all the forms in the given body. Returns any underlying uncaught exceptions that may make the forms fail.\"\n  [& body]\n  `(do\n     ~@(map trace-form body)))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["&" "body"]]}
   {"ns" "clojure.tools.trace",
    "name" "trace-ns*",
    "line" 371,
    "column" 1,
    "doc"
    "Replaces each function from the given namespace with a version wrapped\n  in a tracing call. Can be undone with untrace-ns. ns should be a namespace\n  object or a symbol.\n\n  No-op for clojure.core and clojure.tools.trace.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} trace-ns*\n  \"Replaces each function from the given namespace with a version wrapped\n  in a tracing call. Can be undone with untrace-ns. ns should be a namespace\n  object or a symbol.\n\n  No-op for clojure.core and clojure.tools.trace.\"\n  [ns]\n  (let [ns (the-ns ns)]\n    (when-not ('#{clojure.core clojure.tools.trace} (.name ns))\n      (let [ns-fns (->> ns ns-interns vals)]\n        (doseq [f ns-fns]\n          (trace-var* f))))))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["ns"]]}
   {"ns" "clojure.tools.trace",
    "name" "dotrace",
    "macro" true,
    "line" 116,
    "column" 1,
    "doc"
    "Given a sequence of function identifiers, evaluate the body\nexpressions in an environment in which the identifiers are bound to\nthe traced functions. Does not work on inlined functions,\nsuch as clojure.core/+",
    "tag" nil,
    "source"
    "(defmacro dotrace\n  \"Given a sequence of function identifiers, evaluate the body\nexpressions in an environment in which the identifiers are bound to\nthe traced functions. Does not work on inlined functions,\nsuch as clojure.core/+\"\n  [fnames & exprs]\n  `(binding [~@(interleave fnames\n                           (for [fname fnames]\n                             `(let [f# @(var ~fname)]\n                                (fn [& args#]\n                                  (trace-fn-call '~fname f# args#)))))]\n     ~@exprs))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["fnames" "&" "exprs"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "recurs?",
    "line" 182,
    "column" 1,
    "doc" "Test if the given form contains a recur call.",
    "tag" nil,
    "source"
    "(defn ^{:private true} recurs?\n  \"Test if the given form contains a recur call.\"\n  [form]\n  (if (and (or (list? form)\n               (seq? form))\n           (> (count form) 0))\n    (condp = (first form)\n      'recur true\n      'quote false\n      (some identity (map recurs? (rest form))))\n    false))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "trace-fn-body",
    "line" 156,
    "column" 1,
    "doc" "Trace the forms in a function body.",
    "tag" nil,
    "source"
    "(defn ^{:private true} trace-fn-body\n  \"Trace the forms in a function body.\"\n  [body]\n  `(~(first body) ~@(map trace-form (rest body))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["body"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "trace-bindings",
    "line" 132,
    "column" 1,
    "doc" "Trace the forms in the given binding list.",
    "tag" nil,
    "source"
    "(defn ^{:private true} trace-bindings\n  \"Trace the forms in the given binding list.\"\n  [bindings]\n  (vec (apply concat\n              (map (fn [[sym value]]\n                     `[~sym (trace-forms ~value)]) (partition 2 bindings)))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["bindings"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "ignored-form?",
    "line" 68,
    "column" 1,
    "doc" "Forms to ignore when tracing forms.",
    "tag" nil,
    "source"
    "(def ^{:doc \"Forms to ignore when tracing forms.\" :private true}\n      ignored-form? '#{def quote var try monitor-enter monitor-exit assert})",
    "file" "clojure/tools/trace.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "*trace-depth*",
    "line" 65,
    "column" 1,
    "doc" "Current stack depth of traced function calls.",
    "tag" nil,
    "source"
    "(def ^{:doc \"Current stack depth of traced function calls.\" :private true :dynamic true}\n      *trace-depth* 0)",
    "file" "clojure/tools/trace.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "trace-form*",
    "line" 194,
    "column" 1,
    "doc" "Trace the given form body except if it is to be ignored.",
    "tag" nil,
    "source"
    "(defn ^{:private true} trace-form*\n  \"Trace the given form body except if it is to be ignored.\"\n  [form]\n  (if (and (or (list? form)\n               (seq? form))\n           (> (count form) 0))\n    (if (ignored-form? (first form))\n      form\n      (let [sform (trace-special-form form)]\n        (if (= sform :default)\n          (let [mform (macroexpand-1 form)]\n            (if (= form mform)\n              (cons (first mform) (map trace-form (rest mform)))\n              (trace-form mform)))\n          sform)))\n    (trace-value form)))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.trace",
    "name" "untrace-var*",
    "line" 338,
    "column" 1,
    "doc"
    "Reverses the effect of trace-var / trace-vars / trace-ns for the\n  given Var, replacing the traced function with the original, untraced\n  version. No-op for non-traced Vars.\n\n  Argument types are the same as those for trace-var.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} untrace-var*\n  \"Reverses the effect of trace-var / trace-vars / trace-ns for the\n  given Var, replacing the traced function with the original, untraced\n  version. No-op for non-traced Vars.\n\n  Argument types are the same as those for trace-var.\"\n  ([ns s]\n     (untrace-var* (ns-resolve ns s)))\n  ([v]\n     (let [^clojure.lang.Var v (if (var? v) v (resolve v))\n           ns (.ns v)\n           s  (.sym v)\n           f  ((meta v) ::traced)]\n       (when f\n         (doto v\n           (alter-var-root (constantly ((meta v) ::traced)))\n           (alter-meta! dissoc ::traced))))))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["ns" "s"] ["v"]]}
   {"ns" "clojure.tools.trace",
    "name" "trace",
    "line" 77,
    "column" 1,
    "doc"
    "Sends name (optional) and value to the tracer function, then\nreturns value. May be wrapped around any expression without\naffecting the result.",
    "tag" nil,
    "source"
    "(defn trace\n  \"Sends name (optional) and value to the tracer function, then\nreturns value. May be wrapped around any expression without\naffecting the result.\"\n  ([value] (trace nil value))\n  ([name value]\n     (tracer name (pr-str value))\n     value))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["value"] ["name" "value"]]}
   {"ns" "clojure.tools.trace",
    "name" "traced?",
    "line" 403,
    "column" 1,
    "doc"
    "Returns true if the given var is currently traced, false otherwise",
    "tag" nil,
    "source"
    "(defn traced?\n  \"Returns true if the given var is currently traced, false otherwise\"\n  [v]\n  (let [^clojure.lang.Var v (if (var? v) v (resolve v))]\n    (-> v meta ::traced nil? not)))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["v"]]}
   {"source" "(defmulti trace-special-form (fn [form] (first form)))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.tools.trace",
    "name" "trace-special-form",
    "column" 1,
    "line" 130,
    "file" "clojure/tools/trace.clj"}
   {"ns" "clojure.tools.trace",
    "name" "untrace-vars",
    "macro" true,
    "line" 363,
    "column" 1,
    "doc"
    "Untrace each of the specified Vars.\n  Reverses the effect of trace-var / trace-vars / trace-ns for each\n  of the arguments, replacing the traced functions with the original,\n  untraced versions.",
    "tag" nil,
    "source"
    "(defmacro untrace-vars\n  \"Untrace each of the specified Vars.\n  Reverses the effect of trace-var / trace-vars / trace-ns for each\n  of the arguments, replacing the traced functions with the original,\n  untraced versions.\"\n  [& vs]\n `(do ~@(for [x vs] `(untrace-var* (quote ~x)))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["&" "vs"]]}
   {"ns" "clojure.tools.trace",
    "name" "deftrace",
    "macro" true,
    "line" 102,
    "column" 1,
    "doc"
    "Use in place of defn; traces each call/return of this fn, including\n   arguments. Nested calls to deftrace'd functions will print a\n   tree-like structure.\n   The first argument of the form definition can be a doc string",
    "tag" nil,
    "source"
    "(defmacro deftrace\n  \"Use in place of defn; traces each call/return of this fn, including\n   arguments. Nested calls to deftrace'd functions will print a\n   tree-like structure.\n   The first argument of the form definition can be a doc string\"\n  [name & definition]\n  (let [doc-string (if (string? (first definition)) (first definition) \"\")\n        fn-form  (if (string? (first definition)) (rest definition) definition)]\n    `(do\n       (def ~name)\n       (let [f# (fn ~@fn-form)]\n         (defn ~name ~doc-string [& args#]\n           (trace-fn-call '~name f# args#))))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["name" "&" "definition"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "trace-indent",
    "line" 86,
    "column" 1,
    "doc" "Returns an indentation string based on *trace-depth*",
    "tag" nil,
    "source"
    "(defn ^{:private true} trace-indent\n  \"Returns an indentation string based on *trace-depth*\"\n  []\n  (apply str (take *trace-depth* (repeat \"| \"))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.trace",
    "name" "trace-vars",
    "macro" true,
    "line" 356,
    "column" 1,
    "doc"
    "Trace each of the specified Vars.\n  The arguments may be Var objects or symbols to be resolved in the current\n  namespace.",
    "tag" nil,
    "source"
    "(defmacro trace-vars\n  \"Trace each of the specified Vars.\n  The arguments may be Var objects or symbols to be resolved in the current\n  namespace.\"\n  [& vs]\n  `(do ~@(for [x vs] `(trace-var* (quote ~x)))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["&" "vs"]]}
   {"ns" "clojure.tools.trace",
    "name" "untrace-ns",
    "macro" true,
    "line" 398,
    "column" 1,
    "doc" "Untrace all fns in the given name space.",
    "tag" nil,
    "source"
    "(defmacro untrace-ns\n  \"Untrace all fns in the given name space.\"\n  [ns]\n  `(untrace-ns* ~ns))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["ns"]]}
   {"ns" "clojure.tools.trace",
    "name" "trace-compose-throwable",
    "line" 288,
    "column" 1,
    "doc"
    "Re-create a new throwable with a composed message from the given throwable\n   and the message to be added. The exception stack trace is kept at a minimum.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} trace-compose-throwable \n  \"Re-create a new throwable with a composed message from the given throwable\n   and the message to be added. The exception stack trace is kept at a minimum.\"\n  [^Throwable throwable ^String message]\n  (let [previous-msg (or (.getMessage throwable) (format \": No message attached to throwable %s\" throwable))\n        composed-msg (str previous-msg (if-not (.endsWith previous-msg \"\\n\") \"\\n\") message (if-not (.endsWith message \"\\n\") \"\\n\"))\n        new-stack-trace (into-array java.lang.StackTraceElement [(aget (.getStackTrace throwable) 0)])\n        new-throwable (clone-throwable throwable new-stack-trace [composed-msg])]\n    new-throwable))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["throwable" "message"]]}
   {"ns" "clojure.tools.trace",
    "name" "ThrowableRecompose",
    "line" 211,
    "column" 1,
    "doc"
    "Protocol to isolate trace-form from convoluted throwables that \n   do not have a constructor with a single string argument.\n\n   clone-throwable attempts to clone a throwable with a human readable stack trace\n   and message :)\n   It must return a throwable of the same class. If not clonable, the original\n   throwable should be returned. At least this will preserve the original \n   throwable information.\n\n   Cloning should be non-obtrusive hence internal exceptions should be silently\n   swallowed and return the original throwable.",
    "tag" nil,
    "source"
    "(defprotocol ThrowableRecompose\n  \"Protocol to isolate trace-form from convoluted throwables that \n   do not have a constructor with a single string argument.\n\n   clone-throwable attempts to clone a throwable with a human readable stack trace\n   and message :)\n   It must return a throwable of the same class. If not clonable, the original\n   throwable should be returned. At least this will preserve the original \n   throwable information.\n\n   Cloning should be non-obtrusive hence internal exceptions should be silently\n   swallowed and return the original throwable.\"\n  (clone-throwable [this stack-trace args]))",
    "file" "clojure/tools/trace.clj",
    "arglists" nil}
   {"ns" "clojure.tools.trace",
    "name" "trace-var*",
    "line" 314,
    "column" 1,
    "doc"
    "If the specified Var holds an IFn and is not marked as a macro, its\n  contents is replaced with a version wrapped in a tracing call;\n  otherwise nothing happens. Can be undone with untrace-var.\n\n  In the unary case, v should be a Var object or a symbol to be\n  resolved in the current namespace.\n\n  In the binary case, ns should be a namespace object or a symbol\n  naming a namespace and s a symbol to be resolved in that namespace.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} trace-var*\n  \"If the specified Var holds an IFn and is not marked as a macro, its\n  contents is replaced with a version wrapped in a tracing call;\n  otherwise nothing happens. Can be undone with untrace-var.\n\n  In the unary case, v should be a Var object or a symbol to be\n  resolved in the current namespace.\n\n  In the binary case, ns should be a namespace object or a symbol\n  naming a namespace and s a symbol to be resolved in that namespace.\"\n  ([ns s]\n     (trace-var* (ns-resolve ns s)))\n  ([v]\n     (let [^clojure.lang.Var v (if (var? v) v (resolve v))\n           ns (.ns v)\n           s  (.sym v)]\n       (if (and (ifn? @v) (-> v meta :macro not))\n         (let [f @v\n               vname (symbol (str ns \"/\" s))]\n           (doto v\n             (alter-var-root #(fn tracing-wrapper [& args]\n                                (trace-fn-call vname % args)))\n             (alter-meta! assoc ::traced f)))))))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["ns" "s"] ["v"]]}
   {"ns" "clojure.tools.trace",
    "name" "traceable?",
    "line" 409,
    "column" 1,
    "doc"
    "Returns true if the given var can be traced, false otherwise",
    "tag" nil,
    "source"
    "(defn traceable?\n  \"Returns true if the given var can be traced, false otherwise\"\n  [v]\n  (let [^clojure.lang.Var v (if (var? v) v (resolve v))]\n    (and (ifn? @v) (-> v meta :macro not))))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["v"]]}
   {"private" true,
    "ns" "clojure.tools.trace",
    "name" "trace-value",
    "line" 173,
    "column" 1,
    "doc"
    "Trace the given data structure by tracing individual values.",
    "tag" nil,
    "source"
    "(defn ^{:private true} trace-value\n  \"Trace the given data structure by tracing individual values.\"\n  [v]\n  (cond\n    (vector? v) `(vector ~@(map trace-form v))\n    (map? v) `(into {} ~(vec (map trace-value v)))\n    (set? v) `(into #{} ~(vec (map trace-form v)))\n    :else v))",
    "file" "clojure/tools/trace.clj",
    "arglists" [["v"]]}
   {"ns" "clojure.tools.trace",
    "name" "trace-fn-call",
    "line" 91,
    "column" 1,
    "doc"
    "Traces a single call to a function f with args. 'name' is the\nsymbol name of the function.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} trace-fn-call\n  \"Traces a single call to a function f with args. 'name' is the\nsymbol name of the function.\"\n  [name f args]\n  (let [id (gensym \"t\")]\n    (tracer id (str (trace-indent) (pr-str (cons name args))))\n    (let [value (binding [*trace-depth* (inc *trace-depth*)]\n                  (apply f args))]\n      (tracer id (str (trace-indent) \"=> \" (pr-str value)))\n      value)))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["name" "f" "args"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.trace",
    "doc" nil,
    "arglists" [["this" "stack-trace" "args"]],
    "name" "clone-throwable"}
   {"ns" "clojure.tools.trace",
    "name" "untrace-ns*",
    "line" 389,
    "column" 1,
    "doc"
    "Reverses the effect of trace-var / trace-vars / trace-ns for the\n  Vars in the given namespace, replacing each traced function from the\n  given namespace with the original, untraced version.",
    "tag" nil,
    "source"
    "(defn ^{:skip-wiki true} untrace-ns*\n  \"Reverses the effect of trace-var / trace-vars / trace-ns for the\n  Vars in the given namespace, replacing each traced function from the\n  given namespace with the original, untraced version.\"\n  [ns]\n  (let [ns-fns (->> ns the-ns ns-interns vals)]\n    (doseq [f ns-fns]\n          (untrace-var* f))))",
    "file" "clojure/tools/trace.clj",
    "skip-wiki" true,
    "arglists" [["ns"]]}]},
 "description" "tools.trace 0.7.6",
 "version" "0.7.6",
 "name" "clojure.tools.trace",
 "group" "clojure.tools.trace"}
