{"namespaces"
 {"clojure.java.classpath"
  [{"ns" "clojure.java.classpath",
    "name" "classpath",
    "line" 55,
    "column" 1,
    "doc"
    "Returns a sequence of File objects of the elements on the classpath.",
    "tag" nil,
    "source"
    "(defn classpath\n  \"Returns a sequence of File objects of the elements on the classpath.\"\n  ([classloader]\n     (distinct\n      (mapcat\n       loader-classpath\n       (take-while\n        identity\n        (iterate #(.getParent ^ClassLoader %) classloader)))))\n  ([] (classpath (clojure.lang.RT/baseLoader))))",
    "file" "clojure/java/classpath.clj",
    "arglists" [["classloader"] []]}
   {"ns" "clojure.java.classpath",
    "name" "classpath-jarfiles",
    "line" 71,
    "column" 1,
    "doc"
    "Returns a sequence of JarFile objects for the JAR files on classpath.",
    "tag" nil,
    "source"
    "(defn classpath-jarfiles\n  \"Returns a sequence of JarFile objects for the JAR files on classpath.\"\n  []\n  (map #(JarFile. ^File %) (filter jar-file? (classpath))))",
    "file" "clojure/java/classpath.clj",
    "arglists" [[]]}
   {"ns" "clojure.java.classpath",
    "name" "loader-classpath",
    "line" 49,
    "column" 1,
    "doc" "Returns a sequence of File paths from a classloader.",
    "tag" nil,
    "source"
    "(defn loader-classpath\n  \"Returns a sequence of File paths from a classloader.\"\n  [loader]\n  (when (instance? java.net.URLClassLoader loader)\n    (map io/as-file (.getURLs ^java.net.URLClassLoader loader))))",
    "file" "clojure/java/classpath.clj",
    "arglists" [["loader"]]}
   {"ns" "clojure.java.classpath",
    "name" "jar-file?",
    "line" 25,
    "column" 1,
    "doc"
    "Returns true if file is a normal file with a .jar or .JAR extension.",
    "tag" nil,
    "source"
    "(defn jar-file?\n  \"Returns true if file is a normal file with a .jar or .JAR extension.\"\n  [f]\n  (let [file (io/file f)]\n    (and (.isFile file)\n         (or (.endsWith (.getName file) \".jar\")\n             (.endsWith (.getName file) \".JAR\")))))",
    "file" "clojure/java/classpath.clj",
    "arglists" [["f"]]}
   {"ns" "clojure.java.classpath",
    "name" "filenames-in-jar",
    "line" 33,
    "column" 1,
    "doc"
    "Returns a sequence of Strings naming the non-directory entries in\n  the JAR file.",
    "tag" nil,
    "source"
    "(defn filenames-in-jar\n  \"Returns a sequence of Strings naming the non-directory entries in\n  the JAR file.\"\n  [^JarFile jar-file]\n  (map #(.getName ^JarEntry %)\n       (filter #(not (.isDirectory ^JarEntry %))\n               (enumeration-seq (.entries jar-file)))))",
    "file" "clojure/java/classpath.clj",
    "arglists" [["jar-file"]]}
   {"ns" "clojure.java.classpath",
    "name" "system-classpath",
    "line" 41,
    "column" 1,
    "doc"
    "Returns a sequence of File paths from the 'java.class.path' system\n  property.",
    "tag" nil,
    "source"
    "(defn system-classpath\n  \"Returns a sequence of File paths from the 'java.class.path' system\n  property.\"\n  []\n  (map #(File. ^String %)\n       (.split (System/getProperty \"java.class.path\")\n               (System/getProperty \"path.separator\"))))",
    "file" "clojure/java/classpath.clj",
    "arglists" [[]]}
   {"ns" "clojure.java.classpath",
    "name" "classpath-directories",
    "line" 66,
    "column" 1,
    "doc"
    "Returns a sequence of File objects for the directories on classpath.",
    "tag" nil,
    "source"
    "(defn classpath-directories\n  \"Returns a sequence of File objects for the directories on classpath.\"\n  []\n  (filter #(.isDirectory ^File %) (classpath)))",
    "file" "clojure/java/classpath.clj",
    "arglists" [[]]}]},
 "description" "java.classpath 0.2.1",
 "version" "0.2.1",
 "name" "clojure.java.classpath",
 "group" "clojure.java.classpath"}
