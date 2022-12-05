(ns advent-of-code.y2022.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day05 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) "JCMHLVGMG")))

(deftest test-solve2
  (is (= (solve2 input) "LVMRWSSPZ")))
