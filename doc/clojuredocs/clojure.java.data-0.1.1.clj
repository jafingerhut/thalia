{"namespaces"
 {"clojure.java.data"
  [{"private" true,
    "ns" "clojure.java.data",
    "name" "when-available",
    "macro" true,
    "line" 105,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^{:private true} when-available\n  [sym & body]\n  (try\n    (when (resolve sym)\n      (list* 'do body))\n    (catch ClassNotFoundException _#)))",
    "file" "clojure/java/data.clj",
    "arglists" [["sym" "&" "body"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "when-not-available",
    "macro" true,
    "line" 112,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^{:private true} when-not-available\n  [sym & body]\n  (try\n    (when-not (resolve sym)\n      (list* 'do body))\n    (catch ClassNotFoundException _#)))",
    "file" "clojure/java/data.clj",
    "arglists" [["sym" "&" "body"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "throw-log-or-ignore-missing-setter",
    "line" 86,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- throw-log-or-ignore-missing-setter [key clazz]\n  (let [message (str \"Missing setter for \" key \" in \" (.getCanonicalName clazz))]\n    (cond (= *to-java-object-missing-setter* :error)\n          (throw (new NoSuchFieldException message))\n          (= *to-java-object-missing-setter* :log)\n          (info message))))",
    "file" "clojure/java/data.clj",
    "arglists" [["key" "clazz"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "defnumbers",
    "macro" true,
    "line" 149,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^{:private true} defnumbers [& boxes]\n  (cons `do\n        (for [box boxes\n              :let [box-cls (resolve box)\n                    prim-cls (.get (.getField box-cls \"TYPE\")\n                                   box-cls)\n                    ;; Clojure 1.3: (assert (class? box-cls) (str box \": no class found\"))\n                    _ (assert (class? box-cls))\n                    ;; Clojure 1.3: (assert (class? prim-cls) (str box \" has no TYPE field\"))\n                    _ (assert (class? prim-cls))\n                    prim-getter (symbol (str (.getName prim-cls) \"Value\"))]]\n          `(defnumber ~box ~(symbol (str box) \"TYPE\") ~prim-getter))))",
    "file" "clojure/java/data.clj",
    "arglists" [["&" "boxes"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "make-getter-fn",
    "line" 33,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- make-getter-fn [method]\n  (fn [instance]\n    (from-java (.invoke method instance nil))))",
    "file" "clojure/java/data.clj",
    "arglists" [["method"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "add-setter-fn",
    "line" 57,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- add-setter-fn [the-map prop-descriptor]\n  (let [name (.getName prop-descriptor)\n        method (.getWriteMethod prop-descriptor)]\n    (if (is-setter method)\n      (assoc the-map (keyword name) (make-setter-fn method))\n      the-map)))",
    "file" "clojure/java/data.clj",
    "arglists" [["the-map" "prop-descriptor"]]}
   {"ns" "clojure.java.data",
    "name" "*to-java-object-missing-setter*",
    "line" 16,
    "column" 1,
    "doc"
    "Specify the behavior of missing setters in to-java in the\n default object case, using one of :ignore, :log, :error",
    "tag" nil,
    "source"
    "(def\n ^{:dynamic true,\n   :doc \"Specify the behavior of missing setters in to-java in the\n default object case, using one of :ignore, :log, :error\"}\n *to-java-object-missing-setter* :ignore)",
    "file" "clojure/java/data.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "add-array-methods",
    "line" 64,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- add-array-methods [acls]\n  (let [cls (.getComponentType acls)\n        to (fn [_ sequence] (into-array cls (map (partial to-java cls)\n                                                sequence)))\n        from (fn [obj] (map from-java obj))]\n    (.addMethod to-java [acls Iterable] to)\n    (.addMethod from-java acls from)\n    {:to to :from from}))",
    "file" "clojure/java/data.clj",
    "arglists" [["acls"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "make-setter-fn",
    "line" 53,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- make-setter-fn [method]\n    (fn [instance value]\n      (.invoke method instance (into-array [(to-java (get-setter-type method) value)]))))",
    "file" "clojure/java/data.clj",
    "arglists" [["method"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "defnumber",
    "macro" true,
    "line" 142,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^{:private true} defnumber [box prim prim-getter]\n  `(let [conv# (fn [_# number#]\n                 (~(symbol (str box) \"valueOf\")\n                  (. number# ~prim-getter)))]\n     (.addMethod to-java [~prim Number] conv#)\n     (.addMethod to-java [~box Number] conv#)))",
    "file" "clojure/java/data.clj",
    "arglists" [["box" "prim" "prim-getter"]]}
   {"source"
    "(defmulti to-java (fn [destination-type value] [destination-type (class value)]))",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.java.data",
    "name" "to-java",
    "column" 1,
    "line" 22,
    "file" "clojure/java/data.clj"}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "is-getter",
    "line" 30,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- is-getter [method]\n  (and method (= 0 (alength (. method (getParameterTypes))))))",
    "file" "clojure/java/data.clj",
    "arglists" [["method"]]}
   {"source" "(defmulti from-java class)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.java.data",
    "name" "from-java",
    "column" 1,
    "line" 23,
    "file" "clojure/java/data.clj"}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "get-property-descriptors",
    "line" 25,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- get-property-descriptors [clazz]\n  (.getPropertyDescriptors (java.beans.Introspector/getBeanInfo clazz)))",
    "file" "clojure/java/data.clj",
    "arglists" [["clazz"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "is-setter",
    "line" 47,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- is-setter [method]\n  (and method (= 1 (alength (. method (getParameterTypes))))))",
    "file" "clojure/java/data.clj",
    "arglists" [["method"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "get-setter-type",
    "line" 50,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- get-setter-type [method]\n  (get (.getParameterTypes method) 0))",
    "file" "clojure/java/data.clj",
    "arglists" [["method"]]}
   {"private" true,
    "ns" "clojure.java.data",
    "name" "add-getter-fn",
    "line" 37,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- add-getter-fn [the-map prop-descriptor]\n  (let [name (.getName prop-descriptor)\n        method (.getReadMethod prop-descriptor)]\n    (if (and (is-getter method) (not (= \"class\" name)))\n      (assoc the-map (keyword name) (make-getter-fn method))\n      the-map)))",
    "file" "clojure/java/data.clj",
    "arglists" [["the-map" "prop-descriptor"]]}]},
 "description" "java.data 0.1.1",
 "version" "0.1.1",
 "name" "clojure.java.data",
 "group" "clojure.java.data"}
