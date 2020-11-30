(ns advent-of-code.2015.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day06 :refer :all]))

(deftest ^:slow test-solve1
  (is (= (solve1 input) 377891)))

(deftest ^:slow test-solve2
  (is (= (solve2 input) 14110788)))
