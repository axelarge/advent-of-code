(ns advent-of-code.2015.day22-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day22 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 1269)))

(deftest test-solve2
  (is (= (solve2 input) 1309)))
