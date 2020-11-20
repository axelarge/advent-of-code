(ns advent-of-code.2015.day24
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations]]))

(def input (get-input 2015 24))

(defn parse [input]
  (map parse-int (str/split-lines input)))

(defn solve [n weights]
  (let [sum (/ (reduce + weights) n)]
    (->> (for [size (range 1 (- (count weights) (dec n)))
               group (combinations weights size)
               :when (= (reduce + group) sum)]
           (reduce * group))
         (first))))

(defn solve1 [input]
  (solve 3 (parse input)))

(defn solve2 [input]
  (solve 4 (parse input)))
