(ns advent-of-code.2020.day04-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day04 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 216)))

(deftest test-solve2
  (is (= (solve2 input) 150)))
