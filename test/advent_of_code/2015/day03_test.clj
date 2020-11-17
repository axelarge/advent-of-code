(ns advent-of-code.2015.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day03 :refer :all]))

(deftest test-solve1
  (is (= (solve1 ">") 2))
  (is (= (solve1 "^>v<") 4))
  (is (= (solve1 "^v^v^v^v^v") 2))
  (is (= (solve1 input) 2565)))

(deftest test-solve2
  (is (= (solve2 "^v") 3))
  (is (= (solve2 "^>v<") 3))
  (is (= (solve2 "^v^v^v^v^v") 11))
  (is (= (solve2 input) 2639)))
