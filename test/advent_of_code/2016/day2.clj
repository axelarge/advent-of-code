(ns advent-of-code.2016.day2
  (:require [clojure.test :refer :all]))

(def test-input "ULL\nRRDDD\nLURDL\nUUUUD")

(deftest test-solve1
  (is (= (solve1 test-input) "1985")))

(deftest test-solve2
  (is (= (solve2 test-input) "5DB3")))

