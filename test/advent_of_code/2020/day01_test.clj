(ns advent-of-code.2020.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day01 :refer :all]))

(def test-input "1721\n979\n366\n299\n675\n1456")

(deftest test-solve1
  (is (= (solve1 test-input) 514579))
  (is (= (solve1 input) 326211)))

(deftest test-solve2
  (is (= (solve2 test-input) 241861950))
  (is (= (solve2 input) 131347190)))
