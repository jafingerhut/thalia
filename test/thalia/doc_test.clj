(ns thalia.doc-test
  (:use clojure.test
        thalia.doc)
  (:require [clojure.string :as str])
  (:import (clojure.lang Compiler Compiler$CompilerException)))


(def major-minor-version ((juxt :major :minor) *clojure-version*))

(def clojure=15 (= (compare major-minor-version [1 5]) 0))
(def clojure=16 (= (compare major-minor-version [1 6]) 0))
(def clojure>=16 (>= (compare major-minor-version [1 6]) 0))
(def clojure=17 (= (compare major-minor-version [1 7]) 0))

;; This value is useful to demonstrate some weirdness with
;; array/vector indexing.

(def long-truncates-to-int-0 (bit-shift-left 1 33))
(def bigint-truncates-to-int-0 (*' (bit-shift-left 1 33) (bit-shift-left 1 33)))

(deftest test-truncates-to-int-0
  (is (not (= 0 long-truncates-to-int-0)))
  (is (= 0 (unchecked-int long-truncates-to-int-0)))
  (is (not (= 0 bigint-truncates-to-int-0)))
  (is (= 0 (unchecked-int bigint-truncates-to-int-0))))


(defn info [x y]
  (let [v [x y]
        smap (reduce (fn [smap [k f]]
                       (assoc smap k (map f v)))
                     (sorted-map)
                     [[[0 :vals] identity]
                      [[1 :class] class]
                      [[2 :hash] hash]
                      [[3 :hashCode] #(.hashCode %)]])]
    (assoc smap
           [4 :equal] (= x y)
           [5 :vector-equal] (= [x] [y])
           [6 :pam-equal] (= {x 5} {y 5})
           [7 :phm-equal] (= (hash-map x 5) (hash-map y 5))
           [8 :hash-set-equal] (= (hash-set x) (hash-set y)))))


(deftest test-equality-and-hash
  (is (= (float 314.0) (double 314.0)))
  (is (= 3 3N))
  (is (not (= 2 2.0)))
  (is (= [0 1 2] (range 3)))
  (is (= [0 1 2] '(0 1 2)))
  (is (not (= [0 1] [0 1 2])))
  (is (not (= '(0 1 2) '(0 1 2.0))))
  (let [s1 (range 3)
        v1 [0 1 2]]
    (is (= s1 v1))
    (is (not (= (conj s1 4) (conj v1 4)))))
  (let [s1 (hash-set 1 2000 30000)
        s2 (sorted-set 30000 2000 1)]
    (is (= s1 s2))
    (is (= (hash s1) (hash s2))))
  (let [m1 {3 -7, 5 10, 15 20}
        m2 (sorted-map-by > 3 -7 5 10 15 20)]
    (is (= m1 m2))
    (is (= (hash m1) (hash m2))))
  (is (not (= ["a" "b" "c"] {0 "a" 1 "b" 2 "c"})))
  (let [s1 (with-meta #{1 2 3} {:key1 "set 1"})
        s2 (with-meta #{1 2 3} {:key1 "set 2 here"})]
    (is (= s1 s2))
    (is (not (= (meta s1) (meta s2)))))
  (is (not (= Double/NaN Double/NaN)))
  (is (not (== Double/NaN Double/NaN)))
  (is (.equals Double/NaN Double/NaN))
  (let [s1 #{1.0 2.0 Double/NaN}
        s2 #{Double/NaN 2.0 1.0}]
    (is (= (s1 1.0) 1.0))
    (is (nil? (s1 1.5)))
    (is (nil? (s1 Double/NaN)))
    (is (not (= s1 s2))))
  (is (= (float 1.0e9) (double 1.0e9)))
  (is (not (= (hash (float 1.0e9)) (hash (double 1.0e9)))))
  (is (= (int -1) (long -1) (bigint -1) (biginteger -1)))
  (cond
    clojure=15 (is (not (= (hash (bigint -1)) (hash (biginteger -1)))))
    clojure>=16 (is (= (hash (bigint -1)) (hash (biginteger -1)))))
  (let [l1 (java.util.ArrayList. [1 2 3])]
    (is (= (info l1 [1 2 3])
           {[0 :vals] [[1 2 3] [1 2 3]],
            [1 :class] [java.util.ArrayList clojure.lang.PersistentVector],
            [2 :hash] [30817 (if clojure>=16 736442005 30817)],
            [3 :hashCode] [30817 30817],
            [4 :equal] true,
            [5 :vector-equal] true,
            [6 :pam-equal] true,
            [7 :phm-equal] (if clojure>=16 false true),
            [8 :hash-set-equal] (if clojure>=16 false true)})))
  (let [s1 (java.util.HashSet. [1 2 3])]
    (is (= (info s1 #{1 2 3})
           {[0 :vals] [#{1 2 3} #{1 3 2}],
            [1 :class] [java.util.HashSet clojure.lang.PersistentHashSet],
            [2 :hash] [6 (if clojure>=16 439094965 6)],
            [3 :hashCode] [6 6],
            [4 :equal] true,
            [5 :vector-equal] true,
            [6 :pam-equal] true,
            [7 :phm-equal] (if clojure>=16 false true),
            [8 :hash-set-equal] (if clojure>=16 false true)})))
  (let [m1 (java.util.TreeMap. {:a 1 :b 2})]
    (is (= (info m1 {:a 1 :b 2})
           {[0 :vals] [{:a 1, :b 2} {:b 2, :a 1}],
            [1 :class] [java.util.TreeMap clojure.lang.PersistentArrayMap],
            [2 :hash] [2027821078  (if clojure>=16 161871944 2027821078)],
            [3 :hashCode] [2027821078 2027821078],
            [4 :equal] true,
            [5 :vector-equal] true,
            [6 :pam-equal] true,
            [7 :phm-equal] (if clojure>=16 false true),
            [8 :hash-set-equal] (if clojure>=16 false true)})))
  )


(deftest test-=
  (is (= 3 3N))
  (is (not (= 2 2.0)))
  (is (= [0 1 2] '(0 1 2)))
  (is (not (= '(0 1 2) '(0 1 2.0))))
  (is (not (= ["a" "b" "c"] {0 "a" 1 "b" 2 "c"})))
  (is (= (with-meta #{1 2 3} {:key1 1})
         (with-meta #{1 2 3} {:key1 2})))
  (is (not (= Double/NaN Double/NaN)))
  (let [s1 #{1.0 2.0 Double/NaN}]
    (is (contains? s1 1.0))
    (is (not (contains? s1 Double/NaN))))
  )


;; Assertions from docs for clojure.core/==

(deftest test-==
  (is (not (= 2 2.0)))
  (is (== 2 2.0))
  (is (== 5 5N (float 5.0) (double 5.0) (biginteger 5)))
  (cond
   clojure=15 (is (not (== 5 5.0M)))  ; this is bug CLJ-1118
   clojure>=16 (is (== 5 5.0M)))       ; fixed in Clojure 1.6.0
  (is (not (== Double/NaN Double/NaN)))
  (is (thrown-with-msg? ClassCastException
                        #"java.lang.String cannot be cast to java.lang.Number"
                        (== 2 "a"))))


(deftest test-apply
  (is (= (apply + [1 2]) 3))
  (is (= (apply + 1 2 [3 4 5 6]) 21))
  (is (= (apply + []) 0))
  (is (thrown-with-msg? Compiler$CompilerException
                        #"Can't take value of a macro"
                        (eval '(apply and [true false true])))))


(deftest test-compare
  (is (= (sort [22/7 2.71828 Double/NEGATIVE_INFINITY 1 55 3N])
         [Double/NEGATIVE_INFINITY 1 2.71828 3N 22/7 55]))
  (is (= (sorted-set "aardvark" "boo" "a" "Antelope" "bar")
         #{"Antelope" "a" "aardvark" "bar" "boo"}))
  (is (= (sorted-set 'user/foo 'clojure.core/pprint 'bar
                     'clojure.core/apply 'user/zz)
         #{'bar 'clojure.core/apply 'clojure.core/pprint 'user/foo 'user/zz}))
  (is (= (sorted-map :map-key 10, :amp [3 2 1],
                     :blammo "kaboom")
         {:amp [3 2 1], :blammo "kaboom", :map-key 10}))
  (is (= (sort [[1 2] [1 -5] [10000] [4 -1 20] [3 2 5]])
         '([10000] [1 -5] [1 2] [3 2 5] [4 -1 20])))
  (is (thrown-with-msg? ClassCastException
                        #"java.lang.Long cannot be cast to java.lang.String"
                        (sort [5 "a"])))
  (is (thrown-with-msg? ClassCastException
                        #"clojure.lang.Keyword cannot be cast to clojure.lang.Symbol"
                        (sort [:foo 'bar])))
  (is (thrown-with-msg? ClassCastException
                        #"clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable"
                        (sort [#{1 2} {2 4}])))
  (is (thrown-with-msg? ClassCastException
                        #"clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable"
                        (sort [{:a 1 :b 3} {:c -2 :d 4}]))))


(deftest test-contains?
    (is (= (contains? #{:a :b 5 nil} :b)
           true))
    (is (= (contains? #{:a :b 5 nil} 2)
           false))
    (is (= (contains? #{:a :b 5 nil} nil)
           true))
    (is (= (contains? #{:a :b 5} nil)
           false))
    (is (= (contains? {:a "a" nil "nil"} :a)
           true))
    (is (= (contains? {:a "a" nil "nil"} :b)
           false))
    (is (= (contains? {:a "a" nil "nil"} nil)
           true))
    (is (= (contains? {:a "a"} nil)
           false))
    (is (= (contains? "abcdef" 5)
           true))
    (is (= (contains? [:a :b :c] 1)
           true))
    (is (= (contains? (int-array [28 35 42 49]) 10)
           false))
    (is (= (contains? "abc" long-truncates-to-int-0)
           true))
    (is (= (contains? "abc" bigint-truncates-to-int-0)
           true))
    (is (= (contains? "abc" -0.99)
           true))
    (is (= (contains? [:a :b :c] long-truncates-to-int-0)
           true))
    (is (= (contains? [:a :b :c] bigint-truncates-to-int-0)
           true))
    (is (= (contains? [:a :b :c] 0.5)
           false)))


(deftest test-get
  (is (= (get #{"a" 'b 5 nil} 'b)
         'b))
  (is (= (get #{"a" 'b 5 nil} 2)
         nil))
  (is (= (get #{"a" 'b 5 nil} nil)
         nil))
  (is (= (get #{"a" 'b 5} nil)
         nil))
  (is (= (get #{"a" 'b 5} nil :not-found)
         :not-found))
  (is (= (get #{"a" 'b 5 nil} nil :not-found)
         nil))
  (is (= (get {"a" 1, "b" nil} "b")
         nil))
  (is (= (get {"a" 1, "b" nil} "b" :not-found)
         nil))
  (is (= (get {"a" 1, "b" nil} "c" :not-found)
         :not-found))
  (is (= (get [:a :b :c] 50)
         nil))
  (is (= (get \t "foo")
         nil))
  (let [vec1 [:a :b :c]]
    (is (= (vec1 2)
           :c))
    (is (thrown? IndexOutOfBoundsException
                 (vec1 3)))
    (is (thrown-with-msg? clojure.lang.ArityException
                          #"Wrong number of args \(2\) passed to: (clojure\.lang\.)?PersistentVector"
                          (vec1 3 :not-found))))
  (let [map1 {:a 1 :b 2}]
    (is (= (map1 :a)
           1))
    (is (= (map1 :c)
           nil))
    (is (= (map1 :c :not-found)
           :not-found)))
  (is (= (get [:a :b :c] 1.7)
         nil))
  (is (= (get (int-array [5 6 7]) -0.99)
         5))
  (is (= (get "abc" long-truncates-to-int-0)
         \a))
  (is (= (get "abc" bigint-truncates-to-int-0)
         \a))
  (is (= (get [:a :b :c] long-truncates-to-int-0)
         :a))
  (is (= (get [:a :b :c] bigint-truncates-to-int-0)
         :a))

  ;; Try all combinations of a data type from list 1, and an index
  ;; from list 2, both with and without a not-found value, and with
  ;; and without get.

  ;; list 1: vector, map, string, Java array, character
  ;; list 2: :a -0.99 0 1/2 (count v) long-truncates-to-int-0
  (doseq [[coll-kind coll elem0] [ [:vector [:x :y :z] :x]
                                   [:map {0 "xyzzy", 2/3 :foo} "xyzzy"]
                                   [:array "abc" \a]
                                   [:array (int-array [5 10 15]) 5]
                                   [:char \t nil] ]]
    (doseq [[idx idx-kind] [ [:a :not-a-number]
                             [-0.99 :non-integer-which-rounds-to-0]
                             [0 :integer-0]
                             [1/2 :non-integer-which-rounds-to-0]
                             [3 :integer-out-of-range]
                             [long-truncates-to-int-0
                              :integer-out-of-range-intValue-converts-to-0]
                             [bigint-truncates-to-int-0
                              :integer-out-of-range-intValue-converts-to-0] ]]
      (doseq [use-get? [false true]]
        (doseq [not-found-arg? [false true]]
          (let [test-desc-str (str "coll-kind: " coll-kind
                                   "  idx-kind: " idx-kind
                                   "  use-get? " use-get?
                                   "  not-found-arg? " not-found-arg?)
                expected-ret-val-if-not-found (if not-found-arg?
                                                :not-found)
                expected-exception-type
                (cond
                 use-get? nil
                 (not (contains? #{:vector :map} coll-kind)) ClassCastException
                 (= coll-kind :map) nil
                 not-found-arg? clojure.lang.ArityException
                 (#{:not-a-number :non-integer-which-rounds-to-0} idx-kind) IllegalArgumentException
                 (= idx-kind :integer-out-of-range) IndexOutOfBoundsException
                 :else nil)
                expected-ret-val
                (case coll-kind
                  :char expected-ret-val-if-not-found
                  :map (if (= idx-kind :integer-0)
                         elem0
                         expected-ret-val-if-not-found)
                  (:vector :array)
                  (case idx-kind
                    :not-a-number expected-ret-val-if-not-found
                    :non-integer-which-rounds-to-0 (case coll-kind
                                                     :vector expected-ret-val-if-not-found
                                                     :array elem0)
                    :integer-out-of-range expected-ret-val-if-not-found
                    :integer-out-of-range-intValue-converts-to-0 elem0
                    :integer-0 elem0))]
            (if (nil? expected-exception-type)
              (is (= (case [use-get? not-found-arg?]
                       [true true] (get coll idx :not-found)
                       [true false] (get coll idx)
                       [false true] (coll idx :not-found)
                       [false false] (coll idx))
                     expected-ret-val)
                  test-desc-str)
              (is (thrown? Exception
                           (case [use-get? not-found-arg?]
                             [true true] (get coll idx :not-found)
                             [true false] (get coll idx)
                             [false true] (coll idx :not-found)
                             [false false] (coll idx)))
                  test-desc-str))))))))


(deftest test-get-in
    (let [str-matrix [["abc" "def" "ghi"]
                      ["jkl" "mno" "pqr"]
                      ["stu" "vwx" "yz"]]]
      (is (= (get-in str-matrix [2])
             ["stu" "vwx" "yz"]))
      (is (= (get-in str-matrix [2 0])
             "stu"))
      (is (= (get-in str-matrix [2 0 1])
             \t))
      (is (= (get-in str-matrix [2 0 1 5])
             nil))
      (is (= (get-in str-matrix [2 0 1 5] :not-found)
             :not-found))
      (is (= (get-in str-matrix [2 :x])
             nil))
      (is (= (get-in str-matrix [2 :x "foo"])
             nil)))
    (let [benchmark-result {:test "bench1", :run-times [5.2 5.7 4.9],
                            :platform {:os "Linux",
                                       :distribution "Ubuntu",
                                       :version-parts [12 4 3]}}]
      (is (= (get-in benchmark-result [:run-times 2])
             4.9))
      (is (= (get-in benchmark-result [:platform :distribution])
             "Ubuntu"))
      (is (= (get-in benchmark-result [:platform :version-parts 2])
             3))))


;; Assertions from docs for clojure.core/hash

(deftest test-hash
  (let [x 8589934588]
    (is (= (bigint x) (biginteger x)))
    (cond
     ;; This behavior was changed in Clojure 1.6.0.  See CLJ-1036
     clojure=15 (is (not (= (hash (bigint x)) (hash (biginteger x)))))
     clojure>=16 (is (= (hash (bigint x)) (hash (biginteger x)))))
    (let [s1 (hash-set (bigint x))
          s2 (hash-set (biginteger x))]
      (is (= (first s1) (first s2)))
      (cond
       clojure=15 (is (not (= s1 s2)))
       clojure>=16 (is (= s1 s2)))))
  (is (= (float 1.0e9) (double 1.0e9)))
  (is (not (= (hash (float 1.0e9)) (hash (double 1.0e9))))))


(deftest test-identical?
    (let [my-contains? (fn [coll key]
                         (let [not-found-sentinel (Object.)]
                           (not (identical? not-found-sentinel
                                            (get coll key not-found-sentinel)))))]
      (is (= (my-contains? {:a nil, nil nil} :a)
             true))
      (is (= (my-contains? {:a nil, nil nil} nil)
             true))
      (is (= (my-contains? {:a nil, nil nil} :b)
             false)))
    (is (= (identical? 500 500)
           false))
    (is (= (identical? 5.0 5.0)
           false)))


(deftest test-range
  (is (= (range 11)
         [0 1 2 3 4 5 6 7 8 9 10]))
  (is (= (range 5 11)
         [5 6 7 8 9 10]))
  (is (= (range 5 11 2)
         [5 7 9]))
  (is (= (range 11 0 -1)
         [11 10 9 8 7 6 5 4 3 2 1]))
  (is (= (range 11 -1 -1)
         [11 10 9 8 7 6 5 4 3 2 1 0]))
  (is (= (count (range 0 100 1))
         100))
  (is (= (last (range 0 100 1))
         99))
  (is (= (count (range 0.0 10.0 0.1))
         101))
  (is (= (last (range 0.0 10.0 0.1))
         9.99999999999998)))


(deftest test-re-find
  (is (= (re-find #"\d+" "abc123def")
         "123"))
  (is (= (re-find #"\d+" "abcdef")
         nil))
  (is (= (re-find #"(?:\d+)" "abc123def")
         "123"))
  (let [line " RX packets:1871 errors:5 dropped:48 overruns:9"]
    (is (= (re-find #"(\S+):(\d+)" line)
           ["packets:1871" "packets" "1871"]))
    (is (= (re-find #"(\S+:(\d+)) \S+:\d+" line)
           ["packets:1871 errors:5" "packets:1871" "1871"])))
  (is (= (re-find #"(\S+):(\d+)" ":2 numbers but not 1 word-and-colon: before")
         nil))
  (is (= (re-find #"(\D+)|(\d+)" "word then number 57")
         ["word then number " "word then number " nil]))
  (is (= (re-find #"(\D+)|(\d+)" "57 number then word")
         ["57" nil "57"]))
  (is (= (re-find #"(\d*)(\S)\S+" "lots o' digits 123456789")
         ["lots" "" "l"])))


(deftest test-re-matches
  (is (= (re-find #"\d+" "abc123def")
         "123"))
  (is (= (re-matches #"\d+" "abc123def")
         nil))
  (is (= (re-matches #"\d+" "123")
         "123")))


(deftest test-re-seq
  (is (= (re-seq #"\d" "Mac OS X 10.6.8")
         ["1" "0" "6" "8"]))
  (is (= (re-seq #"\d+" "Mac OS X 10.6.8")
         ["10" "6" "8"]))
  (is (= (re-seq #"ZZ" "Mac OS X 10.6.8")
         nil))
  (is (= (re-seq #"\S+:\d+" " RX pkts:18 err:5 drop:48")
         ["pkts:18" "err:5" "drop:48"]))
  (is (= (re-seq #"(\S+):(\d+)" " RX pkts:18 err:5 drop:48")
         [["pkts:18" "pkts" "18"] ["err:5" "err" "5"] ["drop:48" "drop" "48"]])))


(deftest test-sort
  (is (= (sort [3 -7 10 8 5.3 9/5 -7.1])
         '(-7.1 -7 9/5 3 5.3 8 10)))
  (is (= (sort #(compare %2 %1) '(apple banana aardvark zebra camel))
         '(zebra camel banana apple aardvark)))
  (let [x (to-array [32 9 11])]
    (is (= (seq x)
           '(32 9 11)))
    (is (= (sort x)
           '(9 11 32)))
    (is (= (seq x)
           '(9 11 32))))
  (let [x (to-array [32 9 11])]
    (is (= (sort (aclone x))
           '(9 11 32)))
    (is (= (seq x)
           '(32 9 11)))))


(deftest test-sort-by
  (is (= (sort-by count ["lummox" "antidisestablishmentarianism" "a"])
         '("a" "lummox" "antidisestablishmentarianism")))
  (is (= (sort-by first > [[8.67 -5] [5 1] [-22/7 3.0] [5 0]])
         '([8.67 -5] [5 1] [5 0] [-22/7 3.0]))))


(deftest test-sorted-map
  (is (= (sorted-map :d 0 :b -5 :a 1)
         {:a 1, :b -5, :d 0}))
  (is (= (assoc (sorted-map :d 0 :b -5 :a 1) :c 57)
         {:a 1, :b -5, :c 57, :d 0}))
  (let [births (sorted-map -428 "Plato"      -384 "Aristotle" -469 "Socrates"
                           -320 "Euclid"     -310 "Aristarchus" 90 "Ptolemy"
                           -570 "Pythagoras" -624 "Thales"    -410 "Eudoxus")]
    (is (= (first births)
           [-624 "Thales"]))
    (is (= (take 4 births)
           [[-624 "Thales"] [-570 "Pythagoras"] [-469 "Socrates"] [-428 "Plato"]]))
    (is (= (keys births)
           [-624 -570 -469 -428 -410 -384 -320 -310 90]))
    (is (= (vals births)
           ["Thales" "Pythagoras" "Socrates" "Plato" "Eudoxus" "Aristotle" "Euclid" "Aristarchus" "Ptolemy"]))
    (is (= (subseq births > -400)
           [[-384 "Aristotle"] [-320 "Euclid"] [-310 "Aristarchus"] [90 "Ptolemy"]]))
    (is (= (subseq births > -400 < -100)
           [[-384 "Aristotle"] [-320 "Euclid"] [-310 "Aristarchus"]]))
    (is (= (rsubseq births > -400 < -100)
           [[-310 "Aristarchus"] [-320 "Euclid"] [-384 "Aristotle"]])))

  (let [m1 (hash-map 1.0 "floatone" 1 "intone" 1.0M "bigdecone"
                     1.5M "bigdec1.5" 3/2 "ratio1.5")
        m2 (sorted-map 1.0 "floatone" 1 "intone" 1.0M "bigdecone"
                       1.5M "bigdec1.5" 3/2 "ratio1.5")]
    (is (= m1
           {1.0 "floatone", 1 "intone", 3/2 "ratio1.5", 1.5M "bigdec1.5",
            1.0M "bigdecone"}))
    (is (= (dissoc m1 1 3/2)
           {1.0 "floatone", 1.5M "bigdec1.5", 1.0M "bigdecone"}))
    (is (= m2
           {1.0 "bigdecone", 1.5M "ratio1.5"}))
    (is (= (dissoc m2 1 3/2)
           {}))

    (is (= (m1 1)
           "intone"))
    (is (= (m1 "a")
           nil))

    (is (= (m2 1)
           "bigdecone"))
    (is (thrown-with-msg? ClassCastException
                          #"java.lang.Double cannot be cast to java.lang.String"
                          (m2 "a")))))


(defn case-insensitive-cmp [s1 s2]
  (compare (str/lower-case s1) (str/lower-case s2)))


(deftest test-sorted-map-by
  (is (= (seq (sorted-map-by > 2 "two" 3 "three" 11 "eleven" 5 "five" 7 "seven"))
         [[11 "eleven"] [7 "seven"] [5 "five"] [3 "three"] [2 "two"]]))
  (is (= (seq (sorted-map-by #(compare %2 %1)
                             "aardvark" "Orycteropus afer"
                             "lion" "Panthera leo"
                             "platypus" "Ornithorhynchus anatinus"))
         [["platypus" "Ornithorhynchus anatinus"]
          ["lion" "Panthera leo"]
          ["aardvark" "Orycteropus afer"]]))
  (is (= (seq (sorted-map-by case-insensitive-cmp
                             "lion" "normal lion"
                             "Lion" "Orycteropus afer"))
         [["lion" "Orycteropus afer"]])))


(deftest test-sorted-set
  (is (= (seq (sorted-set 4 2 1))
         [1 2 4]))
  (is (= (seq (conj (sorted-set 4 2 1) 3))
         [1 2 3 4]))
  (let [ss (apply sorted-set (range 100 0 -5))]
    (is (= (seq ss)
           [5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100]))
    (is (= (first ss)
           5))
    (is (= (last ss)
           100))
    (is (= (subseq ss >= 80)
           [80 85 90 95 100]))
    (is (= (subseq ss > 20 < 60)
           [25 30 35 40 45 50 55]))))


(deftest test-sorted-set-by
  (is (= (seq (sorted-set-by compare "Food" "good" "air" "My" "AiR" "My"))
         ["AiR" "Food" "My" "air" "good"]))
  (is (= (seq (sorted-set-by case-insensitive-cmp
                             "Food" "good" "air" "My" "AiR" "My"))
         ["air" "Food" "good" "My"])))


(deftest test-subs
  (is (= (subs "abcdef" 1 3)
         "bc"))
  (is (= (subs "abcdef" 1)
         "bcdef"))
  (is (= (subs "abcdef" 4 6)
         "ef"))
  (is (thrown-with-msg? StringIndexOutOfBoundsException
                        #"String index out of range: 7"
                        (subs "abcdef" 4 7)))
  (is (= (subs "abcdef" 5/3 6.28)   ; args converted to ints
         "bcdef")))

;(deftest test-=
;  (is (= 5 5)))
