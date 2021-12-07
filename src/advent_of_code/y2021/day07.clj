(ns advent-of-code.y2021.day07
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2021 7))

(defn solve [cost input]
  (let [crabs (find-ints input)]
    (->> (range (apply min crabs) (inc (apply max crabs)))
         (map (fn [pos] (reduce + (map #(cost (abs (- pos %))) crabs))))
         (apply min))))

(def solve1 (partial solve identity))
(def solve2 (partial solve #(/ (* % (inc %)) 2)))
