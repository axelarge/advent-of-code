(ns advent-of-code.2019.day01
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2019 1))

(defn parse [input]
  (find-ints input))

(defn fuel-req [mass]
  (-> mass (quot 3) (- 2)))

(defn fuel-req2 [mass]
  (loop [mass mass
         fuel-total 0]
    (let [fuel (fuel-req mass)]
      (if (pos? fuel)
        (recur fuel (+ fuel-total fuel))
        fuel-total))))

(defn solve [f input]
  (->> input
       parse
       (map f)
       (apply +)))

(def solve1 (partial solve fuel-req))
(def solve2 (partial solve fuel-req2))
