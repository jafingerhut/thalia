{"namespaces"
 {"clojure.algo.generic.math-functions"
  [{"ns" "clojure.algo.generic.math-functions",
    "name" "tan",
    "line" 75,
    "column" 1,
    "doc" "Return the tan of x.",
    "tag" nil,
    "source" "(defmathfn-1 tan)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "atan",
    "line" 63,
    "column" 1,
    "doc" "Return the atan of x.",
    "tag" nil,
    "source" "(defmathfn-1 atan)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "sqrt",
    "line" 74,
    "column" 1,
    "doc" "Return the sqrt of x.",
    "tag" nil,
    "source" "(defmathfn-1 sqrt)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "defmacro-",
    "macro" true,
    "line" 25,
    "column" 1,
    "doc" "Same as defmacro but yields a private definition",
    "tag" nil,
    "source"
    "(defmacro defmacro-\n  \"Same as defmacro but yields a private definition\"\n  [name & decls]\n  (list* `defmacro (with-meta name (assoc (meta name) :private true)) decls))",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["name" "&" "decls"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "sgn",
    "line" 80,
    "column" 1,
    "doc" "Return the sign of x (-1, 0, or 1).",
    "tag" nil,
    "source"
    "(defmulti sgn\n  \"Return the sign of x (-1, 0, or 1).\"\n  {:arglists '([x])}\n  type)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "abs",
    "line" 60,
    "column" 1,
    "doc" "Return the abs of x.",
    "tag" nil,
    "source" "(defmathfn-1 abs)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "conjugate",
    "line" 94,
    "column" 1,
    "doc" "Return the conjugate of x.",
    "tag" nil,
    "source"
    "(defmulti conjugate\n  \"Return the conjugate of x.\"\n  {:arglists '([x])}\n  type)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"private" true,
    "ns" "clojure.algo.generic.math-functions",
    "name" "defmathfn-2",
    "macro" true,
    "line" 46,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro- defmathfn-2\n  [name]\n  (let [java-symbol (symbol \"java.lang.Math\" (str name))]\n    `(do\n       (defmulti ~name\n         ~(str \"Return the \" name \" of x and y.\")\n         {:arglists '([~'x ~'y])}\n         two-types)\n       (defmethod ~name [java.lang.Number java.lang.Number]\n         [~'x ~'y]\n         (~java-symbol ~'x ~'y)))))",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["name"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "log",
    "line" 69,
    "column" 1,
    "doc" "Return the log of x.",
    "tag" nil,
    "source" "(defmathfn-1 log)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"private" true,
    "ns" "clojure.algo.generic.math-functions",
    "name" "defmathfn-1",
    "macro" true,
    "line" 31,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro- defmathfn-1\n  [name]\n  (let [java-symbol (symbol \"java.lang.Math\" (str name))]\n    `(do\n       (defmulti ~name\n         ~(str \"Return the \" name \" of x.\")\n         {:arglists '([~'x])}\n         type)\n       (defmethod ~name java.lang.Number\n         [~'x]\n         (~java-symbol ~'x)))))",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["name"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "atan2",
    "line" 64,
    "column" 1,
    "doc" "Return the atan2 of x and y.",
    "tag" nil,
    "source" "(defmathfn-2 atan2)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x" "y"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "floor",
    "line" 68,
    "column" 1,
    "doc" "Return the floor of x.",
    "tag" nil,
    "source" "(defmathfn-1 floor)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "round",
    "line" 72,
    "column" 1,
    "doc" "Return the round of x.",
    "tag" nil,
    "source" "(defmathfn-1 round)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "approx=",
    "line" 117,
    "column" 1,
    "doc"
    "Return true if the absolute value of the difference between x and y\n   is less than eps.",
    "tag" nil,
    "source"
    "(defn approx=\n  \"Return true if the absolute value of the difference between x and y\n   is less than eps.\"\n  [x y eps]\n  (gc/< (abs (ga/- x y)) eps))",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x" "y" "eps"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "rint",
    "line" 71,
    "column" 1,
    "doc" "Return the rint of x.",
    "tag" nil,
    "source" "(defmathfn-1 rint)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "pow",
    "line" 70,
    "column" 1,
    "doc" "Return the pow of x and y.",
    "tag" nil,
    "source" "(defmathfn-2 pow)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x" "y"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "sin",
    "line" 73,
    "column" 1,
    "doc" "Return the sin of x.",
    "tag" nil,
    "source" "(defmathfn-1 sin)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "asin",
    "line" 62,
    "column" 1,
    "doc" "Return the asin of x.",
    "tag" nil,
    "source" "(defmathfn-1 asin)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "sqr",
    "line" 105,
    "column" 1,
    "doc" "Return the square of x.",
    "tag" nil,
    "source"
    "(defmulti sqr\n  \"Return the square of x.\"\n  {:arglists '([x])}\n  type)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "acos",
    "line" 61,
    "column" 1,
    "doc" "Return the acos of x.",
    "tag" nil,
    "source" "(defmathfn-1 acos)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "exp",
    "line" 67,
    "column" 1,
    "doc" "Return the exp of x.",
    "tag" nil,
    "source" "(defmathfn-1 exp)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "cos",
    "line" 66,
    "column" 1,
    "doc" "Return the cos of x.",
    "tag" nil,
    "source" "(defmathfn-1 cos)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.math-functions",
    "name" "ceil",
    "line" 65,
    "column" 1,
    "doc" "Return the ceil of x.",
    "tag" nil,
    "source" "(defmathfn-1 ceil)",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x"]]}
   {"private" true,
    "ns" "clojure.algo.generic.math-functions",
    "name" "two-types",
    "line" 43,
    "column" 1,
    "tag" nil,
    "source" "(defn- two-types [x y] [(type x) (type y)])",
    "file" "clojure/algo/generic/math_functions.clj",
    "arglists" [["x" "y"]]}],
  "clojure.algo.generic.functor"
  [{"ns" "clojure.algo.generic.functor",
    "name" "fmap",
    "line" 19,
    "column" 1,
    "doc"
    "Applies function f to each item in the data structure s and returns\n   a structure of the same kind.",
    "tag" nil,
    "source"
    "(defmulti fmap\n  \"Applies function f to each item in the data structure s and returns\n   a structure of the same kind.\"\n   {:arglists '([f s])}\n   (fn [f s] (type s)))",
    "file" "clojure/algo/generic/functor.clj",
    "arglists" [["f" "s"]]}],
  "clojure.algo.generic.comparison"
  [{"ns" "clojure.algo.generic.comparison",
    "name" "not=",
    "line" 62,
    "column" 1,
    "doc" "Equivalent to (not (= ...)).",
    "tag" nil,
    "source"
    "(defn not=\n  \"Equivalent to (not (= ...)).\"\n  [& args]\n  (not (apply = args)))",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["&" "args"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "zero?",
    "line" 27,
    "column" 1,
    "doc" "Return true of x is zero.",
    "tag" nil,
    "source"
    "(defmulti zero?\n  \"Return true of x is zero.\"\n  {:arglists '([x])}\n  type)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "neg?",
    "line" 37,
    "column" 1,
    "doc" "Return true of x is negative.",
    "tag" nil,
    "source"
    "(defmulti neg?\n  \"Return true of x is negative.\"\n  {:arglists '([x])}\n  type)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "<",
    "line" 91,
    "column" 1,
    "doc"
    "Return true if each argument is smaller than the following ones.\n   The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type]. A default implementation\n   is provided in terms of >.",
    "tag" nil,
    "source"
    "(defmulti <\n  \"Return true if each argument is smaller than the following ones.\n   The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type]. A default implementation\n   is provided in terms of >.\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "=",
    "line" 45,
    "column" 1,
    "doc"
    "Return true if all arguments are equal. The minimal implementation for type\n   ::my-type is the binary form with dispatch value [::my-type ::my-type].",
    "tag" nil,
    "source"
    "(defmulti =\n  \"Return true if all arguments are equal. The minimal implementation for type\n   ::my-type is the binary form with dispatch value [::my-type ::my-type].\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" ">",
    "line" 70,
    "column" 1,
    "doc"
    "Return true if each argument is larger than the following ones.\n   The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type].",
    "tag" nil,
    "source"
    "(defmulti >\n  \"Return true if each argument is larger than the following ones.\n   The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type].\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "max",
    "line" 204,
    "column" 1,
    "doc"
    "Returns the greatest of its arguments. Like clojure.core/max except that\n   is uses generic comparison functions implementable for any data type.",
    "tag" nil,
    "source"
    "(defn max\n  \"Returns the greatest of its arguments. Like clojure.core/max except that\n   is uses generic comparison functions implementable for any data type.\"\n  ([x] x)\n  ([x y] (if (> x y) x y))\n  ([x y & more]\n   (reduce max (max x y) more)))",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" ">=",
    "line" 117,
    "column" 1,
    "doc"
    "Return true if each argument is larger than or equal to the following\n   ones. The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type]. A default implementation\n   is provided in terms of <.",
    "tag" nil,
    "source"
    "(defmulti >=\n  \"Return true if each argument is larger than or equal to the following\n   ones. The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type]. A default implementation\n   is provided in terms of <.\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "<=",
    "line" 143,
    "column" 1,
    "doc"
    "Return true if each arguments is smaller than or equal to the following\n   ones. The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type]. A default implementation\n   is provided in terms of >.",
    "tag" nil,
    "source"
    "(defmulti <=\n  \"Return true if each arguments is smaller than or equal to the following\n   ones. The minimal implementation for type ::my-type is the binary form\n   with dispatch value [::my-type ::my-type]. A default implementation\n   is provided in terms of >.\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "pos?",
    "line" 32,
    "column" 1,
    "doc" "Return true of x is positive.",
    "tag" nil,
    "source"
    "(defmulti pos?\n  \"Return true of x is positive.\"\n  {:arglists '([x])}\n  type)",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.algo.generic.comparison",
    "name" "min",
    "line" 212,
    "column" 1,
    "doc"
    "Returns the least of its arguments. Like clojure.core/min except that\n   is uses generic comparison functions implementable for any data type.",
    "tag" nil,
    "source"
    "(defn min\n  \"Returns the least of its arguments. Like clojure.core/min except that\n   is uses generic comparison functions implementable for any data type.\"\n  ([x] x)\n  ([x y] (if (< x y) x y))\n  ([x y & more]\n   (reduce min (min x y) more)))",
    "file" "clojure/algo/generic/comparison.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}],
  "clojure.algo.generic.collection"
  [{"ns" "clojure.algo.generic.collection",
    "name" "dissoc",
    "line" 51,
    "column" 1,
    "doc"
    "Returns a new collection in which the entries corresponding to the\n   given keys are removed. Each type of collection can have specific\n   restrictions on the possible keys.",
    "tag" nil,
    "source"
    "(defmulti dissoc\n  \"Returns a new collection in which the entries corresponding to the\n   given keys are removed. Each type of collection can have specific\n   restrictions on the possible keys.\"\n   {:arglists '([coll & keys])}\n   (fn [coll & keys] (type coll)))",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["coll" "&" "keys"]]}
   {"ns" "clojure.algo.generic.collection",
    "name" "get",
    "line" 77,
    "column" 1,
    "doc"
    "Returns the element of coll referred to by key. Each type of collection\n   can have specific restrictions on the possible keys.",
    "tag" nil,
    "source"
    "(defmulti get\n  \"Returns the element of coll referred to by key. Each type of collection\n   can have specific restrictions on the possible keys.\"\n   {:arglists '([coll key] [coll key not-found])}\n  (fn [coll & args] (type coll)))",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["coll" "key"] ["coll" "key" "not-found"]]}
   {"ns" "clojure.algo.generic.collection",
    "name" "into",
    "line" 92,
    "column" 1,
    "doc"
    "Returns a new coll consisting of to-coll with all of the items of\n  from-coll conjoined. A default implementation based on reduce, conj, and\n  seq is provided.",
    "tag" nil,
    "source"
    "(defmulti into\n  \"Returns a new coll consisting of to-coll with all of the items of\n  from-coll conjoined. A default implementation based on reduce, conj, and\n  seq is provided.\"\n   {:arglists '([to from])}\n  (fn [to from] (type to)))",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["to" "from"]]}
   {"ns" "clojure.algo.generic.collection",
    "name" "seq",
    "line" 107,
    "column" 1,
    "doc" "Returns a seq on the object s.",
    "tag" nil,
    "source"
    "(defmulti seq\n  \"Returns a seq on the object s.\"\n  {:arglists '([s])}\n  type)",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.algo.generic.collection",
    "name" "assoc",
    "line" 25,
    "column" 1,
    "doc"
    "Returns a new collection in which the values corresponding to the\n   given keys are updated by the given values. Each type of collection\n   can have specific restrictions on the possible keys.",
    "tag" nil,
    "source"
    "(defmulti assoc\n  \"Returns a new collection in which the values corresponding to the\n   given keys are updated by the given values. Each type of collection\n   can have specific restrictions on the possible keys.\"\n   {:arglists '([coll & key-val-pairs])}\n   (fn [coll & items] (type coll)))",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["coll" "&" "key-val-pairs"]]}
   {"ns" "clojure.algo.generic.collection",
    "name" "conj",
    "line" 39,
    "column" 1,
    "doc"
    "Returns a new collection resulting from adding all xs to coll.",
    "tag" nil,
    "source"
    "(defmulti conj\n  \"Returns a new collection resulting from adding all xs to coll.\"\n   {:arglists '([coll & xs])}\n  (fn [coll & xs] (type coll)))",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["coll" "&" "xs"]]}
   {"ns" "clojure.algo.generic.collection",
    "name" "empty",
    "line" 65,
    "column" 1,
    "doc"
    "Returns an empty collection of the same kind as the argument",
    "tag" nil,
    "source"
    "(defmulti empty\n  \"Returns an empty collection of the same kind as the argument\"\n   {:arglists '([coll])}\n   type)",
    "file" "clojure/algo/generic/collection.clj",
    "arglists" [["coll"]]}],
  "clojure.algo.generic.arithmetic"
  [{"ns" "clojure.algo.generic.arithmetic",
    "name" "map->one-type",
    "line" 34,
    "column" 1,
    "doc"
    "Factory function for class clojure.algo.generic.arithmetic.one-type, taking a map of keywords to field values.",
    "tag" nil,
    "source" "(defrecord one-type [])",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "->one-type",
    "line" 34,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.algo.generic.arithmetic.one-type.",
    "tag" nil,
    "source" "(defrecord one-type [])",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [[]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "+",
    "line" 45,
    "column" 1,
    "doc"
    "Return the sum of all arguments. The minimal implementation for type\n   ::my-type is the binary form with dispatch value [::my-type ::my-type].",
    "tag" nil,
    "source"
    "(defmulti + \n  \"Return the sum of all arguments. The minimal implementation for type\n   ::my-type is the binary form with dispatch value [::my-type ::my-type].\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "*",
    "line" 111,
    "column" 1,
    "doc"
    "Return the product of all arguments. The minimal implementation for type\n   ::my-type is the binary form with dispatch value [::my-type ::my-type].",
    "tag" nil,
    "source"
    "(defmulti *\n  \"Return the product of all arguments. The minimal implementation for type\n   ::my-type is the binary form with dispatch value [::my-type ::my-type].\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "-",
    "line" 77,
    "column" 1,
    "doc"
    "Return the difference of the first argument and the sum of all other\n   arguments. The minimal implementation for type ::my-type is the binary\n   form with dispatch value [::my-type ::my-type].",
    "tag" nil,
    "source"
    "(defmulti -\n  \"Return the difference of the first argument and the sum of all other\n   arguments. The minimal implementation for type ::my-type is the binary\n   form with dispatch value [::my-type ::my-type].\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "->zero-type",
    "line" 30,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.algo.generic.arithmetic.zero-type.",
    "tag" nil,
    "source" "(defrecord zero-type [])",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [[]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "/",
    "line" 143,
    "column" 1,
    "doc"
    "Return the quotient of the first argument and the product of all other\n   arguments. The minimal implementation for type ::my-type is the binary\n   form with dispatch value [::my-type ::my-type].",
    "tag" nil,
    "source"
    "(defmulti /\n  \"Return the quotient of the first argument and the product of all other\n   arguments. The minimal implementation for type ::my-type is the binary\n   form with dispatch value [::my-type ::my-type].\"\n  {:arglists '([x] [x y] [x y & more])}\n  nary-dispatch)",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["x"] ["x" "y"] ["x" "y" "&" "more"]]}
   {"arglists" nil,
    "ns" "clojure.algo.generic.arithmetic",
    "name" "zero",
    "column" 1,
    "line" 32,
    "source" "(def zero (new zero-type))",
    "file" "clojure/algo/generic/arithmetic.clj",
    "tag" nil}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "defmethod*",
    "macro" true,
    "line" 173,
    "column" 1,
    "doc"
    "Define a method implementation for the multimethod name in namespace ns.\n   Required for implementing the division function from another namespace.",
    "tag" nil,
    "source"
    "(defmacro defmethod*\n  \"Define a method implementation for the multimethod name in namespace ns.\n   Required for implementing the division function from another namespace.\"\n  [ns name & args]\n  (let [qsym (symbol (str ns) (str name))]\n    `(defmethod ~qsym ~@args)))",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["ns" "name" "&" "args"]]}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "qsym",
    "macro" true,
    "line" 180,
    "column" 1,
    "doc"
    "Create the qualified symbol corresponding to sym in namespace ns.\n   Required to access the division function from another namespace,\n   e.g. as (qsym clojure.algo.generic.arithmetic /).",
    "tag" nil,
    "source"
    "(defmacro qsym\n  \"Create the qualified symbol corresponding to sym in namespace ns.\n   Required to access the division function from another namespace,\n   e.g. as (qsym clojure.algo.generic.arithmetic /).\"\n  [ns sym]\n  (symbol (str ns) (str sym)))",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["ns" "sym"]]}
   {"arglists" nil,
    "ns" "clojure.algo.generic.arithmetic",
    "name" "one",
    "column" 1,
    "line" 36,
    "source" "(def one (new one-type))",
    "file" "clojure/algo/generic/arithmetic.clj",
    "tag" nil}
   {"ns" "clojure.algo.generic.arithmetic",
    "name" "map->zero-type",
    "line" 30,
    "column" 1,
    "doc"
    "Factory function for class clojure.algo.generic.arithmetic.zero-type, taking a map of keywords to field values.",
    "tag" nil,
    "source" "(defrecord zero-type [])",
    "file" "clojure/algo/generic/arithmetic.clj",
    "arglists" [["m__5818__auto__"]]}],
  "clojure.algo.generic"
  [{"arglists" nil,
    "ns" "clojure.algo.generic",
    "name" "nulary-type",
    "column" 1,
    "line" 51,
    "source" "(def nulary-type ::nulary)",
    "file" "clojure/algo/generic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.algo.generic",
    "name" "nary-type",
    "column" 1,
    "line" 52,
    "source" "(def nary-type ::nary)",
    "file" "clojure/algo/generic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.algo.generic",
    "name" "root-type",
    "column" 1,
    "line" 45,
    "source" "(def root-type ::any)",
    "file" "clojure/algo/generic.clj",
    "tag" nil}
   {"arglists" [[] ["x"] ["x" "y"] ["x" "y" "&" "more"]],
    "ns" "clojure.algo.generic",
    "name" "nary-dispatch",
    "column" 1,
    "line" 31,
    "source"
    "(defn nary-dispatch\n  ([] ::nulary)\n  ([x] (type x))\n  ([x y]\n     [(type x) (type y)])\n  ([x y & more] ::nary))",
    "file" "clojure/algo/generic.clj",
    "tag" nil}]},
 "description" "algo.generic 0.1.1",
 "version" "0.1.1",
 "name" "clojure.algo.generic",
 "group" "clojure.algo.generic"}
