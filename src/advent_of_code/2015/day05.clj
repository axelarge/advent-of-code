(ns advent-of-code.2015.day05
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

(defn solve1 [input]
  (->> input
       (str/split-lines)
       (count-where nice1)))

(defn solve2 [input]
  (->> input
       (str/split-lines)
       (count-where nice2)))
