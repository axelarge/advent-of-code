(ns advent-of-code.y2017.day17-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day17 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 638))
  (is (= (solve1 input) 136)))

(deftest test-solve2
  (is (= (map #(after-zero % 3) (range 0 13))
         [0 1 2 2 2 5 5 5 5 9 9 9 12]))
  (is (= (solve2 input) 1080289)))
