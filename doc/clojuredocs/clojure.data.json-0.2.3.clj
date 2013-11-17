{"namespaces"
 {"clojure.data.json"
  [{"ns" "clojure.data.json",
    "name" "json-str",
    "line" 42,
    "column" 1,
    "doc"
    "DEPRECATED; replaced by 'write-str'.\n\n  Converts x to a JSON-formatted string.\n\n  Valid options are:\n    :escape-unicode false\n        to turn of \\uXXXX escapes of Unicode characters.",
    "tag" nil,
    "source"
    "(defn json-str\n  \"DEPRECATED; replaced by 'write-str'.\n\n  Converts x to a JSON-formatted string.\n\n  Valid options are:\n    :escape-unicode false\n        to turn of \\\\uXXXX escapes of Unicode characters.\"\n  [x & options]\n  (apply write-str x options))",
    "file" "clojure/data/json_compat_0_1.clj",
    "arglists" [["x" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-named",
    "line" 361,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-named [x out]\n  (write-string (name x) out))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "pprint-array",
    "line" 464,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pprint-array [s] \n  ((pprint/formatter-out \"~<[~;~@{~w~^, ~:_~}~;]~:>\") s))",
    "file" "clojure/data/json.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "pprint-object",
    "line" 467,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pprint-object [m]\n  ((pprint/formatter-out \"~<{~;~@{~<~w:~_~w~:>~^, ~_~}~;}~:>\") \n   (for [[k v] m] [(*key-fn* k) v])))",
    "file" "clojure/data/json.clj",
    "arglists" [["m"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "*escape-unicode*",
    "line" 280,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} *escape-unicode*)",
    "file" "clojure/data/json.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-decimal",
    "line" 153,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-decimal [^String string]\n  (if *bigdec*\n    (bigdec string)\n    (Double/valueOf string)))",
    "file" "clojure/data/json.clj",
    "arglists" [["string"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "*escape-js-separators*",
    "line" 281,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^{:dynamic true :private true} *escape-js-separators*)",
    "file" "clojure/data/json.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "*bigdec*",
    "line" 20,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} *bigdec*)",
    "file" "clojure/data/json.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.data.json",
    "name" "read",
    "line" 226,
    "column" 1,
    "doc"
    "Reads a single item of JSON data from a java.io.Reader. Options are\n  key-value pairs, valid options are:\n\n     :eof-error? boolean\n\n        If true (default) will throw exception if the stream is empty.\n\n     :eof-value Object\n\n        Object to return if the stream is empty and eof-error? is\n        false. Default is nil.\n\n     :bigdec boolean\n\n        If true use BigDecimal for decimal numbers instead of Double.\n        Default is false.\n\n     :key-fn function\n\n        Single-argument function called on JSON property names; return\n        value will replace the property names in the output. Default\n        is clojure.core/identity, use clojure.core/keyword to get\n        keyword properties.\n\n     :value-fn function\n\n        Function to transform values in maps (\"objects\" in JSON) in\n        the output. For each JSON property, value-fn is called with\n        two arguments: the property name (transformed by key-fn) and\n        the value. The return value of value-fn will replace the value\n        in the output. If value-fn returns itself, the property will\n        be omitted from the output. The default value-fn returns the\n        value unchanged. This option does not apply to non-map\n        collections.",
    "tag" nil,
    "source"
    "(defn read\n  \"Reads a single item of JSON data from a java.io.Reader. Options are\n  key-value pairs, valid options are:\n\n     :eof-error? boolean\n\n        If true (default) will throw exception if the stream is empty.\n\n     :eof-value Object\n\n        Object to return if the stream is empty and eof-error? is\n        false. Default is nil.\n\n     :bigdec boolean\n\n        If true use BigDecimal for decimal numbers instead of Double.\n        Default is false.\n\n     :key-fn function\n\n        Single-argument function called on JSON property names; return\n        value will replace the property names in the output. Default\n        is clojure.core/identity, use clojure.core/keyword to get\n        keyword properties.\n\n     :value-fn function\n\n        Function to transform values in maps (\\\"objects\\\" in JSON) in\n        the output. For each JSON property, value-fn is called with\n        two arguments: the property name (transformed by key-fn) and\n        the value. The return value of value-fn will replace the value\n        in the output. If value-fn returns itself, the property will\n        be omitted from the output. The default value-fn returns the\n        value unchanged. This option does not apply to non-map\n        collections.\"\n  [reader & options]\n  (let [{:keys [eof-error? eof-value bigdec key-fn value-fn]\n         :or {bigdec false\n              eof-error? true\n              key-fn identity\n              value-fn default-value-fn}} options]\n    (binding [*bigdec* bigdec\n              *key-fn* key-fn\n              *value-fn* value-fn]\n      (-read (PushbackReader. reader) eof-error? eof-value))))",
    "file" "clojure/data/json.clj",
    "arglists" [["reader" "&" "options"]]}
   {"ns" "clojure.data.json",
    "name" "write-json",
    "line" 35,
    "column" 1,
    "doc"
    "DEPRECATED; replaced by 'write'.\n\n  Print object to PrintWriter out as JSON",
    "tag" nil,
    "source"
    "(defn write-json\n  \"DEPRECATED; replaced by 'write'.\n\n  Print object to PrintWriter out as JSON\"\n  [x out escape-unicode?]\n  (write x out :escape-unicode escape-unicode?))",
    "file" "clojure/data/json_compat_0_1.clj",
    "arglists" [["x" "out" "escape-unicode?"]]}
   {"ns" "clojure.data.json",
    "name" "write-str",
    "line" 452,
    "column" 1,
    "doc"
    "Converts x to a JSON-formatted string. Options are the same as\n  write.",
    "tag" nil,
    "source"
    "(defn write-str\n  \"Converts x to a JSON-formatted string. Options are the same as\n  write.\"\n  [x & options]\n  (let [sw (StringWriter.)]\n    (apply write x sw options)\n    (.toString sw)))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "codepoint",
    "macro" true,
    "line" 36,
    "column" 1,
    "tag" nil,
    "source" "(defmacro ^:private codepoint [c]\n  (int c))",
    "file" "clojure/data/json.clj",
    "arglists" [["c"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "-read",
    "line" 175,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- -read\n  [^PushbackReader stream eof-error? eof-value]\n  (loop []\n    (let [c (.read stream)]\n      (if (neg? c) ;; Handle end-of-stream\n        (if eof-error?\n          (throw (EOFException. \"JSON error (end-of-file)\"))\n          eof-value)\n        (codepoint-case\n          c\n          :whitespace (recur)\n\n          ;; Read numbers\n          (\\- \\0 \\1 \\2 \\3 \\4 \\5 \\6 \\7 \\8 \\9)\n          (do (.unread stream c)\n              (read-number stream))\n\n          ;; Read strings\n          \\\" (read-quoted-string stream)\n\n          ;; Read null as nil\n          \\n (if (and (= (codepoint \\u) (.read stream))\n                      (= (codepoint \\l) (.read stream))\n                      (= (codepoint \\l) (.read stream)))\n               nil\n               (throw (Exception. \"JSON error (expected null)\")))\n\n          ;; Read true\n          \\t (if (and (= (codepoint \\r) (.read stream))\n                      (= (codepoint \\u) (.read stream))\n                      (= (codepoint \\e) (.read stream)))\n               true\n               (throw (Exception. \"JSON error (expected true)\")))\n\n          ;; Read false\n          \\f (if (and (= (codepoint \\a) (.read stream))\n                      (= (codepoint \\l) (.read stream))\n                      (= (codepoint \\s) (.read stream))\n                      (= (codepoint \\e) (.read stream)))\n               false\n               (throw (Exception. \"JSON error (expected false)\")))\n\n          ;; Read JSON objects\n          \\{ (read-object stream)\n\n          ;; Read JSON arrays\n          \\[ (read-array stream)\n\n          (throw (Exception.\n                  (str \"JSON error (unexpected character): \" (char c)))))))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream" "eof-error?" "eof-value"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "*escape-slash*",
    "line" 282,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} *escape-slash*)",
    "file" "clojure/data/json.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.data.json",
    "name" "JSONWriter",
    "line" 284,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol JSONWriter\n  (-write [object out]\n    \"Print object to PrintWriter out as JSON\"))",
    "file" "clojure/data/json.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-plain",
    "line" 355,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-plain [x ^PrintWriter out]\n  (.print out x))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "codepoint-case",
    "macro" true,
    "line" 53,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private codepoint-case [e & clauses]\n  `(case ~e\n     ~@(mapcat codepoint-clause (partition 2 clauses))\n     ~@(when (odd? (count clauses))\n         [(last clauses)])))",
    "file" "clojure/data/json.clj",
    "arglists" [["e" "&" "clauses"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "pprint-generic",
    "line" 471,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pprint-generic [x]\n  (if (.isArray (class x))\n    (pprint-array (seq x))\n    ;; pprint proxies Writer, so we can't just wrap it\n    (print (with-out-str (-write x (PrintWriter. *out*))))))",
    "file" "clojure/data/json.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.data.json",
    "name" "read-str",
    "line" 272,
    "column" 1,
    "doc"
    "Reads one JSON value from input String. Options are the same as for\n  read.",
    "tag" nil,
    "source"
    "(defn read-str\n  \"Reads one JSON value from input String. Options are the same as for\n  read.\"\n  [string & options]\n  (apply read (StringReader. string) options))",
    "file" "clojure/data/json.clj",
    "arglists" [["string" "&" "options"]]}
   {"ns" "clojure.data.json",
    "name" "pprint-json",
    "line" 64,
    "column" 1,
    "doc"
    "DEPRECATED; replaced by 'pprint'.\n\n  Pretty-prints JSON representation of x to *out*.\n\n  Valid options are:\n    :escape-unicode false\n        to turn off \\uXXXX escapes of Unicode characters.",
    "tag" nil,
    "source"
    "(defn pprint-json\n  \"DEPRECATED; replaced by 'pprint'.\n\n  Pretty-prints JSON representation of x to *out*.\n\n  Valid options are:\n    :escape-unicode false\n        to turn off \\\\uXXXX escapes of Unicode characters.\"\n  [x & options]\n  (apply pprint x options))",
    "file" "clojure/data/json_compat_0_1.clj",
    "arglists" [["x" "&" "options"]]}
   {"ns" "clojure.data.json",
    "name" "write",
    "line" 399,
    "column" 1,
    "doc"
    "Write JSON-formatted output to a java.io.Writer. Options are\n   key-value pairs, valid options are:\n\n    :escape-unicode boolean\n\n       If true (default) non-ASCII characters are escaped as \\uXXXX\n\n    :escape-js-separators boolean\n\n       If true (default) the Unicode characters U+2028 and U+2029 will\n       be escaped as \\u2028 and \\u2029 even if :escape-unicode is\n       false. (These two characters are valid in pure JSON but are not\n       valid in JavaScript strings.)\n\n    :escape-slash boolean\n\n       If true (default) the slash / is escaped as \\/\n\n    :key-fn function\n\n        Single-argument function called on map keys; return value will\n        replace the property names in the output. Must return a\n        string. Default calls clojure.core/name on symbols and\n        keywords and clojure.core/str on everything else.\n\n    :value-fn function\n\n        Function to transform values in maps before writing. For each\n        key-value pair in an input map, called with two arguments: the\n        key (BEFORE transformation by key-fn) and the value. The\n        return value of value-fn will replace the value in the output.\n        If the return value is a number, boolean, string, or nil it\n        will be included literally in the output. If the return value\n        is a non-map collection, it will be processed recursively. If\n        the return value is a map, it will be processed recursively,\n        calling value-fn again on its key-value pairs. If value-fn\n        returns itself, the key-value pair will be omitted from the\n        output. This option does not apply to non-map collections.",
    "tag" nil,
    "source"
    "(defn write\n  \"Write JSON-formatted output to a java.io.Writer. Options are\n   key-value pairs, valid options are:\n\n    :escape-unicode boolean\n\n       If true (default) non-ASCII characters are escaped as \\\\uXXXX\n\n    :escape-js-separators boolean\n\n       If true (default) the Unicode characters U+2028 and U+2029 will\n       be escaped as \\\\u2028 and \\\\u2029 even if :escape-unicode is\n       false. (These two characters are valid in pure JSON but are not\n       valid in JavaScript strings.)\n\n    :escape-slash boolean\n\n       If true (default) the slash / is escaped as \\\\/\n\n    :key-fn function\n\n        Single-argument function called on map keys; return value will\n        replace the property names in the output. Must return a\n        string. Default calls clojure.core/name on symbols and\n        keywords and clojure.core/str on everything else.\n\n    :value-fn function\n\n        Function to transform values in maps before writing. For each\n        key-value pair in an input map, called with two arguments: the\n        key (BEFORE transformation by key-fn) and the value. The\n        return value of value-fn will replace the value in the output.\n        If the return value is a number, boolean, string, or nil it\n        will be included literally in the output. If the return value\n        is a non-map collection, it will be processed recursively. If\n        the return value is a map, it will be processed recursively,\n        calling value-fn again on its key-value pairs. If value-fn\n        returns itself, the key-value pair will be omitted from the\n        output. This option does not apply to non-map collections.\"\n  [x ^Writer writer & options]\n  (let [{:keys [escape-unicode escape-js-separators escape-slash key-fn value-fn]\n         :or {escape-unicode true\n              escape-js-separators true\n              escape-slash true\n              key-fn default-write-key-fn\n              value-fn default-value-fn}} options]\n    (binding [*escape-unicode* escape-unicode\n              *escape-js-separators* escape-js-separators\n              *escape-slash* escape-slash\n              *key-fn* key-fn\n              *value-fn* value-fn]\n      (-write x (PrintWriter. writer)))))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "writer" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-number",
    "line" 158,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-number [^PushbackReader stream]\n  (let [buffer (StringBuilder.)\n        decimal? (loop [decimal? false]\n                   (let [c (.read stream)]\n                     (codepoint-case c\n                       (\\- \\+ \\0 \\1 \\2 \\3 \\4 \\5 \\6 \\7 \\8 \\9)\n                       (do (.append buffer (char c))\n                           (recur decimal?))\n                       (\\e \\E \\.)\n                       (do (.append buffer (char c))\n                           (recur true))\n                       (do (.unread stream c)\n                           decimal?))))]\n    (if decimal?\n      (read-decimal (str buffer))\n      (read-integer (str buffer)))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "pprint-dispatch",
    "line" 477,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pprint-dispatch [x]\n  (cond (nil? x) (print \"null\")\n        (instance? java.util.Map x) (pprint-object x)\n        (instance? java.util.Collection x) (pprint-array x)\n        (instance? clojure.lang.ISeq x) (pprint-array x)\n        :else (pprint-generic x)))",
    "file" "clojure/data/json.clj",
    "arglists" [["x"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "*key-fn*",
    "line" 21,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} *key-fn*)",
    "file" "clojure/data/json.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-escaped-char",
    "line" 118,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-escaped-char [^PushbackReader stream]\n  ;; Expects to be called with the head of the stream AFTER the\n  ;; initial backslash.\n  (let [c (.read stream)]\n    (codepoint-case c\n      (\\\" \\\\ \\/) (char c)\n      \\b \\backspace\n      \\f \\formfeed\n      \\n \\newline\n      \\r \\return\n      \\t \\tab\n      \\u (read-hex-char stream))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-bignum",
    "line" 352,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-bignum [x ^PrintWriter out]\n  (.print out (str x)))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-null",
    "line" 358,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-null [x ^PrintWriter out]\n  (.print out \"null\"))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-array",
    "line" 59,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-array [^PushbackReader stream]\n  ;; Expects to be called with the head of the stream AFTER the\n  ;; opening bracket.\n  (loop [result (transient [])]\n    (let [c (.read stream)]\n      (when (neg? c)\n        (throw (EOFException. \"JSON error (end-of-file inside array)\")))\n      (codepoint-case c\n        :whitespace (recur result)\n        \\, (recur result)\n        \\] (persistent! result)\n        (do (.unread stream c)\n            (let [element (-read stream true nil)]\n              (recur (conj! result element))))))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream"]]}
   {"ns" "clojure.data.json",
    "name" "print-json",
    "line" 53,
    "column" 1,
    "doc"
    "DEPRECATED; replaced by 'write' to *out*.\n\n  Write JSON-formatted output to *out*.\n\n  Valid options are:\n    :escape-unicode false\n        to turn off \\uXXXX escapes of Unicode characters.",
    "tag" nil,
    "source"
    "(defn print-json\n  \"DEPRECATED; replaced by 'write' to *out*.\n\n  Write JSON-formatted output to *out*.\n\n  Valid options are:\n    :escape-unicode false\n        to turn off \\\\uXXXX escapes of Unicode characters.\"\n  [x & options]\n  (apply write x *out* options))",
    "file" "clojure/data/json_compat_0_1.clj",
    "arglists" [["x" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "default-write-key-fn",
    "line" 24,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- default-write-key-fn\n  [x]\n  (cond (instance? clojure.lang.Named x)\n        (name x)\n        (nil? x)\n        (throw (Exception. \"JSON object properties may not be nil\"))\n        :else (str x)))",
    "file" "clojure/data/json.clj",
    "arglists" [["x"]]}
   {"ns" "clojure.data.json",
    "name" "pprint",
    "line" 484,
    "column" 1,
    "doc"
    "Pretty-prints JSON representation of x to *out*. Options are the\n  same as for write except :value-fn, which is not supported.",
    "tag" nil,
    "source"
    "(defn pprint\n  \"Pretty-prints JSON representation of x to *out*. Options are the\n  same as for write except :value-fn, which is not supported.\"\n  [x & options]\n  (let [{:keys [escape-unicode escape-slash key-fn]\n         :or {escape-unicode true\n              escape-slash true\n              key-fn default-write-key-fn}} options]\n    (binding [*escape-unicode* escape-unicode\n              *escape-slash* escape-slash\n              *key-fn* key-fn]\n      (pprint/write x :dispatch pprint-dispatch))))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "&" "options"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "default-value-fn",
    "line" 32,
    "column" 1,
    "tag" nil,
    "source" "(defn- default-value-fn [k v] v)",
    "file" "clojure/data/json.clj",
    "arglists" [["k" "v"]]}
   {"ns" "clojure.data.json",
    "name" "read-json",
    "line" 10,
    "column" 1,
    "doc"
    "DEPRECATED; replaced by read-str.\n\n  Reads one JSON value from input String or Reader. If keywordize? is\n  true (default), object keys will be converted to keywords. If\n  eof-error? is true (default), empty input will throw an\n  EOFException; if false EOF will return eof-value.",
    "tag" nil,
    "source"
    "(defn read-json\n  \"DEPRECATED; replaced by read-str.\n\n  Reads one JSON value from input String or Reader. If keywordize? is\n  true (default), object keys will be converted to keywords. If\n  eof-error? is true (default), empty input will throw an\n  EOFException; if false EOF will return eof-value.\"\n  ([input]\n     (read-json input true true nil))\n  ([input keywordize?]\n     (read-json input keywordize? true nil))\n  ([input keywordize? eof-error? eof-value]\n     (let [key-fn (if keywordize? keyword identity)]\n       (condp instance? input\n         String\n         (read-str input\n                   :key-fn key-fn\n                   :eof-error? eof-error?\n                   :eof-value eof-value)\n         java.io.Reader\n         (read input\n               :key-fn key-fn\n               :eof-error? eof-error?\n               :eof-value eof-value)))))",
    "file" "clojure/data/json_compat_0_1.clj",
    "arglists"
    [["input"]
     ["input" "keywordize?"]
     ["input" "keywordize?" "eof-error?" "eof-value"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-string",
    "line" 288,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-string [^CharSequence s ^PrintWriter out]\n  (let [sb (StringBuilder. (count s))]\n    (.append sb \\\")\n    (dotimes [i (count s)]\n      (let [cp (int (.charAt s i))]\n        (codepoint-case cp\n          ;; Printable JSON escapes\n          \\\" (.append sb \"\\\\\\\"\")\n          \\\\ (.append sb \"\\\\\\\\\")\n          \\/ (.append sb (if *escape-slash* \"\\\\/\" \"/\"))\n          ;; Simple ASCII characters\n          :simple-ascii (.append sb (.charAt s i))\n          ;; JSON escapes\n          \\backspace (.append sb \"\\\\b\")\n          \\formfeed  (.append sb \"\\\\f\")\n          \\newline   (.append sb \"\\\\n\")\n          \\return    (.append sb \"\\\\r\")\n          \\tab       (.append sb \"\\\\t\")\n          ;; Unicode characters that Javascript forbids raw in strings\n          :js-separators (if *escape-js-separators*\n                           (.append sb (format \"\\\\u%04x\" cp))\n                           (.appendCodePoint sb cp))\n          ;; Any other character is Unicode\n          (if *escape-unicode*\n            (.append sb (format \"\\\\u%04x\" cp)) ; Hexadecimal-escaped\n            (.appendCodePoint sb cp)))))\n    (.append sb \\\")\n    (.print out (str sb))))",
    "file" "clojure/data/json.clj",
    "arglists" [["s" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-object",
    "line" 74,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-object [^PushbackReader stream]\n  ;; Expects to be called with the head of the stream AFTER the\n  ;; opening bracket.\n  (loop [key nil, result (transient {})]\n    (let [c (.read stream)]\n      (when (neg? c)\n        (throw (EOFException. \"JSON error (end-of-file inside object)\")))\n      (codepoint-case c\n        :whitespace (recur key result)\n\n        \\, (recur nil result)\n\n        \\: (recur key result)\n\n        \\} (if (nil? key)\n             (persistent! result)\n             (throw (Exception. \"JSON error (key missing value in object)\")))\n\n        (do (.unread stream c)\n            (let [element (-read stream true nil)]\n              (if (nil? key)\n                (if (string? element)\n                  (recur element result)\n                  (throw (Exception. \"JSON error (non-string key in object)\")))\n                (recur nil\n                       (let [out-key (*key-fn* key)\n                             out-value (*value-fn* out-key element)]\n                         (if (= *value-fn* out-value)\n                           result\n                           (assoc! result out-key out-value)))))))))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-array",
    "line" 340,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-array [s ^PrintWriter out]\n  (.print out \\[)\n  (loop [x s]\n    (when (seq x)\n      (let [fst (first x)\n            nxt (next x)]\n        (-write fst out)\n        (when (seq nxt)\n          (.print out \\,)\n          (recur nxt)))))\n  (.print out \\]))",
    "file" "clojure/data/json.clj",
    "arglists" [["s" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-object",
    "line" 317,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-object [m ^PrintWriter out] \n  (.print out \\{)\n  (loop [x m, have-printed-kv false]\n    (when (seq m)\n      (let [[k v] (first x)\n            out-key (*key-fn* k)\n            out-value (*value-fn* k v)\n            nxt (next x)]\n        (when-not (string? out-key)\n          (throw (Exception. \"JSON object keys must be strings\")))\n        (if-not (= *value-fn* out-value)\n          (do\n            (when have-printed-kv\n              (.print out \\,))\n            (write-string out-key out)\n            (.print out \\:)\n            (-write out-value out)\n            (when (seq nxt)\n              (recur nxt true)))\n          (when (seq nxt)\n            (recur nxt have-printed-kv))))))\n  (.print out \\}))",
    "file" "clojure/data/json.clj",
    "arglists" [["m" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-generic",
    "line" 364,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- write-generic [x out]\n  (if (.isArray (class x))\n    (-write (seq x) out)\n    (throw (Exception. (str \"Don't know how to write JSON of \" (class x))))))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "*value-fn*",
    "line" 22,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true :private true} *value-fn*)",
    "file" "clojure/data/json.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-integer",
    "line" 146,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-integer [^String string]\n  (if (< (count string) 18)  ; definitely fits in a Long\n    (Long/valueOf string)\n    (or (try (Long/valueOf string)\n             (catch NumberFormatException e nil))\n        (bigint string))))",
    "file" "clojure/data/json.clj",
    "arglists" [["string"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "write-ratio",
    "line" 369,
    "column" 1,
    "tag" nil,
    "source" "(defn- write-ratio [x out]\n  (-write (double x) out))",
    "file" "clojure/data/json.clj",
    "arglists" [["x" "out"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "codepoint-clause",
    "line" 39,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- codepoint-clause [[test result]]\n  (cond (list? test)\n        [(map int test) result]\n        (= test :whitespace)\n        ['(9 10 13 32) result]\n        (= test :simple-ascii)\n        [(remove #{(codepoint \\\") (codepoint \\\\) (codepoint \\/)}\n                 (range 32 127))\n         result]\n        (= test :js-separators)\n        ['(16r2028 16r2029) result]\n        :else\n        [(int test) result]))",
    "file" "clojure/data/json.clj",
    "arglists" [[["test" "result"]]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-quoted-string",
    "line" 131,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-quoted-string [^PushbackReader stream]\n  ;; Expects to be called with the head of the stream AFTER the\n  ;; opening quotation mark.\n  (let [buffer (StringBuilder.)]\n    (loop []\n      (let [c (.read stream)]\n        (when (neg? c)\n          (throw (EOFException. \"JSON error (end-of-file inside string)\")))\n        (codepoint-case c\n          \\\" (str buffer)\n          \\\\ (do (.append buffer (read-escaped-char stream))\n                 (recur))\n          (do (.append buffer (char c))\n              (recur)))))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream"]]}
   {"private" true,
    "ns" "clojure.data.json",
    "name" "read-hex-char",
    "line" 105,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-hex-char [^PushbackReader stream]\n  ;; Expects to be called with the head of the stream AFTER the\n  ;; initial \"\\u\".  Reads the next four characters from the stream.\n  (let [a (.read stream)\n        b (.read stream)\n        c (.read stream)\n        d (.read stream)]\n    (when (or (neg? a) (neg? b) (neg? c) (neg? d))\n      (throw (EOFException.\n              \"JSON error (end-of-file inside Unicode character escape)\")))\n    (let [s (str (char a) (char b) (char c) (char d))]\n      (char (Integer/parseInt s 16)))))",
    "file" "clojure/data/json.clj",
    "arglists" [["stream"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.json",
    "doc" "Print object to PrintWriter out as JSON",
    "arglists" [["object" "out"]],
    "name" "-write"}]},
 "description" "data.json 0.2.3",
 "version" "0.2.3",
 "name" "clojure.data.json",
 "group" "clojure.data.json"}
