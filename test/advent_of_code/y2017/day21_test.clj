(ns advent-of-code.y2017.day21-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day21 :refer :all]))

(deftest test-solve1
  (is (= (solve 2 test-input) 12))
  (is (= (solve1 input) 167)))

(deftest test-solve2
  (is (= (solve2 input) 2425195)))
