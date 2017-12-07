(ns advent-of-code.2017.day5
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 5))

(defn step [maze i f]
  [(update maze i f)
   (+ i (nth maze i))])

(defn steps [f maze]
  (loop [n 1
         i 0
         maze maze]
    (let [[maze i] (step maze i f)]
      (if (contains? maze i)
        (recur (inc n) i maze)
        n))))

(defn solve [f input]
  (->> input
       str/split-lines
       (mapv parse-int)
       (steps f)))

(def solve1 (partial solve inc))
(def solve2 (partial solve (fn [x] (if (< x 3) (inc x) (dec x)))))
