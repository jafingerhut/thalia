(ns thalia.core
  (:gen-class)
  (:import (java.io File))
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.edn :as edn]
            [thalia.utils :refer :all]
            [cheshire.core :as cheshire]
            [me.raynes.fs :as fs]))


(defn show-usage [prog-name]
  (iprintf *err* "usage:
    %s [ help ]
    %s json2edn
    %s cdocs-summary
    %s create-empty-doc-files <language>
    %s create-doc-rsrc <language>
" prog-name prog-name prog-name prog-name prog-name))


(def prog-name "lein run")


(defn file-with-suffix [^File f suffix]
  (and (not (. f (isDirectory)))
       (.endsWith (str f) suffix)))


(defn get-sym-info
  "Get the info from a lein-clojuredocs data structure x about the
  symbol named sym-str in the namespace named ns-str."
  [x ns-name-str sym-str]
  (let [ns-info (get-in x ["namespaces" ns-name-str])]
    (first (filter #(= (get % "name") sym-str) ns-info))))


(defn symbols-in-namespaces
  [x]
  (let [y (get x "namespaces")]
    (into {} (map-vals (fn [sym-info-list]
                         (map #(get % "name") sym-info-list))
                       y))))
  

(defn clojuredocs-file-data [f]
  (edn/read-string (slurp f)))


(defn cdocs-data-from-all-clj-files-in-dir [dir]
  (->> (file-seq (io/file dir))
       (filter #(file-with-suffix % ".clj"))
       (map (fn [f]
              [(str f) (clojuredocs-file-data f)]))
       (into {})
       ;; next step removes any files that contained only 'nil'
       (filter-vals identity)))


(defn iterate-over-cdocs-data
  [cdocs-data cb-data per-project-cb-fn per-namespace-cb-fn
   before-all-public-symbols-cb-fn per-public-symbol-cb-fn
   before-all-private-symbols-cb-fn per-private-symbol-cb-fn]
  (doseq [fname (sort (keys cdocs-data))]
    (let [project-data (cdocs-data fname)
          all-ns-data (project-data "namespaces")]
      (when per-project-cb-fn
        (per-project-cb-fn cb-data project-data))
      (doseq [ns-name-str (sort (keys all-ns-data))]
        (let [all-syms (all-ns-data ns-name-str)
              public-syms (remove #(get % "private") all-syms)
              private-syms (filter #(get % "private") all-syms)]
          (when per-namespace-cb-fn
            (per-namespace-cb-fn cb-data project-data ns-name-str all-syms
                                 public-syms private-syms))
          (when before-all-public-symbols-cb-fn
            (before-all-public-symbols-cb-fn cb-data project-data ns-name-str
                                             all-syms public-syms))
          (when per-public-symbol-cb-fn
            (doseq [sym-info (sort-by #(get % "name") public-syms)]
              (per-public-symbol-cb-fn cb-data project-data ns-name-str
                                       all-syms sym-info)))
          (when before-all-private-symbols-cb-fn
            (before-all-private-symbols-cb-fn cb-data project-data ns-name-str
                                              all-syms private-syms))
          (when per-private-symbol-cb-fn
            (doseq [sym-info (sort-by #(get % "name") private-syms)]
              (per-private-symbol-cb-fn cb-data project-data ns-name-str
                                        all-syms sym-info)))
          )))))


(defn print-project-heading [cb-data project-data]
  (iprintf "%s %s\n" (project-data "name") (project-data "version")))

(defn print-namespace-info
  [cb-data project-data ns-name-str all-syms public-syms private-syms]
  (iprintf "    %3d %3d %s\n"
           (count public-syms) (count private-syms) ns-name-str))

(defn print-public-symbol-header
  [cb-data project-data ns-name-str all-syms public-syms]
  (when (and (:show-public-symbol-names cb-data)
             (> (count public-syms) 0))
    (iprintf "            Public symbols:\n")))

(defn print-public-symbol-name
  [cb-data project-data ns-name-str all-syms sym-info]
  (when (:show-public-symbol-names cb-data)
    (iprintf "             %s\n" (sym-info "name"))))

(defn print-private-symbol-header
  [cb-data project-data ns-name-str all-syms private-syms]
  (when (and (:show-private-symbol-names cb-data)
             (> (count private-syms) 0))
    (iprintf "            Private symbols:\n")))

(defn print-private-symbol-name
  [cb-data project-data ns-name-str all-syms sym-info]
  (when (:show-private-symbol-names cb-data)
    (iprintf "             %s\n" (sym-info "name"))))


(defn- one-hex-escape-to-byte [escape]
  (-> escape
      (subs 1 3)
      (Integer/parseInt 16)
      (#(if (< % 128) % (- % 256)))
      byte))


(defn decode-url-component
  "Section 2.5 of RFC 3986 'Uniform Resource Identifier (URI): Generic
Syntax' says:

    When a new URI scheme defines a component that represents textual
    data consisting of characters from the Universal Character Set
    [UCS], the data should first be encoded as octets according to the
    UTF-8 character encoding [STD63]; then only those octets that do
    not correspond to characters in the unreserved set should be
    percent-encoded.

Thus to convert a URL to a string, take consecutive sequences of %HH
escape sequences, where HH are hex digits, convert them to bytes, and
treat those bytes as strings encoded using UTF-8."
  [s]
  (str/replace s
               #"((%..)+)"
               (fn [escapes]
                 (String. (->> (first escapes)
                               (re-seq #"%..")
                               (map one-hex-escape-to-byte)
                               byte-array)
                          "UTF-8"))))


(defn- one-byte-to-hex-escape [b]
  (let [b (if (< b 0)
            (+ b 256)
            b)]
    (format "%%%02X" b)))


(defn encode-url-component
  "Section 2.3 of RFC 3986 'Uniform Resource Identifier (URI): Generic
Syntax' specifies the list of unreserved characters in a URI as

    unreserved  = ALPHA / DIGIT / \"-\" / \".\" / \"_\" / \"~\"

This function does percent-encoding of all characters except the
unreserved ones, with the assumption that this will give the maximum
safety possible in getting other characters through the encode-decode
process, with as few characters needed in the set supported after
encoding and before decoding."
  [s]
  (let [orig-s s]
    (loop [s s
           encoded-strs []]
      (if (= s "")
        (let [encoded-str (apply str encoded-strs)
              encoded-then-decoded-str (decode-url-component encoded-str)]
          (when (not= orig-s encoded-then-decoded-str)
            (die "Internal error in encode-url-component or decode-url-component, because '%s' encodes as '%s', and then decodes as '%s', which is not equal to the original string"
                 orig-s encoded-str encoded-then-decoded-str))
          encoded-str)
        (let [[_ unreserved ^String reserved s]
              (re-find #"(?x) ^
                         (  [-._~a-zA-Z0-9]* )
                         ( [^-._~a-zA-Z0-9]* )
                         ( .* )
                         $" s)
              escaped-bytes (->> (.getBytes reserved "UTF-8")
                                 (map one-byte-to-hex-escape))]
          (recur s (conj encoded-strs unreserved
                         (apply str escaped-bytes))))))))


(def path-sep (get (System/getProperties) "file.separator"))
(def path-sep-pat (re-pattern (str "\\Q" path-sep "\\E")))

(defn path-from-parts [parts]
  (apply str (interpose path-sep parts)))

(defn parts-from-path [path]
  (str/split path path-sep-pat))


(defn doc-file-name [root-dir project-name project-version & args]
  (let [namespace-name (first args)
        symbol-name (second args)
        fname-suffix (if (>= (count args) 3) (nth args 2))]
    (path-from-parts (concat
                      [root-dir
                       (encode-url-component project-name)
                       (encode-url-component project-version)]
                      (if namespace-name
                        [ (encode-url-component namespace-name) ])
                      (if symbol-name
                        [ (encode-url-component symbol-name) ])
                      (if fname-suffix
                        fname-suffix)))))

(defn make-dir! [dir-name dry-run?]
  (let [^File dir-file (io/file dir-name)]
    (if (.exists dir-file)
      (if dry-run?
        (iprintf "Directory '%s' already exists.  Skip creating it.\n"
                 dir-name))
      (if dry-run?
        (iprintf "Directory '%s' does not exist.  Creating it.\n" dir-name)
        (when-not (.mkdirs dir-file)
          (die ".mkdir '%s' failed.  Aborting.\n" dir-name))))))

(defn make-doc-file! [docfile-name dry-run?]
  (let [^File docfile-file (io/file docfile-name)]
    (if (.exists docfile-file)
      (if dry-run?
        (iprintf "File '%s' already exists.  Skip creating it.\n"
                 docfile-name))
      (if dry-run?
        (iprintf "File '%s' does not exist.  Creating it.\n" docfile-name)
        (try
          (spit docfile-file "")
          (catch Throwable t
            (die ".mkdir '%s' failed.  Aborting.\n" docfile-name)))))))
  
(defn make-project-dir [cb-data project-data]
  (let [root-dir (:root-dir cb-data)
        project-dir-name (doc-file-name root-dir (project-data "name")
                                        (project-data "version"))]
    (make-dir! project-dir-name (:dry-run? cb-data))))

(defn make-namespace-dir
  [cb-data project-data ns-name-str all-syms public-syms private-syms]
  (let [root-dir (:root-dir cb-data)
        namespace-dir-name (doc-file-name root-dir (project-data "name")
                                          (project-data "version") ns-name-str)]
    (make-dir! namespace-dir-name (:dry-run? cb-data))))

(defn make-public-symbol-doc-file
  [cb-data project-data ns-name-str all-syms sym-info]
  (let [root-dir (:root-dir cb-data)
        symbol-file-name (doc-file-name root-dir (project-data "name")
                                        (project-data "version") ns-name-str
                                        (sym-info "name")
                                        (:filename-suffix cb-data))]
    (make-doc-file! symbol-file-name (:dry-run? cb-data))))


(defn project-doc-name-components [^String fname root-dir fname-suffix]
  (if (.startsWith fname root-dir)
    (let [;; remove root-dir prefix
          fname (subs fname (count root-dir))
          ;; remove leading path separator if there is one
          fname (if (.startsWith fname path-sep)
                  (subs fname 1)
                  fname)
          ;; remove fname-suffix if it is there
          fname (if (.endsWith fname fname-suffix)
                  (subs fname 0 (- (count fname) (count fname-suffix)))
                  fname)
          ;; break path name up into separate components
          [lang lib version ns sym] (map decode-url-component
                                         (parts-from-path fname))]
      {:language lang, :library lib, :version version, :ns ns, :symbol sym})))


(defn non-empty-project-docs [dir lang]
  (let [fname-suffix ".txt"]
    (->> (file-seq (io/file (path-from-parts [dir lang])))
         (filter #(file-with-suffix % fname-suffix))
         (map (fn [f] {:filename (str f) :extended-docstring (slurp f)}))
         ;; remove any files that are completely blank
         (remove #(str/blank? (:extended-docstring %)))
         ;; parse file names for project name, version, namespace, and symbol
         (map #(merge % (project-doc-name-components (:filename %)
                                                     dir fname-suffix)))
         (group-by #(select-keys % [:language :library :version]))

         ;; After grouping, we don't need the keys :language :library
         ;; :version in every map, nor do we need :filename any
         ;; longer.
         (map-vals (fn [map-list]
                     (map #(dissoc % :language :library :version :filename)
                          map-list))))))


(def clojuredocs-files-root (path-from-parts ["." "doc" "clojuredocs"]))
(def project-docs-root (path-from-parts ["." "doc" "project-docs"]))
(def resource-root-dir (path-from-parts ["." "resource"]))


(defn -main [& args]
  (when (or (= 0 (count args))
            (#{"help"} (first args)))
    (show-usage prog-name)
    (System/exit 0))
  (let [[action & args] args]
    (case action

      "json2edn"
      (let [data (cheshire/parse-stream (io/reader *in*))]
        (pp/pprint data))

      "cdocs-summary"
      (let [arg-set (set args)
            show-publics (arg-set "public-symbols")
            show-privates (arg-set "private-symbols")
            fname-to-data
            (cdocs-data-from-all-clj-files-in-dir clojuredocs-files-root)]
        (iterate-over-cdocs-data fname-to-data
                                 {:show-public-symbol-names show-publics
                                  :show-private-symbol-names show-privates}
                                 print-project-heading
                                 print-namespace-info
                                 print-public-symbol-header
                                 print-public-symbol-name
                                 print-private-symbol-header
                                 print-private-symbol-name))

      "create-empty-doc-files"
      (do
        (when-not (= 1 (count args))
          (iprintf *err* "Must specify language\n")
          (show-usage prog-name)
          (System/exit 1))
        (let [lang (first args)
              fname-to-data
              (cdocs-data-from-all-clj-files-in-dir clojuredocs-files-root)]
          (iterate-over-cdocs-data fname-to-data
                                   {:root-dir (path-from-parts
                                               [project-docs-root lang])
                                    :dry-run? false
                                    :filename-suffix ".txt"}
                                   make-project-dir
                                   make-namespace-dir
                                   nil    ;; before all public symbols
                                   make-public-symbol-doc-file
                                   nil    ;; before all private symbols
                                   nil)))  ;; per private symbol
      
      "create-doc-rsrc"
      (do
        (when-not (= 1 (count args))
          (iprintf *err* "Must specify language\n")
          (show-usage prog-name)
          (System/exit 1))
        (let [lang (first args)
              lang-root (path-from-parts [project-docs-root lang])
              non-empty-doc-files (non-empty-project-docs project-docs-root
                                                          lang)]
          (when-not (fs/exists? lang-root)
            (die "Directory %s does not exist.  Aborting.\n" lang-root))
          (with-open [wrtr (io/writer (path-from-parts [resource-root-dir
                                                        (str lang ".clj")]))]
            (binding [*out* wrtr]
              (pp/pprint (filter-keys #(= lang (:language %))
                                      non-empty-doc-files))))))

      ;; default case
      (do (iprintf *err* "Urecognized first arg '%s'\n" action)
          (show-usage prog-name)
          (System/exit 1))
      )))
