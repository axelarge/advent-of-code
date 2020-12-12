(ns advent-of-code.y2015.day05
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 05))

(defn nice1 [s]
  (and (>= (count (keep #{\a \e \i \o \u} s)) 3)
       (re-find #"(.)\1" s)
       (not (re-find #"ab|cd|pq|xy" s))))

(defn nice2 [s]
  (and (re-find #"(..).*\1" s)
       (re-find #"(.).\1" s)))

(defn solve [pred input]
  (count-where pred (str/split-lines input)))

(defn solve1 [input]
  (solve nice1 input))

(defn solve2 [input]
  (solve nice2 input))
