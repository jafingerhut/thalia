{"namespaces"
 {"clojure.data.priority-map"
  [{"ns" "clojure.data.priority-map",
    "name" "->PersistentPriorityMap",
    "line" 204,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.priority_map.PersistentPriorityMap.",
    "tag" nil,
    "source"
    "(deftype PersistentPriorityMap [priority->set-of-items item->priority _meta keyfn]\n  Object\n  (toString [this] (str (.seq this)))\n\n  clojure.lang.ILookup\n  ; valAt gives (get pm key) and (get pm key not-found) behavior\n  (valAt [this item] (get item->priority item))\n  (valAt [this item not-found] (get item->priority item not-found))\n\n  clojure.lang.IPersistentMap\n  (count [this] (count item->priority))\n\n  (assoc [this item priority]\n    (let [current-priority (get item->priority item nil)]\n      (if current-priority\n        ;Case 1 - item is already in priority map, so this is a reassignment\n        (if (= current-priority priority)\n          ;Subcase 1 - no change in priority, do nothing\n          this\n          (let [priority-key (apply-keyfn priority)\n                current-priority-key (apply-keyfn current-priority)\n                item-set (get priority->set-of-items current-priority-key)]\n            (if (= (count item-set) 1)\n              ;Subcase 2 - it was the only item of this priority\n              ;so remove old priority entirely\n              ;and conj item onto new priority's set\n              (PersistentPriorityMap.\n                (assoc (dissoc priority->set-of-items current-priority-key)\n                  priority-key (conj (get priority->set-of-items priority-key #{}) item))\n                (assoc item->priority item priority)\n                (meta this)\n                keyfn)\n              ;Subcase 3 - there were many items associated with the item's original priority,\n              ;so remove it from the old set and conj it onto the new one.\n              (PersistentPriorityMap.\n                (assoc priority->set-of-items\n                  current-priority-key (disj (get priority->set-of-items current-priority-key) item)\n                  priority-key (conj (get priority->set-of-items priority-key #{}) item))\n                (assoc item->priority item priority)\n                (meta this)\n                keyfn))))\n        ; Case 2: Item is new to the priority map, so just add it.\n        (let [priority-key (apply-keyfn priority)]\n          (PersistentPriorityMap.\n            (assoc priority->set-of-items\n                   priority-key (conj (get priority->set-of-items priority-key #{}) item))\n            (assoc item->priority item priority)\n            (meta this)\n            keyfn)))))\n\n  (empty [this] (PersistentPriorityMap. (empty priority->set-of-items) {} _meta keyfn))\n\n  ; cons defines conj behavior\n  (cons [this e] (let [[item priority] e] (.assoc this item priority)))\n\n  ; Like sorted maps, priority maps are equal to other maps provided\n  ; their key-value pairs are the same.\n  (equiv [this o] (= item->priority o))\n  (hashCode [this] (.hashCode item->priority))\n  (equals [this o] (or (identical? this o) (.equals item->priority o)))\n\n  ;containsKey implements (contains? pm k) behavior\n  (containsKey [this item] (contains? item->priority item))\n\n  (entryAt [this k]\n    (let [v (.valAt this k this)]\n      (when-not (identical? v this)\n        (MapEntry. k v))))\n\n  (seq [this]\n    (if keyfn\n      (seq (for [[priority item-set] priority->set-of-items, item item-set]\n             (MapEntry. item (item->priority item))))\n      (seq (for [[priority item-set] priority->set-of-items, item item-set]\n             (MapEntry. item priority)))))\n\n  ;without implements (dissoc pm k) behavior\n  (without\n    [this item]\n    (let [priority (item->priority item ::not-found)]\n      (if (= priority ::not-found)\n        ;; If item is not in map, return the map unchanged.\n        this\n        (let [priority-key (apply-keyfn priority)\n              item-set (priority->set-of-items priority-key)]\n          (if (= (count item-set) 1)\n            ;;If it is the only item with this priority, remove that priority's set completely\n            (PersistentPriorityMap. (dissoc priority->set-of-items priority-key)\n                                    (dissoc item->priority item)\n                                    (meta this)\n                                    keyfn)\n            ;;Otherwise, just remove the item from the priority's set.\n            (PersistentPriorityMap.\n              (assoc priority->set-of-items priority-key (disj item-set item)),\n              (dissoc item->priority item)\n              (meta this)\n              keyfn))))))\n  \n  java.io.Serializable  ;Serialization comes for free with the other things implemented\n  clojure.lang.MapEquivalence\n  Map ;Makes this compatible with java's map\n  (size [this] (count item->priority))\n  (isEmpty [this] (zero? (count item->priority)))\n  (containsValue [this v] \n    (if keyfn\n      (some (partial = v) (vals this)) ; no shortcut if there is a keyfn\n      (contains? priority->set-of-items v)))\n  (get [this k] (.valAt this k))\n  (put [this k v] (throw (UnsupportedOperationException.)))\n  (remove [this k] (throw (UnsupportedOperationException.)))\n  (putAll [this m] (throw (UnsupportedOperationException.)))\n  (clear [this] (throw (UnsupportedOperationException.)))\n  (keySet [this] (set (keys this)))\n  (values [this] (vals this))\n  (entrySet [this] (set this))\n  \n  Iterable\n  (iterator [this] (clojure.lang.SeqIterator. (seq this)))\n\n  clojure.lang.IPersistentStack\n  (peek [this]\n    (when-not (.isEmpty this)\n      (let [f (first priority->set-of-items)\n            item (first (val f))]\n        (if keyfn\n          (MapEntry. item (item->priority item)) \n          (MapEntry. item (key f))))))\n\n  (pop [this]\n    (if (.isEmpty this) (throw (IllegalStateException. \"Can't pop empty priority map\"))\n      (let [f (first priority->set-of-items),\n            item-set (val f)\n            item (first item-set),\n            priority-key (key f)]\n        (if (= (count item-set) 1)\n          ;If the first item is the only item with its priority, remove that priority's set completely\n          (PersistentPriorityMap.\n            (dissoc priority->set-of-items priority-key)\n            (dissoc item->priority item)\n            (meta this)\n            keyfn)\n          ;Otherwise, just remove the item from the priority's set.\n          (PersistentPriorityMap.\n            (assoc priority->set-of-items priority-key (disj item-set item)),\n            (dissoc item->priority item)\n            (meta this)\n            keyfn)))))\n\n  clojure.lang.IFn\n  ;makes priority map usable as a function\n  (invoke [this k] (.valAt this k))\n  (invoke [this k not-found] (.valAt this k not-found))\n\n  clojure.lang.IObj\n  ;adds metadata support\n  (meta [this] _meta)\n  (withMeta [this m] (PersistentPriorityMap. priority->set-of-items item->priority m keyfn))\n\n  clojure.lang.Reversible\n  (rseq [this]\n    (if keyfn\n      (seq (for [[priority item-set] (rseq priority->set-of-items), item item-set]\n             (MapEntry. item (item->priority item))))\n      (seq (for [[priority item-set] (rseq priority->set-of-items), item item-set]\n             (MapEntry. item priority))))))",
    "file" "clojure/data/priority_map.clj",
    "arglists"
    [["priority->set-of-items" "item->priority" "_meta" "keyfn"]]}
   {"ns" "clojure.data.priority-map",
    "name" "priority-map-keyfn-by",
    "line" 414,
    "column" 1,
    "doc"
    "Usage: (priority-map-keyfn-by keyfn comparator key val key val ...)\nReturns a new priority map with custom keyfn, custom comparator, and optional supplied mappings.\nThe priority is determined by comparing (keyfn val).\n(priority-map-keyfn-by keyfn comparator) yields an empty priority map with custom keyfn and comparator.",
    "tag" nil,
    "source"
    "(defn priority-map-keyfn-by\n  \"Usage: (priority-map-keyfn-by keyfn comparator key val key val ...)\nReturns a new priority map with custom keyfn, custom comparator, and optional supplied mappings.\nThe priority is determined by comparing (keyfn val).\n(priority-map-keyfn-by keyfn comparator) yields an empty priority map with custom keyfn and comparator.\"\n  [keyfn comparator & keyvals]\n  {:pre [(even? (count keyvals))]}\n  (reduce conj (pm-empty-keyfn keyfn comparator) (partition 2 keyvals)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["keyfn" "comparator" "&" "keyvals"]]}
   {"ns" "clojure.data.priority-map",
    "name" "priority-map",
    "line" 389,
    "column" 1,
    "doc"
    "Usage: (priority-map key val key val ...)\nReturns a new priority map with optional supplied mappings.\n(priority-map) returns an empty priority map.",
    "tag" nil,
    "source"
    "(defn priority-map\n  \"Usage: (priority-map key val key val ...)\nReturns a new priority map with optional supplied mappings.\n(priority-map) returns an empty priority map.\"\n  [& keyvals]\n  {:pre [(even? (count keyvals))]}\n  (reduce conj pm-empty (partition 2 keyvals)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["&" "keyvals"]]}
   {"ns" "clojure.data.priority-map",
    "name" "priority-map-by",
    "line" 397,
    "column" 1,
    "doc"
    "Usage: (priority-map comparator key val key val ...)\nReturns a new priority map with custom comparator and optional supplied mappings.\n(priority-map-by comparator) yields an empty priority map with custom comparator.",
    "tag" nil,
    "source"
    "(defn priority-map-by\n  \"Usage: (priority-map comparator key val key val ...)\nReturns a new priority map with custom comparator and optional supplied mappings.\n(priority-map-by comparator) yields an empty priority map with custom comparator.\"\n  [comparator & keyvals]\n  {:pre [(even? (count keyvals))]}\n  (reduce conj (pm-empty-by comparator) (partition 2 keyvals)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["comparator" "&" "keyvals"]]}
   {"private" true,
    "ns" "clojure.data.priority-map",
    "name" "pm-empty-keyfn",
    "line" 383,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pm-empty-keyfn\n  ([keyfn] (PersistentPriorityMap. (sorted-map) {} {} keyfn))\n  ([keyfn comparator] (PersistentPriorityMap. (sorted-map-by comparator) {} {} keyfn)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["keyfn"] ["keyfn" "comparator"]]}
   {"private" true,
    "ns" "clojure.data.priority-map",
    "name" "pm-empty",
    "line" 381,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private pm-empty (PersistentPriorityMap. (sorted-map) {} {} nil))",
    "file" "clojure/data/priority_map.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.priority-map",
    "name" "pm-empty-by",
    "line" 382,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pm-empty-by [comparator] (PersistentPriorityMap. (sorted-map-by comparator) {} {} nil))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["comparator"]]}
   {"ns" "clojure.data.priority-map",
    "name" "apply-keyfn",
    "macro" true,
    "line" 194,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro apply-keyfn [x]\n  `(if ~'keyfn (~'keyfn ~x) ~x))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.data.priority-map",
    "name" "priority-map-keyfn",
    "line" 405,
    "column" 1,
    "doc"
    "Usage: (priority-map-keyfn keyfn key val key val ...)\nReturns a new priority map with custom keyfn and optional supplied mappings.\nThe priority is determined by comparing (keyfn val). \n(priority-map-keyfn keyfn) yields an empty priority map with custom keyfn.",
    "tag" nil,
    "source"
    "(defn priority-map-keyfn\n  \"Usage: (priority-map-keyfn keyfn key val key val ...)\nReturns a new priority map with custom keyfn and optional supplied mappings.\nThe priority is determined by comparing (keyfn val). \n(priority-map-keyfn keyfn) yields an empty priority map with custom keyfn.\"\n  [keyfn & keyvals]\n  {:pre [(even? (count keyvals))]}\n  (reduce conj (pm-empty-keyfn keyfn) (partition 2 keyvals)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["keyfn" "&" "keyvals"]]}]},
 "description" "data.priority-map 0.0.4",
 "version" "0.0.4",
 "name" "clojure.data.priority-map",
 "group" "clojure.data.priority-map"}
