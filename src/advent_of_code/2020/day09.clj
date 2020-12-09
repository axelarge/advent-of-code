(ns advent-of-code.2020.day09
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2020 9))

(defn parse [input]
  (find-ints input))

(defn solve1 [input]
  (->> input
       (parse)
       (window 26)
       (find-where (fn [nums]
                     (let [n (peek nums)
                           others (set (pop nums))]
                       (not-any? #(others (- n %)) others))))
       (last)))

(defn solve2 [input]
  (let [nums (parse input)
        target (solve1 input)]
    (loop [sum 0 lo 0 hi 0]
      (cond
        (== sum target) (apply + (apply (juxt min max) (subvec nums lo hi)))
        (< sum target) (recur (+ sum (nth nums hi)) lo (inc hi))
        (> sum target) (recur (- sum (nth nums lo)) (inc lo) hi)))))
