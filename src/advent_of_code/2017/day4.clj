(ns advent-of-code.2017.day4
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 4))

(defn no-dupes-by [f phrase]
  (->> phrase
       (group-by f)
       vals
       (not-any? #(> (count %) 1))))

(defn solve
  ([f]
   (solve f input))
  ([f input]
   (->> input
        tokenize-lines
        (filter (partial no-dupes-by f))
        count)))

(def solve1
  (partial solve identity))

(def solve2
  (partial solve sort))
