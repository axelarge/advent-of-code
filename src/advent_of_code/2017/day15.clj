(ns advent-of-code.2017.day15
  (:require [advent-of-code.support :refer :all]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def test-input [65 8921])
(def input [703 516])

(defn stream [^long start ^long mul ^long div-by]
  (->> (iterate (fn [^long x] (-> x (* mul) (mod 2147483647)))
                start)
       rest
       (filter (fn [^long x] (zero? (rem x div-by))))))

(defn lowest [^long n]
  (bit-and 0xffff n))

(defn match? [[a b]]
  (= (lowest a) (lowest b)))

(defn solve [divs limit input]
  (->> (mapv stream input [16807 48271] divs)
       (apply map vector)
       (take limit)
       (count-where match?)))

(def solve1 (partial solve [1 1] 40000000))
(def solve2 (partial solve [4 8] 5000000))
