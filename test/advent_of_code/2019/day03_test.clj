(ns advent-of-code.2019.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2019.day03 :refer :all]))

(def test-input-1 "R8,U5,L5,D3\nU7,R6,D4,L4")
(def test-input-2 "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83")
(def test-input-3 "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7")

(deftest test-solve1
  (is (= (solve1 test-input-1) 6))
  (is (= (solve1 test-input-2) 159))
  (is (= (solve1 test-input-3) 135))
  (is (= (solve1 input) 557)))

(deftest test-solve2
  (is (= (solve2 test-input-1) 30))
  (is (= (solve2 test-input-2) 610))
  (is (= (solve2 test-input-3) 410))
  (is (= (solve2 input) 56410)))
