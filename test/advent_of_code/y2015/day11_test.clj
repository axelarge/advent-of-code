(ns advent-of-code.y2015.day11-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day11 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) "hepxxyzz")))

(deftest test-solve2
  (is (= (solve2 input) "heqaabcc")))
