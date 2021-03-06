(ns advent-of-code.y2017.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day05 :refer :all]))

(def test-input "0\n3\n0\n1\n-3")

(deftest test-solve1-sample
  (is (= (solve1 test-input) 5)))

(deftest ^:slow test-solve1
  (is (= (solve1 input) 376976)))

(deftest test-solve2-sample
  (is (= (solve2 test-input) 10)))

(deftest ^:slow test-solve2
  (is (= (solve2 input) 29227751)))
