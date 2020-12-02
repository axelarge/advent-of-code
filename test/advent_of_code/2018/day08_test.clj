(ns advent-of-code.2018.day08-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day08 :refer :all]))

(def test-input "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")

(deftest test-solve1
  (is (= (solve1 test-input) 138))
  (is (= (solve1 input) 40309)))

(deftest test-solve2
  (is (= (solve2 test-input) 66))
  (is (= (solve2 input) 28779)))
