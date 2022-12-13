(ns advent-of-code.y2022.day03
  (:require [advent-of-code.support :refer :all]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (get-input 2022 3))

(defn score [c]
  (if (neg? (compare c \a))
    (- (int c) (int \A) -27)
    (- (int c) (int \a) -1)))

(defn common [sets]
  (->> sets (map set) (apply set/intersection) first score))

(defn solve [input f]
  (->> input str/split-lines f (map common) (reduce +)))

(defn solve1 [input]
  (solve input (partial map #(partition (/ (count %) 2) %))))

(defn solve2 [input]
  (solve input (partial partition 3)))
