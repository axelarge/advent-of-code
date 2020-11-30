(ns advent-of-code.2015.day03
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2015 3))

(defn positions [moves]
  (->> moves
       (map {\^ [0 -1] \v [0 1] \< [-1 0] \> [1 0]})
       (reductions (partial mapv +) [0 0])))

(defn solve1 [input]
  (count (set (positions input))))

(defn solve2 [input]
  (->> (concat (positions (take-nth 2 input))
               (positions (take-nth 2 (rest input))))
       (set)
       (count)))
