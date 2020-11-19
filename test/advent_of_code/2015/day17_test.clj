(ns advent-of-code.2015.day17-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day17 :refer :all]))

(def test-input "20\n15\n10\n5\n5")

(deftest test-solve1
  (is (= (solve1 25 test-input) 4))
  (is (= (solve1 input) 4372)))

(deftest test-solve2
  (is (= (solve2 25 test-input) 3))
  (is (= (solve2 input) 4)))
