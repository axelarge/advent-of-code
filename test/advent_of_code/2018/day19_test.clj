(ns advent-of-code.2018.day19-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day19 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 1824)))

(deftest test-solve2
  #_(is (= (solve2 input) nil)))
