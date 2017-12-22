(ns advent-of-code.2017.day22-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day22 :refer :all]))

(deftest test-solve1
  (is (= (:infected (solve1 test-input 7)) 5))
  (is (= (:infected (solve1 test-input 70)) 41))
  (is (= (:infected (solve1 test-input 10000)) 5587))
  (is (= (:infected (solve1 input 10000)) 5223)))


(deftest test-solve2
  (is (= (:infected (solve2 test-input 100)) 26))
  (is (= (:infected (solve2 test-input 10000000)) 2511944))
  (is (= (:infected (solve2 input 10000000)) 2511456)))

