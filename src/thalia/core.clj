(ns thalia.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.tools.reader.edn :as edn]
            [cheshire.core :as cheshire]))


(def ^:dynamic *auto-flush* true)

(defn printf-to-writer [w fmt-str & args]
  (binding [*out* w]
    (apply clojure.core/printf fmt-str args)
    (when *auto-flush* (flush))))

(defn iprintf [fmt-str-or-writer & args]
  (if (instance? CharSequence fmt-str-or-writer)
    (apply printf-to-writer *out* fmt-str-or-writer args)
    (apply printf-to-writer fmt-str-or-writer args)))


(defn show-usage [prog-name]
  (iprintf *err* "usage:
    %s [ help ]
    %s json2edn
    %s cdocs-summary
" prog-name prog-name prog-name))


(def prog-name "lein2 run")


(defn dot-clj-file [f]
  (and (not (. f (isDirectory)))
       (let [s (str f)
             n (count s)]
         (and (>= n 4)
              (= ".clj" (subs s (- n 4) n))))))


(defn get-sym-info
  "Get the info from a lein-clojuredocs data structure x about the
  symbol named sym-str in the namespace named ns-str."
  [x ns-str sym-str]
  (let [ns-info (get-in x ["namespaces" ns-str])]
    (first (filter #(= (get % "name") sym-str) ns-info))))


(defn map-keys [f m]
  (into (empty m)
        (for [[k v] m] [(f k) v])))

(defn map-vals [f m]
  (into (empty m)
        (for [[k v] m] [k (f v)])))

(defn filter-vals [f m]
  (into (empty m)
        (filter (fn [[_ v]] (f v)) m)))


(defn symbols-in-namespaces
  [x]
  (let [y (get x "namespaces")]
    (into {} (map-vals (fn [sym-info-list]
                         (map #(get % "name") sym-info-list))
                       y))))
  

(defn clojuredocs-file-data [f]
  (edn/read-string (slurp f)))


(defn -main [& args]
  (when (or (= 0 (count args))
            (#{"-h" "--help" "-help" "help"} (first args)))
    (show-usage prog-name)
    (System/exit 0))
  (let [[action & args] args]
    (case action

      "json2edn"
      (let [data (cheshire/parse-stream (io/reader *in*))]
        (pp/pprint data))

      "cdocs-summary"
      (let [arg-set (set args)
            show-public-symbol-names (arg-set "public-symbols")
            show-private-symbol-names (arg-set "private-symbols")
            fname-to-data
            (->> (file-seq (io/file "./doc/clojuredocs"))
                 (filter dot-clj-file)
                 (map (fn [f]
                        [(str f) (clojuredocs-file-data f)]))
                 (into {})
                 ;; next step removes any files that contained only 'nil'
                 (filter-vals identity))]
        (doseq [fname (sort (keys fname-to-data))]
          (let [dat (fname-to-data fname)
                summary (dat "namespaces")]
            (iprintf "%s %s\n" (dat "name") (dat "version"))
            (doseq [ns-str (sort (keys summary))]
              (let [all-syms (summary ns-str)
                    public-syms (remove #(get % "private") all-syms)
                    private-syms (filter #(get % "private") all-syms)]
                (iprintf "    %3d %3d %s\n"
                         (count public-syms) (count private-syms) ns-str)
                (when show-public-symbol-names
                  (when (> (count public-syms) 0)
                    (iprintf "            Public symbols:\n"))
                  (doseq [sym-info (sort-by #(get % "name") public-syms)]
                    (iprintf "             %s\n" (sym-info "name"))))
                (when show-private-symbol-names
                  (when (> (count private-syms) 0)
                    (iprintf "            Private symbols:\n"))
                  (doseq [sym-info (sort-by #(get % "name") private-syms)]
                    (iprintf "             %s\n" (sym-info "name"))))
                )))))
      
      ;; default case
      (do (iprintf *err* "Urecognized first arg '%s'\n" action)
          (show-usage prog-name)
          (System/exit 1))
      )))
