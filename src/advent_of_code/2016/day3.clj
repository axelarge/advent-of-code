(ns advent-of-code.2016.day3
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

(defn solve1
  ([] (solve1 input))
  ([input]
   (count-valid (tokenize-lines parse-int input))))

(defn solve2
  ([] (solve2 input))
  ([input]
   (->> input
        (tokenize-lines parse-int)
        (partition 3)
        (map transpose)
        (apply concat)
        count-valid)))
