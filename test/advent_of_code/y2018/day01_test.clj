(ns advent-of-code.y2018.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day01 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "+1, -2, +3, +1") 3))
  (is (= (solve1 "+1, +1, +1") 3))
  (is (= (solve1 "+1, +1, -2") 0))
  (is (= (solve1 "-1, -2, -3") -6))
  (is (= (solve1 input) 484)))

(deftest test-solve2
  (is (= (solve2 "+1, -2, +3, +1") 2))
  (is (= (solve2 "+1, -1") 0))
  (is (= (solve2 "+3, +3, +4, -2, -4") 10))
  (is (= (solve2 "-6, +3, +8, +5, -6") 5))
  (is (= (solve2 "+7, +7, -2, -7, -4") 14))
  (is (= (solve2 input) 367)))
