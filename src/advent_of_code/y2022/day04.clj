(ns advent-of-code.y2022.day04
  (:require [advent-of-code.support :refer :all]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (get-input 2022 4))

(defn parse-line [line]
  (let [[a b c d] (find-pos-ints line)]
    [(set (range a (inc b)))
     (set (range c (inc d)))]))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn solve1 [input]
  (->> (parse input)
       (count-where (fn [[a b]]
                      (or (set/subset? a b)
                          (set/subset? b a))))))

(defn solve2 [input]
  (->> (parse input)
       (count-where (fn [[a b]]
                      (not-empty (set/intersection a b))))))
