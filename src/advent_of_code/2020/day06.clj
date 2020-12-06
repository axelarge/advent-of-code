(ns advent-of-code.2020.day06
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 6))

(defn parse [input]
  (for [group (str/split input #"\n\n")]
    (map set (str/split-lines group))))

(defn solve1 [input]
  (->> (parse input)
       (map #(count (apply clojure.set/union %)))
       (reduce +)))

(defn solve2 [input]
  (->> (parse input)
       (map #(count (apply clojure.set/intersection %)))
       (reduce +)))
