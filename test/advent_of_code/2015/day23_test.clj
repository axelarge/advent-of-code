(ns advent-of-code.2015.day23-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day23 :refer :all]))

(def test-input "inc a\njio a, +2\ntpl a\ninc a")

(deftest test-solve1
  (is (= ((juxt :a :b) (run (parse test-input))) [2 0]))
  (is (= (solve1 input) 184)))

(deftest test-solve2
  (is (= (solve2 input) 231)))
