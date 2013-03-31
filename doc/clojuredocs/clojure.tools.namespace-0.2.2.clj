{"namespaces"
 {"clojure.tools.namespace.move"
  [{"private" true,
    "ns" "clojure.tools.namespace.move",
    "name" "symbol-regex",
    "line" 44,
    "column" 1,
    "tag" nil,
    "source"
    "(def ^:private symbol-regex\n  ;; LispReader.java uses #\"[:]?([\\D&&[^/]].*/)?([\\D&&[^/]][^/]*)\" but\n  ;; that's too broad; we don't want a whole namespace-qualified symbol,\n  ;; just each part individually.\n  #\"[a-zA-Z0-9$%*+=?!<>_-]['.a-zA-Z0-9$%*+=?!<>_-]*\")",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" nil}
   {"private" true,
    "ns" "clojure.tools.namespace.move",
    "name" "update-file",
    "line" 22,
    "column" 1,
    "doc"
    "Reads file as a string, calls f on the string plus any args, then\n  writes out return value of f as the new contents of file.",
    "tag" nil,
    "source"
    "(defn- update-file\n  \"Reads file as a string, calls f on the string plus any args, then\n  writes out return value of f as the new contents of file.\"\n  [file f & args]\n  (spit file (str (apply f (slurp file) args))))",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" [["file" "f" "&" "args"]]}
   {"ns" "clojure.tools.namespace.move",
    "name" "move-ns-file",
    "line" 64,
    "column" 1,
    "doc"
    "ALPHA: subject to change. Moves the .clj source file (found relative\n  to source-path) for the namespace named old-sym to a file for a\n  namespace named new-sym.\n\n  WARNING: This function moves and deletes your source files! Make\n  sure you have a backup or version control.",
    "tag" nil,
    "source"
    "(defn move-ns-file\n  \"ALPHA: subject to change. Moves the .clj source file (found relative\n  to source-path) for the namespace named old-sym to a file for a\n  namespace named new-sym.\n\n  WARNING: This function moves and deletes your source files! Make\n  sure you have a backup or version control.\"\n  [old-sym new-sym source-path]\n  (let [old-file (io/file source-path (ns-file-name old-sym))\n        new-file (io/file source-path (ns-file-name new-sym))]\n    (.mkdirs (.getParentFile new-file))\n    (io/copy old-file new-file)\n    (.delete old-file)\n    (loop [dir (.getParentFile old-file)]\n      (when (empty? (.listFiles dir))\n        (.delete dir)\n        (recur (.getParentFile dir))))))",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" [["old-sym" "new-sym" "source-path"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.move",
    "name" "ns-file-name",
    "line" 28,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- ns-file-name [sym]\n  (str (-> (name sym)\n           (str/replace #\"-\" \"_\")\n           (str/replace #\"\\.\" File/separator))\n       \".clj\"))",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.move",
    "name" "clojure-source-files",
    "line" 34,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- clojure-source-files [dirs]\n  (->> dirs\n       (map io/file)\n       (filter #(.exists ^File %))\n       (mapcat file-seq)\n       (filter (fn [^File file]\n                 (and (.isFile file)\n                      (.endsWith (.getName file) \".clj\"))))\n       (map #(.getCanonicalFile ^File %))))",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" [["dirs"]]}
   {"ns" "clojure.tools.namespace.move",
    "name" "replace-ns-symbol",
    "line" 50,
    "column" 1,
    "doc"
    "ALPHA: subject to change. Given Clojure source as a string, replaces\n  all occurances of the namespace name old-sym with new-sym and\n  returns modified source as a string.",
    "tag" nil,
    "source"
    "(defn replace-ns-symbol\n  \"ALPHA: subject to change. Given Clojure source as a string, replaces\n  all occurances of the namespace name old-sym with new-sym and\n  returns modified source as a string.\"\n  [source old-sym new-sym]\n  (let [old-name (name old-sym)\n        new-name (name new-sym)]\n    ;; A lossless parser would be better, but this is adequate\n    (str/replace source symbol-regex\n                 (fn [match]\n                   (if (= match old-name)\n                     new-name\n                     match)))))",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" [["source" "old-sym" "new-sym"]]}
   {"ns" "clojure.tools.namespace.move",
    "name" "move-ns",
    "line" 82,
    "column" 1,
    "doc"
    "ALPHA: subject to change. Moves the .clj source file (found relative\n  to source-path) for the namespace named old-sym to new-sym and\n  replace all occurances of the old name with the new name in all\n  Clojure source files found in dirs.\n\n  WARNING: This function modifies and deletes your source files! Make\n  sure you have a backup or version control.",
    "tag" nil,
    "source"
    "(defn move-ns\n  \"ALPHA: subject to change. Moves the .clj source file (found relative\n  to source-path) for the namespace named old-sym to new-sym and\n  replace all occurances of the old name with the new name in all\n  Clojure source files found in dirs.\n\n  WARNING: This function modifies and deletes your source files! Make\n  sure you have a backup or version control.\"\n  [old-sym new-sym source-path dirs]\n  (move-ns-file old-sym new-sym source-path)\n  (doseq [file (clojure-source-files dirs)]\n    (update-file file replace-ns-symbol old-sym new-sym)))",
    "file" "clojure/tools/namespace/move.clj",
    "arglists" [["old-sym" "new-sym" "source-path" "dirs"]]}],
  "clojure.tools.namespace.parse"
  [{"private" true,
    "ns" "clojure.tools.namespace.parse",
    "name" "deps-from-libspec",
    "line" 42,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- deps-from-libspec [prefix form]\n  (cond (list? form)\n          (apply set/union\n                 (map (fn [f] (deps-from-libspec\n                               (symbol (str (when prefix (str prefix \".\"))\n                                            (first form)))\n                               f))\n                      (rest form)))\n\t(vector? form)\n          (deps-from-libspec prefix (first form))\n\t(symbol? form)\n          #{(symbol (str (when prefix (str prefix \".\")) form))}\n\t(keyword? form)\n          #{}\n\t:else\n          (throw (IllegalArgumentException.\n                  (pr-str \"Unparsable namespace form:\" form)))))",
    "file" "clojure/tools/namespace/parse.clj",
    "arglists" [["prefix" "form"]]}
   {"ns" "clojure.tools.namespace.parse",
    "name" "deps-from-ns-decl",
    "line" 65,
    "column" 1,
    "doc"
    "Given an (ns...) declaration form (unevaluated), returns a set of\n  symbols naming the dependencies of that namespace.  Handles :use and\n  :require clauses but not :load.",
    "tag" nil,
    "source"
    "(defn deps-from-ns-decl\n  \"Given an (ns...) declaration form (unevaluated), returns a set of\n  symbols naming the dependencies of that namespace.  Handles :use and\n  :require clauses but not :load.\"\n  [decl]\n  (apply set/union (map deps-from-ns-form decl)))",
    "file" "clojure/tools/namespace/parse.clj",
    "arglists" [["decl"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.parse",
    "name" "deps-from-ns-form",
    "line" 60,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- deps-from-ns-form [form]\n  (when (and (list? form)\n\t     (contains? #{:use :require} (first form)))\n    (apply set/union (map #(deps-from-libspec nil %) (rest form)))))",
    "file" "clojure/tools/namespace/parse.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.namespace.parse",
    "name" "read-ns-decl",
    "line" 25,
    "column" 1,
    "doc"
    "Attempts to read a (ns ...) declaration from a\n  java.io.PushbackReader, and returns the unevaluated form. Returns\n  nil if read fails or if a ns declaration cannot be found. The ns\n  declaration must be the first Clojure form in the file, except for\n  (comment ...) forms.",
    "tag" nil,
    "source"
    "(defn read-ns-decl\n  \"Attempts to read a (ns ...) declaration from a\n  java.io.PushbackReader, and returns the unevaluated form. Returns\n  nil if read fails or if a ns declaration cannot be found. The ns\n  declaration must be the first Clojure form in the file, except for\n  (comment ...) forms.\"\n  [rdr]\n  (try\n   (loop [] (let [form (doto (read rdr) str)]\n              (cond\n               (ns-decl? form) form\n               (comment? form) (recur)\n               :else nil)))\n       (catch Exception e nil)))",
    "file" "clojure/tools/namespace/parse.clj",
    "arglists" [["rdr"]]}
   {"ns" "clojure.tools.namespace.parse",
    "name" "comment?",
    "line" 15,
    "column" 1,
    "doc" "Returns true if form is a (comment ...)",
    "tag" nil,
    "source"
    "(defn comment?\n  \"Returns true if form is a (comment ...)\"\n  [form]\n  (and (list? form) (= 'comment (first form))))",
    "file" "clojure/tools/namespace/parse.clj",
    "arglists" [["form"]]}
   {"ns" "clojure.tools.namespace.parse",
    "name" "ns-decl?",
    "line" 20,
    "column" 1,
    "doc" "Returns true if form is a (ns ...) declaration.",
    "tag" nil,
    "source"
    "(defn ns-decl?\n  \"Returns true if form is a (ns ...) declaration.\"\n  [form]\n  (and (list? form) (= 'ns (first form))))",
    "file" "clojure/tools/namespace/parse.clj",
    "arglists" [["form"]]}],
  "clojure.tools.namespace.reload"
  [{"ns" "clojure.tools.namespace.reload",
    "name" "remove-lib",
    "line" 15,
    "column" 1,
    "doc"
    "Remove lib's namespace and remove lib from the set of loaded libs.",
    "tag" nil,
    "source"
    "(defn remove-lib\n  \"Remove lib's namespace and remove lib from the set of loaded libs.\"\n  [lib]\n  (remove-ns lib)\n  (dosync (alter @#'clojure.core/*loaded-libs* disj lib)))",
    "file" "clojure/tools/namespace/reload.clj",
    "arglists" [["lib"]]}
   {"ns" "clojure.tools.namespace.reload",
    "name" "track-reload",
    "line" 43,
    "column" 1,
    "doc"
    "Executes all pending unload/reload operations on dependency tracker\n  until either an error is encountered or there are no more pending\n  operations.",
    "tag" nil,
    "source" nil,
    "file" "clojure/tools/namespace/reload.clj",
    "arglists" [["tracker"]]}
   {"ns" "clojure.tools.namespace.reload",
    "name" "track-reload-one",
    "line" 21,
    "column" 1,
    "doc"
    "Executes the next pending unload/reload operation in the dependency\n  tracker. Returns the updated dependency tracker. If reloading caused\n  an error, it is captured as ::error and the namespace which caused\n  the error is ::error-ns.",
    "tag" nil,
    "source" nil,
    "file" "clojure/tools/namespace/reload.clj",
    "arglists" [["tracker"]]}],
  "clojure.tools.namespace.find"
  [{"ns" "clojure.tools.namespace.find",
    "name" "find-ns-decls-in-jarfile",
    "line" 92,
    "column" 1,
    "doc"
    "Searches the JAR file for Clojure source files containing (ns ...)\n  declarations; returns the unevaluated ns declarations.",
    "tag" nil,
    "source"
    "(defn find-ns-decls-in-jarfile\n  \"Searches the JAR file for Clojure source files containing (ns ...)\n  declarations; returns the unevaluated ns declarations.\"\n  [^JarFile jarfile]\n  (filter identity\n          (map #(read-ns-decl-from-jarfile-entry jarfile %)\n               (clojure-sources-in-jar jarfile))))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["jarfile"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.find",
    "name" "filenames-in-jar",
    "line" 37,
    "column" 1,
    "doc"
    "Returns a sequence of Strings naming the non-directory entries in\n  the JAR file.",
    "tag" nil,
    "source"
    "(defn- filenames-in-jar\n  \"Returns a sequence of Strings naming the non-directory entries in\n  the JAR file.\"\n  [^JarFile jar-file]\n  (map #(.getName ^JarEntry %)\n       (filter #(not (.isDirectory ^JarEntry %))\n               (enumeration-seq (.entries jar-file)))))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["jar-file"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "find-ns-decls",
    "line" 110,
    "column" 1,
    "doc"
    "Searches a sequence of java.io.File objects (both directories and\n  JAR files) for .clj source files containing (ns...) declarations.\n  Returns a sequence of the unevaluated ns declaration forms. Use with\n  clojure.java.classpath to search Clojure's classpath.",
    "tag" nil,
    "source"
    "(defn find-ns-decls\n  \"Searches a sequence of java.io.File objects (both directories and\n  JAR files) for .clj source files containing (ns...) declarations.\n  Returns a sequence of the unevaluated ns declaration forms. Use with\n  clojure.java.classpath to search Clojure's classpath.\"\n  [files]\n  (concat\n   (mapcat find-ns-decls-in-dir (filter #(.isDirectory ^File %) files))\n   (mapcat find-ns-decls-in-jarfile (jar-files files))))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["files"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "find-namespaces-in-dir",
    "line" 69,
    "column" 1,
    "doc"
    "Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the symbol names of the declared namespaces.",
    "tag" nil,
    "source"
    "(defn find-namespaces-in-dir\n  \"Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the symbol names of the declared namespaces.\"\n  [^File dir]\n  (map second (find-ns-decls-in-dir dir)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["dir"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.find",
    "name" "jar-file?",
    "line" 45,
    "column" 1,
    "doc"
    "Returns true if file is a normal file with a .jar or .JAR extension.",
    "tag" nil,
    "source"
    "(defn- jar-file?\n  \"Returns true if file is a normal file with a .jar or .JAR extension.\"\n  [f]\n  (let [file (io/file f)]\n    (and (.isFile file)\n         (or (.endsWith (.getName file) \".jar\")\n             (.endsWith (.getName file) \".JAR\")))))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["f"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.find",
    "name" "jar-files",
    "line" 31,
    "column" 1,
    "doc"
    "Given a sequence of File objects, filters it for JAR files, returns\n  a sequence of java.util.jar.JarFile objects.",
    "tag" nil,
    "source"
    "(defn- jar-files\n  \"Given a sequence of File objects, filters it for JAR files, returns\n  a sequence of java.util.jar.JarFile objects.\"\n  [files]\n  (map #(JarFile. ^File %) (filter jar-file? files)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["files"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "find-namespaces",
    "line" 120,
    "column" 1,
    "doc"
    "Searches a sequence of java.io.File objects (both directories and\n  JAR files) for .clj source files containing (ns...) declarations.\n  Returns a sequence of the symbol names of the declared\n  namespaces. Use with clojure.java.classpath to search Clojure's\n  classpath.",
    "tag" nil,
    "source"
    "(defn find-namespaces\n  \"Searches a sequence of java.io.File objects (both directories and\n  JAR files) for .clj source files containing (ns...) declarations.\n  Returns a sequence of the symbol names of the declared\n  namespaces. Use with clojure.java.classpath to search Clojure's\n  classpath.\"\n  [files]\n  (map second (find-ns-decls files)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["files"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "find-clojure-sources-in-dir",
    "line" 55,
    "column" 1,
    "doc"
    "Searches recursively under dir for Clojure source files (.clj).\n  Returns a sequence of File objects, in breadth-first sort order.",
    "tag" nil,
    "source"
    "(defn find-clojure-sources-in-dir\n  \"Searches recursively under dir for Clojure source files (.clj).\n  Returns a sequence of File objects, in breadth-first sort order.\"\n  [^File dir]\n  ;; Use sort by absolute path to get breadth-first search.\n  (sort-by #(.getAbsolutePath ^File %)\n           (filter file/clojure-file? (file-seq dir))))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["dir"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "clojure-sources-in-jar",
    "line" 77,
    "column" 1,
    "doc"
    "Returns a sequence of filenames ending in .clj found in the JAR file.",
    "tag" nil,
    "source"
    "(defn clojure-sources-in-jar\n  \"Returns a sequence of filenames ending in .clj found in the JAR file.\"\n  [^JarFile jar-file]\n  (filter #(.endsWith ^String % \".clj\") (filenames-in-jar jar-file)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["jar-file"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "read-ns-decl-from-jarfile-entry",
    "line" 82,
    "column" 1,
    "doc"
    "Attempts to read a (ns ...) declaration from the named entry in the\n  JAR file, and returns the unevaluated form.  Returns nil if the read\n  fails, or if the first form is not a ns declaration.",
    "tag" nil,
    "source"
    "(defn read-ns-decl-from-jarfile-entry\n  \"Attempts to read a (ns ...) declaration from the named entry in the\n  JAR file, and returns the unevaluated form.  Returns nil if the read\n  fails, or if the first form is not a ns declaration.\"\n  [^JarFile jarfile ^String entry-name]\n  (with-open [rdr (PushbackReader.\n                   (io/reader\n                    (.getInputStream jarfile (.getEntry jarfile entry-name))))]\n    (parse/read-ns-decl rdr)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["jarfile" "entry-name"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "find-namespaces-in-jarfile",
    "line" 100,
    "column" 1,
    "doc"
    "Searches the JAR file for Clojure source files containing (ns ...)\n  declarations.  Returns a sequence of the symbol names of the\n  declared namespaces.",
    "tag" nil,
    "source"
    "(defn find-namespaces-in-jarfile\n  \"Searches the JAR file for Clojure source files containing (ns ...)\n  declarations.  Returns a sequence of the symbol names of the\n  declared namespaces.\"\n  [^JarFile jarfile]\n  (map second (find-ns-decls-in-jarfile jarfile)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["jarfile"]]}
   {"ns" "clojure.tools.namespace.find",
    "name" "find-ns-decls-in-dir",
    "line" 63,
    "column" 1,
    "doc"
    "Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the unevaluated ns declarations.",
    "tag" nil,
    "source"
    "(defn find-ns-decls-in-dir\n  \"Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the unevaluated ns declarations.\"\n  [^File dir]\n  (keep file/read-file-ns-decl (find-clojure-sources-in-dir dir)))",
    "file" "clojure/tools/namespace/find.clj",
    "arglists" [["dir"]]}],
  "clojure.tools.namespace.file"
  [{"ns" "clojure.tools.namespace.file",
    "name" "read-file-ns-decl",
    "line" 17,
    "column" 1,
    "doc"
    "Attempts to read a (ns ...) declaration from file, and returns the\n  unevaluated form.  Returns nil if read fails, or if the first form\n  is not a ns declaration.",
    "tag" nil,
    "source"
    "(defn read-file-ns-decl\n  \"Attempts to read a (ns ...) declaration from file, and returns the\n  unevaluated form.  Returns nil if read fails, or if the first form\n  is not a ns declaration.\"\n  [file]\n  (with-open [rdr (PushbackReader. (io/reader file))]\n    (parse/read-ns-decl rdr)))",
    "file" "clojure/tools/namespace/file.clj",
    "arglists" [["file"]]}
   {"ns" "clojure.tools.namespace.file",
    "name" "remove-files",
    "line" 54,
    "column" 1,
    "doc"
    "Returns an updated dependency tracker with files removed. The files\n  must have been previously added with add-files.",
    "tag" nil,
    "source"
    "(defn remove-files\n  \"Returns an updated dependency tracker with files removed. The files\n  must have been previously added with add-files.\"\n  [tracker files]\n  (-> tracker\n      (track/remove (keep (::filemap tracker {}) files))\n      (update-in [::filemap] #(apply dissoc % files))))",
    "file" "clojure/tools/namespace/file.clj",
    "arglists" [["tracker" "files"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.file",
    "name" "files-and-deps",
    "line" 34,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- files-and-deps [files]\n  (reduce (fn [m file]\n            (if-let [decl (read-file-ns-decl file)]\n              (let [deps (parse/deps-from-ns-decl decl)\n                    name (second decl)]\n                (-> m\n                    (assoc-in [:depmap name] deps)\n                    (assoc-in [:filemap file] name)))\n              m))\n          {} files))",
    "file" "clojure/tools/namespace/file.clj",
    "arglists" [["files"]]}
   {"ns" "clojure.tools.namespace.file",
    "name" "clojure-file?",
    "line" 25,
    "column" 1,
    "doc"
    "Returns true if the java.io.File represents a normal Clojure source\n  file.",
    "tag" nil,
    "source"
    "(defn clojure-file?\n  \"Returns true if the java.io.File represents a normal Clojure source\n  file.\"\n  [^java.io.File file]\n  (and (.isFile file)\n       (.endsWith (.getName file) \".clj\")))",
    "file" "clojure/tools/namespace/file.clj",
    "arglists" [["file"]]}
   {"ns" "clojure.tools.namespace.file",
    "name" "add-files",
    "line" 45,
    "column" 1,
    "doc"
    "Reads ns declarations from files; returns an updated dependency\n  tracker with those files added.",
    "tag" nil,
    "source"
    "(defn add-files\n  \"Reads ns declarations from files; returns an updated dependency\n  tracker with those files added.\"\n  [tracker files]\n  (let [{:keys [depmap filemap]} (files-and-deps files)]\n    (-> tracker\n        (track/add depmap)\n        (update-in [::filemap] (fnil merge {}) filemap))))",
    "file" "clojure/tools/namespace/file.clj",
    "arglists" [["tracker" "files"]]}],
  "clojure.tools.namespace.dependency"
  [{"ns" "clojure.tools.namespace.dependency",
    "name" "DependencyGraphUpdate",
    "line" 28,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol DependencyGraphUpdate\n  (depend [graph node dep]\n    \"Returns a new graph with a dependency from node to dep (\\\"node depends\n    on dep\\\"). Forbids circular dependencies.\")\n  (remove-edge [graph node dep]\n    \"Returns a new graph with the dependency from node to dep removed.\")\n  (remove-all [graph node]\n    \"Returns a new dependency graph with all references to node removed.\")\n  (remove-node [graph node]\n    \"Removes the node from the dependency graph without removing it as a\n    dependency of other nodes. That is, removes all outgoing edges from\n    node.\"))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" nil}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "dependent?",
    "line" 102,
    "column" 1,
    "doc" "True if y is a dependent of x.",
    "tag" nil,
    "source"
    "(defn dependent?\n  \"True if y is a dependent of x.\"\n  [graph x y]\n  (contains? (transitive-dependents graph x) y))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["graph" "x" "y"]]}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "graph",
    "line" 94,
    "column" 1,
    "doc" "Returns a new, empty, dependency graph.",
    "tag" nil,
    "source"
    "(defn graph \"Returns a new, empty, dependency graph.\" []\n  (->MapDependencyGraph {} {}))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "DependencyGraph",
    "line" 14,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol DependencyGraph\n  (immediate-dependencies [graph node]\n    \"Returns the set of immediate dependencies of node.\")\n  (immediate-dependents [graph node]\n    \"Returns the set of immediate dependents of node.\")\n  (transitive-dependencies [graph node]\n    \"Returns the set of all things which node depends on, directly or\n    transitively.\")\n  (transitive-dependents [graph node]\n    \"Returns the set of all things which depend upon node, directly or\n    transitively.\")\n  (nodes [graph]\n    \"Returns the set of all nodes in graph.\"))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" nil}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "topo-comparator",
    "line" 132,
    "column" 1,
    "doc"
    "Returns a comparator fn which produces a topological sort based on\n  the dependencies in graph. Nodes not present in the graph will sort\n  after nodes in the graph.",
    "tag" nil,
    "source"
    "(defn topo-comparator\n  \"Returns a comparator fn which produces a topological sort based on\n  the dependencies in graph. Nodes not present in the graph will sort\n  after nodes in the graph.\"\n  [graph]\n  (let [pos (zipmap (topo-sort graph) (range))]\n    (fn [a b]\n      (compare (get pos a Long/MAX_VALUE)\n               (get pos b Long/MAX_VALUE)))))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["graph"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc"
    "Returns the set of all things which depend upon node, directly or\n    transitively.",
    "arglists" [["graph" "node"]],
    "name" "transitive-dependents"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc" "Returns the set of immediate dependents of node.",
    "arglists" [["graph" "node"]],
    "name" "immediate-dependents"}
   {"private" true,
    "ns" "clojure.tools.namespace.dependency",
    "name" "transitive",
    "line" 46,
    "column" 1,
    "doc"
    "Recursively expands the set of dependency relationships starting\n  at (get m x)",
    "tag" nil,
    "source"
    "(defn- transitive\n  \"Recursively expands the set of dependency relationships starting\n  at (get m x)\"\n  [m x]\n  (reduce (fn [s k]\n\t    (set/union s (transitive m k)))\n\t  (get m x) (get m x)))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["m" "x"]]}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "depends?",
    "line" 97,
    "column" 1,
    "doc" "True if x is directly or transitively dependent on y.",
    "tag" nil,
    "source"
    "(defn depends?\n  \"True if x is directly or transitively dependent on y.\"\n  [graph x y]\n  (contains? (transitive-dependencies graph x) y))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["graph" "x" "y"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc"
    "Removes the node from the dependency graph without removing it as a\n    dependency of other nodes. That is, removes all outgoing edges from\n    node.",
    "arglists" [["graph" "node"]],
    "name" "remove-node"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc" "Returns the set of immediate dependencies of node.",
    "arglists" [["graph" "node"]],
    "name" "immediate-dependencies"}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "map->MapDependencyGraph",
    "line" 58,
    "column" 1,
    "doc"
    "Factory function for class clojure.tools.namespace.dependency.MapDependencyGraph, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord MapDependencyGraph [dependencies dependents]\n  DependencyGraph\n  (immediate-dependencies [graph node]\n    (get dependencies node #{}))\n  (immediate-dependents [graph node]\n    (get dependents node #{}))\n  (transitive-dependencies [graph node]\n    (transitive dependencies node))\n  (transitive-dependents [graph node]\n    (transitive dependents node))\n  (nodes [graph]\n    (clojure.set/union (set (keys dependencies))\n                       (set (keys dependents))))\n  DependencyGraphUpdate\n  (depend [graph node dep]\n    (when (depends? graph dep node)\n      (let [^String msg (binding [*print-length* 10]\n                          (str \"Circular dependency between \"\n                               (pr-str node) \" and \" (pr-str dep)))]\n        (throw (Exception. msg))))\n    (MapDependencyGraph.\n     (update-in dependencies [node] set-conj dep)\n     (update-in dependents [dep] set-conj node)))\n  (remove-edge [graph node dep]\n    (MapDependencyGraph.\n     (update-in dependencies [node] disj dep)\n     (update-in dependents [dep] disj node)))\n  (remove-all [graph node]\n    (MapDependencyGraph.\n     (remove-from-map dependencies node)\n     (remove-from-map dependents node)))\n  (remove-node [graph node]\n    (MapDependencyGraph.\n     (dissoc dependencies node)\n     dependents)))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["m__5818__auto__"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.dependency",
    "name" "remove-from-map",
    "line" 41,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- remove-from-map [amap x]\n  (reduce (fn [m [k vs]]\n\t    (assoc m k (disj vs x)))\n\t  {} (dissoc amap x)))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["amap" "x"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc" "Returns the set of all nodes in graph.",
    "arglists" [["graph"]],
    "name" "nodes"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc"
    "Returns a new graph with the dependency from node to dep removed.",
    "arglists" [["graph" "node" "dep"]],
    "name" "remove-edge"}
   {"arglists" nil,
    "ns" "clojure.tools.namespace.dependency",
    "name" "set-conj",
    "column" 1,
    "line" 56,
    "source" "(def set-conj (fnil conj #{}))",
    "file" "clojure/tools/namespace/dependency.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc"
    "Returns a new dependency graph with all references to node removed.",
    "arglists" [["graph" "node"]],
    "name" "remove-all"}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "->MapDependencyGraph",
    "line" 58,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.tools.namespace.dependency.MapDependencyGraph.",
    "tag" nil,
    "source"
    "(defrecord MapDependencyGraph [dependencies dependents]\n  DependencyGraph\n  (immediate-dependencies [graph node]\n    (get dependencies node #{}))\n  (immediate-dependents [graph node]\n    (get dependents node #{}))\n  (transitive-dependencies [graph node]\n    (transitive dependencies node))\n  (transitive-dependents [graph node]\n    (transitive dependents node))\n  (nodes [graph]\n    (clojure.set/union (set (keys dependencies))\n                       (set (keys dependents))))\n  DependencyGraphUpdate\n  (depend [graph node dep]\n    (when (depends? graph dep node)\n      (let [^String msg (binding [*print-length* 10]\n                          (str \"Circular dependency between \"\n                               (pr-str node) \" and \" (pr-str dep)))]\n        (throw (Exception. msg))))\n    (MapDependencyGraph.\n     (update-in dependencies [node] set-conj dep)\n     (update-in dependents [dep] set-conj node)))\n  (remove-edge [graph node dep]\n    (MapDependencyGraph.\n     (update-in dependencies [node] disj dep)\n     (update-in dependents [dep] disj node)))\n  (remove-all [graph node]\n    (MapDependencyGraph.\n     (remove-from-map dependencies node)\n     (remove-from-map dependents node)))\n  (remove-node [graph node]\n    (MapDependencyGraph.\n     (dissoc dependencies node)\n     dependents)))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["dependencies" "dependents"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc"
    "Returns a new graph with a dependency from node to dep (\"node depends\n    on dep\"). Forbids circular dependencies.",
    "arglists" [["graph" "node" "dep"]],
    "name" "depend"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.tools.namespace.dependency",
    "doc"
    "Returns the set of all things which node depends on, directly or\n    transitively.",
    "arglists" [["graph" "node"]],
    "name" "transitive-dependencies"}
   {"ns" "clojure.tools.namespace.dependency",
    "name" "topo-sort",
    "line" 107,
    "column" 1,
    "doc" "Returns a topologically-sorted list of nodes in graph.",
    "tag" nil,
    "source"
    "(defn topo-sort\n  \"Returns a topologically-sorted list of nodes in graph.\"\n  [graph]\n  (loop [sorted ()\n         g graph\n         todo (set (filter #(empty? (immediate-dependents graph %))\n                           (nodes graph)))]\n    (if (empty? todo)\n      sorted\n      (let [[node & more] (seq todo)\n            deps (immediate-dependencies g node)\n            [add g'] (loop [deps deps\n                            g g\n                            add #{}]\n                       (if (seq deps)\n                         (let [d (first deps)\n                               g' (remove-edge g node d)]\n                           (if (empty? (immediate-dependents g' d))\n                             (recur (rest deps) g' (conj add d))\n                             (recur (rest deps) g' add)))\n                         [add g]))]\n        (recur (cons node sorted)\n               (remove-node g' node)\n               (clojure.set/union (set more) (set add)))))))",
    "file" "clojure/tools/namespace/dependency.clj",
    "arglists" [["graph"]]}],
  "clojure.tools.namespace.track"
  [{"ns" "clojure.tools.namespace.track",
    "name" "tracker",
    "line" 108,
    "column" 1,
    "doc" "Returns a new, empty dependency tracker",
    "tag" nil,
    "source"
    "(defn tracker\n  \"Returns a new, empty dependency tracker\"\n  []\n  {})",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.namespace.track",
    "name" "remove",
    "line" 85,
    "column" 1,
    "doc"
    "Returns an updated dependency tracker from which the namespaces\n  (symbols) have been removed. The ::unload and ::load lists are\n  populated as with 'add'.",
    "tag" nil,
    "source"
    "(defn remove\n  \"Returns an updated dependency tracker from which the namespaces\n  (symbols) have been removed. The ::unload and ::load lists are\n  populated as with 'add'.\"\n  [tracker names]\n  (let [{load ::load\n         unload ::unload\n         deps ::deps\n         :or {load (), unload (), deps (dep/graph)}} tracker\n        known (set (dep/nodes deps))\n        removed-names (filter known names)\n        new-deps (remove-deps deps removed-names)\n        changed (affected-namespaces deps removed-names)]\n    (assoc tracker\n      ::deps new-deps\n      ::unload (distinct\n                (concat (reverse (sort (dep/topo-comparator deps) changed))\n                        unload))\n      ::load (distinct\n              (filter (complement (set removed-names))\n                      (concat (sort (dep/topo-comparator new-deps) changed)\n                              load))))))",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [["tracker" "names"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.track",
    "name" "remove-deps",
    "line" 21,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- remove-deps [deps names]\n  (reduce dep/remove-node deps names))",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [["deps" "names"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.track",
    "name" "add-deps",
    "line" 24,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- add-deps [deps depmap]\n  (reduce (fn [ds [name dependencies]]\n            (reduce (fn [g dep] (dep/depend g name dep))\n                    ds dependencies))\n          deps depmap))",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [["deps" "depmap"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.track",
    "name" "affected-namespaces",
    "line" 35,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- affected-namespaces [deps names]\n  (apply set/union\n         (set names)\n         (map #(dep/transitive-dependents deps %) names)))",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [["deps" "names"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.track",
    "name" "update-deps",
    "line" 30,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- update-deps [deps depmap]\n  (-> deps\n      (remove-deps (keys depmap))\n      (add-deps depmap)))",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [["deps" "depmap"]]}
   {"ns" "clojure.tools.namespace.track",
    "name" "add",
    "line" 40,
    "column" 1,
    "doc"
    "Returns an updated dependency tracker with new/updated namespaces.\n\n  Depmap is a map describing the new or modified namespaces. Keys in\n  the map are namespace names (symbols). Values in the map are sets of\n  symbols naming the birect dependencies of each namespace. For\n  example, assuming these ns declarations:\n\n      (ns alpha (:require beta))\n      (ns beta (:require gamma delta))\n\n  the depmap would look like this:\n\n      {alpha #{beta}\n       beta  #{gamma delta}}\n\n  After adding new/updated namespaces, the dependency tracker will\n  have two lists associated with the following keys:\n\n      :clojure.tools.namespace.track/unload\n          is the list of namespaces that need to be removed\n\n      :clojure.tools.namespace.track/load\n          is the list of namespaces that need to be reloaded\n\n  To reload namespaces in the correct order, first remove/unload all\n  namespaces in the 'unload' list, then (re)load all namespaces in the\n  'load' list. The clojure.tools.namespace.reload namespace has\n  functions to do this.",
    "tag" nil,
    "source"
    "(defn add\n  \"Returns an updated dependency tracker with new/updated namespaces.\n\n  Depmap is a map describing the new or modified namespaces. Keys in\n  the map are namespace names (symbols). Values in the map are sets of\n  symbols naming the birect dependencies of each namespace. For\n  example, assuming these ns declarations:\n\n      (ns alpha (:require beta))\n      (ns beta (:require gamma delta))\n\n  the depmap would look like this:\n\n      {alpha #{beta}\n       beta  #{gamma delta}}\n\n  After adding new/updated namespaces, the dependency tracker will\n  have two lists associated with the following keys:\n\n      :clojure.tools.namespace.track/unload\n          is the list of namespaces that need to be removed\n\n      :clojure.tools.namespace.track/load\n          is the list of namespaces that need to be reloaded\n\n  To reload namespaces in the correct order, first remove/unload all\n  namespaces in the 'unload' list, then (re)load all namespaces in the\n  'load' list. The clojure.tools.namespace.reload namespace has\n  functions to do this.\"\n  [tracker depmap]\n  (let [{load ::load\n         unload ::unload\n         deps ::deps\n         :or {load (), unload (), deps (dep/graph)}} tracker\n        new-deps (update-deps deps depmap)\n        changed (affected-namespaces new-deps (keys depmap))]\n    (assoc tracker\n      ::deps new-deps\n      ::unload (distinct\n               (concat (reverse (sort (dep/topo-comparator deps) changed))\n                       unload))\n      ::load (distinct\n             (concat (sort (dep/topo-comparator new-deps) changed)\n                     load)))))",
    "file" "clojure/tools/namespace/track.clj",
    "arglists" [["tracker" "depmap"]]}],
  "clojure.tools.namespace.dir"
  [{"ns" "clojure.tools.namespace.dir",
    "name" "scan-all",
    "line" 65,
    "column" 1,
    "doc"
    "Scans directories for all Clojure source files and updates the\n  dependency tracker to reload files. If no dirs given, defaults to\n  all directories on the classpath.",
    "tag" nil,
    "source"
    "(defn scan-all\n  \"Scans directories for all Clojure source files and updates the\n  dependency tracker to reload files. If no dirs given, defaults to\n  all directories on the classpath.\"\n  [tracker & dirs]\n  (let [ds (or (seq dirs) (dirs-on-classpath))\n        files (find-files ds)\n        deleted (seq (deleted-files tracker files))]\n    (update-files tracker deleted files)))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [["tracker" "&" "dirs"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.dir",
    "name" "find-files",
    "line" 20,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- find-files [dirs]\n  (->> dirs\n       (map io/file)\n       (filter #(.exists ^File %))\n       (mapcat file-seq)\n       (filter file/clojure-file?)\n       (map #(.getCanonicalFile ^File %))))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [["dirs"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.dir",
    "name" "deleted-files",
    "line" 31,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- deleted-files [tracker files]\n  (set/difference (::files tracker #{}) (set files)))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [["tracker" "files"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.dir",
    "name" "dirs-on-classpath",
    "line" 43,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- dirs-on-classpath []\n  (filter #(.isDirectory ^File %)\n          (map #(File. ^String %)\n               (string/split\n                (System/getProperty \"java.class.path\")\n                (Pattern/compile (Pattern/quote File/pathSeparator))))))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.tools.namespace.dir",
    "name" "modified-files",
    "line" 28,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- modified-files [tracker files]\n  (filter #(< (::time tracker 0) (.lastModified ^File %)) files))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [["tracker" "files"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.dir",
    "name" "update-files",
    "line" 34,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- update-files [tracker deleted modified]\n  (let [now (System/currentTimeMillis)]\n    (-> tracker\n        (update-in [::files] #(if % (apply disj % deleted) #{}))\n        (file/remove-files deleted)\n        (update-in [::files] into modified)\n        (file/add-files modified)\n        (assoc ::time now))))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [["tracker" "deleted" "modified"]]}
   {"ns" "clojure.tools.namespace.dir",
    "name" "scan",
    "line" 50,
    "column" 1,
    "doc"
    "Scans directories for files which have changed since the last time\n  'scan' was run; update the dependency tracker with\n  new/changed/deleted files.\n\n  If no dirs given, defaults to all directories on the classpath.",
    "tag" nil,
    "source"
    "(defn scan\n  \"Scans directories for files which have changed since the last time\n  'scan' was run; update the dependency tracker with\n  new/changed/deleted files.\n\n  If no dirs given, defaults to all directories on the classpath.\"\n  [tracker & dirs]\n  (let [ds (or (seq dirs) (dirs-on-classpath))\n        files (find-files ds)\n        deleted (seq (deleted-files tracker files))\n        modified (seq (modified-files tracker files))]\n    (if (or deleted modified)\n      (update-files tracker deleted modified)\n      tracker)))",
    "file" "clojure/tools/namespace/dir.clj",
    "arglists" [["tracker" "&" "dirs"]]}],
  "clojure.tools.namespace"
  [{"ns" "clojure.tools.namespace",
    "name" "ns-decl?",
    "line" 47,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.parse\n\n  Returns true if form is a (ns ...) declaration.",
    "tag" nil,
    "source"
    "(defn ns-decl?\n  \"DEPRECATED; moved to clojure.tools.namespace.parse\n\n  Returns true if form is a (ns ...) declaration.\"\n  [form]\n  (and (list? form) (= 'ns (first form))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.namespace",
    "name" "classpath-directories",
    "line" 115,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- classpath-directories []\n  (filter #(.isDirectory ^File %) (classpath)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.namespace",
    "name" "find-ns-decls-in-jarfile",
    "line" 154,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches the JAR file for Clojure source files containing (ns ...)\n  declarations; returns the unevaluated ns declarations.",
    "tag" nil,
    "source"
    "(defn find-ns-decls-in-jarfile\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches the JAR file for Clojure source files containing (ns ...)\n  declarations; returns the unevaluated ns declarations.\"\n  [^JarFile jarfile]\n  (filter identity\n          (map #(read-ns-decl-from-jarfile-entry jarfile %)\n               (clojure-sources-in-jar jarfile))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["jarfile"]]}
   {"ns" "clojure.tools.namespace",
    "name" "comment?",
    "line" 40,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.parse\n\n  Returns true if form is a (comment ...)",
    "tag" nil,
    "source"
    "(defn comment?\n  \"DEPRECATED; moved to clojure.tools.namespace.parse\n\n  Returns true if form is a (comment ...)\"\n  [form]\n  (and (list? form) (= 'comment (first form))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["form"]]}
   {"private" true,
    "ns" "clojure.tools.namespace",
    "name" "filenames-in-jar",
    "line" 127,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- filenames-in-jar [^JarFile jar-file]\n  (map #(.getName ^JarEntry %)\n       (filter #(not (.isDirectory ^JarEntry %))\n               (enumeration-seq (.entries jar-file)))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["jar-file"]]}
   {"ns" "clojure.tools.namespace",
    "name" "read-ns-decl",
    "line" 54,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.parse\n\n  Attempts to read a (ns ...) declaration from rdr, and returns the\n  unevaluated form.  Returns nil if read fails or if a ns declaration\n  cannot be found.  The ns declaration must be the first Clojure form\n  in the file, except for (comment ...)  forms.",
    "tag" nil,
    "source"
    "(defn read-ns-decl\n  \"DEPRECATED; moved to clojure.tools.namespace.parse\n\n  Attempts to read a (ns ...) declaration from rdr, and returns the\n  unevaluated form.  Returns nil if read fails or if a ns declaration\n  cannot be found.  The ns declaration must be the first Clojure form\n  in the file, except for (comment ...)  forms.\"\n  [^PushbackReader rdr]\n  (try\n   (loop [] (let [form (doto (read rdr) str)]\n              (cond\n               (ns-decl? form) form\n               (comment? form) (recur)\n               :else nil)))\n       (catch Exception e nil)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["rdr"]]}
   {"ns" "clojure.tools.namespace",
    "name" "find-namespaces-in-dir",
    "line" 88,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the symbol names of the declared namespaces.",
    "tag" nil,
    "source"
    "(defn find-namespaces-in-dir\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the symbol names of the declared namespaces.\"\n  [^File dir]\n  (map second (find-ns-decls-in-dir dir)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["dir"]]}
   {"private" true,
    "ns" "clojure.tools.namespace",
    "name" "jar-file?",
    "line" 118,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- jar-file? [f]\n  (let [file (io/file f)]\n    (and (.isFile file)\n         (or (.endsWith (.getName file) \".jar\")\n             (.endsWith (.getName file) \".JAR\")))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["f"]]}
   {"ns" "clojure.tools.namespace",
    "name" "find-clojure-sources-in-dir",
    "line" 30,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches recursively under dir for Clojure source files (.clj).\n  Returns a sequence of File objects, in breadth-first sort order.",
    "tag" nil,
    "source"
    "(defn find-clojure-sources-in-dir\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches recursively under dir for Clojure source files (.clj).\n  Returns a sequence of File objects, in breadth-first sort order.\"\n  [^File dir]\n  ;; Use sort by absolute path to get breadth-first search.\n  (sort-by #(.getAbsolutePath ^File %)\n           (filter clojure-source-file? (file-seq dir))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["dir"]]}
   {"private" true,
    "ns" "clojure.tools.namespace",
    "name" "loader-classpath",
    "line" 99,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- loader-classpath [loader]\n  (when (instance? java.net.URLClassLoader loader)\n    (map\n     #(java.io.File. (.getPath ^java.net.URL %))\n     (.getURLs ^java.net.URLClassLoader loader))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["loader"]]}
   {"ns" "clojure.tools.namespace",
    "name" "clojure-sources-in-jar",
    "line" 134,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Returns a sequence of filenames ending in .clj found in the JAR file.",
    "tag" nil,
    "source"
    "(defn clojure-sources-in-jar\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Returns a sequence of filenames ending in .clj found in the JAR file.\"\n  [^JarFile jar-file]\n  (filter #(.endsWith ^String % \".clj\") (filenames-in-jar jar-file)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["jar-file"]]}
   {"ns" "clojure.tools.namespace",
    "name" "clojure-source-file?",
    "line" 22,
    "column" 1,
    "doc"
    "DEPRECATED; trivial to implement locally\n\n  Returns true if file is a normal file with a .clj extension.",
    "tag" nil,
    "source"
    "(defn clojure-source-file?\n  \"DEPRECATED; trivial to implement locally\n\n  Returns true if file is a normal file with a .clj extension.\"\n  [^File file]\n  (and (.isFile file)\n       (.endsWith (.getName file) \".clj\")))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["file"]]}
   {"ns" "clojure.tools.namespace",
    "name" "find-ns-decls-on-classpath",
    "line" 175,
    "column" 1,
    "doc"
    "DEPRECATED; use clojure.tools.namespace.find/find-ns-decls\n  and clojure.java.classpath/classpath from\n  http://github.com/clojure/java.classpath\n\n  Searches CLASSPATH (both directories and JAR files) for Clojure\n  source files containing (ns ...) declarations. Returns a sequence of\n  the unevaluated ns declaration forms.",
    "tag" nil,
    "source"
    "(defn find-ns-decls-on-classpath\n  \"DEPRECATED; use clojure.tools.namespace.find/find-ns-decls\n  and clojure.java.classpath/classpath from\n  http://github.com/clojure/java.classpath\n\n  Searches CLASSPATH (both directories and JAR files) for Clojure\n  source files containing (ns ...) declarations. Returns a sequence of\n  the unevaluated ns declaration forms.\" []\n  (concat\n   (mapcat find-ns-decls-in-dir (classpath-directories))\n   (mapcat find-ns-decls-in-jarfile (classpath-jarfiles))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.tools.namespace",
    "name" "classpath-jarfiles",
    "line" 124,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- classpath-jarfiles []\n  (map #(JarFile. ^File %) (filter jar-file? (classpath))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.namespace",
    "name" "read-ns-decl-from-jarfile-entry",
    "line" 141,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Attempts to read a (ns ...) declaration from the named entry in the\n  JAR file, and returns the unevaluated form.  Returns nil if the read\n  fails, or if the first form is not a ns declaration.",
    "tag" nil,
    "source"
    "(defn read-ns-decl-from-jarfile-entry\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Attempts to read a (ns ...) declaration from the named entry in the\n  JAR file, and returns the unevaluated form.  Returns nil if the read\n  fails, or if the first form is not a ns declaration.\"\n  [^JarFile jarfile ^String entry-name]\n  (with-open [rdr (PushbackReader.\n                   (BufferedReader.\n                    (InputStreamReader.\n                     (.getInputStream jarfile (.getEntry jarfile entry-name)))))]\n    (read-ns-decl rdr)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["jarfile" "entry-name"]]}
   {"ns" "clojure.tools.namespace",
    "name" "find-namespaces-in-jarfile",
    "line" 164,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches the JAR file for Clojure source files containing (ns ...)\n  declarations.  Returns a sequence of the symbol names of the\n  declared namespaces.",
    "tag" nil,
    "source"
    "(defn find-namespaces-in-jarfile\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches the JAR file for Clojure source files containing (ns ...)\n  declarations.  Returns a sequence of the symbol names of the\n  declared namespaces.\"\n  [^JarFile jarfile]\n  (map second (find-ns-decls-in-jarfile jarfile)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["jarfile"]]}
   {"private" true,
    "ns" "clojure.tools.namespace",
    "name" "classpath",
    "line" 105,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- classpath\n  ([classloader]\n     (distinct\n      (mapcat\n       loader-classpath\n       (take-while\n        identity\n        (iterate #(.getParent ^ClassLoader %) classloader)))))\n  ([] (classpath (clojure.lang.RT/baseLoader))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["classloader"] []]}
   {"ns" "clojure.tools.namespace",
    "name" "find-namespaces-on-classpath",
    "line" 187,
    "column" 1,
    "doc"
    "DEPRECATED; use clojure.tools.namespace.find/find-namespaces\n  and clojure.java.classpath/classpath from\n  http://github.com/clojure/java.classpath\n\n  Searches CLASSPATH (both directories and JAR files) for Clojure\n  source files containing (ns ...) declarations.  Returns a sequence\n  of the symbol names of the declared namespaces.",
    "tag" nil,
    "source"
    "(defn find-namespaces-on-classpath\n  \"DEPRECATED; use clojure.tools.namespace.find/find-namespaces\n  and clojure.java.classpath/classpath from\n  http://github.com/clojure/java.classpath\n\n  Searches CLASSPATH (both directories and JAR files) for Clojure\n  source files containing (ns ...) declarations.  Returns a sequence\n  of the symbol names of the declared namespaces.\"\n  []\n  (map second (find-ns-decls-on-classpath)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [[]]}
   {"ns" "clojure.tools.namespace",
    "name" "find-ns-decls-in-dir",
    "line" 80,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the unevaluated ns declarations.",
    "tag" nil,
    "source"
    "(defn find-ns-decls-in-dir\n  \"DEPRECATED; moved to clojure.tools.namespace.find\n\n  Searches dir recursively for (ns ...) declarations in Clojure\n  source files; returns the unevaluated ns declarations.\"\n  [^File dir]\n  (filter identity (map read-file-ns-decl (find-clojure-sources-in-dir dir))))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["dir"]]}
   {"ns" "clojure.tools.namespace",
    "name" "read-file-ns-decl",
    "line" 70,
    "column" 1,
    "doc"
    "DEPRECATED; moved to clojure.tools.namespace.file\n\n  Attempts to read a (ns ...) declaration from file, and returns the\n  unevaluated form.  Returns nil if read fails, or if the first form\n  is not a ns declaration.",
    "tag" nil,
    "source"
    "(defn read-file-ns-decl\n  \"DEPRECATED; moved to clojure.tools.namespace.file\n\n  Attempts to read a (ns ...) declaration from file, and returns the\n  unevaluated form.  Returns nil if read fails, or if the first form\n  is not a ns declaration.\"\n  [^File file]\n  (with-open [rdr (PushbackReader. (BufferedReader. (FileReader. file)))]\n    (read-ns-decl rdr)))",
    "file" "clojure/tools/namespace.clj",
    "arglists" [["file"]]}],
  "clojure.tools.namespace.repl"
  [{"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "unload-disabled?",
    "line" 34,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- unload-disabled? [sym]\n  (or (false? (::unload (meta (find-ns sym))))\n      (load-disabled? sym)))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["sym"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "do-refresh",
    "line" 43,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- do-refresh [scan-fn after-sym]\n  (when after-sym\n    (assert (symbol? after-sym) \":after value must be a symbol\")\n    (assert (namespace after-sym)\n            \":after value must be a namespace-qualified symbol\"))\n  (let [current-ns (ns-name *ns*)]\n    (alter-var-root #'refresh-tracker\n                    #(apply scan-fn % refresh-dirs))\n    (alter-var-root #'refresh-tracker remove-disabled)\n    (print-pending-reloads refresh-tracker)\n    (alter-var-root #'refresh-tracker reload/track-reload)\n    (in-ns current-ns)\n    (let [result (print-and-return refresh-tracker)]\n      (if (and (= :ok result) after-sym)\n        ((ns-resolve *ns* after-sym))\n        result))))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["scan-fn" "after-sym"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "print-pending-reloads",
    "line" 28,
    "column" 1,
    "tag" nil,
    "source" nil,
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["tracker"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "remove-disabled",
    "line" 38,
    "column" 1,
    "tag" nil,
    "source" nil,
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["tracker"]]}
   {"ns" "clojure.tools.namespace.repl",
    "name" "disable-reload!",
    "line" 67,
    "column" 1,
    "doc"
    "Adds metadata to namespace (or *ns* if unspecified) telling\n  'refresh' not to load it. Implies disable-unload! also.",
    "tag" nil,
    "source"
    "(defn disable-reload!\n  \"Adds metadata to namespace (or *ns* if unspecified) telling\n  'refresh' not to load it. Implies disable-unload! also.\"\n  ([] (disable-reload! *ns*))\n  ([namespace] (alter-meta! namespace assoc ::load false)))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [[] ["namespace"]]}
   {"ns" "clojure.tools.namespace.repl",
    "name" "set-refresh-dirs",
    "line" 109,
    "column" 1,
    "doc"
    "Sets the directories which are scanned by 'refresh'. Supports the\n  same types as clojure.java.io/file.",
    "tag" nil,
    "source"
    "(defn set-refresh-dirs\n  \"Sets the directories which are scanned by 'refresh'. Supports the\n  same types as clojure.java.io/file.\"\n  [& dirs]\n  (alter-var-root #'refresh-dirs (constantly dirs)))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["&" "dirs"]]}
   {"ns" "clojure.tools.namespace.repl",
    "name" "refresh-all",
    "line" 92,
    "column" 1,
    "doc"
    "Scans source code directories for all Clojure source files and\n  reloads them in dependency order.\n\n  The directories to be scanned are controlled by 'set-refresh-dirs';\n  defaults to all directories on the Java classpath.\n\n  Options are key-value pairs. Valid options are:\n\n      :after   Namespace-qualified symbol naming a zero-argument\n               function to be invoked after a successful refresh. This\n               symbol will be resolved *after* all namespaces have\n               been reloaded.",
    "tag" nil,
    "source"
    "(defn refresh-all\n  \"Scans source code directories for all Clojure source files and\n  reloads them in dependency order.\n\n  The directories to be scanned are controlled by 'set-refresh-dirs';\n  defaults to all directories on the Java classpath.\n\n  Options are key-value pairs. Valid options are:\n\n      :after   Namespace-qualified symbol naming a zero-argument\n               function to be invoked after a successful refresh. This\n               symbol will be resolved *after* all namespaces have\n               been reloaded.\"\n  [& options]\n  (let [{:keys [after]} options]\n    (do-refresh dir/scan-all after)))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["&" "options"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "refresh-tracker",
    "line" 16,
    "column" 1,
    "tag" nil,
    "source" "(defonce ^:private refresh-tracker (track/tracker))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" nil}
   {"ns" "clojure.tools.namespace.repl",
    "name" "refresh",
    "line" 73,
    "column" 1,
    "doc"
    "Scans source code directories for files which have changed (since\n  the last time this function was run) and reloads them in dependency\n  order. Returns :ok or an error; sets the latest exception to\n  clojure.core/*e (if *e is thread-bound).\n\n  The directories to be scanned are controlled by 'set-refresh-dirs';\n  defaults to all directories on the Java classpath.\n\n  Options are key-value pairs. Valid options are:\n\n      :after   Namespace-qualified symbol naming a zero-argument\n               function to be invoked after a successful refresh. This\n               symbol will be resolved *after* all namespaces have\n               been reloaded.",
    "tag" nil,
    "source"
    "(defn refresh\n  \"Scans source code directories for files which have changed (since\n  the last time this function was run) and reloads them in dependency\n  order. Returns :ok or an error; sets the latest exception to\n  clojure.core/*e (if *e is thread-bound).\n\n  The directories to be scanned are controlled by 'set-refresh-dirs';\n  defaults to all directories on the Java classpath.\n\n  Options are key-value pairs. Valid options are:\n\n      :after   Namespace-qualified symbol naming a zero-argument\n               function to be invoked after a successful refresh. This\n               symbol will be resolved *after* all namespaces have\n               been reloaded.\"\n  [& options]\n  (let [{:keys [after]} options]\n    (do-refresh dir/scan after)))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["&" "options"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "print-and-return",
    "line" 20,
    "column" 1,
    "tag" nil,
    "source" nil,
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["tracker"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "load-disabled?",
    "line" 31,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- load-disabled? [sym]\n  (false? (::load (meta (find-ns sym)))))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [["sym"]]}
   {"ns" "clojure.tools.namespace.repl",
    "name" "disable-unload!",
    "line" 60,
    "column" 1,
    "doc"
    "Adds metadata to namespace (or *ns* if unspecified) telling\n  'refresh' not to unload it. The namespace may still be reloaded, it\n  just won't be removed first.",
    "tag" nil,
    "source"
    "(defn disable-unload!\n  \"Adds metadata to namespace (or *ns* if unspecified) telling\n  'refresh' not to unload it. The namespace may still be reloaded, it\n  just won't be removed first.\"\n  ([] (disable-unload! *ns*))\n  ([namespace] (alter-meta! namespace assoc ::unload false)))",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" [[] ["namespace"]]}
   {"private" true,
    "ns" "clojure.tools.namespace.repl",
    "name" "refresh-dirs",
    "line" 18,
    "column" 1,
    "tag" nil,
    "source" "(defonce ^:private refresh-dirs [])",
    "file" "clojure/tools/namespace/repl.clj",
    "arglists" nil}]},
 "description" "tools.namespace 0.2.2",
 "version" "0.2.2",
 "name" "clojure.tools.namespace",
 "group" "clojure.tools.namespace"}
