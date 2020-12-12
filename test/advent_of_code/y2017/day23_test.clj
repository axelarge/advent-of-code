(ns advent-of-code.y2017.day23-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day23 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 6241)))

(deftest test-solve2
  (is (= (solve2) 909)))
