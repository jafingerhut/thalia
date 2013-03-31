{"namespaces"
 {"clojure.core.cache"
  [{"ns" "clojure.core.cache",
    "name" "lru-cache-factory",
    "line" 575,
    "column" 1,
    "doc"
    "Returns an LRU cache with the cache and usage-table initialied to `base` --\n   each entry is initialized with the same usage value.\n\n   This function takes an optional `:threshold` argument that defines the maximum number\n   of elements in the cache before the LRU semantics apply (default is 32).",
    "tag" nil,
    "source"
    "(defn lru-cache-factory\n  \"Returns an LRU cache with the cache and usage-table initialied to `base` --\n   each entry is initialized with the same usage value.\n\n   This function takes an optional `:threshold` argument that defines the maximum number\n   of elements in the cache before the LRU semantics apply (default is 32).\"\n  [base & {threshold :threshold :or {threshold 32}}]\n  {:pre [(number? threshold) (< 0 threshold)\n         (map? base)]}\n  (clojure.core.cache/seed (LRUCache. {} {} 0 threshold) base))",
    "file" "clojure/core/cache.clj",
    "arglists"
    [["base"
      "&"
      [["threshold" "threshold"] ["or" [["threshold" 32]]]]]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.cache",
    "doc" "Removes an entry from the cache",
    "arglists" [["cache" "e"]],
    "name" "evict"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.cache",
    "doc"
    "Is meant to be called if the cache is determined to **not** contain a\n   value associated with `e`",
    "arglists" [["cache" "e" "ret"]],
    "name" "miss"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.cache",
    "doc"
    "Is used to signal that the cache should be created with a seed.\n   The contract is that said cache should return an instance of its\n   own type.",
    "arglists" [["cache" "base"]],
    "name" "seed"}
   {"ns" "clojure.core.cache",
    "name" "CacheProtocol",
    "line" 19,
    "column" 1,
    "doc"
    "This is the protocol describing the basic cache capability.",
    "tag" nil,
    "source"
    "(defprotocol CacheProtocol\n  \"This is the protocol describing the basic cache capability.\"\n  (lookup [cache e]\n          [cache e not-found]\n   \"Retrieve the value associated with `e` if it exists, else `nil` in\n   the 2-arg case.  Retrieve the value associated with `e` if it exists,\n   else `not-found` in the 3-arg case.\")\n  (has?    [cache e]\n   \"Checks if the cache contains a value associtaed with `e`\")\n  (hit     [cache e]\n   \"Is meant to be called if the cache is determined to contain a value\n   associated with `e`\")\n  (miss    [cache e ret]\n   \"Is meant to be called if the cache is determined to **not** contain a\n   value associated with `e`\")\n  (evict  [cache e]\n   \"Removes an entry from the cache\")\n  (seed    [cache base]\n   \"Is used to signal that the cache should be created with a seed.\n   The contract is that said cache should return an instance of its\n   own type.\"))",
    "file" "clojure/core/cache.clj",
    "arglists" nil}
   {"ns" "clojure.core.cache",
    "name" "fifo-cache-factory",
    "line" 554,
    "column" 1,
    "doc"
    "Returns a FIFO cache with the cache and FIFO queue initialized to `base` --\n   the queue is filled as the values are pulled out of `base`.  If the associative\n   structure can guarantee ordering, then the said ordering will define the\n   eventual eviction order.  Otherwise, there are no guarantees for the eventual\n   eviction ordering.\n\n   This function takes an optional `:threshold` argument that defines the maximum number\n   of elements in the cache before the FIFO semantics apply (default is 32).\n\n   If the number of elements in `base` is greater than the limit then some items\n   in `base` will be dropped from the resulting cache.  If the associative\n   structure used as `base` can guarantee sorting, then the last `limit` elements\n   will be used as the cache seed values.  Otherwise, there are no guarantees about\n   the elements in the resulting cache.",
    "tag" nil,
    "source"
    "(defn fifo-cache-factory\n  \"Returns a FIFO cache with the cache and FIFO queue initialized to `base` --\n   the queue is filled as the values are pulled out of `base`.  If the associative\n   structure can guarantee ordering, then the said ordering will define the\n   eventual eviction order.  Otherwise, there are no guarantees for the eventual\n   eviction ordering.\n\n   This function takes an optional `:threshold` argument that defines the maximum number\n   of elements in the cache before the FIFO semantics apply (default is 32).\n\n   If the number of elements in `base` is greater than the limit then some items\n   in `base` will be dropped from the resulting cache.  If the associative\n   structure used as `base` can guarantee sorting, then the last `limit` elements\n   will be used as the cache seed values.  Otherwise, there are no guarantees about\n   the elements in the resulting cache.\"\n  [base & {threshold :threshold :or {threshold 32}}]\n  {:pre [(number? threshold) (< 0 threshold)\n         (map? base)]\n   :post [(== threshold (count (.q ^FIFOCache %)))]}\n  (clojure.core.cache/seed (FIFOCache. {} clojure.lang.PersistentQueue/EMPTY threshold) base))",
    "file" "clojure/core/cache.clj",
    "arglists"
    [["base"
      "&"
      [["threshold" "threshold"] ["or" [["threshold" 32]]]]]]}
   {"private" true,
    "ns" "clojure.core.cache",
    "name" "prune-stack",
    "line" 399,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- prune-stack [lruS lruQ cache]\n  (loop [s lruS q lruQ c cache]\n    (let [k (apply min-key s (keys s))]\n      (if (or (contains? q k)               ; HIR item\n              (not (contains? c k)))        ; non-resident HIR item\n        (recur (dissoc s k) q c)\n        s))))",
    "file" "clojure/core/cache.clj",
    "arglists" [["lruS" "lruQ" "cache"]]}
   {"ns" "clojure.core.cache",
    "name" "make-reference",
    "line" 486,
    "column" 1,
    "tag" nil,
    "source"
    "(defn ^{:dynamic true} make-reference [v rq]\n  (if (nil? v)\n    (SoftReference. ::nil rq)\n    (SoftReference. v rq)))",
    "file" "clojure/core/cache.clj",
    "dynamic" true,
    "arglists" [["v" "rq"]]}
   {"ns" "clojure.core.cache",
    "name" "->FnCache",
    "line" 105,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.FnCache.",
    "tag" nil,
    "source"
    "(defcache FnCache [cache f]\n  CacheProtocol\n  (lookup [_ item]\n    (f (get cache item)))\n  (lookup [_ item not-found]\n    (let [ret (get cache item not-found)]\n      (if (= ret not-found)\n        not-found\n        (f ret))))\n  (has? [_ item]\n    (contains? cache item))\n  (hit [this item] this)\n  (miss [_ item result]\n    (BasicCache. (assoc cache item result)))\n  (evict [_ key]\n    (BasicCache. (dissoc cache key)))\n  (seed [_ base]\n    (BasicCache. base))\n  Object\n  (toString [_] (str cache)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "f"]]}
   {"private" true,
    "ns" "clojure.core.cache",
    "name" "prune-queue",
    "line" 143,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- prune-queue [q ks]\n  (into clojure.lang.PersistentQueue/EMPTY\n        (filter (complement (set ks)) q)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["q" "ks"]]}
   {"ns" "clojure.core.cache",
    "name" "->LUCache",
    "line" 280,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.LUCache.",
    "tag" nil,
    "source"
    "(defcache LUCache [cache lu limit]\n  CacheProtocol\n  (lookup [_ item]\n    (get cache item))\n  (lookup [_ item not-found]\n    (get cache item not-found))\n  (has? [_ item]\n    (contains? cache item))\n  (hit [_ item]\n    (LUCache. cache (update-in lu [item] inc) limit))\n  (miss [_ item result]\n    (if-let [ks (keys lu)]\n      (let [k (if (contains? lu item)\n                  ::nope\n                  (apply min-key lu ks)) ;; maybe evict case\n            sz (count ks)\n            c (if (>= sz limit)\n                (-> cache (dissoc k) (assoc item result))\n                (assoc cache item result))\n            l (if (>= sz limit)\n                (-> lu (dissoc k) (update-in [item] (fnil inc 0)))\n                (assoc lu item 0))]\n        (LUCache. c l limit))\n      (LUCache. (assoc cache item result)  ;; no change case\n                (assoc lu item 0)\n                limit)))\n  (evict [this key]\n    (let [v (get cache key ::miss)]\n      (if (= v ::miss)\n        this\n        (LUCache. (dissoc cache key)\n                   (dissoc lu key)\n                   limit))))\n  (seed [_ base]\n    (LUCache. base\n              (build-leastness-queue base limit 0)\n              limit))\n  Object\n  (toString [_]\n    (str cache \\, \\space lu \\, \\space limit)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "lu" "limit"]]}
   {"ns" "clojure.core.cache",
    "name" "soft-cache-factory",
    "line" 618,
    "column" 1,
    "doc"
    "Returns a SoftReference cache.  Cached values will be referred to with\n  SoftReferences, allowing the values to be garbage collected when there is\n  memory pressure on the JVM.\n\n  SoftCache is a mutable cache, since it is always based on a\n  ConcurrentHashMap.",
    "tag" nil,
    "source"
    "(defn soft-cache-factory\n  \"Returns a SoftReference cache.  Cached values will be referred to with\n  SoftReferences, allowing the values to be garbage collected when there is\n  memory pressure on the JVM.\n\n  SoftCache is a mutable cache, since it is always based on a\n  ConcurrentHashMap.\"\n  [base]\n  {:pre [(map? base)]}\n  (seed (SoftCache. (ConcurrentHashMap.) (ConcurrentHashMap.) (ReferenceQueue.))\n        base))",
    "file" "clojure/core/cache.clj",
    "arglists" [["base"]]}
   {"ns" "clojure.core.cache",
    "name" "lu-cache-factory",
    "line" 597,
    "column" 1,
    "doc"
    "Returns an LU cache with the cache and usage-table initialied to `base`.\n\n   This function takes an optional `:threshold` argument that defines the maximum number\n   of elements in the cache before the LU semantics apply (default is 32).",
    "tag" nil,
    "source"
    "(defn lu-cache-factory\n  \"Returns an LU cache with the cache and usage-table initialied to `base`.\n\n   This function takes an optional `:threshold` argument that defines the maximum number\n   of elements in the cache before the LU semantics apply (default is 32).\"\n  [base & {threshold :threshold :or {threshold 32}}]\n  {:pre [(number? threshold) (< 0 threshold)\n         (map? base)]}\n  (clojure.core.cache/seed (LUCache. {} {} threshold) base))",
    "file" "clojure/core/cache.clj",
    "arglists"
    [["base"
      "&"
      [["threshold" "threshold"] ["or" [["threshold" 32]]]]]]}
   {"ns" "clojure.core.cache",
    "name" "->TTLCache",
    "line" 245,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.TTLCache.",
    "tag" nil,
    "source"
    "(defcache TTLCache [cache ttl ttl-ms]\n  CacheProtocol\n  (lookup [this item]\n    (let [ret (lookup this item ::nope)]\n      (when-not (= ret ::nope) ret)))\n  (lookup [this item not-found]\n    (if (has? this item)\n      (get cache item)\n      not-found))\n  (has? [_ item]\n    (let [t (get ttl item (- ttl-ms))]\n      (< (- (System/currentTimeMillis)\n            t)\n         ttl-ms)))\n  (hit [this item] this)\n  (miss [this item result]\n    (let [now  (System/currentTimeMillis)\n          kill-old (key-killer ttl ttl-ms now)]\n      (TTLCache. (assoc (kill-old cache) item result)\n                 (assoc (kill-old ttl) item now)\n                 ttl-ms)))\n  (seed [_ base]\n    (let [now (System/currentTimeMillis)]\n      (TTLCache. base\n                 (into {} (for [x base] [(key x) now]))\n                 ttl-ms)))\n  (evict [_ key]\n    (TTLCache. (dissoc cache key)\n               (dissoc ttl key)\n               ttl-ms))\n  Object\n  (toString [_]\n    (str cache \\, \\space ttl \\, \\space ttl-ms)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "ttl" "ttl-ms"]]}
   {"ns" "clojure.core.cache",
    "name" "->SoftCache",
    "line" 491,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.SoftCache.",
    "tag" nil,
    "source"
    "(defcache SoftCache [^java.util.Map cache ^java.util.Map rcache rq]\n  CacheProtocol\n  (lookup [_ item]\n    (when-let [^SoftReference r (get cache (or item ::nil))]\n      (if (= ::nil (.get r))\n        nil\n        (.get r))))\n  (lookup [_ item not-found]\n    (if-let [^SoftReference r (get cache (or item ::nil))]\n      (if-let [v (.get r)]\n        (if (= ::nil v)\n          nil\n          v)\n        not-found)\n      not-found))\n  (has? [_ item]\n    (let [item (or item ::nil)\n          ^SoftReference cell (get cache item)]\n      (and (contains? cache item)\n           (not (nil? (.get cell))))))\n  (hit [this item]\n    (clear-soft-cache! cache rcache rq)\n    this)\n  (miss [this item result]\n    (let [item (or item ::nil)\n          r (make-reference result rq)]\n      (.put cache item r)\n      (.put rcache r item)\n      (clear-soft-cache! cache rcache rq)\n      this))\n  (evict [this key]\n    (let [key (or key ::nil)\n          r (get cache key)]\n      (when r\n        (.remove cache key)\n        (.remove rcache r))\n      (clear-soft-cache! cache rcache rq)\n      this))\n  (seed [_ base]\n    (let [soft-cache? (instance? SoftCache base)\n          cache (ConcurrentHashMap.)\n          rcache (ConcurrentHashMap.)\n          rq (ReferenceQueue.)]\n      (if (seq base)\n        (doseq [[k ^SoftReference v] base]\n          (let [k (or k ::nil)\n                r (if soft-cache?\n                    (make-reference (.get v) rq)\n                    (make-reference v rq))]\n            (.put cache k r)\n            (.put rcache r k))))\n      (SoftCache. cache rcache rq)))\n  Object\n  (toString [_] (str cache)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "rcache" "rq"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.cache",
    "doc"
    "Retrieve the value associated with `e` if it exists, else `nil` in\n   the 2-arg case.  Retrieve the value associated with `e` if it exists,\n   else `not-found` in the 3-arg case.",
    "arglists" [["cache" "e"] ["cache" "e" "not-found"]],
    "name" "lookup"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.cache",
    "doc"
    "Is meant to be called if the cache is determined to contain a value\n   associated with `e`",
    "arglists" [["cache" "e"]],
    "name" "hit"}
   {"ns" "clojure.core.cache",
    "name" "->LIRSCache",
    "line" 407,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.LIRSCache.",
    "tag" nil,
    "source"
    "(defcache LIRSCache [cache lruS lruQ tick limitS limitQ]\n  CacheProtocol\n  (lookup [_ item]\n    (get cache item))\n  (lookup [_ item not-found]\n    (get cache item not-found))\n  (has? [_ item]\n    (contains? cache item))\n  (hit [_ item]\n    (let [tick+ (inc tick)]\n      (if (not (contains? lruS item))\n                                        ; (2.3) item ∉ S ∧ item ∈ Q\n        (LIRSCache. cache (assoc lruS item tick+) (assoc lruQ item tick+) tick+ limitS limitQ)\n        (let [k (apply min-key lruS (keys lruS))]\n          (if (contains? lruQ item)\n                                        ; (2.2) item ∈ S ∧ item ∈ Q\n            (let [new-lruQ (-> lruQ (dissoc item) (assoc k tick+))]\n              (LIRSCache. cache\n                          (-> lruS (dissoc k) (assoc item tick+) (prune-stack new-lruQ cache))\n                          new-lruQ\n                          tick+\n                          limitS\n                          limitQ))\n                                        ; (2.1) item ∈ S ∧ item ∉ Q\n            (LIRSCache. cache\n                        (-> lruS (assoc item tick+) (prune-stack lruQ cache))\n                        lruQ\n                        tick+\n                        limitS\n                        limitQ))))))\n\n  (miss [_ item result]\n    (let [tick+ (inc tick)]\n      (if (< (count cache) limitS)\n                                        ; (1.1)\n        (let [k (apply min-key lruS (keys lruS))]\n          (LIRSCache. (assoc cache item result)\n                      (-> lruS (dissoc k) (assoc item tick+))\n                      lruQ\n                      tick+\n                      limitS\n                      limitQ))\n        (let [k (apply min-key lruQ (keys lruQ))\n              new-lruQ (dissoc lruQ k)\n              new-cache (-> cache  (dissoc k) (assoc item result))]\n          (if (contains? lruS item)\n                                        ; (1.3)\n            (let [lastS (apply min-key lruS (keys lruS))]\n              (LIRSCache. new-cache\n                          (-> lruS (dissoc lastS) (assoc item tick+) (prune-stack new-lruQ new-cache))\n                          (assoc new-lruQ lastS tick+)\n                          tick+\n                          limitS\n                          limitQ))\n                                        ; (1.2)\n            (LIRSCache. new-cache\n                        (assoc lruS item tick+)\n                        (assoc new-lruQ item tick+)\n                        tick+\n                        limitS\n                        limitQ))))))\n  (seed [_ base]\n    (LIRSCache. base\n                (into {} (for [x (range (- limitS) 0)] [x x]))\n                (into {} (for [x (range (- limitQ) 0)] [x x]))\n                0\n                limitS\n                limitQ))\n  Object\n  (toString [_]\n    (str cache \\, \\space lruS \\, \\space lruQ \\, \\space tick \\, \\space limitS \\, \\space limitQ)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "lruS" "lruQ" "tick" "limitS" "limitQ"]]}
   {"ns" "clojure.core.cache",
    "name" "basic-cache-factory",
    "line" 548,
    "column" 1,
    "doc" "Returns a pluggable basic cache initialied to `base`",
    "tag" nil,
    "source"
    "(defn basic-cache-factory\n  \"Returns a pluggable basic cache initialied to `base`\"\n  [base]\n  {:pre [(map? base)]}\n  (BasicCache. base))",
    "file" "clojure/core/cache.clj",
    "arglists" [["base"]]}
   {"private" true,
    "ns" "clojure.core.cache",
    "name" "dissoc-keys",
    "line" 138,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- dissoc-keys [m ks]\n  (if ks\n    (recur (dissoc m (first ks)) (next ks))\n    m))",
    "file" "clojure/core/cache.clj",
    "arglists" [["m" "ks"]]}
   {"source"
    "(defn clear-soft-cache! [^java.util.Map cache ^java.util.Map rcache ^ReferenceQueue rq]\n  (loop [r (.poll rq)]\n    (when r\n      (.remove cache (get rcache r))\n      (.remove rcache r)\n      (recur (.poll rq)))))",
    "tag" nil,
    "ns" "clojure.core.cache",
    "name" "clear-soft-cache!",
    "arglists" [["cache" "rcache" "rq"]],
    "column" 1,
    "line" 479,
    "file" "clojure/core/cache.clj"}
   {"ns" "clojure.core.cache",
    "name" "lirs-cache-factory",
    "line" 607,
    "column" 1,
    "doc"
    "Returns an LIRS cache with the S & R LRU lists set to the indicated\n   limits.",
    "tag" nil,
    "source"
    "(defn lirs-cache-factory\n  \"Returns an LIRS cache with the S & R LRU lists set to the indicated\n   limits.\"\n  [base & {:keys [s-history-limit q-history-limit]\n           :or {s-history-limit 32\n                q-history-limit 32}}]\n  {:pre [(number? s-history-limit) (< 0 s-history-limit)\n         (number? q-history-limit) (< 0 q-history-limit)\n         (map? base)]}\n  (seed (LIRSCache. {} {} {} 0 s-history-limit q-history-limit) base))",
    "file" "clojure/core/cache.clj",
    "arglists"
    [["base"
      "&"
      [["keys" ["s-history-limit" "q-history-limit"]]
       ["or" [["s-history-limit" 32] ["q-history-limit" 32]]]]]]}
   {"ns" "clojure.core.cache",
    "name" "->LRUCache",
    "line" 189,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.LRUCache.",
    "tag" nil,
    "source"
    "(defcache LRUCache [cache lru tick limit]\n  CacheProtocol\n  (lookup [_ item]\n    (get cache item))\n  (lookup [_ item not-found]\n    (get cache item not-found))\n  (has? [_ item]\n    (contains? cache item))\n  (hit [_ item]\n    (let [tick+ (inc tick)]\n      (LRUCache. cache\n                 (assoc lru item tick+)\n                 tick+\n                 limit)))\n  (miss [_ item result]\n    (let [tick+ (inc tick)]\n      (if-let [ks (keys lru)]\n        (let [k (if (contains? lru item)\n                  item\n                  (apply min-key lru ks))        ;; maybe evict case\n              sz (count ks)\n              c (if (>= sz limit)\n                  (-> cache (dissoc k) (assoc item result))\n                  (assoc cache item result))\n              l (if (>= sz limit)\n                  (-> lru (dissoc k) (assoc item tick+))\n                  (assoc lru item tick+))]\n          (LRUCache. c l tick+ limit))\n        (LRUCache. (assoc cache item result)  ;; no change case\n                   (assoc lru item tick+)\n                   tick+\n                   limit))))\n  (evict [this key]\n    (let [v (get cache key ::miss)]\n      (if (= v ::miss)\n        this\n        (LRUCache. (dissoc cache key)\n                   (dissoc lru key)\n                   (inc tick)\n                   limit))))\n  (seed [_ base]\n    (LRUCache. base\n               (build-leastness-queue base limit 0)\n               0\n               limit))\n  Object\n  (toString [_]\n    (str cache \\, \\space lru \\, \\space tick \\, \\space limit)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "lru" "tick" "limit"]]}
   {"private" true,
    "ns" "clojure.core.cache",
    "name" "build-leastness-queue",
    "line" 182,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- build-leastness-queue\n  [base limit start-at]\n  (merge\n   (into {} (take (- limit (count base)) (for [k (range (- limit) 0)] [k k])))\n   (into {} (for [[k _] base] [k start-at]))))",
    "file" "clojure/core/cache.clj",
    "arglists" [["base" "limit" "start-at"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.cache",
    "doc" "Checks if the cache contains a value associtaed with `e`",
    "arglists" [["cache" "e"]],
    "name" "has?"}
   {"private" true,
    "ns" "clojure.core.cache",
    "name" "describe-layout",
    "line" 128,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- describe-layout [mappy limit]\n  (let [q clojure.lang.PersistentQueue/EMPTY \n        ks (keys mappy)\n        [dropping keeping] (split-at (- (count ks) limit) ks)]\n    {:dropping dropping\n     :keeping  keeping\n     :queue\n     (into q (concat (repeat (- limit (count keeping)) ::free)\n                     (take limit keeping)))}))",
    "file" "clojure/core/cache.clj",
    "arglists" [["mappy" "limit"]]}
   {"ns" "clojure.core.cache",
    "name" "defcache",
    "macro" true,
    "line" 42,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro defcache\n  [type-name fields & specifics]\n  (let [[base-field & _] fields]\n    `(deftype ~type-name [~@fields]\n       ~@specifics\n     \n       clojure.lang.ILookup\n       (valAt [this# key#]\n              (lookup this# key#))\n       (valAt [this# key# not-found#]\n              (if (has? this# key#)\n                (lookup this# key#)\n                not-found#))\n\n       clojure.lang.IPersistentMap\n       (assoc [this# k# v#]\n         (miss this# k# v#))\n       (without [this# k#]\n         (evict this# k#))\n\n       clojure.lang.Associative\n       (containsKey [this# k#]\n         (has? this# k#))\n       (entryAt [this# k#]\n         (when (has? this# k#)\n           (clojure.lang.MapEntry. k# (lookup this# k#))))\n\n       clojure.lang.Counted\n       (count [this#]\n         (clojure.core/count ~base-field))\n\n       clojure.lang.IPersistentCollection\n       (cons [_# elem#]\n         (clojure.core/cons ~base-field elem#))\n       (empty [this#]\n         (seed this# (empty ~base-field)))\n       (equiv [_# other#]\n         (clojure.lang.Util/equiv ~base-field other#))\n\n       clojure.lang.Seqable\n       (seq [_#]\n         (seq ~base-field)))))",
    "file" "clojure/core/cache.clj",
    "arglists" [["type-name" "fields" "&" "specifics"]]}
   {"ns" "clojure.core.cache",
    "name" "ttl-cache-factory",
    "line" 586,
    "column" 1,
    "doc"
    "Returns a TTL cache with the cache and expiration-table initialied to `base` --\n   each with the same time-to-live.\n\n   This function also allows an optional `:ttl` argument that defines the default\n   time in milliseconds that entries are allowed to reside in the cache.",
    "tag" nil,
    "source"
    "(defn ttl-cache-factory\n  \"Returns a TTL cache with the cache and expiration-table initialied to `base` --\n   each with the same time-to-live.\n\n   This function also allows an optional `:ttl` argument that defines the default\n   time in milliseconds that entries are allowed to reside in the cache.\"\n  [base & {ttl :ttl :or {ttl 2000}}]\n  {:pre [(number? ttl) (<= 0 ttl)\n         (map? base)]}\n  (clojure.core.cache/seed (TTLCache. {} {} ttl) base))",
    "file" "clojure/core/cache.clj",
    "arglists" [["base" "&" [["ttl" "ttl"] ["or" [["ttl" 2000]]]]]]}
   {"private" true,
    "ns" "clojure.core.cache",
    "name" "key-killer",
    "line" 239,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- key-killer\n  [ttl expiry now]\n  (let [ks (map key (filter #(> (- now (val %)) expiry) ttl))]\n    #(apply dissoc % ks)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["ttl" "expiry" "now"]]}
   {"ns" "clojure.core.cache",
    "name" "->FIFOCache",
    "line" 147,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.FIFOCache.",
    "tag" nil,
    "source"
    "(defcache FIFOCache [cache q limit]\n  CacheProtocol\n  (lookup [_ item]\n    (get cache item))\n  (lookup [_ item not-found]\n    (get cache item not-found))\n  (has? [_ item]\n    (contains? cache item))\n  (hit [this item]\n    this)\n  (miss [_ item result]\n    (let [[cache q] (if (>= (count cache) limit)\n                      (let [k (peek q)]\n                        [(dissoc cache k) (pop q)])\n                      [cache q])]\n      (FIFOCache. (assoc cache item result)\n                  (conj q item)\n                  limit)))\n  (evict [this key]\n    (let [v (get cache key ::miss)]\n      (if (= v ::miss)\n        this\n        (FIFOCache. (dissoc cache key)\n                    (prune-queue q [key])\n                    limit))))\n  (seed [_ base]\n    (let [{dropping :dropping\n           q :queue} (describe-layout base limit)]\n      (FIFOCache. (dissoc-keys base dropping)\n                  q\n                  limit)))\n  Object\n  (toString [_]\n    (str cache \\, \\space (pr-str q))))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache" "q" "limit"]]}
   {"ns" "clojure.core.cache",
    "name" "->BasicCache",
    "line" 85,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.cache.BasicCache.",
    "tag" nil,
    "source"
    "(defcache BasicCache [cache]\n  CacheProtocol\n  (lookup [_ item]\n    (get cache item))\n  (lookup [_ item not-found]\n    (get cache item not-found))\n  (has? [_ item]\n    (contains? cache item))\n  (hit [this item] this)\n  (miss [_ item result]\n    (BasicCache. (assoc cache item result)))\n  (evict [_ key]\n    (BasicCache. (dissoc cache key)))\n  (seed [_ base]\n    (BasicCache. base))\n  Object\n  (toString [_] (str cache)))",
    "file" "clojure/core/cache.clj",
    "arglists" [["cache"]]}]},
 "description" "core.cache 0.6.3",
 "version" "0.6.3",
 "name" "clojure.core.cache",
 "group" "clojure.core.cache"}
