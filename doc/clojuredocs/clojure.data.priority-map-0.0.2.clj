{"namespaces"
 {"clojure.data.priority-map"
  [{"ns" "clojure.data.priority-map",
    "name" "->PersistentPriorityMap",
    "line" 164,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.priority_map.PersistentPriorityMap.",
    "tag" nil,
    "source"
    "(deftype PersistentPriorityMap [priority->set-of-items item->priority _meta]\n  Object\n  (toString [this] (str (.seq this)))\n\n  clojure.lang.ILookup\n  ; valAt gives (get pm key) and (get pm key not-found) behavior\n  (valAt [this item] (get item->priority item))\n  (valAt [this item not-found] (get item->priority item not-found))\n\n  clojure.lang.IPersistentMap\n  (count [this] (count item->priority))\n\n  (assoc [this item priority]\n    (let [current-priority (get item->priority item nil)]\n      (if current-priority\n        ;Case 1 - item is already in priority map, so this is a reassignment\n        (if (= current-priority priority)\n          ;Subcase 1 - no change in priority, do nothing\n          this\n          (let [item-set (get priority->set-of-items current-priority)]\n            (if (= (count item-set) 1)\n              ;Subcase 2 - it was the only item of this priority\n              ;so remove old priority entirely\n              ;and conj item onto new priority's set\n              (PersistentPriorityMap.\n                (assoc (dissoc priority->set-of-items current-priority)\n                  priority (conj (get priority->set-of-items priority #{}) item))\n                (assoc item->priority item priority)\n                (meta this))\n              ;Subcase 3 - there were many items associated with the item's original priority,\n              ;so remove it from the old set and conj it onto the new one.\n              (PersistentPriorityMap.\n                (assoc priority->set-of-items\n                  current-priority (disj (get priority->set-of-items current-priority) item)\n                  priority (conj (get priority->set-of-items priority #{}) item))\n                (assoc item->priority item priority)\n                (meta this)))))\n        ; Case 2: Item is new to the priority map, so just add it.\n        (PersistentPriorityMap.\n          (assoc priority->set-of-items\n            priority (conj (get priority->set-of-items priority #{}) item))\n          (assoc item->priority item priority)\n          (meta this)))))\n\n  (empty [this] pm-empty)\n\n  ; cons defines conj behavior\n  (cons [this e] (let [[item priority] e] (.assoc this item priority)))\n\n  ; Like sorted maps, priority maps are equal to other maps provided\n  ; their key-value pairs are the same.\n  (equiv [this o] (.equiv item->priority o))\n  (hashCode [this] (.hashCode item->priority))\n  (equals [this o] (or (identical? this o) (.equals item->priority o)))\n\n  ;containsKey implements (contains? pm k) behavior\n  (containsKey [this item] (contains? item->priority item))\n\n  (entryAt [this k]\n    (let [v (.valAt this k this)]\n      (when-not (identical? v this)\n        (MapEntry. k v))))\n\n  (seq [this]\n    (seq (for [[priority item-set] priority->set-of-items, item item-set]\n           (MapEntry. item priority))))\n\n  ;without implements (dissoc pm k) behavior\n  (without\n    [this item]\n    (let [priority (item->priority item ::not-found)]\n      (if (= priority ::not-found)\n\t;; If item is not in map, return the map unchanged.\n\tthis\n\t(let [item-set (priority->set-of-items priority)]\n\t  (if (= (count item-set) 1)\n\t    ;;If it is the only item with this priority, remove that priority's set completely\n\t    (PersistentPriorityMap. (dissoc priority->set-of-items priority)\n\t\t\t\t    (dissoc item->priority item)\n                    (meta this))\n\t    ;;Otherwise, just remove the item from the priority's set.\n\t    (PersistentPriorityMap.\n\t     (assoc priority->set-of-items priority (disj item-set item)),\n\t     (dissoc item->priority item)\n         (meta this)))))))\n\n  java.io.Serializable  ;Serialization comes for free with the other things implemented\n  clojure.lang.MapEquivalence\n  Map ;Makes this compatible with java's map\n  (size [this] (count item->priority))\n  (isEmpty [this] (zero? (count item->priority)))\n  (containsValue [this v] (contains? (priority->set-of-items this) v))\n  (get [this k] (.valAt this k))\n  (put [this k v] (throw (UnsupportedOperationException.)))\n  (remove [this k] (throw (UnsupportedOperationException.)))\n  (putAll [this m] (throw (UnsupportedOperationException.)))\n  (clear [this] (throw (UnsupportedOperationException.)))\n  (keySet [this] (set (keys this)))\n  (values [this] (vals this))\n  (entrySet [this] (set this))\n  \n  Iterable\n  (iterator [this] (clojure.lang.SeqIterator. (seq this)))\n\n  clojure.lang.IPersistentStack\n  (peek [this]\n    (when-not (.isEmpty this)\n      (let [f (first priority->set-of-items)]\n        (MapEntry. (first (val f)) (key f)))))\n\n  (pop [this]\n    (if (.isEmpty this) (throw (IllegalStateException. \"Can't pop empty priority map\"))\n      (let [f (first priority->set-of-items),\n            item-set (val f)\n            item (first item-set),\n            priority (key f)]\n        (if (= (count item-set) 1)\n          ;If the first item is the only item with its priority, remove that priority's set completely\n          (PersistentPriorityMap.\n            (dissoc priority->set-of-items priority)\n            (dissoc item->priority item)\n            (meta this))\n          ;Otherwise, just remove the item from the priority's set.\n          (PersistentPriorityMap.\n            (assoc priority->set-of-items priority (disj item-set item)),\n            (dissoc item->priority item)\n            (meta this))))))\n\n  clojure.lang.IFn\n  ;makes priority map usable as a function\n  (invoke [this k] (.valAt this k))\n  (invoke [this k not-found] (.valAt this k not-found))\n\n  clojure.lang.IObj\n  ;adds metadata support\n  (meta [this] _meta)\n  (withMeta [this m] (PersistentPriorityMap. priority->set-of-items item->priority m))\n\n  clojure.lang.Reversible\n  (rseq [this]\n    (seq (for [[priority item-set] (rseq priority->set-of-items), item item-set]\n           (MapEntry. item priority)))))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["priority->set-of-items" "item->priority" "_meta"]]}
   {"ns" "clojure.data.priority-map",
    "name" "priority-map",
    "line" 322,
    "column" 1,
    "doc"
    "keyval => key val\nReturns a new priority map with supplied mappings",
    "tag" nil,
    "source"
    "(defn priority-map\n  \"keyval => key val\nReturns a new priority map with supplied mappings\"\n  [& keyvals]\n  (reduce conj pm-empty (partition 2 keyvals)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["&" "keyvals"]]}
   {"ns" "clojure.data.priority-map",
    "name" "priority-map-by",
    "line" 328,
    "column" 1,
    "doc"
    "keyval => key val\nReturns a new priority map with supplied mappings",
    "tag" nil,
    "source"
    "(defn priority-map-by\n  \"keyval => key val\nReturns a new priority map with supplied mappings\"\n  [comparator & keyvals]\n  (reduce conj (pm-empty-by comparator) (partition 2 keyvals)))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["comparator" "&" "keyvals"]]}
   {"private" true,
    "ns" "clojure.data.priority-map",
    "name" "pm-empty",
    "line" 318,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private pm-empty (PersistentPriorityMap. (sorted-map) {} {}))",
    "file" "clojure/data/priority_map.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.priority-map",
    "name" "pm-empty-by",
    "line" 319,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pm-empty-by [comparator] (PersistentPriorityMap. (sorted-map-by comparator) {} {}))",
    "file" "clojure/data/priority_map.clj",
    "arglists" [["comparator"]]}]},
 "description" "data.priority-map 0.0.2",
 "version" "0.0.2",
 "name" "clojure.data.priority-map",
 "group" "clojure.data.priority-map"}
