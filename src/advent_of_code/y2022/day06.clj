(ns advent-of-code.y2022.day06
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2022 6))

(defn solve [n input]
  (->> input
       (partition n 1)
       (index-where #(= n (count (set %))))
       (+ n)))

(defn solve1 [input]
  (solve 4 input))

(defn solve2 [input]
  (solve 14 input))
