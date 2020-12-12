(ns advent-of-code.y2019.day04-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2019.day04 :refer :all]))


(deftest test-solve1
  (is (true? (valid1? "111111")))
  (is (false? (valid1? "223450")))
  (is (false? (valid1? "123789")))
  (is (= (solve1 input) 1716)))

(deftest test-solve2
  (is (true? (valid2? "112233")))
  (is (false? (valid2? "123444")))
  (is (true? (valid2? "111122")))
  (is (= (solve2 input) 1163)))
