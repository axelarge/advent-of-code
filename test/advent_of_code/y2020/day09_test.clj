(ns advent-of-code.y2020.day09-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2020.day09 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 1721308972)))

(deftest test-solve2
  (is (= (solve2 input) 209694133)))
