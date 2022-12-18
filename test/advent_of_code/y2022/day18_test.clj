(ns advent-of-code.y2022.day18-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day18 :refer :all]))

(def test-input "2,2,2\n1,2,2\n3,2,2\n2,1,2\n2,3,2\n2,2,1\n2,2,3\n2,2,4\n2,2,6\n1,2,5\n3,2,5\n2,1,5\n2,3,5")

(deftest test-solve1
  (is (= (solve1 test-input) 64))
  (is (= (solve1 input) 4536)))

(deftest test-solve2
  (is (= (solve2 test-input) 58))
  (is (= (solve2 input) 2606)))
