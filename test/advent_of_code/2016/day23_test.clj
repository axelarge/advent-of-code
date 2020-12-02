(ns advent-of-code.2016.day23-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2016.day23 :refer :all]))

(def test-input "cpy 2 a\ntgl a\ntgl a\ntgl a\ncpy 1 a\ndec a\ndec a")

(deftest test-solve1
  (is (= (solve1 test-input) 3))
  (is (= (solve1 input) 12748)))

(deftest test-solve2
  (is (= (solve2 input) 479009308)))
