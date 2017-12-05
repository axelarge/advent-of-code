(ns advent-of-code.2017.day5-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day5 :refer :all]))

(def test-input "0\n3\n0\n1\n-3")

(deftest test-solve1
  (is (= (solve1 test-input) 5))
  (is (= (solve1) 376976)))

(deftest test-solve2
  (is (= (solve2 test-input) 10))
  (is (= (solve2) 29227751)))
