(ns advent-of-code.y2022.day17-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day17 :refer :all]))


(def test-input ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(deftest test-solve1
  (is (= (solve1 test-input) 3068))
  (is (= (solve1 input) 3202)))

(deftest test-solve2
  (is (= (solve2 test-input) 1514285714288))
  (is (= (solve2 input) 1591977077352)))
