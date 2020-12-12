(ns advent-of-code.y2018.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day03 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 4))
  (is (= (solve1 input) 104439)))

(deftest test-solve2
  (is (= (solve2 test-input) 3))
  (is (= (solve2 input) 701)))
