(ns advent-of-code.2015.day24-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day24 :refer :all]))

(def test-input [1 2 3 4 5 7 8 9 10 11])

(deftest test-solve1
  (is (= (solve 3 test-input) 99))
  (is (= (solve1 input) 11266889531)))

(deftest test-solve2
  (is (= (solve 4 test-input) 44))
  (is (= (solve2 input) 77387711)))
