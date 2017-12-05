(ns advent-of-code.2017.day4-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day4 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "aa bb cc dd ee") 1))
  (is (= (solve1 "aa bb cc dd aa") 0))
  (is (= (solve1 "aa bb cc dd aaa") 1))
  (is (= (solve1) 455)))

(deftest test-solve2
  (is (= (solve2 "abcde fghij") 1))
  (is (= (solve2 "abcde xyz ecdab") 0))
  (is (= (solve2 "a ab abc abd abf abj") 1))
  (is (= (solve2 "iiii oiii ooii oooi oooo") 1))
  (is (= (solve2 "oiii ioii iioi iiio") 0))
  (is (= (solve2) 186)))
