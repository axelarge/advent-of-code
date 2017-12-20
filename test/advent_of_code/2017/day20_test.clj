(ns advent-of-code.2017.day20-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day20 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input1) 0))
  (is (= (solve1 input) 376)))

(deftest test-solve2
  (is (= (solve2 test-input2) 1))
  (is (= (solve2 input) 574)))
