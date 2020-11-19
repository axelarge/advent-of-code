(ns advent-of-code.2015.day21-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day21 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 111)))

(deftest test-solve2
  (is (= (solve2 input) 188)))
