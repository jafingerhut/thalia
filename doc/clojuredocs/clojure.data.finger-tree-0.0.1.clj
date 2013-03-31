{"namespaces"
 {"clojure.data.finger-tree"
  [{"ns" "clojure.data.finger-tree",
    "name" "newEmptyTree",
    "static" true,
    "line" 200,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static newEmptyTree [meter-obj]\n  (EmptyTree. meter-obj))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "SplitAt",
    "line" 49,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol SplitAt\n  (ft-split-at [o k notfound] [o k]\n               \"Return [pre m post] where pre and post are trees\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return the identity element for this meter",
    "arglists" [["_"]],
    "name" "idElem"}
   {"ns" "clojure.data.finger-tree",
    "name" "->DoubleList",
    "line" 392,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.DoubleList.",
    "tag" nil,
    "source"
    "(deftype DoubleList [tree]\n  Sequential\n  Seqable\n    (seq [this] (when (seq tree) this))\n  IPersistentCollection\n    (cons [this x] (conjr this x))\n    (count [_] (count (seq tree))) ; Slow!\n    (empty [_] (DoubleList. (empty tree)))\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] (first tree))\n    (more [_] (DoubleList. (rest tree)))\n    (next [_] (if-let [t (next tree)] (DoubleList. t)))\n  IPersistentStack ; actually, queue\n    (peek [_] (peek tree))\n    (pop [_] (DoubleList. (pop tree)))\n  Reversible\n    (rseq [_] (rseq tree)) ; not 'this' because tree ops can't be reversed\n  DoubleSeq\n    (consl [_ a] (DoubleList. (consl tree a)))\n    (conjr [_ b] (DoubleList. (conjr tree b)))\n  Measured\n    (measured [_] (measured tree))\n    (getMeter [_] (getMeter tree))\n  Tree\n    (app3 [_ ts t2] (DoubleList. (app3 tree ts t2)))\n    (app3deep [_ ts t1] (DoubleList. (app3deep tree ts t1))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["tree"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Append b to the right-hand side of s",
    "arglists" [["s" "b"]],
    "name" "conjr"}
   {"arglists" [["pre" "m" "suf"]],
    "ns" "clojure.data.finger-tree",
    "name" "deep-left",
    "column" 1,
    "line" 287,
    "source"
    "(defn deep-left [pre m suf]\n  (cond\n    (seq pre) (deep pre m suf)\n    (empty? (first m)) (to-tree (getMeter suf) suf)\n    :else (deep (first m)\n                (delay-ft (rest m) (measureMore m))\n                suf)))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.data.finger-tree",
    "name" "defdigit",
    "macro" true,
    "line" 70,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private defdigit [& items]\n  (let [i (gensym \"i_\")\n        p (gensym \"p_\")\n        o (gensym \"o_\")\n        typename (symbol (str \"Digit\" (count items)))\n        this-items (map #(list (keyword %) o) items)]\n   `(deftype ~typename [~@items ~'meter-obj ~'measure-ref]\n      Seqable\n        (seq [_] ~(reduce #(list `cons %2 %1) nil (reverse items)))\n      Indexed\n        (count [_] ~(count items)) ; not needed?\n        (nth [_ ~i notfound#]\n          (cond ~@(mapcat (fn [sym n] [`(== ~i (int ~n)) sym])\n                          items\n                          (range (count items)))\n            :else notfound#))\n      IPersistentCollection\n        (cons [this# x#] (conjr this# x#))\n        (empty [_]) ; TBD ; not needed?\n        (equiv [_ x] false) ; TBD\n      ISeq\n        (first     [_] ~(first items))\n        (more      [_] ~(if (> (count items) 1)\n                          `(digit ~'meter-obj ~@(next items))\n                          `(newEmptyTree ~'meter-obj)))\n        (next      [_] ~(when (> (count items) 1)\n                          `(digit ~'meter-obj ~@(next items))))\n      IPersistentStack\n        (peek      [_] ~(last items))\n        (pop       [_] ~(if (> (count items) 1)\n                          `(digit ~'meter-obj ~@(drop-last items))\n                          `(newEmptyTree ~'meter-obj)))\n      DoubleSeq\n        (consl [_ x#] (digit ~'meter-obj x# ~@items))\n        (conjr [_ x#] (digit ~'meter-obj ~@items x#))\n      Measured\n        (measured [_] @~'measure-ref)\n        (getMeter [_] ~'meter-obj) ; not needed?\n      Splittable ; allow to fail if op is nil:\n        (split [_ ~p ~i]\n          ~(letfn [(step [ips [ix & ixs]]\n                      (if (empty? ixs)\n                        [(when ips `(digit ~'meter-obj ~@ips))\n                         ix\n                         nil]\n                        `(let [~i ((opfn ~'meter-obj)\n                                     ~i\n                                     (measure ~'meter-obj ~ix))]\n                           (if (~p ~i)\n                             [~(when ips\n                                 `(digit ~'meter-obj ~@ips))\n                              ~ix\n                              (digit ~'meter-obj ~@ixs)]\n                             ~(step (concat ips [ix]) ixs)))))]\n             (step nil items))))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["&" "items"]]}
   {"arglists" [["t" "p"]],
    "ns" "clojure.data.finger-tree",
    "name" "split-tree",
    "column" 1,
    "line" 383,
    "source"
    "(defn split-tree [t p]\n  (split t p (idElem (getMeter t))))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"arglists" [["&" "args"]],
    "ns" "clojure.data.finger-tree",
    "name" "double-list",
    "column" 1,
    "line" 420,
    "source"
    "(defn double-list [& args]\n  (into (DoubleList. (EmptyTree. nil)) args))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.data.finger-tree",
    "name" "p",
    "line" 594,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- p [t & xs]\n  (print \"<\")\n  (print t)\n  (doseq [x xs]\n    (print \" \")\n    (print-tree x))\n  (print \">\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["t" "&" "xs"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return [pre m post] where pre and post are trees",
    "arglists" [["o" "pred" "acc"]],
    "name" "split"}
   {"arglists" [["t1" "t2"]],
    "ns" "clojure.data.finger-tree",
    "name" "ft-concat",
    "column" 1,
    "line" 386,
    "source"
    "(defn ft-concat [t1 t2]\n  (assert (= (getMeter t1) (getMeter t2))) ;meters must be the same\n  (app3 t1 nil t2))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "PrintableTree",
    "line" 591,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol PrintableTree\n  (print-tree [tree]))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "finger-meter",
    "static" true,
    "line" 203,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static finger-meter [meter-obj]\n  (when meter-obj\n    (meter\n      #(measured %)\n      (idElem meter-obj)\n      (opfn meter-obj))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "ObjMeter",
    "line" 36,
    "column" 1,
    "doc"
    "Object for annotating tree elements.  idElem and op together form a Monoid.",
    "tag" nil,
    "source"
    "(defprotocol ObjMeter\n  \"Object for annotating tree elements.  idElem and op together form a Monoid.\"\n  (measure [_ o] \"Return the measured value of o (same type as idElem)\")\n  (idElem [_] \"Return the identity element for this meter\")\n  (opfn [_] \"Return an associative function of two args for combining measures\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"arglists"
    [["meter-obj" "a"]
     ["meter-obj" "a" "b"]
     ["meter-obj" "a" "b" "c"]
     ["meter-obj" "a" "b" "c" "d"]],
    "ns" "clojure.data.finger-tree",
    "name" "digit",
    "column" 1,
    "line" 147,
    "source"
    "(defn digit\n  ([meter-obj a]       (make-digit meter-obj a))\n  ([meter-obj a b]     (make-digit meter-obj a b))\n  ([meter-obj a b c]   (make-digit meter-obj a b c))\n  ([meter-obj a b c d] (make-digit meter-obj a b c d)))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "->SingleTree",
    "line" 210,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.SingleTree.",
    "tag" nil,
    "source"
    "(deftype SingleTree [meter-obj x]\n  Seqable\n    (seq [this] this)\n  IPersistentCollection\n    (cons [this x] (conjr this x))\n    (count [_]) ; not needed?\n    (empty [_] (EmptyTree. meter-obj)) ; not needed?\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] x)\n    (more [_] (EmptyTree. meter-obj))\n    (next [_] nil)\n  IPersistentStack\n    (peek [_] x)\n    (pop [_] (EmptyTree. meter-obj))\n  Reversible\n    (rseq [_] (list x)) ; not 'this' because tree ops can't be reversed\n  DoubleSeq\n    (consl [_ a] (deep (digit meter-obj a)\n                       (EmptyTree. (finger-meter meter-obj))\n                       (digit meter-obj x)))\n    (conjr [_ b] (deep (digit meter-obj x)\n                       (EmptyTree. (finger-meter meter-obj))\n                       (digit meter-obj b)))\n  Measured\n    (measured [_] (measure meter-obj x))\n    (getMeter [_] meter-obj) ; not needed?\n  Splittable\n    (split [this pred acc] (let [e (empty this)] [e x e]))\n  Tree\n    (app3 [this ts t2] (consl (app3 (empty this) ts t2) x))\n    (app3deep [_ ts t1] (conjr (reduce conjr t1 ts) x))\n    (measureMore [_] (idElem meter-obj))\n    (measurePop [_] (idElem meter-obj)))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "x"]]}
   {"private" true,
    "ns" "clojure.data.finger-tree",
    "name" "measured3",
    "line" 303,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:private measured3 [meter-obj pre m suf]\n  (when-let [op (opfn meter-obj)]\n    (op\n      (op (measured pre)\n          (measured m))\n        (measured suf))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "pre" "m" "suf"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "Splittable",
    "line" 46,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol Splittable\n  (split [o pred acc] \"Return [pre m post] where pre and post are trees\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "DoubleSeq",
    "line" 32,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol DoubleSeq\n  (consl [s a] \"Append a to the left-hand side of s\")\n  (conjr [s b] \"Append b to the right-hand side of s\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "->DelayedTree",
    "line" 248,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.DelayedTree.",
    "tag" nil,
    "source"
    "(deftype DelayedTree [tree-ref mval]\n  Seqable\n    (seq [this] this)\n  IPersistentCollection\n    (cons [this x] (conjr this x))\n    (count [_]) ; not needed?\n    (empty [_] (empty @tree-ref))\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] (first @tree-ref))\n    (more [_] (rest @tree-ref))\n    (next [_] (next @tree-ref))\n  IPersistentStack\n    (peek [_] (peek @tree-ref))\n    (pop [_] (pop @tree-ref))\n  Reversible\n    (rseq [_] (rseq @tree-ref)) ; not this because tree ops can't be reversed\n  DoubleSeq\n    (consl [_ a] (consl @tree-ref a))\n    (conjr [_ b] (conjr @tree-ref b))\n  Measured\n    (measured [_] mval)\n    (getMeter [_] (getMeter @tree-ref)) ; not needed?\n  Splittable\n    (split [_ pred acc] (split @tree-ref pred acc))\n  Tree\n    (app3 [_ ts t2] (app3 @tree-ref ts t2))\n    (app3deep [_ ts t1] (app3deep @tree-ref ts t1))\n    (measureMore [_] (measureMore @tree-ref))\n    (measurePop [_] (measurePop @tree-ref)))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["tree-ref" "mval"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "->Len-Right-Meter",
    "line" 497,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.Len-Right-Meter.",
    "tag" nil,
    "source" "(defrecord Len-Right-Meter [^int len right])",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["len" "right"]]}
   {"source"
    "  (defn counted-sorted-set [& args]\n    (into default-empty-set args))",
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "name" "counted-sorted-set",
    "arglists" [["&" "args"]],
    "column" 3,
    "line" 586,
    "file" "clojure/data/finger_tree.clj"}
   {"arglists" [["pre" "m" "suf"]],
    "ns" "clojure.data.finger-tree",
    "name" "deep-right",
    "column" 1,
    "line" 295,
    "source"
    "(defn deep-right [pre m suf]\n  (cond\n    (seq suf) (deep pre m suf)\n    (empty? (peek m)) (to-tree (getMeter pre) pre)\n    :else (deep pre\n                (delay-ft (pop m) (measurePop m))\n                (peek m))))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "->DeepTree",
    "line" 319,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.DeepTree.",
    "tag" nil,
    "source"
    "(deftype DeepTree [meter-obj pre mid suf mval]\n  Seqable\n    (seq [this] this)\n  IPersistentCollection\n    (cons [this x] (conjr this x))\n    (count [_]) ; not needed?\n    (empty [_] (newEmptyTree meter-obj))\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] (first pre))\n    (more [_] (deep-left (rest pre) mid suf))\n    (next [this] (seq (rest this)))\n  IPersistentStack\n    (peek [_] (peek suf))\n    (pop [_] (deep-right pre mid (pop suf)))\n  Reversible\n    (rseq [this] (lazy-seq (cons (peek this) (rseq (pop this)))))\n  DoubleSeq\n    (consl [_ a] (if (< (count pre) 4)\n                   (deep (consl pre a) mid suf)\n                   (let [[b c d e] pre\n                         n (digit meter-obj c d e)]\n                     (deep (digit meter-obj a b) (consl mid n) suf))))\n    (conjr [_ a] (if (< (count suf) 4)\n                   (deep pre mid (conjr suf a))\n                   (let [[e d c b] suf\n                         n (digit meter-obj e d c)]\n                     (deep pre (conjr mid n) (digit meter-obj b a)))))\n  Measured\n    (measured [_] @mval)\n    (getMeter [_] (getMeter pre)) ; not needed?\n  Splittable ; allow to fail if op is nil:\n    (split [_ pred acc]\n      (let [op (opfn meter-obj)\n            vpr (op acc (measured pre))]\n        (if (pred vpr)\n          (let [[sl sx sr] (split pre pred acc)]\n            [(to-tree meter-obj sl) sx (deep-left sr mid suf)])\n          (let [vm (op vpr (measured mid))]\n            (if (pred vm)\n              (let [[ml xs mr] (split mid pred vpr)\n                    [sl sx sr] (split xs pred (op vpr (measured ml)))]\n                [(deep-right pre ml sl) sx (deep-left sr mr suf)])\n              (let [[sl sx sr] (split suf pred vm)]\n                [(deep-right pre mid sl)\n                  sx\n                  (to-tree meter-obj sr)]))))))\n  Tree\n    (app3 [this ts t2] (app3deep t2 ts this))\n    (app3deep [_ ts t1]\n      (deep (.pre ^DeepTree t1)\n            (app3 (.mid ^DeepTree t1)\n                  (nodes meter-obj (concat (.suf ^DeepTree t1) ts pre))\n                  mid)\n            suf))\n    (measureMore [this] (measured3 meter-obj (rest pre) mid suf))\n    (measurePop  [this] (measured3 meter-obj pre mid (pop suf))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "pre" "mid" "suf" "mval"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "newSingleTree",
    "static" true,
    "line" 245,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static newSingleTree [meter-obj x]\n  (SingleTree. meter-obj x))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "x"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return the measured value of o",
    "arglists" [["o"]],
    "name" "measured"}
   {"source"
    "  (defn counted-sorted-set-by [cmpr & args]\n    (into (CountedSortedSet. cmpr empty-tree) args))",
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "name" "counted-sorted-set-by",
    "arglists" [["cmpr" "&" "args"]],
    "column" 3,
    "line" 584,
    "file" "clojure/data/finger_tree.clj"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return the measure of o not including the rightmost item",
    "arglists" [["o"]],
    "name" "measurePop"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return the measured value of o (same type as idElem)",
    "arglists" [["_" "o"]],
    "name" "measure"}
   {"ns" "clojure.data.finger-tree",
    "name" "->Digit2",
    "line" 142,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.Digit2.",
    "tag" nil,
    "source" "(defdigit a b)",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["a" "b" "meter-obj" "measure-ref"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "->Digit3",
    "line" 143,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.Digit3.",
    "tag" nil,
    "source" "(defdigit a b c)",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["a" "b" "c" "meter-obj" "measure-ref"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "meter",
    "macro" true,
    "line" 135,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro meter [measure idElem op]\n  `(reify ObjMeter\n      (measure [_ a#] (~measure a#))\n      (idElem [_] ~idElem)\n      (opfn [_] ~op)))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["measure" "idElem" "op"]]}
   {"private" true,
    "ns" "clojure.data.finger-tree",
    "name" "make-digit",
    "macro" true,
    "line" 126,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private make-digit [meter-obj & items]\n  (let [typename (symbol (str \"Digit\" (count items)))]\n    `(let [~'mobj ~meter-obj\n           ~'op (opfn ~'mobj)]\n       (new ~typename ~@items ~'mobj\n            (when ~'op\n              (delay ~(reduce #(list 'op %1 %2)\n                              (map #(list `measure 'mobj %) items))))))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "&" "items"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "->Digit1",
    "line" 141,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.Digit1.",
    "tag" nil,
    "source" "(defdigit a)",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["a" "meter-obj" "measure-ref"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "nodes",
    "static" true,
    "line" 153,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static nodes [mfns xs]\n  (let [v (vec xs), c (count v)]\n    (seq\n      (loop [i (int 0), nds []]\n        (condp == (- c i)\n          (int 2) (-> nds (conj (digit mfns (v i) (v (+ (int 1) i)))))\n          (int 3) (-> nds (conj (digit mfns (v i) (v (+ (int 1) i))\n                                       (v (+ (int 2) i)))))\n          (int 4) (-> nds (conj (digit mfns (v i) (v (+ (int 1) i))))\n                    (conj (digit mfns (v (+ (int 2) i))\n                                 (v (+ (int 3) i)))))\n          (recur (+ (int 3) i)\n                 (-> nds\n                   (conj (digit mfns (v i) (v (+ (int 1) i))\n                                (v (+ (int 2) i)))))))))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["mfns" "xs"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Append a to the left-hand side of s",
    "arglists" [["s" "a"]],
    "name" "consl"}
   {"ns" "clojure.data.finger-tree",
    "name" "Measured",
    "line" 42,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol Measured\n  (measured [o] \"Return the measured value of o\")\n  (getMeter [o] \"Return the meter object for o\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "to-tree",
    "static" true,
    "line" 284,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static to-tree [meter-obj coll]\n  (reduce conjr (EmptyTree. meter-obj) coll))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "coll"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "->EmptyTree",
    "line" 169,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.EmptyTree.",
    "tag" nil,
    "source"
    "(deftype EmptyTree [meter-obj]\n  Seqable\n    (seq [_] nil)\n  IPersistentCollection\n    (cons [this x] (conjr this x))\n    (count [_] 0) ; not needed?\n    (empty [this] this)\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] nil)\n    (more [this] this)\n    (next [_] nil)\n  IPersistentStack\n    (peek [_] nil)\n    (pop [this] this)\n  Reversible\n    (rseq [_] nil)\n  DoubleSeq\n    (consl [_ a] (newSingleTree meter-obj a))\n    (conjr [_ b] (newSingleTree meter-obj b))\n  Measured\n    (measured [_] (idElem meter-obj))\n    (getMeter [_] meter-obj) ; not needed?\n;  Splittable\n;    (split [pred acc]) ; TBD -- not needed??\n  Tree\n    (app3 [_ ts t2] (reduce consl t2 (reverse ts)))\n    (app3deep [_ ts t1] (reduce conjr t1 ts))\n    (measureMore [_] (idElem meter-obj))\n    (measurePop [_] (idElem meter-obj)))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" nil,
    "arglists" [["tree"]],
    "name" "print-tree"}
   {"ns" "clojure.data.finger-tree",
    "name" "Tree",
    "line" 53,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol Tree\n  (app3 [t1 ts t2] \"Append ts and (possibly deep) t2 to tree t1\")\n  (app3deep [t2 ts t1] \"Append ts and t2 to deep tree t1\")\n  (measureMore [o] \"Return the measure of o not including the leftmost item\")\n  (measurePop [o] \"Return the measure of o not including the rightmost item\"))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return [pre m post] where pre and post are trees",
    "arglists" [["o" "k" "notfound"] ["o" "k"]],
    "name" "ft-split-at"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Append ts and (possibly deep) t2 to tree t1",
    "arglists" [["t1" "ts" "t2"]],
    "name" "app3"}
   {"private" true,
    "ns" "clojure.data.finger-tree",
    "name" "delay-ft",
    "macro" true,
    "line" 279,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private delay-ft [tree-expr mval]\n  `(DelayedTree. (delay ~tree-expr) ~mval))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["tree-expr" "mval"]]}
   {"arglists" [["pre" "m" "suf"]],
    "ns" "clojure.data.finger-tree",
    "name" "deep",
    "column" 1,
    "line" 310,
    "source"
    "(defn deep [pre m suf]\n  (let [meter-obj (getMeter pre)\n        op (opfn meter-obj)]\n    (newDeepTree meter-obj pre m suf\n                 (when op\n                   (delay (if (seq m)\n                            (measured3 meter-obj pre m suf)\n                            (op (measured pre) (measured suf))))))))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"ns" "clojure.data.finger-tree",
    "name" "->CountedDoubleList",
    "line" 423,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.CountedDoubleList.",
    "tag" nil,
    "source"
    "(deftype CountedDoubleList [tree]\n  Sequential\n  Seqable\n    (seq [this] (when (seq tree) this))\n  IPersistentCollection\n    (cons [this x] (conjr this x))\n    (empty [_] (CountedDoubleList. (empty tree)))\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] (first tree))\n    (more [_] (CountedDoubleList. (rest tree)))\n    (next [_] (if-let [t (next tree)] (CountedDoubleList. t)))\n  IPersistentStack\n    (peek [_] (peek tree))\n    (pop [_] (CountedDoubleList. (pop tree)))\n  Reversible\n    (rseq [_] (rseq tree)) ; not 'this' because tree ops can't be reversed\n  DoubleSeq\n    (consl [_ a] (CountedDoubleList. (consl tree a)))\n    (conjr [_ b] (CountedDoubleList. (conjr tree b)))\n  Measured\n    (measured [_] (measured tree))\n    (getMeter [_] (getMeter tree)) ; not needed?\n  SplitAt\n    (ft-split-at [this n notfound]\n      (cond\n        (< n 0) [(empty this) notfound this]\n        (< n (count this))\n          (let [[pre m post] (split-tree tree #(> % n))]\n            [(CountedDoubleList. pre) m (CountedDoubleList. post)])\n        :else [this notfound (empty this)]))\n    (ft-split-at [this n]\n      (ft-split-at this n nil))\n  Tree\n    (app3 [_ ts t2]\n      (CountedDoubleList. (app3 tree ts (.tree ^CountedDoubleList t2))))\n    ;(app3deep [_ ts t1] (CountedDoubleList. (app3deep tree ts t1)))\n    (measureMore [_] (measureMore tree))\n    (measurePop [_] (measurePop tree))\n  Counted\n    (count [_] (measured tree))\n  Associative\n    (assoc [this k v]\n      (cond\n        (== k -1) (consl this v)\n        (== k (measured tree)) (conjr this v)\n        (< -1 k (measured tree))\n          (let [[pre mid post] (split-tree tree #(> % k))]\n            (CountedDoubleList. (ft-concat (conjr pre v) post)))\n        :else (throw (IndexOutOfBoundsException.))))\n    (containsKey [_ k] (< -1 k (measured tree)))\n    (entryAt [_ n] (clojure.lang.MapEntry.\n                     n (second (split-tree tree #(> % n)))))\n    (valAt [this n notfound] (if (.containsKey this n)\n                               (second (split-tree tree #(> % n)))\n                               notfound))\n    (valAt [this n] (.valAt this n nil))\n  Indexed\n    (nth [this n notfound] (if (.containsKey this n)\n                             (second (split-tree tree #(> % n)))\n                             notfound))\n    (nth [this n] (if (.containsKey this n)\n                    (second (split-tree tree #(> % n)))\n                    (throw (IndexOutOfBoundsException.)))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["tree"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "newDeepTree",
    "static" true,
    "line" 377,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static newDeepTree [meter-obj pre mid suf mval]\n  (DeepTree. meter-obj pre mid suf mval))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "pre" "mid" "suf" "mval"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc"
    "Return an associative function of two args for combining measures",
    "arglists" [["_"]],
    "name" "opfn"}
   {"ns" "clojure.data.finger-tree",
    "name" "finger-tree",
    "static" true,
    "line" 380,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^:static finger-tree [meter-obj & xs]\n  (to-tree meter-obj xs))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["meter-obj" "&" "xs"]]}
   {"ns" "clojure.data.finger-tree",
    "name" "->CountedSortedSet",
    "line" 499,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.CountedSortedSet.",
    "tag" nil,
    "source"
    "(deftype CountedSortedSet [cmpr tree]\n  Sequential\n  Seqable\n    (seq [this] (when (seq tree) this))\n  IPersistentCollection\n    (cons [this value]\n      (if (empty? tree)\n        (CountedSortedSet. cmpr (conjr tree value))\n        (let [[l x r] (split-tree tree #(>= 0 (cmpr value (:right %))))\n              compared (cmpr value x)]\n          (if (zero? compared)\n            this ; already in set\n            (let [[a b] (if (>= 0 compared) [value x] [x value])]\n              (CountedSortedSet. cmpr (ft-concat (conjr l a) (consl r b))))))))\n    (empty [_] (CountedSortedSet. cmpr (empty tree)))\n    (equiv [_ x] false) ; TBD\n  ISeq\n    (first [_] (first tree))\n    (more [_] (CountedSortedSet. cmpr (rest tree)))\n    (next [_] (if-let [t (next tree)] (CountedSortedSet. cmpr t)))\n  IPersistentStack\n    (peek [_] (peek tree))\n    (pop [_] (CountedSortedSet. cmpr (pop tree)))\n  Reversible\n    (rseq [_] (rseq tree)) ; not 'this' because tree ops can't be reversed\n  Measured\n    (measured [_] (measured tree))\n    (getMeter [_] (getMeter tree)) ; not needed?\n  SplitAt\n    (ft-split-at [this n notfound]\n      (cond\n        (< n 0) [(empty this) notfound this]\n        (< n (count this)) (let [[l x r] (split-tree tree #(> (:len %) n))]\n                             [(CountedSortedSet. cmpr l) x\n                              (CountedSortedSet. cmpr r)])\n        :else [this notfound (empty this)]))\n    (ft-split-at [this n]\n      (ft-split-at this n nil))\n  Counted\n    (count [_] (:len (measured tree)))\n  ILookup\n    (valAt [_ k notfound]\n      (if (empty? tree)\n        notfound\n        (let [x (second (split-tree tree #(>= 0 (cmpr k (:right %)))))]\n          (if (= x k)\n            k\n            notfound))))\n    (valAt [this k]\n      (.valAt this k nil))\n  IPersistentSet\n    (disjoin [this k]\n      (if (empty? tree)\n        this\n        (let [[l x r] (split-tree tree #(>= 0 (cmpr k (:right %))))]\n          (if (= x k)\n            (CountedSortedSet. cmpr (ft-concat l r))\n            this))))\n    (get [this k] (.valAt this k nil))\n  Indexed\n    (nth [this n notfound] (if (< -1 n (:len (measured tree)))\n                             (second (split-tree tree #(> (:len %) n)))\n                             notfound))\n    (nth [this n] (if (< -1 n (:len (measured tree)))\n                    (second (split-tree tree #(> (:len %) n)))\n                    (throw (IndexOutOfBoundsException.))))\n  Sorted\n    (comparator [_] cmpr)\n    (entryKey [_ x] x)\n    (seq [this ascending?] (if ascending?  (.seq this) (rseq tree)))\n    (seqFrom [_ k ascending?]\n      (let [[l x r] (split-tree tree #(>= 0 (cmpr k (:right %))))]\n        (if ascending?\n          (CountedSortedSet. cmpr (consl r x))\n          (rseq (conjr l x))))))",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["cmpr" "tree"]]}
   {"arglists" [["&" "args"]],
    "ns" "clojure.data.finger-tree",
    "name" "counted-double-list",
    "column" 1,
    "line" 493,
    "source"
    "(defn counted-double-list [& args]\n  (into empty-counted-double-list args))",
    "file" "clojure/data/finger_tree.clj",
    "tag" nil}
   {"source"
    "  (def empty-counted-double-list\n    (CountedDoubleList. (EmptyTree. len-meter)))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "name" "empty-counted-double-list",
    "column" 3,
    "line" 490,
    "file" "clojure/data/finger_tree.clj"}
   {"ns" "clojure.data.finger-tree",
    "name" "map->Len-Right-Meter",
    "line" 497,
    "column" 1,
    "doc"
    "Factory function for class clojure.data.finger_tree.Len-Right-Meter, taking a map of keywords to field values.",
    "tag" nil,
    "source" "(defrecord Len-Right-Meter [^int len right])",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["m__5818__auto__"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return the measure of o not including the leftmost item",
    "arglists" [["o"]],
    "name" "measureMore"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Append ts and t2 to deep tree t1",
    "arglists" [["t2" "ts" "t1"]],
    "name" "app3deep"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.finger-tree",
    "doc" "Return the meter object for o",
    "arglists" [["o"]],
    "name" "getMeter"}
   {"ns" "clojure.data.finger-tree",
    "name" "->Digit4",
    "line" 144,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.finger_tree.Digit4.",
    "tag" nil,
    "source" "(defdigit a b c d)",
    "file" "clojure/data/finger_tree.clj",
    "arglists" [["a" "b" "c" "d" "meter-obj" "measure-ref"]]}]},
 "description" "data.finger-tree 0.0.1",
 "version" "0.0.1",
 "name" "clojure.data.finger-tree",
 "group" "clojure.data.finger-tree"}
