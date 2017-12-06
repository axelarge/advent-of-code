(ns advent-of-code.2017.day6
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 6))

(defn step [banks]
  (let [x (apply max banks)
        i (.indexOf banks x)
        n (count banks)
        q (quot x n)
        r (rem x n)
        to-end (+ i 1 r)
        from-start (- to-end n)]
    (->> (assoc banks i 0)
         (map-indexed
           (fn [idx v] (+ v
                          q
                          (if (or (< idx from-start)
                                  (< i idx to-end))
                            1 0))))
         vec)))

(defn step-until-seen [banks]
  (loop [banks banks
         seen #{banks}]
    (let [banks (step banks)]
      (if (seen banks)
        [(count seen) banks]
        (recur banks (conj seen banks))))))

(defn solve
  ([] (solve input))
  ([input]
   (->> input
        split-whitespace
        (mapv parse-int)
        step-until-seen)))

(def solve1 (comp first solve))
(def solve2 (comp first step-until-seen second solve))