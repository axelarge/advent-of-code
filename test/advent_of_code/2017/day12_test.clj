(ns advent-of-code.2017.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day12 :refer :all]))

(def test-input
  (str "0 <-> 2\n"
       "1 <-> 1\n"
       "2 <-> 0, 3, 4\n"
       "3 <-> 2, 4\n"
       "4 <-> 2, 3, 6\n"
       "5 <-> 6\n"
       "6 <-> 4, 5"))

(deftest test-solve1
  (is (= (solve1 test-input) 6))
  (is (= (solve1 input) 128)))

(deftest test-solve2
  (is (= (solve2 test-input) 2))
  (is (= (solve2 input) 209)))
