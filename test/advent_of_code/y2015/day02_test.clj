(ns advent-of-code.y2015.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day02 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "2x3x4") 58))
  (is (= (solve1 "1x1x10") 43))
  (is (= (solve1 input) 1598415)))

(deftest test-solve2
  (is (= (solve2 "2x3x4") 34))
  (is (= (solve2 "1x1x10") 14))
  (is (= (solve2 input) 3812909)))
