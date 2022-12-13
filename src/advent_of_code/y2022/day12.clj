(ns advent-of-code.y2022.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2022 12))

(defn parse [input]
  (mapv vec (str/split-lines input)))

(defn normalize [v]
  (case v \S \a \E \z v))

(defn find-pos [grid pred]
  (reduce-grid (fn [_ x y v] (when (pred v) (reduced [y x])))
               nil
               grid))

(defn can-go? [grid from to]
  (when-let [from-val (normalize (get-in grid from))]
    (when-let [to-val (normalize (get-in grid to))]
      (>= (int from-val) (dec (int to-val))))))

(defn solve [input start end? can-go?]
  (let [grid (parse input)]
    (dijkstra (find-pos grid #{start})
              #(end? (get-in grid %))
              #(->> (neighbors4 %)
                    (filter (partial can-go? grid %))))))

(defn solve1 [input]
  (solve input \S #{\E} can-go?))

(defn solve2 [input]
  (solve input \E #{\S \a} (fn [g from to] (can-go? g to from))))
