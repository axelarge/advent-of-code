(ns advent-of-code.y2015.day08
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 8))

(defn decode-cost [line]
  (->> line
       (re-seq #"\\\\|\\\"|\\x[0-9a-f]{2}")
       (map (comp dec count))
       (reduce + 2)))

(defn encode-cost [line]
  (+ 2 (count-where #{\\ \"} line)))

(defn solve1 [input]
  (->> input
       (str/split-lines)
       (map decode-cost)
       (reduce +)))

(defn solve2 [input]
  (->> input
       (str/split-lines)
       (map encode-cost)
       (reduce +)))
