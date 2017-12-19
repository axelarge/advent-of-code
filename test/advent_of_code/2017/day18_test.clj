(ns advent-of-code.2017.day18-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day18 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 4))
  (is (= (solve1 input) 7071)))

(deftest test-solve2
  (is (= (solve2 test-input2) 3))
  (is (= (solve2 input) 8001)))
