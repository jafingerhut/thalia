(ns thalia.doc
  (:require [clojure.pprint :as pp]
            [clojure.java.io :as io]
            [clojure.edn :as edn]))


(def ^:dynamic *auto-flush* true)

(defn printf-to-writer [w fmt-str & args]
  (binding [*out* w]
    (apply clojure.core/printf fmt-str args)
    (when *auto-flush* (flush))))

(defn iprintf [fmt-str-or-writer & args]
  (if (instance? CharSequence fmt-str-or-writer)
    (apply printf-to-writer *out* fmt-str-or-writer args)
    (apply printf-to-writer fmt-str-or-writer args)))


(defn read-safely [x & opts]
  (with-open [r (java.io.PushbackReader. (apply io/reader x opts))]
    (edn/read r)))


(defn alter-doc! [v new-docstring]
  (alter-meta! v assoc :doc new-docstring))


(defn append-doc! [v additional-docstring]
  (let [^String orig-doc (:doc (meta v))]
    (alter-doc! v
                (str orig-doc
                     (if (.endsWith orig-doc "\n")
                       ""
                       "\n")
"--------- ^^^ original docs --------- VVV unofficial extra docs ---------\n"
                     additional-docstring))))

;; TBD: Find somewhere appropriate to describe the behavior of
;; keywords looking themselves up in collections via (:keyword coll)
;; syntax.

;; TBD: Are the sequences returned by subseq and rsubseq lazy?


(defn ns-if-loaded [ns-name-str]
  (try
    (the-ns (symbol ns-name-str))
    (catch Exception e
        nil)))


(defn add-extra-docs! [& args]
  (let [default-locale (str (java.util.Locale/getDefault))
        opts (merge {:language default-locale}
                    (apply hash-map args))
        rsrc-name (str (:language opts) ".clj")
        doc-data (read-safely (io/resource rsrc-name))]

    ;; TBD: Is there a 'standard' way to check for libraries other
    ;; than clojure.core what version is loaded?

    (doseq [[{:keys [library version]} data-for-symbols] doc-data]
      (doseq [one-sym-data data-for-symbols]
        (let [ns (:ns one-sym-data)
              sym (:symbol one-sym-data)
              extended-docstring (:extended-docstring one-sym-data)]
          (if (and ns sym extended-docstring)
            (if-let [var (try (resolve (symbol ns sym))
                              (catch Exception e nil))]
              (append-doc! var extended-docstring)
              (iprintf *err* "No such var %s/%s\n" ns sym))
            ;; else
            (do
              (binding [*out* *err*]
                (iprintf "Resource file has this record that is missing one of the keys [:ns :symbol :extended-docstring]:\n")
                (pp/pprint one-sym-data))
              )))))))


(comment
(in-ns 'user) (use 'clojure.repl) (require 'thalia.doc) (thalia.doc/add-extra-docs!)
)
