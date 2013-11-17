{"namespaces"
 {"clojure.tools.reader.reader-types"
  [{"source" nil,
    "tag" nil,
    "ns" "clojure.tools.reader.reader-types",
    "doc"
    "Returns the line number of the next character to be read from the stream",
    "arglists" [["reader"]],
    "name" "get-line-number"}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "read-line",
    "line" 212,
    "column" 1,
    "doc"
    "Reads a line from the reader or from *in* if no reader is specified",
    "tag" nil,
    "source"
    "(defn read-line\n  \"Reads a line from the reader or from *in* if no reader is specified\"\n  ([] (read-line *in*))\n  ([rdr]\n     (if (or (instance? LineNumberingPushbackReader rdr)\n             (instance? BufferedReader rdr))\n       (clojure.core/read-line rdr)\n       (loop [c (read-char rdr) s (StringBuilder.)]\n         (if (newline? c)\n           (str s)\n           (recur (read-char rdr) (.append s c)))))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [[] ["rdr"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "IPushbackReader",
    "line" 30,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IPushbackReader\n  (unread [reader ch]\n    \"Pushes back a single character on to the stream\"))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" nil}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "reader-error",
    "line" 224,
    "column" 1,
    "doc"
    "Throws an ExceptionInfo with the given message.\n   If rdr is an IndexingReader, additional information about column and line number is provided",
    "tag" nil,
    "source"
    "(defn reader-error\n  \"Throws an ExceptionInfo with the given message.\n   If rdr is an IndexingReader, additional information about column and line number is provided\"\n  [rdr & msg]\n  (throw (ex-info (apply str msg)\n                  (merge {:type :reader-exception}\n                         (when (indexing-reader? rdr)\n                           (merge\n                            {:line (get-line-number rdr)\n                             :column (get-column-number rdr)}\n                            (when-let [file-name (get-file-name rdr)]\n                              {:file file-name})))))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["rdr" "&" "msg"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "->IndexingPushbackReader",
    "line" 107,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.tools.reader.reader_types.IndexingPushbackReader.",
    "tag" nil,
    "source"
    "(deftype IndexingPushbackReader\n    [rdr ^:unsynchronized-mutable line ^:unsynchronized-mutable column\n     ^:unsynchronized-mutable line-start? ^:unsynchronized-mutable prev file-name]\n  Reader\n  (read-char [reader]\n    (when-let [ch (read-char rdr)]\n      (let [ch (normalize-newline rdr ch)]\n        (set! prev line-start?)\n        (set! line-start? (newline? ch))\n        (when line-start?\n          (set! column 0)\n          (update! line inc))\n        (update! column inc)\n        ch)))\n\n  (peek-char [reader]\n    (peek-char rdr))\n\n  IPushbackReader\n  (unread [reader ch]\n    (when line-start? (update! line dec))\n    (set! line-start? prev)\n    (update! column dec)\n    (unread rdr ch))\n\n  IndexingReader\n  (get-line-number [reader] (int (inc line)))\n  (get-column-number [reader] (int column))\n  (get-file-name [reader] file-name))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists"
    [["rdr" "line" "column" "line-start?" "prev" "file-name"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "string-reader",
    "line" 178,
    "column" 1,
    "doc" "Creates a StringReader from a given string",
    "tag" nil,
    "source"
    "(defn string-reader\n  \"Creates a StringReader from a given string\"\n  ([^String s]\n     (StringReader. s (count s) 0)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "string-push-back-reader",
    "line" 183,
    "column" 1,
    "doc" "Creates a PushbackReader from a given string",
    "tag" nil,
    "source"
    "(defn string-push-back-reader\n  \"Creates a PushbackReader from a given string\"\n  ([s]\n     (string-push-back-reader s 1))\n  ([^String s buf-len]\n     (PushbackReader. (string-reader s) (object-array buf-len) buf-len buf-len)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["s"] ["s" "buf-len"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.reader.reader-types",
    "doc"
    "Returns the next char from the Reader, nil if the end of stream has been reached",
    "arglists" [["reader"]],
    "name" "read-char"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.reader.reader-types",
    "doc" "Pushes back a single character on to the stream",
    "arglists" [["reader" "ch"]],
    "name" "unread"}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "indexing-push-back-reader",
    "line" 202,
    "column" 1,
    "doc"
    "Creates an IndexingPushbackReader from a given string or Reader",
    "tag" nil,
    "source"
    "(defn indexing-push-back-reader\n  \"Creates an IndexingPushbackReader from a given string or Reader\"\n  ([s-or-rdr]\n     (indexing-push-back-reader s-or-rdr 1))\n  ([s-or-rdr buf-len]\n     (indexing-push-back-reader s-or-rdr buf-len nil))\n  ([s-or-rdr buf-len file-name]\n     (IndexingPushbackReader.\n      (if (string? s-or-rdr) (string-push-back-reader s-or-rdr buf-len) s-or-rdr) 0 1 true nil file-name)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists"
    [["s-or-rdr"]
     ["s-or-rdr" "buf-len"]
     ["s-or-rdr" "buf-len" "file-name"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "indexing-reader?",
    "line" 168,
    "column" 1,
    "doc" "Returns true if the reader satisfies IndexingReader",
    "tag" nil,
    "source"
    "(defn indexing-reader?\n  \"Returns true if the reader satisfies IndexingReader\"\n  [rdr]\n  (or (instance? clojure.tools.reader.reader_types.IndexingReader rdr)\n      (instance? LineNumberingPushbackReader rdr)\n      (and (not (instance? clojure.tools.reader.reader_types.PushbackReader rdr))\n           (not (instance? clojure.tools.reader.reader_types.StringReader rdr))\n           (not (instance? clojure.tools.reader.reader_types.InputStreamReader rdr))\n           (get (:impls IndexingReader) (class rdr)))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["rdr"]]}
   {"private" true,
    "ns" "clojure.tools.reader.reader-types",
    "name" "update!",
    "macro" true,
    "line" 17,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private update! [what f]\n  (list 'set! what (list f what)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["what" "f"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "input-stream-push-back-reader",
    "line" 195,
    "column" 1,
    "doc" "Creates a PushbackReader from a given InputStream",
    "tag" nil,
    "source"
    "(defn input-stream-push-back-reader\n  \"Creates a PushbackReader from a given InputStream\"\n  ([is]\n     (input-stream-push-back-reader is 1))\n  ([^InputStream is buf-len]\n     (PushbackReader. (input-stream-reader is) (object-array buf-len) buf-len buf-len)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["is"] ["is" "buf-len"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "Reader",
    "line" 24,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol Reader\n  (read-char [reader]\n    \"Returns the next char from the Reader, nil if the end of stream has been reached\")\n  (peek-char [reader]\n    \"Returns the next char from the Reader without removing it from the reader stream\"))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.reader.reader-types",
    "doc"
    "Returns the next char from the Reader without removing it from the reader stream",
    "arglists" [["reader"]],
    "name" "peek-char"}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "->PushbackReader",
    "line" 76,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.tools.reader.reader_types.PushbackReader.",
    "tag" nil,
    "source"
    "(deftype PushbackReader\n    [rdr ^\"[Ljava.lang.Object;\" buf buf-len ^:unsynchronized-mutable buf-pos]\n  Reader\n  (read-char [reader]\n    (char\n     (if (< buf-pos buf-len)\n       (let [r (aget buf buf-pos)]\n         (update! buf-pos inc)\n         r)\n       (read-char rdr))))\n  (peek-char [reader]\n    (char\n     (if (< buf-pos buf-len)\n       (aget buf buf-pos)\n       (peek-char rdr))))\n  IPushbackReader\n  (unread [reader ch]\n    (when ch\n      (if (zero? buf-pos) (throw (RuntimeException. \"Pushback buffer is full\")))\n      (update! buf-pos dec)\n      (aset buf buf-pos ch))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["rdr" "buf" "buf-len" "buf-pos"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "input-stream-reader",
    "line" 190,
    "column" 1,
    "doc" "Creates an InputStreamReader from an InputStream",
    "tag" nil,
    "source"
    "(defn input-stream-reader\n  \"Creates an InputStreamReader from an InputStream\"\n  [is]\n  (InputStreamReader. is nil))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["is"]]}
   {"private" true,
    "ns" "clojure.tools.reader.reader-types",
    "name" "normalize-newline",
    "line" 98,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- normalize-newline [rdr ch]\n  (if (identical? \\return ch)\n    (let [c (peek-char rdr)]\n      (when (or (identical? \\formfeed c)\n                (identical? \\newline c))\n        (read-char rdr))\n      \\newline)\n    ch))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["rdr" "ch"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.reader.reader-types",
    "doc"
    "Returns the line number of the next character to be read from the stream",
    "arglists" [["reader"]],
    "name" "get-column-number"}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "->InputStreamReader",
    "line" 58,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.tools.reader.reader_types.InputStreamReader.",
    "tag" nil,
    "source"
    "(deftype InputStreamReader [^InputStream is ^:unsynchronized-mutable ^\"[B\" buf]\n  Reader\n  (read-char [reader]\n    (if buf\n      (let [c (aget buf 0)]\n        (set! buf nil)\n        (char c))\n      (let [c (.read is)]\n        (when (>= c 0)\n          (char c)))))\n  (peek-char [reader]\n    (when-not buf\n      (set! buf (byte-array 1))\n      (when (== -1 (.read is buf))\n        (set! buf nil)))\n    (when buf\n      (char (aget buf 0)))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["is" "buf"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "IndexingReader",
    "line" 34,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IndexingReader\n  (get-line-number [reader]\n    \"Returns the line number of the next character to be read from the stream\")\n  (get-column-number [reader]\n    \"Returns the line number of the next character to be read from the stream\")\n  (get-file-name [reader]\n    \"Returns the file name the reader is reading from, or nil\"))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.reader.reader-types",
    "doc" "Returns the file name the reader is reading from, or nil",
    "arglists" [["reader"]],
    "name" "get-file-name"}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "->StringReader",
    "line" 46,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.tools.reader.reader_types.StringReader.",
    "tag" nil,
    "source"
    "(deftype StringReader\n    [^String s s-len ^:unsynchronized-mutable s-pos]\n  Reader\n  (read-char [reader]\n    (when (> s-len s-pos)\n      (let [r (nth s s-pos)]\n        (update! s-pos inc)\n        r)))\n  (peek-char [reader]\n    (when (> s-len s-pos)\n      (nth s s-pos))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["s" "s-len" "s-pos"]]}],
  "clojure.tools.reader.impl.utils"
  [{"ns" "clojure.tools.reader.impl.utils",
    "name" "numeric?",
    "line" 54,
    "column" 1,
    "doc" "Checks whether a given character is numeric",
    "tag" nil,
    "source"
    "(defn numeric?\n  \"Checks whether a given character is numeric\"\n  [^Character ch]\n  (when ch\n    (Character/isDigit ch)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "arglists" [["ch"]]}
   {"arglists" [["x"]],
    "ns" "clojure.tools.reader.impl.utils",
    "name" "char",
    "column" 1,
    "line" 13,
    "source" "(defn char [x]\n  (when x\n    (clojure.core/char x)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"ns" "clojure.tools.reader.impl.utils",
    "name" "whitespace?",
    "line" 47,
    "column" 1,
    "doc" "Checks whether a given character is whitespace",
    "tag" nil,
    "source"
    "(defn whitespace?\n  \"Checks whether a given character is whitespace\"\n  [ch]\n  (when ch\n    (or (Character/isWhitespace ^Character ch)\n        (identical? \\,  ch))))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "arglists" [["ch"]]}
   {"arglists" [["ex"]],
    "ns" "clojure.tools.reader.impl.utils",
    "name" "ex-info?",
    "column" 1,
    "line" 31,
    "source"
    "(compile-if (= 3 (:minor *clojure-version*))\n  (do\n    (defn ex-info\n      ([msg map]\n         (clojure.tools.reader.impl.ExceptionInfo. msg map))\n      ([msg map cause]\n         (clojure.tools.reader.impl.ExceptionInfo. msg map cause)))\n    (defn ex-data\n      [^clojure.tools.reader.impl.ExceptionInfo ex]\n      (.getData ex))\n    (defn ex-info? [ex]\n      (instance? clojure.tools.reader.impl.ExceptionInfo ex)))\n\n    (defn ex-info? [ex]\n      (instance? clojure.lang.ExceptionInfo ex)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.tools.reader.impl.utils",
    "name" "newline?",
    "column" 1,
    "line" 65,
    "source"
    "(defn newline? [c]\n  \"Checks whether the character is a newline\"\n  (or (identical? \\newline c)\n      (nil? c)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"ns" "clojure.tools.reader.impl.utils",
    "name" "comment-prefix?",
    "line" 60,
    "column" 1,
    "doc" "Checks whether the character begins a comment.",
    "tag" nil,
    "source"
    "(defn comment-prefix?\n  \"Checks whether the character begins a comment.\"\n  [ch]\n  (identical? \\;  ch))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "arglists" [["ch"]]}
   {"arglists" [["f"]],
    "ns" "clojure.tools.reader.impl.utils",
    "name" "desugar-meta",
    "column" 1,
    "line" 70,
    "source"
    "(defn desugar-meta\n  [f]\n  (cond\n    (keyword? f) {f true}\n    (symbol? f)  {:tag f}\n    (string? f)  {:tag f}\n    :else        f))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.utils",
    "name" ">=clojure-1-5-alpha*?",
    "column" 1,
    "line" 18,
    "source"
    "(def >=clojure-1-5-alpha*?\n  (let [{:keys [minor qualifier]} *clojure-version*]\n    (or (and (= minor 5)\n             (not= \"alpha\"\n                   (when qualifier\n                     (subs qualifier 0 (dec (count qualifier))))))\n        (> minor 5))))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"ns" "clojure.tools.reader.impl.utils",
    "name" "compile-if",
    "macro" true,
    "line" 26,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro compile-if [cond then else]\n  (if (eval cond)\n    then\n    else))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "arglists" [["cond" "then" "else"]]}],
  "clojure.tools.reader.impl.commons"
  [{"arglists" [["msg"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "throwing-reader",
    "column" 1,
    "line" 124,
    "source"
    "(defn throwing-reader\n  [msg]\n  (fn [rdr & _]\n    (reader-error rdr msg)))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "ratio-pattern",
    "column" 1,
    "line" 46,
    "source"
    "(def ^Pattern ratio-pattern #\"([-+]?[0-9]+)/([0-9]+)\")",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" "java.util.regex.Pattern"}
   {"ns" "clojure.tools.reader.impl.commons",
    "name" "number-literal?",
    "line" 21,
    "column" 1,
    "doc"
    "Checks whether the reader is at the start of a number literal",
    "tag" nil,
    "source"
    "(defn number-literal?\n  \"Checks whether the reader is at the start of a number literal\"\n  [reader initch]\n  (or (numeric? initch)\n      (and (or (identical? \\+ initch) (identical?  \\- initch))\n           (numeric? (peek-char reader)))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["reader" "initch"]]}
   {"arglists" [["token"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "parse-symbol",
    "column" 1,
    "line" 98,
    "source"
    "(defn parse-symbol [^String token]\n  (when-not (or (identical? \"\" token)\n                (not= -1 (.indexOf token \"::\")))\n    (let [ns-idx (.indexOf token \"/\")]\n      (if-let [ns (and (pos? ns-idx)\n                       (subs token 0 ns-idx))]\n        (let [ns-idx (inc ns-idx)]\n          (when-not (== ns-idx (count token))\n            (let [sym (subs token ns-idx)]\n              (when (and (not (numeric? (nth sym 0)))\n                         (not (identical? \"\" sym))\n                         (or (= sym \"/\")\n                             (== -1 (.indexOf sym \"/\"))))\n                [ns sym]))))\n        (when (or (= token \"/\")\n                  (== -1 (.indexOf token \"/\")))\n            [nil token])))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" [["rdr" "&" "_"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "read-comment",
    "column" 1,
    "line" 120,
    "source" "(defn read-comment\n  [rdr & _]\n  (skip-line rdr _))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-int",
    "line" 49,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- match-int\n  [^Matcher m]\n  (if (.group m 2)\n    (if (.group m 8) 0N 0)\n    (let [negate? (= \"-\" (.group m 1))\n          a (cond\n             (.group m 3) [(.group m 3) 10]\n             (.group m 4) [(.group m 4) 16]\n             (.group m 5) [(.group m 5) 8]\n             (.group m 7) [(.group m 7) (Integer/parseInt (.group m 6))]\n             :else        [nil nil])\n          ^String n (a 0)\n          radix (int (a 1))]\n      (when n\n        (let [bn (BigInteger. n radix)\n              bn (if negate? (.negate bn) bn)]\n          (if (.group m 8)\n            (BigInt/fromBigInteger bn)\n            (if (< (.bitLength bn) 64)\n              (.longValue bn)\n              (BigInt/fromBigInteger bn))))))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["m"]]}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "int-pattern",
    "column" 1,
    "line" 45,
    "source"
    "(def ^Pattern int-pattern #\"([-+]?)(?:(0)|([1-9][0-9]*)|0[xX]([0-9A-Fa-f]+)|0([0-7]+)|([1-9][0-9]?)[rR]([0-9A-Za-z]+)|0[0-9]+)(N)?\")",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" "java.util.regex.Pattern"}
   {"private" true,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-float",
    "line" 81,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- match-float\n  [^String s ^Matcher m]\n  (if (.group m 4)\n    (BigDecimal. ^String (.group m 1))\n    (Double/parseDouble s)))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["s" "m"]]}
   {"private" true,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-ratio",
    "line" 71,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- match-ratio\n  [^Matcher m]\n  (let [^String numerator (.group m 1)\n        ^String denominator (.group m 2)\n        numerator (if (.startsWith numerator \"+\")\n                    (subs numerator 1)\n                    numerator)]\n    (/ (-> numerator   BigInteger. BigInt/fromBigInteger Numbers/reduceBigInt)\n       (-> denominator BigInteger. BigInt/fromBigInteger Numbers/reduceBigInt))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["m"]]}
   {"ns" "clojure.tools.reader.impl.commons",
    "name" "read-past",
    "line" 28,
    "column" 1,
    "doc"
    "Read until first character that doesn't match pred, returning\n   char.",
    "tag" nil,
    "source"
    "(defn read-past\n  \"Read until first character that doesn't match pred, returning\n   char.\"\n  [pred rdr]\n  (loop [ch (read-char rdr)]\n    (if (pred ch)\n      (recur (read-char rdr))\n      ch)))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["pred" "rdr"]]}
   {"ns" "clojure.tools.reader.impl.commons",
    "name" "skip-line",
    "line" 37,
    "column" 1,
    "doc"
    "Advances the reader to the end of a line. Returns the reader",
    "tag" nil,
    "source"
    "(defn skip-line\n  \"Advances the reader to the end of a line. Returns the reader\"\n  [reader _]\n  (loop []\n    (when-not (newline? (read-char reader))\n      (recur)))\n  reader)",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["reader" "_"]]}
   {"arglists" [["s"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-number",
    "column" 1,
    "line" 87,
    "source"
    "(defn match-number [^String s]\n  (let [int-matcher (.matcher int-pattern s)]\n    (if (.matches int-matcher)\n      (match-int int-matcher)\n      (let [float-matcher (.matcher float-pattern s)]\n        (if (.matches float-matcher)\n          (match-float s float-matcher)\n          (let [ratio-matcher (.matcher ratio-pattern s)]\n            (when (.matches ratio-matcher)\n              (match-ratio ratio-matcher))))))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" [["rdr" "ch"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "read-regex",
    "column" 1,
    "line" 129,
    "source"
    "(defn read-regex\n  [rdr ch]\n  (let [sb (StringBuilder.)]\n    (loop [ch (read-char rdr)]\n      (if (identical? \\\" ch)\n        (Pattern/compile (str sb))\n        (if (nil? ch)\n          (reader-error rdr \"EOF while reading regex\")\n          (do\n            (.append sb ch )\n            (when (identical? \\\\ ch)\n              (let [ch (read-char rdr)]\n                (if (nil? ch)\n                  (reader-error rdr \"EOF while reading regex\"))\n                (.append sb ch)))\n            (recur (read-char rdr))))))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "float-pattern",
    "column" 1,
    "line" 47,
    "source"
    "(def ^Pattern float-pattern #\"([-+]?[0-9]+(\\.[0-9]*)?([eE][-+]?[0-9]+)?)(M)?\")",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" "java.util.regex.Pattern"}],
  "clojure.tools.reader.edn"
  [{"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "wrapping-reader",
    "line" 271,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- wrapping-reader\n  [sym]\n  (fn [rdr _ opts]\n    (list sym (read rdr true nil opts))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "escape-char",
    "line" 202,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- escape-char [sb rdr]\n  (let [ch (read-char rdr)]\n    (case ch\n      \\t \"\\t\"\n      \\r \"\\r\"\n      \\n \"\\n\"\n      \\\\ \"\\\\\"\n      \\\" \"\\\"\"\n      \\b \"\\b\"\n      \\f \"\\f\"\n      \\u (let [ch (read-char rdr)]\n           (if (== -1 (Character/digit (int ch) 16))\n             (reader-error rdr \"Invalid unicode escape: \\\\u\" ch)\n             (read-unicode-char rdr ch 16 4 true)))\n      \\x (let [ch (read-char rdr)]\n           (if (== -1 (Character/digit (int ch) 16))\n             (reader-error rdr \"Invalid unicode escape: \\\\x\" ch)\n             (read-unicode-char rdr ch 16 2 true)))\n      (if (numeric? ch)\n        (let [ch (read-unicode-char rdr ch 8 3 false)]\n          (if (> (int ch) 0337)\n            (reader-error rdr \"Octal escape sequence must be in range [0, 377]\")\n            ch))\n        (reader-error rdr \"Unsupported escape character: \\\\\" ch)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["sb" "rdr"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-list",
    "line" 173,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-list\n  [rdr _ opts]\n  (let [the-list (read-delimited \\) rdr opts)]\n    (if (empty? the-list)\n      '()\n      (clojure.lang.PersistentList/create the-list))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "macros",
    "line" 295,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- macros [ch]\n  (case ch\n    \\\" read-string*\n    \\: read-keyword\n    \\; read-comment\n    \\^ read-meta\n    \\( read-list\n    \\) read-unmatched-delimiter\n    \\[ read-vector\n    \\] read-unmatched-delimiter\n    \\{ read-map\n    \\} read-unmatched-delimiter\n    \\\\ read-char*\n    \\# read-dispatch\n    nil))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-map",
    "line" 184,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-map\n  [rdr _ opts]\n  (let [l (to-array (read-delimited \\} rdr opts))]\n    (when (== 1 (bit-and (alength l) 1))\n      (reader-error rdr \"Map literal must contain an even number of forms\"))\n    (RT/map l)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"ns" "clojure.tools.reader.edn",
    "name" "read",
    "line" 336,
    "column" 1,
    "doc"
    "Reads the first object from an IPushbackReader or a java.io.PushbackReader.\n   Returns the object read. If EOF, throws if eof-error? is true otherwise returns eof.\n   If no reader is provided, *in* will be used.\n\n   Reads data in the edn format (subset of Clojure data):\n   http://edn-format.org\n\n   clojure.tools.reader.edn/read doesn't depend on dynamic Vars, all configuration\n   is done by passing an opt map.\n\n   opts is a map that can include the following keys:\n   :eof - value to return on end-of-file. When not supplied, eof throws an exception.\n   :readers  - a map of tag symbols to data-reader functions to be considered before default-data-readers.\n              When not supplied, only the default-data-readers will be used.\n   :default - A function of two args, that will, if present and no reader is found for a tag,\n              be called with the tag and the value.",
    "tag" nil,
    "source"
    "(defn read\n  \"Reads the first object from an IPushbackReader or a java.io.PushbackReader.\n   Returns the object read. If EOF, throws if eof-error? is true otherwise returns eof.\n   If no reader is provided, *in* will be used.\n\n   Reads data in the edn format (subset of Clojure data):\n   http://edn-format.org\n\n   clojure.tools.reader.edn/read doesn't depend on dynamic Vars, all configuration\n   is done by passing an opt map.\n\n   opts is a map that can include the following keys:\n   :eof - value to return on end-of-file. When not supplied, eof throws an exception.\n   :readers  - a map of tag symbols to data-reader functions to be considered before default-data-readers.\n              When not supplied, only the default-data-readers will be used.\n   :default - A function of two args, that will, if present and no reader is found for a tag,\n              be called with the tag and the value.\"\n  ([] (read *in*))\n  ([reader] (read {} reader))\n  ([{:keys [eof] :as opts} reader]\n     (let [eof-error? (not (contains? opts :eof))]\n       (read reader eof-error? eof opts)))\n  ([reader eof-error? eof opts]\n     (try\n       (let [ch (read-char reader)]\n         (cond\n          (whitespace? ch) (read opts reader)\n          (nil? ch) (if eof-error? (reader-error reader \"EOF\") eof)\n          (number-literal? reader ch) (read-number reader ch opts)\n          (comment-prefix? ch) (read opts (read-comment reader ch opts))\n          :else (let [f (macros ch)]\n                  (if f\n                    (let [res (f reader ch opts)]\n                      (if (identical? res reader)\n                        (read opts reader)\n                        res))\n                    (read-symbol reader ch)))))\n       (catch Exception e\n         (if (ex-info? e)\n           (throw e)\n           (throw (ex-info (.getMessage e)\n                           (merge {:type :reader-exception}\n                                  (if (indexing-reader? reader)\n                                    {:line (get-line-number reader)\n                                     :column (get-column-number reader)}))\n                           e)))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists"
    [[]
     ["reader"]
     [[["keys" ["eof"]] ["as" "opts"]] "reader"]
     ["reader" "eof-error?" "eof" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-set",
    "line" 286,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-set\n  [rdr _ opts]\n  (PersistentHashSet/createWithCheck (read-delimited \\} rdr opts)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-symbol",
    "line" 238,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-symbol\n  [rdr initch]\n  (when-let [token (read-token rdr initch)]\n    (case token\n\n      ;; special symbols\n      \"nil\" nil\n      \"true\" true\n      \"false\" false\n      \"/\" '/\n      \"NaN\" Double/NaN\n      \"-Infinity\" Double/NEGATIVE_INFINITY\n      (\"Infinity\" \"+Infinity\") Double/POSITIVE_INFINITY\n\n      (or (when-let [p (parse-symbol token)]\n            (symbol (p 0) (p 1)))\n          (reader-error rdr \"Invalid token: \" token)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "initch"]]}
   {"ns" "clojure.tools.reader.edn",
    "name" "read-string",
    "line" 383,
    "column" 1,
    "doc"
    "Reads one object from the string s.\n   Returns nil when s is nil or empty.\n\n   Reads data in the edn format (subset of Clojure data):\n   http://edn-format.org\n\n   opts is a map as per clojure.tools.reader.edn/read",
    "tag" nil,
    "source"
    "(defn read-string\n  \"Reads one object from the string s.\n   Returns nil when s is nil or empty.\n\n   Reads data in the edn format (subset of Clojure data):\n   http://edn-format.org\n\n   opts is a map as per clojure.tools.reader.edn/read\"\n  ([s] (read-string {:eof nil} s))\n  ([opts s]\n     (when (and s (not (identical? s \"\")))\n       (read opts (string-push-back-reader s)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["s"] ["opts" "s"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-meta",
    "line" 276,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-meta\n  [rdr _ opts]\n  (let [m (desugar-meta (read rdr true nil opts))]\n    (when-not (map? m)\n      (reader-error rdr \"Metadata must be Symbol, Keyword, String or Map\"))\n    (let [o (read rdr true nil opts)]\n      (if (instance? IMeta o)\n        (with-meta o (merge (meta o) m))\n        (reader-error rdr \"Metadata can only be applied to IMetas\")))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-number",
    "line" 191,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-number\n  [reader initch opts]\n  (loop [sb (doto (StringBuilder.) (.append initch))\n         ch (read-char reader)]\n    (if (or (whitespace? ch) (macros ch) (nil? ch))\n      (let [s (str sb)]\n        (unread reader ch)\n        (or (match-number s)\n            (reader-error reader \"Invalid number format [\" s \"]\")))\n      (recur (doto sb (.append ch)) (read-char reader)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["reader" "initch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-discard",
    "line" 290,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-discard\n  [rdr _ opts]\n  (read rdr true nil opts)\n  rdr)",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-vector",
    "line" 180,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-vector\n  [rdr _ opts]\n  (read-delimited \\] rdr opts))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "dispatch-macros",
    "line" 311,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- dispatch-macros [ch]\n  (case ch\n    \\^ read-meta                ;deprecated\n    \\{ read-set\n    \\< (throwing-reader \"Unreadable form\")\n    \\! read-comment\n    \\_ read-discard\n    nil))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-keyword",
    "line" 256,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-keyword\n  [reader initch opts]\n  (let [ch (read-char reader)]\n    (if-not (whitespace? ch)\n      (let [token (read-token reader ch)\n            s (parse-symbol token)]\n        (if (and s (== -1 (.indexOf token \"::\")))\n          (let [^String ns (s 0)\n                ^String name (s 1)]\n            (if (identical? \\: (nth token 0))\n              (reader-error reader \"Invalid token: :\" token) ;; no ::keyword in edn\n              (keyword ns name)))\n          (reader-error reader \"Invalid token: :\" token)))\n      (reader-error reader \"Invalid token: :\"))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["reader" "initch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-unmatched-delimiter",
    "line" 70,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unmatched-delimiter\n  [rdr ch opts]\n  (reader-error rdr \"Unmatched delimiter \" ch))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "ch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-tagged",
    "line" 320,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-tagged [rdr initch opts]\n  (let [tag (read rdr true nil opts)\n        object (read rdr true nil opts)]\n    (if-not (symbol? tag)\n      (reader-error rdr \"Reader tag must be a symbol\"))\n    (if-let [f (or (get (:readers opts) tag)\n                   (default-data-readers tag))]\n      (f object)\n      (if-let [d (:default opts)]\n        (d tag object)\n        (reader-error rdr \"No reader function for tag \" (name tag))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "initch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "not-constituent?",
    "line" 30,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- not-constituent? [ch]\n  (or (identical? \\@ ch)\n      (identical? \\` ch)\n      (identical? \\~ ch)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "macro-terminating?",
    "line" 24,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- macro-terminating? [ch]\n  (and (not (identical? \\# ch))\n       (not (identical? \\' ch))\n       (not (identical? \\: ch))\n       (macros ch)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-string*",
    "line" 227,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-string*\n  [reader _ opts]\n  (loop [sb (StringBuilder.)\n         ch (read-char reader)]\n    (case ch\n      nil (reader-error reader \"EOF while reading string\")\n      \\\\ (recur (doto sb (.append (escape-char sb reader)))\n                (read-char reader))\n      \\\" (str sb)\n      (recur (doto sb (.append ch)) (read-char reader)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["reader" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-token",
    "line" 35,
    "column" 1,
    "tag" "java.lang.String",
    "source"
    "(defn- ^String read-token\n  ([rdr initch]\n     (read-token rdr initch true))\n  ([rdr initch validate-leading?]\n     (cond\n      (not initch)\n      (reader-error rdr \"EOF while reading\")\n\n      (and validate-leading?\n           (not-constituent? initch))\n      (reader-error rdr \"Invalid leading character: \" initch)\n\n      :else\n      (loop [sb (doto (StringBuilder.) (.append initch))\n             ch (peek-char rdr)]\n        (if (or (whitespace? ch)\n                (macro-terminating? ch)\n                (nil? ch))\n          (str sb)\n          (if (not-constituent? ch)\n            (reader-error rdr \"Invalid constituent character: \" ch)\n            (recur (doto sb (.append (read-char rdr))) (peek-char rdr))))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "initch"] ["rdr" "initch" "validate-leading?"]]}
   {"private" true,
    "const" true,
    "ns" "clojure.tools.reader.edn",
    "name" "lower-limit",
    "line" 112,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:const lower-limit (int \\uE000))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-delimited",
    "line" 154,
    "column" 1,
    "tag" "clojure.lang.PersistentVector",
    "source"
    "(defn- ^PersistentVector read-delimited\n  [delim rdr opts]\n  (let [first-line (when (indexing-reader? rdr)\n                     (get-line-number rdr))\n        delim (char delim)]\n    (loop [a (transient [])]\n      (let [ch (read-past whitespace? rdr)]\n        (when-not ch\n          (reader-error rdr \"EOF while reading\"\n                        (if first-line\n                          (str \", starting at line\" first-line))))\n        (if (identical? delim (char ch))\n          (persistent! a)\n          (if-let [macrofn (macros ch)]\n            (let [mret (macrofn rdr ch opts)]\n              (recur (if-not (identical? mret rdr) (conj! a mret) a)))\n            (let [o (read (doto rdr (unread ch)) true nil opts)]\n              (recur (if-not (identical? o rdr) (conj! a o) a)))))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["delim" "rdr" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-dispatch",
    "line" 60,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-dispatch\n  [rdr _ opts]\n  (if-let [ch (read-char rdr)]\n    (if-let [dm (dispatch-macros ch)]\n      (dm rdr ch opts)\n      (if-let [obj (read-tagged (doto rdr (unread ch)) ch opts)]\n        obj\n        (reader-error rdr \"No dispatch macro for \" ch)))\n    (reader-error rdr \"EOF while reading character\")))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-char*",
    "line" 114,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-char*\n  [rdr backslash opts]\n  (let [ch (read-char rdr)]\n    (if-not (nil? ch)\n      (let [token (read-token rdr ch false)\n            token-len (count token)]\n        (cond\n\n         (== 1 token-len)  (Character/valueOf (nth token 0))\n\n         (= token \"newline\") \\newline\n         (= token \"space\") \\space\n         (= token \"tab\") \\tab\n         (= token \"backspace\") \\backspace\n         (= token \"formfeed\") \\formfeed\n         (= token \"return\") \\return\n\n         (.startsWith token \"u\")\n         (let [c (read-unicode-char token 1 4 16)\n               ic (int c)]\n           (if (and (> ic upper-limit)\n                    (< ic lower-limit))\n             (reader-error rdr \"Invalid character constant: \\\\u\" (Integer/toString ic 16))\n             c))\n\n         (.startsWith token \"x\")\n         (read-unicode-char token 1 2 16)\n\n         (.startsWith token \"o\")\n         (let [len (dec token-len)]\n           (if (> len 3)\n             (reader-error rdr \"Invalid octal escape sequence length: \" len)\n             (let [uc (read-unicode-char token 1 len 8)]\n               (if (> (int uc) 0377)\n                 (reader-error rdr \"Octal escape sequence must be in range [0, 377]\")\n                 uc))))\n\n         :else (reader-error rdr \"Unsupported character: \\\\\" token)))\n      (reader-error rdr \"EOF while reading character\"))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "backslash" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-unicode-char",
    "line" 78,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unicode-char\n  ([^String token offset length base]\n     (let [l (+ offset length)]\n       (when-not (== (count token) l)\n         (throw (IllegalArgumentException. (str \"Invalid unicode character: \\\\\" token))))\n       (loop [i offset uc 0]\n         (if (== i l)\n           (char uc)\n           (let [d (Character/digit (int (nth token i)) (int base))]\n             (if (== d -1)\n               (throw (IllegalArgumentException. (str \"Invalid digit: \" (nth token i))))\n               (recur (inc i) (long (+ d (* uc base))))))))))\n\n  ([rdr initch base length exact?]\n     (loop [i 1 uc (Character/digit (int initch) (int base))]\n       (if (== uc -1)\n         (throw (IllegalArgumentException. (str \"Invalid digit: \" initch)))\n         (if-not (== i length)\n           (let [ch (peek-char rdr)]\n             (if (or (whitespace? ch)\n                     (macros ch)\n                     (nil? ch))\n               (if exact?\n                 (throw (IllegalArgumentException.\n                         (str \"Invalid character length: \" i \", should be: \" length)))\n                 (char uc))\n               (let [d (Character/digit (int ch) (int base))]\n                 (read-char rdr)\n                 (if (== d -1)\n                   (throw (IllegalArgumentException. (str \"Invalid digit: \" ch)))\n                   (recur (inc i) (long (+ d (* uc base))))))))\n           (char uc))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists"
    [["token" "offset" "length" "base"]
     ["rdr" "initch" "base" "length" "exact?"]]}
   {"private" true,
    "const" true,
    "ns" "clojure.tools.reader.edn",
    "name" "upper-limit",
    "line" 111,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:const upper-limit (int \\uD7ff))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" nil}],
  "clojure.tools.reader.default-data-readers"
  [{"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "print-timestamp",
    "line" 217,
    "column" 1,
    "doc"
    "Print a java.sql.Timestamp as RFC3339 timestamp, always in UTC.",
    "tag" nil,
    "source"
    "(defn- print-timestamp\n  \"Print a java.sql.Timestamp as RFC3339 timestamp, always in UTC.\"\n  [^java.sql.Timestamp ts, ^java.io.Writer w]\n  (let [utc-format (.get thread-local-utc-timestamp-format)]\n    (.write w \"#inst \\\"\")\n    (.write w ^String (.format ^java.text.SimpleDateFormat utc-format ts))\n    ;; add on nanos and offset\n    ;; RFC3339 says to use -00:00 when the timezone is unknown (+00:00 implies a known GMT)\n    (.write w (format \".%09d-00:00\" (.getNanos ts)))\n    (.write w \"\\\"\")))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["ts" "w"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "indivisible?",
    "line" 32,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- indivisible?\n  [num div]\n  (not (divisible? num div)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["num" "div"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "construct-timestamp",
    "line" 262,
    "column" 1,
    "doc"
    "Construct a java.sql.Timestamp, which has nanosecond precision.",
    "tag" nil,
    "source"
    "(defn- construct-timestamp\n  \"Construct a java.sql.Timestamp, which has nanosecond precision.\"\n  [years months days hours minutes seconds nanoseconds\n   offset-sign offset-hours offset-minutes]\n  (doto (Timestamp.\n         (.getTimeInMillis\n          (construct-calendar years months days\n                              hours minutes seconds 0\n                              offset-sign offset-hours offset-minutes)))\n    ;; nanos must be set separately, pass 0 above for the base calendar\n    (.setNanos nanoseconds)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists"
    [["years"
      "months"
      "days"
      "hours"
      "minutes"
      "seconds"
      "nanoseconds"
      "offset-sign"
      "offset-hours"
      "offset-minutes"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "construct-calendar",
    "line" 240,
    "column" 1,
    "doc"
    "Construct a java.util.Calendar, preserving the timezone\noffset, but truncating the subsecond fraction to milliseconds.",
    "tag" nil,
    "source"
    "(defn- construct-calendar\n  \"Construct a java.util.Calendar, preserving the timezone\noffset, but truncating the subsecond fraction to milliseconds.\"\n  ^GregorianCalendar\n  [years months days hours minutes seconds nanoseconds\n   offset-sign offset-hours offset-minutes]\n  (doto (GregorianCalendar. years (dec months) days hours minutes seconds)\n    (.set Calendar/MILLISECOND (quot nanoseconds 1000000))\n    (.setTimeZone (TimeZone/getTimeZone\n                   (format \"GMT%s%02d:%02d\"\n                           (if (neg? offset-sign) \"-\" \"+\")\n                           offset-hours offset-minutes)))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists"
    [["years"
      "months"
      "days"
      "hours"
      "minutes"
      "seconds"
      "nanoseconds"
      "offset-sign"
      "offset-hours"
      "offset-minutes"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "print-date",
    "line" 172,
    "column" 1,
    "doc"
    "Print a java.util.Date as RFC3339 timestamp, always in UTC.",
    "tag" nil,
    "source"
    "(defn- print-date\n  \"Print a java.util.Date as RFC3339 timestamp, always in UTC.\"\n  [^java.util.Date d, ^java.io.Writer w]\n  (let [utc-format (.get thread-local-utc-date-format)]\n    (.write w \"#inst \\\"\")\n    (.write w ^String (.format ^java.text.SimpleDateFormat utc-format d))\n    (.write w \"\\\"\")))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["d" "w"]]}
   {"arglists" nil,
    "ns" "clojure.tools.reader.default-data-readers",
    "name"
    "clojure.tools.reader.default_data_readers.proxy$java.lang.ThreadLocal$0",
    "source" nil,
    "tag" nil}
   {"ns" "clojure.tools.reader.default-data-readers",
    "name" "parse-timestamp",
    "line" 51,
    "column" 1,
    "doc"
    "Parse a string containing an RFC3339-like like timestamp.\n\nThe function new-instant is called with the following arguments.\n\n                min  max           default\n                ---  ------------  -------\n  years          0           9999      N/A (s must provide years)\n  months         1             12        1\n  days           1             31        1 (actual max days depends\n  hours          0             23        0  on month and year)\n  minutes        0             59        0\n  seconds        0             60        0 (though 60 is only valid\n  nanoseconds    0      999999999        0  when minutes is 59)\n  offset-sign   -1              1        0\n  offset-hours   0             23        0\n  offset-minutes 0             59        0\n\nThese are all integers and will be non-nil. (The listed defaults\nwill be passed if the corresponding field is not present in s.)\n\nGrammar (of s):\n\n  date-fullyear   = 4DIGIT\n  date-month      = 2DIGIT  ; 01-12\n  date-mday       = 2DIGIT  ; 01-28, 01-29, 01-30, 01-31 based on\n                            ; month/year\n  time-hour       = 2DIGIT  ; 00-23\n  time-minute     = 2DIGIT  ; 00-59\n  time-second     = 2DIGIT  ; 00-58, 00-59, 00-60 based on leap second\n                            ; rules\n  time-secfrac    = '.' 1*DIGIT\n  time-numoffset  = ('+' / '-') time-hour ':' time-minute\n  time-offset     = 'Z' / time-numoffset\n\n  time-part       = time-hour [ ':' time-minute [ ':' time-second\n                    [time-secfrac] [time-offset] ] ]\n\n  timestamp       = date-year [ '-' date-month [ '-' date-mday\n                    [ 'T' time-part ] ] ]\n\nUnlike RFC3339:\n\n  - we only parse the timestamp format\n  - timestamp can elide trailing components\n  - time-offset is optional (defaults to +00:00)\n\nThough time-offset is syntactically optional, a missing time-offset\nwill be treated as if the time-offset zero (+00:00) had been\nspecified.\n",
    "tag" nil,
    "source"
    "(def parse-timestamp\n     \"Parse a string containing an RFC3339-like like timestamp.\n\nThe function new-instant is called with the following arguments.\n\n                min  max           default\n                ---  ------------  -------\n  years          0           9999      N/A (s must provide years)\n  months         1             12        1\n  days           1             31        1 (actual max days depends\n  hours          0             23        0  on month and year)\n  minutes        0             59        0\n  seconds        0             60        0 (though 60 is only valid\n  nanoseconds    0      999999999        0  when minutes is 59)\n  offset-sign   -1              1        0\n  offset-hours   0             23        0\n  offset-minutes 0             59        0\n\nThese are all integers and will be non-nil. (The listed defaults\nwill be passed if the corresponding field is not present in s.)\n\nGrammar (of s):\n\n  date-fullyear   = 4DIGIT\n  date-month      = 2DIGIT  ; 01-12\n  date-mday       = 2DIGIT  ; 01-28, 01-29, 01-30, 01-31 based on\n                            ; month/year\n  time-hour       = 2DIGIT  ; 00-23\n  time-minute     = 2DIGIT  ; 00-59\n  time-second     = 2DIGIT  ; 00-58, 00-59, 00-60 based on leap second\n                            ; rules\n  time-secfrac    = '.' 1*DIGIT\n  time-numoffset  = ('+' / '-') time-hour ':' time-minute\n  time-offset     = 'Z' / time-numoffset\n\n  time-part       = time-hour [ ':' time-minute [ ':' time-second\n                    [time-secfrac] [time-offset] ] ]\n\n  timestamp       = date-year [ '-' date-month [ '-' date-mday\n                    [ 'T' time-part ] ] ]\n\nUnlike RFC3339:\n\n  - we only parse the timestamp format\n  - timestamp can elide trailing components\n  - time-offset is optional (defaults to +00:00)\n\nThough time-offset is syntactically optional, a missing time-offset\nwill be treated as if the time-offset zero (+00:00) had been\nspecified.\n\"\n     (let [timestamp #\"(\\d\\d\\d\\d)(?:-(\\d\\d)(?:-(\\d\\d)(?:[T](\\d\\d)(?::(\\d\\d)(?::(\\d\\d)(?:[.](\\d+))?)?)?)?)?)?(?:[Z]|([-+])(\\d\\d):(\\d\\d))?\"]\n\n       (fn [new-instant ^CharSequence cs]\n         (if-let [[_ years months days hours minutes seconds fraction\n                   offset-sign offset-hours offset-minutes]\n                  (re-matches timestamp cs)]\n           (new-instant\n            (parse-int years)\n            (if-not months   1 (parse-int months))\n            (if-not days     1 (parse-int days))\n            (if-not hours    0 (parse-int hours))\n            (if-not minutes  0 (parse-int minutes))\n            (if-not seconds  0 (parse-int seconds))\n            (if-not fraction 0 (parse-int (zero-fill-right fraction 9)))\n            (cond (= \"-\" offset-sign) -1\n                  (= \"+\" offset-sign)  1\n                  :else                0)\n            (if-not offset-hours   0 (parse-int offset-hours))\n            (if-not offset-minutes 0 (parse-int offset-minutes)))\n           (fail (str \"Unrecognized date/time syntax: \" cs))))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"ns" "clojure.tools.reader.default-data-readers",
    "name" "read-instant-date",
    "line" 274,
    "column" 1,
    "doc"
    "To read an instant as a java.util.Date, bind *data-readers* to a map with\nthis var as the value for the 'inst key. The timezone offset will be used\nto convert into UTC.",
    "tag" nil,
    "source"
    "(def read-instant-date\n  \"To read an instant as a java.util.Date, bind *data-readers* to a map with\nthis var as the value for the 'inst key. The timezone offset will be used\nto convert into UTC.\"\n  (partial parse-timestamp (validated construct-date)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "leap-year?",
    "line" 127,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- leap-year?\n  [year]\n  (and (divisible? year 4)\n       (or (indivisible? year 100)\n           (divisible? year 400))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["year"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "zero-fill-right",
    "line" 43,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- zero-fill-right [^String s width]\n  (cond (= width (count s)) s\n        (< width (count s)) (.substring s 0 width)\n        :else (loop [b (StringBuilder. s)]\n                (if (< (.length b) width)\n                  (recur (.append b \\0))\n                  (.toString b)))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["s" "width"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "thread-local-utc-timestamp-format",
    "line" 209,
    "column" 1,
    "tag" "java.lang.ThreadLocal",
    "source"
    "(def ^:private ^ThreadLocal thread-local-utc-timestamp-format\n  ;; SimpleDateFormat is not thread-safe, so we use a ThreadLocal proxy for access.\n  ;; http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4228335\n  (proxy [ThreadLocal] []\n    (initialValue []\n      (doto (java.text.SimpleDateFormat. \"yyyy-MM-dd'T'HH:mm:ss\")\n        (.setTimeZone (java.util.TimeZone/getTimeZone \"GMT\"))))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "verify",
    "macro" true,
    "line" 24,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private verify\n  ([test msg] `(when-not ~test (fail ~msg)))\n  ([test] `(verify ~test ~(str \"failed: \" (pr-str test)))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["test" "msg"] ["test"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "print-calendar",
    "line" 188,
    "column" 1,
    "doc"
    "Print a java.util.Calendar as RFC3339 timestamp, preserving timezone.",
    "tag" nil,
    "source"
    "(defn- print-calendar\n  \"Print a java.util.Calendar as RFC3339 timestamp, preserving timezone.\"\n  [^java.util.Calendar c, ^java.io.Writer w]\n  (let [calstr (format \"%1$tFT%1$tT.%1$tL%1$tz\" c)\n        offset-minutes (- (.length calstr) 2)]\n    ;; calstr is almost right, but is missing the colon in the offset\n    (.write w \"#inst \\\"\")\n    (.write w calstr 0 offset-minutes)\n    (.write w \":\")\n    (.write w calstr offset-minutes 2)\n    (.write w \"\\\"\")))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["c" "w"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "divisible?",
    "line" 28,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- divisible?\n  [num div]\n  (zero? (mod num div)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["num" "div"]]}
   {"arglists" [["form"]],
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "default-uuid-reader",
    "column" 1,
    "line" 295,
    "source"
    "(defn default-uuid-reader [form]\n  {:pre [(string? form)]}\n  (java.util.UUID/fromString form))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "tag" nil}
   {"ns" "clojure.tools.reader.default-data-readers",
    "name" "validated",
    "line" 139,
    "column" 1,
    "doc"
    "Return a function which constructs and instant by calling constructor\nafter first validating that those arguments are in range and otherwise\nplausible. The resulting function will throw an exception if called\nwith invalid arguments.",
    "tag" nil,
    "source"
    "(defn validated\n  \"Return a function which constructs and instant by calling constructor\nafter first validating that those arguments are in range and otherwise\nplausible. The resulting function will throw an exception if called\nwith invalid arguments.\"\n  [new-instance]\n  (fn [years months days hours minutes seconds nanoseconds\n       offset-sign offset-hours offset-minutes]\n    (verify (<= 1 months 12))\n    (verify (<= 1 days (days-in-month months (leap-year? years))))\n    (verify (<= 0 hours 23))\n    (verify (<= 0 minutes 59))\n    (verify (<= 0 seconds (if (= minutes 59) 60 59)))\n    (verify (<= 0 nanoseconds 999999999))\n    (verify (<= -1 offset-sign 1))\n    (verify (<= 0 offset-hours 23))\n    (verify (<= 0 offset-minutes 59))\n    (new-instance years months days hours minutes seconds nanoseconds\n                  offset-sign offset-hours offset-minutes)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["new-instance"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "days-in-month",
    "line" 133,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private days-in-month\n     (let [dim-norm [nil 31 28 31 30 31 30 31 31 30 31 30 31]\n           dim-leap [nil 31 29 31 30 31 30 31 31 30 31 30 31]]\n       (fn [month leap-year?]\n         ((if leap-year? dim-leap dim-norm) month))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "construct-date",
    "line" 253,
    "column" 1,
    "doc"
    "Construct a java.util.Date, which expresses the original instant as\nmilliseconds since the epoch, UTC.",
    "tag" nil,
    "source"
    "(defn- construct-date\n  \"Construct a java.util.Date, which expresses the original instant as\nmilliseconds since the epoch, UTC.\"\n  [years months days hours minutes seconds nanoseconds\n   offset-sign offset-hours offset-minutes]\n  (.getTime (construct-calendar years months days\n                                hours minutes seconds nanoseconds\n                                offset-sign offset-hours offset-minutes)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists"
    [["years"
      "months"
      "days"
      "hours"
      "minutes"
      "seconds"
      "nanoseconds"
      "offset-sign"
      "offset-hours"
      "offset-minutes"]]}
   {"ns" "clojure.tools.reader.default-data-readers",
    "name" "read-instant-timestamp",
    "line" 286,
    "column" 1,
    "doc"
    "To read an instant as a java.sql.Timestamp, bind *data-readers* to a\nmap with this var as the value for the 'inst key. Timestamp preserves\nfractional seconds with nanosecond precision. The timezone offset will\nbe used to convert into UTC.",
    "tag" nil,
    "source"
    "(def read-instant-timestamp\n  \"To read an instant as a java.sql.Timestamp, bind *data-readers* to a\nmap with this var as the value for the 'inst key. Timestamp preserves\nfractional seconds with nanosecond precision. The timezone offset will\nbe used to convert into UTC.\"\n  (partial parse-timestamp (validated construct-timestamp)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"ns" "clojure.tools.reader.default-data-readers",
    "name" "read-instant-calendar",
    "line" 280,
    "column" 1,
    "doc"
    "To read an instant as a java.util.Calendar, bind *data-readers* to a map with\nthis var as the value for the 'inst key.  Calendar preserves the timezone\noffset.",
    "tag" nil,
    "source"
    "(def read-instant-calendar\n  \"To read an instant as a java.util.Calendar, bind *data-readers* to a map with\nthis var as the value for the 'inst key.  Calendar preserves the timezone\noffset.\"\n  (partial parse-timestamp (validated construct-calendar)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "parse-int",
    "line" 40,
    "column" 1,
    "tag" nil,
    "source" "(defn- parse-int [^String s]\n  (Long/parseLong s))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "thread-local-utc-date-format",
    "line" 163,
    "column" 1,
    "tag" "java.lang.ThreadLocal",
    "source"
    "(def ^:private ^ThreadLocal thread-local-utc-date-format\n  ;; SimpleDateFormat is not thread-safe, so we use a ThreadLocal proxy for access.\n  ;; http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4228335\n  (proxy [ThreadLocal] []\n    (initialValue []\n      (doto (java.text.SimpleDateFormat. \"yyyy-MM-dd'T'HH:mm:ss.SSS-00:00\")\n        ;; RFC3339 says to use -00:00 when the timezone is unknown (+00:00 implies a known GMT)\n        (.setTimeZone (java.util.TimeZone/getTimeZone \"GMT\"))))))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.default-data-readers",
    "name" "fail",
    "macro" true,
    "line" 20,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private fail\n  [msg]\n  `(throw (RuntimeException. ~msg)))",
    "file" "clojure/tools/reader/default_data_readers.clj",
    "arglists" [["msg"]]}]},
 "description" "tools.reader 0.7.10",
 "version" "0.7.10",
 "name" "clojure.tools.reader",
 "group" "clojure.tools.reader"}
