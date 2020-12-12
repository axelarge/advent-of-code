(ns advent-of-code.y2020.day11-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2020.day11 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 2489)))

(deftest test-solve2
  (is (= (solve2 input) 2180)))
