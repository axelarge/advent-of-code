(ns advent-of-code.2017.day2
  (:require [clojure.test :refer :all]))

(deftest test-solve1
  (is (= (solve1 "5 1 9 5\n7 5 3\n2 4 6 8")
         18)))

(deftest test-solve2
  (is (= (solve2 "5 9 2 8\n9 4 7 3\n3 8 6 5")
         9)))
