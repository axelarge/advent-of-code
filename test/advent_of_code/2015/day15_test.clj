(ns advent-of-code.2015.day15-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day15 :refer :all]))

(def test-input "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\nCinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3\n")

(deftest test-solve1
  (is (= (solve1 test-input) 62842880))
  (is (= (solve1 input) 13882464)))

(deftest test-solve2
  (is (= (solve2 test-input) 57600000))
  (is (= (solve2 input) 11171160)))
