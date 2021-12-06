(ns advent-of-code.y2021.day06
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2021 6))

(defn step [fish]
  (->> fish
       (reduce-kv (fn [res k v]
                    (if (= k 0)
                      (-> res
                          (update 6 (fnil + 0) v)
                          (assoc 8 v))
                      (update res (dec k) (fnil + 0) v)))
                  {})))

(defn solve [n input]
  (->> input
       (find-ints)
       (frequencies)
       (nth-iter n step)
       (vals)
       (reduce +)))

(def solve1 (partial solve 80))
(def solve2 (partial solve 256))
