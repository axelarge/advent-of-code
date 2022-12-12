(ns advent-of-code.y2022.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day12 :refer :all]))

(def test-input "Sabqponm\nabcryxxl\naccszExk\nacctuvwj\nabdefghi")

(deftest test-solve1
  (is (= (solve1 test-input) 31))
  (is (= (solve1 input) 520)))

(deftest test-solve2
  (is (= (solve2 test-input) 29))
  (is (= (solve2 input) 508)))
