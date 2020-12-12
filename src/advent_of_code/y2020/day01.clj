(ns advent-of-code.y2020.day01
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2020 1))

(defn parse [input]
  (set (find-ints input)))

(defn solve [sum n nums]
  (->> nums
       (some (if (< n 3)
               #(some-> (nums (- sum %)) (* %))
               #(some-> (solve (- sum %) (dec n) nums) (* %))))))

(defn solve1 [input]
  (solve 2020 2 (parse input)))

(defn solve2 [input]
  (solve 2020 3 (parse input)))
