(ns advent-of-code.2017.day2-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day2 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "5 1 9 5\n7 5 3\n2 4 6 8") 18))
  (is (= (solve1 input) 58975)))

(deftest test-solve2
  (is (= (solve2 "5 9 2 8\n9 4 7 3\n3 8 6 5") 9))
  (is (= (solve2 input) 308)))
