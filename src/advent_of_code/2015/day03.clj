(ns advent-of-code.2015.day03
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 3))

(defn positions [moves]
  (->> moves
       (map {\^ [0 -1] \v [0 1] \< [-1 0] \> [1 0]})
       (reductions (partial mapv +)
                   [0 0])))

(defn alternate [moves]
  (apply concat (partition 1 2 moves)))

(defn solve1 [input]
  (->> input
       (positions)
       (set)
       (count)))

(defn solve2 [input]
  (->> (concat (positions (alternate input))
               (positions (alternate (rest input))))
       (set)
       (count)))
