(ns advent-of-code.y2020.day03
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 3))

(def parse str/split-lines)

(defn solve [lines [dx dy]]
  (let [h (count lines)
        w (count (first lines))]
    (loop [seen 0
           y 0
           x 0]
     (if (>= y h)
       seen
       (let [v (get-in lines [y (rem x w)])]
         (recur (cond-> seen (= \# v) (inc))
                (+ y dy)
                (+ x dx)))))))

(defn solve1 [input]
  (solve (parse input) [3 1]))

(defn solve2 [input]
  (let [lines (parse input)]
    (->> [[1 1] [3 1] [5 1] [7 1] [1 2]]
         (map #(solve lines %))
         (reduce *))))
