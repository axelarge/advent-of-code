(ns advent-of-code.y2016.day13-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2016.day13 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "10" [7 4]) 11))
  (is (= (solve1 input) 96)))

(deftest test-solve2
  (is (= (solve2 input) 141)))
