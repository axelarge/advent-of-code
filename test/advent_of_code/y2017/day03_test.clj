(ns advent-of-code.y2017.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day03 :refer :all]))

(deftest test-solve1
  (is (= (solve1 1) 0))
  (is (= (solve1 12) 3))
  (is (= (solve1 23) 2))
  (is (= (solve1 1024) 31))
  (is (= (solve1 input) 552)))

(deftest test-solve2
  (is (= (solve2 50) 54))
  (is (= (solve2 800) 806))
  (is (= (solve2 input) 330785)))
