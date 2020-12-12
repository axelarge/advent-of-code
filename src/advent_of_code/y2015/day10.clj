(ns advent-of-code.y2015.day10
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input "3113322113")

(defn step [digits]
  (->> digits
       (partition-by identity)
       (mapcat (fn [g] [(count g) (first g)]))
       (str/join)))

(defn solve [n input]
  (-> (iterate step input)
      (nth n)
      (count)))

(defn solve1 [input]
  (solve 40 input))


(defn solve2 [input]
  (solve 50 input))

