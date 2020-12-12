(ns advent-of-code.y2020.day08-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2020.day08 :refer :all]))

(def test-input "nop +0\nacc +1\njmp +4\nacc +3\njmp -3\nacc -99\nacc +1\njmp -4\nacc +6")

(deftest test-solve1
  (is (= (solve1 test-input) 5))
  (is (= (solve1 input) 1867)))

(deftest test-solve2
  (is (= (solve2 test-input) 8))
  (is (= (solve2 input) 1303)))
