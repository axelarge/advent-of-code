(ns advent-of-code.y2018.day11-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day11 :refer :all]))

(deftest test-power-level
  (is (= (power-level 8 3 5) 4))
  (is (= (power-level 57 122 79) -5))
  (is (= (power-level 39 217 196) 0))
  (is (= (power-level 71 101 153) 4)))

(deftest test-solve1
  (is (= (solve1 18) "33,45"))
  (is (= (solve1 42) "21,61"))
  (is (= (solve1 input) "243,72")))


(deftest test-solve2
  (is (= (solve2 18) "90,269,16"))
  (is (= (solve2 42) "232,251,12"))
  (is (= (solve2 input) "229,192,11")))
