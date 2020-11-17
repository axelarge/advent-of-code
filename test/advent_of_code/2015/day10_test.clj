(ns advent-of-code.2015.day10-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day10 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 329356)))

(deftest ^:slow test-solve2
  (is (= (solve2 input) 4666278)))
