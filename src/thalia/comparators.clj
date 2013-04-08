(ns thalia.comparators)

(set! *warn-on-reflection* true)


(defn multicmp [& key-specs]
  (fn [a b]
    (or (first (remove zero? (map #(let [[keyfn order] %]
                                     (if (= order :desc)
                                       (compare (keyfn b) (keyfn a))
                                       (compare (keyfn a) (keyfn b))))
                                  key-specs)))
        0)))


;; TBD: Write doc string for multicmp2 with examples of use.
;; Allow optional comparison function cmp for the key, defaulting to
;; clojure.core/compare if none is given.  Allow optional order :asc
;; or :desc, defaulting to :asc.  comparison function can be left out
;; if you want clojure.core/compare, by specifing :asc or :desc as
;; second item of vector.

(defn multicmp2 [& key-specs]
  ;; Loop through the key-specs and create the individual comparison
  ;; functions for each one only once.
  (let [cmp-fns (map #(let [[keyfn x y] (if (vector? %)
                                          %
                                          [%])
                            [^clojure.lang.AFunction cmp order]
                            (if y
                              [x y]
                              (if x
                                (if (#{:asc :desc} x)
                                  [compare x]
                                  [x :asc])
                                [compare :asc]))]
                        (if (= order :desc)
                          (fn [a b]
                            (. cmp (compare (keyfn b) (keyfn a))))
                          (fn [a b]
                            (. cmp (compare (keyfn a) (keyfn b))))))
                     key-specs)]
    (fn [a b]
      (or (first (remove zero? (map #(% a b) cmp-fns)))
          0))))


;; comparison-class throws exceptions for many types that would be
;; useful to include, e.g. Java arrays, and Clojure records, sets, and
;; maps.  We'll save such enhancements for a fancier version.

(defn comparison-class [x]
  (cond (nil? x) ""
        ;; Lump all numbers together since Clojure's compare can
        ;; compare them all to each other sensibly.
        (number? x) "java.lang.Number"
        ;; sequential? includes lists, conses, vectors, and seqs of
	;; just about any collection, although it is recommended not
	;; to use this to compare seqs of unordered collections like
	;; sets or maps (vectors should be OK).  This should be
	;; everything we would want to compare using cmp-seq-lexi
	;; below.  TBD: Does it leave anything out?  Include anything
	;; it should not?
        (sequential? x) "clojure.lang.Sequential"
        ;; Comparable includes Boolean, Character, String, Clojure
        ;; refs, and many others.
        (instance? Comparable x) (.getName (class x))
        :else (throw
               (ex-info (format "cc-cmp does not implement comparison of values with class %s"
                                (.getName (class x)))
                        {:value x}))))

(defn cmp-seq-lexi
  "Compare sequences x and y in lexicographic order, using comparator
cmpf to compare elements from x and y to each other.  As a comparator
function, cmp-seq-lexi returns a negative, 0, or positive integer if x
is less than, equal to, or greater than y."
  [cmpf x y]
  (loop [x x
         y y]
    (if (seq x)
      (if (seq y)
        (let [c (cmpf (first x) (first y))]
          (if (zero? c)
            (recur (rest x) (rest y))
            c))
        ;; else we reached end of y first, so x > y
        1)
      (if (seq y)
        ;; we reached end of x first, so x < y
        -1
        ;; Sequences contain same elements.  x = y
        0))))

;; The same result can be obtained by calling cmp-seq-lexi on two
;; vectors, but this one should allocate less memory comparing
;; vectors.
(defn cmp-vec-lexi
  "Compare vectors x and y in lexicographic order, using comparator
cmpf to compare elements from x and y to each other.  As a comparator
function, cmp-seq-lexi returns a negative, 0, or positive integer if x
is less than, equal to, or greater than y.

It is possible to use cmp-seq-lexi on vectors, too, but this function
should be faster and allocate less memory than cmp-seq-lexi, but only
works for vectors."
  [cmpf x y]
  (let [x-len (count x)
        y-len (count y)
        len (min x-len y-len)]
    (loop [i 0]
      (if (== i len)
        ;; If all elements 0..(len-1) are same, shorter vector comes
        ;; first.
        (compare x-len y-len) 
        (let [c (cmpf (x i) (y i))]
          (if (zero? c)
            (recur (inc i))
            c))))))

(defn cc-cmp
  "cc-cmp compares two values x and y.  As a comparator, it returns a
negative, 0, or positive integer if x is less than, equal to, or
greater than y.

cc-cmp can compare values of many types, including numbers, strings,
symbols, keywords, vectors, lists, sequences, anything that implements
the Java Comparable interface (including booleans, characters, File,
URL, UUID, Clojure refs), and nil.

Unlike the function compare, it sorts vectors in lexicographic order.
It also sorts lists and sequences, on which compare throws exceptions,
in lexicographic order.

Also unlike compare, cc-cmp can compare values of different types to
each other without throwing an exception, if it can compare them at
all.  Values of different types are sorted relative to each other by a
string that is the name of their class.  Note that all numbers use the
string \"java.lang.Number\" so that numbers are sorted together,
rather than separated out by type, and for a similar reason all
vectors, lists, and sequences use the string
\"clojure.lang.Sequential\".

cc-cmp throws exceptions if given a Clojure set or map, or any other
type of value not mentioned above."
  [x y]
  (let [x-cls (comparison-class x)
        y-cls (comparison-class y)
        c (compare x-cls y-cls)]
    (cond (not= c 0) c  ; different classes

          ;; Make a special check for two vectors, since cmp-vec-lexi
          ;; should allocate less memory comparing them than
          ;; cmp-seq-lexi.  Both here and for comparing sequences, we
          ;; must use cc-cmp recursively on the elements, because if
          ;; we used compare we would lose the ability to compare
          ;; elements with different types.
          (and (vector? x) (vector? y)) (cmp-vec-lexi cc-cmp x y)

          ;; This will compare any two sequences, if they are not both
          ;; vectors, e.g. a vector and a list will be compared here.
          (= x-cls "clojure.lang.Sequential")
          (cmp-seq-lexi cc-cmp x y)
          
          :else (compare x y))))

(comment

(use 'thalia.comparators)

(def seq-vals [[5 4 3]
               '(5 4)
               (seq [5])
               (cons 6 '(1 2 3))])

(def seq-vals2 [[5 4 3]
                '(5 4)
                (seq [5])
                [6 1 2 3]
                (cons 6 '(1 2 3))])

(def variety-vals (concat [true false nil Double/MAX_VALUE 10 Integer/MIN_VALUE
                           :a "b" 'c (ref 5)]
                          seq-vals2))

(def some-vals variety-vals)
(def some-vals seq-vals)
(def some-vals seq-vals2)

(doseq [a some-vals
      b some-vals]
  (printf "a='%s' b='%s'" a b)
  (flush)
  (printf " cc-cmp=%s\n" (cc-cmp a b)))

(sort cc-cmp some-vals)

)
