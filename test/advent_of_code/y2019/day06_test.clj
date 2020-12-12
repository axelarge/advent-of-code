(ns advent-of-code.y2019.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2019.day06 :refer :all]))

(def test-input "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L")
(def test-input2 "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L\nK)YOU\nI)SAN")

(deftest test-solve1
  (is (= 42 (solve1 test-input)))
  (is (= 150150 (solve1 input))))

(deftest test-solve2
  (is (= (solve-path (parse test-input2)) ["K" "J" "E" "D" "I"]))
  (is (= (solve2 test-input2) 4))
  (is (= (solve2 input) 352)))
