(ns advent-of-code.2015.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day01 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 74)))

(deftest test-solve2
  (is (= (solve2 ")") 1))
  (is (= (solve2 "()())") 5))
  (is (= (solve2 input) 1795)))
