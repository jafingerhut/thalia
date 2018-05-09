(ns thalia.collections-test
  (:use clojure.test
        thalia.doc)
  (:require
   [clojure.data.avl :as avl]
   [flatland.ordered.set :as fset]
   [flatland.ordered.map :as fmap]
   [flatland.useful.map :as umap]
   [clojure.data.int-map :as imap]
   [clojure.data.priority-map :as pmap]
   ))


;; Test clojure.core/= and Java .equals between many pairs of Java and
;; Clojure collections, including those in some 3rd party Clojure
;; libraries.

;; Function is-same-collection was copied from file
;; test/clojure/test_clojure/data_structures.clj in the Clojure
;; implementation test suite.

(defn is-same-collection [a b]
  (let [msg (format "(class a)=%s (class b)=%s a=%s b=%s"
                    (.getName (class a)) (.getName (class b)) a b)]
    (is (= (count a) (count b) (.size a) (.size b)) msg)
    (is (= a b) msg)
    (is (= b a) msg)
    (is (.equals ^Object a b) msg)
    (is (.equals ^Object b a) msg)
    (is (= (hash a) (hash b)) msg)
    (is (= (.hashCode ^Object a) (.hashCode ^Object b)) msg)))

(deftest set-collection-tests
  (let [empty-sets [ #{}
                    (set [])
                    (hash-set)
                    (sorted-set)
                    (sorted-set-by <)
                    (avl/sorted-set)
                    (avl/sorted-set-by <)
                    (fset/ordered-set)
                    (imap/int-set)
                    (imap/dense-int-set)
                    ]]
    (doseq [c1 empty-sets, c2 empty-sets]
      (is-same-collection c1 c2))))

(deftest map-collection-tests
  (let [empty-maps [
                    {}
                    (hash-map)
                    (array-map)
                    (zipmap [] [])
                    (sorted-map)
                    (sorted-map-by <)
                    (avl/sorted-map)
                    (avl/sorted-map-by <)
                    (fmap/ordered-map)
                    (imap/int-map)
                    (pmap/priority-map)
                    (umap/ordering-map [])
                    (umap/ordering-map [] <)
                    ]]
    (doseq [c1 empty-maps, c2 empty-maps]
      (is-same-collection c1 c2))))
