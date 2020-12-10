(ns advent-of-code.2020.day10
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2020 10))

(defn parse [input]
  (let [nums (into [0] (sort (find-ints input)))]
    (conj nums (+ 3 (peek nums)))))

(defn solve1 [input]
  (let [f (->> (parse input)
               (window)
               (map (fn [[a b]] (- b a)))
               (frequencies))]
    (* (f 1) (f 3))))

(defn solve2 [input]
  (let [nums (parse input)]
    (get (reduce (fn [seen x]
                   (->> (range -3 0)
                        (map #(get seen (+ x %) 0))
                        (reduce +)
                        (assoc seen x)))
                 {0 1}
                 (rest nums))
         (peek nums))))

;;(defn solve [[from & nums]]
;;  (if nums
;;    (->> (tails nums)
;;         (take-while #(<= (- (first %) from) 3))
;;         (map solve)
;;         (reduce +))
;;    1))
