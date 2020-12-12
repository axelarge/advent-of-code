(ns advent-of-code.y2015.day18-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2015.day18 :refer :all]))

(def test-input ".#.#.#\n...##.\n#....#\n..#...\n#.#..#\n####..")

(deftest test-solve1
  (is (= (solve1 4 test-input) 4))
  (is (= (solve1 input) 821)))

(deftest test-solve2
  (is (= (solve2 5 test-input) 17))
  (is (= (solve2 input) 886)))
