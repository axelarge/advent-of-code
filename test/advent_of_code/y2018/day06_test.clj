(ns advent-of-code.y2018.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day06 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 17))
  (is (= (solve1 input) 3010)))

(deftest test-solve2
  (is (= (solve2 test-input 32) 16))
  (is (= (solve2 input) 48034)))
