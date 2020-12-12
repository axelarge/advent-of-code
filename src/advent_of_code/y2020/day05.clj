(ns advent-of-code.y2020.day05
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 5))

(defn parse-line [line]
  (parse-int (apply str (map {\F 0 \B 1 \L 0 \R 1} line)) 2))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn solve1 [input]
  (apply max (parse input)))

(defn solve2 [input]
  (->> input
       (parse)
       (sort)
       (window)
       (filter (fn [[a b]] (not= (inc a) b)))
       (ffirst)
       (inc)))
