{"namespaces"
 {"clojure.test.generative.runner"
  [{"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "find-vars-in-dirs",
    "line" 77,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- find-vars-in-dirs\n  [& dirs]\n  (let [nses (mapcat #(ns/find-namespaces-in-dir (java.io.File. ^String %)) dirs)]\n    (doseq [ns nses] (require ns))\n    (apply find-vars-in-namespaces nses)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["&" "dirs"]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "failed?",
    "line" 112,
    "column" 1,
    "doc" "Does test result indicate a failure?",
    "tag" nil,
    "source"
    "(defn- failed?\n  \"Does test result indicate a failure?\"\n  [result]\n  (contains? result :exception))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["result"]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "prf",
    "line" 126,
    "column" 1,
    "doc" "Print and flush.",
    "tag" nil,
    "source"
    "(defn- prf\n  \"Print and flush.\"\n  [s]\n  (print s) (flush))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "run-one",
    "line" 83,
    "column" 1,
    "doc"
    "Run f (presumably for side effects) repeatedly on n threads,\n   until msec has passed or somebody throws an exception.\n   Returns as many status maps as seeds passed in.",
    "tag" nil,
    "source"
    "(defn- run-one\n  \"Run f (presumably for side effects) repeatedly on n threads,\n   until msec has passed or somebody throws an exception.\n   Returns as many status maps as seeds passed in.\"\n  [{:keys [test input-gen]} {:keys [msec seeds]}]\n  (let [f (eval test)\n        start (System/currentTimeMillis)\n        futs (mapv\n              #(future\n                (try\n                 (binding [gen/*rnd* (java.util.Random. %)]\n                   (loop [iter 0\n                          [input & more] (input-gen)]\n                     (let [status {:iter iter :seed % :test test :input input}]\n                       (if input\n                         (let [failure (try\n                                        (apply f input)\n                                        nil\n                                        (catch Throwable t\n                                          (assoc status :exception t) ))\n                               now (System/currentTimeMillis)]\n                           (cond\n                            failure failure\n                            (< now (+ start msec)) (recur (inc iter) more)\n                            :else (select-keys status [:test :seed :iter])))\n                         (assoc status :exhausted true)))))))\n              seeds)]\n    (map deref futs)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists"
    [[[["keys" ["test" "input-gen"]]] [["keys" ["msec" "seeds"]]]]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "run-n",
    "line" 117,
    "column" 1,
    "doc"
    "Run tests in parallel on nthreads, dividing msec equally between the tests.\n   Returns a list of maps of :iter, :seed",
    "tag" nil,
    "source"
    "(defn- run-n\n  \"Run tests in parallel on nthreads, dividing msec equally between the tests.\n   Returns a list of maps of :iter, :seed\"\n  [{:keys [nthreads msec]} tests]\n  (mapcat #(run-one %\n                    {:msec (/ msec (count tests))\n                     :seeds (repeatedly nthreads next-seed)})\n          tests))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [[[["keys" ["nthreads" "msec"]]] "tests"]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "config-mapping",
    "line" 19,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private config-mapping\n     [[\"clojure.test.generative.threads\"\n       [:nthreads]\n       read-string\n       (max 1 (dec (.availableProcessors (Runtime/getRuntime))))]\n      [\"clojure.test.generative.msec\"\n       [:msec]\n       read-string\n       10000]])",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "config",
    "line" 29,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- config\n  []\n  (reduce\n   (fn [m [prop path coerce default]]\n     (let [val (System/getProperty prop)]\n       (if (seq val)\n         (assoc-in m path (coerce val))\n         (assoc-in m path default))))\n   {}\n   config-mapping))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "rnd",
    "line" 40,
    "column" 1,
    "tag" "java.util.Random",
    "source"
    "(def ^:private ^java.util.Random rnd (java.util.Random. (System/currentTimeMillis)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "next-seed",
    "line" 42,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- next-seed\n  []\n  (locking rnd\n    (.nextInt rnd)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [[]]}
   {"ns" "clojure.test.generative.runner",
    "name" "dir-tests",
    "line" 131,
    "column" 1,
    "doc" "Returns all tests in dirs",
    "tag" nil,
    "source"
    "(defn dir-tests\n  \"Returns all tests in dirs\"\n  [dirs]\n  (let [load (fn [s] (require s) s)]\n    (->> (mapcat #(ns/find-namespaces-in-dir (java.io.File. ^String %)) dirs)\n         (map load)\n         (apply find-vars-in-namespaces)\n         (mapcat get-tests))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["dirs"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "Testable",
    "line" 47,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol Testable\n  (get-tests [_]))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" nil}
   {"ns" "clojure.test.generative.runner",
    "name" "run",
    "line" 146,
    "column" 1,
    "doc"
    "Designed for interactive use.  Prints results to *out* and throws\n   on first failure encountered.",
    "tag" nil,
    "source"
    "(defn run\n  \"Designed for interactive use.  Prints results to *out* and throws\n   on first failure encountered.\"\n  [nthreads msec & test-containers]\n  (doseq [result (run-n {:nthreads nthreads\n                         :msec msec}\n                        (mapcat get-tests test-containers))]\n    (if (failed? result)\n      (throw (ex-info \"Generative test failed\" result))\n      (prn result))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["nthreads" "msec" "&" "test-containers"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "run-suite",
    "line" 157,
    "column" 1,
    "doc" "Designed for test suite use.",
    "tag" nil,
    "source"
    "(defn run-suite\n  \"Designed for test suite use.\"\n  [{:keys [nthreads msec progress]} tests]\n  (let [progress (or progress #(prf \".\"))\n        ret (reduce\n             (fn [{:keys [failures iters nresults]} result]\n               (when (:exception result)\n                 (.printStackTrace ^Throwable (:exception result)))\n               (if (:exception result)\n                 (prn result)\n                 (progress))\n               {:failures (+ failures (if (:exception result) 1 0))\n                :iters (+ iters (:iter result))\n                :nresults (+ nresults 1)})\n             {:failures 0 :iters 0 :nresults 0}\n             (run-n {:nthreads nthreads\n                     :msec msec}\n                    tests))]\n    (-> ret\n        (assoc :tests (/ (:nresults ret) nthreads))\n        (dissoc :nresults))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [[[["keys" ["nthreads" "msec" "progress"]]] "tests"]]}
   {"ns" "clojure.test.generative.runner",
    "name" "-main",
    "line" 179,
    "column" 1,
    "doc" "Command line entry point. Calls System.exit!",
    "tag" nil,
    "source"
    "(defn -main\n  \"Command line entry point. Calls System.exit!\"\n  [& dirs]\n  (if (seq dirs)\n    (try\n     (let [result (run-suite (config) (dir-tests dirs))]\n       (println \"\\n\" result)\n       (System/exit (:failures result)))\n     (catch Throwable t\n       (.printStackTrace t)\n       (System/exit -1))\n     (finally\n      (shutdown-agents)))\n    (do\n      (println \"Specify at least one directory with tests\")\n      (System/exit -1))))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["&" "dirs"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.test.generative.runner",
    "doc" nil,
    "arglists" [["_"]],
    "name" "get-tests"}
   {"ns" "clojure.test.generative.runner",
    "name" "inputs",
    "line" 140,
    "column" 1,
    "doc"
    "For interactive use.  Returns an infinite sequence of inputs for\n   a test.",
    "tag" nil,
    "source"
    "(defn inputs\n  \"For interactive use.  Returns an infinite sequence of inputs for\n   a test.\"\n  [test]\n  ((:input-gen test)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["test"]]}
   {"private" true,
    "ns" "clojure.test.generative.runner",
    "name" "find-vars-in-namespaces",
    "line" 72,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- find-vars-in-namespaces\n  [& nses]\n  (when nses\n    (reduce (fn [v ns] (into v (vals (ns-interns ns)))) [] nses)))",
    "file" "clojure/test/generative/runner.clj",
    "arglists" [["&" "nses"]]}],
  "clojure.test.generative"
  [{"private" true,
    "ns" "clojure.test.generative",
    "name" "fully-qualified",
    "line" 13,
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
    "line" 46,
    "column" 1,
    "doc"
    "Defines a function named name that expects args. The defined\n   function binds '%' to the result of calling fn-to-test with args,\n   and runs validator-body forms (if any), which have access to both\n   args and %. The defined function.\n\n   Args must have type hints (i.e. :tag metadata), which are\n   interpreted as instructions for generating test input\n   data. Unquoted names in type hints are resolved in the\n   c.t.g.generators namespace, which has generator functions for\n   common Clojure data types. For example, the following argument list\n   declares that 'seed' is an int, and that 'iters' is an int in the\n   uniform distribution from 1 to 100:\n\n       [^int seed ^{:tag (uniform 1 100)} iters]\n\n   Backquoted names in an argument list are resolved in the current\n   namespace, allowing arbitrary generators, e.g.\n\n       [^{:tag `scary-word} word]\n\n   The function c.t.g.runner/run-iter takes a var naming a test, and runs\n   a single test iteration, generating inputs based on the arg type hints.",
    "tag" nil,
    "source"
    "(defmacro defspec\n  \"Defines a function named name that expects args. The defined\n   function binds '%' to the result of calling fn-to-test with args,\n   and runs validator-body forms (if any), which have access to both\n   args and %. The defined function.\n\n   Args must have type hints (i.e. :tag metadata), which are\n   interpreted as instructions for generating test input\n   data. Unquoted names in type hints are resolved in the\n   c.t.g.generators namespace, which has generator functions for\n   common Clojure data types. For example, the following argument list\n   declares that 'seed' is an int, and that 'iters' is an int in the\n   uniform distribution from 1 to 100:\n\n       [^int seed ^{:tag (uniform 1 100)} iters]\n\n   Backquoted names in an argument list are resolved in the current\n   namespace, allowing arbitrary generators, e.g.\n\n       [^{:tag `scary-word} word]\n\n   The function c.t.g.runner/run-iter takes a var naming a test, and runs\n   a single test iteration, generating inputs based on the arg type hints.\"\n  [name fn-to-test args & validator-body]\n  (when-let [missing-tags (->> (map #(list % (-> % meta :tag)) args)\n                               (filter (fn [[_ tag]] (nil? tag)))\n                               seq)]\n    (throw (IllegalArgumentException. (str \"Missing tags for \" (seq (map first missing-tags)) \" in \" name))))\n  `(defn ~(with-meta name (assoc (meta name)\n                            ::arg-fns (into [] (map #(-> % meta :tag tag->gen eval)  args))))\n     ~(into [] (map (fn [a#] (with-meta a# (dissoc (meta a#) :tag))) args))\n     (let [~'% (apply ~fn-to-test ~args)]\n       ~@validator-body\n       ~'%)))",
    "file" "clojure/test/generative.clj",
    "arglists" [["name" "fn-to-test" "args" "&" "validator-body"]]}
   {"private" true,
    "ns" "clojure.test.generative",
    "name" "tag->gen",
    "line" 38,
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
    "line" 27,
    "column" 1,
    "doc"
    "Remove the backquotes used to call out user-namespaced forms.",
    "tag" nil,
    "source"
    "(defn- dequote\n  \"Remove the backquotes used to call out user-namespaced forms.\"\n  [form]\n  (walk/prewalk\n   #(if (and (sequential? %)\n             (= 2 (count %))\n             (= 'quote (first %)))\n      (second %)\n      %)\n   form))",
    "file" "clojure/test/generative.clj",
    "arglists" [["form"]]}]},
 "description" "test.generative 0.5.1",
 "version" "0.5.1",
 "name" "clojure.test.generative",
 "group" "clojure.test.generative"}
