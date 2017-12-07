(ns advent-of-code.2016.day4-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2016.day4 :refer :all]))

(def test-input "aaaaa-bbb-z-y-x-123[abxyz]\na-b-c-d-e-f-g-h-987[abcde]\nnot-a-real-room-404[oarel]\ntotally-real-room-200[decoy]")

(deftest test-solve1
  (is (= (solve1 test-input) 1514))
  (is (= (solve1 input) 173787)))

(deftest test-solve2
  (is (= (decrypt "qzmt-zixmtkozy-ivhz" 343)
         "very encrypted name")))
