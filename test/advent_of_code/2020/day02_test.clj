(ns advent-of-code.2020.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day02 :refer :all]))

(def test-input "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc")

(deftest test-solve1
  (is (= (solve1 test-input) 2))
  (is (= (solve1 input) 625)))

(deftest test-solve2
  (is (= (solve2 test-input) 1))
  (is (= (solve2 input) 391)))
