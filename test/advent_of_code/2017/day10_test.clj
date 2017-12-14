(ns advent-of-code.2017.day10-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day10 :refer :all]
            [criterium.core :refer [quick-bench]]))

(deftest test-solve1
  (is (= (solve1 5 "3, 4, 1, 5") 12))
  (is (= (solve1 input)) 13760))

(deftest test-hex-str
  (is (= (to-hex [64 7 255]) "4007ff")))

(deftest test-solve2
  (is (= (solve2 "")
         "a2582a3a0e66e6e86e3812dcb672a272"))
  (is (= (solve2 "AoC 2017")
         "33efeb34ea91902bb2f59c9920caa6cd"))
  (is (= (solve2 "1,2,3")
         "3efbe78a8d82f29979031a4aa0b16a9d"))
  (is (= (solve2 "1,2,4")
         "63960835bcdc130f0b66d7ff4f6a5a8e"))
  (is (= (solve2 input)
         "2da93395f1a6bb3472203252e3b17fe5")))
