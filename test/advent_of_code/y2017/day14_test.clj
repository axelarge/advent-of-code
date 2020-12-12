(ns advent-of-code.y2017.day14-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day14 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 8108))
  (is (= (solve1 input) 8316)))

(deftest test-solve2
  (is (= (solve2 test-input) 1242))
  (is (= (solve2 input) 1074)))
