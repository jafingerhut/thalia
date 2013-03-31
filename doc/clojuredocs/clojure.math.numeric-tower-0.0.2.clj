{"namespaces"
 {"clojure.math.numeric-tower"
  [{"ns" "clojure.math.numeric-tower",
    "name" "when-available",
    "macro" true,
    "line" 105,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro when-available\n  [sym & body]\n  (try\n    (when (resolve sym)\n      (list* 'do body))\n    (catch ClassNotFoundException _#)))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["sym" "&" "body"]]}
   {"ns" "clojure.math.numeric-tower",
    "name" "expt",
    "line" 72,
    "column" 1,
    "doc"
    "(expt base pow) is base to the pow power.\nReturns an exact number if the base is an exact number and the power is an integer, otherwise returns a double.",
    "tag" nil,
    "source"
    "(defn expt\n  \"(expt base pow) is base to the pow power.\nReturns an exact number if the base is an exact number and the power is an integer, otherwise returns a double.\"\n  [base pow]\n  (if (and (not (float? base)) (integer? pow))\n    (cond\n     (pos? pow) (expt-int base pow)\n     (zero? pow) 1\n     :else (/ 1 (expt-int base (minus pow))))\n    (Math/pow base pow)))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["base" "pow"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.math.numeric-tower",
    "doc" "Square root, but returns exact number if possible.",
    "arglists" [["n"]],
    "name" "sqrt"}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "integer-sqrt",
    "line" 196,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- integer-sqrt [n]\n  (cond\n   (> n 24)\n   (let [n-len (integer-length n)]\n     (loop [init-value (if (even? n-len)\n\t\t\t (expt 2 (quot n-len 2))\n\t\t\t (expt 2 (inc* (quot n-len 2))))]\n       (let [iterated-value (quot (plus init-value (quot n init-value)) 2)]\n\t (if (>= iterated-value init-value)\n\t   init-value\n\t   (recur iterated-value)))))\n   (> n 15) 4\n   (> n  8) 3\n   (> n  3) 2\n   (> n  0) 1\n   (> n -1) 0))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["n"]]}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "plus",
    "line" 60,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} plus (first [+' +]))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "minus",
    "line" 58,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} minus (first [-' -]))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "sqrt-decimal",
    "line" 241,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- sqrt-decimal [n]\n  (if (neg? n) Double/NaN\n      (let [frac (rationalize n),\n\t    sqrtfrac (sqrt frac)]\n\t(if (ratio? sqrtfrac)\n\t  (/ (BigDecimal. (.numerator ^clojure.lang.Ratio sqrtfrac))\n\t     (BigDecimal. (.denominator ^clojure.lang.Ratio sqrtfrac)))\n\t  sqrtfrac))))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["n"]]}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "expt-int",
    "line" 64,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- expt-int [base pow]\n  (loop [n pow, y (num 1), z base]\n    (let [t (even? n), n (quot n 2)]\n      (cond\n       t (recur n y (mult z z))\n       (zero? n) (mult z y)\n       :else (recur n (mult z y) (mult z z))))))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["base" "pow"]]}
   {"ns" "clojure.math.numeric-tower",
    "name" "abs",
    "line" 83,
    "column" 1,
    "doc" "(abs n) is the absolute value of n",
    "tag" nil,
    "source"
    "(defn abs \"(abs n) is the absolute value of n\" [n]\n  (cond\n   (not (number? n)) (throw (IllegalArgumentException.\n\t\t\t     \"abs requires a number\"))\n   (neg? n) (minus n)\n   :else n))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["n"]]}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "sqrt-integer",
    "line" 222,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- sqrt-integer [n]\n  (if (neg? n) Double/NaN\n      (let [isqrt (integer-sqrt n),\n\t    error (minus n (mult isqrt isqrt))]\n\t(if (zero? error) isqrt\n\t    (Math/sqrt n)))))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.math.numeric-tower",
    "name" "exact-integer-sqrt",
    "line" 213,
    "column" 1,
    "doc"
    "(exact-integer-sqrt n) expects a non-negative integer n, and returns [s r] where n = s^2+r and n < (s+1)^2.  In other words, it returns the floor of the square root and the 'remainder'.\nFor example, (exact-integer-sqrt 15) is [3 6] because 15 = 3^2+6.",
    "tag" nil,
    "source"
    "(defn exact-integer-sqrt \"(exact-integer-sqrt n) expects a non-negative integer n, and returns [s r] where n = s^2+r and n < (s+1)^2.  In other words, it returns the floor of the square root and the 'remainder'.\nFor example, (exact-integer-sqrt 15) is [3 6] because 15 = 3^2+6.\"\n  [n]\n  (if (or (not (integer? n)) (neg? n))\n    (throw (IllegalArgumentException. \"exact-integer-sqrt requires a non-negative integer\"))\n    (let [isqrt (integer-sqrt n),\n\t  error (minus n (mult isqrt isqrt))]\n      [isqrt error])))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["n"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.math.numeric-tower",
    "doc"
    "(floor n) returns the greatest integer less than or equal to n.\nIf n is an exact number, floor returns an integer, otherwise a double.",
    "arglists" [["n"]],
    "name" "floor"}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "inc*",
    "line" 62,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} inc* (first [inc' inc]))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.math.numeric-tower",
    "doc"
    "(round n) rounds to the nearest integer.\nround always returns an integer.  Rounds up for values exactly in between two integers.",
    "arglists" [["n"]],
    "name" "round"}
   {"ns" "clojure.math.numeric-tower",
    "name" "gcd",
    "line" 178,
    "column" 1,
    "doc" "(gcd a b) returns the greatest common divisor of a and b",
    "tag" nil,
    "source"
    "(defn gcd \"(gcd a b) returns the greatest common divisor of a and b\" [a b]\n  (if (or (not (integer? a)) (not (integer? b)))\n    (throw (IllegalArgumentException. \"gcd requires two integers\"))  \n    (loop [a (abs a) b (abs b)]\n      (if (zero? b) a,\n\t  (recur b (mod a b))))))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["a" "b"]]}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "mult",
    "line" 59,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} mult (first [*' *]))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" nil}
   {"ns" "clojure.math.numeric-tower",
    "name" "lcm",
    "line" 185,
    "column" 1,
    "doc" "(lcm a b) returns the least common multiple of a and b",
    "tag" nil,
    "source"
    "(defn lcm\n  \"(lcm a b) returns the least common multiple of a and b\"\n  [a b]\n  (when (or (not (integer? a)) (not (integer? b)))\n    (throw (IllegalArgumentException. \"lcm requires two integers\")))\n  (cond (zero? a) 0\n        (zero? b) 0\n        :else (abs (mult b (quot a (gcd a b))))))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["a" "b"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.math.numeric-tower",
    "doc" "Length of integer in binary",
    "arglists" [["n"]],
    "name" "integer-length"}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "dec*",
    "line" 61,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} dec* (first [dec' dec]))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" nil}
   {"ns" "clojure.math.numeric-tower",
    "name" "MathFunctions",
    "line" 90,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol MathFunctions\n  (floor [n] \"(floor n) returns the greatest integer less than or equal to n.\nIf n is an exact number, floor returns an integer, otherwise a double.\")\n  (ceil [n] \"(ceil n) returns the least integer greater than or equal to n.\nIf n is an exact number, ceil returns an integer, otherwise a double.\")\n  (round [n] \"(round n) rounds to the nearest integer.\nround always returns an integer.  Rounds up for values exactly in between two integers.\")\n  (integer-length [n] \"Length of integer in binary\")\n  (sqrt [n] \"Square root, but returns exact number if possible.\"))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.math.numeric-tower",
    "name" "sqrt-ratio",
    "line" 229,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- sqrt-ratio [^clojure.lang.Ratio n]\n  (if (neg? n) Double/NaN\n      (let [numerator (.numerator n),\n\t    denominator (.denominator n),\n\t    sqrtnum (sqrt numerator)]\n\t(if (float? sqrtnum)\n\t  (Math/sqrt n)\n\t  (let [sqrtden (sqrt denominator)]\n\t    (if (float? sqrtnum)\n\t      (Math/sqrt n)\n\t      (/ sqrtnum sqrtden)))))))",
    "file" "clojure/math/numeric_tower.clj",
    "arglists" [["n"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.math.numeric-tower",
    "doc"
    "(ceil n) returns the least integer greater than or equal to n.\nIf n is an exact number, ceil returns an integer, otherwise a double.",
    "arglists" [["n"]],
    "name" "ceil"}]},
 "description" "math.numeric-tower 0.0.2",
 "version" "0.0.2",
 "name" "clojure.math.numeric-tower",
 "group" "clojure.math.numeric-tower"}
