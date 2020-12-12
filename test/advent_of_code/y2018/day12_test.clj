(ns advent-of-code.y2018.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day12 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 325))
  (is (= (solve1 input) 3241)))

(deftest test-solve2
  (is (= (solve2 test-input)))
  (is (= (solve2 input))))
