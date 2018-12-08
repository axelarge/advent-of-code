(ns advent-of-code.2018.day04-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day04 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 240))
  (is (= (solve1 input) 26281)))

(deftest test-solve2
  (is (= (solve2 test-input) 4455))
  (is (= (solve2 input) 73001)))
