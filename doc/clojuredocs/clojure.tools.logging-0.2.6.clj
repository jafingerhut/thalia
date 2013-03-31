{"namespaces"
 {"clojure.tools.logging.impl"
  [{"ns" "clojure.tools.logging.impl",
    "name" "find-factory",
    "line" 202,
    "column" 1,
    "doc"
    "Returns the first non-nil value from slf4j-factory, cl-factory,\n   log4j-factory, and jul-factory.",
    "tag" nil,
    "source"
    "(defn find-factory\n  \"Returns the first non-nil value from slf4j-factory, cl-factory,\n   log4j-factory, and jul-factory.\"\n  []\n  (or (slf4j-factory)\n      (cl-factory)\n      (log4j-factory)\n      (jul-factory)\n      (throw ; this should never happen in 1.5+\n        (RuntimeException.\n          \"Valid logging implementation could not be found.\"))))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" [[]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.logging.impl",
    "doc" "Writes a log message to the given Logger.",
    "arglists" [["logger" "level" "throwable" "message"]],
    "name" "write!"}
   {"ns" "clojure.tools.logging.impl",
    "name" "cl-factory",
    "line" 82,
    "column" 1,
    "doc"
    "Returns a Commons Logging-based implementation of the LoggerFactory protocol, or\n  nil if not available.",
    "tag" nil,
    "source"
    "(defn cl-factory\n  \"Returns a Commons Logging-based implementation of the LoggerFactory protocol, or\n  nil if not available.\"\n  []\n  (try\n    (Class/forName \"org.apache.commons.logging.Log\")\n    (eval\n      `(do\n         (extend org.apache.commons.logging.Log\n           Logger\n           {:enabled?\n            (fn [^org.apache.commons.logging.Log logger# level#]\n              (condp = level#\n                :trace (.isTraceEnabled logger#)\n                :debug (.isDebugEnabled logger#)\n                :info  (.isInfoEnabled  logger#)\n                :warn  (.isWarnEnabled  logger#)\n                :error (.isErrorEnabled logger#)\n                :fatal (.isFatalEnabled logger#)\n                (throw (IllegalArgumentException. (str level#)))))\n            :write!\n            (fn [^org.apache.commons.logging.Log logger# level# e# msg#]\n              (if e#\n                (condp = level#\n                  :trace (.trace logger# msg# e#)\n                  :debug (.debug logger# msg# e#)\n                  :info  (.info  logger# msg# e#)\n                  :warn  (.warn  logger# msg# e#)\n                  :error (.error logger# msg# e#)\n                  :fatal (.fatal logger# msg# e#)\n                  (throw (IllegalArgumentException. (str level#))))\n                (condp = level#\n                  :trace (.trace logger# msg#)\n                  :debug (.debug logger# msg#)\n                  :info  (.info  logger# msg#)\n                  :warn  (.warn  logger# msg#)\n                  :error (.error logger# msg#)\n                  :fatal (.fatal logger# msg#)\n                  (throw (IllegalArgumentException. (str level#))))))})\n         (reify LoggerFactory\n           (name [_#]\n             \"org.apache.commons.logging\")\n           (get-logger [_# logger-ns#]\n             (org.apache.commons.logging.LogFactory/getLog (str logger-ns#))))))\n    (catch Exception e nil)))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" [[]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.logging.impl",
    "doc"
    "Returns some text identifying the underlying implementation.",
    "arglists" [["factory"]],
    "name" "name"}
   {"ns" "clojure.tools.logging.impl",
    "name" "slf4j-factory",
    "line" 35,
    "column" 1,
    "doc"
    "Returns a SLF4J-based implementation of the LoggerFactory protocol, or nil if\n  not available.",
    "tag" nil,
    "source"
    "(defn slf4j-factory\n  \"Returns a SLF4J-based implementation of the LoggerFactory protocol, or nil if\n  not available.\"\n  []\n  (try\n    (Class/forName \"org.slf4j.Logger\")\n    (eval\n      `(do\n        (extend org.slf4j.Logger\n          Logger\n          {:enabled?\n           (fn [^org.slf4j.Logger logger# level#]\n             (condp = level#\n               :trace (.isTraceEnabled logger#)\n               :debug (.isDebugEnabled logger#)\n               :info  (.isInfoEnabled  logger#)\n               :warn  (.isWarnEnabled  logger#)\n               :error (.isErrorEnabled logger#)\n               :fatal (.isErrorEnabled logger#)\n               (throw (IllegalArgumentException. (str level#)))))\n           :write!\n           (fn [^org.slf4j.Logger logger# level# ^Throwable e# msg#]\n             (let [^String msg# (str msg#)]\n               (if e#\n                 (condp = level#\n                   :trace (.trace logger# msg# e#)\n                   :debug (.debug logger# msg# e#)\n                   :info  (.info  logger# msg# e#)\n                   :warn  (.warn  logger# msg# e#)\n                   :error (.error logger# msg# e#)\n                   :fatal (.error logger# msg# e#)\n                   (throw (IllegalArgumentException. (str level#))))\n                 (condp = level#\n                   :trace (.trace logger# msg#)\n                   :debug (.debug logger# msg#)\n                   :info  (.info  logger# msg#)\n                   :warn  (.warn  logger# msg#)\n                   :error (.error logger# msg#)\n                   :fatal (.error logger# msg#)\n                   (throw (IllegalArgumentException. (str level#)))))))})\n        (reify LoggerFactory\n          (name [_#]\n            \"org.slf4j\")\n          (get-logger [_# logger-ns#]\n            (org.slf4j.LoggerFactory/getLogger ^String (str logger-ns#))))))\n    (catch Exception e nil)))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.logging.impl",
    "name" "log4j-factory",
    "line" 128,
    "column" 1,
    "doc"
    "Returns a Log4j-based implementation of the LoggerFactory protocol, or nil if\n  not available.",
    "tag" nil,
    "source"
    "(defn log4j-factory\n  \"Returns a Log4j-based implementation of the LoggerFactory protocol, or nil if\n  not available.\"\n  []\n  (try\n    (Class/forName \"org.apache.log4j.Logger\")\n    (eval\n      `(let [levels# {:trace org.apache.log4j.Level/TRACE\n                      :debug org.apache.log4j.Level/DEBUG\n                      :info  org.apache.log4j.Level/INFO\n                      :warn  org.apache.log4j.Level/WARN\n                      :error org.apache.log4j.Level/ERROR\n                      :fatal org.apache.log4j.Level/FATAL}]\n         (extend org.apache.log4j.Logger\n           Logger\n           {:enabled?\n            (fn [^org.apache.log4j.Logger logger# level#]\n              (.isEnabledFor logger#\n                 (or\n                   (levels# level#)\n                   (throw (IllegalArgumentException. (str level#))))))\n            :write!\n            (fn [^org.apache.log4j.Logger logger# level# e# msg#]\n              (let [level# (or\n                             (levels# level#)\n                             (throw (IllegalArgumentException. (str level#))))]\n                (if e#\n                  (.log logger# level# msg# e#)\n                  (.log logger# level# msg#))))})\n         (reify LoggerFactory\n           (name [_#]\n             \"org.apache.log4j\")\n           (get-logger [_# logger-ns#]\n             (org.apache.log4j.Logger/getLogger ^String (str logger-ns#))))))\n    (catch Exception e nil)))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" [[]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.logging.impl",
    "doc" "Returns an implementation-specific Logger by namespace.",
    "arglists" [["factory" "logger-ns"]],
    "name" "get-logger"}
   {"ns" "clojure.tools.logging.impl",
    "name" "Logger",
    "line" 16,
    "column" 1,
    "doc"
    "The protocol through which the core api will interact with an underlying logging\n  implementation.  Implementations should at least support the six standard\n  logging levels if they wish to work from the level-specific macros.",
    "tag" nil,
    "source"
    "(defprotocol Logger\n  \"The protocol through which the core api will interact with an underlying logging\n  implementation.  Implementations should at least support the six standard\n  logging levels if they wish to work from the level-specific macros.\"\n  (enabled? [logger level]\n    \"Check if a particular level is enabled for the given Logger.\")\n  (write! [logger level throwable message]\n    \"Writes a log message to the given Logger.\"))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" nil}
   {"ns" "clojure.tools.logging.impl",
    "name" "jul-factory",
    "line" 164,
    "column" 1,
    "doc"
    "Returns a java.util.logging-based implementation of the LoggerFactory protocol,\n  or nil if not available.",
    "tag" nil,
    "source"
    "(defn jul-factory\n  \"Returns a java.util.logging-based implementation of the LoggerFactory protocol,\n  or nil if not available.\"\n  []\n  (try\n    (Class/forName \"java.util.logging.Logger\")\n    (eval\n      `(let [levels# {:trace java.util.logging.Level/FINEST\n                      :debug java.util.logging.Level/FINE\n                      :info  java.util.logging.Level/INFO\n                      :warn  java.util.logging.Level/WARNING\n                      :error java.util.logging.Level/SEVERE\n                      :fatal java.util.logging.Level/SEVERE}]\n         (extend java.util.logging.Logger\n           Logger\n           {:enabled?\n            (fn [^java.util.logging.Logger logger# level#]\n              (.isLoggable logger#\n                (or\n                  (levels# level#)\n                  (throw (IllegalArgumentException. (str level#))))))\n            :write!\n            (fn [^java.util.logging.Logger logger# level# ^Throwable e# msg#]\n              (let [^java.util.logging.Level level#\n                    (or\n                      (levels# level#)\n                      (throw (IllegalArgumentException. (str level#))))\n                    ^String msg# (str msg#)]\n                (if e#\n                  (.log logger# level# msg# e#)\n                  (.log logger# level# msg#))))})\n         (reify LoggerFactory\n           (name [_#]\n             \"java.util.logging\")\n           (get-logger [_# logger-ns#]\n             (java.util.logging.Logger/getLogger (str logger-ns#))))))\n    (catch Exception e nil)))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.logging.impl",
    "name" "LoggerFactory",
    "line" 25,
    "column" 1,
    "doc"
    "The protocol through which the core api will obtain an instance satisfying Logger\n  as well as providing information about the particular implementation being used.\n  Implementations should be bound to *logger-factory* in order to be picked up by\n  this library.",
    "tag" nil,
    "source"
    "(defprotocol LoggerFactory\n  \"The protocol through which the core api will obtain an instance satisfying Logger\n  as well as providing information about the particular implementation being used.\n  Implementations should be bound to *logger-factory* in order to be picked up by\n  this library.\"\n  (name [factory]\n    \"Returns some text identifying the underlying implementation.\")\n  (get-logger [factory logger-ns]\n    \"Returns an implementation-specific Logger by namespace.\"))",
    "file" "clojure/tools/logging/impl.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.logging.impl",
    "doc"
    "Check if a particular level is enabled for the given Logger.",
    "arglists" [["logger" "level"]],
    "name" "enabled?"}],
  "clojure.tools.logging"
  [{"ns" "clojure.tools.logging",
    "name" "warnf",
    "macro" true,
    "line" 254,
    "column" 1,
    "doc" "Warn level logging using format.",
    "tag" nil,
    "source"
    "(defmacro warnf\n  \"Warn level logging using format.\"\n  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}\n  [& args]\n  `(logf :warn ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["fmt" "&" "fmt-args"] ["throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "*tx-agent-levels*",
    "line" 28,
    "column" 1,
    "doc"
    "The set of levels that will require using an agent when logging from within a\n  running transaction. Defaults to #{:info :warn}. See log* for details.",
    "tag" nil,
    "source"
    "(def ^{:doc\n  \"The set of levels that will require using an agent when logging from within a\n  running transaction. Defaults to #{:info :warn}. See log* for details.\" :dynamic true}\n  *tx-agent-levels* #{:info :warn})",
    "file" "clojure/tools/logging.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.tools.logging",
    "name" "*force*",
    "line" 33,
    "column" 1,
    "doc"
    "Overrides the default rules for choosing between logging directly or via an\n  agent. Defaults to nil. See log* for details.",
    "tag" nil,
    "source"
    "(def ^{:doc\n  \"Overrides the default rules for choosing between logging directly or via an\n  agent. Defaults to nil. See log* for details.\" :dynamic true}\n  *force* nil)",
    "file" "clojure/tools/logging.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.tools.logging",
    "name" "logf",
    "macro" true,
    "line" 91,
    "column" 1,
    "doc"
    "Logs a message using a format string and args. Can optionally take a\n  throwable as its second arg. See level-specific macros, e.g., debugf.",
    "tag" nil,
    "source"
    "(defmacro logf\n  \"Logs a message using a format string and args. Can optionally take a\n  throwable as its second arg. See level-specific macros, e.g., debugf.\"\n  {:arglists '([level fmt & fmt-args] [level throwable fmt & fmt-args])}\n  [level x & more]\n  (if (or (instance? String x) (nil? more)) ; optimize for common case\n    `(log ~level (format ~x ~@more))\n    `(let [logger# (impl/get-logger *logger-factory* ~*ns*)]\n       (if (impl/enabled? logger# ~level)\n         (let [x# ~x]\n           (if (instance? Throwable x#) ; type check only when enabled\n             (log* logger# ~level x# (format ~@more))\n             (log* logger# ~level nil (format x# ~@more))))))))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["level" "fmt" "&" "fmt-args"]
     ["level" "throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "debugf",
    "macro" true,
    "line" 242,
    "column" 1,
    "doc" "Debug level logging using format.",
    "tag" nil,
    "source"
    "(defmacro debugf\n  \"Debug level logging using format.\"\n  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}\n  [& args]\n  `(logf :debug ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["fmt" "&" "fmt-args"] ["throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "tracef",
    "macro" true,
    "line" 236,
    "column" 1,
    "doc" "Trace level logging using format.",
    "tag" nil,
    "source"
    "(defmacro tracef\n  \"Trace level logging using format.\"\n  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}\n  [& args]\n  `(logf :trace ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["fmt" "&" "fmt-args"] ["throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "logp",
    "macro" true,
    "line" 77,
    "column" 1,
    "doc"
    "Logs a message using print style args. Can optionally take a throwable as its\n  second arg. See level-specific macros, e.g., debug.",
    "tag" nil,
    "source"
    "(defmacro logp\n  \"Logs a message using print style args. Can optionally take a throwable as its\n  second arg. See level-specific macros, e.g., debug.\"\n  {:arglists '([level message & more] [level throwable message & more])}\n  [level x & more]\n  (if (or (instance? String x) (nil? more)) ; optimize for common case\n    `(log ~level (print-str ~x ~@more))\n    `(let [logger# (impl/get-logger *logger-factory* ~*ns*)]\n       (if (impl/enabled? logger# ~level)\n         (let [x# ~x]\n           (if (instance? Throwable x#) ; type check only when enabled\n             (log* logger# ~level x# (print-str ~@more))\n             (log* logger# ~level nil (print-str x# ~@more))))))))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["level" "message" "&" "more"]
     ["level" "throwable" "message" "&" "more"]]}
   {"arglists" nil,
    "ns" "clojure.tools.logging",
    "name"
    "clojure.tools.logging.proxy$java.io.ByteArrayOutputStream$0",
    "source" nil,
    "tag" nil}
   {"ns" "clojure.tools.logging",
    "name" "log-stream",
    "line" 130,
    "column" 1,
    "doc"
    "Creates a PrintStream that will output to the log at the specified level.",
    "tag" nil,
    "source"
    "(defn log-stream\n  \"Creates a PrintStream that will output to the log at the specified level.\"\n  [level logger-ns]\n  (let [logger (impl/get-logger *logger-factory* logger-ns)]\n    (java.io.PrintStream.\n      (proxy [java.io.ByteArrayOutputStream] []\n        (flush []\n          ; deal with reflection in proxy-super\n          (let [^java.io.ByteArrayOutputStream this this]\n            (proxy-super flush)\n            (let [message (.trim (.toString this))]\n              (proxy-super reset)\n              (if (> (.length message) 0)\n                (log* logger level nil message))))))\n      true)))",
    "file" "clojure/tools/logging.clj",
    "arglists" [["level" "logger-ns"]]}
   {"ns" "clojure.tools.logging",
    "name" "info",
    "macro" true,
    "line" 212,
    "column" 1,
    "doc" "Info level logging using print-style args.",
    "tag" nil,
    "source"
    "(defmacro info\n  \"Info level logging using print-style args.\"\n  {:arglists '([message & more] [throwable message & more])}\n  [& args]\n  `(logp :info ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["message" "&" "more"] ["throwable" "message" "&" "more"]]}
   {"ns" "clojure.tools.logging",
    "name" "log-uncapture!",
    "line" 168,
    "column" 3,
    "doc"
    "Restores System.out and System.err to their original values.",
    "tag" nil,
    "source"
    "  (defn log-uncapture!\n    \"Restores System.out and System.err to their original values.\"\n    []\n    (locking monitor\n      (when-let [[out err :as v] @orig]\n        (swap! orig (constantly nil))\n        (System/setOut out)\n        (System/setErr err))))",
    "file" "clojure/tools/logging.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.logging",
    "name" "log",
    "macro" true,
    "line" 63,
    "column" 1,
    "doc"
    "Evaluates and logs a message only if the specified level is enabled. See log*\n  for more details.",
    "tag" nil,
    "source"
    "(defmacro log\n  \"Evaluates and logs a message only if the specified level is enabled. See log*\n  for more details.\"\n  ([level message]\n    `(log ~level nil ~message))\n  ([level throwable message]\n    `(log ~*ns* ~level ~throwable ~message))\n  ([logger-ns level throwable message]\n    `(log *logger-factory* ~logger-ns ~level ~throwable ~message))\n  ([logger-factory logger-ns level throwable message]\n    `(let [logger# (impl/get-logger ~logger-factory ~logger-ns)]\n       (if (impl/enabled? logger# ~level)\n         (log* logger# ~level ~throwable ~message)))))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["level" "message"]
     ["level" "throwable" "message"]
     ["logger-ns" "level" "throwable" "message"]
     ["logger-factory" "logger-ns" "level" "throwable" "message"]]}
   {"ns" "clojure.tools.logging",
    "name" "errorf",
    "macro" true,
    "line" 260,
    "column" 1,
    "doc" "Error level logging using format.",
    "tag" nil,
    "source"
    "(defmacro errorf\n  \"Error level logging using format.\"\n  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}\n  [& args]\n  `(logf :error ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["fmt" "&" "fmt-args"] ["throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "*logger-factory*",
    "line" 272,
    "column" 1,
    "doc"
    "An instance satisfying the impl/LoggerFactory protocol. Used internally to\n   obtain an impl/Logger. Defaults to the value returned from impl/find-factory.",
    "tag" nil,
    "source"
    "(def ^{:doc\n  \"An instance satisfying the impl/LoggerFactory protocol. Used internally to\n   obtain an impl/Logger. Defaults to the value returned from impl/find-factory.\"\n  :dynamic true}\n  *logger-factory*\n  (impl/find-factory))",
    "file" "clojure/tools/logging.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.tools.logging",
    "name" "infof",
    "macro" true,
    "line" 248,
    "column" 1,
    "doc" "Info level logging using format.",
    "tag" nil,
    "source"
    "(defmacro infof\n  \"Info level logging using format.\"\n  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}\n  [& args]\n  `(logf :info ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["fmt" "&" "fmt-args"] ["throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "trace",
    "macro" true,
    "line" 200,
    "column" 1,
    "doc" "Trace level logging using print-style args.",
    "tag" nil,
    "source"
    "(defmacro trace\n  \"Trace level logging using print-style args.\"\n  {:arglists '([message & more] [throwable message & more])}\n  [& args]\n  `(logp :trace ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["message" "&" "more"] ["throwable" "message" "&" "more"]]}
   {"ns" "clojure.tools.logging",
    "name" "error",
    "macro" true,
    "line" 224,
    "column" 1,
    "doc" "Error level logging using print-style args.",
    "tag" nil,
    "source"
    "(defmacro error\n  \"Error level logging using print-style args.\"\n  {:arglists '([message & more] [throwable message & more])}\n  [& args]\n  `(logp :error ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["message" "&" "more"] ["throwable" "message" "&" "more"]]}
   {"ns" "clojure.tools.logging",
    "name" "fatalf",
    "macro" true,
    "line" 266,
    "column" 1,
    "doc" "Fatal level logging using format.",
    "tag" nil,
    "source"
    "(defmacro fatalf\n  \"Fatal level logging using format.\"\n  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}\n  [& args]\n  `(logf :fatal ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["fmt" "&" "fmt-args"] ["throwable" "fmt" "&" "fmt-args"]]}
   {"ns" "clojure.tools.logging",
    "name" "spy",
    "macro" true,
    "line" 114,
    "column" 1,
    "doc"
    "Evaluates expr and may write the form and its result to the log. Returns the\n  result of expr. Defaults to :debug log level.",
    "tag" nil,
    "source"
    "(defmacro spy\n  \"Evaluates expr and may write the form and its result to the log. Returns the\n  result of expr. Defaults to :debug log level.\"\n  ([expr]\n    `(spy :debug ~expr))\n  ([level expr]\n    `(let [a# ~expr]\n       (log ~level\n         (let [s# (with-out-str\n                    (with-pprint-dispatch code-dispatch ; need a better way\n                      (pprint '~expr)\n                      (print \"=> \")\n                      (pprint a#)))]\n           (trim-newline s#)))\n       a#)))",
    "file" "clojure/tools/logging.clj",
    "arglists" [["expr"] ["level" "expr"]]}
   {"ns" "clojure.tools.logging",
    "name" "log*",
    "line" 38,
    "column" 1,
    "doc"
    "Attempts to log a message, either directly or via an agent; does not check if\n  the level is enabled.\n\n  For performance reasons, an agent will only be used when invoked within a\n  running transaction, and only for logging levels specified by\n  *tx-agent-levels*. This allows those entries to only be written once the\n  transaction commits, and are discarded if it is retried or aborted.  As\n  corollary, other levels (e.g., :debug, :error) will be written even from\n  failed transactions though at the cost of repeat messages during retries.\n\n  One can override the above by setting *force* to :direct or :agent; all\n  subsequent writes will be direct or via an agent, respectively.",
    "tag" nil,
    "source"
    "(defn log*\n  \"Attempts to log a message, either directly or via an agent; does not check if\n  the level is enabled.\n\n  For performance reasons, an agent will only be used when invoked within a\n  running transaction, and only for logging levels specified by\n  *tx-agent-levels*. This allows those entries to only be written once the\n  transaction commits, and are discarded if it is retried or aborted.  As\n  corollary, other levels (e.g., :debug, :error) will be written even from\n  failed transactions though at the cost of repeat messages during retries.\n\n  One can override the above by setting *force* to :direct or :agent; all\n  subsequent writes will be direct or via an agent, respectively.\"\n  [logger level throwable message]\n  (if (cond\n        (nil? *force*) (and (clojure.lang.LockingTransaction/isRunning)\n                            (*tx-agent-levels* level))\n        (= *force* :agent)  true\n        (= *force* :direct) false)\n    (send-off *logging-agent*\n      (fn [_#] (impl/write! logger level throwable message)))\n    (impl/write! logger level throwable message)))",
    "file" "clojure/tools/logging.clj",
    "arglists" [["logger" "level" "throwable" "message"]]}
   {"ns" "clojure.tools.logging",
    "name" "with-logs",
    "macro" true,
    "line" 177,
    "column" 1,
    "doc"
    "Evaluates exprs in a context in which *out* and *err* write to the log. The\n  specified logger-ns value will be used to namespace all log entries.\n\n  By default *out* and *err* write to :info and :error, respectively.",
    "tag" nil,
    "source"
    "(defmacro with-logs\n  \"Evaluates exprs in a context in which *out* and *err* write to the log. The\n  specified logger-ns value will be used to namespace all log entries.\n\n  By default *out* and *err* write to :info and :error, respectively.\"\n  {:arglists '([logger-ns & body]\n               [[logger-ns out-level err-level] & body])}\n  [arg & body]\n  ; Implementation Notes:\n  ; - no enabled? check before making writers since that may change later\n  (let [[logger-ns out-level err-level] (if (vector? arg)\n                                       arg\n                                       [arg :info :error])]\n    (if (and logger-ns (seq body))\n      `(binding [*out* (java.io.OutputStreamWriter.\n                         (log-stream ~out-level ~logger-ns))\n                 *err* (java.io.OutputStreamWriter.\n                         (log-stream ~err-level ~logger-ns))]\n         ~@body))))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["logger-ns" "&" "body"]
     [["logger-ns" "out-level" "err-level"] "&" "body"]]}
   {"ns" "clojure.tools.logging",
    "name" "warn",
    "macro" true,
    "line" 218,
    "column" 1,
    "doc" "Warn level logging using print-style args.",
    "tag" nil,
    "source"
    "(defmacro warn\n  \"Warn level logging using print-style args.\"\n  {:arglists '([message & more] [throwable message & more])}\n  [& args]\n  `(logp :warn ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["message" "&" "more"] ["throwable" "message" "&" "more"]]}
   {"ns" "clojure.tools.logging",
    "name" "*logging-agent*",
    "line" 23,
    "column" 1,
    "doc"
    "The default agent used for performing logging when direct logging is\n  disabled. See log* for details.",
    "tag" nil,
    "source"
    "(def ^{:doc\n  \"The default agent used for performing logging when direct logging is\n  disabled. See log* for details.\" :dynamic true}\n  *logging-agent* (agent nil :error-mode :continue))",
    "file" "clojure/tools/logging.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.tools.logging",
    "name" "log-capture!",
    "line" 148,
    "column" 3,
    "doc"
    "Captures System.out and System.err, piping all writes of those streams to\n    the log. If unspecified, levels default to :info and :error, respectively.\n    The specified logger-ns value will be used to namespace all log entries.\n\n    Note: use with-logs to redirect output of *out* or *err*.\n\n    Warning: if the logging implementation is configured to output to System.out\n    (as is the default with java.util.logging) then using this function will\n    result in StackOverflowException when writing to the log.",
    "tag" nil,
    "source"
    "  (defn log-capture!\n    \"Captures System.out and System.err, piping all writes of those streams to\n    the log. If unspecified, levels default to :info and :error, respectively.\n    The specified logger-ns value will be used to namespace all log entries.\n\n    Note: use with-logs to redirect output of *out* or *err*.\n\n    Warning: if the logging implementation is configured to output to System.out\n    (as is the default with java.util.logging) then using this function will\n    result in StackOverflowException when writing to the log.\"\n    ; Implementation Notes:\n    ; - only set orig when nil to preserve original out/err\n    ; - no enabled? check before making streams since that may change later\n    ([logger-ns]\n      (log-capture! logger-ns :info :error))\n    ([logger-ns out-level err-level]\n      (locking monitor\n        (compare-and-set! orig nil [System/out System/err])\n        (System/setOut  (log-stream out-level logger-ns))\n        (System/setErr (log-stream err-level logger-ns)))))",
    "file" "clojure/tools/logging.clj",
    "arglists" [["logger-ns"] ["logger-ns" "out-level" "err-level"]]}
   {"ns" "clojure.tools.logging",
    "name" "enabled?",
    "macro" true,
    "line" 105,
    "column" 1,
    "doc"
    "Returns true if the specific logging level is enabled.  Use of this macro\n  should only be necessary if one needs to execute alternate code paths beyond\n  whether the log should be written to.",
    "tag" nil,
    "source"
    "(defmacro enabled?\n  \"Returns true if the specific logging level is enabled.  Use of this macro\n  should only be necessary if one needs to execute alternate code paths beyond\n  whether the log should be written to.\"\n  ([level]\n    `(enabled? ~level ~*ns*))\n  ([level logger-ns]\n    `(impl/enabled? (impl/get-logger *logger-factory* ~logger-ns) ~level)))",
    "file" "clojure/tools/logging.clj",
    "arglists" [["level"] ["level" "logger-ns"]]}
   {"ns" "clojure.tools.logging",
    "name" "debug",
    "macro" true,
    "line" 206,
    "column" 1,
    "doc" "Debug level logging using print-style args.",
    "tag" nil,
    "source"
    "(defmacro debug\n  \"Debug level logging using print-style args.\"\n  {:arglists '([message & more] [throwable message & more])}\n  [& args]\n  `(logp :debug ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["message" "&" "more"] ["throwable" "message" "&" "more"]]}
   {"ns" "clojure.tools.logging",
    "name" "fatal",
    "macro" true,
    "line" 230,
    "column" 1,
    "doc" "Fatal level logging using print-style args.",
    "tag" nil,
    "source"
    "(defmacro fatal\n  \"Fatal level logging using print-style args.\"\n  {:arglists '([message & more] [throwable message & more])}\n  [& args]\n  `(logp :fatal ~@args))",
    "file" "clojure/tools/logging.clj",
    "arglists"
    [["message" "&" "more"] ["throwable" "message" "&" "more"]]}]},
 "description" "tools.logging 0.2.6",
 "version" "0.2.6",
 "name" "clojure.tools.logging",
 "group" "clojure.tools.logging"}
