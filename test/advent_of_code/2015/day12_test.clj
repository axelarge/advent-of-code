(ns advent-of-code.2015.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day12 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 119433)))

(deftest test-solve2
  (is (= (solve2 input) 68466)))
