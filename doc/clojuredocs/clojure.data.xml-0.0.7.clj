{"namespaces"
 {"clojure.data.xml"
  [{"private" true,
    "ns" "clojure.data.xml",
    "name" "seq-tree",
    "line" 74,
    "column" 1,
    "doc"
    "Takes a seq of events that logically represents\n  a tree by each event being one of: enter-sub-tree event,\n  exit-sub-tree event, or node event.\n\n  Returns a lazy sequence whose first element is a sequence of\n  sub-trees and whose remaining elements are events that are not\n  siblings or descendants of the initial event.\n\n  The given exit? function must return true for any exit-sub-tree\n  event.  parent must be a function of two arguments: the first is an\n  event, the second a sequence of nodes or subtrees that are children\n  of the event.  parent must return nil or false if the event is not\n  an enter-sub-tree event.  Any other return value will become\n  a sub-tree of the output tree and should normally contain in some\n  way the children passed as the second arg.  The node function is\n  called with a single event arg on every event that is neither parent\n  nor exit, and its return value will become a node of the output tree.\n\n  (seq-tree #(when (= %1 :<) (vector %2)) #{:>} str\n            [1 2 :< 3 :< 4 :> :> 5 :> 6])\n  ;=> ((\"1\" \"2\" [(\"3\" [(\"4\")])] \"5\") 6)",
    "tag" nil,
    "source"
    "(defn- seq-tree\n  \"Takes a seq of events that logically represents\n  a tree by each event being one of: enter-sub-tree event,\n  exit-sub-tree event, or node event.\n\n  Returns a lazy sequence whose first element is a sequence of\n  sub-trees and whose remaining elements are events that are not\n  siblings or descendants of the initial event.\n\n  The given exit? function must return true for any exit-sub-tree\n  event.  parent must be a function of two arguments: the first is an\n  event, the second a sequence of nodes or subtrees that are children\n  of the event.  parent must return nil or false if the event is not\n  an enter-sub-tree event.  Any other return value will become\n  a sub-tree of the output tree and should normally contain in some\n  way the children passed as the second arg.  The node function is\n  called with a single event arg on every event that is neither parent\n  nor exit, and its return value will become a node of the output tree.\n\n  (seq-tree #(when (= %1 :<) (vector %2)) #{:>} str\n            [1 2 :< 3 :< 4 :> :> 5 :> 6])\n  ;=> ((\\\"1\\\" \\\"2\\\" [(\\\"3\\\" [(\\\"4\\\")])] \\\"5\\\") 6)\"\n [parent exit? node coll]\n  (lazy-seq\n    (when-let [[event] (seq coll)]\n      (let [more (rest coll)]\n        (if (exit? event)\n          (cons nil more)\n          (let [tree (seq-tree parent exit? node more)]\n            (if-let [p (parent event (lazy-seq (first tree)))]\n              (let [subtree (seq-tree parent exit? node (lazy-seq (rest tree)))]\n                (cons (cons p (lazy-seq (first subtree)))\n                      (lazy-seq (rest subtree))))\n              (cons (cons (node event) (lazy-seq (first tree)))\n                    (lazy-seq (rest tree))))))))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["parent" "exit?" "node" "coll"]]}
   {"ns" "clojure.data.xml",
    "name" "map->CData",
    "line" 49,
    "column" 1,
    "doc"
    "Factory function for class clojure.data.xml.CData, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord CData [content]\n  Emit\n  (emit-element [e writer]\n    (.writeCData ^javax.xml.stream.XMLStreamWriter writer (:content e))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.data.xml",
    "name" "event-tree",
    "line" 110,
    "column" 1,
    "doc"
    "Returns a lazy tree of Element objects for the given seq of Event\n  objects. See source-seq and parse.",
    "tag" nil,
    "source"
    "(defn event-tree\n  \"Returns a lazy tree of Element objects for the given seq of Event\n  objects. See source-seq and parse.\"\n  [events]\n  (ffirst\n   (seq-tree\n    (fn [^Event event contents]\n      (when (= :start-element (.type event))\n        (Element. (.name event) (.attrs event) contents)))\n    (fn [^Event event] (= :end-element (.type event)))\n    (fn [^Event event] (.str event))\n    events)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["events"]]}
   {"ns" "clojure.data.xml",
    "name" "->CData",
    "line" 49,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.xml.CData.",
    "tag" nil,
    "source"
    "(defrecord CData [content]\n  Emit\n  (emit-element [e writer]\n    (.writeCData ^javax.xml.stream.XMLStreamWriter writer (:content e))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["content"]]}
   {"arglists" [["tag" "&" ["attrs" "&" "content"]]],
    "ns" "clojure.data.xml",
    "name" "element",
    "column" 1,
    "line" 64,
    "source"
    "(defn element [tag & [attrs & content]]\n  (Element. tag (or attrs {}) (remove nil? content)))",
    "file" "clojure/data/xml.clj",
    "tag" nil}
   {"arglists" [["type" "name" "&" ["attrs" "str"]]],
    "ns" "clojure.data.xml",
    "name" "event",
    "column" 1,
    "line" 24,
    "source"
    "(defn event [type name & [attrs str]]\n  (Event. type name attrs str))",
    "file" "clojure/data/xml.clj",
    "tag" nil}
   {"ns" "clojure.data.xml",
    "name" "sexps-as-fragment",
    "line" 156,
    "column" 1,
    "doc"
    "Convert a compact prxml/hiccup-style data structure into the more formal\n   tag/attrs/content format. A seq of elements will be returned, which may\n   not be suitable for immediate use as there is no root element. See also\n   sexp-as-element.\n\n   The format is [:tag-name attr-map? content*]. Each vector opens a new tag;\n   seqs do not open new tags, and are just used for inserting groups of elements\n   into the parent tag. A bare keyword not in a vector creates an empty element.\n\n   To provide XML conversion for your own data types, extend the AsElements\n   protocol to them.",
    "tag" nil,
    "source"
    "(defn sexps-as-fragment\n  \"Convert a compact prxml/hiccup-style data structure into the more formal\n   tag/attrs/content format. A seq of elements will be returned, which may\n   not be suitable for immediate use as there is no root element. See also\n   sexp-as-element.\n\n   The format is [:tag-name attr-map? content*]. Each vector opens a new tag;\n   seqs do not open new tags, and are just used for inserting groups of elements\n   into the parent tag. A bare keyword not in a vector creates an empty element.\n\n   To provide XML conversion for your own data types, extend the AsElements\n   protocol to them.\"\n  ([] nil)\n  ([sexp] (as-elements sexp))\n  ([sexp & sexps] (mapcat as-elements (cons sexp sexps))))",
    "file" "clojure/data/xml.clj",
    "arglists" [[] ["sexp"] ["sexp" "&" "sexps"]]}
   {"ns" "clojure.data.xml",
    "name" "Emit",
    "line" 27,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol Emit\n  (emit-element [element writer]))",
    "file" "clojure/data/xml.clj",
    "arglists" nil}
   {"ns" "clojure.data.xml",
    "name" "indent",
    "line" 283,
    "column" 1,
    "doc"
    "Emits the XML and indents the result.  WARNING: this is slow\n   it will emit the XML and read it in again to indent it.  Intended for \n   debugging/testing only.",
    "tag" nil,
    "source"
    "(defn indent\n  \"Emits the XML and indents the result.  WARNING: this is slow\n   it will emit the XML and read it in again to indent it.  Intended for \n   debugging/testing only.\"\n  [e ^java.io.Writer stream & {:as opts}]\n  (let [sw (java.io.StringWriter.)\n        _ (apply emit e sw opts)\n        source (-> sw .toString java.io.StringReader. javax.xml.transform.stream.StreamSource.)\n        result (javax.xml.transform.stream.StreamResult. stream)]\n    (.transform (indenting-transformer) source result)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["e" "stream" "&" [["as" "opts"]]]]}
   {"ns" "clojure.data.xml",
    "name" "source-seq",
    "line" 222,
    "column" 1,
    "doc"
    "Parses the XML InputSource source using a pull-parser. Returns\n  a lazy sequence of Event records.",
    "tag" nil,
    "source"
    "(defn source-seq\n  \"Parses the XML InputSource source using a pull-parser. Returns\n  a lazy sequence of Event records.\"\n  [s]\n  (let [fac (doto (javax.xml.stream.XMLInputFactory/newInstance)\n              (.setProperty javax.xml.stream.XMLInputFactory/IS_COALESCING true))\n        sreader (.createXMLStreamReader fac s)]\n    (pull-seq sreader)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.data.xml",
    "name" "parse-str",
    "line" 238,
    "column" 1,
    "doc" "Parses the passed in string to Clojure data structures",
    "tag" nil,
    "source"
    "(defn parse-str\n  \"Parses the passed in string to Clojure data structures\"\n  [s]\n  (let [sr (java.io.StringReader. s)]\n    (parse sr)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["s"]]}
   {"arglists" [["content"]],
    "ns" "clojure.data.xml",
    "name" "xml-comment",
    "column" 1,
    "line" 70,
    "source" "(defn xml-comment [content]\n  (Comment. content))",
    "file" "clojure/data/xml.clj",
    "tag" nil}
   {"ns" "clojure.data.xml",
    "name" "map->Element",
    "line" 37,
    "column" 1,
    "doc"
    "Factory function for class clojure.data.xml.Element, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord Element [tag attrs content]\n  Emit\n  (emit-element [e writer]\n    (let [nspace (namespace (:tag e))\n          qname (name (:tag e))\n          ^javax.xml.stream.XMLStreamWriter writer writer]\n      (.writeStartElement writer \"\"  qname (or nspace \"\"))\n      (write-attributes e writer)\n      (doseq [c (:content e)]\n        (emit-element c writer))\n      (.writeEndElement writer))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.data.xml",
    "name" "indent-str",
    "line" 294,
    "column" 1,
    "doc"
    "Emits the XML and indents the result.  Writes the results to a String and returns it",
    "tag" nil,
    "source"
    "(defn indent-str\n  \"Emits the XML and indents the result.  Writes the results to a String and returns it\"\n  [e]\n  (let [^java.io.StringWriter sw (java.io.StringWriter.)]\n    (indent e sw)\n    (.toString sw))  )",
    "file" "clojure/data/xml.clj",
    "arglists" [["e"]]}
   {"ns" "clojure.data.xml",
    "name" "sexp-as-element",
    "line" 172,
    "column" 1,
    "doc" "Convert a single sexp into an Element",
    "tag" nil,
    "source"
    "(defn sexp-as-element\n  \"Convert a single sexp into an Element\"\n  [sexp]\n  (let [[root & more] (sexps-as-fragment sexp)]\n    (when more\n      (throw\n       (IllegalArgumentException.\n        \"Cannot have multiple root elements; try creating a fragment instead\")))\n    root))",
    "file" "clojure/data/xml.clj",
    "arglists" [["sexp"]]}
   {"ns" "clojure.data.xml",
    "name" "map->Comment",
    "line" 54,
    "column" 1,
    "doc"
    "Factory function for class clojure.data.xml.Comment, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord Comment [content]\n  Emit\n  (emit-element [e writer]\n    (.writeComment ^javax.xml.stream.XMLStreamWriter writer (:content e))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.data.xml",
    "name" "->Element",
    "line" 37,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.xml.Element.",
    "tag" nil,
    "source"
    "(defrecord Element [tag attrs content]\n  Emit\n  (emit-element [e writer]\n    (let [nspace (namespace (:tag e))\n          qname (name (:tag e))\n          ^javax.xml.stream.XMLStreamWriter writer writer]\n      (.writeStartElement writer \"\"  qname (or nspace \"\"))\n      (write-attributes e writer)\n      (doseq [c (:content e)]\n        (emit-element c writer))\n      (.writeEndElement writer))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["tag" "attrs" "content"]]}
   {"arglists" [["content"]],
    "ns" "clojure.data.xml",
    "name" "cdata",
    "column" 1,
    "line" 67,
    "source" "(defn cdata [content]\n  (CData. content))",
    "file" "clojure/data/xml.clj",
    "tag" nil}
   {"ns" "clojure.data.xml",
    "name" "map->Event",
    "line" 22,
    "column" 1,
    "doc"
    "Factory function for class clojure.data.xml.Event, taking a map of keywords to field values.",
    "tag" nil,
    "source" "(defrecord Event [type name attrs str])",
    "file" "clojure/data/xml.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.data.xml",
    "name" "emit-str",
    "line" 270,
    "column" 1,
    "doc" "Emits the Element to String and returns it",
    "tag" nil,
    "source"
    "(defn emit-str\n  \"Emits the Element to String and returns it\"\n  [e]\n  (let [^java.io.StringWriter sw (java.io.StringWriter.)]\n    (emit e sw)\n    (.toString sw)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["e"]]}
   {"ns" "clojure.data.xml",
    "name" "parse",
    "line" 231,
    "column" 1,
    "doc"
    "Convenience function. Parses the source, which can be an\n  InputStream or Reader, and returns a lazy tree of Element records.\n  See lazy-source-seq for finer-grained control.",
    "tag" nil,
    "source"
    "(defn parse\n  \"Convenience function. Parses the source, which can be an\n  InputStream or Reader, and returns a lazy tree of Element records.\n  See lazy-source-seq for finer-grained control.\"\n  [source]\n  (event-tree (source-seq source)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["source"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.xml",
    "doc" "Return a seq of elements represented by an expression.",
    "arglists" [["expr"]],
    "name" "as-elements"}
   {"private" true,
    "ns" "clojure.data.xml",
    "name" "pull-seq",
    "line" 195,
    "column" 1,
    "doc"
    "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to \n   be triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete.",
    "tag" nil,
    "source"
    "(defn- pull-seq\n  \"Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to \n   be triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete.\"\n  [^XMLStreamReader sreader]\n  (lazy-seq\n   (loop []\n     (condp == (.next sreader)\n       XMLStreamConstants/START_ELEMENT\n       (cons (event :start-element\n                    (keyword (.getLocalName sreader))\n                    (attr-hash sreader) nil)\n             (pull-seq sreader)) \n       XMLStreamConstants/END_ELEMENT\n       (cons (event :end-element\n                    (keyword (.getLocalName sreader)) nil nil)\n             (pull-seq sreader))\n       XMLStreamConstants/CHARACTERS\n       (if-let [text (and (not (.isWhiteSpace sreader))\n                          (.getText sreader))]\n         (cons (event :characters nil nil text)\n               (pull-seq sreader))\n         (recur))\n       XMLStreamConstants/END_DOCUMENT\n       nil\n       (recur);; Consume and ignore comments, spaces, processing instructions etc\n       ))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["sreader"]]}
   {"private" true,
    "ns" "clojure.data.xml",
    "name" "attr-hash",
    "line" 188,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- attr-hash [^XMLStreamReader sreader] (into {}\n    (for [i (range (.getAttributeCount sreader))]\n      [(keyword (attr-prefix sreader i) (.getAttributeLocalName sreader i))\n       (.getAttributeValue sreader i)])))",
    "file" "clojure/data/xml.clj",
    "arglists" [["sreader"]]}
   {"private" true,
    "ns" "clojure.data.xml",
    "name" "attr-prefix",
    "line" 183,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- attr-prefix [^XMLStreamReader sreader index]\n  (let [p (.getAttributePrefix sreader index)]\n    (when-not (str/blank? p)\n      p)))",
    "file" "clojure/data/xml.clj",
    "arglists" [["sreader" "index"]]}
   {"ns" "clojure.data.xml",
    "name" "->Comment",
    "line" 54,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.xml.Comment.",
    "tag" nil,
    "source"
    "(defrecord Comment [content]\n  Emit\n  (emit-element [e writer]\n    (.writeComment ^javax.xml.stream.XMLStreamWriter writer (:content e))))",
    "file" "clojure/data/xml.clj",
    "arglists" [["content"]]}
   {"ns" "clojure.data.xml",
    "name" "AsElements",
    "line" 123,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol AsElements\n  (as-elements [expr] \"Return a seq of elements represented by an expression.\"))",
    "file" "clojure/data/xml.clj",
    "arglists" nil}
   {"ns" "clojure.data.xml",
    "name" "->Event",
    "line" 22,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.data.xml.Event.",
    "tag" nil,
    "source" "(defrecord Event [type name attrs str])",
    "file" "clojure/data/xml.clj",
    "arglists" [["type" "name" "attrs" "str"]]}
   {"arglists" [[]],
    "ns" "clojure.data.xml",
    "name" "indenting-transformer",
    "column" 1,
    "line" 277,
    "source"
    "(defn indenting-transformer []\n  (doto (-> (javax.xml.transform.TransformerFactory/newInstance) .newTransformer)\n    (.setOutputProperty (javax.xml.transform.OutputKeys/INDENT) \"yes\")\n    (.setOutputProperty (javax.xml.transform.OutputKeys/METHOD) \"xml\")\n    (.setOutputProperty \"{http://xml.apache.org/xslt}indent-amount\" \"2\")))",
    "file" "clojure/data/xml.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.data.xml",
    "doc" nil,
    "arglists" [["element" "writer"]],
    "name" "emit-element"}
   {"arglists" [["stream" "xml-encoding"]],
    "ns" "clojure.data.xml",
    "name" "check-stream-encoding",
    "column" 1,
    "line" 248,
    "source"
    "(defn check-stream-encoding [^java.io.OutputStreamWriter stream xml-encoding]\n  (when (not= (Charset/forName xml-encoding) (Charset/forName (.getEncoding stream)))\n    (throw (Exception. (str \"Output encoding of stream (\" xml-encoding\n                            \") doesn't match declaration (\"\n                            (.getEncoding stream) \")\")))))",
    "file" "clojure/data/xml.clj",
    "tag" nil}
   {"ns" "clojure.data.xml",
    "name" "emit",
    "line" 254,
    "column" 1,
    "doc"
    "Prints the given Element tree as XML text to stream.\n   Options:\n    :encoding <str>          Character encoding to use",
    "tag" nil,
    "source"
    "(defn emit\n  \"Prints the given Element tree as XML text to stream.\n   Options:\n    :encoding <str>          Character encoding to use\"\n  [e ^java.io.Writer stream & {:as opts}]\n  (let [^javax.xml.stream.XMLStreamWriter writer (-> (javax.xml.stream.XMLOutputFactory/newInstance)\n                                                     (.createXMLStreamWriter stream))]\n\n    (when (instance? java.io.OutputStreamWriter stream)\n      (check-stream-encoding stream (or (:encoding opts) \"UTF-8\")))\n    \n    (.writeStartDocument writer (or (:encoding opts) \"UTF-8\") \"1.0\")\n    (emit-element e writer)\n    (.writeEndDocument writer)\n    stream))",
    "file" "clojure/data/xml.clj",
    "arglists" [["e" "stream" "&" [["as" "opts"]]]]}
   {"arglists" [[[["keys" ["attrs"]]] "writer"]],
    "ns" "clojure.data.xml",
    "name" "write-attributes",
    "column" 1,
    "line" 30,
    "source"
    "(defn write-attributes [{:keys (attrs)} ^javax.xml.stream.XMLStreamWriter writer]\n  (doseq [[k v] attrs]\n    (if (namespace k)\n      (.writeAttribute writer (str (namespace k)) (name k) (str v))\n      (.writeAttribute writer (name k) (str v)))))",
    "file" "clojure/data/xml.clj",
    "tag" nil}]},
 "description" "data.xml 0.0.7",
 "version" "0.0.7",
 "name" "clojure.data.xml",
 "group" "clojure.data.xml"}
