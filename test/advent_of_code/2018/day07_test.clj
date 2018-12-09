(ns advent-of-code.2018.day07-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day07 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) "CABDFE"))
  (is (= (solve1 input) "FMOXCDGJRAUIHKNYZTESWLPBQV")))

(deftest test-solve2
  (is (= (solve2 test-input 2 0) 15))
  (is (= (solve2 input) 1053)))
