(ns advent-of-code.y2021.day01
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2021 1))

(defn parse [input]
  (map find-int (str/split-lines input)))

(defn solve1 [input]
  (->> input
       (parse)
       (window)
       (count-where (partial apply <))))

(defn solve2 [input]
  (->> input
       (parse)
       (window 3)
       (map (partial apply +))
       (window)
       (count-where (partial apply <))))
