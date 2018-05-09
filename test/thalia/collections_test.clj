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

(defn is-same-set [a b should-be-java-equals?]
  (let [msg (format "(class a)=%s (class b)=%s a=%s b=%s"
                    (.getName (class a)) (.getName (class b)) a b)
        a-persistent? (set? a)
        b-persistent? (set? b)
        both-non-persistent? (and (not a-persistent?) (not b-persistent?))
        should-be-=? true]
    (is (= (count a) (count b) (.size a) (.size b)) msg)
    (is (= (= a b) should-be-=?) msg)
    (is (= (= b a) should-be-=?) msg)
    (is (= (.equals ^Object a b) should-be-java-equals?) msg)
    (is (= (.equals ^Object b a) should-be-java-equals?) msg)
    (is (= (.hashCode ^Object a) (.hashCode ^Object b)) msg)
    ;; At least while CLJ-1372 is unresolved, Clojure persistent
    ;; collections intentionally have different result for
    ;; clojure.core/hash than otherwise = non-persistent collections.
    (if (or (and a-persistent? b-persistent?)
            both-non-persistent?)
      (is (= (hash a) (hash b)) msg))))

(defn is-same-map [a b should-be-java-equals?]
  ;; These are really more like assertions.  I never want to call this
  ;; function unless both a and b are instances of java.util.Map of
  ;; some kind, whether Clojure persistent maps or not, or whether
  ;; Clojure defrecord instances or not.
  (is (= true (instance? java.util.Map a)))
  (is (= true (instance? java.util.Map b)))
  (let [msg (format "(class a)=%s (class b)=%s a=%s b=%s"
                    (.getName (class a)) (.getName (class b)) a b)
        a-persistent? (map? a)
        b-persistent? (map? b)
        at-least-one-persistent? (or a-persistent? b-persistent?)
        both-non-persistent? (and (not a-persistent?) (not b-persistent?))
        a-or-coerced-to-map (if (or (record? a) (not a-persistent?))
                              (into {} a)
                              a)
        b-or-coerced-to-map (if (or (record? b) (not b-persistent?))
                              (into {} b)
                              b)
        same-record-type? (and (record? a) (record? b)
                               (= (class a) (class b)))
        should-be-=? (or same-record-type?
                         (and (not (record? a)) (not (record? b))))]
    (is (= (count a) (count b) (.size a) (.size b)) msg)
    (when at-least-one-persistent?
      (is (= (= a b) should-be-=?) msg)
      (is (= (= b a) should-be-=?) msg))
    (is (= (.equals ^Object a b) should-be-java-equals?) msg)
    (is (= (.equals ^Object b a) should-be-java-equals?) msg)
    (is (= a-or-coerced-to-map b-or-coerced-to-map) msg)
    (is (= b-or-coerced-to-map a-or-coerced-to-map) msg)
    (is (= (.hashCode ^Object a) (.hashCode ^Object b)) msg)
    ;; At least while CLJ-1372 is unresolved, Clojure persistent
    ;; collections intentionally have different result for
    ;; clojure.core/hash than otherwise = non-persistent collections.
    (if (or same-record-type?
            (and a-persistent? (not (record? a))
                 b-persistent? (not (record? b)))
            both-non-persistent?)
      (is (= (hash a) (hash b)) msg))))

(deftest set-collection-tests
  (let [clj-sets [
                  #{}
                  (set [])
                  (hash-set)
                  (sorted-set)
                  (sorted-set-by <)
                  (avl/sorted-set)
                  (avl/sorted-set-by <)
                  (fset/ordered-set)
                  (imap/int-set)
                  (imap/dense-int-set)
                  ]
        non-clj-sets [
                      (java.util.HashSet.)
                      ]
        ]
    (doseq [c1 (concat clj-sets non-clj-sets),
            c2 (concat clj-sets non-clj-sets)]
      (is-same-set c1 c2 true))))

(defrecord EmptyRec1 [])
(defrecord EmptyRec2 [])
(defrecord OneFieldRec1 [k])
(defrecord OneFieldRec2 [k])

(deftest map-collection-tests
  ;; empty maps
  (let [clj-maps [
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
                  (->EmptyRec1)
                  (->EmptyRec2)
                  ]
        non-clj-maps [
                      (java.util.HashMap.)
                      ]
        ]
    (doseq [c1 (concat clj-maps non-clj-maps),
            c2 (concat clj-maps non-clj-maps)]
      (is-same-map c1 c2 true)))

  ;; maps with integers as keys, so no records
  (let [clj-maps [
                  {1 17}
                  {1 17N}
                  (hash-map 1 17)
                  (hash-map 1 17N)
                  (array-map 1 17)
                  (array-map 1 17N)
                  (zipmap [1] [17])
                  (zipmap [1] [17N])
                  (sorted-map 1 17)
                  (sorted-map 1 17N)
                  (sorted-map-by < 1 17)
                  (sorted-map-by < 1 17N)
                  (avl/sorted-map 1 17)
                  (avl/sorted-map 1 17N)
                  (avl/sorted-map-by < 1 17)
                  (avl/sorted-map-by < 1 17N)
                  (fmap/ordered-map 1 17)
                  (fmap/ordered-map 1 17N)
                  (imap/int-map 1 17)
                  (imap/int-map 1 17N)
                  (pmap/priority-map 1 17)
                  (pmap/priority-map 1 17N)
                  (assoc (umap/ordering-map []) 1 17)
                  (assoc (umap/ordering-map []) 1 17N)
                  (assoc (umap/ordering-map [] <) 1 17)
                  (assoc (umap/ordering-map [] <) 1 17N)
                  ;;(->EmptyRec1)
                  ;;(->EmptyRec2)
                  ]
        non-clj-maps [
                      (java.util.HashMap. {1 17})
                      (java.util.HashMap. {1 17N})
                      ]
        ]
    (doseq [c1 (concat clj-maps non-clj-maps),
            c2 (concat clj-maps non-clj-maps)]
      (is-same-map c1 c2
                   ;;(if (or (map? c1) (map? c2))
                   ;;  true
                   ;;  (.equals (get c1 1) (get c2 1)))
                   (.equals (get c1 1) (get c2 1))
                   )))

  ;; maps with keywords as keys, so records ok, but not int maps
  (let [clj-maps [
                  {:k 17}
                  (hash-map :k 17)
                  (array-map :k 17)
                  (zipmap [:k] [17])
                  (sorted-map :k 17)
                  (sorted-map-by compare :k 17)
                  (avl/sorted-map :k 17)
                  (avl/sorted-map-by compare :k 17)
                  (fmap/ordered-map :k 17)
                  ;;(imap/int-map :k 17)
                  (pmap/priority-map :k 17)
                  (assoc (umap/ordering-map []) :k 17)
                  (assoc (umap/ordering-map [] compare) :k 17)
                  (->OneFieldRec1 17)
                  (->OneFieldRec2 17)
                  ]
        non-clj-maps [
                      (java.util.HashMap. {:k 17})
                      ]
        ]
    (doseq [c1 (concat clj-maps non-clj-maps),
            c2 (concat clj-maps non-clj-maps)]
      (is-same-map c1 c2 true)))

  )
