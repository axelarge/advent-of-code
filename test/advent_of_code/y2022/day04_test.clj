(ns advent-of-code.y2022.day04-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day04 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 509)))

(deftest test-solve2
  (is (= (solve2 input) 870)))
