(ns advent-of-code.2015.day08-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day08 :refer :all]))

(def test-input "\"\"\n\"abc\"\n\"aaa\\\"aaa\"\n\"\\x27\"")

(deftest test-solve1
  (is (= (solve1 test-input) 12))
  (is (= (solve1 input) 1371)))

(deftest test-solve2
  (is (= (solve2 test-input) 19))
  (is (= (solve2 input) 2117)))
