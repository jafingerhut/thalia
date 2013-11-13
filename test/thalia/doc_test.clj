(ns thalia.doc-test
  (:use clojure.test
        thalia.doc)
  (:import (clojure.lang Compiler Compiler$CompilerException)))


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


(deftest test-==
  (is (not (= 2 2.0)))
  (is (== 2 2.0))
  (is (== 5 5N (float 5.0) (double 5.0) (biginteger 5)))
  (is (not (== 5 5.0M)))  ; this is likely a bug
  (is (not (== Double/NaN Double/NaN)))
  (is (thrown? ClassCastException 
               "java.lang.String cannot be cast to java.lang.Number"
               (== 2 "a"))))


(deftest test-apply
  (is (= (apply + [1 2]) 3))
  (is (= (apply + 1 2 [3 4 5 6]) 21))
  (is (= (apply + []) 0))
  (is (thrown? Compiler$CompilerException
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
  (is (thrown? ClassCastException
               #"java.lang.Long cannot be cast to java.lang.String"
               (sort [5 "a"])))
  (is (thrown? ClassCastException
               #"clojure.lang.Keyword cannot be cast to clojure.lang.Symbol"
               (sort [:foo 'bar])))
  (is (thrown? ClassCastException
               #"clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable"
               (sort [#{1 2} {2 4}])))
  (is (thrown? ClassCastException
               #"clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable"
               (sort [{:a 1 :b 3} {:c -2 :d 4}]))))


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


(deftest test-subs
  (is (= (subs "abcdef" 1 3)
         "bc"))
  (is (= (subs "abcdef" 1)
         "bcdef"))
  (is (= (subs "abcdef" 4 6)
         "ef"))
  (is (thrown? StringIndexOutOfBoundsException
               #"String index out of range: 7"
               (subs "abcdef" 4 7)))
  (is (= (subs "abcdef" 5/3 6.28)   ; args converted to ints
         "bcdef")))
