(ns advent-of-code.y2017.day25-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day25 :refer :all]))

(def test-input-1 "Begin in state A.\nPerform a diagnostic checksum after 6 steps.\n\nIn state A:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state B.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state B.\n\nIn state B:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state A.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state A.")

(deftest test-solve1-sample
  (is (= (solve1 test-input-1) 3)))

(deftest ^:slow test-solve1
  (is (= (solve1 input) 3145)))
