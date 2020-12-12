(ns advent-of-code.y2019.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2019.day01 :refer :all]))

(deftest test-solve1
  (is (= (fuel-req 12) 2))
  (is (= (fuel-req 14) 2))
  (is (= (fuel-req 1969) 654))
  (is (= (fuel-req 100756) 33583))
  (is (= (solve1 input) 3299598)))

(deftest test-solve2
  (is (= (fuel-req2 12) 2))
  (is (= (fuel-req2 14) 2))
  (is (= (fuel-req2 1969) 966))
  (is (= (fuel-req2 100756) 50346))
  (is (= (solve2 input) 4946546)))

