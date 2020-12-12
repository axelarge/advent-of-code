(ns advent-of-code.y2017.day19-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day19 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) "ABCDEF"))
  (is (= (solve1 input) "MOABEUCWQS")))

(deftest test-solve2
  (is (= (solve2 test-input) 38))
  (is (= (solve2 input) 18058)))
