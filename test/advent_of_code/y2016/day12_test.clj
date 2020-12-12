(ns advent-of-code.y2016.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2016.day12 :refer :all]))

(def test-input "cpy 41 a\ninc a\ninc a\ndec a\njnz a 2\ndec a")

(deftest test-solve1
  (is (= (solve1 test-input) 42))
  (is (= (solve1 input) 318003)))

(deftest ^:slow test-solve2
  (is (= (solve2 input) 9227657)))
