(ns advent-of-code.2017.day04
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 4))

(defn no-dupes-by [f phrase]
  (= (count (distinct (map f phrase)))
     (count phrase)))

(defn solve [f input]
  (->> input
       (split-lines split-ws)
       (count-where (partial no-dupes-by f))))

(def solve1 (partial solve identity))
(def solve2 (partial solve sort))
