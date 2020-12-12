(ns advent-of-code.y2015.day24
  (:require [advent-of-code.support :refer :all]
            [clojure.math.combinatorics :refer [combinations]]))

(def input (get-input 2015 24))

(defn solve [n weights]
  (let [sum (/ (reduce + weights) n)]
    (->> (for [size (range 1 (- (count weights) (dec n)))
               group (combinations weights size)
               :when (= (reduce + group) sum)]
           (reduce * group))
         (first))))

(defn solve1 [input]
  (solve 3 (find-ints input)))

(defn solve2 [input]
  (solve 4 (find-ints input)))
