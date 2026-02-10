(ns advent-of-code.y2022.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2022 12))

(defn parse [input]
  (grid->map (mapv vec (str/split-lines input))))

(defn normalize [v]
  (case v \S \a \E \z v))

(defn can-go? [grid to from]
  (when-let [from-val (normalize (get grid from))]
    (when-let [to-val (normalize (get grid to))]
      (>= (int from-val) (dec (int to-val))))))

(defn solve [input end?]
  (let [grid (parse input)]
    (dijkstra (key (first-where #(= \E (val %)) grid))
              #(end? (get grid %))
              #(->> (neighbors4 %)
                    (filter (partial can-go? grid %))))))

(defn solve1 [input]
  (solve input #{\S}))

(defn solve2 [input]
  (solve input #{\S \a}))
