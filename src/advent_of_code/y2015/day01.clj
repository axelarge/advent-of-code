(ns advent-of-code.y2015.day01
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2015 1))

(defn parse [s]
  (map {\( +1 \) -1} s))

(defn solve1 [input]
  (->> input
       (parse)
       (reduce +)))

(defn solve2 [input]
  (->> input
       (parse)
       (reductions +)
       (map-indexed vector)
       (find-where (comp neg? second))
       (first)
       (inc)))
