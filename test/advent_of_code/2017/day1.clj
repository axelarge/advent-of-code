(ns advent-of-code.2017.day1
  (:require [clojure.test :refer :all]))

(deftest test-solve1
  (is (= (solve1 "1122") 3))
  (is (= (solve1 "1111") 4))
  (is (= (solve1 "1234") 0))
  (is (= (solve1 "91212129") 9)))

(deftest test-solve2
  (is (= (solve2 "1212") 6))
  (is (= (solve2 "1221") 0))
  (is (= (solve2 "123425") 4))
  (is (= (solve2 "123123") 12))
  (is (= (solve2 "12131415") 4)))
