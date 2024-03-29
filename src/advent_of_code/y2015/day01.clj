(ns advent-of-code.y2015.day01
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2015 1))

(defn parse [s]
  (map {\( +1 \) -1} s))

(defn solve1 [input]
  (reduce + (parse input)))

(defn solve2 [input]
  (->> input parse (reductions +) (index-where neg?) inc))
