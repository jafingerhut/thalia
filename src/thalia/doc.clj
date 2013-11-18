(ns thalia.doc
  (:require [clojure.pprint :as pp]
            [clojure.java.io :as io]
            [clojure.set :as set]
            [thalia.utils :refer [iprintf read-safely]]))


(defn- append-doc! [v additional-docstring]
  (let [m (meta v)
        ;; Save the original doc string if we have not done so earlier
        m (if (contains? m :thalia/original-doc)
            m
            (assoc m :thalia/original-doc (:doc m)))
        ^String orig-doc (:thalia/original-doc m)
        new-doc (str orig-doc
                     (if (.endsWith orig-doc "\n")
                       ""
                       "\n")
"--------- ^^^ original docs --------- VVV unofficial extra docs ---------\n"
                     additional-docstring)
        m (assoc m :doc new-doc)]
    (reset-meta! v m)))


;; TBD: Find somewhere appropriate to describe the behavior of
;; keywords looking themselves up in collections via (:keyword coll)
;; syntax.

;; TBD: Are the sequences returned by subseq and rsubseq lazy?


(defn- ns-if-loaded [ns-name-str]
  (try
    (the-ns (symbol ns-name-str))
    (catch Exception e
        nil)))


(defn- do-the-add! [opts rsrc-fname doc-data]
  ;; TBD: Is there a 'standard' way to check for libraries other
  ;; than clojure.core what version is loaded?
  (let [missing-namespaces-warned (atom #{})]
    (doseq [[{:keys [library version]} data-for-symbols] doc-data]
      (doseq [one-sym-data data-for-symbols]
        (if-let [missing-keys (seq (set/difference #{:ns :symbol
                                                     :extended-docstring}
                                                   (set (keys one-sym-data))))]
          (do
            (binding [*out* *err*]
              (iprintf "Resource file '%s' has the following map that is missing needed keys %s:\n"
                       rsrc-fname missing-keys)
              (pp/pprint one-sym-data)
              (println)))
          (let [ns-name-str (:ns one-sym-data)
                sym-name-str (:symbol one-sym-data)
                extended-docstring (:extended-docstring one-sym-data)
                ns (find-ns (symbol ns-name-str))
                sym (symbol ns-name-str sym-name-str)]
            (if-let [var (try (resolve sym)
                              (catch Exception e nil))]
              (do
                (append-doc! var extended-docstring)
                (when (:debug opts)
                  (iprintf *err* "Added extended doc string for symbol %s\n" sym)))
              (if ns
                (iprintf *err* "Resource file '%s' has extra docs for var %s in namespace %s, but you have no such symbol in that namespace -- do you have a different version of library '%s' than '%s'?\n"
                         rsrc-fname sym-name-str ns-name-str library version)
                (do
                  (when-not (@missing-namespaces-warned ns-name-str)
                    (iprintf *err* "Skipping extra docs for namespace %s in resource file '%s -- no such namespace is loaded\n"
                             ns-name-str rsrc-fname)
                    (swap! missing-namespaces-warned conj ns-name-str)))))))))))


(defn add-extra-docs!
  "Modify the doc strings of vars, by adding extra docs specified in a
resource file.  Options can be given as args: key1 val1 key2 val2 ...

Examples:
    (add-extra-docs!)
    (add-extra-docs! :debug true :language \"en_US\")

    :language - A string naming the language of the extra docs to add.
                Defaults to the language of your default locale in the
                JVM, e.g. \"en_US\" is US English.

    :debug - A boolean indicating whether to print extra debug
             messages.  Defaults to false."
  [& args]
  (let [default-locale (str (java.util.Locale/getDefault))
        opts (merge {:language default-locale}
                    (apply hash-map args))
        rsrc-fname (str (:language opts) ".clj")
        file (io/resource rsrc-fname)]
    (if-not file
      (iprintf *err* "No resource file %s exists.  Either '%s' is not a language,
or no one has written thalia docs in that language yet.\n"
               rsrc-fname (:language opts))
      (let [doc-data (read-safely file)]
        (do-the-add! opts rsrc-fname doc-data)))))


(comment
(in-ns 'user) (use 'clojure.repl) (require 'thalia.doc) (thalia.doc/add-extra-docs!)
)
