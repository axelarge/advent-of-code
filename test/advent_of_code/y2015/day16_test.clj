(ns advent-of-code.y2015.day16-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day16 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 40)))

(deftest test-solve2
  (is (= (solve2 input) 241)))
