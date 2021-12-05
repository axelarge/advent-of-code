(ns advent-of-code.y2021.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day02 :refer :all]))

(def test-input "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2")

(deftest test-solve1
  (is (= (solve1 test-input) 150))
  (is (= (solve1 input) 1636725)))

(deftest test-solve2
  (is (= (solve2 test-input) 900))
  (is (= (solve2 input) 1872757425)))
