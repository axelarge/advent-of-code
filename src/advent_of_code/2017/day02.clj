(ns advent-of-code.2017.day02
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 2))

(defn row-checksum [row]
  (let [min-val (apply min row)
        max-val (apply max row)]
    (- max-val min-val)))

(defn divisible-pair [row]
  (loop [[x & xs] (sort row)]
    (when xs
      (if-let [dividend (find-where #(zero? (rem % x)) xs)]
        (/ dividend x)
        (recur xs)))))

(defn solve [f input]
  (->> input
       (split-lines find-ints)
       (map f)
       (apply +)))

(def solve1 (partial solve row-checksum))
(def solve2 (partial solve divisible-pair))
