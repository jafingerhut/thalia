{"namespaces"
 {"clojure.math.combinatorics"
  [{"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "vec-lex-permutations",
    "line" 128,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- vec-lex-permutations [v]\n  (when v (cons v (lazy-seq (vec-lex-permutations (iter-perm v))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["v"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "subsets",
    "line" 81,
    "column" 1,
    "doc" "All the subsets of items",
    "tag" nil,
    "source"
    "(defn subsets\n  \"All the subsets of items\"\n  [items]\n  (mapcat (fn [n] (combinations items n))\n\t  (range (inc (count items)))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "sorted-numbers?",
    "line" 143,
    "column" 1,
    "doc"
    "Returns true iff s is a sequence of numbers in non-decreasing order",
    "tag" nil,
    "source"
    "(defn- sorted-numbers?\n  \"Returns true iff s is a sequence of numbers in non-decreasing order\"\n  [s]\n  (and (every? number? s)\n       (every? (partial apply <=) (partition 2 1 s))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "lex-permutations",
    "deprecated" "1.3",
    "line" 131,
    "column" 1,
    "doc"
    "DEPRECATED as a public function.\n\nIn prior versions of the combinatorics library, there were two similar functions: permutations and lex-permutations.  It was a source of confusion to know which to call.  Now, you can always call permutations.  When appropriate (i.e., when you pass in a sorted sequence of numbers), permutations will automatically call lex-permutations as a speed optimization.",
    "tag" nil,
    "source"
    "(defn lex-permutations\n  \"DEPRECATED as a public function.\n\nIn prior versions of the combinatorics library, there were two similar functions: permutations and lex-permutations.  It was a source of confusion to know which to call.  Now, you can always call permutations.  When appropriate (i.e., when you pass in a sorted sequence of numbers), permutations will automatically call lex-permutations as a speed optimization.\"\n  {:deprecated \"1.3\"}\n  [c]\n  (lazy-seq\n   (let [vec-sorted (vec (sort c))]\n     (if (zero? (count vec-sorted))\n       (list [])\n       (vec-lex-permutations vec-sorted)))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["c"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "selections",
    "line" 107,
    "column" 1,
    "doc"
    "All the ways of taking n (possibly the same) elements from the sequence of items",
    "tag" nil,
    "source"
    "(defn selections\n  \"All the ways of taking n (possibly the same) elements from the sequence of items\"\n  [items n]\n  (apply cartesian-product (take n (repeat items))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items" "n"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "cartesian-product",
    "line" 87,
    "column" 1,
    "doc" "All the ways to take one item from each sequence",
    "tag" nil,
    "source"
    "(defn cartesian-product\n  \"All the ways to take one item from each sequence\"\n  [& seqs]\n  (let [v-original-seqs (vec seqs)\n\tstep\n\t(fn step [v-seqs]\n\t  (let [increment\n\t\t(fn [v-seqs]\n\t\t  (loop [i (dec (count v-seqs)), v-seqs v-seqs]\n\t\t    (if (= i -1) nil\n\t\t\t(if-let [rst (next (v-seqs i))]\n\t\t\t  (assoc v-seqs i rst)\n\t\t\t  (recur (dec i) (assoc v-seqs i (v-original-seqs i)))))))]\n\t    (when v-seqs\n\t       (cons (map first v-seqs)\n\t\t     (lazy-seq (step (increment v-seqs)))))))]\n    (when (every? seq seqs)\n      (lazy-seq (step v-original-seqs)))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["&" "seqs"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "permutations",
    "line" 159,
    "column" 1,
    "doc"
    "All the distinct permutations of items, lexicographic by index.",
    "tag" nil,
    "source"
    "(defn permutations\n  \"All the distinct permutations of items, lexicographic by index.\"\n  [items]\n  (cond\n   (sorted-numbers? items) (lex-permutations items),\n\n   (apply distinct? items)\n   (let [v (vec items)]\n     (map #(map v %) (lex-permutations (range (count v)))))\n\n   :else\n   (multi-perm items)))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "combinations",
    "line" 70,
    "column" 1,
    "doc"
    "All the unique ways of taking n different elements from items",
    "tag" nil,
    "source"
    "(defn combinations\n  \"All the unique ways of taking n different elements from items\"\n  [items n]      \n  (let [v-items (vec (reverse items))]\n    (if (zero? n) (list ())\n\t(let [cnt (count items)]\n\t  (cond (> n cnt) nil\n\t\t(= n cnt) (list (seq items))\n\t\t:else\n\t\t(map #(map v-items %) (index-combinations n cnt)))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items" "n"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "multi-perm",
    "line" 149,
    "column" 1,
    "doc"
    "Handles the case when you want the permutations of a list with duplicate items.",
    "tag" nil,
    "source"
    "(defn- multi-perm\n  \"Handles the case when you want the permutations of a list with duplicate items.\"\n  [l]\n  (let [f (frequencies l),\n        v (vec (keys f)),\n        indices (apply concat\n                       (for [i (range (count v))]\n                         (repeat (f (v i)) i)))]\n    (map (partial map v) (lex-permutations indices))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["l"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "index-combinations",
    "line" 51,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- index-combinations\n  [n cnt]\n  (lazy-seq\n   (let [c (vec (cons nil (for [j (range 1 (inc n))] (+ j cnt (- (inc n)))))),\n\t iter-comb\n\t (fn iter-comb [c j]\n\t   (if (> j n) nil\n\t       (let [c (assoc c j (dec (c j)))]\n\t\t (if (< (c j) j) [c (inc j)]\n\t\t     (loop [c c, j j]\n\t\t       (if (= j 1) [c j]\n\t\t\t   (recur (assoc c (dec j) (dec (c j))) (dec j)))))))),\n\t step\n\t (fn step [c j]\n\t   (cons (rseq (subvec c 1 (inc n)))\n\t\t (lazy-seq (let [next-step (iter-comb c j)]\n\t\t\t     (when next-step (step (next-step 0) (next-step 1)))))))]\n     (step c 1))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["n" "cnt"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "iter-perm",
    "line" 113,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- iter-perm [v]\n  (let [len (count v),\n\tj (loop [i (- len 2)]\n\t     (cond (= i -1) nil\n\t\t   (< (v i) (v (inc i))) i\n\t\t   :else (recur (dec i))))]\n    (when j\n      (let [vj (v j),\n\t    l (loop [i (dec len)]\n\t\t(if (< vj (v i)) i (recur (dec i))))]\n\t(loop [v (assoc v j (v l) l vj), k (inc j), l (dec len)]\n\t  (if (< k l)\n\t    (recur (assoc v k (v l) l (v k)) (inc k) (dec l))\n\t    v))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["v"]]}]},
 "description" "math.combinatorics 0.0.3",
 "version" "0.0.3",
 "name" "clojure.math.combinatorics",
 "group" "clojure.math.combinatorics"}
