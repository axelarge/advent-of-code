(ns advent-of-code.2017.day6-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day6 :refer :all]))

(def test-input "0 2 7 0")

(deftest test-solve1
  (is (= (solve1 test-input) 5))
  (is (= (solve1) 4074)))

(deftest test-solve2
  (is (= (solve2 test-input) 4))
  (is (= (solve2) 2793)))

(deftest test-solve1'
  (is (= (solve1' test-input) 5))
  (is (= (solve1') 4074)))

(deftest test-solve2'
  (is (= (solve2' test-input) 4))
  (is (= (solve2') 2793)))
