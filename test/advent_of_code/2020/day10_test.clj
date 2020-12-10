(ns advent-of-code.2020.day10-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day10 :refer :all]))

(def test-input1 "16\n10\n15\n5\n1\n11\n7\n19\n6\n12\n4")
(def test-input2 "28\n33\n18\n42\n31\n14\n46\n20\n48\n47\n24\n23\n49\n45\n19\n38\n39\n11\n1\n32\n25\n35\n8\n17\n7\n9\n4\n2\n34\n10\n3")


(deftest test-solve1
  (is (= (solve1 test-input1) (* 7 5)))
  (is (= (solve1 test-input2) (* 22 10)))
  (is (= (solve1 input) 1920)))

(deftest test-solve2
  (is (= (solve2 test-input1) 8))
  (is (= (solve2 test-input2) 19208))
  (is (= (solve2 input) 1511207993344)))
