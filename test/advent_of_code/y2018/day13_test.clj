(ns advent-of-code.y2018.day13-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day13 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) "7,3"))
  (is (= (solve1 input) "69,46")))

(deftest test-solve2
  (is (= (solve2 test-input-2) "6,4"))
  (is (= (solve2 input)) "118,108"))
