{"namespaces"
 {"clojure.java.jmx"
  [{"private" true,
    "ns" "clojure.java.jmx",
    "name" "op-param-types",
    "line" 250,
    "column" 1,
    "doc"
    "The parameter types (as class name strings) for operation op on n.\n   Used for invoke.",
    "tag" nil,
    "source"
    "(defn- op-param-types\n  \"The parameter types (as class name strings) for operation op on n.\n   Used for invoke.\"\n  [n op]\n  (map #(-> % .getType) (.getSignature (operation n op))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "op"]]}
   {"ns" "clojure.java.jmx",
    "name" "invoke-signature",
    "line" 277,
    "column" 1,
    "doc"
    "Invoke an operation an an MBean. You must also supply\n  the signature of the operation. This is useful in cases\n  where the operation is overloaded. Otherwise you should\n  use the 'invoke' operation which will determine the\n  signature for you.\n\n  The signature parameter is a sequence of strings that\n  describes the method parameter types in order.",
    "tag" nil,
    "source"
    "(defn invoke-signature\n  \"Invoke an operation an an MBean. You must also supply\n  the signature of the operation. This is useful in cases\n  where the operation is overloaded. Otherwise you should\n  use the 'invoke' operation which will determine the\n  signature for you.\n\n  The signature parameter is a sequence of strings that\n  describes the method parameter types in order.\"\n  [n op signature & args]\n  (if ( seq args)\n    (.invoke *connection* (as-object-name n) (name op)\n             (into-array Object args)\n             (into-array String signature))\n    (.invoke *connection* (as-object-name n) (name op)\n             nil nil)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "op" "signature" "&" "args"]]}
   {"ns" "clojure.java.jmx",
    "name" "Destract",
    "line" 124,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol Destract\n  (objects->data [_] \"Convert JMX object model into data. Handles CompositeData, TabularData, maps, and atoms.\"))",
    "file" "clojure/java/jmx.clj",
    "arglists" nil}
   {"ns" "clojure.java.jmx",
    "name" "readable?",
    "line" 235,
    "column" 1,
    "doc" "Is attribute readable?",
    "tag" nil,
    "source"
    "(defn readable?\n  \"Is attribute readable?\"\n  [n attr]\n  (.isReadable (mbean-info n)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "attr"]]}
   {"ns" "clojure.java.jmx",
    "name" "*connection*",
    "line" 82,
    "column" 1,
    "doc"
    "The connection to be used for JMX ops. Defaults to the local process.",
    "tag" nil,
    "source"
    "(def ^{:dynamic true\n       :doc \"The connection to be used for JMX ops. Defaults to the local process.\"}\n  *connection*\n  (ManagementFactory/getPlatformMBeanServer))",
    "file" "clojure/java/jmx.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.java.jmx",
    "name" "write!",
    "line" 221,
    "column" 1,
    "doc" "Write an attribute value.",
    "tag" nil,
    "source"
    "(defn write!\n  \"Write an attribute value.\"\n  [n attr value]\n  (.setAttribute\n   *connection*\n   (as-object-name n)\n   (Attribute. (name attr) value)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "attr" "value"]]}
   {"ns" "clojure.java.jmx",
    "name" "raw-read",
    "line" 193,
    "column" 1,
    "doc"
    "Read a list of mbean properties. Returns low-level Java object\n   models for composites, tabulars, etc. Most callers should use\n   read.",
    "tag" nil,
    "source"
    "(defn raw-read\n  \"Read a list of mbean properties. Returns low-level Java object\n   models for composites, tabulars, etc. Most callers should use\n   read.\"\n  [n attrs]\n  (if (sequential? attrs)\n    (into {}\n          (map (fn [attr] [(keyword (.getName attr)) (.getValue attr)])\n               (.getAttributes *connection*\n                               (as-object-name n)\n                               (into-array (map name attrs)))))\n    (.getAttribute *connection* (as-object-name n) (name attrs))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "attrs"]]}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "build-attribute-info",
    "line" 162,
    "column" 1,
    "doc"
    "Construct an MBeanAttributeInfo. Normally called with a key/value pair from a Clojure map.",
    "tag" nil,
    "source"
    "(defn- build-attribute-info\n  \"Construct an MBeanAttributeInfo. Normally called with a key/value pair from a Clojure map.\"\n  ([attr-name attr-value]\n     (build-attribute-info\n      (name attr-name)\n      (guess-attribute-typename attr-value)\n      (name attr-name) true false false))\n  ([name type desc readable? writable? is?] (MBeanAttributeInfo. name type desc readable? writable? is? )))",
    "file" "clojure/java/jmx.clj",
    "arglists"
    [["attr-name" "attr-value"]
     ["name" "type" "desc" "readable?" "writable?" "is?"]]}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "maybe-atomize",
    "line" 112,
    "column" 1,
    "doc"
    "Convert a list of length 1 into its contents, leaving other things alone.\n  Used to simplify keys in the tabular data API.",
    "tag" nil,
    "source"
    "(defn- maybe-atomize\n  \"Convert a list of length 1 into its contents, leaving other things alone.\n  Used to simplify keys in the tabular data API.\"\n  [k]\n  (if (and (instance? java.util.List k)\n           (= 1 (count k)))\n    (first k)\n    k))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["k"]]}
   {"ns" "clojure.java.jmx",
    "name" "register-mbean",
    "line" 256,
    "column" 1,
    "doc" "Register an mbean with the current *connection*.",
    "tag" nil,
    "source"
    "(defn register-mbean\n  \"Register an mbean with the current *connection*.\"\n  [mbean mbean-name]\n  (.registerMBean *connection* mbean (as-object-name mbean-name)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["mbean" "mbean-name"]]}
   {"ns" "clojure.java.jmx",
    "name" "mbean-names",
    "line" 261,
    "column" 1,
    "doc"
    "Finds all MBeans matching a name on the current *connection*.",
    "tag" nil,
    "source"
    "(defn mbean-names\n  \"Finds all MBeans matching a name on the current *connection*.\"\n   [n]\n  (.queryNames *connection* (as-object-name n) nil))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.java.jmx",
    "name" "read",
    "line" 206,
    "column" 1,
    "doc" "Read one or more mbean properties.",
    "tag" nil,
    "source"
    "(def ^{:doc \"Read one or more mbean properties.\"}\n  read\n  (comp objects->data raw-read))",
    "file" "clojure/java/jmx.clj",
    "arglists" nil}
   {"arglists" [["n"]],
    "ns" "clojure.java.jmx",
    "name" "mbean-info",
    "column" 1,
    "line" 190,
    "source"
    "(defn mbean-info [n]\n  (.getMBeanInfo *connection* (as-object-name n)))",
    "file" "clojure/java/jmx.clj",
    "tag" nil}
   {"ns" "clojure.java.jmx",
    "name" "invoke",
    "line" 294,
    "column" 1,
    "doc" "Invoke an operation an an MBean.",
    "tag" nil,
    "source"
    "(defn invoke\n  \"Invoke an operation an an MBean.\"\n  [n op & args]\n  (apply invoke-signature n op (op-param-types n op) args))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "op" "&" "args"]]}
   {"ns" "clojure.java.jmx",
    "name" "with-connection",
    "macro" true,
    "line" 177,
    "column" 1,
    "doc"
    "Execute body with JMX connection specified by opts. opts can also\n   include an optional :environment key which is passed as the\n   environment arg to JMXConnectorFactory/connect.",
    "tag" nil,
    "source"
    "(defmacro with-connection\n  \"Execute body with JMX connection specified by opts. opts can also\n   include an optional :environment key which is passed as the\n   environment arg to JMXConnectorFactory/connect.\"\n  [opts & body]\n  `(let [opts# ~opts\n         env# (get opts# :environment {})\n         opts# (dissoc opts# :environment)]\n     (with-open [connector# (javax.management.remote.JMXConnectorFactory/connect\n                             (JMXServiceURL. (jmx-url opts#)) env#)]\n       (binding [*connection* (.getMBeanServerConnection connector#)]\n         ~@body))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["opts" "&" "body"]]}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "guess-attribute-typename",
    "line" 156,
    "column" 1,
    "doc"
    "Guess the attribute typename for MBeanAttributeInfo based on the attribute value.",
    "tag" nil,
    "source"
    "(defn- guess-attribute-typename\n  \"Guess the attribute typename for MBeanAttributeInfo based on the attribute value.\"\n  [value]\n  (let [classname (.getName (class value))]\n    (get guess-attribute-map classname classname)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["value"]]}
   {"ns" "clojure.java.jmx",
    "name" "create-bean",
    "line" 324,
    "column" 1,
    "doc"
    "Expose a reference as a JMX bean. state-ref should be a Clojure\n   reference (ref, atom, agent) containing a map.",
    "tag" nil,
    "source"
    "(defn create-bean\n  \"Expose a reference as a JMX bean. state-ref should be a Clojure\n   reference (ref, atom, agent) containing a map.\"\n  [state-ref]\n  (Bean. state-ref))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["state-ref"]]}
   {"ns" "clojure.java.jmx",
    "name" "jmx-url",
    "line" 89,
    "column" 1,
    "doc" "Build a JMX URL from options.",
    "tag" nil,
    "source"
    "(defn jmx-url\n  \"Build a JMX URL from options.\"\n  ([] (jmx-url {}))\n  ([overrides]\n     (let [opts (merge {:host \"localhost\", :port \"3000\", :jndi-path \"jmxrmi\"} overrides)]\n       (format \"service:jmx:rmi:///jndi/rmi://%s:%s/%s\" (opts :host) (opts :port) (opts :jndi-path)))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [[] ["overrides"]]}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "read-supported",
    "line" 210,
    "column" 1,
    "doc"
    "Calls read to read an mbean property, *returning* unsupported\n   operation exceptions instead of throwing them. Used to keep mbean\n   from blowing up. Note: There is no good exception that aggregates\n   unsupported operations, hence the overly-general catch block.",
    "tag" nil,
    "source"
    "(defn- read-supported\n  \"Calls read to read an mbean property, *returning* unsupported\n   operation exceptions instead of throwing them. Used to keep mbean\n   from blowing up. Note: There is no good exception that aggregates\n   unsupported operations, hence the overly-general catch block.\"\n  [n attr]\n  (try\n   (read n attr)\n   (catch Exception e\n     e)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "attr"]]}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "guess-attribute-map",
    "line" 150,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:private true} guess-attribute-map\n     {\"java.lang.Integer\" \"int\"\n      \"java.lang.Boolean\" \"boolean\"\n      \"java.lang.Long\" \"long\"\n      })",
    "file" "clojure/java/jmx.clj",
    "arglists" nil}
   {"ns" "clojure.java.jmx",
    "name" "operation-names",
    "line" 272,
    "column" 1,
    "doc" "All operation names available on an MBean.",
    "tag" nil,
    "source"
    "(defn operation-names\n  \"All operation names available on an MBean.\"\n  [n]\n  (doall (map #(-> % .getName keyword) (operations n))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.java.jmx",
    "name" "jmx->clj",
    "line" 87,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source" "(declare jmx->clj)",
    "file" "clojure/java/jmx.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "simplify-tabular-data-key",
    "line" 121,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:private true} simplify-tabular-data-key\n  (comp maybe-keywordize maybe-atomize))",
    "file" "clojure/java/jmx.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "map->attribute-infos",
    "line" 171,
    "column" 1,
    "doc"
    "Construct an MBeanAttributeInfo[] from a Clojure associative.",
    "tag" nil,
    "source"
    "(defn- map->attribute-infos\n  \"Construct an MBeanAttributeInfo[] from a Clojure associative.\"\n  [attr-map]\n  (into-array (map (fn [[attr-name value]] (build-attribute-info attr-name value))\n                   attr-map)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["attr-map"]]}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "operation",
    "line" 245,
    "column" 1,
    "doc"
    "The MBeanOperationInfo for operation op on mbean n. Used by invoke.",
    "tag" nil,
    "source"
    "(defn- operation\n  \"The MBeanOperationInfo for operation op on mbean n. Used by invoke.\"\n  [n op]\n  (first  (filter #(= (-> % .getName keyword) op) (operations n))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n" "op"]]}
   {"ns" "clojure.java.jmx",
    "name" "CoercionImpl",
    "line" 96,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol CoercionImpl\n  (as-object-name [_]))",
    "file" "clojure/java/jmx.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.java.jmx",
    "doc" nil,
    "arglists" [["_"]],
    "name" "as-object-name"}
   {"ns" "clojure.java.jmx",
    "name" "attribute-info",
    "line" 229,
    "column" 1,
    "doc" "Get the MBeanAttributeInfo for an attribute.",
    "tag" nil,
    "source"
    "(defn attribute-info\n  \"Get the MBeanAttributeInfo for an attribute.\"\n  [object-name attr-name]\n  (filter #(= (name attr-name) (.getName %))\n          (.getAttributes (mbean-info object-name))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["object-name" "attr-name"]]}
   {"ns" "clojure.java.jmx",
    "name" "->Bean",
    "line" 307,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.java.jmx.Bean.",
    "tag" nil,
    "source"
    "(deftype Bean [state-ref]\n  DynamicMBean\n  (getMBeanInfo [_]\n                (MBeanInfo. (.. _ getClass getName)                             ; class name\n                            \"Clojure Dynamic MBean\"                             ; description\n                            (map->attribute-infos @state-ref)                   ; attributes\n                            nil                                                 ; constructors\n                            nil                                                 ; operations\n                            nil))\n  (getAttribute [_ attr]\n                (@state-ref (keyword attr)))\n  (getAttributes [_ attrs]\n                 (let [result (AttributeList.)]\n                   (doseq [attr attrs]\n                     (.add result (.getAttribute _ attr)))\n                   result)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["state-ref"]]}
   {"ns" "clojure.java.jmx",
    "name" "operations",
    "line" 240,
    "column" 1,
    "doc" "All oeprations available on an MBean.",
    "tag" nil,
    "source"
    "(defn operations\n  \"All oeprations available on an MBean.\"\n  [n]\n  (.getOperations (mbean-info n)))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.java.jmx",
    "name" "attribute-names",
    "line" 266,
    "column" 1,
    "doc" "All attribute names available on an MBean.",
    "tag" nil,
    "source"
    "(defn attribute-names\n  \"All attribute names available on an MBean.\"\n  [n]\n  (doall (map #(-> % .getName keyword)\n              (.getAttributes (mbean-info n)))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.java.jmx",
    "doc"
    "Convert JMX object model into data. Handles CompositeData, TabularData, maps, and atoms.",
    "arglists" [["_"]],
    "name" "objects->data"}
   {"private" true,
    "ns" "clojure.java.jmx",
    "name" "maybe-keywordize",
    "line" 106,
    "column" 1,
    "doc"
    "Convert a string key to a keyword, leaving other types alone. Used to\n   simplify keys in the tabular data API.",
    "tag" nil,
    "source"
    "(defn- maybe-keywordize\n  \"Convert a string key to a keyword, leaving other types alone. Used to\n   simplify keys in the tabular data API.\"\n  [s]\n  (if (string? s) (keyword s) s))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.java.jmx",
    "name" "mbean",
    "line" 299,
    "column" 1,
    "doc"
    "Like clojure.core/bean, but for JMX beans. Returns a read-only map of\n   a JMX bean's attributes. If an attribute it not supported, value is\n   set to the exception thrown.",
    "tag" nil,
    "source"
    "(defn mbean\n  \"Like clojure.core/bean, but for JMX beans. Returns a read-only map of\n   a JMX bean's attributes. If an attribute it not supported, value is\n   set to the exception thrown.\"\n  [n]\n  (into {} (map (fn [attr-name] [(keyword attr-name) (read-supported n attr-name)])\n                (attribute-names n))))",
    "file" "clojure/java/jmx.clj",
    "arglists" [["n"]]}]},
 "description" "java.jmx 0.2.0",
 "version" "0.2.0",
 "name" "clojure.java.jmx",
 "group" "clojure.java.jmx"}
