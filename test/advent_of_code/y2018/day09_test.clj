(ns advent-of-code.y2018.day09-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day09 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "10 players; last marble is worth 1618 points") 8317))
  ;(is (= (solve1 "13 players; last marble is worth 7999 points") 146373))
  (is (= (solve1 "17 players; last marble is worth 1104 points") 2764))
  (is (= (solve1 "21 players; last marble is worth 6111 points") 54718))
  (is (= (solve1 "30 players; last marble is worth 5807 points") 37305)))
  ;(is (= (solve1 input) 399745)))

(deftest test-solve2
  (is (= (solve2 input))))
