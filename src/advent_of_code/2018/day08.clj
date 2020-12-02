(ns advent-of-code.2018.day08
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2018 8))

(defn parse [input]
  (find-ints input))

(defn solve ; -> [meta-sum, value, next-i]
  ([xs] (solve xs 0))
  ([xs i]
   (let [n-children (nth xs i)
         n-meta (nth xs (inc i))]
     (let [[sum child-vals i]
           (reduce (fn [[sum child-vals i] _]
                     (let [[child-sum value i] (solve xs i)]
                       [(+ sum child-sum)
                        (conj child-vals value)
                        i]))
                   [0 [] (+ i 2)]
                   (range n-children))
           metas (subvec xs i (+ i n-meta))
           sum (reduce + sum metas)
           value (if (pos? n-children)
                   (->> metas
                        (map #(get child-vals (dec %) 0))
                        (reduce +))
                   sum)]
       [sum value (+ i n-meta)]))))

(defn solve1 [input]
  (first (solve (parse input))))

(defn solve2 [input]
  (second (solve (parse input))))
