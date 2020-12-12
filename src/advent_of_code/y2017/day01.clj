(ns advent-of-code.y2017.day01
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (-> (get-input 2017 1) str/trim))

(defn sum-valid-pairs [pairs]
  (->> pairs
       (filter #(apply = %))
       (map (comp parse-int str first))
       (apply +)))

(defn solve1 [input]
  (->> (str input (first input))
       (partition 2 1)
       sum-valid-pairs))

(defn solve2 [input]
  (let [offset (/ (count input) 2)]
    (->> input
         cycle
         (drop offset)
         (map vector input)
         sum-valid-pairs)))
