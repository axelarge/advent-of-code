(ns advent-of-code.2017.day1
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (-> (get-input 2017 1) str/trim))

(defn sum-valid-pairs [pairs]
  (->> pairs
       (filter #(apply = %))
       (map (comp parse-int str first))
       (apply +)))

(defn solve1
  ([] (solve1 input))
  ([s]
   (->> (str s (first s))
        (partition 2 1)
        sum-valid-pairs)))

(defn solve2
  ([] (solve2 input))
  ([s]
   (let [offset (/ (count s) 2)]
     (->> s
          cycle
          (drop offset)
          (map vector s)
          sum-valid-pairs))))
