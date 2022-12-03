(ns advent-of-code.y2022.day03
  (:require [advent-of-code.support :refer :all]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (get-input 2022 3))

(defn score [c]
  (if (neg? (compare c \a))
    (- (int c) (int \A) -27)
    (- (int c) (int \a) -1)))

(defn solve1 [input]
  (->> input
       str/split-lines
       (map (fn [line]
              (->> line
                   (partition (/ (count line) 2))
                   (map set)
                   (apply set/intersection)
                   first
                   score)))
       (reduce +)))

(defn solve2 [input]
  (->> input
       str/split-lines
       (map set)
       (partition 3)
       (map (comp score first (partial apply set/intersection)))
       (reduce +)))
