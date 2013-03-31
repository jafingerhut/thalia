{"namespaces"
 {"clojure.data.csv"
  [{"ns" "clojure.data.csv",
    "name" "read-csv",
    "line" 83,
    "column" 1,
    "doc"
    "Reads CSV-data from input (String or java.io.Reader) into a lazy\n  sequence of vectors.\n\n   Valid options are\n     :separator (default \\,)\n     :quote (default \\\")",
    "tag" nil,
    "source"
    "(defn read-csv\n  \"Reads CSV-data from input (String or java.io.Reader) into a lazy\n  sequence of vectors.\n\n   Valid options are\n     :separator (default \\\\,)\n     :quote (default \\\\\\\")\"\n  [input & options]\n  (let [{:keys [separator quote] :or {separator \\, quote \\\"}} options]\n    (read-csv-from input (int separator) (int quote))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["input" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "eof",
    "line" 21,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} eof -1)",
    "file" "clojure/data/csv.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "read-quoted-cell",
    "line" 23,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-quoted-cell [^Reader reader ^StringBuilder sb sep quote]\n  (loop [ch (.read reader)]\n    (condp == ch\n      quote (let [next-ch (.read reader)]\n              (condp == next-ch\n                quote (do (.append sb (char quote))\n                          (recur (.read reader)))\n                sep :sep\n                lf  :eol\n                cr  (if (== (.read reader) lf)\n                      :eol\n                      (throw (Exception. ^String (format \"CSV error (unexpected character: %c)\" next-ch))))\n                eof :eof\n                (throw (Exception. ^String (format \"CSV error (unexpected character: %c)\" next-ch)))))\n      eof (throw (EOFException. \"CSV error (unexpected end of file)\"))\n      (do (.append sb (char ch))\n          (recur (.read reader))))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["reader" "sb" "sep" "quote"]]}
   {"ns" "clojure.data.csv",
    "name" "write-csv",
    "line" 123,
    "column" 1,
    "doc"
    "Writes data to writer in CSV-format.\n\n   Valid options are\n     :separator (Default \\,)\n     :quote (Default \\\")\n     :guote? (A predicate function which determines if a string should be quoted. Defaults to quoting only when necessary.)\n     :newline (:lf (default) or :cr+lf)",
    "tag" nil,
    "source"
    "(defn write-csv\n  \"Writes data to writer in CSV-format.\n\n   Valid options are\n     :separator (Default \\\\,)\n     :quote (Default \\\\\\\")\n     :guote? (A predicate function which determines if a string should be quoted. Defaults to quoting only when necessary.)\n     :newline (:lf (default) or :cr+lf)\"\n  [writer data & options]\n  (let [opts (apply hash-map options)\n        separator (or (:separator opts) \\,)\n        quote (or (:quote opts) \\\")\n        quote? (or (:quote? opts) #(some #{separator quote \\return \\newline} %))\n        newline (or (:newline opts) :lf)]\n    (write-csv* writer\n\t\tdata\n\t\tseparator\n\t\tquote\n                quote?\n\t\t({:lf \"\\n\" :cr+lf \"\\r\\n\"} newline))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["writer" "data" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "write-record",
    "line" 107,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-record [^Writer writer record sep quote quote?]\n  (loop [record record]\n    (when-first [cell record]\n      (write-cell writer cell sep quote quote?)\n      (when-let [more (next record)]\n\t(.write writer (int sep))\n\t(recur more)))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["writer" "record" "sep" "quote" "quote?"]]}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "write-cell",
    "line" 97,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-cell [^Writer writer obj sep quote quote?]\n  (let [string (str obj)\n\tmust-quote (quote? string)]\n    (when must-quote (.write writer (int quote)))\n    (.write writer (if must-quote\n\t\t     (str/escape string\n\t\t\t\t {quote (str quote quote)})\n\t\t     string))\n    (when must-quote (.write writer (int quote)))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["writer" "obj" "sep" "quote" "quote?"]]}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "lf",
    "line" 19,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} lf  (int \\newline))",
    "file" "clojure/data/csv.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "write-csv*",
    "line" 115,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-csv*\n  [^Writer writer records sep quote quote? ^String newline]\n  (loop [records records]\n    (when-first [record records]\n      (write-record writer record sep quote quote?)\n      (.write writer newline)\n      (recur (next records)))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["writer" "records" "sep" "quote" "quote?" "newline"]]}
   {"ns" "clojure.data.csv",
    "name" "Read-CSV-From",
    "line" 66,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol Read-CSV-From\n  (read-csv-from [input sep quote]))",
    "file" "clojure/data/csv.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "cr",
    "line" 20,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:private true} cr  (int \\return))",
    "file" "clojure/data/csv.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "read-record",
    "line" 58,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-record [reader sep quote]\n  (loop [record (transient [])]\n    (let [cell (StringBuilder.)\n          sentinel (read-cell reader cell sep quote)]\n      (if (= sentinel :sep)\n        (recur (conj! record (str cell)))\n        [(persistent! (conj! record (str cell))) sentinel]))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["reader" "sep" "quote"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.csv",
    "doc" nil,
    "arglists" [["input" "sep" "quote"]],
    "name" "read-csv-from"}
   {"private" true,
    "ns" "clojure.data.csv",
    "name" "read-cell",
    "line" 41,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-cell [^Reader reader ^StringBuilder sb sep quote]\n  (let [first-ch (.read reader)]\n    (if (== first-ch quote)\n      (read-quoted-cell reader sb sep quote)\n      (loop [ch first-ch]\n        (condp == ch\n          sep :sep\n          lf  :eol\n          cr (let [next-ch (.read reader)]\n               (if (== next-ch lf)\n                 :eol\n                 (do (.append sb \\return)\n                     (recur next-ch))))\n          eof :eof\n          (do (.append sb (char ch))\n              (recur (.read reader))))))))",
    "file" "clojure/data/csv.clj",
    "arglists" [["reader" "sb" "sep" "quote"]]}]},
 "description" "data.csv 0.1.2",
 "version" "0.1.2",
 "name" "clojure.data.csv",
 "group" "clojure.data.csv"}
