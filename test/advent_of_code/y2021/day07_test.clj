(ns advent-of-code.y2021.day07-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day07 :refer :all]))

(def test-input "16,1,2,0,4,2,7,1,2,14")

(deftest test-solve1
  (is (= (solve1 test-input) 37))
  (is (= (solve1 input) 356992)))

(deftest test-solve2
  (is (= (solve2 test-input) 168))
  (is (= (solve2 input) 101268110)))
