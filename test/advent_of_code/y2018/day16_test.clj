(ns advent-of-code.y2018.day16-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day16 :refer :all]))

(deftest test-solve1
  (is (= (solve1 input) 560)))

(deftest test-solve2
  (is (= (solve2 input) 622)))
