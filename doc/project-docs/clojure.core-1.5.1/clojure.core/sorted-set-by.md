Be cautious about comparison functions that only compare part of the
objects:

```clojure
    user> (defn second-< [x y]
            (< (second x) (second y)))
    user> (sorted-set-by second-< [:a 1] [:b 1] [:c 1])
    #{[:a 1]}
```
 
Where did the other elements go?
 
Replacing `<` with `<=` might look like a fix, but doesn't work,
either:

```clojure
    user> (defn second-<= [x y]
            (<= (second x) (second y)))
    user> (def s2 (sorted-set-by second-<= [:a 1] [:b 1] [:c 1]))
    #'user/s2
    user> s2
    #{[:c 1] [:b 1] [:a 1]}
```

So far, so good, but set membership tests can't find the elements.

```clojure
    user> (contains? s2 [:b 1])
    false
    user> (s2 [:c 1])
    nil
```
 
Below is one way to write a good comparison function.  When the two
objects are equal in the parts we care about, use the tie-breaker
`compare` on the whole values to give them a consistent order that is
only equal if the entire values are equal.

```clojure
    user> (defn second-<-with-tie-break [x y]
            (let [c (compare (second x) (second y))]
              (if (not= c 0)
                c
                ;; Otherwise we don't care as long as ties are broken
                ;; consistently.
                (compare x y))))
    user> (def s3 (sorted-set-by second-<-with-tie-break [:a 1] [:b 1] [:c 1]))
    #'user/s3
    user> s3
    #{[:a 1] [:b 1] [:c 1]}
    user> (contains? s3 [:b 1])
    true
    user> (s3 [:c 1])
    [:c 1]
```

All good now!

Another correct approach is to rely on Clojure's way of comparing
vectors that have equal lengths.  These are compared
lexicographically, meaning that it is similar to alphabetical order of
words.  The first element of the two vectors are compared first.  If
they are not equal, their order determines the order of the vectors.
If the first elements are equal, then continue with comparing the
second elements, and so on if needed.

Comparison function `good-second-<` below returns the same value that
`second-<-with-tie-break` above does, but is shorter.

```clojure
    user (defn good-second-< [x y]
           (compare [(second x) x] [(second y) y]))
    user> (def s4 (sorted-set-by good-second-< [:a 1] [:b 1] [:c 1]))
    #'user/s4
    user> s4
    #{[:a 1] [:b 1] [:c 1]}
    user> (contains? s4 [:b 1])
    true
    user> (s4 [:c 1])
    [:c 1]
```

Note: Clojure does *not* compare vectors with different lengths
lexicographically, so only use this technique with equal length
vectors.
