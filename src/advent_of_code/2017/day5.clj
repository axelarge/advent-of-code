(ns advent-of-code.2017.day5
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 5))

(defn step [jumps i f]
  [(update jumps i f)
   (+ i (nth jumps i))])

(defn steps [f jumps]
  (loop [n 1
         i 0
         jumps jumps]
    (let [[jumps i] (step jumps i f)]
      (if (< -1 i (count jumps))
        (recur (inc n) i jumps)
        n))))

(defn solve
  ([f] (solve f input))
  ([f input]
   (->> input
        str/split-lines
        (mapv parse-int)
        (steps f))))

(def solve1 (partial solve inc))
(def solve2 (partial solve (fn [x] (if (>= x 3)
                                     (dec x)
                                     (inc x)))))
