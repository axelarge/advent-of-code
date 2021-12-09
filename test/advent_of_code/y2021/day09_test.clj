(ns advent-of-code.y2021.day09-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day09 :refer :all]))

(def test-input "2199943210\n3987894921\n9856789892\n8767896789\n9899965678")

(deftest test-solve1
  (is (= (solve1 test-input) 15))
  (is (= (solve1 input) 594)))

(deftest test-solve2
  (is (= (solve2 test-input) 1134))
  (is (= (solve2 input) 858494)))
