(ns advent-of-code.y2022.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day01 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 67658)))

(deftest test-solve2
  (is (= (solve2 input) 200158)))
