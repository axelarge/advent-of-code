(ns advent-of-code.2017.day24-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day24 :refer :all]))

(def test-input-1 "0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10")

(deftest test-solve1
  (is (= (solve1 test-input-1) 31))
  (is (= (solve1 input) 1656)))

(deftest test-solve2
  (is (= (solve2 test-input-1) 19))
  (is (= (solve2 input) 1642)))
