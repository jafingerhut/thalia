(ns thalia.utils
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))


(def ^:dynamic *auto-flush* true)


(defn- printf-to-writer [w fmt-str & args]
  (binding [*out* w]
    (apply clojure.core/printf fmt-str args)
    (when *auto-flush* (flush))))


(defn iprintf [fmt-str-or-writer & args]
  (if (instance? CharSequence fmt-str-or-writer)
    (apply printf-to-writer *out* fmt-str-or-writer args)
    (apply printf-to-writer fmt-str-or-writer args)))


(defn die [fmt-str & args]
  (apply iprintf *err* fmt-str args)
  (System/exit 1))


(defn read-safely [x & opts]
  (with-open [r (java.io.PushbackReader. (apply io/reader x opts))]
    (edn/read r)))


(defn map-keys [f m]
  (into (empty m)
        (for [[k v] m] [(f k) v])))


(defn map-vals [f m]
  (into (empty m)
        (for [[k v] m] [k (f v)])))


(defn filter-keys [f m]
  (into (empty m)
        (filter (fn [[k _]] (f k)) m)))


(defn filter-vals [f m]
  (into (empty m)
        (filter (fn [[_ v]] (f v)) m)))


(defn double-range
  "TBD: Descriptive text.

Examples:

    user=> (count (double-range 0.0 10.0 0.1))
    100

    user=> (last (double-range 0.0 10.0 0.1))
    9.9"
  [start end step]
  (map #(+ start (* % step))
       (range (/ (- end start) step))))


(defn rangef
  "Returns a sequence of n numbers from start to end inclusive.

Examples:

    user=> (count (rangef 0.0 10.0 100))
    100

    ;; Note that rangef does *not* give the same last value as
    ;; double-range, by design.
    user=> (last (rangef 0.0 10.0 100))
    10.0"
  [start end n]
  (for [i (range n)]
    (+ start (* i (/ (- end start) (dec n))))))
