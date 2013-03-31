{"namespaces"
 {"clojure.data.generators"
  [{"ns" "clojure.data.generators",
    "name" "reps",
    "line" 31,
    "column" 1,
    "doc" "Returns sizer repetitions of f (or (f) if f is a fn).",
    "tag" nil,
    "source"
    "(defn reps\n  \"Returns sizer repetitions of f (or (f) if f is a fn).\"\n  [sizer f]\n  (let [count (call-through sizer)]\n    (if (fn? f)\n      (repeatedly count f)\n      (repeat count f))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["sizer" "f"]]}
   {"ns" "clojure.data.generators",
    "name" "boolean-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "reservoir-sample",
    "line" 320,
    "column" 1,
    "doc" "Reservoir sample ct items from coll, using *rnd*.",
    "tag" nil,
    "source"
    "(defn reservoir-sample\n  \"Reservoir sample ct items from coll, using *rnd*.\"\n  [ct coll]\n  (loop [result (transient (core/vec (take ct coll)))\n         n ct\n         coll (drop ct coll)]\n    (if (seq coll)\n      (let [pos (uniform 0 n)]\n        (recur (if (< pos ct) \n                 (assoc! result pos (first coll)) \n                 result)\n               (inc n)\n               (rest coll)))\n      (persistent! result))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["ct" "coll"]]}
   {"ns" "clojure.data.generators",
    "name" "long-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "tuple",
    "line" 68,
    "column" 1,
    "doc" "Generate a tuple with one element from each generator.",
    "tag" nil,
    "source"
    "(defn tuple\n  \"Generate a tuple with one element from each generator.\"\n  [& generators]\n  (into [] (map #(%) generators)))",
    "file" "clojure/data/generators.clj",
    "arglists" [["&" "generators"]]}
   {"ns" "clojure.data.generators",
    "name" "short-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "char-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "long",
    "line" 92,
    "column" 1,
    "doc" "Returns a long based on *rnd*. Same as uniform.",
    "tag" nil,
    "source"
    "(def long\n  \"Returns a long based on *rnd*. Same as uniform.\"\n  uniform)",
    "file" "clojure/data/generators.clj",
    "arglists" nil}
   {"arglists" [[]],
    "ns" "clojure.data.generators",
    "name" "short",
    "column" 1,
    "line" 107,
    "source"
    "(defn short\n  []\n  \"Returns a long based on *rnd* in the short range.\"\n  (uniform Short/MIN_VALUE (inc (core/long Short/MAX_VALUE))))",
    "file" "clojure/data/generators.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "name-body",
    "line" 232,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- name-body\n  [sizer]\n  (string #(core/char (rand-nth symbol-char)) sizer))",
    "file" "clojure/data/generators.clj",
    "arglists" [["sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "primitive-array",
    "macro" true,
    "line" 144,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro primitive-array\n  [type]\n  (let [fn-name (core/symbol (str type \"-array\"))\n        factory-name (core/symbol (str \"core/\" fn-name))\n        cast-name (core/symbol (str \"core/\" type))]\n    `(defn ~fn-name\n       \"Create an array with elements from f and sized from sizer.\"\n       ([~'f]\n          (~fn-name ~'f default-sizer))\n       ([~'f ~'sizer]\n          (let [~'arr (~factory-name (call-through ~'sizer))]\n            (dotimes [~'i (count ~'arr)]\n              (aset ~'arr ~'i (~cast-name (call-through ~'f))))\n            ~'arr)))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["type"]]}
   {"ns" "clojure.data.generators",
    "name" "symbol",
    "line" 242,
    "column" 1,
    "doc" "Create a non-namepsaced symbol sized from sizer.",
    "tag" nil,
    "source"
    "(defn symbol\n  \"Create a non-namepsaced symbol sized from sizer.\"\n  ([] (symbol default-sizer))\n  ([sizer] (core/symbol (name sizer))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "*rnd*",
    "line" 18,
    "column" 1,
    "doc"
    "Random instance for use in generators. By consistently using this\ninstance you can get a repeatable basis for tests.",
    "tag" "java.util.Random",
    "source"
    "(def ^:dynamic ^java.util.Random\n     *rnd*\n     \"Random instance for use in generators. By consistently using this\ninstance you can get a repeatable basis for tests.\"\n     (java.util.Random. 42))",
    "file" "clojure/data/generators.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.data.generators",
    "name" "geometric",
    "line" 39,
    "column" 1,
    "doc" "Geometric distribution with mean 1/p.",
    "tag" nil,
    "source"
    "(defn geometric\n  \"Geometric distribution with mean 1/p.\"\n  ^long [p]\n  (core/long (Math/ceil (/ (Math/log (.nextDouble *rnd*))\n                           (Math/log (- 1.0 p))))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["p"]]}
   {"ns" "clojure.data.generators",
    "name" "weighted",
    "line" 73,
    "column" 1,
    "doc"
    "Given a map of generators and weights, return a value from one of\n   the generators, selecting generator based on weights.",
    "tag" nil,
    "source"
    "(defn weighted\n  \"Given a map of generators and weights, return a value from one of\n   the generators, selecting generator based on weights.\"\n  [m]\n  (let [weights   (reductions + (vals m))\n        total   (last weights)\n        choices (map vector (keys m) weights)]\n    (let [choice (uniform 0 total)]\n      (loop [[[c w] & more] choices]\n        (when w\n          (if (< choice w)\n            (call-through c)\n            (recur more)))))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["m"]]}
   {"ns" "clojure.data.generators",
    "name" "date",
    "line" 257,
    "column" 1,
    "doc"
    "Create a date with geoemetric mean around base. Defaults to\n   #inst \"2007-10-16T00:00:00.000-00:00\"",
    "tag" nil,
    "source"
    "(defn date\n  \"Create a date with geoemetric mean around base. Defaults to\n   #inst \\\"2007-10-16T00:00:00.000-00:00\\\"\"\n  ([] (date #inst \"2007-10-16T00:00:00.000-00:00\"))\n  ([^java.util.Date base]\n     (java.util.Date. (geometric (/ 1 (.getTime base))))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["base"]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "name-prefix",
    "line" 227,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- name-prefix\n  []\n  (str (rand-nth [(core/char (rand-nth symbol-start)) \"\"])\n       (core/char (rand-nth ascii-alpha))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "vec",
    "line" 187,
    "column" 1,
    "doc" "Create a vec with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(defn vec\n  \"Create a vec with elements from f and sized from sizer.\"\n  ([f] (vec f default-sizer))\n  ([f sizer]\n     (into [] (reps sizer f))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"arglists" nil,
    "ns" "clojure.data.generators",
    "name" "collections",
    "column" 1,
    "line" 284,
    "source"
    "(def collections\n  [[vec [scalars]]\n   [set [scalars]]\n   [hash-map [scalars scalars]]])",
    "file" "clojure/data/generators.clj",
    "tag" nil}
   {"ns" "clojure.data.generators",
    "name" "boolean",
    "line" 117,
    "column" 1,
    "doc" "Returns a bool based on *rnd*.",
    "tag" nil,
    "source"
    "(defn boolean\n  \"Returns a bool based on *rnd*.\"\n  []\n  (.nextBoolean *rnd*))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "float-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "string",
    "line" 208,
    "column" 1,
    "doc" "Create a string with chars from v and sized from sizer.",
    "tag" nil,
    "source"
    "(defn string\n  \"Create a string with chars from v and sized from sizer.\"\n  ([] (string printable-ascii-char))\n  ([f] (string f default-sizer))\n  ([f sizer] (apply str (reps sizer f))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "int-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "float",
    "line" 52,
    "column" 1,
    "doc" "Generate af float between 0 and 1 based on *rnd*",
    "tag" nil,
    "source"
    "(defn float\n  \"Generate af float between 0 and 1 based on *rnd*\"\n  ^double []\n  (.nextFloat *rnd*))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "double-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "shuffle",
    "line" 313,
    "column" 1,
    "doc" "Shuffle coll based on *rnd*",
    "tag" nil,
    "source"
    "(defn shuffle\n  \"Shuffle coll based on *rnd*\"\n  [coll]\n  ;; using our own fischer-yates instead of core/shuffle so that\n  ;; we'll get the same shuffle, given the same *rnd*.\n  (fisher-yates coll))",
    "file" "clojure/data/generators.clj",
    "arglists" [["coll"]]}
   {"ns" "clojure.data.generators",
    "name" "ratio",
    "line" 96,
    "column" 1,
    "doc"
    "Generate a ratio, with numerator and denominator uniform longs\n   or as specified",
    "tag" nil,
    "source"
    "(defn ratio\n  \"Generate a ratio, with numerator and denominator uniform longs\n   or as specified\"\n  ([] (ratio long long))\n  ([num-gen denom-gen] (/ (num-gen) (denom-gen))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["num-gen" "denom-gen"]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "name",
    "line" 236,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- name\n  ([] (name default-sizer))\n  ([sizer]\n     (str (name-prefix)\n          (name-body sizer))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "primitive-arrays",
    "macro" true,
    "line" 159,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro primitive-arrays\n  [types]\n  `(do ~@(map (fn [type] `(primitive-array ~type)) types)))",
    "file" "clojure/data/generators.clj",
    "arglists" [["types"]]}
   {"ns" "clojure.data.generators",
    "name" "uniform",
    "line" 45,
    "column" 1,
    "doc"
    "Uniform distribution from lo (inclusive) to high (exclusive).\n   Defaults to range of Java long.",
    "tag" nil,
    "source"
    "(defn uniform\n  \"Uniform distribution from lo (inclusive) to high (exclusive).\n   Defaults to range of Java long.\"\n  (^long [] (.nextLong *rnd*))\n  (^long[lo hi] {:pre [(< lo hi)]}\n         (clojure.core/long (Math/floor (+ lo (* (.nextDouble *rnd*) (- hi lo)))))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["lo" "hi"]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "ascii-alpha",
    "line" 214,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private ascii-alpha\n  (concat (range 65 (+ 65 26))\n          (range 97 (+ 97 26))))",
    "file" "clojure/data/generators.clj",
    "arglists" nil}
   {"ns" "clojure.data.generators",
    "name" "set",
    "line" 193,
    "column" 1,
    "doc" "Create a set with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(defn set\n  \"Create a set with elements from f and sized from sizer.\"\n  ([f] (set f default-sizer))\n  ([f sizer]\n     (into #{} (reps sizer f))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "byte",
    "line" 112,
    "column" 1,
    "doc" "Returns a long based on *rnd* in the byte range.",
    "tag" nil,
    "source"
    "(defn byte\n  \"Returns a long based on *rnd* in the byte range.\"\n  ^long []\n  (uniform Byte/MIN_VALUE (inc (core/int Byte/MAX_VALUE))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "printable-ascii-char",
    "line" 122,
    "column" 1,
    "doc"
    "Returns a char based on *rnd* in the printable ascii range.",
    "tag" nil,
    "source"
    "(defn printable-ascii-char\n  \"Returns a char based on *rnd* in the printable ascii range.\"\n  []\n  (core/char (uniform 32 127)))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "symbol-char",
    "line" 222,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private symbol-char\n  (into symbol-start [\\1 \\2 \\3 \\4 \\5 \\6 \\7 \\8 \\9 \\0]))",
    "file" "clojure/data/generators.clj",
    "arglists" nil}
   {"ns" "clojure.data.generators",
    "name" "scalar",
    "line" 279,
    "column" 1,
    "doc" "Returns a scalar based on *rnd*.",
    "tag" nil,
    "source"
    "(defn scalar\n  \"Returns a scalar based on *rnd*.\"\n  []\n  (call-through (rand-nth scalars)))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "char",
    "line" 127,
    "column" 1,
    "doc" "Returns a character based on *rnd* in the range 0-65536.",
    "tag" nil,
    "source"
    "(defn char\n  \"Returns a character based on *rnd* in the range 0-65536.\"\n  []\n  (core/char (uniform 0 65536)))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "call-through",
    "line" 24,
    "column" 1,
    "doc" "Recursively call x until it doesn't return a function.",
    "tag" nil,
    "source"
    "(defn- call-through\n  \"Recursively call x until it doesn't return a function.\"\n  [x]\n  (if (fn? x)\n    (recur (x))\n    x))",
    "file" "clojure/data/generators.clj",
    "arglists" [["x"]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "symbol-start",
    "line" 218,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private symbol-start\n  (-> (concat ascii-alpha [\\* \\+ \\! \\- \\_ \\?])\n      core/vec))",
    "file" "clojure/data/generators.clj",
    "arglists" nil}
   {"ns" "clojure.data.generators",
    "name" "byte-array",
    "line" 163,
    "column" 1,
    "doc" "Create an array with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(primitive-arrays [\"byte\" \"short\" \"long\" \"char\" \"double\" \"float\" \"int\" \"boolean\"])",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "keyword",
    "line" 247,
    "column" 1,
    "doc" "Create a non-namespaced keyword sized from sizer.",
    "tag" nil,
    "source"
    "(defn keyword\n  \"Create a non-namespaced keyword sized from sizer.\"\n  ([] (keyword default-sizer))\n  ([sizer] (core/keyword (name sizer))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[] ["sizer"]]}
   {"arglists" [[]],
    "ns" "clojure.data.generators",
    "name" "int",
    "column" 1,
    "line" 102,
    "source"
    "(defn int\n  []\n  \"Returns a long based on *rnd* in the int range.\"\n  (uniform Integer/MIN_VALUE (inc Integer/MAX_VALUE)))",
    "file" "clojure/data/generators.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.data.generators",
    "name" "bigdec",
    "column" 1,
    "line" 183,
    "source"
    "(defn bigdec\n  []\n  (BigDecimal. (.toBigInteger (bigint)) (geometric 0.01)))",
    "file" "clojure/data/generators.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.data.generators",
    "name" "bigint",
    "column" 1,
    "line" 175,
    "source"
    "(defn bigint\n  ^clojure.lang.BigInt []\n  (loop []\n    (let [i (try\n             (BigInteger. ^bytes (byte-array byte))\n             (catch NumberFormatException e :retry))]\n      (if (= i :retry) (recur) (core/bigint i)))))",
    "file" "clojure/data/generators.clj",
    "tag" nil}
   {"ns" "clojure.data.generators",
    "name" "anything",
    "line" 295,
    "column" 1,
    "doc" "Returns a scalar or collection based on *rnd*.",
    "tag" nil,
    "source"
    "(defn anything\n  \"Returns a scalar or collection based on *rnd*.\"\n  []\n  (one-of scalar collection))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "uuid",
    "line" 252,
    "column" 1,
    "doc"
    "Create a UUID based on uniform distribution of low and high parts.",
    "tag" nil,
    "source"
    "(defn uuid\n  \"Create a UUID based on uniform distribution of low and high parts.\"\n  []\n  (java.util.UUID. (long) (long)))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"ns" "clojure.data.generators",
    "name" "double",
    "line" 57,
    "column" 1,
    "doc" "Generate a double between 0 and 1 based on *rnd*.",
    "tag" nil,
    "source"
    "(defn double\n  \"Generate a double between 0 and 1 based on *rnd*.\"\n  ^double []\n  (.nextDouble *rnd*))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"arglists" nil,
    "ns" "clojure.data.generators",
    "name" "scalars",
    "column" 1,
    "line" 264,
    "source"
    "(def scalars\n  [(constantly nil)\n   byte\n   long\n   boolean\n   printable-ascii-char\n   string\n   symbol\n   keyword\n   uuid\n   date\n   ratio\n   bigint\n   bigdec])",
    "file" "clojure/data/generators.clj",
    "tag" nil}
   {"ns" "clojure.data.generators",
    "name" "rand-nth",
    "line" 62,
    "column" 1,
    "doc"
    "Replacement of core/rand-nth that allows control of the\n   randomization basis (through binding *rnd*).",
    "tag" nil,
    "source"
    "(defn rand-nth\n  \"Replacement of core/rand-nth that allows control of the\n   randomization basis (through binding *rnd*).\"\n  [coll]\n  (nth coll (uniform 0 (count coll))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["coll"]]}
   {"ns" "clojure.data.generators",
    "name" "hash-map",
    "line" 199,
    "column" 1,
    "doc"
    "Create a hash-map with keys from fk, vals from fv, and\n   sized from sizer.",
    "tag" nil,
    "source"
    "(defn hash-map\n  \"Create a hash-map with keys from fk, vals from fv, and\n   sized from sizer.\"\n  ([fk fv] (hash-map fk fv default-sizer))\n  ([fk fv sizer]\n     (into {}\n           (zipmap (reps sizer fk)\n                   (reps sizer fv)))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["fk" "fv"] ["fk" "fv" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "one-of",
    "line" 87,
    "column" 1,
    "doc"
    "Generates one of the specs passed in, with equal probability.",
    "tag" nil,
    "source"
    "(defn one-of\n  \"Generates one of the specs passed in, with equal probability.\"\n  ([& specs]\n     (weighted (zipmap specs (repeat 1)))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["&" "specs"]]}
   {"ns" "clojure.data.generators",
    "name" "list",
    "line" 138,
    "column" 1,
    "doc" "Create a list with elements from f and sized from sizer.",
    "tag" nil,
    "source"
    "(defn list\n  \"Create a list with elements from f and sized from sizer.\"\n  ([f] (list f default-sizer))\n  ([f sizer]\n     (reps sizer f)))",
    "file" "clojure/data/generators.clj",
    "arglists" [["f"] ["f" "sizer"]]}
   {"ns" "clojure.data.generators",
    "name" "collection",
    "line" 289,
    "column" 1,
    "doc" "Returns a collection of scalar elements based on *rnd*.",
    "tag" nil,
    "source"
    "(defn collection\n  \"Returns a collection of scalar elements based on *rnd*.\"\n  []\n  (let [[coll args] (rand-nth collections)]\n    (apply coll (map rand-nth args))))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.data.generators",
    "name" "fisher-yates",
    "line" 300,
    "column" 1,
    "doc"
    "http://en.wikipedia.org/wiki/Fisher–Yates_shuffle#The_modern_algorithm",
    "tag" nil,
    "source"
    "(defn ^:private fisher-yates\n  \"http://en.wikipedia.org/wiki/Fisher–Yates_shuffle#The_modern_algorithm\"\n  [coll]\n  (let [as (object-array coll)]\n    (loop [i (dec (count as))]\n      (if (<= 1 i)\n        (let [j (uniform 0 (inc i))\n              t (aget as i)]\n          (aset as i (aget as j))\n          (aset as j t)\n          (recur (dec i)))\n        (into (empty coll) (seq as))))))",
    "file" "clojure/data/generators.clj",
    "arglists" [["coll"]]}
   {"ns" "clojure.data.generators",
    "name" "default-sizer",
    "line" 132,
    "column" 1,
    "doc"
    "Default sizer used to run tests. If you want a specific distribution,\n   create your own and pass it to a fn that wants a sizer.",
    "tag" nil,
    "source"
    "(defn default-sizer\n  \"Default sizer used to run tests. If you want a specific distribution,\n   create your own and pass it to a fn that wants a sizer.\"\n  []\n  (dec (geometric 0.02)))",
    "file" "clojure/data/generators.clj",
    "arglists" [[]]}]},
 "description" "data.generators 0.1.2",
 "version" "0.1.2",
 "name" "clojure.data.generators",
 "group" "clojure.data.generators"}
