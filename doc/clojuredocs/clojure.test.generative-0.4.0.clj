{"namespaces"
 {"clojure.test.generative.runner"
  [{"arglists" [["&" "dirs"]],
    "ns" "clojure.test.generative.runner",
    "name" "find-vars-in-dirs",
    "column" 1,
    "line" 205,
    "source"
    "(defn find-vars-in-dirs\n  [& dirs]\n  (let [nses (mapcat #(ns/find-namespaces-in-dir (java.io.File. ^String %)) dirs)]\n    (doseq [ns nses] (require ns))\n    (apply find-vars-in-namespaces nses)))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.test.generative.runner",
    "doc"
    "Returns a collection of generative tests, where a test is a map with\n      :name     ns-qualified symbol\n      :fn       fn to test\n      :inputs   fn returning a (possibly infinite!) sequence of inputs\n\n   All input generation should use and gen/*rnd*\n   if a source of pseudo-randomness is needed.",
    "arglists" [["_"]],
    "name" "tests"}
   {"ns" "clojure.test.generative.runner",
    "name" "ctevent->event",
    "line" 32,
    "column" 1,
    "doc" "Convert a clojure.test reporting event to an event.",
    "tag" nil,
    "source"
    "(defmulti ctevent->event\n  \"Convert a clojure.test reporting event to an event.\"\n  :type)",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "*failed*",
    "line" 24,
    "column" 1,
    "tag" nil,
    "source" "(def ^:dynamic *failed* nil)",
    "file" "clojure/test/generative/runner.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["result"]],
    "ns" "clojure.test.generative.runner",
    "name" "failed?",
    "column" 1,
    "line" 262,
    "source"
    "(defn failed?\n  [result]\n  (or (:assert/fail result)\n      (:test/fail result)\n      (:error result)))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "save",
    "line" 290,
    "column" 1,
    "doc" "Save results at info level or higher, using store.",
    "tag" nil,
    "source"
    "(defn save\n  \"Save results at info level or higher, using store.\"\n  [e]\n  (when (event/level-enabled? (:level e) :info)\n    (store e)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["e"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "run-iter",
    "line" 103,
    "column" 1,
    "doc" "Run a single test iteration",
    "tag" nil,
    "source"
    "(defn run-iter\n  \"Run a single test iteration\"\n  [name f input]\n  (event/report :test/iter :level :debug :name name :args input :tags #{:begin})\n  (try\n   (let [result (apply f input)]\n     (when-not (realized? *failed*)\n       (event/report :test/iter :level :debug :name name :return result :tags #{:end})))\n   (catch Throwable t\n     (deliver *failed* :error)\n     (event/report :error :name name :exception t))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["name" "f" "input"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "run-generative-tests",
    "line" 211,
    "column" 1,
    "doc" "Run generative tests.",
    "tag" nil,
    "source"
    "(defn run-generative-tests\n  \"Run generative tests.\"\n  [nses nthreads msec]\n  (let [c (count (->> (apply find-vars-in-namespaces nses)\n                      (mapcat tests)))]\n    (when-not (zero? c)\n      (let [test-msec (quot msec c)]\n        (doseq [ns nses]\n          (when-let [fs (->> (find-vars-in-namespaces ns)\n                             (mapcat tests)\n                             seq)]\n            (event/report :test/group\n                          :name ns\n                          :tags #{:begin}\n                          :test/threads nthreads\n                          :test/count (count fs))\n            (try\n             (run-batch\n              fs\n              nthreads\n              test-msec)\n             (finally\n              (event/report :test/group :tags #{:end} :test/threads nthreads :test/count (count fs))))))))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["nses" "nthreads" "msec"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "ct-adapter",
    "line" 97,
    "column" 1,
    "doc" "Adapt clojure.test event model to fire c.t.g events.",
    "tag" nil,
    "source"
    "(defn ct-adapter\n  \"Adapt clojure.test event model to fire c.t.g events.\"\n  [m]\n  (when-let [e (ctevent->event m)]\n    (event/report-fn e)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["m"]]}
   {"arglists" nil,
    "ns" "clojure.test.generative.runner",
    "name" "store-agent",
    "column" 1,
    "line" 278,
    "source" "(def store-agent (agent nil))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "var-tests",
    "line" 166,
    "column" 1,
    "doc"
    "TestContainer.tests support for vars. To create custom test\n   types, define vars that have :c.t.g/type metadata, and then add\n   a matching var-tests method that returns a collection of tests.",
    "tag" nil,
    "source"
    "(defmulti var-tests\n  \"TestContainer.tests support for vars. To create custom test\n   types, define vars that have :c.t.g/type metadata, and then add\n   a matching var-tests method that returns a collection of tests.\"\n  (fn [v] (:clojure.test.generative/type (meta v))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "run-batch",
    "line" 158,
    "column" 1,
    "doc"
    "Run a batch of fs on nthreads each. Call each f repeatedly\n   for up to test-msec",
    "tag" nil,
    "source"
    "(defn run-batch\n  \"Run a batch of fs on nthreads each. Call each f repeatedly\n   for up to test-msec\"\n  [tests nthreads test-msec]\n  (when (seq tests)\n    (doseq [test tests]\n      (run-for test nthreads test-msec))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["tests" "nthreads" "test-msec"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "store",
    "line" 280,
    "column" 1,
    "doc" "store data in .tg/{process-id}",
    "tag" nil,
    "source"
    "(def store\n  \"store data in .tg/{process-id}\"\n  (io/serialized\n   (fn [e]\n     (binding [*print-length* nil\n               *print-level* nil\n               *out* @storage-writer]\n       (println e)))\n   store-agent))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "test-dirs",
    "line" 296,
    "column" 1,
    "doc"
    "Runs tests in dirs, returning a map of test lib keyword\n   to summary data",
    "tag" nil,
    "source"
    "(defn test-dirs\n  \"Runs tests in dirs, returning a map of test lib keyword\n   to summary data\"\n  [& dirs]\n  (let [nses (mapcat #(ns/find-namespaces-in-dir (java.io.File. ^String %)) dirs)\n        conf (config/config)]\n    (doseq [ns nses] (require ns))\n    (event/install-default-handlers)\n    (run-all-tests nses (:threads conf) (:msec conf))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["&" "dirs"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "-main",
    "line" 306,
    "column" 1,
    "doc"
    "Command line entry point, runs all tests in dirs using clojure.test and\n   test.generative. Calls System.exit!",
    "tag" nil,
    "source"
    "(defn -main\n  \"Command line entry point, runs all tests in dirs using clojure.test and\n   test.generative. Calls System.exit!\"\n  [& dirs]\n  (if (seq dirs)\n    (try\n     (let [results (apply test-dirs dirs)\n           failed? (boolean (some failed? (vals results)))]\n       (doseq [[k v] results]\n         (println (str \"\\nFramework \" k))\n         (println v))\n       (when failed?\n         (binding [*out* *err*]\n           (println \"\\n*** Some tests failed ***\\n\")))\n       (System/exit (if failed? 1 0)))\n     (catch Throwable t\n       (.printStackTrace t)\n       (System/exit -1))\n     (finally\n      (shutdown-agents)))\n    (do\n      (println \"Specify at least one directory with tests\")\n      (System/exit -1))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["&" "dirs"]]}
   {"arglists" [["ns"]],
    "ns" "clojure.test.generative.runner",
    "name" "has-clojure-test-tests?",
    "column" 1,
    "line" 235,
    "source"
    "(defn has-clojure-test-tests?\n  [ns]\n  (or (contains? (ns-interns ns) 'test-ns-hook)\n      (some (comp :test meta) (vals (ns-interns ns)))))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "run-all-tests",
    "line" 240,
    "column" 1,
    "doc" "Run generative tests and clojure.test tests",
    "tag" nil,
    "source"
    "(defn run-all-tests\n  \"Run generative tests and clojure.test tests\"\n  [nses threads msec]\n  (binding [ctest/report ct-adapter]\n    (let [run-with-counts\n          (fn [lib f]\n            (let [event-counts (atom {})\n                  event-counter #(when-not (contains? (:tags %) :begin)\n                                   (when-let [type (:type %)]\n                                     (swap! event-counts update-in [type] (fnil inc 0))))]\n              (event/report :test/library :name lib)\n              (event/with-handler event-counter (f))\n              @event-counts))\n          ct-results (run-with-counts 'clojure.test\n                       #(when-let [ctnses (seq (filter has-clojure-test-tests? nses))]\n                          (apply ctest/run-tests ctnses)))\n          ctg-results (run-with-counts 'clojure.test.generative\n                        #(run-generative-tests nses threads msec))]\n      (io/await)\n      {'clojure.test ct-results\n       'clojure.test.generative ctg-results})))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["nses" "threads" "msec"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "TestContainer",
    "line" 185,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol TestContainer\n  (tests\n   [_]\n   \"Returns a collection of generative tests, where a test is a map with\n      :name     ns-qualified symbol\n      :fn       fn to test\n      :inputs   fn returning a (possibly infinite!) sequence of inputs\n\n   All input generation should use and gen/*rnd*\n   if a source of pseudo-randomness is needed.\"))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "run-for",
    "line" 115,
    "column" 1,
    "doc"
    "Run f (presumably for side effects) repeatedly on n threads,\n   until msec has passed or somebody signals *failed*",
    "tag" nil,
    "source"
    "(defn run-for\n  \"Run f (presumably for side effects) repeatedly on n threads,\n   until msec has passed or somebody signals *failed*\"\n  [test nthreads msec]\n  (let [start (System/currentTimeMillis)\n        futs (doall\n              (map\n               #(future\n                 (try\n                  (let [seed (+ % 42)\n                        name (:name test)\n                        f (:fn test)]\n                    (binding [gen/*rnd* (java.util.Random. seed)\n                              *failed* (promise)]\n                      (event/report :test/test :tags #{:begin} :test/seed seed :name name)\n                      (loop [iter 0\n                             [input & more] ((:inputs test))]\n                        (let [now (System/currentTimeMillis)\n                              failed? (realized? *failed*)]\n                          (if input\n                            (let [result (run-iter name f input)]\n                              (if (and (< now (+ start msec))\n                                       (not failed?))\n                                (recur (inc iter) more)\n                                (event/report :test/test\n                                              :msec (- now start)\n                                              :count (inc iter)\n                                              :tags #{:end}\n                                              :test/result (if failed? :test/fail :test/pass)\n                                              :level (if failed? :warn :info)\n                                              :name name)))\n                            (event/report :test/test\n                                          :msec (- now start)\n                                          :count iter\n                                          :tags #{:end :test/inputs-exhausted}\n                                          :test/result (if failed? :test/fail :test/pass)\n                                          :level (if failed? :warn :info)\n                                          :name name))))))\n                  (catch Throwable t\n                    (event/report :error :level :error :exception t :name name))))\n               (range nthreads)))]\n    (doseq [f futs] @f)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["test" "nthreads" "msec"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "failed!",
    "line" 26,
    "column" 1,
    "doc" "Tell the runner that a test failed",
    "tag" nil,
    "source"
    "(defn failed!\n  \"Tell the runner that a test failed\"\n  []\n  (when *failed*\n    (deliver *failed* :failed)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [[]]}
   {"arglists" [["&" "nses"]],
    "ns" "clojure.test.generative.runner",
    "name" "find-vars-in-namespaces",
    "column" 1,
    "line" 200,
    "source"
    "(defn find-vars-in-namespaces\n  [& nses]\n  (when nses\n    (reduce (fn [v ns] (into v (vals (ns-interns ns)))) [] nses)))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.test.generative.runner",
    "name" "process-id",
    "column" 1,
    "line" 268,
    "source"
    "(def process-id\n  (delay\n   (java.util.UUID/randomUUID)))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.test.generative.runner",
    "name" "storage-writer",
    "column" 1,
    "line" 272,
    "source"
    "(def storage-writer\n  (delay\n   (let [f (str \".tg/\" @process-id)]\n     (jio/make-parents f)\n     (jio/writer f :append true))))",
    "file" "clojure/test/generative/runner.clj",
    "tag" nil}],
  "clojure.test.generative.logback"
  [{"ns" "clojure.test.generative.logback",
    "name" "event->logback",
    "line" 32,
    "column" 1,
    "doc" "Returns map with keys :logger, :event, :fire",
    "tag" nil,
    "source"
    "(defn event->logback\n  \"Returns map with keys :logger, :event, :fire\"\n  [event]\n  (let [name (logger-name event)\n        logger ^ch.qos.logback.classic.Logger (LoggerFactory/getLogger name)\n        msg (delay (binding [*print-length* 50]\n                     (pr-str (dissoc event :level :thread :tstamp :thread/name))))\n        level (level->logback (:level event))\n        logback-event (reify ILoggingEvent\n                             (getThreadName [_] (str (:thread/name event)))\n                             (getLevel [_] level)\n                             (getMessage [_] @msg)\n                             (getArgumentArray [_])\n                             (getFormattedMessage [_] @msg)\n                             (getLoggerName [_] (.getName logger))\n                             (getLoggerContextVO [_] (.. logger getLoggerContext getLoggerContextRemoteView))\n                             (getThrowableProxy [_])\n                             (getCallerData [_])\n                             (hasCallerData [_])\n                             (getMarker [_])\n                             (getMdc [_])\n                             (getTimeStamp [_] (:tstamp event))\n                             (prepareForDeferredProcessing [_]))]\n    {:logger logger\n     :event logback-event\n     :fire (fn [] (.callAppenders logger logback-event))}))",
    "file" "clojure/test/generative/logback.clj",
    "arglists" [["event"]]}
   {"arglists" [["level"]],
    "ns" "clojure.test.generative.logback",
    "name" "level->logback",
    "column" 1,
    "line" 18,
    "source"
    "(defn level->logback\n  ^Level [level]\n  (case level\n        :info Level/INFO\n        :warn Level/WARN\n        :error Level/ERROR\n        :debug Level/DEBUG))",
    "file" "clojure/test/generative/logback.clj",
    "tag" nil}
   {"arglists" [["event"]],
    "ns" "clojure.test.generative.logback",
    "name" "logger-name",
    "column" 1,
    "line" 26,
    "source"
    "(defn logger-name\n  ^String [event]\n  (event/fqname (or (:ns event)\n                    (:type event)\n                    \"root\")))",
    "file" "clojure/test/generative/logback.clj",
    "tag" nil}
   {"arglists" [["event"]],
    "ns" "clojure.test.generative.logback",
    "name" "handler",
    "column" 1,
    "line" 59,
    "source"
    "(defn handler\n  [event]\n  ((:fire (event->logback event))))",
    "file" "clojure/test/generative/logback.clj",
    "tag" nil}],
  "clojure.test.generative.io"
  [{"source"
    "(defmulti console-reporter :type :hierarchy #'report-hierarchy)",
    "arglists" nil,
    "tag" nil,
    "ns" "clojure.test.generative.io",
    "name" "console-reporter",
    "column" 1,
    "line" 71,
    "file" "clojure/test/generative/io.clj"}
   {"arglists" nil,
    "ns" "clojure.test.generative.io",
    "name" "report-hierarchy",
    "column" 1,
    "line" 61,
    "source"
    "(def report-hierarchy\n  (reduce\n   #(apply derive %1 %2)\n   (make-hierarchy)\n   [[:test/iter :ignore]\n    [:test/seed :ignore]\n    [:test/pass :ignore]\n    [:assert/pass :ignore]\n    [:assert/summary :ignore]]))",
    "file" "clojure/test/generative/io.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.test.generative.io",
    "name" "serializer",
    "line" 14,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private serializer (agent nil))",
    "file" "clojure/test/generative/io.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.io",
    "name" "pr-str",
    "line" 41,
    "column" 1,
    "doc" "Print with event print settings",
    "tag" nil,
    "source"
    "(defn pr-str\n  \"Print with event print settings\"\n  [s]\n  (binding [*print-length* event-print-length\n            *print-level* event-print-level]\n    (clojure.core/pr-str s)))",
    "file" "clojure/test/generative/io.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.test.generative.io",
    "name" "event-print-level",
    "line" 39,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private event-print-level 10)",
    "file" "clojure/test/generative/io.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.io",
    "name" "pprint",
    "line" 52,
    "column" 1,
    "doc" "threadsafe pprint with event print settings",
    "tag" nil,
    "source"
    "(def pprint\n  \"threadsafe pprint with event print settings\"\n  (serialized\n   (fn [s]\n     (binding [*print-length* event-print-length\n               *print-level* event-print-level]\n       (pprint/pprint s)\n       (flush)))))",
    "file" "clojure/test/generative/io.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.test.generative.io",
    "name" "event-print-length",
    "line" 38,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private event-print-length 100)",
    "file" "clojure/test/generative/io.clj",
    "arglists" nil}
   {"arglists" [[]],
    "ns" "clojure.test.generative.io",
    "name" "await",
    "column" 1,
    "line" 16,
    "source"
    "(defn await\n  []\n  \"Wait for everything sent to the serializer\"\n  (send-off serializer (fn [_]))\n  (clojure.core/await serializer))",
    "file" "clojure/test/generative/io.clj",
    "tag" nil}
   {"ns" "clojure.test.generative.io",
    "name" "println",
    "line" 48,
    "column" 1,
    "doc" "threadsafe print with event print settings",
    "tag" nil,
    "source"
    "(def println\n  \"threadsafe print with event print settings\"\n  (serialized clojure.core/println))",
    "file" "clojure/test/generative/io.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.io",
    "name" "serialized",
    "line" 22,
    "column" 1,
    "doc"
    "Returns a function that calls f for side effects, async,\n   serialized by an agent",
    "tag" nil,
    "source"
    "(defn serialized\n  \"Returns a function that calls f for side effects, async,\n   serialized by an agent\"\n  ([f] (serialized f serializer))\n  ([f agt]\n     (fn [& args]\n       (send-off agt\n                 (fn [_]\n                   (try\n                    (apply f args)\n                    (catch Throwable t\n                      (.printStackTrace t)))\n                   nil))\n       nil)))",
    "file" "clojure/test/generative/io.clj",
    "arglists" [["f"] ["f" "agt"]]}],
  "clojure.test.generative.event"
  [{"ns" "clojure.test.generative.event",
    "name" "FQName",
    "line" 16,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol FQName\n  (fqname [_]))",
    "file" "clojure/test/generative/event.clj",
    "arglists" nil}
   {"arglists" [["&" "args"]],
    "ns" "clojure.test.generative.event",
    "name" "create",
    "column" 1,
    "line" 55,
    "source"
    "(defn create\n  [& args]\n  (let [t (Thread/currentThread)\n        event (apply assocnn\n                     {:tstamp (System/currentTimeMillis)\n                      :thread (.getId t)\n                      :thread/name (.getName t)\n                      :pid pid\n                      :level :info}\n                     args)]\n    (assert (keyword? (:type event)) event)\n    event))",
    "file" "clojure/test/generative/event.clj",
    "tag" nil}
   {"ns" "clojure.test.generative.event",
    "name" "report-context",
    "macro" true,
    "line" 139,
    "column" 1,
    "doc" "Report event with contextual ns, file, line, bindings.",
    "tag" nil,
    "source"
    "(defmacro report-context\n  \"Report event with contextual ns, file, line, bindings.\"\n  [type & args]\n  (assert (even? (count args)) args)\n  `(report-fn\n    (create :locals ~(local-bindings &env)\n            :file ~*file*\n            :line ~(:line (meta &form))\n            ~@args\n            :type ~type)))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["type" "&" "args"]]}
   {"ns" "clojure.test.generative.event",
    "name" "load-var-val",
    "line" 91,
    "column" 1,
    "doc" "Load and return the value of a var",
    "tag" nil,
    "source"
    "(defn load-var-val\n  \"Load and return the value of a var\"\n  [fqname]\n  (when-let [ns (namespace fqname)]\n    (require (symbol ns)))\n  (if-let [v (resolve fqname)]\n    @v\n    (throw (IllegalArgumentException. (str \"No var named \" fqname)))))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["fqname"]]}
   {"ns" "clojure.test.generative.event",
    "name" "with-handler",
    "macro" true,
    "line" 100,
    "column" 1,
    "doc" "Run with handler temporarily installed.",
    "tag" nil,
    "source"
    "(defmacro with-handler\n  \"Run with handler temporarily installed.\"\n  [handler & body]\n  `(let [h# ~handler]\n     (add-handler h#)\n     (try\n      ~@body\n      (finally\n       (remove-handler h#)))))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["handler" "&" "body"]]}
   {"ns" "clojure.test.generative.event",
    "name" "assocnn",
    "line" 47,
    "column" 1,
    "doc" "Assoc but drop nils",
    "tag" nil,
    "source"
    "(defn assocnn\n  \"Assoc but drop nils\"\n  ([m k v] (if (nil? v) m (assoc m k v)))\n  ([m k v & kvs] (let [ret (assocnn m k v)]\n                   (if kvs\n                     (recur ret (first kvs) (second kvs) (nnext kvs))\n                     ret))))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["m" "k" "v"] ["m" "k" "v" "&" "kvs"]]}
   {"ns" "clojure.test.generative.event",
    "name" "level-enabled?",
    "line" 34,
    "column" 1,
    "doc" "Is the event-level enabled?",
    "tag" nil,
    "source"
    "(defn level-enabled?\n  \"Is the event-level enabled?\"\n  [event-level enable-level]\n  (case enable-level\n        :error (case event-level (:error) true false)\n        :warn (case event-level (:error :warn) true false)\n        :info (case event-level (:error :warn :info) true false)\n        :debug true))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["event-level" "enable-level"]]}
   {"ns" "clojure.test.generative.event",
    "name" "pid",
    "line" 43,
    "column" 1,
    "doc" "Process id",
    "tag" "#<core$long clojure.core$long@3c3c270c>",
    "source"
    "(def ^long pid\n  \"Process id\"\n  (read-string (.getName (java.lang.management.ManagementFactory/getRuntimeMXBean))))",
    "file" "clojure/test/generative/event.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.event",
    "name" "report",
    "macro" true,
    "line" 128,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro report\n  [type & args]\n  (assert (even? (count args)) args)\n  `(report-fn (create ~@args :type ~type)))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["type" "&" "args"]]}
   {"ns" "clojure.test.generative.event",
    "name" "install-default-handlers",
    "line" 110,
    "column" 1,
    "doc"
    "Installs handler functions, a comma-delimited list of fn names, from\n   clojure.test.generative.event.handlers. If none are specified, install\n   c.t.g.io/console-reporter",
    "tag" nil,
    "source"
    "(defn install-default-handlers\n  \"Installs handler functions, a comma-delimited list of fn names, from\n   clojure.test.generative.event.handlers. If none are specified, install\n   c.t.g.io/console-reporter\"\n  []\n  (reset! handlers [])\n  (doseq [handler (:handlers (config/config))]\n    (add-handler (load-var-val (symbol handler)))))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [[]]}
   {"ns" "clojure.test.generative.event",
    "name" "local-bindings",
    "line" 133,
    "column" 1,
    "doc"
    "Produces a map of the names of local bindings to their values.",
    "tag" nil,
    "source"
    "(defn local-bindings\n  \"Produces a map of the names of local bindings to their values.\"\n  [env]\n  (let [symbols (map key env)]\n    (zipmap (map (fn [sym] `(quote ~sym)) symbols) symbols)))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["env"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.test.generative.event",
    "doc" nil,
    "arglists" [["_"]],
    "name" "fqname"}
   {"ns" "clojure.test.generative.event",
    "name" "remove-handler",
    "line" 82,
    "column" 1,
    "doc" "Remove a handler. Idempotent",
    "tag" nil,
    "source"
    "(defn remove-handler\n  \"Remove a handler. Idempotent\"\n  [f]\n  (swap!\n   handlers\n   (fn [v f]\n     (into (empty v) (remove #{f} v)))\n   f))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["f"]]}
   {"ns" "clojure.test.generative.event",
    "name" "report-fn",
    "line" 119,
    "column" 1,
    "doc"
    "Call the installed handlers for an event, or io/pprint if no handlers\n   installed.",
    "tag" nil,
    "source"
    "(defn report-fn\n  \"Call the installed handlers for an event, or io/pprint if no handlers\n   installed.\"\n  [event]\n  (if-let [hs (seq @handlers)]\n    (doseq [h hs]\n      (h event))\n    (io/pprint event)))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["event"]]}
   {"private" true,
    "ns" "clojure.test.generative.event",
    "name" "handlers",
    "line" 68,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private handlers\n  (atom []))",
    "file" "clojure/test/generative/event.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.event",
    "name" "add-handler",
    "line" 71,
    "column" 1,
    "doc" "Add a handler. Idempotent",
    "tag" nil,
    "source"
    "(defn add-handler\n  \"Add a handler. Idempotent\"\n  [f]\n  (swap!\n   handlers\n   (fn [v f]\n     (if (some #{f} v)\n       v\n       (conj v f)))\n   f))",
    "file" "clojure/test/generative/event.clj",
    "arglists" [["f"]]}],
  "clojure.test.generative.config"
  [{"arglists" [[]],
    "ns" "clojure.test.generative.config",
    "name" "config",
    "column" 1,
    "line" 18,
    "source"
    "(defn config\n  []\n  (reduce\n   (fn [m [prop path coerce default]]\n     (let [val (System/getProperty prop)]\n       (if (seq val)\n         (assoc-in m path (coerce val))\n         (assoc-in m path default))))\n   {}\n   config-mapping))",
    "file" "clojure/test/generative/config.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.test.generative.config",
    "name" "config-mapping",
    "column" 1,
    "line" 13,
    "source"
    "(def config-mapping\n  [[\"clojure.test.generative.threads\" [:threads] read-string (.availableProcessors (Runtime/getRuntime))]\n   [\"clojure.test.generative.msec\" [:msec] read-string 10000]\n   [\"clojure.test.generative.handlers\" [:handlers] #(str/split % #\",\") [\"clojure.test.generative.io/console-reporter\"]]])",
    "file" "clojure/test/generative/config.clj",
    "tag" nil}],
  "clojure.test.generative"
  [{"ns" "clojure.test.generative",
    "name" "fail",
    "macro" true,
    "line" 48,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro fail\n  [& args]\n  `(do\n     (runner/failed!)\n     ~(with-meta `(event/report-context :assert/fail\n                                        :level :warn\n                                        ~@args)\n        (meta &form))))",
    "file" "clojure/test/generative.clj",
    "arglists" [["&" "args"]]}
   {"private" true,
    "ns" "clojure.test.generative",
    "name" "fully-qualified",
    "line" 15,
    "column" 1,
    "doc"
    "Qualify a name used in :tag metadata. Unqualified names are\n   interpreted in the 'clojure.data.generators, except\n   for the fn-building symbols fn and fn*.",
    "tag" nil,
    "source"
    "(defn- fully-qualified\n  \"Qualify a name used in :tag metadata. Unqualified names are\n   interpreted in the 'clojure.data.generators, except\n   for the fn-building symbols fn and fn*.\"\n  [n]\n  (let [ns (cond\n            (#{'fn*} n) nil\n            (#{'fn} n) 'clojure.core\n            (namespace n) (namespace n)\n            :else 'clojure.data.generators)]\n    (if ns\n      (symbol (str ns) (name n))\n      n)))",
    "file" "clojure/test/generative.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.test.generative",
    "name" "defspec",
    "macro" true,
    "line" 71,
    "column" 1,
    "doc"
    "Defines a function named name that expects args. The defined\n   function binds '%' to the result of calling fn-to-test with args,\n   and runs validator-body forms (if any), which have access to both\n   args and %. The defined function.\n\n   Args must have type hints (i.e. :tag metdata), which are\n   interpreted as instructions for generating test input\n   data. Unquoted names in type hints are resolved in the\n   c.t.g.generators namespace, which has generator functions for\n   common Clojure data types. For example, the following argument list\n   declares that 'seed' is an int, and that 'iters' is an int in the\n   uniform distribution from 1 to 100:\n\n       [^int seed ^{:tag (uniform 1 100)} iters]\n\n   Backquoted names in an argument list are resolved in the current\n   namespace, allowing arbitrary generators, e.g.\n\n       [^{:tag `scary-word} word]\n\n   The function c.t.g.runner/run-iter takes a var naming a test, and runs\n   a single test iteration, generating inputs based on the arg type hints.",
    "tag" nil,
    "source"
    "(defmacro defspec\n  \"Defines a function named name that expects args. The defined\n   function binds '%' to the result of calling fn-to-test with args,\n   and runs validator-body forms (if any), which have access to both\n   args and %. The defined function.\n\n   Args must have type hints (i.e. :tag metdata), which are\n   interpreted as instructions for generating test input\n   data. Unquoted names in type hints are resolved in the\n   c.t.g.generators namespace, which has generator functions for\n   common Clojure data types. For example, the following argument list\n   declares that 'seed' is an int, and that 'iters' is an int in the\n   uniform distribution from 1 to 100:\n\n       [^int seed ^{:tag (uniform 1 100)} iters]\n\n   Backquoted names in an argument list are resolved in the current\n   namespace, allowing arbitrary generators, e.g.\n\n       [^{:tag `scary-word} word]\n\n   The function c.t.g.runner/run-iter takes a var naming a test, and runs\n   a single test iteration, generating inputs based on the arg type hints.\"\n  [name fn-to-test args & validator-body]\n  (when-let [missing-tags (->> (map #(list % (-> % meta :tag)) args)\n                               (filter (fn [[_ tag]] (nil? tag)))\n                               seq)]\n    (throw (IllegalArgumentException. (str \"Missing tags for \" (seq (map first missing-tags)) \" in \" name))))\n  `(defn ~(with-meta name (assoc (meta name)\n                            ::type :defspec\n                            ::arg-fns (into [] (map #(-> % meta :tag tag->gen eval)  args))))\n     ~(into [] (map (fn [a#] (with-meta a# (dissoc (meta a#) :tag))) args))\n     (let [~'% (apply ~fn-to-test ~args)]\n       ~@validator-body\n       ~'%)))",
    "file" "clojure/test/generative.clj",
    "arglists" [["name" "fn-to-test" "args" "&" "validator-body"]]}
   {"ns" "clojure.test.generative",
    "name" "is",
    "macro" true,
    "line" 57,
    "column" 1,
    "doc"
    "Assert that v is true, otherwise fail the current generative\n   test (with optional msg).",
    "tag" nil,
    "source"
    "(defmacro is\n  \"Assert that v is true, otherwise fail the current generative\n   test (with optional msg).\"\n  ([v] (with-meta `(is ~v nil) (meta &form)))\n  ([v msg]\n     `(let [~'actual ~v ~'expected '~v]\n        (if ~'actual\n          (do\n            (event/report :assert/pass :level :debug)\n            ~'actual)\n          ~(with-meta\n             `(fail ~@(when msg `[:message ~msg]))\n             (meta &form))))))",
    "file" "clojure/test/generative.clj",
    "arglists" [["v"] ["v" "msg"]]}
   {"private" true,
    "ns" "clojure.test.generative",
    "name" "tag->gen",
    "line" 40,
    "column" 1,
    "doc" "Convert tag to source code form for a test data generator.",
    "tag" nil,
    "source"
    "(defn- tag->gen\n  \"Convert tag to source code form for a test data generator.\"\n  [arg]\n  (let [form (walk/prewalk (fn [s] (if (symbol? s) (fully-qualified s) s)) (dequote arg))]\n    (if (seq? form)\n      (list 'fn '[] form) \n      form)))",
    "file" "clojure/test/generative.clj",
    "arglists" [["arg"]]}
   {"private" true,
    "ns" "clojure.test.generative",
    "name" "dequote",
    "line" 29,
    "column" 1,
    "doc"
    "Remove the backquotes used to call out user-namespaced forms.",
    "tag" nil,
    "source"
    "(defn- dequote\n  \"Remove the backquotes used to call out user-namespaced forms.\"\n  [form]\n  (walk/prewalk\n   #(if (and (sequential? %)\n             (= 2 (count %))\n             (= 'quote (first %)))\n      (second %)\n      %)\n   form))",
    "file" "clojure/test/generative.clj",
    "arglists" [["form"]]}]},
 "description" "test.generative 0.4.0",
 "version" "0.4.0",
 "name" "clojure.test.generative",
 "group" "clojure.test.generative"}
