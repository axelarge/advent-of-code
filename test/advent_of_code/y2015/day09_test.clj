(ns advent-of-code.y2015.day09-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day09 :refer :all]))

(def test-input "London to Dublin = 464\nLondon to Belfast = 518\nDublin to Belfast = 141")

(deftest test-solve1
  (is (= (solve1 test-input) 605))
  (is (= (solve1 input) 117)))

(deftest test-solve2
  (is (= (solve2 test-input) 982))
  (is (= (solve2 input) 909)))
