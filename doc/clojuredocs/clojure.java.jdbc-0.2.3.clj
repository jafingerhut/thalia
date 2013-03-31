{"namespaces"
 {"clojure.java.jdbc"
  [{"ns" "clojure.java.jdbc",
    "name" "set-rollback-only",
    "line" 399,
    "column" 1,
    "doc"
    "Marks the outermost transaction such that it will rollback rather than\n  commit when complete",
    "tag" nil,
    "source"
    "(defn set-rollback-only\n  \"Marks the outermost transaction such that it will rollback rather than\n  commit when complete\"\n  []\n  (rollback true))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "make-name-unique",
    "line" 195,
    "column" 1,
    "doc"
    "Given a collection of column names and a new column name,\n   return the new column name made unique, if necessary, by\n   appending _N where N is some unique integer suffix.",
    "tag" nil,
    "source"
    "(defn- make-name-unique\n  \"Given a collection of column names and a new column name,\n   return the new column name made unique, if necessary, by\n   appending _N where N is some unique integer suffix.\"\n  [cols col-name n]\n  (let [suffixed-name (if (= n 1) col-name (str col-name \"_\" n))]\n    (if (apply distinct? suffixed-name cols)\n      suffixed-name\n      (recur cols col-name (inc n)))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["cols" "col-name" "n"]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-keyword",
    "line" 90,
    "column" 1,
    "doc"
    "Given an entity name (string), convert it to a keyword using the\n   current naming strategy.\n   Given a keyword, return it as-is.",
    "tag" nil,
    "source"
    "(defn as-keyword\n  \"Given an entity name (string), convert it to a keyword using the\n   current naming strategy.\n   Given a keyword, return it as-is.\"\n  ([x] (as-keyword x *as-key*))\n  ([x f-keyword] (as-key f-keyword x)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["x"] ["x" "f-keyword"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "result-set-type",
    "line" 444,
    "column" 1,
    "doc" "Map friendly :type values to ResultSet constants.",
    "tag" nil,
    "source"
    "(def ^{:private true\n       :doc \"Map friendly :type values to ResultSet constants.\"} \n  result-set-type\n  {:forward-only ResultSet/TYPE_FORWARD_ONLY\n   :scroll-insensitive ResultSet/TYPE_SCROLL_INSENSITIVE\n   :scroll-sensitive ResultSet/TYPE_SCROLL_SENSITIVE})",
    "file" "clojure/java/jdbc.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "subprotocols",
    "line" 117,
    "column" 1,
    "doc" "Map of schemes to subprotocols",
    "tag" nil,
    "source"
    "(def ^{:private true :doc \"Map of schemes to subprotocols\"} subprotocols\n  {\"postgres\" \"postgresql\"})",
    "file" "clojure/java/jdbc.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "*as-key*",
    "line" 52,
    "column" 1,
    "doc"
    "The default keyword naming strategy is to lowercase the entity.",
    "tag" nil,
    "source"
    "(def ^{:private true :dynamic true\n       :doc \"The default keyword naming strategy is to lowercase the entity.\"}\n  *as-key*\n  str/lower-case)",
    "file" "clojure/java/jdbc.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.java.jdbc",
    "name" "update-values",
    "line" 615,
    "column" 1,
    "doc"
    "Updates values on selected rows in a table. where-params is a vector\n  containing a string providing the (optionally parameterized) selection\n  criteria followed by values for any parameters. record is a map from\n  strings or keywords (identifying columns) to updated values.",
    "tag" nil,
    "source"
    "(defn update-values\n  \"Updates values on selected rows in a table. where-params is a vector\n  containing a string providing the (optionally parameterized) selection\n  criteria followed by values for any parameters. record is a map from\n  strings or keywords (identifying columns) to updated values.\"\n  [table where-params record]\n  (let [[where & params] where-params\n        column-strs (map as-identifier (keys record))\n        columns (apply str (concat (interpose \"=?, \" column-strs) \"=?\"))]\n    (do-prepared\n      (format \"UPDATE %s SET %s WHERE %s\"\n              (as-identifier table) columns where)\n      (concat (vals record) params))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "where-params" "record"]]}
   {"ns" "clojure.java.jdbc",
    "name" "print-sql-exception",
    "line" 687,
    "column" 1,
    "doc" "Prints the contents of an SQLException to *out*",
    "tag" nil,
    "source"
    "(defn print-sql-exception\n  \"Prints the contents of an SQLException to *out*\"\n  [^SQLException exception]\n  (let [^Class exception-class (class exception)]\n    (println\n      (format (str \"%s:\" \\newline\n                   \" Message: %s\" \\newline\n                   \" SQLState: %s\" \\newline\n                   \" Error Code: %d\")\n              (.getSimpleName exception-class)\n              (.getMessage exception)\n              (.getSQLState exception)\n              (.getErrorCode exception)))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["exception"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "result-set-holdability",
    "line" 438,
    "column" 1,
    "doc" "Map friendly :cursors values to ResultSet constants.",
    "tag" nil,
    "source"
    "(def ^{:private true\n       :doc \"Map friendly :cursors values to ResultSet constants.\"} \n  result-set-holdability\n  {:hold ResultSet/HOLD_CURSORS_OVER_COMMIT\n   :close ResultSet/CLOSE_CURSORS_AT_COMMIT})",
    "file" "clojure/java/jdbc.clj",
    "arglists" nil}
   {"ns" "clojure.java.jdbc",
    "name" "drop-table",
    "line" 533,
    "column" 1,
    "doc"
    "Drops a table on the open database connection given its name, a string\n  or keyword",
    "tag" nil,
    "source"
    "(defn drop-table\n  \"Drops a table on the open database connection given its name, a string\n  or keyword\"\n  [name]\n  (do-commands\n    (format \"DROP TABLE %s\" (as-identifier name))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["name"]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-identifier",
    "line" 83,
    "column" 1,
    "doc"
    "Given a keyword, convert it to a string using the current naming\n   strategy.\n   Given a string, return it as-is.",
    "tag" nil,
    "source"
    "(defn as-identifier\n  \"Given a keyword, convert it to a string using the current naming\n   strategy.\n   Given a string, return it as-is.\"\n  ([x] (as-identifier x *as-str*))\n  ([x f-entity] (as-str f-entity x)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["x"] ["x" "f-entity"]]}
   {"ns" "clojure.java.jdbc",
    "name" "print-sql-exception-chain",
    "line" 701,
    "column" 1,
    "doc" "Prints a chain of SQLExceptions to *out*",
    "tag" nil,
    "source"
    "(defn print-sql-exception-chain\n  \"Prints a chain of SQLExceptions to *out*\"\n  [^SQLException exception]\n  (loop [e exception]\n    (when e\n      (print-sql-exception e)\n      (recur (.getNextException e)))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["exception"]]}
   {"ns" "clojure.java.jdbc",
    "name" "find-connection",
    "line" 120,
    "column" 1,
    "doc"
    "Returns the current database connection (or nil if there is none)",
    "tag" nil,
    "source"
    "(defn find-connection\n  \"Returns the current database connection (or nil if there is none)\"\n  ^java.sql.Connection []\n  (:connection *db*))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [[]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-str",
    "line" 57,
    "column" 1,
    "doc"
    "Given a naming strategy and a keyword, return the keyword as a\n   string per that naming strategy. Given (a naming strategy and)\n   a string, return it as-is.\n   A keyword of the form :x.y is treated as keywords :x and :y,\n   both are turned into strings via the naming strategy and then\n   joined back together so :x.y might become `x`.`y` if the naming\n   strategy quotes identifiers with `.",
    "tag" nil,
    "source"
    "(defn as-str\n  \"Given a naming strategy and a keyword, return the keyword as a\n   string per that naming strategy. Given (a naming strategy and)\n   a string, return it as-is.\n   A keyword of the form :x.y is treated as keywords :x and :y,\n   both are turned into strings via the naming strategy and then\n   joined back together so :x.y might become `x`.`y` if the naming\n   strategy quotes identifiers with `.\"\n  [f x]\n  (if (instance? clojure.lang.Named x)\n    (let [n (name x)\n          i (.indexOf n (int \\.))]\n      (if (= -1 i)\n        (f n)\n        (str/join \".\" (map f (.split n \"\\\\.\")))))\n    (str x)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["f" "x"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "parse-properties-uri",
    "line" 131,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- parse-properties-uri [^URI uri]\n  (let [host (.getHost uri)\n        port (if (pos? (.getPort uri)) (.getPort uri))\n        path (.getPath uri)\n        scheme (.getScheme uri)]\n    (merge\n     {:subname (if port\n                 (str \"//\" host \":\" port path)\n                 (str \"//\" host path))\n      :subprotocol (subprotocols scheme scheme)}\n     (if-let [user-info (.getUserInfo uri)]\n             {:user (first (str/split user-info #\":\"))\n              :password (second (str/split user-info #\":\"))}))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["uri"]]}
   {"ns" "clojure.java.jdbc",
    "name" "with-query-results*",
    "line" 642,
    "column" 1,
    "doc"
    "Executes a query, then evaluates func passing in a seq of the results as\n  an argument. The first argument is a vector containing either:\n    [sql & params] - a SQL query, followed by any parameters it needs\n    [stmt & params] - a PreparedStatement, followed by any parameters it needs\n                      (the PreparedStatement already contains the SQL query)\n    [options sql & params] - options and a SQL query for creating a\n                      PreparedStatement, follwed by any parameters it needs\n  See prepare-statement for supported options.",
    "tag" nil,
    "source"
    "(defn with-query-results*\n  \"Executes a query, then evaluates func passing in a seq of the results as\n  an argument. The first argument is a vector containing either:\n    [sql & params] - a SQL query, followed by any parameters it needs\n    [stmt & params] - a PreparedStatement, followed by any parameters it needs\n                      (the PreparedStatement already contains the SQL query)\n    [options sql & params] - options and a SQL query for creating a\n                      PreparedStatement, follwed by any parameters it needs\n  See prepare-statement for supported options.\"\n  [sql-params func]\n  (when-not (vector? sql-params)\n    (let [^Class sql-params-class (class sql-params)\n          ^String msg (format \"\\\"%s\\\" expected %s %s, found %s %s\"\n                              \"sql-params\"\n                              \"vector\"\n                              \"[sql param*]\"\n                              (.getName sql-params-class)\n                              (pr-str sql-params))] \n      (throw (IllegalArgumentException. msg))))\n  (let [special (first sql-params)\n        sql-is-first (string? special)\n        options-are-first (map? special)\n        sql (cond sql-is-first special \n                  options-are-first (second sql-params))\n        params (vec (cond sql-is-first (rest sql-params)\n                          options-are-first (rest (rest sql-params))\n                          :else (rest sql-params)))\n        prepare-args (when (map? special) (flatten (seq special)))]\n    (with-open [^PreparedStatement stmt (if (instance? PreparedStatement special) special (apply prepare-statement (connection) sql prepare-args))]\n      (set-parameters stmt params)\n      (with-open [rset (.executeQuery stmt)]\n        (func (resultset-seq rset))))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["sql-params" "func"]]}
   {"ns" "clojure.java.jdbc",
    "name" "with-query-results",
    "macro" true,
    "line" 675,
    "column" 1,
    "doc"
    "Executes a query, then evaluates body with results bound to a seq of the\n  results. sql-params is a vector containing either:\n    [sql & params] - a SQL query, followed by any parameters it needs\n    [stmt & params] - a PreparedStatement, followed by any parameters it needs\n                      (the PreparedStatement already contains the SQL query)\n    [options sql & params] - options and a SQL query for creating a\n                      PreparedStatement, follwed by any parameters it needs\n  See prepare-statement for supported options.",
    "tag" nil,
    "source"
    "(defmacro with-query-results\n  \"Executes a query, then evaluates body with results bound to a seq of the\n  results. sql-params is a vector containing either:\n    [sql & params] - a SQL query, followed by any parameters it needs\n    [stmt & params] - a PreparedStatement, followed by any parameters it needs\n                      (the PreparedStatement already contains the SQL query)\n    [options sql & params] - options and a SQL query for creating a\n                      PreparedStatement, follwed by any parameters it needs\n  See prepare-statement for supported options.\"\n  [results sql-params & body]\n  `(with-query-results* ~sql-params (fn [~results] ~@body)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["results" "sql-params" "&" "body"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "as-properties",
    "line" 97,
    "column" 1,
    "doc"
    "Convert any seq of pairs to a java.utils.Properties instance.\n   Uses as-str to convert both keys and values into strings.",
    "tag" "java.util.Properties",
    "source"
    "(defn- ^Properties as-properties\n  \"Convert any seq of pairs to a java.utils.Properties instance.\n   Uses as-str to convert both keys and values into strings.\"\n  [m]\n  (let [p (Properties.)]\n    (doseq [[k v] m]\n      (.setProperty p (as-str identity k) (as-str identity v)))\n    p))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["m"]]}
   {"ns" "clojure.java.jdbc",
    "name" "prepare-statement",
    "line" 451,
    "column" 1,
    "doc"
    "Create a prepared statement from a connection, a SQL string and an\n   optional list of parameters:\n     :return-keys true | false - default false\n     :result-type :forward-only | :scroll-insensitive | :scroll-sensitive\n     :concurrency :read-only | :updatable\n     :fetch-size n\n     :max-rows n",
    "tag" nil,
    "source"
    "(defn prepare-statement\n  \"Create a prepared statement from a connection, a SQL string and an\n   optional list of parameters:\n     :return-keys true | false - default false\n     :result-type :forward-only | :scroll-insensitive | :scroll-sensitive\n     :concurrency :read-only | :updatable\n     :fetch-size n\n     :max-rows n\"\n  [^java.sql.Connection con ^String sql & {:keys [return-keys result-type concurrency cursors fetch-size max-rows]}]\n  (let [^PreparedStatement stmt (cond\n                                  return-keys (try\n                                                (.prepareStatement con sql java.sql.Statement/RETURN_GENERATED_KEYS)\n                                                (catch Exception _\n                                                  ;; assume it is unsupported and try basic PreparedStatement:\n                                                  (.prepareStatement con sql)))\n                                  (and result-type concurrency) (if cursors\n                                                                  (.prepareStatement con sql \n                                                                                     (result-type result-set-type)\n                                                                                     (concurrency result-set-concurrency)\n                                                                                     (cursors result-set-holdability))\n                                                                  (.prepareStatement con sql \n                                                                                     (result-type result-set-type)\n                                                                                     (concurrency result-set-concurrency)))\n                                  :else (.prepareStatement con sql))]\n    (when fetch-size (.setFetchSize stmt fetch-size))\n    (when max-rows (.setMaxRows stmt max-rows))\n    stmt))",
    "file" "clojure/java/jdbc.clj",
    "arglists"
    [["con"
      "sql"
      "&"
      [["keys"
        ["return-keys"
         "result-type"
         "concurrency"
         "cursors"
         "fetch-size"
         "max-rows"]]]]]}
   {"ns" "clojure.java.jdbc",
    "name" "insert-records",
    "line" 588,
    "column" 1,
    "doc"
    "Inserts records into a table. records are maps from strings or keywords\n  (identifying columns) to values. Inserts the records one at a time.\n  Returns a sequence of maps containing the generated keys for each record.",
    "tag" nil,
    "source"
    "(defn insert-records\n  \"Inserts records into a table. records are maps from strings or keywords\n  (identifying columns) to values. Inserts the records one at a time.\n  Returns a sequence of maps containing the generated keys for each record.\"\n  [table & records]\n  (let [ins-v (fn [record] (insert-values table (keys record) (vals record)))]\n    (doall (map ins-v records))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "&" "records"]]}
   {"ns" "clojure.java.jdbc",
    "name" "print-update-counts",
    "line" 713,
    "column" 1,
    "doc"
    "Prints the update counts from a BatchUpdateException to *out*",
    "tag" nil,
    "source"
    "(defn print-update-counts\n  \"Prints the update counts from a BatchUpdateException to *out*\"\n  [^BatchUpdateException exception]\n  (println \"Update counts:\")\n  (dorun \n    (map-indexed \n      (fn [index count] \n        (println (format \" Statement %d: %s\"\n                         index\n                         (get special-counts count count)))) \n      (.getUpdateCounts exception))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["exception"]]}
   {"ns" "clojure.java.jdbc",
    "name" "with-naming-strategy",
    "macro" true,
    "line" 281,
    "column" 1,
    "doc"
    "Evaluates body in the context of a naming strategy.\n   The naming strategy is either a function - the entity naming strategy - or\n   a map containing :entity and/or :keyword keys which provide the entity naming\n   strategy and/or the keyword naming strategy respectively. The default entity\n   naming strategy is identity; the default keyword naming strategy is lower-case.",
    "tag" nil,
    "source"
    "(defmacro with-naming-strategy\n  \"Evaluates body in the context of a naming strategy.\n   The naming strategy is either a function - the entity naming strategy - or\n   a map containing :entity and/or :keyword keys which provide the entity naming\n   strategy and/or the keyword naming strategy respectively. The default entity\n   naming strategy is identity; the default keyword naming strategy is lower-case.\"\n  [naming-strategy & body ]\n  `(binding [*as-str* (if (map? ~naming-strategy) (or (:entity ~naming-strategy) identity) ~naming-strategy)\n             *as-key* (if (map? ~naming-strategy) (or (:keyword ~naming-strategy) str/lower-case))] ~@body))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["naming-strategy" "&" "body"]]}
   {"ns" "clojure.java.jdbc",
    "name" "create-table-ddl",
    "line" 503,
    "column" 1,
    "doc"
    "Given a table name and column specs with an optional table-spec\n   return the DDL string for creating a table based on that.",
    "tag" nil,
    "source"
    "(defn create-table-ddl\n  \"Given a table name and column specs with an optional table-spec\n   return the DDL string for creating a table based on that.\"\n  [name & specs]\n  (let [split-specs (partition-by #(= :table-spec %) specs)\n        col-specs (first split-specs)\n        table-spec (first (second (rest split-specs)))\n        table-spec-str (or (and table-spec (str \" \" table-spec)) \"\")\n        specs-to-string (fn [specs]\n                          (apply str\n                                 (map as-identifier\n                                      (apply concat\n                                             (interpose [\", \"]\n                                                        (map (partial interpose \" \") specs))))))]\n    (format \"CREATE TABLE %s (%s)%s\"\n            (as-identifier name)\n            (specs-to-string col-specs)\n            table-spec-str)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["name" "&" "specs"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "strip-jdbc",
    "line" 145,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- strip-jdbc [^String spec]\n  (if (.startsWith spec \"jdbc:\")\n    (.substring spec 5)\n    spec))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["spec"]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-quoted-str",
    "line" 239,
    "column" 1,
    "doc"
    "Given a quoting pattern - either a single character or a vector pair of\n   characters - and a string, return the quoted string:\n     (as-quoted-str X foo) will return XfooX\n     (as-quoted-str [A B] foo) will return AfooB",
    "tag" nil,
    "source"
    "(defn as-quoted-str\n  \"Given a quoting pattern - either a single character or a vector pair of\n   characters - and a string, return the quoted string:\n     (as-quoted-str X foo) will return XfooX\n     (as-quoted-str [A B] foo) will return AfooB\"\n  [q x]\n  (if (vector? q)\n    (str (first q) x (last q))\n    (str q x q)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["q" "x"]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-named-keyword",
    "line" 259,
    "column" 1,
    "doc"
    "Given a naming strategy and a string, return the string as a keyword using the \n   keyword naming strategy.\n   Given a naming strategy and a keyword, return the keyword as-is.\n   The naming strategy should either be a function (the entity naming strategy) or \n   a map containing :entity and/or :keyword keys which provide the entity naming\n   strategy and/or keyword naming strategy respectively.\n   Note that providing a single function will cause the default keyword naming\n   strategy to be used!",
    "tag" nil,
    "source"
    "(defn as-named-keyword\n  \"Given a naming strategy and a string, return the string as a keyword using the \n   keyword naming strategy.\n   Given a naming strategy and a keyword, return the keyword as-is.\n   The naming strategy should either be a function (the entity naming strategy) or \n   a map containing :entity and/or :keyword keys which provide the entity naming\n   strategy and/or keyword naming strategy respectively.\n   Note that providing a single function will cause the default keyword naming\n   strategy to be used!\"\n  [naming-strategy x]\n  (as-keyword x (if (and (map? naming-strategy) (:keyword naming-strategy)) (:keyword naming-strategy) str/lower-case)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["naming-strategy" "x"]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-key",
    "line" 74,
    "column" 1,
    "doc"
    "Given a naming strategy and a string, return the string as a\n   keyword per that naming strategy. Given (a naming strategy and)\n   a keyword, return it as-is.",
    "tag" nil,
    "source"
    "(defn as-key\n  \"Given a naming strategy and a string, return the string as a\n   keyword per that naming strategy. Given (a naming strategy and)\n   a keyword, return it as-is.\"\n  [f x]\n  (if (instance? clojure.lang.Named x)\n    x\n    (keyword (f (str x)))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["f" "x"]]}
   {"ns" "clojure.java.jdbc",
    "name" "connection",
    "line" 125,
    "column" 1,
    "doc"
    "Returns the current database connection (or throws if there is none)",
    "tag" nil,
    "source"
    "(defn connection\n  \"Returns the current database connection (or throws if there is none)\"\n  ^java.sql.Connection []\n  (or (find-connection)\n      (throw (Exception. \"no current database connection\"))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [[]]}
   {"ns" "clojure.java.jdbc",
    "name" "transaction",
    "macro" true,
    "line" 388,
    "column" 1,
    "doc"
    "Evaluates body as a transaction on the open database connection. Any\n  nested transactions are absorbed into the outermost transaction. By\n  default, all database updates are committed together as a group after\n  evaluating the outermost body, or rolled back on any uncaught\n  exception. If set-rollback-only is called within scope of the outermost\n  transaction, the entire transaction will be rolled back rather than\n  committed when complete.",
    "tag" nil,
    "source"
    "(defmacro transaction\n  \"Evaluates body as a transaction on the open database connection. Any\n  nested transactions are absorbed into the outermost transaction. By\n  default, all database updates are committed together as a group after\n  evaluating the outermost body, or rolled back on any uncaught\n  exception. If set-rollback-only is called within scope of the outermost\n  transaction, the entire transaction will be rolled back rather than\n  committed when complete.\"\n  [& body]\n  `(transaction* (fn [] ~@body)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["&" "body"]]}
   {"ns" "clojure.java.jdbc",
    "name" "is-rollback-only",
    "line" 405,
    "column" 1,
    "doc"
    "Returns true if the outermost transaction will rollback rather than\n  commit when complete",
    "tag" nil,
    "source"
    "(defn is-rollback-only\n  \"Returns true if the outermost transaction will rollback rather than\n  commit when complete\"\n  []\n  (rollback))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "result-set-concurrency",
    "line" 432,
    "column" 1,
    "doc" "Map friendly :concurrency values to ResultSet constants.",
    "tag" nil,
    "source"
    "(def ^{:private true\n       :doc \"Map friendly :concurrency values to ResultSet constants.\"} \n  result-set-concurrency\n  {:read-only ResultSet/CONCUR_READ_ONLY\n   :updatable ResultSet/CONCUR_UPDATABLE})",
    "file" "clojure/java/jdbc.clj",
    "arglists" nil}
   {"ns" "clojure.java.jdbc",
    "name" "with-connection",
    "macro" true,
    "line" 304,
    "column" 1,
    "doc"
    "Evaluates body in the context of a new connection to a database then\n  closes the connection. db-spec is a map containing values for one of the\n  following parameter sets:\n\n  Factory:\n    :factory     (required) a function of one argument, a map of params\n    (others)     (optional) passed to the factory function in a map\n\n  DriverManager:\n    :subprotocol (required) a String, the jdbc subprotocol\n    :subname     (required) a String, the jdbc subname\n    :classname   (optional) a String, the jdbc driver class name\n    (others)     (optional) passed to the driver as properties.\n\n  DataSource:\n    :datasource  (required) a javax.sql.DataSource\n    :username    (optional) a String\n    :password    (optional) a String, required if :username is supplied\n\n  JNDI:\n    :name        (required) a String or javax.naming.Name\n    :environment (optional) a java.util.Map\n\n  Raw:\n    :connection-uri (required) a String\n                 Passed directly to DriverManager/getConnection\n\n  URI:\n    Parsed JDBC connection string - see below\n  \n  String:\n    subprotocol://user:password@host:post/subname\n                 An optional prefix of jdbc: is allowed.",
    "tag" nil,
    "source"
    "(defmacro with-connection\n  \"Evaluates body in the context of a new connection to a database then\n  closes the connection. db-spec is a map containing values for one of the\n  following parameter sets:\n\n  Factory:\n    :factory     (required) a function of one argument, a map of params\n    (others)     (optional) passed to the factory function in a map\n\n  DriverManager:\n    :subprotocol (required) a String, the jdbc subprotocol\n    :subname     (required) a String, the jdbc subname\n    :classname   (optional) a String, the jdbc driver class name\n    (others)     (optional) passed to the driver as properties.\n\n  DataSource:\n    :datasource  (required) a javax.sql.DataSource\n    :username    (optional) a String\n    :password    (optional) a String, required if :username is supplied\n\n  JNDI:\n    :name        (required) a String or javax.naming.Name\n    :environment (optional) a java.util.Map\n\n  Raw:\n    :connection-uri (required) a String\n                 Passed directly to DriverManager/getConnection\n\n  URI:\n    Parsed JDBC connection string - see below\n  \n  String:\n    subprotocol://user:password@host:post/subname\n                 An optional prefix of jdbc: is allowed.\"\n  [db-spec & body]\n  `(with-connection* ~db-spec (fn [] ~@body)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["db-spec" "&" "body"]]}
   {"ns" "clojure.java.jdbc",
    "name" "as-named-identifier",
    "line" 249,
    "column" 1,
    "doc"
    "Given a naming strategy and a keyword, return the keyword as a string using the \n   entity naming strategy.\n   Given a naming strategy and a string, return the string as-is.\n   The naming strategy should either be a function (the entity naming strategy) or \n   a map containing :entity and/or :keyword keys which provide the entity naming\n   strategy and/or keyword naming strategy respectively.",
    "tag" nil,
    "source"
    "(defn as-named-identifier\n  \"Given a naming strategy and a keyword, return the keyword as a string using the \n   entity naming strategy.\n   Given a naming strategy and a string, return the string as-is.\n   The naming strategy should either be a function (the entity naming strategy) or \n   a map containing :entity and/or :keyword keys which provide the entity naming\n   strategy and/or keyword naming strategy respectively.\"\n  [naming-strategy x]\n  (as-identifier x (if (map? naming-strategy) (or (:entity naming-strategy) identity) naming-strategy)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["naming-strategy" "x"]]}
   {"ns" "clojure.java.jdbc",
    "name" "insert-values",
    "line" 560,
    "column" 1,
    "doc"
    "Inserts rows into a table with values for specified columns only.\n  column-names is a vector of strings or keywords identifying columns. Each\n  value-group is a vector containing a values for each column in\n  order. When inserting complete rows (all columns), consider using\n  insert-rows instead.\n  If a single set of values is inserted, returns a map of the generated keys.",
    "tag" nil,
    "source"
    "(defn insert-values\n  \"Inserts rows into a table with values for specified columns only.\n  column-names is a vector of strings or keywords identifying columns. Each\n  value-group is a vector containing a values for each column in\n  order. When inserting complete rows (all columns), consider using\n  insert-rows instead.\n  If a single set of values is inserted, returns a map of the generated keys.\"\n  [table column-names & value-groups]\n  (let [column-strs (map as-identifier column-names)\n        n (count (first value-groups))\n        return-keys (= 1 (count value-groups))\n        prepared-statement (if return-keys do-prepared-return-keys do-prepared)\n        template (apply str (interpose \",\" (repeat n \"?\")))\n        columns (if (seq column-names)\n                  (format \"(%s)\" (apply str (interpose \",\" column-strs)))\n                  \"\")]\n    (apply prepared-statement\n           (format \"INSERT INTO %s %s VALUES (%s)\"\n                   (as-identifier table) columns template)\n           value-groups)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "column-names" "&" "value-groups"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "make-cols-unique",
    "line" 205,
    "column" 1,
    "doc"
    "Given a collection of column names, rename duplicates so\n   that the result is a collection of unique column names.",
    "tag" nil,
    "source"
    "(defn- make-cols-unique\n  \"Given a collection of column names, rename duplicates so\n   that the result is a collection of unique column names.\"\n  [cols]\n  (if (or (empty? cols) (apply distinct? cols))\n    cols\n    (reduce (fn [unique-cols col-name] (conj unique-cols (make-name-unique unique-cols col-name 1))) []  cols)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["cols"]]}
   {"ns" "clojure.java.jdbc",
    "name" "transaction*",
    "line" 348,
    "column" 1,
    "doc"
    "Evaluates func as a transaction on the open database connection. Any\n  nested transactions are absorbed into the outermost transaction. By\n  default, all database updates are committed together as a group after\n  evaluating the outermost body, or rolled back on any uncaught\n  exception. If rollback is set within scope of the outermost transaction,\n  the entire transaction will be rolled back rather than committed when\n  complete.",
    "tag" nil,
    "source"
    "(defn transaction*\n  \"Evaluates func as a transaction on the open database connection. Any\n  nested transactions are absorbed into the outermost transaction. By\n  default, all database updates are committed together as a group after\n  evaluating the outermost body, or rolled back on any uncaught\n  exception. If rollback is set within scope of the outermost transaction,\n  the entire transaction will be rolled back rather than committed when\n  complete.\"\n  [func]\n  (binding [*db* (update-in *db* [:level] inc)]\n    ;; This ugliness makes it easier to catch SQLException objects\n    ;; rather than something wrapped in a RuntimeException which\n    ;; can really obscure your code when working with JDBC from\n    ;; Clojure... :(\n    (letfn [(throw-non-rte [^Throwable ex]\n              (cond (instance? java.sql.SQLException ex) (throw ex)\n                    (and (instance? RuntimeException ex) (.getCause ex)) (throw-non-rte (.getCause ex))\n                    :else (throw ex)))]\n      (if (= (:level *db*) 1)\n        (let [^java.sql.Connection con (connection)\n              auto-commit (.getAutoCommit con)]\n          (io!\n           (.setAutoCommit con false)\n           (try\n             (let [result (func)]\n               (if (rollback)\n                 (.rollback con)\n                 (.commit con))\n               result)\n             (catch Exception e\n               (.rollback con)\n               (throw-non-rte e))\n             (finally\n              (rollback false)\n              (.setAutoCommit con auto-commit)))))\n        (try\n          (func)\n          (catch Exception e\n            (throw-non-rte e)))))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["func"]]}
   {"ns" "clojure.java.jdbc",
    "name" "resultset-seq",
    "line" 213,
    "column" 1,
    "doc"
    "Creates and returns a lazy sequence of maps corresponding to\n   the rows in the java.sql.ResultSet rs. Based on clojure.core/resultset-seq\n   but it respects the current naming strategy. Duplicate column names are\n   made unique by appending _N before applying the naming strategy (where\n   N is a unique integer).",
    "tag" nil,
    "source"
    "(defn resultset-seq\n  \"Creates and returns a lazy sequence of maps corresponding to\n   the rows in the java.sql.ResultSet rs. Based on clojure.core/resultset-seq\n   but it respects the current naming strategy. Duplicate column names are\n   made unique by appending _N before applying the naming strategy (where\n   N is a unique integer).\"\n  [^ResultSet rs]\n    (let [rsmeta (.getMetaData rs)\n          idxs (range 1 (inc (.getColumnCount rsmeta)))\n          keys (->> idxs\n                 (map (fn [^Integer i] (.getColumnLabel rsmeta i)))\n                 make-cols-unique\n                 (map (comp keyword *as-key*)))\n          row-values (fn [] (map (fn [^Integer i] (.getObject rs i)) idxs))\n          ;; This used to use create-struct (on keys) and then struct to populate each row.\n          ;; That had the side effect of preserving the order of columns in each row. As\n          ;; part of JDBC-15, this was changed because structmaps are deprecated. We don't\n          ;; want to switch to records so we're using regular maps instead. We no longer\n          ;; guarantee column order in rows but using into {} should preserve order for up\n          ;; to 16 columns (because it will use a PersistentArrayMap). If someone is relying\n          ;; on the order-preserving behavior of structmaps, we can reconsider...\n          rows (fn thisfn []\n                 (when (.next rs)\n                   (cons (zipmap keys (row-values)) (lazy-seq (thisfn)))))]\n      (rows)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["rs"]]}
   {"ns" "clojure.java.jdbc",
    "name" "do-commands",
    "line" 423,
    "column" 1,
    "doc" "Executes SQL commands on the open database connection.",
    "tag" nil,
    "source"
    "(defn do-commands\n  \"Executes SQL commands on the open database connection.\"\n  [& commands]\n  (with-open [^Statement stmt (let [^java.sql.Connection con (connection)] (.createStatement con))]\n    (doseq [^String cmd commands]\n      (.addBatch stmt cmd))\n    (transaction\n     (execute-batch stmt))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["&" "commands"]]}
   {"ns" "clojure.java.jdbc",
    "name" "do-prepared-return-keys",
    "line" 540,
    "column" 1,
    "doc"
    "Executes an (optionally parameterized) SQL prepared statement on the\n  open database connection. The param-group is a seq of values for all of\n  the parameters.\n  Return the generated keys for the (single) update/insert.",
    "tag" nil,
    "source"
    "(defn do-prepared-return-keys\n  \"Executes an (optionally parameterized) SQL prepared statement on the\n  open database connection. The param-group is a seq of values for all of\n  the parameters.\n  Return the generated keys for the (single) update/insert.\"\n  [sql param-group]\n  (with-open [^PreparedStatement stmt (prepare-statement (connection) sql :return-keys true)]\n    (set-parameters stmt param-group)\n    (transaction* (fn [] (let [counts (.executeUpdate stmt)]\n                          (try\n                            (let [rs (.getGeneratedKeys stmt)\n                                  result (first (resultset-seq rs))]\n                              ;; sqlite (and maybe others?) requires\n                              ;; record set to be closed\n                              (.close rs)\n                              result)\n                            (catch Exception _\n                              ;; assume generated keys is unsupported and return counts instead: \n                              counts)))))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["sql" "param-group"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "get-connection",
    "line" 150,
    "column" 1,
    "doc"
    "Creates a connection to a database. db-spec is a map containing connection\n   parameters - see with-connection for full details.",
    "tag" nil,
    "source"
    "(defn- get-connection\n  \"Creates a connection to a database. db-spec is a map containing connection\n   parameters - see with-connection for full details.\"\n  [{:keys [factory\n           connection-uri\n           classname subprotocol subname\n           datasource username password\n           name environment]\n    :as db-spec}]\n  (cond\n    (instance? URI db-spec)\n    (get-connection (parse-properties-uri db-spec))\n    \n    (string? db-spec)\n    (get-connection (URI. (strip-jdbc db-spec)))\n    \n    factory\n    (factory (dissoc db-spec :factory))\n    \n    connection-uri\n    (DriverManager/getConnection connection-uri)\n    \n    (and subprotocol subname)\n    (let [url (format \"jdbc:%s:%s\" subprotocol subname)\n          etc (dissoc db-spec :classname :subprotocol :subname)\n          classname (or classname (classnames subprotocol))]\n      (clojure.lang.RT/loadClassForName classname)\n      (DriverManager/getConnection url (as-properties etc)))\n    \n    (and datasource username password)\n    (.getConnection ^DataSource datasource ^String username ^String password)\n    \n    datasource\n    (.getConnection ^DataSource datasource)\n    \n    name\n    (let [env (and environment (Hashtable. ^Map environment))\n          context (InitialContext. env)\n          ^DataSource datasource (.lookup context ^String name)]\n      (.getConnection datasource))\n    \n    :else\n    (let [^String msg (format \"db-spec %s is missing a required parameter\" db-spec)]\n      (throw (IllegalArgumentException. msg)))))",
    "file" "clojure/java/jdbc.clj",
    "arglists"
    [[[["keys"
        ["factory"
         "connection-uri"
         "classname"
         "subprotocol"
         "subname"
         "datasource"
         "username"
         "password"
         "name"
         "environment"]]
       ["as" "db-spec"]]]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "execute-batch",
    "line" 411,
    "column" 1,
    "doc"
    "Executes a batch of SQL commands and returns a sequence of update counts.\n   (-2) indicates a single operation operating on an unknown number of rows.\n   Specifically, Oracle returns that and we must call getUpdateCount() to get\n   the actual number of rows affected. In general, operations return an array\n   of update counts, so this may not be a general solution for Oracle...",
    "tag" nil,
    "source"
    "(defn- execute-batch\n  \"Executes a batch of SQL commands and returns a sequence of update counts.\n   (-2) indicates a single operation operating on an unknown number of rows.\n   Specifically, Oracle returns that and we must call getUpdateCount() to get\n   the actual number of rows affected. In general, operations return an array\n   of update counts, so this may not be a general solution for Oracle...\"\n  [stmt]\n  (let [result (.executeBatch stmt)]\n    (if (and (= 1 (count result)) (= -2 (first result)))\n      (list (.getUpdateCount stmt))\n      (seq result))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["stmt"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "set-parameters",
    "line" 479,
    "column" 1,
    "doc" "Add the parameters to the given statement.",
    "tag" nil,
    "source"
    "(defn- set-parameters\n  \"Add the parameters to the given statement.\"\n  [^PreparedStatement stmt params]\n  (dorun\n    (map-indexed\n      (fn [ix value]\n        (.setObject stmt (inc ix) value))\n      params)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["stmt" "params"]]}
   {"ns" "clojure.java.jdbc",
    "name" "create-table",
    "line" 522,
    "column" 1,
    "doc"
    "Creates a table on the open database connection given a table name and\n  specs. Each spec is either a column spec: a vector containing a column\n  name and optionally a type and other constraints, or a table-level\n  constraint: a vector containing words that express the constraint. An\n  optional suffix to the CREATE TABLE DDL describing table attributes may\n  by provided as :table-spec {table-attributes-string}. All words used to\n  describe the table may be supplied as strings or keywords.",
    "tag" nil,
    "source"
    "(defn create-table\n  \"Creates a table on the open database connection given a table name and\n  specs. Each spec is either a column spec: a vector containing a column\n  name and optionally a type and other constraints, or a table-level\n  constraint: a vector containing words that express the constraint. An\n  optional suffix to the CREATE TABLE DDL describing table attributes may\n  by provided as :table-spec {table-attributes-string}. All words used to\n  describe the table may be supplied as strings or keywords.\"\n  [name & specs]\n  (do-commands (apply create-table-ddl name specs)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["name" "&" "specs"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "rollback",
    "line" 341,
    "column" 1,
    "doc" "Accessor for the rollback flag on the current connection",
    "tag" nil,
    "source"
    "(defn- rollback\n  \"Accessor for the rollback flag on the current connection\"\n  ([]\n    (deref (:rollback *db*)))\n  ([val]\n    (swap! (:rollback *db*) (fn [_] val))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [[] ["val"]]}
   {"ns" "clojure.java.jdbc",
    "name" "do-prepared",
    "line" 488,
    "column" 1,
    "doc"
    "Executes an (optionally parameterized) SQL prepared statement on the\n  open database connection. Each param-group is a seq of values for all of\n  the parameters.\n  Return a seq of update counts (one count for each param-group).",
    "tag" nil,
    "source"
    "(defn do-prepared\n  \"Executes an (optionally parameterized) SQL prepared statement on the\n  open database connection. Each param-group is a seq of values for all of\n  the parameters.\n  Return a seq of update counts (one count for each param-group).\"\n  [sql & param-groups]\n  (with-open [^PreparedStatement stmt (prepare-statement (connection) sql)]\n    (if (empty? param-groups)\n      (transaction* (fn [] (vector (.executeUpdate stmt))))\n      (do\n        (doseq [param-group param-groups]\n          (set-parameters stmt param-group)\n          (.addBatch stmt))\n        (transaction* (fn [] (execute-batch stmt)))))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["sql" "&" "param-groups"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "special-counts",
    "line" 709,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:private true} special-counts\n  {Statement/EXECUTE_FAILED \"EXECUTE_FAILED\"\n   Statement/SUCCESS_NO_INFO \"SUCCESS_NO_INFO\"})",
    "file" "clojure/java/jdbc.clj",
    "arglists" nil}
   {"ns" "clojure.java.jdbc",
    "name" "insert-record",
    "line" 596,
    "column" 1,
    "doc"
    "Inserts a single record into a table. A record is a map from strings or\n  keywords (identifying columns) to values.\n  Returns a map of the generated keys.",
    "tag" nil,
    "source"
    "(defn insert-record\n  \"Inserts a single record into a table. A record is a map from strings or\n  keywords (identifying columns) to values.\n  Returns a map of the generated keys.\"\n  [table record]\n  (let [keys (insert-records table record)]\n    (first keys)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "record"]]}
   {"ns" "clojure.java.jdbc",
    "name" "with-quoted-identifiers",
    "macro" true,
    "line" 291,
    "column" 1,
    "doc"
    "Evaluates body in the context of a simple quoting naming strategy.",
    "tag" nil,
    "source"
    "(defmacro with-quoted-identifiers\n  \"Evaluates body in the context of a simple quoting naming strategy.\"\n  [q & body ]\n  `(binding [*as-str* (partial as-quoted-str ~q)] ~@body))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["q" "&" "body"]]}
   {"ns" "clojure.java.jdbc",
    "name" "insert-rows",
    "line" 581,
    "column" 1,
    "doc"
    "Inserts complete rows into a table. Each row is a vector of values for\n  each of the table's columns in order.\n  If a single row is inserted, returns a map of the generated keys.",
    "tag" nil,
    "source"
    "(defn insert-rows\n  \"Inserts complete rows into a table. Each row is a vector of values for\n  each of the table's columns in order.\n  If a single row is inserted, returns a map of the generated keys.\"\n  [table & rows]\n  (apply insert-values table nil rows))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "&" "rows"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "*as-str*",
    "line" 47,
    "column" 1,
    "doc" "The default entity naming strategy is to do nothing.",
    "tag" nil,
    "source"
    "(def ^{:private true :dynamic true\n       :doc \"The default entity naming strategy is to do nothing.\"}\n  *as-str* \n  identity)",
    "file" "clojure/java/jdbc.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "*db*",
    "line" 106,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:private true :dynamic true} *db* {:connection nil :level 0})",
    "file" "clojure/java/jdbc.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.java.jdbc",
    "name" "delete-rows",
    "line" 604,
    "column" 1,
    "doc"
    "Deletes rows from a table. where-params is a vector containing a string\n  providing the (optionally parameterized) selection criteria followed by\n  values for any parameters.",
    "tag" nil,
    "source"
    "(defn delete-rows\n  \"Deletes rows from a table. where-params is a vector containing a string\n  providing the (optionally parameterized) selection criteria followed by\n  values for any parameters.\"\n  [table where-params]\n  (let [[where & params] where-params]\n    (do-prepared\n      (format \"DELETE FROM %s WHERE %s\"\n              (as-identifier table) where)\n      params)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "where-params"]]}
   {"ns" "clojure.java.jdbc",
    "name" "update-or-insert-values",
    "line" 629,
    "column" 1,
    "doc"
    "Updates values on selected rows in a table, or inserts a new row when no\n  existing row matches the selection criteria. where-params is a vector\n  containing a string providing the (optionally parameterized) selection\n  criteria followed by values for any parameters. record is a map from\n  strings or keywords (identifying columns) to updated values.",
    "tag" nil,
    "source"
    "(defn update-or-insert-values\n  \"Updates values on selected rows in a table, or inserts a new row when no\n  existing row matches the selection criteria. where-params is a vector\n  containing a string providing the (optionally parameterized) selection\n  criteria followed by values for any parameters. record is a map from\n  strings or keywords (identifying columns) to updated values.\"\n  [table where-params record]\n  (transaction\n   (let [result (update-values table where-params record)]\n     (if (zero? (first result))\n       (insert-values table (keys record) (vals record))\n       result))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["table" "where-params" "record"]]}
   {"private" true,
    "ns" "clojure.java.jdbc",
    "name" "classnames",
    "line" 108,
    "column" 1,
    "doc" "Map of classnames to subprotocols",
    "tag" nil,
    "source"
    "(def ^{:private true :doc \"Map of classnames to subprotocols\"} classnames\n  {\"postgresql\"     \"org.postgresql.Driver\"\n   \"mysql\"          \"com.mysql.jdbc.Driver\"\n   \"sqlserver\"      \"com.microsoft.sqlserver.jdbc.SQLServerDriver\"\n   \"jtds:sqlserver\" \"net.sourceforge.jtds.jdbc.Driver\"\n   \"derby\"          \"org.apache.derby.jdbc.EmbeddedDriver\"\n   \"hsqldb\"         \"org.hsqldb.jdbcDriver\"\n   \"sqlite\"         \"org.sqlite.JDBC\"})",
    "file" "clojure/java/jdbc.clj",
    "arglists" nil}
   {"ns" "clojure.java.jdbc",
    "name" "as-quoted-identifier",
    "line" 271,
    "column" 1,
    "doc"
    "Given a quote pattern - either a single character or a pair of characters in\n   a vector - and a keyword, return the keyword as a string using a simple\n   quoting naming strategy.\n   Given a qote pattern and a string, return the string as-is.\n     (as-quoted-identifier X :name) will return XnameX as a string.\n     (as-quoted-identifier [A B] :name) will return AnameB as a string.",
    "tag" nil,
    "source"
    "(defn as-quoted-identifier\n  \"Given a quote pattern - either a single character or a pair of characters in\n   a vector - and a keyword, return the keyword as a string using a simple\n   quoting naming strategy.\n   Given a qote pattern and a string, return the string as-is.\n     (as-quoted-identifier X :name) will return XnameX as a string.\n     (as-quoted-identifier [A B] :name) will return AnameB as a string.\"\n  [q x]\n  (as-identifier x (partial as-quoted-str q)))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["q" "x"]]}
   {"ns" "clojure.java.jdbc",
    "name" "with-connection*",
    "line" 296,
    "column" 1,
    "doc"
    "Evaluates func in the context of a new connection to a database then\n  closes the connection.",
    "tag" nil,
    "source"
    "(defn with-connection*\n  \"Evaluates func in the context of a new connection to a database then\n  closes the connection.\"\n  [db-spec func]\n  (with-open [^java.sql.Connection con (get-connection db-spec)]\n    (binding [*db* (assoc *db* :connection con :level 0 :rollback (atom false))]\n      (func))))",
    "file" "clojure/java/jdbc.clj",
    "arglists" [["db-spec" "func"]]}]},
 "description" "java.jdbc 0.2.3",
 "version" "0.2.3",
 "name" "clojure.java.jdbc",
 "group" "clojure.java.jdbc"}
