(ns advent-of-code.y2021.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day01 :refer :all]))

(def test-input "199\n200\n208\n210\n200\n207\n240\n269\n260\n263")

(deftest test-solve1
  (is (= (solve1 test-input) 7))
  (is (= (solve1 input) 1715)))

(deftest test-solve2
  (is (= (solve2 test-input) 5))
  (is (= (solve2 input) 1739)))
