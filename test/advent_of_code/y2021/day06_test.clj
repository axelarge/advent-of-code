(ns advent-of-code.y2021.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day06 :refer :all]))

(def test-input "3,4,3,1,2")

(deftest test-solve1
  (is (= (solve1 test-input) 5934))
  (is (= (solve1 input) 360268)))

(deftest test-solve2
  (is (= (solve2 test-input) 26984457539))
  (is (= (solve2 input) 1632146183902)))
