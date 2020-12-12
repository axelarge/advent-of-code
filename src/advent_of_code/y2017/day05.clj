(ns advent-of-code.y2017.day05
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 5))

(defn steps [f maze]
  (loop [n 0
         i 0
         maze (transient maze)]
    (if-let [v (get maze i)]
      (recur (inc n)
             (+ i v)
             (assoc! maze i (f v)))
      n)))

(defn solve [f input]
  (steps f (find-ints input)))

(def solve1 (partial solve inc))
(def solve2 (partial solve #(if (< % 3) (inc %) (dec %))))
