(ns advent-of-code.y2016.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2016.day02 :refer :all]))

(def test-input "ULL\nRRDDD\nLURDL\nUUUUD")

(deftest test-solve1
  (is (= (solve1 test-input) "1985"))
  (is (= (solve1 input) "47978")))

(deftest test-solve2
  (is (= (solve2 test-input) "5DB3"))
  (is (= (solve2 input) "659AD")))

