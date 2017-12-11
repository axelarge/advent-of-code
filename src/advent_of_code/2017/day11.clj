(ns advent-of-code.2017.day11
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 11))

(def to-coords
  {:n  [0 +1]
   :s  [0 -1]
   :ne [+1 0]
   :sw [-1 0]
   :se [+1 -1]
   :nw [-1 +1]})

(defn distance [[a b]]
  (->> [a b (- 0 a b)]
       (map #(Math/abs %))
       (apply max)))

(defn solve [input]
  (->> (str/split input #"[^a-z]+")
       (map (comp to-coords keyword))
       (reductions (partial mapv +))
       (map distance)))

(def solve1 (comp last solve))
(def solve2 (comp (partial apply max) solve))
