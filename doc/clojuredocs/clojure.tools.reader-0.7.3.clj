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
    "line" 196,
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
    "line" 22,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IPushbackReader\n  (unread [reader ch]\n    \"Pushes back a single character on to the stream\"))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" nil}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "reader-error",
    "line" 208,
    "column" 1,
    "doc"
    "Throws an ExceptionInfo with the given message.\n   If rdr is an IndexingReader, additional information about column and line number is provided",
    "tag" nil,
    "source"
    "(defn reader-error\n  \"Throws an ExceptionInfo with the given message.\n   If rdr is an IndexingReader, additional information about column and line number is provided\"\n  [rdr & msg]\n  (throw (ex-info (apply str msg)\n                  (merge {:type :reader-exception}\n                         (when (indexing-reader? rdr)\n                           {:line (get-line-number rdr)\n                            :column (get-column-number rdr)})))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["rdr" "&" "msg"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "->IndexingPushbackReader",
    "line" 96,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.tools.reader.reader_types.IndexingPushbackReader.",
    "tag" nil,
    "source"
    "(deftype IndexingPushbackReader\n    [rdr ^:unsynchronized-mutable line ^:unsynchronized-mutable column\n     ^:unsynchronized-mutable line-start? ^:unsynchronized-mutable prev]\n  Reader\n  (read-char [reader]\n    (when-let [ch (read-char rdr)]\n      (let [ch (normalize-newline rdr ch)]\n        (set! prev line-start?)\n        (set! line-start? (newline? ch))\n        (when line-start?\n          (set! column 0)\n          (update! line inc))\n        (update! column inc)\n        ch)))\n\n  (peek-char [reader]\n    (peek-char rdr))\n\n  IPushbackReader\n  (unread [reader ch]\n    (when line-start? (update! line dec))\n    (set! line-start? prev)\n    (update! column dec)\n    (unread rdr ch))\n\n  IndexingReader\n  (get-line-number [reader] (int (inc line)))\n  (get-column-number [reader] (int column)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["rdr" "line" "column" "line-start?" "prev"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "string-reader",
    "line" 164,
    "column" 1,
    "doc" "Creates a StringReader from a given string",
    "tag" nil,
    "source"
    "(defn string-reader\n  \"Creates a StringReader from a given string\"\n  ([^String s]\n     (StringReader. s (count s) 0)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "string-push-back-reader",
    "line" 169,
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
    "line" 188,
    "column" 1,
    "doc"
    "Creates an IndexingPushbackReader from a given string or Reader",
    "tag" nil,
    "source"
    "(defn indexing-push-back-reader\n  \"Creates an IndexingPushbackReader from a given string or Reader\"\n  ([s-or-rdr]\n     (indexing-push-back-reader s-or-rdr 1))\n  ([s-or-rdr buf-len]\n     (IndexingPushbackReader.\n      (if (string? s-or-rdr) (string-push-back-reader s-or-rdr buf-len) s-or-rdr) 0 1 true nil)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["s-or-rdr"] ["s-or-rdr" "buf-len"]]}
   {"arglists" [["rdr"]],
    "ns" "clojure.tools.reader.reader-types",
    "name" "indexing-reader?",
    "column" 1,
    "line" 155,
    "source"
    "(defn indexing-reader? [rdr]\n  \"Returns true if the reader satisfies IndexingReader\"\n  (or (instance? clojure.tools.reader.reader_types.IndexingReader rdr)\n      (instance? LineNumberingPushbackReader rdr)\n      (and (not (instance? clojure.tools.reader.reader_types.PushbackReader rdr))\n           (not (instance? clojure.tools.reader.reader_types.StringReader rdr))\n           (not (instance? clojure.tools.reader.reader_types.InputStreamReader rdr))\n           (get (:impls IndexingReader) (class rdr)))))",
    "file" "clojure/tools/reader/reader_types.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.reader.reader-types",
    "name" "update!",
    "macro" true,
    "line" 9,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private update! [what f]\n  (list 'set! what (list f what)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["what" "f"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "input-stream-push-back-reader",
    "line" 181,
    "column" 1,
    "doc" "Creates a PushbackReader from a given InputString",
    "tag" nil,
    "source"
    "(defn input-stream-push-back-reader\n  \"Creates a PushbackReader from a given InputString\"\n  ([is]\n     (input-stream-push-back-reader is 1))\n  ([^InputStream is buf-len]\n     (PushbackReader. (input-stream-reader is) (object-array buf-len) buf-len buf-len)))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["is"] ["is" "buf-len"]]}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "Reader",
    "line" 16,
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
    "line" 66,
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
    "line" 176,
    "column" 1,
    "doc" "Creates an InputStreamReader from an InputString",
    "tag" nil,
    "source"
    "(defn input-stream-reader\n  \"Creates an InputStreamReader from an InputString\"\n  [is]\n  (InputStreamReader. is nil))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" [["is"]]}
   {"private" true,
    "ns" "clojure.tools.reader.reader-types",
    "name" "normalize-newline",
    "line" 88,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- normalize-newline [rdr ch]\n  (if (identical? \\return ch)\n    (let [c (peek-char rdr)]\n      (when (identical? \\formfeed c)\n        (read-char rdr))\n      \\newline)\n    ch))",
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
    "line" 48,
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
    "line" 26,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IndexingReader\n  (get-line-number [reader]\n    \"Returns the line number of the next character to be read from the stream\")\n  (get-column-number [reader]\n    \"Returns the line number of the next character to be read from the stream\"))",
    "file" "clojure/tools/reader/reader_types.clj",
    "arglists" nil}
   {"ns" "clojure.tools.reader.reader-types",
    "name" "->StringReader",
    "line" 36,
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
    "line" 45,
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
    "line" 5,
    "source" "(defn char [x]\n  (when x\n    (clojure.core/char x)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"ns" "clojure.tools.reader.impl.utils",
    "name" "whitespace?",
    "line" 38,
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
    "line" 22,
    "source"
    "(compile-if (= 3 (:minor *clojure-version*))\n  (do\n    (defn ex-info\n      ([msg map]\n         (clojure.tools.reader.impl.ExceptionInfo. msg map))\n      ([msg map cause]\n         (clojure.tools.reader.impl.ExceptionInfo. msg map cause)))\n    (defn ex-data\n      [ex]\n      (.getData ex))\n    (defn ex-info? [ex]\n      (instance? clojure.tools.reader.impl.ExceptionInfo ex)))\n\n  (defn ex-info? [ex]\n    (instance? clojure.lang.ExceptionInfo ex)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.tools.reader.impl.utils",
    "name" "newline?",
    "column" 1,
    "line" 56,
    "source"
    "(defn newline? [c]\n  \"Checks whether the character is a newline\"\n  (or (identical? \\newline c)\n      (nil? c)))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"ns" "clojure.tools.reader.impl.utils",
    "name" "comment-prefix?",
    "line" 51,
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
    "line" 61,
    "source"
    "(defn desugar-meta\n  [f]\n  (cond\n    (keyword? f) {f true}\n    (symbol? f)  {:tag f}\n    (string? f)  {:tag f}\n    :else        f))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.utils",
    "name" ">=clojure-1-5-alpha*?",
    "column" 1,
    "line" 10,
    "source"
    "(def >=clojure-1-5-alpha*?\n  (let [{:keys [minor qualifier]} *clojure-version*]\n    (and (>= minor 5)\n         (not= \"alpha\"\n               (when qualifier\n                 (subs qualifier 0 (dec (count qualifier))))))))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.reader.impl.utils",
    "name" "compile-if",
    "macro" true,
    "line" 17,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ^:private compile-if [cond then else]\n  (if (eval cond)\n    then\n    else))",
    "file" "clojure/tools/reader/impl/utils.clj",
    "arglists" [["cond" "then" "else"]]}],
  "clojure.tools.reader.impl.commons"
  [{"arglists" [["msg"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "throwing-reader",
    "column" 1,
    "line" 113,
    "source"
    "(defn throwing-reader\n  [msg]\n  (fn [rdr & _]\n    (reader-error rdr msg)))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "ratio-pattern",
    "column" 1,
    "line" 38,
    "source"
    "(def ^Pattern ratio-pattern #\"([-+]?[0-9]+)/([0-9]+)\")",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" "java.util.regex.Pattern"}
   {"ns" "clojure.tools.reader.impl.commons",
    "name" "number-literal?",
    "line" 13,
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
    "line" 90,
    "source"
    "(defn parse-symbol [^String token]\n  (when-not (identical? \"\" token)\n    (let [ns-idx (.indexOf token \"/\")]\n      (if-let [ns (and (pos? ns-idx)\n                       (subs token 0 ns-idx))]\n        (let [ns-idx (inc ns-idx)]\n          (when-not (== ns-idx (count token))\n            (let [sym (subs token ns-idx)]\n              (when (and (not (numeric? (nth sym 0)))\n                         (not (identical? \"\" sym))\n                         (or (= sym \"/\")\n                             (== -1 (.indexOf sym \"/\"))))\n                [ns sym]))))\n        [nil token]))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" [["rdr" "&" "_"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "read-comment",
    "column" 1,
    "line" 109,
    "source" "(defn read-comment\n  [rdr & _]\n  (skip-line rdr _))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-int",
    "line" 41,
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
    "line" 37,
    "source"
    "(def ^Pattern int-pattern #\"([-+]?)(?:(0)|([1-9][0-9]*)|0[xX]([0-9A-Fa-f]+)|0([0-7]+)|([1-9][0-9]?)[rR]([0-9A-Za-z]+)|0[0-9]+)(N)?\")",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" "java.util.regex.Pattern"}
   {"private" true,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-float",
    "line" 73,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- match-float\n  [^String s ^Matcher m]\n  (if (.group m 4)\n    (BigDecimal. ^String (.group m 1))\n    (Double/parseDouble s)))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["s" "m"]]}
   {"private" true,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "match-ratio",
    "line" 63,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- match-ratio\n  [^Matcher m]\n  (let [^String numerator (.group m 1)\n        ^String denominator (.group m 2)\n        numerator (if (.startsWith numerator \"+\")\n                    (subs numerator 1)\n                    numerator)]\n    (/ (-> numerator   BigInteger. BigInt/fromBigInteger Numbers/reduceBigInt)\n       (-> denominator BigInteger. BigInt/fromBigInteger Numbers/reduceBigInt))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "arglists" [["m"]]}
   {"ns" "clojure.tools.reader.impl.commons",
    "name" "read-past",
    "line" 20,
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
    "line" 29,
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
    "line" 79,
    "source"
    "(defn match-number [^String s]\n  (let [int-matcher (.matcher int-pattern s)]\n    (if (.matches int-matcher)\n      (match-int int-matcher)\n      (let [float-matcher (.matcher float-pattern s)]\n        (if (.matches float-matcher)\n          (match-float s float-matcher)\n          (let [ratio-matcher (.matcher ratio-pattern s)]\n            (when (.matches ratio-matcher)\n              (match-ratio ratio-matcher))))))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" [["rdr" "ch"]],
    "ns" "clojure.tools.reader.impl.commons",
    "name" "read-regex",
    "column" 1,
    "line" 118,
    "source"
    "(defn read-regex\n  [rdr ch]\n  (let [sb (StringBuilder.)]\n    (loop [ch (read-char rdr)]\n      (if (identical? \\\" ch)\n        (Pattern/compile (str sb))\n        (if (nil? ch)\n          (reader-error rdr \"EOF while reading regex\")\n          (do\n            (.append sb ch )\n            (when (identical? \\\\ ch)\n              (let [ch (read-char rdr)]\n                (if (nil? ch)\n                  (reader-error rdr \"EOF while reading regex\"))\n                (.append sb ch)))\n            (recur (read-char rdr))))))))",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.tools.reader.impl.commons",
    "name" "float-pattern",
    "column" 1,
    "line" 39,
    "source"
    "(def ^Pattern float-pattern #\"([-+]?[0-9]+(\\.[0-9]*)?([eE][-+]?[0-9]+)?)(M)?\")",
    "file" "clojure/tools/reader/impl/commons.clj",
    "tag" "java.util.regex.Pattern"}],
  "clojure.tools.reader.impl.ExceptionInfo"
  [{"arglists" [["this"]],
    "ns" "clojure.tools.reader.impl.ExceptionInfo",
    "name" "-getData",
    "column" 1,
    "line" 15,
    "source" "(defn -getData [this]\n  (.data this))",
    "file" "clojure/tools/reader/impl/ExceptionInfo.clj",
    "tag" nil}
   {"arglists" [["s" "data"] ["s" "data" "throwable"]],
    "ns" "clojure.tools.reader.impl.ExceptionInfo",
    "name" "-init",
    "column" 1,
    "line" 9,
    "source"
    "(defn -init\n  ([s data]\n     [[s] data])\n  ([s data throwable]\n     [[s throwable] data]))",
    "file" "clojure/tools/reader/impl/ExceptionInfo.clj",
    "tag" nil}
   {"arglists" [["this"]],
    "ns" "clojure.tools.reader.impl.ExceptionInfo",
    "name" "-toString",
    "column" 1,
    "line" 18,
    "source"
    "(defn -toString [this]\n  (str \"clojure.toold.reader.ExceptionInfo: \" (.getMessage this) \" \" (str (.data this))))",
    "file" "clojure/tools/reader/impl/ExceptionInfo.clj",
    "tag" nil}],
  "clojure.tools.reader.edn"
  [{"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "wrapping-reader",
    "line" 263,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- wrapping-reader\n  [sym]\n  (fn [rdr _ opts]\n    (list sym (read rdr true nil opts))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "escape-char",
    "line" 194,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- escape-char [sb rdr]\n  (let [ch (read-char rdr)]\n    (case ch\n      \\t \"\\t\"\n      \\r \"\\r\"\n      \\n \"\\n\"\n      \\\\ \"\\\\\"\n      \\\" \"\\\"\"\n      \\b \"\\b\"\n      \\f \"\\f\"\n      \\u (let [ch (read-char rdr)]\n           (if (== -1 (Character/digit (char ch) 16))\n             (reader-error rdr \"Invalid unicode escape: \\\\u\" ch)\n             (read-unicode-char rdr ch 16 4 true)))\n      \\x (let [ch (read-char rdr)]\n           (if (== -1 (Character/digit (char ch) 16))\n             (reader-error rdr \"Invalid unicode escape: \\\\x\" ch)\n             (read-unicode-char rdr ch 16 2 true)))\n      (if (numeric? ch)\n        (let [ch (read-unicode-char rdr ch 8 3 false)]\n          (if (> (int ch) 0337)\n            (reader-error rdr \"Octal escape sequence must be in range [0, 377]\")\n            ch))\n        (reader-error rdr \"Unsupported escape character: \\\\\" ch)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["sb" "rdr"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-list",
    "line" 165,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-list\n  [rdr _ opts]\n  (let [the-list (read-delimited \\) rdr opts)]\n    (if (empty? the-list)\n      '()\n      (clojure.lang.PersistentList/create the-list))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "macros",
    "line" 287,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- macros [ch]\n  (case ch\n    \\\" read-string*\n    \\: read-keyword\n    \\; read-comment\n    \\^ read-meta\n    \\( read-list\n    \\) read-unmatched-delimiter\n    \\[ read-vector\n    \\] read-unmatched-delimiter\n    \\{ read-map\n    \\} read-unmatched-delimiter\n    \\\\ read-char*\n    \\# read-dispatch\n    nil))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-map",
    "line" 176,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-map\n  [rdr _ opts]\n  (let [l (to-array (read-delimited \\} rdr opts))]\n    (when (== 1 (bit-and (alength l) 1))\n      (reader-error rdr \"Map literal must contain an even number of forms\"))\n    (RT/map l)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"ns" "clojure.tools.reader.edn",
    "name" "read",
    "line" 328,
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
    "line" 278,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-set\n  [rdr _ opts]\n  (PersistentHashSet/createWithCheck (read-delimited \\} rdr opts)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-symbol",
    "line" 230,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-symbol\n  [rdr initch]\n  (when-let [token (read-token rdr initch)]\n    (case token\n\n      ;; special symbols\n      \"nil\" nil\n      \"true\" true\n      \"false\" false\n      \"/\" '/\n      \"NaN\" Double/NaN\n      \"-Infinity\" Double/NEGATIVE_INFINITY\n      (\"Infinity\" \"+Infinity\") Double/POSITIVE_INFINITY\n\n      (or (when-let [p (parse-symbol token)]\n            (symbol (p 0) (p 1)))\n          (reader-error rdr \"Invalid token: \" token)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "initch"]]}
   {"ns" "clojure.tools.reader.edn",
    "name" "read-string",
    "line" 375,
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
    "line" 268,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-meta\n  [rdr _ opts]\n  (let [m (desugar-meta (read rdr true nil opts))]\n    (when-not (map? m)\n      (reader-error rdr \"Metadata must be Symbol, Keyword, String or Map\"))\n    (let [o (read rdr true nil opts)]\n      (if (instance? IMeta o)\n        (with-meta o (merge (meta o) m))\n        (reader-error rdr \"Metadata can only be applied to IMetas\")))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-number",
    "line" 183,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-number\n  [reader initch opts]\n  (loop [sb (doto (StringBuilder.) (.append initch))\n         ch (read-char reader)]\n    (if (or (whitespace? ch) (macros ch) (nil? ch))\n      (let [s (str sb)]\n        (unread reader ch)\n        (or (match-number s)\n            (reader-error reader \"Invalid number format [\" s \"]\")))\n      (recur (doto sb (.append ch)) (read-char reader)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["reader" "initch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-discard",
    "line" 282,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-discard\n  [rdr _ opts]\n  (read rdr true nil opts)\n  rdr)",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-vector",
    "line" 172,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-vector\n  [rdr _ opts]\n  (read-delimited \\] rdr opts))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "dispatch-macros",
    "line" 303,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- dispatch-macros [ch]\n  (case ch\n    \\^ read-meta                ;deprecated\n    \\{ read-set\n    \\< (throwing-reader \"Unreadable form\")\n    \\! read-comment\n    \\_ read-discard\n    nil))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-keyword",
    "line" 248,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-keyword\n  [reader initch opts]\n  (let [ch (read-char reader)]\n    (if-not (whitespace? ch)\n      (let [token (read-token reader ch)\n            s (parse-symbol token)]\n        (if (and s (== -1 (.indexOf token \"::\")))\n          (let [^String ns (s 0)\n                ^String name (s 1)]\n            (if (identical? \\: (nth token 0))\n              (reader-error reader \"Invalid token: :\" token) ;; no ::keyword in edn\n              (keyword ns name)))\n          (reader-error reader \"Invalid token: :\" token)))\n      (reader-error reader \"Invalid token: :\"))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["reader" "initch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-unmatched-delimiter",
    "line" 62,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unmatched-delimiter\n  [rdr ch opts]\n  (reader-error rdr \"Unmatched delimiter \" ch))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "ch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-tagged",
    "line" 312,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-tagged [rdr initch opts]\n  (let [tag (read rdr true nil opts)\n        object (read rdr true nil opts)]\n    (if-not (symbol? tag)\n      (reader-error rdr \"Reader tag must be a symbol\"))\n    (if-let [f (or (get (:readers opts) tag)\n                   (default-data-readers tag))]\n      (f object)\n      (if-let [d (:default opts)]\n        (d tag object)\n        (reader-error rdr \"No reader function for tag \" (name tag))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "initch" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "not-constituent?",
    "line" 22,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- not-constituent? [ch]\n  (or (identical? \\@ ch)\n      (identical? \\` ch)\n      (identical? \\~ ch)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "macro-terminating?",
    "line" 16,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- macro-terminating? [ch]\n  (and (not (identical? \\# ch))\n       (not (identical? \\' ch))\n       (not (identical? \\: ch))\n       (macros ch)))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-string*",
    "line" 219,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-string*\n  [reader _ opts]\n  (loop [sb (StringBuilder.)\n         ch (read-char reader)]\n    (case ch\n      nil (reader-error reader \"EOF while reading string\")\n      \\\\ (recur (doto sb (.append (escape-char sb reader)))\n                (read-char reader))\n      \\\" (str sb)\n      (recur (doto sb (.append ch)) (read-char reader)))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["reader" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-token",
    "line" 27,
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
    "line" 104,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:const lower-limit (int \\uE000))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-delimited",
    "line" 146,
    "column" 1,
    "tag" "clojure.lang.PersistentVector",
    "source"
    "(defn- ^PersistentVector read-delimited\n  [delim rdr opts]\n  (let [first-line (when (indexing-reader? rdr)\n                     (get-line-number rdr))\n        delim (char delim)]\n    (loop [a (transient [])]\n      (let [ch (read-past whitespace? rdr)]\n        (when-not ch\n          (reader-error rdr \"EOF while reading\"\n                        (if first-line\n                          (str \", starting at line\" first-line))))\n        (if (identical? delim (char ch))\n          (persistent! a)\n          (if-let [macrofn (macros ch)]\n            (let [mret (macrofn rdr ch opts)]\n              (recur (if-not (identical? mret rdr) (conj! a mret) a)))\n            (let [o (read (doto rdr (unread ch)) true nil opts)]\n              (recur (if-not (identical? o rdr) (conj! a o) a)))))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["delim" "rdr" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-dispatch",
    "line" 52,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-dispatch\n  [rdr _ opts]\n  (if-let [ch (read-char rdr)]\n    (if-let [dm (dispatch-macros ch)]\n      (dm rdr ch opts)\n      (if-let [obj (read-tagged (doto rdr (unread ch)) ch opts)]\n        obj\n        (reader-error rdr \"No dispatch macro for \" ch)))\n    (reader-error rdr \"EOF while reading character\")))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "_" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-char*",
    "line" 106,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-char*\n  [rdr backslash opts]\n  (let [ch (read-char rdr)]\n    (if-not (nil? ch)\n      (let [token (read-token rdr ch false)\n            token-len (count token)]\n        (cond\n\n         (== 1 token-len)  (Character/valueOf (nth token 0))\n\n         (= token \"newline\") \\newline\n         (= token \"space\") \\space\n         (= token \"tab\") \\tab\n         (= token \"backspace\") \\backspace\n         (= token \"formfeed\") \\formfeed\n         (= token \"return\") \\return\n\n         (.startsWith token \"u\")\n         (let [c (read-unicode-char token 1 4 16)\n               ic (int c)]\n           (if (and (> ic upper-limit)\n                    (< ic lower-limit))\n             (reader-error rdr \"Invalid character constant: \\\\u\" (Integer/toString ic 16))\n             c))\n\n         (.startsWith token \"x\")\n         (read-unicode-char token 1 2 16)\n\n         (.startsWith token \"o\")\n         (let [len (dec token-len)]\n           (if (> len 3)\n             (reader-error rdr \"Invalid octal escape sequence length: \" len)\n             (let [uc (read-unicode-char token 1 len 8)]\n               (if (> (int uc) 0377)\n                 (reader-error rdr \"Octal escape sequence must be in range [0, 377]\")\n                 uc))))\n\n         :else (reader-error rdr \"Unsupported character: \\\\\" token)))\n      (reader-error rdr \"EOF while reading character\"))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists" [["rdr" "backslash" "opts"]]}
   {"private" true,
    "ns" "clojure.tools.reader.edn",
    "name" "read-unicode-char",
    "line" 70,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unicode-char\n  ([^String token offset length base]\n     (let [l (+ offset length)]\n       (when-not (== (count token) l)\n         (throw (IllegalArgumentException. (str \"Invalid unicode character: \\\\\" token))))\n       (loop [i offset uc 0]\n         (if (== i l)\n           (char uc)\n           (let [d (Character/digit (char (nth token i)) (int base))]\n             (if (== d -1)\n               (throw (IllegalArgumentException. (str \"Invalid digit: \" (nth token i))))\n               (recur (inc i) (long (+ d (* uc base))))))))))\n\n  ([rdr initch base length exact?]\n     (loop [i 1 uc (Character/digit (char initch) (int base))]\n       (if (== uc -1)\n         (throw (IllegalArgumentException. (str \"Invalid digit: \" initch)))\n         (if-not (== i length)\n           (let [ch (peek-char rdr)]\n             (if (or (whitespace? ch)\n                     (macros ch)\n                     (nil? ch))\n               (if exact?\n                 (throw (IllegalArgumentException.\n                         (str \"Invalid character length: \" i \", should be: \" length)))\n                 (char uc))\n               (let [d (Character/digit (char ch) (int base))]\n                 (read-char rdr)\n                 (if (== d -1)\n                   (throw (IllegalArgumentException. (str \"Invalid digit: \" ch)))\n                   (recur (inc i) (long (+ d (* uc base))))))))\n           (char uc))))))",
    "file" "clojure/tools/reader/edn.clj",
    "arglists"
    [["token" "offset" "length" "base"]
     ["rdr" "initch" "base" "length" "exact?"]]}
   {"private" true,
    "const" true,
    "ns" "clojure.tools.reader.edn",
    "name" "upper-limit",
    "line" 103,
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
    "(defn- print-timestamp\n  \"Print a java.sql.Timestamp as RFC3339 timestamp, always in UTC.\"\n  [^java.sql.Timestamp ts, ^java.io.Writer w]\n  (let [utc-format (.get thread-local-utc-timestamp-format)]\n    (.write w \"#inst \\\"\")\n    (.write w (.format utc-format ts))\n    ;; add on nanos and offset\n    ;; RFC3339 says to use -00:00 when the timezone is unknown (+00:00 implies a known GMT)\n    (.write w (format \".%09d-00:00\" (.getNanos ts)))\n    (.write w \"\\\"\")))",
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
    "(defn- print-date\n  \"Print a java.util.Date as RFC3339 timestamp, always in UTC.\"\n  [^java.util.Date d, ^java.io.Writer w]\n  (let [utc-format (.get thread-local-utc-date-format)]\n    (.write w \"#inst \\\"\")\n    (.write w (.format utc-format d))\n    (.write w \"\\\"\")))",
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
    "tag" nil,
    "source"
    "(def ^:private thread-local-utc-timestamp-format\n  ;; SimpleDateFormat is not thread-safe, so we use a ThreadLocal proxy for access.\n  ;; http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4228335\n  (proxy [ThreadLocal] []\n    (initialValue []\n      (doto (java.text.SimpleDateFormat. \"yyyy-MM-dd'T'HH:mm:ss\")\n        (.setTimeZone (java.util.TimeZone/getTimeZone \"GMT\"))))))",
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
    "tag" nil,
    "source"
    "(def ^:private thread-local-utc-date-format\n  ;; SimpleDateFormat is not thread-safe, so we use a ThreadLocal proxy for access.\n  ;; http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4228335\n  (proxy [ThreadLocal] []\n    (initialValue []\n      (doto (java.text.SimpleDateFormat. \"yyyy-MM-dd'T'HH:mm:ss.SSS-00:00\")\n        ;; RFC3339 says to use -00:00 when the timezone is unknown (+00:00 implies a known GMT)\n        (.setTimeZone (java.util.TimeZone/getTimeZone \"GMT\"))))))",
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
    "arglists" [["msg"]]}],
  "clojure.tools.reader"
  [{"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-unquote",
    "line" 410,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unquote\n  [rdr comma]\n  (if-let [ch (peek-char rdr)]\n    (if (identical? \\@ ch)\n      ((wrapping-reader 'clojure.core/unquote-splicing) (doto rdr read-char) \\@)\n      ((wrapping-reader 'clojure.core/unquote) rdr \\~))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "comma"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "arg-env",
    "line" 318,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:dynamic arg-env)",
    "file" "clojure/tools/reader.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "wrapping-reader",
    "line" 285,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- wrapping-reader\n  [sym]\n  (fn [rdr _]\n    (list sym (read rdr true nil true))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "escape-char",
    "line" 203,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- escape-char [sb rdr]\n  (let [ch (read-char rdr)]\n    (case ch\n      \\t \"\\t\"\n      \\r \"\\r\"\n      \\n \"\\n\"\n      \\\\ \"\\\\\"\n      \\\" \"\\\"\"\n      \\b \"\\b\"\n      \\f \"\\f\"\n      \\u (let [ch (read-char rdr)]\n           (if (== -1 (Character/digit (char ch) 16))\n             (reader-error rdr \"Invalid unicode escape: \\\\u\" ch)\n             (read-unicode-char rdr ch 16 4 true)))\n      \\x (let [ch (read-char rdr)]\n           (if (== -1 (Character/digit (char ch) 16))\n             (reader-error rdr \"Invalid unicode escape: \\\\x\" ch)\n             (read-unicode-char rdr ch 16 2 true)))\n      (if (numeric? ch)\n        (let [ch (read-unicode-char rdr ch 8 3 false)]\n          (if (> (int ch) 0337)\n            (reader-error rdr \"Octal escape sequence must be in range [0, 377]\")\n            ch))\n        (reader-error rdr \"Unsupported escape character: \\\\\" ch)))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["sb" "rdr"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-list",
    "line" 157,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-list\n  [rdr _]\n  (let [[line column] (when (indexing-reader? rdr)\n                        [(get-line-number rdr) (int (dec (get-column-number rdr)))])\n        the-list (read-delimited \\) rdr true)]\n    (if (empty? the-list)\n      '()\n      (with-meta (clojure.lang.PersistentList/create the-list)\n        (when line\n          {:line line :column column})))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "expand-list",
    "line" 426,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- expand-list [s]\n  (loop [s (seq s) r (transient [])]\n    (if s\n      (let [item (first s)\n            ret (conj! r\n                       (cond\n                        (unquote? item)          (list 'clojure.core/list (second item))\n                        (unquote-splicing? item) (second item)\n                        :else                    (list 'clojure.core/list (syntax-quote* item))))]\n        (recur (next s) ret))\n      (seq (persistent! r)))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "macros",
    "line" 544,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- macros [ch]\n  (case ch\n    \\\" read-string*\n    \\: read-keyword\n    \\; read-comment\n    \\' (wrapping-reader 'quote)\n    \\@ (wrapping-reader 'clojure.core/deref)\n    \\^ read-meta\n    \\` read-syntax-quote ;;(wrapping-reader 'syntax-quote)\n    \\~ read-unquote\n    \\( read-list\n    \\) read-unmatched-delimiter\n    \\[ read-vector\n    \\] read-unmatched-delimiter\n    \\{ read-map\n    \\} read-unmatched-delimiter\n    \\\\ read-char*\n    \\% read-arg\n    \\# read-dispatch\n    nil))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["ch"]]}
   {"ns" "clojure.tools.reader",
    "name" "*data-readers*",
    "line" 649,
    "column" 1,
    "doc"
    "Map from reader tag symbols to data reader Vars.\n   Reader tags without namespace qualifiers are reserved for Clojure.\n   Default reader tags are defined in clojure.tools.reader/default-data-readers\n   and may be overridden by binding this Var.",
    "tag" nil,
    "source"
    "(def ^:dynamic *data-readers*\n  \"Map from reader tag symbols to data reader Vars.\n   Reader tags without namespace qualifiers are reserved for Clojure.\n   Default reader tags are defined in clojure.tools.reader/default-data-readers\n   and may be overridden by binding this Var.\"\n  {})",
    "file" "clojure/tools/reader.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-eval",
    "line" 378,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-eval\n  [rdr _]\n  (when-not *read-eval*\n    (reader-error rdr \"#= not allowed when *read-eval* is false\"))\n  (let [o (read rdr true nil true)]\n    (if (symbol? o)\n      (RT/classForName (str ^Symbol o))\n      (if (list? o)\n        (let [fs (first o)\n              o (rest o)\n              fs-name (name fs)]\n          (cond\n           (= fs 'var) (let [vs (first o)]\n                         (RT/var (namespace vs) (name vs)))\n           (.endsWith fs-name \".\")\n           (let [args (to-array o)]\n             (-> fs-name (subs 0 (dec (count fs-name)))\n                 RT/classForName (Reflector/invokeConstructor args)))\n\n           (Compiler/namesStaticMember fs)\n           (let [args (to-array o)]\n             (Reflector/invokeStaticMethod (namespace fs) fs-name args))\n\n           :else\n           (let [v (Compiler/maybeResolveIn *ns* fs)]\n             (if (var? v)\n               (apply v o)\n               (reader-error rdr \"Can't resolve \" fs)))))\n        (throw (IllegalArgumentException. \"Unsupported #= form\"))))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "resolve-symbol",
    "line" 457,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- resolve-symbol [s]\n  (if (pos? (.indexOf (name s) \".\"))\n    s\n    (if-let [ns-str (namespace s)]\n      (let [^Namespace ns (resolve-ns (symbol ns-str))]\n        (if (or (nil? ns)\n                (= (name (ns-name ns)) ns-str)) ;; not an alias\n          s\n          (symbol (name (.name ns)) (name s))))\n      (if-let [o ((ns-map *ns*) s)]\n        (if (class? o)\n          (symbol (.getName ^Class o))\n          (if (var? o)\n            (symbol (-> ^Var o .ns .name name) (-> ^Var o .sym name))))\n        (symbol (name (ns-name *ns*)) (name s))))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-map",
    "line" 177,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-map\n  [rdr _]\n  (let [[line column] (when (indexing-reader? rdr)\n                        [(get-line-number rdr) (int (dec (get-column-number rdr)))])\n        the-map (read-delimited \\} rdr true)\n        map-count (count the-map)]\n    (when (odd? map-count)\n      (reader-error rdr \"Map literal must contain an even number of forms\"))\n    (with-meta\n      (if (zero? map-count)\n        {}\n        (RT/map (to-array the-map)))\n      (when line\n        {:line line :column column}))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"ns" "clojure.tools.reader",
    "name" "read",
    "line" 669,
    "column" 1,
    "doc"
    "Reads the first object from an IPushbackReader or a java.io.PushbackReader.\n   Returns the object read. If EOF, throws if eof-error? is true.\n   Otherwise returns sentinel. If no stream is providen, *in* will be used.\n\n   ***WARNING***\n   Note that read can execute code (controlled by *read-eval*),\n   and as such should be used only with trusted sources.\n\n   To read data structures only, use clojure.tools.reader.edn/read\n\n   Note that the function signature of clojure.tools.reader/read and\n   clojure.tools.reader.edn/read is not the same for eof-handling",
    "tag" nil,
    "source"
    "(defn read\n  \"Reads the first object from an IPushbackReader or a java.io.PushbackReader.\n   Returns the object read. If EOF, throws if eof-error? is true.\n   Otherwise returns sentinel. If no stream is providen, *in* will be used.\n\n   ***WARNING***\n   Note that read can execute code (controlled by *read-eval*),\n   and as such should be used only with trusted sources.\n\n   To read data structures only, use clojure.tools.reader.edn/read\n\n   Note that the function signature of clojure.tools.reader/read and\n   clojure.tools.reader.edn/read is not the same for eof-handling\"\n  ([] (read *in*))\n  ([reader] (read reader true nil))\n  ([reader eof-error? sentinel] (read reader eof-error? sentinel false))\n  ([reader eof-error? sentinel recursive?]\n     (when (= :unknown *read-eval*)\n       (reader-error \"Reading disallowed - *read-eval* bound to :unknown\"))\n     (try\n       (let [ch (read-char reader)]\n         (cond\n          (whitespace? ch) (read reader eof-error? sentinel recursive?)\n          (nil? ch) (if eof-error? (reader-error reader \"EOF\") sentinel)\n          (number-literal? reader ch) (read-number reader ch)\n          (comment-prefix? ch) (read (read-comment reader ch) eof-error? sentinel recursive?)\n          :else (let [f (macros ch)]\n                  (if f\n                    (let [res (f reader ch)]\n                      (if (identical? res reader)\n                        (read reader eof-error? sentinel recursive?)\n                        res))\n                    (read-symbol reader ch)))))\n       (catch Exception e\n         (if (ex-info? e)\n           (throw e)\n           (throw (ex-info (.getMessage e)\n                           (merge {:type :reader-exception}\n                                  (if (indexing-reader? reader)\n                                    {:line (get-line-number reader)\n                                     :column (get-column-number reader)}))\n                           e)))))))",
    "file" "clojure/tools/reader.clj",
    "arglists"
    [[]
     ["reader"]
     ["reader" "eof-error?" "sentinel"]
     ["reader" "eof-error?" "sentinel" "recursive?"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "register-arg",
    "line" 346,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- register-arg [n]\n  (if (thread-bound? #'arg-env)\n    (if-let [ret (arg-env n)]\n      ret\n      (let [g (garg n)]\n        (set! arg-env (assoc arg-env n g))\n        g))\n    (throw (IllegalStateException. \"Arg literal not in #()\"))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.tools.reader",
    "name" "*read-eval*",
    "line" 631,
    "column" 1,
    "doc"
    "Defaults to true.\n\n   ***WARNING***\n   This setting implies that the full power of the reader is in play,\n   including syntax that can cause code to execute. It should never be\n   used with untrusted sources. See also: clojure.tools.reader.edn/read.\n\n   When set to logical false in the thread-local binding,\n   the eval reader (#=) and *record/type literal syntax* are disabled in read/load.\n   Example (will fail): (binding [*read-eval* false] (read-string \"#=(* 2 21)\"))\n\n   When set to :unknown all reads will fail in contexts where *read-eval*\n   has not been explicitly bound to either true or false. This setting\n   can be a useful diagnostic tool to ensure that all of your reads\n   occur in considered contexts.",
    "tag" nil,
    "source"
    "(def ^:dynamic *read-eval*\n  \"Defaults to true.\n\n   ***WARNING***\n   This setting implies that the full power of the reader is in play,\n   including syntax that can cause code to execute. It should never be\n   used with untrusted sources. See also: clojure.tools.reader.edn/read.\n\n   When set to logical false in the thread-local binding,\n   the eval reader (#=) and *record/type literal syntax* are disabled in read/load.\n   Example (will fail): (binding [*read-eval* false] (read-string \\\"#=(* 2 21)\\\"))\n\n   When set to :unknown all reads will fail in contexts where *read-eval*\n   has not been explicitly bound to either true or false. This setting\n   can be a useful diagnostic tool to ensure that all of your reads\n   occur in considered contexts.\"\n  true)",
    "file" "clojure/tools/reader.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-set",
    "line" 309,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-set\n  [rdr _]\n  (PersistentHashSet/createWithCheck (read-delimited \\} rdr true)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "gensym-env",
    "line" 408,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:dynamic gensym-env nil)",
    "file" "clojure/tools/reader.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.tools.reader",
    "name" "read-symbol",
    "line" 355,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source" "(declare read-symbol)",
    "file" "clojure/tools/reader.clj",
    "arglists" nil}
   {"ns" "clojure.tools.reader",
    "name" "read-string",
    "line" 712,
    "column" 1,
    "doc"
    "Reads one object from the string s.\n   Returns nil when s is nil or empty.\n\n   ***WARNING***\n   Note that read-string can execute code (controlled by *read-eval*),\n   and as such should be used only with trusted sources.\n\n   To read data structures only, use clojure.tools.reader.edn/read-string\n\n   Note that the function signature of clojure.tools.reader/read-string and\n   clojure.tools.reader.edn/read-string is not the same for eof-handling",
    "tag" nil,
    "source"
    "(defn read-string\n  \"Reads one object from the string s.\n   Returns nil when s is nil or empty.\n\n   ***WARNING***\n   Note that read-string can execute code (controlled by *read-eval*),\n   and as such should be used only with trusted sources.\n\n   To read data structures only, use clojure.tools.reader.edn/read-string\n\n   Note that the function signature of clojure.tools.reader/read-string and\n   clojure.tools.reader.edn/read-string is not the same for eof-handling\"\n  [s]\n  (when (and s (not (identical? s \"\")))\n    (read (string-push-back-reader s) true nil false)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-meta",
    "line" 290,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-meta\n  [rdr _]\n  (let [[line column] (when (indexing-reader? rdr)\n                        [(get-line-number rdr) (int (dec (get-column-number rdr)))])\n        m (desugar-meta (read rdr true nil true))]\n    (when-not (map? m)\n      (reader-error rdr \"Metadata must be Symbol, Keyword, String or Map\"))\n    (let [o (read rdr true nil true)]\n      (if (instance? IMeta o)\n        (let [m (if (and line\n                         (seq? o))\n                  (assoc m :line line\n                           :column column)\n                  m)]\n          (if (instance? IObj o)\n            (with-meta o (merge (meta o) m))\n            (reset-meta! o m)))\n        (reader-error rdr \"Metadata can only be applied to IMetas\")))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "unquote-splicing?",
    "line" 418,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- unquote-splicing? [form]\n  (and (seq? form)\n       (= (first form) 'clojure.core/unquote-splicing)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "resolve-ns",
    "line" 261,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- resolve-ns [sym]\n  (or ((ns-aliases *ns*) sym)\n      (find-ns sym)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "syntax-quote-coll",
    "line" 479,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- syntax-quote-coll [type coll]\n  (let [res (list 'clojure.core/seq\n                  (cons 'clojure.core/concat\n                        (expand-list coll)))]\n    (if type\n      (list 'clojure.core/apply type res)\n      res)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["type" "coll"]]}
   {"ns" "clojure.tools.reader",
    "name" "default-data-readers",
    "line" 663,
    "column" 1,
    "doc"
    "Default map of data reader functions provided by Clojure.\n   May be overridden by binding *data-readers*",
    "tag" nil,
    "source"
    "(def default-data-readers\n  \"Default map of data reader functions provided by Clojure.\n   May be overridden by binding *data-readers*\"\n  {'inst #'data-readers/read-instant-date\n   'uuid #'data-readers/default-uuid-reader})",
    "file" "clojure/tools/reader.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "flatten-map",
    "line" 438,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- flatten-map [form]\n  (loop [s (seq form) key-vals (transient [])]\n    (if s\n      (let [e (first s)]\n        (recur (next s) (-> key-vals\n                            (conj! (key e))\n                            (conj! (val e)))))\n      (seq (persistent! key-vals)))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.reader",
    "name" "syntax-quote",
    "macro" true,
    "line" 728,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro syntax-quote [form]\n  \"Macro equivalent to the syntax-quote reader macro (`).\"\n  (binding [gensym-env {}]\n    (syntax-quote* form)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-fn",
    "line" 324,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-fn\n  [rdr _]\n  (if (thread-bound? #'arg-env)\n    (throw (IllegalStateException. \"Nested #()s are not allowed\")))\n  (binding [arg-env (sorted-map)]\n    (let [form (read (doto rdr (unread \\()) true nil true) ;; this sets bindings\n          rargs (rseq arg-env)\n          args (if rargs\n                 (let [higharg (key (first rargs))]\n                   (if (pos? higharg)\n                     (let [args (loop [i 1 args (transient [])]\n                                  (if (> i higharg)\n                                    (persistent! args)\n                                    (recur (inc i) (conj! args (or (get arg-env i)\n                                                                   (garg i))))))\n                           args (if (arg-env -1)\n                                  (conj args '& (arg-env -1))\n                                  args)]\n                       args)))\n                 [])]\n      (list 'fn* args form))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "register-gensym",
    "line" 447,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- register-gensym [sym]\n  (if-not gensym-env\n    (throw (IllegalStateException. \"Gensym literal not in syntax-quote\")))\n  (or (get gensym-env sym)\n      (let [gs (symbol (str (subs (name sym)\n                                  0 (dec (count (name sym))))\n                            \"__\" (RT/nextID) \"__auto__\"))]\n        (set! gensym-env (assoc gensym-env sym gs))\n        gs)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-number",
    "line" 192,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-number\n  [reader initch]\n  (loop [sb (doto (StringBuilder.) (.append initch))\n         ch (read-char reader)]\n    (if (or (whitespace? ch) (macros ch) (nil? ch))\n      (let [s (str sb)]\n        (unread reader ch)\n        (or (match-number s)\n            (reader-error reader \"Invalid number format [\" s \"]\")))\n      (recur (doto sb (.append ch)) (read-char reader)))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["reader" "initch"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "garg",
    "line" 320,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- garg [n]\n  (symbol (str (if (== -1 n) \"rest\" (str \"p\" n))\n               \"__\" (RT/nextID) \"#\")))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["n"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "add-meta",
    "line" 473,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- add-meta [form ret]\n  (if (and (instance? IObj form)\n           (dissoc (meta form) :line :column))\n    (list 'clojure.core/with-meta ret (syntax-quote* (meta form)))\n    ret))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["form" "ret"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-discard",
    "line" 313,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-discard\n  [rdr _]\n  (read rdr true nil true)\n  rdr)",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-vector",
    "line" 168,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-vector\n  [rdr _]\n  (let [[line column] (when (indexing-reader? rdr)\n                        [(get-line-number rdr) (int (dec (get-column-number rdr)))])\n        the-vector (read-delimited \\] rdr true)]\n    (with-meta the-vector\n      (when line\n        {:line line :column column}))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "dispatch-macros",
    "line" 565,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- dispatch-macros [ch]\n  (case ch\n    \\^ read-meta                ;deprecated\n    \\' (wrapping-reader 'var)\n    \\( read-fn\n    \\= read-eval\n    \\{ read-set\n    \\< (throwing-reader \"Unreadable form\")\n    \\\" read-regex\n    \\! read-comment\n    \\_ read-discard\n    nil))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "syntax-quote*",
    "line" 487,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- syntax-quote* [form]\n  (->>\n   (cond\n    (special-symbol? form) (list 'quote form)\n\n    (symbol? form)\n    (list 'quote\n          (if (namespace form)\n            (let [maybe-class ((ns-map *ns*)\n                               (symbol (namespace form)))]\n              (if (class? class)\n                (symbol (.getName ^Class maybe-class) (name form))\n                (resolve-symbol form)))\n            (let [sym (name form)]\n              (cond\n               (.endsWith sym \"#\")\n               (register-gensym form)\n\n               (.startsWith sym \".\")\n               form\n\n               (.endsWith sym \".\")\n               (let [csym (symbol (subs sym 0 (dec (count sym))))]\n                 (symbol (.concat (name (resolve-symbol csym)) \".\")))\n               :else (resolve-symbol form)))))\n\n    (unquote? form) (second form)\n    (unquote-splicing? form) (throw (IllegalStateException. \"splice not in list\"))\n\n    (coll? form)\n    (cond\n     (instance? IRecord form) form\n     (map? form) (syntax-quote-coll 'clojure.core/hash-map (flatten-map form))\n     (vector? form) (syntax-quote-coll 'clojure.core/vector form)\n     (set? form) (syntax-quote-coll 'clojure.core/hash-set form)\n     (or (seq? form) (list? form))\n     (let [seq (seq form)]\n       (if seq\n         (syntax-quote-coll nil seq)\n         '(clojure.core/list)))\n     :else (throw (UnsupportedOperationException. \"Unknown Collection type\")))\n\n    (or (keyword? form)\n        (number? form)\n        (char? form)\n        (string? form))\n    form\n\n    :else (list 'quote form))\n   (add-meta form)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.reader",
    "name" "*default-data-reader-fn*",
    "line" 656,
    "column" 1,
    "doc"
    "When no data reader is found for a tag and *default-data-reader-fn*\n   is non-nil, it will be called with two arguments, the tag and the value.\n   If *default-data-reader-fn* is nil (the default value), an exception\n   will be thrown for the unknown tag.",
    "tag" nil,
    "source"
    "(def ^:dynamic *default-data-reader-fn*\n  \"When no data reader is found for a tag and *default-data-reader-fn*\n   is non-nil, it will be called with two arguments, the tag and the value.\n   If *default-data-reader-fn* is nil (the default value), an exception\n   will be thrown for the unknown tag.\"\n  nil)",
    "file" "clojure/tools/reader.clj",
    "dynamic" true,
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-keyword",
    "line" 265,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-keyword\n  [reader initch]\n  (let [ch (read-char reader)]\n    (if-not (whitespace? ch)\n      (let [token (read-token reader ch)\n            s (parse-symbol token)]\n        (if (and s (== -1 (.indexOf token \"::\")))\n          (let [^String ns (s 0)\n                ^String name (s 1)]\n            (if (identical? \\: (nth token 0))\n              (if ns\n                (let [ns (resolve-ns (symbol (subs ns 1)))]\n                  (if ns\n                    (keyword (str ns) name)\n                    (reader-error reader \"Invalid token: :\" token)))\n                (keyword (str *ns*) (subs name 1)))\n              (keyword ns name)))\n          (reader-error reader \"Invalid token: :\" token)))\n      (reader-error reader \"Invalid token: :\"))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["reader" "initch"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-ctor",
    "line" 582,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-ctor [rdr class-name]\n  (when-not *read-eval*\n    (reader-error \"Record construction syntax can only be used when *read-eval* == true\"))\n  (let [class (Class/forName (name class-name) false (RT/baseLoader))\n        ch (read-past whitespace? rdr)] ;; differs from clojure\n    (if-let [[end-ch form] (case ch\n                             \\[ [\\] :short]\n                             \\{ [\\} :extended]\n                             nil)]\n      (let [entries (to-array (read-delimited end-ch rdr true))\n            all-ctors (.getConstructors class)\n            ctors-num (count all-ctors)]\n        (case form\n          :short\n          (loop [i 0]\n            (if (> i ctors-num)\n              (reader-error rdr \"Unexpected number of constructor arguments to \" (str class)\n                            \": got\" (count entries))\n              (if (== (count (.getParameterTypes ^Constructor (aget all-ctors i)))\n                      ctors-num)\n                (Reflector/invokeConstructor class entries)\n                (recur (inc i)))))\n          :extended\n          (let [vals (RT/map entries)]\n            (loop [s (keys vals)]\n              (if s\n                (if-not (keyword? (first s))\n                  (reader-error rdr \"Unreadable ctor form: key must be of type clojure.lang.Keyword\")\n                  (recur (next s)))))\n            (Reflector/invokeStaticMethod class \"create\" (object-array [vals])))))\n      (reader-error rdr \"Invalid reader constructor form\"))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "class-name"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-unmatched-delimiter",
    "line" 55,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unmatched-delimiter\n  [rdr ch]\n  (reader-error rdr \"Unmatched delimiter \" ch))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-tagged",
    "line" 614,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-tagged [rdr initch]\n  (let [tag (read rdr true nil false)]\n    (if-not (symbol? tag)\n      (reader-error rdr \"Reader tag must be a symbol\"))\n    (if-let [f (or (*data-readers* tag)\n                   (default-data-readers tag))]\n      (read-tagged* rdr tag f)\n      (if (.contains (name tag) \".\")\n        (read-ctor rdr tag)\n        (if-let [f *default-data-reader-fn*]\n          (f tag (read rdr true nil true))\n          (reader-error rdr \"No reader function for tag \" (name tag)))))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "initch"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-syntax-quote",
    "line" 538,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-syntax-quote\n  [rdr backquote]\n  (binding [gensym-env {}]\n    (-> (read rdr true nil true)\n        syntax-quote*)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "backquote"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "macro-terminating?",
    "line" 25,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- macro-terminating? [ch]\n  (case ch\n    (\\\" \\; \\@ \\^ \\` \\~ \\( \\) \\[ \\] \\{ \\} \\\\) true\n    false))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["ch"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-string*",
    "line" 228,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-string*\n  [reader _]\n  (loop [sb (StringBuilder.)\n         ch (read-char reader)]\n    (case ch\n      nil (reader-error reader \"EOF while reading string\")\n      \\\\ (recur (doto sb (.append (escape-char sb reader)))\n                (read-char reader))\n      \\\" (str sb)\n      (recur (doto sb (.append ch)) (read-char reader)))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["reader" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-token",
    "line" 30,
    "column" 1,
    "tag" "java.lang.String",
    "source"
    "(defn- ^String read-token\n  [rdr initch]\n  (if-not initch\n    (reader-error rdr \"EOF while reading\")\n    (loop [sb (doto (StringBuilder.) (.append initch))\n           ch (read-char rdr)]\n      (if (or (whitespace? ch)\n              (macro-terminating? ch)\n              (nil? ch))\n        (do (unread rdr ch)\n            (str sb))\n        (recur (.append sb ch) (read-char rdr))))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "initch"]]}
   {"private" true,
    "const" true,
    "ns" "clojure.tools.reader",
    "name" "lower-limit",
    "line" 97,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:const lower-limit (int \\uE000))",
    "file" "clojure/tools/reader.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-delimited",
    "line" 139,
    "column" 1,
    "tag" "clojure.lang.PersistentVector",
    "source"
    "(defn- ^PersistentVector read-delimited\n  [delim rdr recursive?]\n  (let [first-line (when (indexing-reader? rdr)\n                     (get-line-number rdr))\n        delim (char delim)]\n    (loop [a (transient [])]\n      (if-let [ch (read-past whitespace? rdr)]\n        (if (identical? delim (char ch))\n          (persistent! a)\n          (if-let [macrofn (macros ch)]\n            (let [mret (macrofn rdr ch)]\n              (recur (if-not (identical? mret rdr) (conj! a mret) a)))\n            (let [o (read (doto rdr (unread ch)) true nil recursive?)]\n              (recur (if-not (identical? o rdr) (conj! a o) a)))))\n        (reader-error rdr \"EOF while reading\"\n                      (when first-line\n                        (str \", starting at line\" first-line)))))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["delim" "rdr" "recursive?"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-dispatch",
    "line" 45,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-dispatch\n  [rdr _]\n  (if-let [ch (read-char rdr)]\n    (if-let [dm (dispatch-macros ch)]\n      (dm rdr ch)\n      (if-let [obj (read-tagged (doto rdr (unread ch)) ch)] ;; ctor reader is implemented as a taggged literal\n        obj\n        (reader-error rdr \"No dispatch macro for \" ch)))\n    (reader-error rdr \"EOF while reading character\")))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "_"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "unquote?",
    "line" 422,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- unquote? [form]\n  (and (seq? form)\n       (= (first form) 'clojure.core/unquote)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-char*",
    "line" 99,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-char*\n  [rdr backslash]\n  (let [ch (read-char rdr)]\n    (if-not (nil? ch)\n      (let [token (read-token rdr ch)\n            token-len (count token)]\n        (cond\n\n         (== 1 token-len)  (Character/valueOf (nth token 0))\n\n         (= token \"newline\") \\newline\n         (= token \"space\") \\space\n         (= token \"tab\") \\tab\n         (= token \"backspace\") \\backspace\n         (= token \"formfeed\") \\formfeed\n         (= token \"return\") \\return\n\n         (.startsWith token \"u\")\n         (let [c (read-unicode-char token 1 4 16)\n               ic (int c)]\n           (if (and (> ic upper-limit)\n                    (< ic lower-limit))\n             (reader-error rdr \"Invalid character constant: \\\\u\" (Integer/toString ic 16))\n             c))\n\n         (.startsWith token \"x\")\n         (read-unicode-char token 1 2 16)\n\n         (.startsWith token \"o\")\n         (let [len (dec token-len)]\n           (if (> len 3)\n             (reader-error rdr \"Invalid octal escape sequence length: \" len)\n             (let [uc (read-unicode-char token 1 len 8)]\n               (if (> (int uc) 0377)\n                 (reader-error rdr \"Octal escape sequence must be in range [0, 377]\")\n                 uc))))\n\n         :else (reader-error rdr \"Unsupported character: \\\\\" token)))\n      (reader-error rdr \"EOF while reading character\"))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "backslash"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-unicode-char",
    "line" 63,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-unicode-char\n  ([^String token offset length base]\n     (let [l (+ offset length)]\n       (when-not (== (count token) l)\n         (throw (IllegalArgumentException. (str \"Invalid unicode character: \\\\\" token))))\n       (loop [i offset uc 0]\n         (if (== i l)\n           (char uc)\n           (let [d (Character/digit (char (nth token i)) (int base))]\n             (if (== d -1)\n               (throw (IllegalArgumentException. (str \"Invalid digit: \" (nth token i))))\n               (recur (inc i) (long (+ d (* uc base))))))))))\n\n  ([rdr initch base length exact?]\n     (loop [i 1 uc (Character/digit (char initch) (int base))]\n       (if (== uc -1)\n         (throw (IllegalArgumentException. (str \"Invalid digit: \" initch)))\n         (if-not (== i length)\n           (let [ch (peek-char rdr)]\n             (if (or (whitespace? ch)\n                     (macros ch)\n                     (nil? ch))\n               (if exact?\n                 (throw (IllegalArgumentException.\n                         (str \"Invalid character length: \" i \", should be: \" length)))\n                 (char uc))\n               (let [d (Character/digit (char ch) (int base))]\n                 (read-char rdr)\n                 (if (== d -1)\n                   (throw (IllegalArgumentException. (str \"Invalid digit: \" ch)))\n                   (recur (inc i) (long (+ d (* uc base))))))))\n           (char uc))))))",
    "file" "clojure/tools/reader.clj",
    "arglists"
    [["token" "offset" "length" "base"]
     ["rdr" "initch" "base" "length" "exact?"]]}
   {"private" true,
    "const" true,
    "ns" "clojure.tools.reader",
    "name" "upper-limit",
    "line" 96,
    "column" 1,
    "tag" nil,
    "source" "(def ^:private ^:const upper-limit (int \\uD7ff))",
    "file" "clojure/tools/reader.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-tagged*",
    "line" 578,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-tagged* [rdr tag f]\n  (let [o (read rdr true nil true)]\n    (f o)))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "tag" "f"]]}
   {"private" true,
    "ns" "clojure.tools.reader",
    "name" "read-arg",
    "line" 357,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- read-arg\n  [rdr pct]\n  (if-not (thread-bound? #'arg-env)\n    (read-symbol rdr pct)\n    (let [ch (peek-char rdr)]\n      (cond\n        (or (whitespace? ch)\n            (macro-terminating? ch)\n            (nil? ch))\n        (register-arg 1)\n\n        (identical? ch \\&)\n        (do (read-char rdr)\n            (register-arg -1))\n\n        :else\n        (let [n (read rdr true nil true)]\n          (if-not (integer? n)\n            (throw (IllegalStateException. \"Arg literal must be %, %& or %integer\"))\n            (register-arg n)))))))",
    "file" "clojure/tools/reader.clj",
    "arglists" [["rdr" "pct"]]}]},
 "description" "tools.reader 0.7.3",
 "version" "0.7.3",
 "name" "clojure.tools.reader",
 "group" "clojure.tools.reader"}
