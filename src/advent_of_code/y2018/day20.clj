(ns advent-of-code.y2018.day20
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2018 20))

(defn parse [input]
  (loop [input (seq input)
         edges {}
         stack [[0 0]]
         pos [0 0]]
    (if-some [[c & t] input]
      (if-let [delta ({\N [0 -1] \S [0 1] \E [1 0] \W [-1 0]} c)]
        (let [pos1 (vec+ pos delta)]
          (recur t (update edges pos conj-set pos1) stack pos1))
        (case c
          \| (recur t edges stack (peek stack))
          \( (recur t edges (conj stack pos) pos)
          \) (recur t edges (pop stack) pos)
          (recur t edges stack pos)))
      edges)))

(defn solve1 [input]
  (dfs (fn [depth _] (inc depth))
       max
       [0 0]
       0
       (parse input)))

(defn solve2 [input]
  (dfs (fn [depth _] (inc depth))
       (fn [n>=1k depth]
         (cond-> n>=1k (>= depth 1000) inc))
       [0 0]
       0
       (parse input)))
