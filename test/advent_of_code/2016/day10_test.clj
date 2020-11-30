(ns advent-of-code.2016.day10-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2016.day10 :refer :all]))

(def test-input "value 5 goes to bot 2\nbot 2 gives low to bot 1 and high to bot 0\nvalue 3 goes to bot 1\nbot 1 gives low to output 1 and high to bot 0\nbot 0 gives low to output 2 and high to output 0\nvalue 2 goes to bot 2")

(deftest test-solve1
  (is (= (solve1 test-input [5 2]) 2))
  (is (= (solve1 input) 118)))

(deftest test-solve2
  (is (= (solve2 input) 143153)))
