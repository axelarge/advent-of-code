(ns advent-of-code.2017.day5
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
  (->> input
       str/split-lines
       (mapv parse-int)
       (steps f)))

(def solve1 (partial solve inc))
(def solve2 (partial solve #(if (< % 3) (inc %) (dec %))))
