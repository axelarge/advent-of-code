(ns advent-of-code.y2020.day11
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 11))

(defn parse [input]
  (mapv vec (str/split-lines input)))

(defn adjacent-seats [grid pos]
  (->> (neighbors8 pos)
       (filter #(#{\L} (get-in grid %)))))

(defn visible-seats [grid pos]
  (->> (neighbors8 [0 0])
       (keep (fn [dir]
               (loop [pos pos]
                 (let [pos (mapv + pos dir)]
                   (case (get-in grid pos)
                     \L pos
                     nil nil
                     (recur pos))))))))

(defn step [limit neighbors grid]
  (map-grid (fn [x y cell]
              (let [near (->> (neighbors [y x])
                              (count-where #(#{\#} (get-in grid %))))]
                (cond
                  (and (= \L cell) (= near 0)) \#
                  (and (= \# cell) (>= near limit)) \L
                  :else cell)))
            grid))

(defn make-lookup [grid f]
  (reduce-grid (fn [m x y _]
                 (assoc m [y x] (f grid [y x])))
               {} grid))

(defn solve [lookup limit input]
  (let [grid (parse input)
        lookup (make-lookup grid lookup)]
    (->> (iterate (partial step limit lookup) grid)
         (window)
         (find-where (fn [[a b]] (= a b)))
         (first)
         (apply concat)
         (count-where #{\#}))))

(defn solve1 [input]
  (solve adjacent-seats 4 input))

(defn solve2 [input]
  (solve visible-seats 5 input))
