(ns advent-of-code.2016.day1
  (:require [clojure.test :refer :all]))

(deftest test-solve1
  (is (= (solve1 "R2, L3") 5))
  (is (= (solve1 "R2, R2, R2") 2))
  (is (= (solve1 "R5, L5, R5, R3") 12)))
