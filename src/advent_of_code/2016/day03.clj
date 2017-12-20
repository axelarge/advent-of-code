(ns advent-of-code.2016.day03
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (str/trim (get-input 2016 3)))

(defn valid-triangle? [a b c]
  (let [[a b c] (sort [a b c])]
    (> (+ a b) c)))

(defn count-valid [triangles]
  (->> triangles
       (map (partial apply valid-triangle?))
       (map #(if % 1 0))
       (reduce +)))

(defn solve1 [input]
  (count-valid (tokenize-lines parse-int input)))

(defn solve2 [input]
  (->> input
       (tokenize-lines parse-int)
       (partition 3)
       (map transpose)
       (apply concat)
       count-valid))
