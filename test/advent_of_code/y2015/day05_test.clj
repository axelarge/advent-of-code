(ns advent-of-code.y2015.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day05 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 236)))

(deftest test-solve2
  (is (= (solve2 input) 51)))
