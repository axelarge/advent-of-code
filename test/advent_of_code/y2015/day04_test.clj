(ns advent-of-code.y2015.day04-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day04 :refer :all]))

(deftest ^:slow test-solve1
  (is (= (solve1 "abcdef") 609043))
  (is (= (solve1 "pqrstuv") 1048970))
  (is (= (solve1 input) 282749)))

(deftest ^:slow test-solve2
  (is (= (solve2 input) 9962624)))
