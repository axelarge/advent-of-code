(ns advent-of-code.2017.day8-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day8 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 1))
  (is (= (solve1 input) 4888)))

(deftest test-solve2
  (is (= (solve2 test-input) 10))
  (is (= (solve2 input) 7774)))
