(ns thalia.collections-test
  (:use clojure.test)
  (:require
   [clojure.data.avl :as avl]
   [flatland.ordered.set :as fset]
   [flatland.ordered.map :as fmap]
   [flatland.useful.map :as umap]
   [clojure.data.int-map :as imap]
   [clojure.data.priority-map :as pmap]
   [clojure.core.rrb-vector :as rrbv]
   ))


;; Test clojure.core/= and Java .equals between many pairs of Java and
;; Clojure collections, including those in some 3rd party Clojure
;; libraries.

;; Functions is-same-sequential, is-same-set, and is-same-map started
;; by being copied from function is-same-collection in file
;; test/clojure/test_clojure/data_structures.clj in the Clojure
;; implementation test suite.

;; TBD: This only approximately characterizes Clojure 1.9.0 behavior,
;; since there are still some test failures that this code is not
;; predicting the results correctly, I think mostly around
;; clojure.core.Vec and clojure.core.VecSeq objects, created from
;; clojure.core/vector-of.

(defn is-same-sequential [a-info b-info should-be-java-equals?]
  (let [a (:val a-info)
        b (:val b-info)
        a-persistent? (sequential? a)
        b-persistent? (sequential? b)
        both-non-persistent? (and (not a-persistent?) (not b-persistent?))
        ;; CLJ-1059 https://dev.clojure.org/jira/browse/CLJ-1059
        ;; = and .equals return false when comparing otherwise equal
        ;; PersistentQueue and non-Clojure java.util.List instances.
;;        clj-1059-case? (or (and (not a-persistent?)
;;                                (instance? clojure.lang.PersistentQueue b))
;;                           (and (instance? clojure.lang.PersistentQueue a)
;;                                (not b-persistent?)))
        clj-1059-case? false
        should-be-=? (if clj-1059-case?
                       false
                       true)
        a-vec-or-vecseq? (or (instance? clojure.core.Vec a)
                             (instance? clojure.core.VecSeq a))
        b-vec-or-vecseq? (or (instance? clojure.core.Vec b)
                             (instance? clojure.core.VecSeq b))
        a-size (try
                 (.size a)
                 (catch IllegalArgumentException e
                     (count a)))
        b-size (try
                 (.size b)
                 (catch IllegalArgumentException e
                     (count b)))
        ;;should-be-equals? (and should-be-java-equals?
        ;;                       (not clj-1059-case?)
        ;;                       (not a-vecseq?))
        should-be-equals? (cond
                            (= false should-be-java-equals?) false
                            a-vec-or-vecseq? b-vec-or-vecseq?
                            clj-1059-case? false
                            :else true)
        msg (format "(class a)=%s (class b)=%s a=%s b=%s (:expr a-info)=%s (:expr b-info)=%s a-vec-or-vecseq?=%s b-vec-or-vecseq?=%s"
                    (.getName (class a)) (.getName (class b))
                    a b
                    (:expr a-info) (:expr b-info)
                    a-vec-or-vecseq? b-vec-or-vecseq?)
        ]
    (is (= (reversible? a) (:reversible? a-info)) msg)
    (is (= (reversible? b) (:reversible? b-info)) msg)
    (is (= (count a) (count b) a-size b-size) msg)
    (is (= (= a b) should-be-=?) msg)
    ;;(is (= (= b a) should-be-=?) msg)
    (when-not (:java-equals-bug-clj-1364? a-info)
      (is (= (.equals ^Object a b) should-be-equals?) msg))
    ;;(is (= (.equals ^Object b a) should-be-equals?) msg)
    (when (not (or a-vec-or-vecseq? b-vec-or-vecseq?))
      (is (= (.hashCode ^Object a) (.hashCode ^Object b)) msg))
    ;; At least while CLJ-1372 is unresolved, Clojure persistent
    ;; collections intentionally have different result for
    ;; clojure.core/hash than otherwise = non-persistent collections.
    (let [do-hash-compare (cond
                            (or a-vec-or-vecseq? b-vec-or-vecseq?) (and a-vec-or-vecseq? b-vec-or-vecseq?)
                            (and a-persistent? b-persistent?) true
                            both-non-persistent? true
                            :else false)]
      (if do-hash-compare
        (is (= (hash a) (hash b)) msg)))))

;; TBD: I believe some test failures are occurring because of
;; CLJ-1364, but if so, which ones?  Can I add some straightforward
;; conditions to is-same-sequential to cover those cases?
;; https://dev.clojure.org/jira/browse/CLJ-1364

(deftest sequential-collection-tests
  (let [sequentials
        [
         ;; TBD: Add core.rrb-vector vectors and some variations on
         ;; them (e.g. subvec, rseq, concatenated, spliced.
         {:val [1 2 3]
          :expr "[1 2 3]" :reversible? true}
         {:val (rseq [3 2 1])
          :expr "(rseq [3 2 1])" :reversible? false}
         {:val (seq [1 2 3])
          :expr "(seq [1 2 3])" :reversible? false}
         {:val '(1 2 3)
          :expr "'(1 2 3)" :reversible? false}
         {:val (conj clojure.lang.PersistentQueue/EMPTY 1 2 3)
          :expr "(conj clojure.lang.PersistentQueue/EMPTY 1 2 3)"
          :reversible? false}
         {:val (subvec [0 1 2 3 4] 1 4)
          :expr "(subvec [0 1 2 3 4] 1 4)" :reversible? true}
         {:val (rseq (subvec [4 3 2 1 0] 1 4))
          :expr "(rseq (subvec [4 3 2 1 0] 1 4))" :reversible? false}
         {:val (java.util.ArrayList. [1 2 3])
          :expr "(java.util.ArrayList. [1 2 3])" :reversible? false}
         {:val (vector-of :long 1 2 3)
          :expr "(vector-of :long 1 2 3)" :reversible? true
          :java-equals-bug-clj-1364? true}
         {:val (rseq (vector-of :long 3 2 1))
          :expr "(rseq (vector-of :long 3 2 1))" :reversible? false}
         {:val (subvec (vector-of :long 0 1 2 3 4) 1 4)
          :expr "(subvec (vector-of :long 0 1 2 3 4) 1 4)" :reversible? true}
         {:val (rseq (subvec (vector-of :long 4 3 2 1 0) 1 4))
          :expr "(rseq (subvec (vector-of :long 4 3 2 1 0) 1 4))"
          :reversible? false}
         {:val (rrbv/vector 1 2 3)
          :expr "(rrbv/vector 1 2 3)" :reversible? true}
         {:val (rseq (rrbv/vector 3 2 1))
          :expr "(rseq (rrbv/vector 3 2 1))" :reversible? false}
         {:val (rrbv/subvec (rrbv/vector 0 1 2 3 4) 1 4)
          :expr "(rrbv/subvec (rrbv/vector 0 1 2 3 4) 1 4)" :reversible? true}
         {:val (rseq (rrbv/subvec (rrbv/vector 4 3 2 1 0) 1 4))
          :expr "(rseq (rrbv/subvec (rrbv/vector 4 3 2 1 0) 1 4))" :reversible? false}
         ]
        seqs-of-sequentials (map (fn [info]
                                   {:val (seq (:val info))
                                    :expr (str "(seq " (:expr info) ")")
                                    :reversible? false})
                                 sequentials)
        all (concat sequentials seqs-of-sequentials)
        ]
    (doseq [idx1 (range (count all)),
            idx2 (range (count all))]
      (is-same-sequential (nth all idx1) (nth all idx2) true))))


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
