(ns thalia.core-test
  (:use clojure.test
        thalia.core))

;;(def all-number-types [(Byte. -1) (Byte. 

(deftest number-conversions
  (doseq [[coerce-fn construct-fn oob-possible min-val max-val other-val]
          [ [byte #(Byte. (format "%d" (biginteger %)))
             :oob Byte/MIN_VALUE Byte/MAX_VALUE 5]
            [short #(Short. (format "%d" (biginteger %)))
             :oob Short/MIN_VALUE Short/MAX_VALUE -28]
            [int #(Integer. (format "%d" (biginteger %)))
             :oob Integer/MIN_VALUE Integer/MAX_VALUE 42]
            [long #(Long. (format "%d" (biginteger %)))
             :oob (bigint Long/MIN_VALUE) (bigint Long/MAX_VALUE) -271828]
            [biginteger #(BigInteger. (format "%d" (biginteger %)))
             :no-oob (* 2N Long/MIN_VALUE) (* 2N Long/MAX_VALUE) 314159265]
            [bigint #(clojure.lang.BigInt/fromBigInteger (BigInteger. (format "%d" (biginteger %))))
             :no-oob (* 2N Long/MIN_VALUE) (* 2N Long/MAX_VALUE) 314159265]
            ]]
    (when (= oob-possible :oob)
      (is (thrown? IllegalArgumentException (coerce-fn (dec min-val))))
      (is (thrown? IllegalArgumentException (coerce-fn (inc max-val))))
      (is (thrown? IllegalArgumentException (construct-fn (dec min-val))))
      (is (thrown? IllegalArgumentException (construct-fn (inc max-val)))))
    (is (== min-val (coerce-fn min-val)))
    (is (== max-val (coerce-fn max-val)))
    (is (== other-val (coerce-fn other-val)))
    (is (== min-val (construct-fn min-val)))
    (is (== max-val (construct-fn max-val)))
    (is (== other-val (construct-fn other-val)))))

;  (is (thrown? Exception (byte -129)))
;  (is (thrown? Exception (Byte. "-129")))
;  (is (thrown? Exception (byte 128)))
;  (is (thrown? Exception (Byte. "128")))
;  (is (== -128 (byte -128)))
;  (is (== -128 (Byte. "-128")))
;  (is (== -128 (byte -128)))
;  (is (== -128 (Byte. "-128")))
