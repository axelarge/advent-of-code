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

(defn dijkstra [start end? can-go?]
  (loop [queue (queue [start 0])
         seen #{}]
    (when-let [[pos n] (peek queue)]
      (cond (seen pos) (recur (pop queue) seen)
            (end? pos) n
            :else (recur (->> (neighbors4 pos)
                              (filter (partial can-go? pos))
                              (map #(vector % (inc n)))
                              (into (pop queue)))
                         (conj seen pos))))))

(defn solve1 [input]
  (let [grid (parse input)]
    (dijkstra (find-pos grid #{\S})
              #(= (get-in grid %) \E)
              (partial can-go? grid))))

(defn solve2 [input]
  (let [grid (parse input)]
    (dijkstra (find-pos grid #{\E})
              #(#{\S \a} (get-in grid %))
              (fn [from to] (can-go? grid to from)))))
