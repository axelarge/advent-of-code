(ns advent-of-code.2017.day13-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day13 :refer :all]))

(deftest test-solve1
  (is (= (solve1 test-input) 24))
  (is (= (solve1 input) 1316)))

(deftest test-solve2
  (is (= (solve2 test-input) 10))
  #_(is (= (solve2 input) 3840052)))
