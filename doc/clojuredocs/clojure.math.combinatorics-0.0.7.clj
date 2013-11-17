{"namespaces"
 {"clojure.math.combinatorics"
  [{"ns" "clojure.math.combinatorics",
    "name" "partitions",
    "line" 482,
    "column" 1,
    "doc"
    "All the lexicographic distinct partitions of items.\nOptionally pass in :min and/or :max to specify inclusive bounds on the number of parts the items can be split into.",
    "tag" nil,
    "source"
    "(defn partitions\n  \"All the lexicographic distinct partitions of items.\nOptionally pass in :min and/or :max to specify inclusive bounds on the number of parts the items can be split into.\"\n  [items & args]\n  (cond\n    (= (count items) 0) (apply partitions-H items args)\n    (apply distinct? items) (apply partitions-H items args)\n    :else (apply partitions-M items args)))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items" "&" "args"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "vec-lex-permutations",
    "line" 158,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- vec-lex-permutations [v]\n  (when v (cons v (lazy-seq (vec-lex-permutations (iter-perm v))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["v"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "reify-bool",
    "macro" true,
    "line" 224,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private reify-bool\n  [statement]\n  `(if ~statement 1 0))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["statement"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "partitions-M",
    "line" 456,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- partitions-M\n  [items & {from :min to :max}]\n  (if (= (count items) 0)\n    (if (<= (or from 0) 0 (or to 0))\n      '(())\n      ())\n    (let [items (vec items)\n          ditems (vec (distinct items))\n          freqs (frequencies items)\n          N (count items)\n          M (count ditems)\n          from (if (and from (<= from 1)) nil from)\n          to (if (and to (>= to N)) nil to)]\n      (cond\n        (not (<= 1 (or from 1) (or to N) N)) ()\n        (= N 1) `(([~(first items)]))\n        :else (let [start-multiset (into {} (for [i (range M)\n                                                  :let [j (inc i)]]\n                                              [j (freqs (ditems i))]))\n                    parts (multiset-partitions-M start-multiset to from)]\n                (->> multiset\n                  (mapcat (fn [[index numtimes]] (repeat numtimes (ditems (dec index)))))\n                  vec\n                  (for [multiset part])\n                  (for [part parts])))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items" "&" [["from" "min"] ["to" "max"]]]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "update",
    "line" 219,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- update\n  [vec index f]\n  (let [item (vec index)]\n    (assoc vec index (f item))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["vec" "index" "f"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "unchunk",
    "line" 99,
    "column" 1,
    "doc"
    "Given a sequence that may have chunks, return a sequence that is 1-at-a-time\nlazy with no chunks. Chunks are good for efficiency when the data items are\nsmall, but when being processed via map, for example, a reference is kept to\nevery function result in the chunk until the entire chunk has been processed,\nwhich increases the amount of memory in use that cannot be garbage\ncollected.",
    "tag" nil,
    "source"
    "(defn- unchunk\n  \"Given a sequence that may have chunks, return a sequence that is 1-at-a-time\nlazy with no chunks. Chunks are good for efficiency when the data items are\nsmall, but when being processed via map, for example, a reference is kept to\nevery function result in the chunk until the entire chunk has been processed,\nwhich increases the amount of memory in use that cannot be garbage\ncollected.\"\n  [s]\n  (lazy-seq\n   (when (seq s)\n     (cons (first s) (unchunk (rest s))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "subsets",
    "line" 111,
    "column" 1,
    "doc" "All the subsets of items",
    "tag" nil,
    "source"
    "(defn subsets\n  \"All the subsets of items\"\n  [items]\n  (mapcat (fn [n] (combinations items n))\n\t  (unchunk (range (inc (count items))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "init",
    "line" 228,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- init\n  [n s]\n  (if s\n    (vec (for [i (range 1 (inc n))]\n           (max 0 (- i (- n s -1)))))\n    (vec (repeat n 0))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["n" "s"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "partitions-H",
    "line" 305,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- partitions-H\n  [items & args]\n  (let [items (vec items)\n        N (count items)\n        lex (apply lex-partitions-H N args)]\n    (for [parts lex]\n      (for [part parts]\n        (-> (reduce (fn [v o] (conj! v (items o))) (transient []) part) ; mapv\n          persistent!)))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items" "&" "args"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "sorted-numbers?",
    "line" 173,
    "column" 1,
    "doc"
    "Returns true iff s is a sequence of numbers in non-decreasing order",
    "tag" nil,
    "source"
    "(defn- sorted-numbers?\n  \"Returns true iff s is a sequence of numbers in non-decreasing order\"\n  [s]\n  (and (every? number? s)\n       (every? (partial apply <=) (partition 2 1 s))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "multiset-partitions-M",
    "line" 348,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- multiset-partitions-M\n  ([multiset r s] ; M1\n    (let [n (apply + (vals multiset))\n          m (count multiset)\n          f []\n          c []\n          u []\n          v []\n          ; these vectors will grow over time, as new values are assoc'd into the next spots.\n          [c u v] (loop [j 0, c c, u u, v v]\n                    (if (= j m)\n                      [c u v]\n                      (recur (inc j)\n                             (assoc c j (inc j))\n                             (assoc u j (multiset (inc j)))\n                             (assoc v j (multiset (inc j))))))\n          a 0, b m, l 0\n          f (assoc f 0 0, 1 m)\n          stack ()]\n      (multiset-partitions-M n m f c u v a b l r s)))\n  ([n m f c u v a b l r s]\n    (let [[u v c j k] (loop [j a, k b, x false     ; M2\n                             u u, v v, c c]\n                        (if (>= j b)\n                          [u v c j k]\n                          (let [u (assoc u k (- (u j) (v j)))]\n                            (if (= (u k) 0)\n                              (recur (inc j), k, true\n                                     u, v, c)\n                              (if-not x\n                                (let [c (assoc c k (c j))\n                                      v (assoc v k (min (v j) (u k)))\n                                      x (< (u k) (v j))\n                                      k (inc k)\n                                      j (inc j)]\n                                  (recur j, k, x\n                                         u, v, c))\n                                (let [c (assoc c k (c j))\n                                      v (assoc v k (u k))\n                                      k (inc k)\n                                      j (inc j)]\n                                  (recur j, k, x\n                                         u, v, c)))))))]\n      (cond  ; M3\n        (and r\n             (> k b)\n             (= l (dec r))) (m5 n m f c u v a b l r s)\n        (and s\n             (<= k b)\n             (< (inc l) s)) (m5 n m f c u v a b l r s)\n        (> k b) (let [a b, b k, l (inc l)\n                      f (assoc f (inc l) b)]\n                  (recur n m f c u v a b l r s))\n        :else (let [part (for [y (range (inc l))]\n                           (let [first-col (f y)\n                                 last-col (dec (f (inc y)))]\n                             (into {} (for [z (range first-col (inc last-col))\n                                            :when (not= (v z) 0)]\n                                        [(c z) (v z)]))))]\n                (cons part ; M4\n                      (lazy-seq (m5 n m f c u v a b l r s))))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists"
    [["multiset" "r" "s"]
     ["n" "m" "f" "c" "u" "v" "a" "b" "l" "r" "s"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "growth-strings-H",
    "line" 235,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- growth-strings-H\n  ([n r s] ; H1\n    (growth-strings-H n\n                      (init n s)\n                      (vec (repeat n 1))\n                      r\n                      s))\n  ([n a b r s]\n    (cons a   ; begin H2\n          (lazy-seq\n            (if (and (> (peek b) (peek a))\n                     (if r (< (peek a) (dec r)) true)) ; end H2\n              (growth-strings-H n (update a (dec n) inc) b r s)  ; H3\n              (let [j (loop [j (- n 2)] ; begin H4\n                        (if (and (< (a j) (b j))\n                                 (if r\n                                   (< (a j) (dec r))\n                                   true)\n                                 (if s\n                                   (<= (- s (b j) (reify-bool (== (inc (a j)) (b j)))) (- n j))\n                                   true))\n                          j\n                          (recur (dec j))))] ; end H4\n                (if (zero? j) ;begin H5\n                  ()\n                  (let [a (update a j inc) ; end H5\n                        x (when s\n                            (- s\n                               (+ (b j)\n                                  (reify-bool (= (a j) (b j))))))\n                        [a b] (loop [a a\n                                     b b\n                                     i (inc j)\n                                     current-max (+ (b j)\n                                                    (reify-bool (== (b j) (a j))))]\n                                (cond\n                                  (== i n) [a b]\n                                  \n                                  (and s (> i (- (- n x) 1)))\n                                  (let [new-a-i (+ (- i n) s)]\n                                    (recur (assoc a i new-a-i)\n                                           (assoc b i current-max)\n                                           (inc i)\n                                           (max current-max (inc new-a-i))))\n                                  \n                                  :else (recur (assoc a i 0)\n                                               (assoc b i current-max)\n                                               (inc i)\n                                               current-max)))]\n                    (growth-strings-H n a b r s)))))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["n" "r" "s"] ["n" "a" "b" "r" "s"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "lex-permutations",
    "deprecated" "1.3",
    "line" 161,
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
    "line" 137,
    "column" 1,
    "doc"
    "All the ways of taking n (possibly the same) elements from the sequence of items",
    "tag" nil,
    "source"
    "(defn selections\n  \"All the ways of taking n (possibly the same) elements from the sequence of items\"\n  [items n]\n  (apply cartesian-product (take n (repeat items))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items" "n"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "lex-partitions-H",
    "line" 286,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- lex-partitions-H\n  [N & {from :min to :max}]\n  (if (= N 0)\n    (if (<= (or from 0) 0 (or to 0))\n      '(())\n      ())\n    (let [from (if (and from (<= from 1)) nil from)\n          to (if (and to (>= to N)) nil to)]\n      (cond\n        (not (<= 1 (or from 1) (or to N) N)) ()\n        \n        (= N 0) '(())\n        (= N 1) '(([0]))\n        (= to 1) `((~(range N)))\n        :else (let [growth-strings (growth-strings-H N to from)]\n                (for [growth-string growth-strings\n                      :let [groups (group-by growth-string (range N))]]\n                  (map groups (range (count groups)))))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["N" "&" [["from" "min"] ["to" "max"]]]]}
   {"ns" "clojure.math.combinatorics",
    "name" "cartesian-product",
    "line" 117,
    "column" 1,
    "doc" "All the ways to take one item from each sequence",
    "tag" nil,
    "source"
    "(defn cartesian-product\n  \"All the ways to take one item from each sequence\"\n  [& seqs]\n  (let [v-original-seqs (vec seqs)\n\tstep\n\t(fn step [v-seqs]\n\t  (let [increment\n\t\t(fn [v-seqs]\n\t\t  (loop [i (dec (count v-seqs)), v-seqs v-seqs]\n\t\t    (if (= i -1) nil\n\t\t\t(if-let [rst (next (v-seqs i))]\n\t\t\t  (assoc v-seqs i rst)\n\t\t\t  (recur (dec i) (assoc v-seqs i (v-original-seqs i)))))))]\n\t    (when v-seqs\n\t       (cons (map first v-seqs)\n\t\t     (lazy-seq (step (increment v-seqs)))))))]\n    (when (every? seq seqs)\n      (lazy-seq (step v-original-seqs)))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["&" "seqs"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "permutations",
    "line" 189,
    "column" 1,
    "doc"
    "All the distinct permutations of items, lexicographic by index.",
    "tag" nil,
    "source"
    "(defn permutations\n  \"All the distinct permutations of items, lexicographic by index.\"\n  [items]\n  (cond\n   (sorted-numbers? items) (lex-permutations items),\n\n   (apply distinct? items)\n   (let [v (vec items)]\n     (map #(map v %) (lex-permutations (range (count v)))))\n\n   :else\n   (multi-perm items)))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["items"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "m6",
    "line" 447,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- m6  ; M6\n  [n m f c u v a b l r s]\n  (if (= l 0)\n    ()\n    (let [l (dec l)\n          b a\n          a (f l)]\n      (m5 n m f c u v a b l r s))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["n" "m" "f" "c" "u" "v" "a" "b" "l" "r" "s"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "m5",
    "line" 410,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- m5  ; M5\n  [n m f c u v a b l r s]\n  (let [j (loop [j (dec b)]\n            (if (not= (v j) 0)\n              j\n              (recur (dec j))))]\n    (cond\n      (and r\n           (= j a)\n           (< (* (dec (v j)) (- r l))\n              (u j))) (m6 n m f c u v a b l r s)\n      (and (= j a)\n           (= (v j) 1)) (m6 n m f c u v a b l r s)\n      :else (let [v (update v j dec)\n                  diff-uv (if s (apply + (for [i (range a (inc j))]\n                                           (- (u i) (v i)))) nil)\n                  v (loop [ks (range (inc j) b)\n                           v v]\n                      (if (empty? ks)\n                        v\n                        (let [k (first ks)]\n                          (recur (rest ks)\n                                 (assoc v k (u k))))))\n                  min-partitions-after-this (if s (- s (inc l)) 0)\n                  amount-to-dec (if s (max 0 (- min-partitions-after-this diff-uv)) 0)\n                  v (if (= amount-to-dec 0)\n                      v\n                      (loop [k-1 (dec b), v v\n                             amount amount-to-dec]\n                        (let [vk (v k-1)]\n                          (if (> amount vk)\n                            (recur (dec k-1)\n                                   (assoc v k-1 0)\n                                   (- amount vk))\n                            (assoc v k-1 (- vk amount))))))]\n              (multiset-partitions-M n m f c u v a b l r s)))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["n" "m" "f" "c" "u" "v" "a" "b" "l" "r" "s"]]}
   {"ns" "clojure.math.combinatorics",
    "name" "combinations",
    "line" 88,
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
    "line" 179,
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
    "line" 69,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- index-combinations\n  [n cnt]\n  (lazy-seq\n   (let [c (vec (cons nil (for [j (range 1 (inc n))] (+ j cnt (- (inc n)))))),\n\t iter-comb\n\t (fn iter-comb [c j]\n\t   (if (> j n) nil\n\t       (let [c (assoc c j (dec (c j)))]\n\t\t (if (< (c j) j) [c (inc j)]\n\t\t     (loop [c c, j j]\n\t\t       (if (= j 1) [c j]\n\t\t\t   (recur (assoc c (dec j) (dec (c j))) (dec j)))))))),\n\t step\n\t (fn step [c j]\n\t   (cons (rseq (subvec c 1 (inc n)))\n\t\t (lazy-seq (let [next-step (iter-comb c j)]\n\t\t\t     (when next-step (step (next-step 0) (next-step 1)))))))]\n     (step c 1))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["n" "cnt"]]}
   {"private" true,
    "ns" "clojure.math.combinatorics",
    "name" "iter-perm",
    "line" 143,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- iter-perm [v]\n  (let [len (count v),\n\tj (loop [i (- len 2)]\n\t     (cond (= i -1) nil\n\t\t   (< (v i) (v (inc i))) i\n\t\t   :else (recur (dec i))))]\n    (when j\n      (let [vj (v j),\n\t    l (loop [i (dec len)]\n\t\t(if (< vj (v i)) i (recur (dec i))))]\n\t(loop [v (assoc v j (v l) l vj), k (inc j), l (dec len)]\n\t  (if (< k l)\n\t    (recur (assoc v k (v l) l (v k)) (inc k) (dec l))\n\t    v))))))",
    "file" "clojure/math/combinatorics.clj",
    "arglists" [["v"]]}]},
 "description" "math.combinatorics 0.0.7",
 "version" "0.0.7",
 "name" "clojure.math.combinatorics",
 "group" "clojure.math.combinatorics"}
