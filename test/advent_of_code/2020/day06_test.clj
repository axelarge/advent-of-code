(ns advent-of-code.2020.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day06 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 6612)))

(deftest test-solve2
  (is (= (solve2 input) 3268)))
