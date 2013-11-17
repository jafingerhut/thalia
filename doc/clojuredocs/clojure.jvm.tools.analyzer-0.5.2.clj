{"namespaces"
 {"clojure.tools.analyzer.hygienic"
  [{"ns" "clojure.tools.analyzer.hygienic",
    "name" "emit-hy",
    "line" 17,
    "column" 1,
    "doc"
    "Emit an already-hygienic AST as a form.\n\n  eg. (-> (ast ...) ast-hy emit-hy)",
    "tag" nil,
    "source"
    "(defn emit-hy \n  \"Emit an already-hygienic AST as a form.\n\n  eg. (-> (ast ...) ast-hy emit-hy)\"\n  [expr]\n  (map->form expr hygienic-emit))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "arglists" [["expr"]]}
   {"arglists"
    [[[["keys" ["op" "init" "sym"]] ["as" "local-binding"]]
      "scope"
      "new-sym?"]],
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hygienic-local-binding",
    "column" 1,
    "line" 73,
    "source"
    "(defn hygienic-local-binding [{:keys [op init sym] :as local-binding} scope new-sym?]\n  {:pre [(= :local-binding op)]}\n  (let [hy-init (when init\n                  (hygienic-ast init scope))\n        hy-sym (if new-sym?\n                 (hygienic-sym scope sym)\n                 (scope sym))\n        _ (assert hy-sym (str \"Local \" sym \" not in scope.\"))]\n    (assoc local-binding\n           :init hy-init\n           hsym-key hy-sym)))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["lbs" "scope"]],
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hygienic-lbs",
    "column" 1,
    "line" 135,
    "source"
    "(defn hygienic-lbs [lbs scope]\n  (reduce (fn [[hy-lbs scope] {:keys [sym] :as local-binding}]\n            {:pre [(vector? hy-lbs)]}\n            (let [hy-local-binding (hygienic-local-binding local-binding scope true)\n                  hy-sym (hsym-key hy-local-binding)\n                  new-scope (add-scope scope sym hy-sym)]\n              [(conj hy-lbs hy-local-binding) new-scope]))\n          [[] scope] lbs))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hsym-key",
    "column" 1,
    "line" 24,
    "source" "(def hsym-key ::hygienic-sym)",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["name" "scope"]],
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hygienic-name",
    "column" 1,
    "line" 116,
    "source"
    "(defn hygienic-name [name scope]\n  (let [hy-name (when name\n                  (hygienic-sym scope name))\n        new-scope (if hy-name\n                    (add-scope scope name hy-name)\n                    scope)]\n    [hy-name new-scope]))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["scope" "sym" "hy-sym"]],
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "add-scope",
    "column" 1,
    "line" 86,
    "source"
    "(defn add-scope [scope sym hy-sym]\n  {:pre [sym hy-sym scope]}\n  (assoc scope sym hy-sym))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hygienic-emit",
    "column" 1,
    "line" 37,
    "source" "(def hygienic-emit ::hygienic-emit)",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"ns" "clojure.tools.analyzer.hygienic",
    "name" "ast-hy",
    "line" 10,
    "column" 1,
    "doc"
    "Perform hygienic transformation on an AST\n\n  eg. (-> (ast ...) ast-hy)",
    "tag" nil,
    "source"
    "(defn ast-hy \n  \"Perform hygienic transformation on an AST\n\n  eg. (-> (ast ...) ast-hy)\"\n  [expr]\n  (hygienic-ast expr {}))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "arglists" [["expr"]]}
   {"ns" "clojure.tools.analyzer.hygienic",
    "name" "macroexpand",
    "line" 27,
    "column" 1,
    "doc" "Fully macroexpand a form, using hygiene.",
    "tag" nil,
    "source"
    "(defn macroexpand \n  \"Fully macroexpand a form, using hygiene.\"\n  [f]\n  (-> f\n      ana/analyze-form\n      ast-hy\n      emit-hy))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "arglists" [["f"]]}
   {"arglists" [["scope" "sym"]],
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hygienic-sym",
    "column" 1,
    "line" 67,
    "source"
    "(defn hygienic-sym [scope sym]\n  ;only generate unique when shadowing\n  (if (scope sym)\n    (gensym sym)\n    sym))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" [["expr" "scope"]],
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hygienic-ast",
    "column" 1,
    "line" 59,
    "source"
    "(defn hygienic-ast [expr scope]\n  (assert expr)\n  (assert scope)\n  (fold-expr ::hygienic\n    {:expr-rec #(hygienic-ast % scope)\n     :locals {::scope scope}}\n    expr))",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.analyzer.hygienic",
    "name" "hname-key",
    "column" 1,
    "line" 25,
    "source" "(def hname-key ::hygienic-name)",
    "file" "clojure/tools/analyzer/hygienic.clj",
    "tag" nil}],
  "clojure.tools.analyzer"
  [{"ns" "clojure.tools.analyzer",
    "name" "uri-for-ns",
    "line" 864,
    "column" 1,
    "doc"
    "Returns a URI representing the namespace. Throws an\n  exception if URI not found.",
    "tag" nil,
    "source"
    "(defn uri-for-ns \n  \"Returns a URI representing the namespace. Throws an\n  exception if URI not found.\"\n  [ns-sym]\n  (let [source-path (munge-ns ns-sym) \n        uri (io/resource source-path)]\n    (when-not uri\n      (throw (Exception. (str \"No file found for namespace \" ns-sym))))\n    uri))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["ns-sym"]]}
   {"arglists" [["k"]],
    "ns" "clojure.tools.analyzer",
    "name" "keyword->Context",
    "column" 1,
    "line" 825,
    "source"
    "(defn keyword->Context [k]\n  (case k\n    :statement Compiler$C/STATEMENT\n    :expression Compiler$C/EXPRESSION\n    :return Compiler$C/RETURN\n    :eval Compiler$C/EVAL))",
    "file" "clojure/tools/analyzer.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "inherit-env",
    "line" 123,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- inherit-env [expr env]\n  (merge env\n         (when-let [line (-> expr :env :line)]\n           {:line line})\n         (when-let [column (-> expr :env :column)]\n           {:column column})\n         (when-let [source (-> expr :env :source)]\n           {:source source})))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["expr" "env"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "children",
    "line" 959,
    "column" 1,
    "doc"
    "Returns a lazy sequence of the immediate children of the expr in\n  order of evaluation, where defined.",
    "tag" nil,
    "source"
    "(defn children \n  \"Returns a lazy sequence of the immediate children of the expr in\n  order of evaluation, where defined.\"\n  [expr]\n  (util/children expr))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["expr"]]}
   {"arglists" [["form"] ["form" "opt"]],
    "ns" "clojure.tools.analyzer",
    "name" "analyze-form",
    "column" 1,
    "line" 35,
    "source"
    "(defn analyze-form \n  ([form] (analyze-form form {}))\n  ([form opt] (analyze-form-in-ns (ns-name *ns*) form opt)))",
    "file" "clojure/tools/analyzer.clj",
    "tag" nil}
   {"ns" "clojure.tools.analyzer",
    "name" "forms-seq",
    "line" 849,
    "column" 1,
    "doc" "Lazy seq of forms in a Clojure or ClojureScript file.",
    "tag" nil,
    "source"
    "(defn forms-seq\n  \"Lazy seq of forms in a Clojure or ClojureScript file.\"\n  [^java.io.PushbackReader rdr]\n  (let [eof (reify)]\n    (lazy-seq\n      (let [form (read rdr nil eof)]\n        (when-not (identical? form eof)\n          (lazy-seq (cons form (forms-seq rdr))))))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["rdr"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.analyzer",
    "doc"
    "Recursively converts the output of the Compiler's analysis to a map. Takes\n    a map of options:\n    - :children\n      when true, include a :children key with all child expressions of each node\n    - :java-obj\n      when true, include a :java-obj key with the node's corresponding Java object",
    "arglists" [["aobj" "env" "opt"]],
    "name" "analysis->map"}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "env-location",
    "line" 117,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- env-location [env expr]\n  (merge env\n         (when-line-map expr)\n         (when-column-map expr)\n         (when-source-map expr)))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["env" "expr"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "field",
    "macro" true,
    "line" 63,
    "column" 1,
    "doc"
    "Call a private field, must be known at compile time. Throws an error\n  if field is already publicly accessible.",
    "tag" nil,
    "source"
    "(defmacro field \n  \"Call a private field, must be known at compile time. Throws an error\n  if field is already publicly accessible.\"\n  ([class-obj field] `(field ~class-obj ~field nil))\n  ([class-obj field obj]\n   (let [{class-flags :flags :keys [members]} (reflect/reflect (resolve class-obj))\n         {field-flags :flags} (some #(and (= (:name %) field) %) members)]\n     (assert field-flags\n             (str \"Class \" (resolve class-obj) \" does not have field \" field))\n     (assert (not (and (:public class-flags)\n                       (:public field-flags)))\n             (str \"Class \" (resolve class-obj) \" and field \" field \" is already public\")))\n   `(field-accessor ~class-obj '~field ~obj)))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["class-obj" "field"] ["class-obj" "field" "obj"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "analyze-file",
    "line" 906,
    "column" 1,
    "doc"
    "Takes a file path and optionally a pushback reader.\n  Returns a vector of maps representing the ASTs of the forms\n  in the target file.\n\n  Options:\n  - :reader  a pushback reader to use to read the namespace forms\n  - :opt     a map of analyzer options\n    - :children\n      when true, include a :children key with all child expressions of each node\n    - :java-obj\n      when true, include a :java-obj key with the node's corresponding Java object\n\n  eg. (analyze-file \"my/ns.clj\")",
    "tag" nil,
    "source"
    "(defn analyze-file\n  \"Takes a file path and optionally a pushback reader.\n  Returns a vector of maps representing the ASTs of the forms\n  in the target file.\n\n  Options:\n  - :reader  a pushback reader to use to read the namespace forms\n  - :opt     a map of analyzer options\n    - :children\n      when true, include a :children key with all child expressions of each node\n    - :java-obj\n      when true, include a :java-obj key with the node's corresponding Java object\n\n  eg. (analyze-file \\\"my/ns.clj\\\")\"\n  [source-path & {:keys [reader opt] \n                  :or {reader (LineNumberingPushbackReader. (io/reader (io/resource source-path)))}}]\n  (let [eof (reify)\n        ^LineNumberingPushbackReader \n        pushback-reader (if (instance? LineNumberingPushbackReader reader)\n                          reader\n                          (LineNumberingPushbackReader. reader))]\n    (with-bindings (analyzer-bindings source-path pushback-reader)\n      (loop [form (read pushback-reader nil eof)\n             out []]\n        (if (identical? form eof)\n          out\n          (let [env {:ns {:name (ns-name *ns*)}\n                     :source-path source-path\n                     :locals {}}\n                expr-ast (Compiler/analyze (keyword->Context :eval) form)\n                m (analysis->map expr-ast env opt)\n                _ (method-accessor Compiler$Expr 'eval expr-ast [])]\n            (recur (read pushback-reader nil eof) (conj out m))))))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists"
    [["source-path"
      "&"
      [["keys" ["reader" "opt"]]
       ["or"
        [["reader"
          ["LineNumberingPushbackReader."
           ["io/reader" ["io/resource" "source-path"]]]]]]]]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "analyze*",
    "line" 832,
    "column" 1,
    "doc"
    "Must be called after binding the appropriate Compiler and RT dynamic Vars.",
    "tag" nil,
    "source"
    "(defn- analyze*\n  \"Must be called after binding the appropriate Compiler and RT dynamic Vars.\"\n  ([env form] (analyze* env form {}))\n  ([env form opt]\n   (let [context (keyword->Context (:context env))\n         expr-ast (try\n                    (Compiler/analyze context form)\n                    (catch RuntimeException e\n                      (throw (repl/root-cause e))))\n         _ (method-accessor (class expr-ast) 'eval expr-ast [])]\n     (analysis->map expr-ast (merge-with conj (dissoc env :context) {:locals {}}) opt))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["env" "form"] ["env" "form" "opt"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "pb-reader-for-ns",
    "line" 874,
    "column" 1,
    "doc" "Returns a LineNumberingPushbackReader for namespace ns-sym",
    "tag" "clojure.lang.LineNumberingPushbackReader",
    "source"
    "(defn ^LineNumberingPushbackReader\n  pb-reader-for-ns\n  \"Returns a LineNumberingPushbackReader for namespace ns-sym\"\n  [ns-sym]\n  (let [uri (uri-for-ns ns-sym)]\n    (LineNumberingPushbackReader. (io/reader uri))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["ns-sym"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "AnalysisToMap",
    "line" 132,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol AnalysisToMap\n  (analysis->map [aobj env opt]\n    \"Recursively converts the output of the Compiler's analysis to a map. Takes\n    a map of options:\n    - :children\n      when true, include a :children key with all child expressions of each node\n    - :java-obj\n      when true, include a :java-obj key with the node's corresponding Java object\"))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" nil}
   {"arglists" [["nsym" "form"] ["nsym" "form" "opt"]],
    "ns" "clojure.tools.analyzer",
    "name" "analyze-form-in-ns",
    "column" 1,
    "line" 29,
    "source"
    "(defn analyze-form-in-ns \n  ([nsym form] (analyze-form-in-ns nsym form {}))\n  ([nsym form opt]\n   (analyze-one {:ns {:name nsym} :context :eval}\n                form opt)))",
    "file" "clojure/tools/analyzer.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "when-line-map",
    "line" 101,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- when-line-map [expr]\n  (let [^java.lang.reflect.Method\n        method (try (.getMethod (class expr) \"line\" (into-array Class []))\n                 (catch Exception e))\n        field (try (.getDeclaredField (class expr) \"line\")\n                (catch Exception e))]\n    (cond \n      method {:line (method-accessor (class expr) 'line expr [])}\n      field {:line (field-accessor (class expr) 'line expr)})))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["expr"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "Compiler-members",
    "line" 881,
    "column" 1,
    "tag" nil,
    "source"
    "(defonce ^:private Compiler-members (set (map :name (:members (reflect/type-reflect RT)))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "munge-ns",
    "line" 858,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:private munge-ns [ns-sym]\n  (-> (name ns-sym)\n      (string/replace \".\" \"/\")\n      (string/replace \"-\" \"_\")\n      (str \".clj\")))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["ns-sym"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "RT-members",
    "line" 882,
    "column" 1,
    "tag" nil,
    "source"
    "(defonce ^:private RT-members (set (map :name (:members (reflect/type-reflect RT)))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" nil}
   {"ns" "clojure.tools.analyzer",
    "name" "ast-in-ns",
    "macro" true,
    "line" 39,
    "column" 1,
    "doc"
    "Returns the abstract syntax tree representation of the given form,\n  evaluated in the given namespace",
    "tag" nil,
    "source"
    "(defmacro ast-in-ns\n  \"Returns the abstract syntax tree representation of the given form,\n  evaluated in the given namespace\"\n  ([nsym form] `(ast-in-ns ~nsym ~form {}))\n  ([nsym form opt]\n   `(analyze-form-in-ns '~nsym '~form ~opt)))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["nsym" "form"] ["nsym" "form" "opt"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "analyze-ns",
    "line" 940,
    "column" 1,
    "doc"
    "Takes a LineNumberingPushbackReader and a namespace symbol.\n  Returns a vector of maps, with keys :op, :env. If expressions\n  have children, will have :children entry.\n\n  Options:\n  - :reader  a pushback reader to use to read the namespace forms\n  - :opt     a map of analyzer options\n    - :children\n      when true, include a :children key with all child expressions of each node\n    - :java-obj\n      when true, include a :java-obj key with the node's corresponding Java object\n\n  eg. (analyze-ns 'my-ns :opt {:children true} :reader (pb-reader-for-ns 'my.ns))",
    "tag" nil,
    "source"
    "(defn analyze-ns\n  \"Takes a LineNumberingPushbackReader and a namespace symbol.\n  Returns a vector of maps, with keys :op, :env. If expressions\n  have children, will have :children entry.\n\n  Options:\n  - :reader  a pushback reader to use to read the namespace forms\n  - :opt     a map of analyzer options\n    - :children\n      when true, include a :children key with all child expressions of each node\n    - :java-obj\n      when true, include a :java-obj key with the node's corresponding Java object\n\n  eg. (analyze-ns 'my-ns :opt {:children true} :reader (pb-reader-for-ns 'my.ns))\"\n  [source-nsym & {:keys [reader opt] :or {reader (pb-reader-for-ns source-nsym)}}]\n  (let [source-path (munge-ns source-nsym)]\n    (analyze-file source-path :reader reader :opt opt)))",
    "file" "clojure/tools/analyzer.clj",
    "arglists"
    [["source-nsym"
      "&"
      [["keys" ["reader" "opt"]]
       ["or" [["reader" ["pb-reader-for-ns" "source-nsym"]]]]]]]}
   {"ns" "clojure.tools.analyzer",
    "name" "macroexpand",
    "line" 53,
    "column" 1,
    "doc" "Fully macroexpand a form.",
    "tag" nil,
    "source"
    "(defn macroexpand \n  \"Fully macroexpand a form.\"\n  [f]\n  (-> f\n      analyze-form\n      emit-form/emit-form))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["f"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "method-accessor",
    "line" 86,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- method-accessor [^Class class-obj method obj types & args]\n  (let [^java.lang.reflect.Method \n        method (.getMethod class-obj (name method) (into-array Class types))]\n    (.setAccessible method true)\n    (try \n      (.invoke method obj (object-array args))\n      (catch java.lang.reflect.InvocationTargetException e\n        (throw (repl/root-cause e))))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["class-obj" "method" "obj" "types" "&" "args"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "when-column-map",
    "line" 95,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- when-column-map [expr]\n  (let [field (try (.getDeclaredField (class expr) \"column\")\n                (catch Exception e))]\n    (when field\n      {:column (field-accessor (class expr) 'column expr)})))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["expr"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "literal-dispatch",
    "macro" true,
    "line" 143,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro literal-dispatch [disp-class op-keyword]\n  `(extend-protocol AnalysisToMap\n     ~disp-class\n     (~'analysis->map\n       [expr# env# opt#]\n       (let []\n         (merge\n           {:op ~op-keyword\n            :env env#\n            :val (.eval expr#)}\n           (when (:java-obj opt#)\n             {:Expr-obj expr#}))))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["disp-class" "op-keyword"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "analyze-one",
    "line" 844,
    "column" 1,
    "doc" "Analyze a single form",
    "tag" nil,
    "source"
    "(defn analyze-one\n  \"Analyze a single form\"\n  ([env form] (analyze-one env form {}))\n  ([env form opt] (analyze* env form opt)))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["env" "form"] ["env" "form" "opt"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "analyzer-bindings",
    "macro" true,
    "line" 884,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private analyzer-bindings [source-path pushback-reader]\n  `(merge\n     {Compiler/LOADER (RT/makeClassLoader)\n      Compiler/SOURCE_PATH (str ~source-path)\n      Compiler/SOURCE (str ~source-path)\n      Compiler/METHOD nil\n      Compiler/LOCAL_ENV nil\n      Compiler/LOOP_LOCALS nil\n      Compiler/NEXT_LOCAL_NUM 0\n      RT/CURRENT_NS @RT/CURRENT_NS\n      Compiler/LINE_BEFORE (.getLineNumber ~pushback-reader)\n      Compiler/LINE_AFTER (.getLineNumber ~pushback-reader)\n      RT/UNCHECKED_MATH @RT/UNCHECKED_MATH}\n     ~(when (RT-members 'WARN_ON_REFLECTION)\n        `{(field RT ~'WARN_ON_REFLECTION) @(field RT ~'WARN_ON_REFLECTION)})\n     ~(when (Compiler-members 'COLUMN_BEFORE)\n        `{Compiler/COLUMN_BEFORE (.getColumnNumber ~pushback-reader)})\n     ~(when (Compiler-members 'COLUMN_AFTER)\n        `{Compiler/COLUMN_AFTER (.getColumnNumber ~pushback-reader)})\n     ~(when (RT-members 'DATA_READERS)\n        `{RT/DATA_READERS @RT/DATA_READERS})))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["source-path" "pushback-reader"]]}
   {"ns" "clojure.tools.analyzer",
    "name" "ast",
    "macro" true,
    "line" 46,
    "column" 1,
    "doc"
    "Returns the abstract syntax tree representation of the given form,\n  evaluated in the current namespace",
    "tag" nil,
    "source"
    "(defmacro ast \n  \"Returns the abstract syntax tree representation of the given form,\n  evaluated in the current namespace\"\n  ([form] `(ast ~form {}))\n  ([form opt]\n   `(analyze-form '~form ~opt)))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["form"] ["form" "opt"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "field-accessor",
    "line" 77,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- field-accessor [^Class class-obj field obj]\n  (let [^java.lang.reflect.Field \n        field (.getDeclaredField class-obj (name field))]\n    (.setAccessible field true)\n    (let [ret (.get field obj)]\n      (if (instance? Boolean ret)\n        (boolean ret)\n        ret))))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["class-obj" "field" "obj"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer",
    "name" "when-source-map",
    "line" 111,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- when-source-map [expr]\n  (let [field (try (.getDeclaredField (class expr) \"source\")\n                (catch Exception e))]\n    (when field\n      {:source (field-accessor (class expr) 'source expr)})))",
    "file" "clojure/tools/analyzer.clj",
    "arglists" [["expr"]]}],
  "clojure.tools.analyzer.examples.reflection"
  [{"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.reflection",
    "name" "check-instance-method",
    "column" 1,
    "line" 13,
    "source"
    "(defn check-instance-method [exp]\n  (when (not (:method exp))\n    (println \"WARNING: Unresolved instance method\" (:method-name exp) (:class exp) (-> exp :env :ns :name))))",
    "file" "clojure/tools/analyzer/examples/reflection.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.reflection",
    "name" "check-static-field",
    "column" 1,
    "line" 17,
    "source"
    "(defn check-static-field [exp]\n  (when (not (:field exp))\n    (println \"WARNING: Unresolved static field\" (:field-name exp) (:class exp) (-> exp :env :ns :name))))",
    "file" "clojure/tools/analyzer/examples/reflection.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.reflection",
    "name" "check-new",
    "column" 1,
    "line" 5,
    "source"
    "(defn check-new [exp]\n  (when (not (:ctor exp))\n    (println \"WARNING: Unresolved constructor\" (:class exp) (-> exp :env :ns :name))))",
    "file" "clojure/tools/analyzer/examples/reflection.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.reflection",
    "name" "check-for-reflection",
    "column" 1,
    "line" 26,
    "source"
    "(defn check-for-reflection [exp]\n  (condp = (:op exp)\n    :new (check-new exp)\n    :static-method (check-static-method exp)\n    :instance-method (check-instance-method exp)\n    :static-field (check-static-field exp)\n    :instance-field (check-instance-field exp)\n    nil)\n\n  (doseq [c (analyze/children exp)]\n    (check-for-reflection c)))",
    "file" "clojure/tools/analyzer/examples/reflection.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.reflection",
    "name" "check-instance-field",
    "column" 1,
    "line" 21,
    "source"
    "(defn check-instance-field [exp]\n  (when (not (:field exp))\n    (println \"WARNING: Unresolved instance field\" (:field-name exp) (:class exp) (-> exp :env :ns :name))))",
    "file" "clojure/tools/analyzer/examples/reflection.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.reflection",
    "name" "check-static-method",
    "column" 1,
    "line" 9,
    "source"
    "(defn check-static-method [exp]\n  (when (not (:method exp))\n    (println \"WARNING: Unresolved static method\" (:method-name exp) (:class exp) (-> exp :env :ns :name))))",
    "file" "clojure/tools/analyzer/examples/reflection.clj",
    "tag" nil}],
  "clojure.tools.analyzer.examples.dynvars"
  [{"arglists" [["sym"]],
    "ns" "clojure.tools.analyzer.examples.dynvars",
    "name" "earmuffed?",
    "column" 1,
    "line" 4,
    "source"
    "(defn earmuffed? [sym]\n  (let [s (name sym)]\n    (and (< 2 (count s))\n         (.startsWith s \"*\")\n         (.endsWith s \"*\"))))",
    "file" "clojure/tools/analyzer/examples/dynvars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.dynvars",
    "name" "check-def",
    "column" 1,
    "line" 10,
    "source"
    "(defn check-def [expr]\n  (let [v (:var expr)\n        s (.sym v)]\n    (when (and (earmuffed? s)\n               (not (:is-dynamic expr)))\n      (println \"WARNING: Should\" v \"be marked dynamic?\"))))",
    "file" "clojure/tools/analyzer/examples/dynvars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.dynvars",
    "name" "find-and-check-defs",
    "column" 1,
    "line" 17,
    "source"
    "(defn find-and-check-defs [expr]\n  (when (= :def (:op expr))\n    (check-def expr))\n  (doseq [child-expr (analyze/children expr)]\n    (find-and-check-defs child-expr)))",
    "file" "clojure/tools/analyzer/examples/dynvars.clj",
    "tag" nil}],
  "clojure.tools.analyzer.examples.load-core" [],
  "clojure.tools.analyzer.fold"
  [{"arglists" [["a" "&" "_"]],
    "ns" "clojure.tools.analyzer.fold",
    "name" "return-first",
    "column" 1,
    "line" 26,
    "source" "(defn return-first [a & _] a)",
    "file" "clojure/tools/analyzer/fold.clj",
    "tag" nil}
   {"ns" "clojure.tools.analyzer.fold",
    "name" "add-fold-case",
    "macro" true,
    "line" 11,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro add-fold-case [mode op fld-fn]\n  `(defmethod fold-expr [~mode ~op]\n     [mode# options# expr#]\n     (let [~'[expr-rec]\n           (map #(or (% options#)\n                     (partial fold-expr mode# options#))\n                [:expr-rec])\n           ~'map-expr-rec #(if % (map ~'expr-rec %) %)\n           ~'if-expr-rec #(if % (~'expr-rec %) %)\n           fld-fn# ~fld-fn]\n       (fld-fn# expr# options#))))",
    "file" "clojure/tools/analyzer/fold.clj",
    "arglists" [["mode" "op" "fld-fn"]]}
   {"arglists" nil,
    "ns" "clojure.tools.analyzer.fold",
    "name" "fold-expr-default",
    "column" 1,
    "line" 3,
    "source" "(def fold-expr-default ::fold-expr)",
    "file" "clojure/tools/analyzer/fold.clj",
    "tag" nil}
   {"ns" "clojure.tools.analyzer.fold",
    "name" "add-default-fold-case",
    "macro" true,
    "line" 23,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro add-default-fold-case [ty fld-fn]\n  `(add-fold-case fold-expr-default ~ty ~fld-fn))",
    "file" "clojure/tools/analyzer/fold.clj",
    "arglists" [["ty" "fld-fn"]]}
   {"source"
    "(defmulti fold-expr (fn [mode options expr]\n                      [mode (:op expr)]))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.tools.analyzer.fold",
    "name" "fold-expr",
    "column" 1,
    "line" 8,
    "file" "clojure/tools/analyzer/fold.clj"}
   {"arglists" [["tag"]],
    "ns" "clojure.tools.analyzer.fold",
    "name" "derive-default-fold",
    "column" 1,
    "line" 5,
    "source"
    "(defn derive-default-fold [tag]\n  (derive tag fold-expr-default))",
    "file" "clojure/tools/analyzer/fold.clj",
    "tag" nil}],
  "clojure.tools.analyzer.examples.side-effect"
  [{"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.side-effect",
    "name" "warn-on-side-effect",
    "column" 1,
    "line" 12,
    "source"
    "(defn warn-on-side-effect [exp]\n  (when (= :set! (:op exp))\n    (binding [*out* *err*]\n      (println \"WARNING: Side effect in transaction\")))\n  (doseq [child-exp (analyze/children exp)]\n    (warn-on-side-effect child-exp)))",
    "file" "clojure/tools/analyzer/examples/side_effect.clj",
    "tag" nil}
   {"ns" "clojure.tools.analyzer.examples.side-effect",
    "name" "transaction-method",
    "line" 7,
    "column" 1,
    "doc" "dosync reduces to a call to this method",
    "tag" nil,
    "source"
    "(def transaction-method\n  \"dosync reduces to a call to this method\"\n  (let [membrs (-> (reflect/reflect clojure.lang.LockingTransaction) :members)]\n    (first (filter #(= 'runInTransaction (:name %)) membrs))))",
    "file" "clojure/tools/analyzer/examples/side_effect.clj",
    "arglists" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.side-effect",
    "name" "forbid-side-effects-in-transaction",
    "column" 1,
    "line" 19,
    "source"
    "(defn forbid-side-effects-in-transaction [exp]\n  (when (and (= :static-method (:op exp))\n             (= transaction-method (:method exp)))\n    (warn-on-side-effect (first (:args exp))))\n  (doseq [child-exp (analyze/children exp)]\n    (forbid-side-effects-in-transaction child-exp)))",
    "file" "clojure/tools/analyzer/examples/side_effect.clj",
    "tag" nil}],
  "clojure.tools.analyzer.util"
  [{"ns" "clojure.tools.analyzer.util",
    "name" "expr-seq",
    "line" 30,
    "column" 1,
    "doc"
    "Given an expression, returns a lazy sequence of the expressions\n  followed by its children (in a depth first manner)",
    "tag" nil,
    "source"
    "(defn expr-seq\n  \"Given an expression, returns a lazy sequence of the expressions\n  followed by its children (in a depth first manner)\"\n  [expr]\n  (tree-seq children\n            children\n            expr))",
    "file" "clojure/tools/analyzer/util.clj",
    "arglists" [["expr"]]}
   {"ns" "clojure.tools.analyzer.util",
    "name" "print-expr",
    "line" 24,
    "column" 1,
    "doc"
    "Pretty-prints expr, excluding supplied keys.\n  Example: (print-expr expr :children :env)",
    "tag" nil,
    "source"
    "(defn print-expr\n  \"Pretty-prints expr, excluding supplied keys.\n  Example: (print-expr expr :children :env)\"\n  [expr & exclusions]\n  (pp/pprint (apply dissoc-rec expr exclusions)))",
    "file" "clojure/tools/analyzer/util.clj",
    "arglists" [["expr" "&" "exclusions"]]}
   {"ns" "clojure.tools.analyzer.util",
    "name" "children",
    "line" 4,
    "column" 1,
    "doc"
    "Returns a lazy sequence of the immediate children of the expr in\n  order of evaluation, where defined.",
    "tag" nil,
    "source"
    "(defn children \n  \"Returns a lazy sequence of the immediate children of the expr in\n  order of evaluation, where defined.\"\n  [expr]\n  (for [[path {:keys [exprs?]}] (:children expr)\n        :let [in (get-in expr path)]\n        child-expr (if exprs?\n                     in\n                     [in])]\n    child-expr))",
    "file" "clojure/tools/analyzer/util.clj",
    "arglists" [["expr"]]}
   {"private" true,
    "ns" "clojure.tools.analyzer.util",
    "name" "dissoc-rec",
    "line" 15,
    "column" 1,
    "doc" "Return expr with the keys dissociated",
    "tag" nil,
    "source"
    "(defn- dissoc-rec \n  \"Return expr with the keys dissociated\"\n  [obj & keys]\n  (cond\n   (map? obj) (into {} (for [[key val] (apply dissoc obj keys)]\n                         [key (apply dissoc-rec val keys)]))\n   (sequential? obj) (map #(apply dissoc-rec % keys) obj)\n   :else obj))",
    "file" "clojure/tools/analyzer/util.clj",
    "arglists" [["obj" "&" "keys"]]}],
  "clojure.tools.analyzer.examples.docstring"
  [{"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.docstring",
    "name" "check-def",
    "column" 1,
    "line" 6,
    "source"
    "(defn check-def [exp]\n  (when (= :fn-expr (-> exp :init :op))\n    (doseq [method (-> exp :init :methods)]\n      (let [body (:body method)]\n        (when (and (= :do (:op body))\n                   (< 1 (count (-> body :exprs))))\n          (let [first-exp (-> body :exprs first)]\n            (when (= :string (:op first-exp))\n              (binding [*out* *err*]\n                (println \"WARNING: Suspicious string, possibly misplaced docstring,\" (-> exp :var))))))))))",
    "file" "clojure/tools/analyzer/examples/docstring.clj",
    "tag" nil}
   {"arglists" [["exp"]],
    "ns" "clojure.tools.analyzer.examples.docstring",
    "name" "find-and-check-defs",
    "column" 1,
    "line" 17,
    "source"
    "(defn find-and-check-defs [exp]\n  (when (= :def (:op exp))\n    (check-def exp))\n  (doseq [child-exp (analyze/children exp)]\n    (find-and-check-defs child-exp)))",
    "file" "clojure/tools/analyzer/examples/docstring.clj",
    "tag" nil}],
  "clojure.tools.analyzer.examples.nsforms"
  [{"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.nsforms",
    "name" "find-and-analyze-use-forms",
    "column" 1,
    "line" 14,
    "source"
    "(defn find-and-analyze-use-forms [expr]\n  (when (use? expr)\n    (warn-on-naked-use expr))\n  (doseq [child-expr (analyze/children expr)]\n    (find-and-analyze-use-forms child-expr)))",
    "file" "clojure/tools/analyzer/examples/nsforms.clj",
    "tag" nil}
   {"arglists" [["use-expr"]],
    "ns" "clojure.tools.analyzer.examples.nsforms",
    "name" "warn-on-naked-use",
    "column" 1,
    "line" 4,
    "source"
    "(defn warn-on-naked-use [use-expr]\n  (doseq [s (map :val (:args use-expr))\n          :when (symbol? s)]\n    (println \"Warning: Naked use of\" (name s) \"in\" (-> use-expr :env :ns :name))))",
    "file" "clojure/tools/analyzer/examples/nsforms.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.nsforms",
    "name" "use?",
    "column" 1,
    "line" 9,
    "source"
    "(defn use? [expr]\n  (and (= :invoke (:op expr))\n       (= :var (-> expr :fexpr :op))\n       (= #'use (-> expr :fexpr :var))))",
    "file" "clojure/tools/analyzer/examples/nsforms.clj",
    "tag" nil}],
  "clojure.tools.analyzer.emit-form"
  [{"source"
    "(defmulti map->form (fn [expr mode]\n                      [(:op expr) mode]))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.tools.analyzer.emit-form",
    "name" "map->form",
    "column" 1,
    "line" 16,
    "file" "clojure/tools/analyzer/emit_form.clj"}
   {"private" true,
    "ns" "clojure.tools.analyzer.emit-form",
    "name" "var->symbol",
    "line" 44,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- var->symbol [^Var var]\n  (symbol (str (ns-name (.ns var))) (str (.sym var))))",
    "file" "clojure/tools/analyzer/emit_form.clj",
    "arglists" [["var"]]}
   {"arglists" nil,
    "ns" "clojure.tools.analyzer.emit-form",
    "name" "emit-default",
    "column" 1,
    "line" 4,
    "source" "(def emit-default ::emit-default)",
    "file" "clojure/tools/analyzer/emit_form.clj",
    "tag" nil}
   {"arglists" [["tag"]],
    "ns" "clojure.tools.analyzer.emit-form",
    "name" "derive-emit-default",
    "column" 1,
    "line" 6,
    "source"
    "(defn derive-emit-default [tag]\n  (derive tag emit-default))",
    "file" "clojure/tools/analyzer/emit_form.clj",
    "tag" nil}
   {"ns" "clojure.tools.analyzer.emit-form",
    "name" "emit-form",
    "line" 11,
    "column" 1,
    "doc" "Return the form represented by the given AST.",
    "tag" nil,
    "source"
    "(defn emit-form \n  \"Return the form represented by the given AST.\"\n  [expr]\n  (map->form expr ::emit-default))",
    "file" "clojure/tools/analyzer/emit_form.clj",
    "arglists" [["expr"]]}],
  "clojure.tools.analyzer.examples.privatevars"
  [{"private" true,
    "ns" "clojure.tools.analyzer.examples.privatevars",
    "name" "unused-fn",
    "line" 6,
    "column" 1,
    "tag" nil,
    "source" "(defn- unused-fn [] nil)",
    "file" "clojure/tools/analyzer/examples/privatevars.clj",
    "arglists" [[]]}
   {"arglists" [["exprs"]],
    "ns" "clojure.tools.analyzer.examples.privatevars",
    "name" "check-usage-of-private-vars",
    "column" 1,
    "line" 24,
    "source"
    "(defn check-usage-of-private-vars [exprs]\n  (let [v-count (apply merge-with + (map var-count exprs))]\n    (doseq [pvar (mapcat private-defs exprs)]\n      (when-not (get v-count pvar)\n        (println \"Private variable\" pvar \"is never used\")))))",
    "file" "clojure/tools/analyzer/examples/privatevars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.privatevars",
    "name" "var-count",
    "column" 1,
    "line" 18,
    "source"
    "(defn var-count [expr]\n  (if (= :var (:op expr))\n    {(:var expr) 1}\n    (apply merge-with +\n           (map var-count (analyze/children expr)))))",
    "file" "clojure/tools/analyzer/examples/privatevars.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.analyzer.examples.privatevars",
    "name" "unused-var",
    "line" 7,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private unused-var 0)",
    "file" "clojure/tools/analyzer/examples/privatevars.clj",
    "arglists" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.privatevars",
    "name" "defs",
    "column" 1,
    "line" 9,
    "source"
    "(defn defs [expr]\n  (apply concat\n         (when (= :def (:op expr)) [(:var expr)])\n         (map defs (analyze/children expr))))",
    "file" "clojure/tools/analyzer/examples/privatevars.clj",
    "tag" nil}
   {"arglists" [["expr"]],
    "ns" "clojure.tools.analyzer.examples.privatevars",
    "name" "private-defs",
    "column" 1,
    "line" 14,
    "source"
    "(defn private-defs [expr]\n  (filter #(:private (meta %))\n          (defs expr)))",
    "file" "clojure/tools/analyzer/examples/privatevars.clj",
    "tag" nil}],
  "cljs.tools.analyzer.fold"
  [{"arglists" [["a" "&" "_"]],
    "ns" "cljs.tools.analyzer.fold",
    "name" "return-first",
    "column" 1,
    "line" 26,
    "source" "(defn return-first [a & _] a)",
    "file" "cljs/tools/analyzer/fold.clj",
    "tag" nil}
   {"ns" "cljs.tools.analyzer.fold",
    "name" "add-fold-case",
    "macro" true,
    "line" 11,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro add-fold-case [mode op fld-fn]\n  `(defmethod fold-expr [~mode ~op]\n     [mode# options# expr#]\n     (let [~'[expr-rec]\n           (map #(or (% options#)\n                     (partial fold-expr mode# options#))\n                [:expr-rec])\n           ~'map-expr-rec #(if % (map ~'expr-rec %) %)\n           ~'if-expr-rec #(if % (~'expr-rec %) %)\n           fld-fn# ~fld-fn]\n       (fld-fn# expr# options#))))",
    "file" "cljs/tools/analyzer/fold.clj",
    "arglists" [["mode" "op" "fld-fn"]]}
   {"arglists" nil,
    "ns" "cljs.tools.analyzer.fold",
    "name" "fold-expr-default",
    "column" 1,
    "line" 3,
    "source" "(def fold-expr-default ::fold-expr)",
    "file" "cljs/tools/analyzer/fold.clj",
    "tag" nil}
   {"ns" "cljs.tools.analyzer.fold",
    "name" "add-default-fold-case",
    "macro" true,
    "line" 23,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro add-default-fold-case [ty fld-fn]\n  `(add-fold-case fold-expr-default ~ty ~fld-fn))",
    "file" "cljs/tools/analyzer/fold.clj",
    "arglists" [["ty" "fld-fn"]]}
   {"source"
    "(defmulti fold-expr (fn [mode options expr]\n                      [mode (:op expr)]))",
    "arglists" nil,
    "tag" nil,
    "ns" "cljs.tools.analyzer.fold",
    "name" "fold-expr",
    "column" 1,
    "line" 8,
    "file" "cljs/tools/analyzer/fold.clj"}
   {"arglists" [["tag"]],
    "ns" "cljs.tools.analyzer.fold",
    "name" "derive-default-fold",
    "column" 1,
    "line" 5,
    "source"
    "(defn derive-default-fold [tag]\n  (derive tag fold-expr-default))",
    "file" "cljs/tools/analyzer/fold.clj",
    "tag" nil}],
  "clojure.tools.analyzer.examples.tail-recursion"
  [{"private" true,
    "ns" "clojure.tools.analyzer.examples.tail-recursion",
    "name" "safe-mapcat",
    "line" 6,
    "column" 1,
    "doc"
    "Like `mapcat`, but works if the returned values aren't sequences.",
    "tag" nil,
    "source"
    "(defn- safe-mapcat\n  \"Like `mapcat`, but works if the returned values aren't sequences.\"\n  [f & colls]\n  (apply concat (map #(if (seq? %) % [%]) (apply map f colls))))",
    "file" "clojure/tools/analyzer/examples/tail_recursion.clj",
    "arglists" [["f" "&" "colls"]]}
   {"ns" "clojure.tools.analyzer.examples.tail-recursion",
    "name" "tail-recursive?",
    "line" 30,
    "column" 1,
    "doc"
    "Returns `true` if there is a call to the function being defined\n   in a tail position.  This does not necessarily mean that the tail call\n   can be replaced with `recur`, since that does not work with functions of\n   different arity, or across `try`.",
    "tag" nil,
    "source"
    "(defn tail-recursive?\n  \"Returns `true` if there is a call to the function being defined\n   in a tail position.  This does not necessarily mean that the tail call\n   can be replaced with `recur`, since that does not work with functions of\n   different arity, or across `try`.\"\n  [fn-tree]\n  (let [fn-name (or (-> fn-tree :name) (-> fn-tree :var))\n        tail-ops (find-tail-ops fn-tree)]\n    (boolean (when fn-name (some (partial = fn-name) tail-ops)))))",
    "file" "clojure/tools/analyzer/examples/tail_recursion.clj",
    "arglists" [["fn-tree"]]}
   {"ns" "clojure.tools.analyzer.examples.tail-recursion",
    "name" "find-tail-ops",
    "line" 13,
    "column" 1,
    "doc"
    "Returns a list of the function calls that are in tail position.",
    "tag" nil,
    "source"
    "(defn find-tail-ops\n  \"Returns a list of the function calls that are in tail position.\"\n  [tree]\n  (case (:op tree)\n    :def (safe-mapcat find-tail-ops (rest (analyze/children tree)))\n    :do (recur (last (analyze/children tree)))\n    :fn-expr (safe-mapcat find-tail-ops (:methods tree))\n    :fn-method (recur (:body tree))\n\n    :invoke\n    (or (-> tree :fexpr :local-binding :sym)\n        (-> tree :fexpr :var))\n\n    :let (recur (:body tree))\n    :if (map find-tail-ops [(:then tree) (:else tree)])\n    nil))",
    "file" "clojure/tools/analyzer/examples/tail_recursion.clj",
    "arglists" [["tree"]]}]},
 "description" "jvm.tools.analyzer 0.5.2",
 "version" "0.5.2",
 "name" "clojure.jvm.tools.analyzer",
 "group" "clojure.jvm.tools.analyzer"}
