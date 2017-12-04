(ns advent-of-code.2017.day3
  (:require [clojure.test :refer :all]))

(deftest test-solve1
  (is (= (solve1 1) 0))
  (is (= (solve1 12) 3))
  (is (= (solve1 23) 2))
  (is (= (solve1 1024) 31)))
