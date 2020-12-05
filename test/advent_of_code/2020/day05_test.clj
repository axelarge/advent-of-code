(ns advent-of-code.2020.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day05 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "FBFBBFFRLR")) 357)
  (is (= (solve1 input) 965)))

(deftest test-solve2
  (is (= (solve2 input) 524)))
