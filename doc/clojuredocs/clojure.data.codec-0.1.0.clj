{"namespaces"
 {"clojure.data.codec.base64"
  [{"ns" "clojure.data.codec.base64",
    "name" "encode!",
    "line" 151,
    "column" 1,
    "doc"
    "Reads from the input byte array for the specified length starting at the offset\n   index, and base64 encodes into the output array starting at index 0. Returns the\n   length written to output.\n\n   Note: if using partial input, length must be a multiple of 3 to avoid padding.",
    "tag" nil,
    "source"
    "(defn encode!\n  \"Reads from the input byte array for the specified length starting at the offset\n   index, and base64 encodes into the output array starting at index 0. Returns the\n   length written to output.\n\n   Note: if using partial input, length must be a multiple of 3 to avoid padding.\"\n  ^long [^bytes input ^long offset ^long length ^bytes output]\n  (let [tail-len (rem length 3)\n        loop-lim (- (+ offset length) tail-len)\n        in-end (dec (+ offset length))\n        out-len (enc-length length)\n        out-end (dec out-len)]\n    (loop [i offset j 0]\n      (when (< i loop-lim)\n        (let [x (long (aget input i))\n              y (long (aget input (inc i)))\n              z (long (aget input (+ 2 i)))\n              a (-> x\n                  (bit-shift-right 2)\n                  (bit-and 0x3F))\n              b1 (-> x\n                   (bit-and 0x3)\n                   (bit-shift-left 4))\n              b2 (-> y\n                   (bit-shift-right 4)\n                   (bit-and 0xF))\n              b (bit-or b1 b2)\n              c1 (-> y\n                   (bit-and 0xF)\n                   (bit-shift-left 2))\n              c2 (-> z\n                   (bit-shift-right 6)\n                   (bit-and 0x3))\n              c (bit-or c1 c2)\n              d (bit-and z 0x3F)]\n          (aset output j (aget enc-bytes a))\n          (aset output (inc j) (aget enc-bytes b))\n          (aset output (+ 2 j) (aget enc-bytes c))\n          (aset output (+ 3 j) (aget enc-bytes d)))\n        (recur (+ 3 i) (+ 4 j))))\n    ; write padded section\n    (case tail-len\n      0 nil\n      1 (let [i in-end\n              j (- out-end 3)\n              x (long (aget input i))\n              a (-> x\n                  (bit-shift-right 2)\n                  (bit-and 0x3F))\n              b1 (-> x\n                   (bit-and 0x3)\n                   (bit-shift-left 4))]\n          (aset output j (aget enc-bytes a))\n          (aset output (inc j) (aget enc-bytes b1))\n          (aset output (+ 2 j) (byte 61))\n          (aset output (+ 3 j) (byte 61)))\n      2 (let [i (dec in-end)\n              j (- out-end 3)\n              x (long (aget input i))\n              y (long (aget input (inc i)))\n              a (-> x\n                  (bit-shift-right 2)\n                  (bit-and 0x3F))\n              b1 (-> x\n                   (bit-and 0x3)\n                   (bit-shift-left 4))\n              b2 (-> y\n                   (bit-shift-right 4)\n                   (bit-and 0xF))\n              b (bit-or b1 b2)\n              c1 (-> y\n                   (bit-and 0xF)\n                   (bit-shift-left 2))]\n          (aset output j (aget enc-bytes a))\n          (aset output (inc j) (aget enc-bytes b))\n          (aset output (+ 2 j) (aget enc-bytes c1))\n          (aset output (+ 3 j) (byte 61))))\n    out-len))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input" "offset" "length" "output"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "decoding-transfer",
    "line" 260,
    "column" 1,
    "doc"
    "Base64 decodes from input-stream to output-stream. Returns nil or throws IOException.\n\n  Options are key/value pairs and may be one of\n    :buffer-size  read buffer size to use, must be a multiple of 4; default is 8192.",
    "tag" nil,
    "source"
    "(defn decoding-transfer\n  \"Base64 decodes from input-stream to output-stream. Returns nil or throws IOException.\n\n  Options are key/value pairs and may be one of\n    :buffer-size  read buffer size to use, must be a multiple of 4; default is 8192.\"\n  [^InputStream input-stream ^OutputStream output-stream & opts]\n  (let [opts (when opts (apply hash-map opts))\n        in-size (buf-size opts 8192 4)\n        out-size (if (== in-size 8192) 6144 (dec-length in-size 0))\n        in-buf (byte-array in-size)\n        out-buf (byte-array out-size)]\n    (loop []\n      (let [in-size (read-fully input-stream in-buf)]\n        (when (pos? in-size)\n          (let [out-size (decode! in-buf 0 in-size out-buf)]\n            (.write output-stream out-buf 0 out-size)\n            (recur)))))))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input-stream" "output-stream" "&" "opts"]]}
   {"private" true,
    "ns" "clojure.data.codec.base64",
    "name" "buf-size",
    "line" 253,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- buf-size ^long [opts ^long default ^long multiple-of]\n  (if-let [in-size (:buffer-size opts)]\n    (if (zero? (rem in-size multiple-of))\n      in-size\n      (throw (IllegalArgumentException. ^String (format \"Buffer size must be a multiple of %d.\" multiple-of))))\n    default))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["opts" "default" "multiple-of"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "decode!",
    "line" 59,
    "column" 1,
    "doc"
    "Reads from the input byte array for the specified length starting at the offset\n   index, and base64 decodes into the output array starting at index 0. Returns the\n   length written to output.\n\n   Note: length must be a multiple of 4.",
    "tag" nil,
    "source"
    "(defn decode!\n  \"Reads from the input byte array for the specified length starting at the offset\n   index, and base64 decodes into the output array starting at index 0. Returns the\n   length written to output.\n\n   Note: length must be a multiple of 4.\"\n  ^long [^bytes input ^long offset ^long length ^bytes output]\n  (let [in-end (+ offset length -1)\n        pad-len (pad-length input offset length)\n        out-len (dec-length length pad-len)\n        out-end (dec out-len)\n        tail-len (rem out-len 3)\n        loop-lim (- out-len tail-len)]\n    (loop [i offset j 0]\n      (when (< j loop-lim)\n        (let [a (long (aget dec-bytes (aget input i)))\n              b (long (aget dec-bytes (aget input (inc i))))\n              c (long (aget dec-bytes (aget input (+ 2 i))))\n              d (long (aget dec-bytes (aget input (+ 3 i))))\n              x1 (-> a\n                   (bit-and 0x3F)\n                   (bit-shift-left 2))\n              x2 (-> b\n                   (bit-shift-right 4)\n                   (bit-and 0x3))\n              y1 (->\n                   (bit-and b 0xF)\n                   (bit-shift-left 4))\n              y2 (-> c\n                   (bit-shift-right 2)\n                   (bit-and 0xF))\n              z1 (-> c\n                   (bit-and 0x3)\n                   (bit-shift-left 6))\n              z2 (bit-and d 0x3F)\n              x (bit-or x1 x2)\n              y (bit-or y1 y2)\n              z (bit-or z1 z2)]\n          (aset output j (byte x))\n          (aset output (inc j) (byte y))\n          (aset output (+ 2 j) (byte z)))\n        (recur (+ 4 i) (+ 3 j))))\n    ; handle padded section\n    (case tail-len\n      0 nil\n      1 (let [i (- in-end 3)\n              j out-end\n              a (long (aget dec-bytes (aget input i)))\n              b (long (aget dec-bytes (aget input (inc i))))\n              x1 (-> a\n                   (bit-and 0x3F)\n                   (bit-shift-left 2))\n              x2 (-> b\n                   (bit-shift-right 4)\n                   (bit-and 0x3))\n              x (bit-or x1 x2)]\n          (aset output j (byte x)))\n      2 (let [i (- in-end 3)\n              j (dec out-end)\n              a (long (aget dec-bytes (aget input i)))\n              b (long (aget dec-bytes (aget input (inc i))))\n              c (long (aget dec-bytes (aget input (+ 2 i))))\n              x1 (-> a\n                   (bit-and 0x3F)\n                   (bit-shift-left 2))\n              x2 (-> b\n                   (bit-shift-right 4)\n                   (bit-and 0x3))\n              y1 (->\n                   (bit-and b 0xF)\n                   (bit-shift-left 4))\n              y2 (-> c\n                   (bit-shift-right 2)\n                   (bit-and 0xF))\n              x (bit-or x1 x2)\n              y (bit-or y1 y2)]\n          (aset output j (byte x))\n          (aset output (inc j) (byte y))))\n    out-len))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input" "offset" "length" "output"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "encoding-transfer",
    "line" 278,
    "column" 1,
    "doc"
    "Base64 encodes from input-stream to output-stream. Returns nil or throws IOException.\n\n  Options are key/value pairs and may be one of\n    :buffer-size  read buffer size to use, must be a multiple of 3; default is 6144.",
    "tag" nil,
    "source"
    "(defn encoding-transfer\n  \"Base64 encodes from input-stream to output-stream. Returns nil or throws IOException.\n\n  Options are key/value pairs and may be one of\n    :buffer-size  read buffer size to use, must be a multiple of 3; default is 6144.\"\n  [^InputStream input-stream ^OutputStream output-stream & opts]\n  (let [opts (when opts (apply hash-map opts))\n        in-size (buf-size opts 6144 3)\n        out-size (if (== in-size 6144) 8192 (enc-length in-size))\n        in-buf (byte-array in-size)\n        out-buf (byte-array out-size)]\n    (loop []\n      (let [in-size (read-fully input-stream in-buf)]\n        (when (pos? in-size)\n          (let [out-size (encode! in-buf 0 in-size out-buf)]\n            (.write output-stream out-buf 0 out-size)\n            (recur)))))))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input-stream" "output-stream" "&" "opts"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "encode",
    "line" 230,
    "column" 1,
    "doc" "Returns a base64 encoded byte array.",
    "tag" nil,
    "source"
    "(defn encode\n  \"Returns a base64 encoded byte array.\"\n  ([^bytes input]\n    (encode input 0 (alength input)))\n  ([^bytes input ^long offset ^long length]\n    (let [dest (byte-array (enc-length length))]\n      (encode! input offset length dest)\n      dest)))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input"] ["input" "offset" "length"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "dec-length",
    "line" 39,
    "column" 1,
    "doc"
    "Calculates what would be the length after decoding of an input array of length\n   in-length with the specified padding length.",
    "tag" nil,
    "source"
    "(defn dec-length\n  \"Calculates what would be the length after decoding of an input array of length\n   in-length with the specified padding length.\"\n  ^long [^long in-length ^long pad-length]\n  (-> in-length\n    (quot 4)\n    (* 3)\n    (- pad-length)))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["in-length" "pad-length"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "pad-length",
    "line" 49,
    "column" 1,
    "doc"
    "Returns the length of padding on the end of the input array.",
    "tag" nil,
    "source"
    "(defn pad-length\n  \"Returns the length of padding on the end of the input array.\"\n  ^long [^bytes input ^long offset ^long length]\n  (let [end (+ offset length -1)]\n    (if (== 61 (long (aget input end)))\n      (if (== 61 (long (aget input (dec end))))\n        2\n        1)\n      0)))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input" "offset" "length"]]}
   {"private" true,
    "ns" "clojure.data.codec.base64",
    "name" "dec-bytes",
    "line" 25,
    "column" 1,
    "tag" "[B",
    "source"
    "(def ^:private ^\"[B\" dec-bytes\n  (let [^bytes ba (byte-array (inc (apply max enc-bytes)))]\n    (doseq [[idx enc] (map-indexed vector enc-bytes)]\n      (aset ba enc (byte idx)))\n    ba))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" nil}
   {"ns" "clojure.data.codec.base64",
    "name" "decode",
    "line" 139,
    "column" 1,
    "doc"
    "Returns a base64 decoded byte array.\n\n  Note: length must be a multiple of 4.",
    "tag" nil,
    "source"
    "(defn decode\n  \"Returns a base64 decoded byte array.\n\n  Note: length must be a multiple of 4.\"\n  ([^bytes input]\n    (decode input 0 (alength input)))\n  ([^bytes input ^long offset ^long length]\n    (let [dest (byte-array (dec-length length (pad-length input offset length)))]\n      (decode! input offset length dest)\n      dest)))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input"] ["input" "offset" "length"]]}
   {"private" true,
    "ns" "clojure.data.codec.base64",
    "name" "read-fully",
    "line" 240,
    "column" 1,
    "doc"
    "Will fill the buffer to capacity, or with whatever is left in the input.\n   Returns the bytes read.",
    "tag" nil,
    "source"
    "(defn- read-fully\n  \"Will fill the buffer to capacity, or with whatever is left in the input.\n   Returns the bytes read.\"\n  ; This is necessary since a partial fill from .read does not necessarily mean EOS,\n  ; and we need full buffers to avoid incorrect padding.\n  ^long [^InputStream input ^bytes buf]\n  (loop [off 0 len (alength buf)]\n    (let [in-size (.read input buf off len)]\n      (cond\n        (== in-size len) (+ off in-size)\n        (neg? in-size) off\n        :else (recur (+ off in-size) (- len in-size))))))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["input" "buf"]]}
   {"ns" "clojure.data.codec.base64",
    "name" "enc-length",
    "line" 31,
    "column" 1,
    "doc"
    "Calculates what would be the length after encoding of an input array of length n.",
    "tag" nil,
    "source"
    "(defn enc-length\n  \"Calculates what would be the length after encoding of an input array of length n.\"\n  ^long [^long n]\n  (-> n\n    (+ 2)\n    (quot 3)\n    (* 4)))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" [["n"]]}
   {"private" true,
    "ns" "clojure.data.codec.base64",
    "name" "enc-bytes",
    "line" 20,
    "column" 1,
    "tag" "[B",
    "source"
    "(def ^:private ^\"[B\" enc-bytes\n  (byte-array\n    (map (comp byte int)\n      \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\")))",
    "file" "clojure/data/codec/base64.clj",
    "arglists" nil}]},
 "description" "data.codec 0.1.0",
 "version" "0.1.0",
 "name" "clojure.data.codec",
 "group" "clojure.data.codec"}
