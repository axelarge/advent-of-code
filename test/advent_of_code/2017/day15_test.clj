(ns advent-of-code.2017.day15-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day15 :refer :all]))

(deftest ^:slow test-solve1
  (is (= (solve1 test-input) 588))
  (is (= (solve1 input) 594)))

(deftest ^:slow test-solve2
  (is (= (solve2 test-input) 309))
  (is (= (solve2 input) 328)))

