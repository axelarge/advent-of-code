(ns advent-of-code.2015.day25-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day25 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 19980801)))
