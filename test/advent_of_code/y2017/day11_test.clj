(ns advent-of-code.y2017.day11-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day11 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "ne,ne,ne") 3))
  (is (= (solve1 "ne,ne,sw,sw") 0))
  (is (= (solve1 "ne,ne,s,s") 2))
  (is (= (solve1 "se,sw,se,sw,sw") 3))
  (is (= (solve1 input) 773)))

(deftest test-solve2
  (is (= (solve2 input) 1560)))
