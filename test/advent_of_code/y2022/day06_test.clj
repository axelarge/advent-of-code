(ns advent-of-code.y2022.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day06 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 1235)))

(deftest test-solve2
  (is (= (solve2 input) 3051)))
