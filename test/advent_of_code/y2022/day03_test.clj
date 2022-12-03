(ns advent-of-code.y2022.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day03 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 8123)))

(deftest test-solve2
  (is (= (solve2 input) 2620)))
