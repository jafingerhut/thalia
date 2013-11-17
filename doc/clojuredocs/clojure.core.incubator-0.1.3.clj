{"namespaces"
 {"clojure.core.strint"
  [{"ns" "clojure.core.strint",
    "name" "<<",
    "macro" true,
    "line" 49,
    "column" 1,
    "doc"
    "Accepts one or more strings; emits a `str` invocation that concatenates\nthe string data and evaluated expressions contained within that argument.\nEvaluation is controlled using ~{} and ~() forms. The former is used for\nsimple value replacement using clojure.core/str; the latter can be used to\nembed the results of arbitrary function invocation into the produced string.\n\nExamples:\n  user=> (def v 30.5)\n  #'user/v\n  user=> (<< \"This trial required ~{v}ml of solution.\")\n  \"This trial required 30.5ml of solution.\"\n  user=> (<< \"There are ~(int v) days in November.\")\n  \"There are 30 days in November.\"\n  user=> (def m {:a [1 2 3]})\n  #'user/m\n  user=> (<< \"The total for your order is $~(->> m :a (apply +)).\")\n  \"The total for your order is $6.\"\n  user=> (<< \"Just split a long interpolated string up into ~(-> m :a (get 0)), \"\n           \"~(-> m :a (get 1)), or even ~(-> m :a (get 2)) separate strings \"\n           \"if you don't want a << expression to end up being e.g. ~(* 4 (int v)) \"\n           \"columns wide.\")\n  \"Just split a long interpolated string up into 1, 2, or even 3 separate strings if you don't want a << expression to end up being e.g. 120 columns wide.\"\n  \nNote that quotes surrounding string literals within ~() forms must be\nescaped.",
    "tag" nil,
    "source"
    "(defmacro <<\n  \"Accepts one or more strings; emits a `str` invocation that concatenates\nthe string data and evaluated expressions contained within that argument.\nEvaluation is controlled using ~{} and ~() forms. The former is used for\nsimple value replacement using clojure.core/str; the latter can be used to\nembed the results of arbitrary function invocation into the produced string.\n\nExamples:\n  user=> (def v 30.5)\n  #'user/v\n  user=> (<< \\\"This trial required ~{v}ml of solution.\\\")\n  \\\"This trial required 30.5ml of solution.\\\"\n  user=> (<< \\\"There are ~(int v) days in November.\\\")\n  \\\"There are 30 days in November.\\\"\n  user=> (def m {:a [1 2 3]})\n  #'user/m\n  user=> (<< \\\"The total for your order is $~(->> m :a (apply +)).\\\")\n  \\\"The total for your order is $6.\\\"\n  user=> (<< \\\"Just split a long interpolated string up into ~(-> m :a (get 0)), \\\"\n           \\\"~(-> m :a (get 1)), or even ~(-> m :a (get 2)) separate strings \\\"\n           \\\"if you don't want a << expression to end up being e.g. ~(* 4 (int v)) \\\"\n           \\\"columns wide.\\\")\n  \\\"Just split a long interpolated string up into 1, 2, or even 3 separate strings if you don't want a << expression to end up being e.g. 120 columns wide.\\\"\n  \nNote that quotes surrounding string literals within ~() forms must be\nescaped.\"\n  [& strings]\n  `(str ~@(interpolate (apply str strings))))",
    "file" "clojure/core/strint.clj",
    "arglists" [["&" "strings"]]}
   {"private" true,
    "ns" "clojure.core.strint",
    "name" "interpolate",
    "line" 31,
    "column" 1,
    "doc" "Yields a seq of Strings and read forms.",
    "tag" nil,
    "source"
    "(defn- interpolate\n  \"Yields a seq of Strings and read forms.\"\n  ([s atom?]\n    (lazy-seq\n      (if-let [[form rest] (silent-read (subs s (if atom? 2 1)))]\n        (cons form (interpolate (if atom? (subs rest 1) rest)))\n        (cons (subs s 0 2) (interpolate (subs s 2))))))\n  ([^String s]\n    (if-let [start (->> [\"~{\" \"~(\"]\n                     (map #(.indexOf s %))\n                     (remove #(== -1 %))\n                     sort\n                     first)]\n      (lazy-seq (cons\n                  (subs s 0 start)\n                  (interpolate (subs s start) (= \\{ (.charAt s (inc start))))))\n      [s])))",
    "file" "clojure/core/strint.clj",
    "arglists" [["s" "atom?"] ["s"]]}
   {"private" true,
    "ns" "clojure.core.strint",
    "name" "silent-read",
    "line" 20,
    "column" 1,
    "doc"
    "Attempts to clojure.core/read a single form from the provided String, returning\n  a vector containing the read form and a String containing the unread remainder\n  of the provided String. Returns nil if no valid form can be read from the\n  head of the String.",
    "tag" nil,
    "source"
    "(defn- silent-read\n  \"Attempts to clojure.core/read a single form from the provided String, returning\n  a vector containing the read form and a String containing the unread remainder\n  of the provided String. Returns nil if no valid form can be read from the\n  head of the String.\"\n  [s]\n  (try\n    (let [r (-> s java.io.StringReader. java.io.PushbackReader.)]\n      [(read r) (slurp r)])\n    (catch Exception e)))",
    "file" "clojure/core/strint.clj",
    "arglists" [["s"]]}],
  "clojure.core.incubator"
  [{"ns" "clojure.core.incubator",
    "name" "dissoc-in",
    "line" 62,
    "column" 1,
    "doc"
    "Dissociates an entry from a nested associative structure returning a new\n  nested structure. keys is a sequence of keys. Any empty maps that result\n  will not be present in the new structure.",
    "tag" nil,
    "source"
    "(defn dissoc-in\n  \"Dissociates an entry from a nested associative structure returning a new\n  nested structure. keys is a sequence of keys. Any empty maps that result\n  will not be present in the new structure.\"\n  [m [k & ks :as keys]]\n  (if ks\n    (if-let [nextmap (get m k)]\n      (let [newmap (dissoc-in nextmap ks)]\n        (if (seq newmap)\n          (assoc m k newmap)\n          (dissoc m k)))\n      m)\n    (dissoc m k)))",
    "file" "clojure/core/incubator.clj",
    "arglists" [["m" ["k" "&" "ks" "as" "keys"]]]}
   {"private" true,
    "ns" "clojure.core.incubator",
    "name" "defnilsafe",
    "macro" true,
    "line" 21,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro- defnilsafe [docstring non-safe-name nil-safe-name]\n  `(defmacro ~nil-safe-name ~docstring\n     {:arglists '([~'x ~'form] [~'x ~'form ~'& ~'forms])}\n\t   ([x# form#]\n\t     `(let [~'i# ~x#] (when-not (nil? ~'i#) (~'~non-safe-name ~'i# ~form#))))\n  \t ([x# form# & more#]\n\t     `(~'~nil-safe-name (~'~nil-safe-name ~x# ~form#) ~@more#))))",
    "file" "clojure/core/incubator.clj",
    "arglists" [["docstring" "non-safe-name" "nil-safe-name"]]}
   {"ns" "clojure.core.incubator",
    "name" "new-by-name",
    "line" 76,
    "column" 1,
    "doc"
    "Constructs a Java object whose class is specified by a String.",
    "tag" nil,
    "source"
    "(defn new-by-name\n  \"Constructs a Java object whose class is specified by a String.\"\n  [class-name & args]\n  (clojure.lang.Reflector/invokeConstructor\n   (clojure.lang.RT/classForName class-name)\n   (into-array Object args)))",
    "file" "clojure/core/incubator.clj",
    "arglists" [["class-name" "&" "args"]]}
   {"ns" "clojure.core.incubator",
    "name" "-?>>",
    "macro" true,
    "deprecated" "0.1.3",
    "line" 48,
    "column" 1,
    "doc"
    "DEPRECATED: use clojure.core/some->> instead.\n  \n   Same as clojure.core/->> but returns nil as soon as the threaded value is nil itself (thus short-circuiting any pending computation).\n   Examples :\n   (-?>> (range 5) (map inc)) returns (1 2 3 4 5)\n   (-?>> [] seq (map inc)) returns nil\n   ",
    "tag" nil,
    "source"
    "(defnilsafe\n  \"DEPRECATED: use clojure.core/some->> instead.\n  \n   Same as clojure.core/->> but returns nil as soon as the threaded value is nil itself (thus short-circuiting any pending computation).\n   Examples :\n   (-?>> (range 5) (map inc)) returns (1 2 3 4 5)\n   (-?>> [] seq (map inc)) returns nil\n   \"\n  ->> \n  ^{:deprecated \"0.1.3\"} -?>>)",
    "file" "clojure/core/incubator.clj",
    "arglists" [["x" "form"] ["x" "form" "&" "forms"]]}
   {"ns" "clojure.core.incubator",
    "name" ".?.",
    "macro" true,
    "line" 40,
    "column" 1,
    "doc"
    "Same as clojure.core/.. but returns nil as soon as the threaded value is nil itself (thus short-circuiting any pending computation).\n   Examples :\n   (.?. \"foo\" .toUpperCase (.substring 1)) returns \"OO\"\n   (.?. nil .toUpperCase (.substring 1)) returns nil\n   ",
    "tag" nil,
    "source"
    "(defnilsafe \n  \"Same as clojure.core/.. but returns nil as soon as the threaded value is nil itself (thus short-circuiting any pending computation).\n   Examples :\n   (.?. \\\"foo\\\" .toUpperCase (.substring 1)) returns \\\"OO\\\"\n   (.?. nil .toUpperCase (.substring 1)) returns nil\n   \"\n   .. .?.)",
    "file" "clojure/core/incubator.clj",
    "arglists" [["x" "form"] ["x" "form" "&" "forms"]]}
   {"ns" "clojure.core.incubator",
    "name" "defmacro-",
    "macro" true,
    "line" 16,
    "column" 1,
    "doc" "Same as defmacro but yields a private definition",
    "tag" nil,
    "source"
    "(defmacro defmacro-\n  \"Same as defmacro but yields a private definition\"\n  [name & decls]\n  (list* `defmacro (with-meta name (assoc (meta name) :private true)) decls))",
    "file" "clojure/core/incubator.clj",
    "arglists" [["name" "&" "decls"]]}
   {"ns" "clojure.core.incubator",
    "name" "seqable?",
    "line" 83,
    "column" 1,
    "doc" "Returns true if (seq x) will succeed, false otherwise.",
    "tag" nil,
    "source"
    "(defn seqable?\n  \"Returns true if (seq x) will succeed, false otherwise.\"\n  [x]\n  (or (seq? x)\n      (instance? clojure.lang.Seqable x)\n      (nil? x)\n      (instance? Iterable x)\n      (-> x .getClass .isArray)\n      (string? x)\n      (instance? java.util.Map x)))",
    "file" "clojure/core/incubator.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.core.incubator",
    "name" "-?>",
    "macro" true,
    "deprecated" "0.1.3",
    "line" 29,
    "column" 1,
    "doc"
    "DEPRECATED: use clojure.core/some-> instead.\n   \n   Same as clojure.core/-> but returns nil as soon as the threaded value is nil itself (thus short-circuiting any pending computation).\n   Examples :\n   (-?> \"foo\" .toUpperCase (.substring 1)) returns \"OO\"\n   (-?> nil .toUpperCase (.substring 1)) returns nil\n   ",
    "tag" nil,
    "source"
    "(defnilsafe \n  \"DEPRECATED: use clojure.core/some-> instead.\n   \n   Same as clojure.core/-> but returns nil as soon as the threaded value is nil itself (thus short-circuiting any pending computation).\n   Examples :\n   (-?> \\\"foo\\\" .toUpperCase (.substring 1)) returns \\\"OO\\\"\n   (-?> nil .toUpperCase (.substring 1)) returns nil\n   \"\n   ->\n  ^{:deprecated \"0.1.3\"} -?>)",
    "file" "clojure/core/incubator.clj",
    "arglists" [["x" "form"] ["x" "form" "&" "forms"]]}]},
 "description" "core.incubator 0.1.3",
 "version" "0.1.3",
 "name" "clojure.core.incubator",
 "group" "clojure.core.incubator"}
