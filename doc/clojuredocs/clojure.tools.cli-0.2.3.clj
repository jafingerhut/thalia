{"namespaces"
 {"clojure.tools.cli"
  [{"source"
    "(defn cli\n  [args & specs]\n  (let [specs (map generate-spec specs)]\n    (let [[options extra-args] (apply-specs specs args)\n          banner  (with-out-str (banner-for specs))]\n      [options extra-args banner])))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "cli",
    "arglists" [["args" "&" "specs"]],
    "column" 1,
    "line" 108,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn default-values-for\n  [specs]\n  (into {} (for [s specs] [(s :name) (s :default)])))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "default-values-for",
    "arglists" [["specs"]],
    "column" 1,
    "line" 50,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn banner-for [specs]\n  (println \"Usage:\")\n  (println)\n  (let [docs (into (map build-doc specs)\n                   [[\"--------\" \"-------\" \"----\"]\n                    [\"Switches\" \"Default\" \"Desc\"]])\n        max-cols (->> (for [d docs] (map count d))\n                      (apply map (fn [& c] (apply vector c)))\n                      (map #(apply max %)))\n        vs (for [d docs]\n             (mapcat (fn [& x] (apply vector x)) max-cols d))]\n    (doseq [v vs]\n      (cl-format true \"~{ ~vA  ~vA  ~vA ~}\" v)\n      (prn))))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "banner-for",
    "arglists" [["specs"]],
    "column" 1,
    "line" 12,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn switches-for\n  [switches flag]\n  (-> (for [^String s switches]\n        (cond\n         (and flag (flag? s))            [(replace s #\"\\[no-\\]\" \"no-\") (replace s #\"\\[no-\\]\" \"\")]\n         (and flag (.startsWith s \"--\")) [(replace s #\"--\" \"--no-\") s]\n         :default                        [s]))\n      flatten))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "switches-for",
    "arglists" [["switches" "flag"]],
    "column" 1,
    "line" 83,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn generate-spec\n  [raw-spec]\n  (let [[switches raw-spec] (split-with #(and (string? %) (opt? %)) raw-spec)\n        [docs raw-spec]     (split-with string? raw-spec)\n        options             (apply hash-map raw-spec)\n        aliases             (map name-for switches)\n        flag                (or (flag? (last switches)) (options :flag))]\n    (merge {:switches (switches-for switches flag)\n            :docs     (first docs)\n            :aliases  (set aliases)\n            :name     (keyword (last aliases))\n            :parse-fn identity\n            :default  (if flag false nil)\n            :flag     flag}\n           options)))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "generate-spec",
    "arglists" [["raw-spec"]],
    "column" 1,
    "line" 92,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn apply-specs\n  [specs args]\n  (loop [options    (default-values-for specs)\n         extra-args []\n         args       args]\n    (if-not (seq args)\n      [options extra-args]\n      (let [opt  (first args)\n            spec (spec-for opt specs)]\n        (cond\n         (end-of-args? opt)\n         (recur options (into extra-args (vec (rest args))) nil)\n\n         (and (opt? opt) (nil? spec))\n         (throw (Exception. (str \"'\" opt \"' is not a valid argument\")))\n         \n         (and (opt? opt) (spec :flag))\n         (recur (assoc options (spec :name) (flag-for opt))\n                extra-args\n                (rest args))\n\n         (opt? opt)\n         (recur (assoc options (spec :name) ((spec :parse-fn) (second args)))\n                extra-args\n                (drop 2 args))\n\n         :default\n         (recur options (conj extra-args (first args)) (rest args)))))))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "apply-specs",
    "arglists" [["specs" "args"]],
    "column" 1,
    "line" 54,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn spec-for\n  [arg specs]\n  (->> specs\n       (filter (fn [s]\n                   (let [switches (set (s :switches))]\n                     (contains? switches arg))))\n       first))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "spec-for",
    "arglists" [["arg" "specs"]],
    "column" 1,
    "line" 42,
    "file" "clojure/tools/cli.clj"}
   {"source" "(defn end-of-args? [x]\n  (= \"--\" x))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "end-of-args?",
    "arglists" [["x"]],
    "column" 1,
    "line" 39,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn build-doc [{:keys [switches docs default]}]\n  [(apply str (interpose \", \" switches))\n   (or (str default) \"\")\n   (or docs \"\")])",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "build-doc",
    "arglists" [[[["keys" ["switches" "docs" "default"]]]]],
    "column" 1,
    "line" 7,
    "file" "clojure/tools/cli.clj"}
   {"source" "(defn opt? [^String x]\n  (.startsWith x \"-\"))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "opt?",
    "arglists" [["x"]],
    "column" 1,
    "line" 33,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn flag-for [^String v]\n  (not (.startsWith v \"--no-\")))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "flag-for",
    "arglists" [["v"]],
    "column" 1,
    "line" 30,
    "file" "clojure/tools/cli.clj"}
   {"source"
    "(defn name-for [k]\n  (replace k #\"^--no-|^--\\[no-\\]|^--|^-\" \"\"))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "name-for",
    "arglists" [["k"]],
    "column" 1,
    "line" 27,
    "file" "clojure/tools/cli.clj"}
   {"source" "(defn flag? [^String x]\n  (.startsWith x \"--[no-]\"))",
    "tag" nil,
    "ns" "clojure.tools.cli",
    "name" "flag?",
    "arglists" [["x"]],
    "column" 1,
    "line" 36,
    "file" "clojure/tools/cli.clj"}]},
 "description" "tools.cli 0.2.3",
 "version" "0.2.3",
 "name" "clojure.tools.cli",
 "group" "clojure.tools.cli"}
