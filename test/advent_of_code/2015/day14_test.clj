(ns advent-of-code.2015.day14-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day14 :refer :all]))

(def test-input "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\nDancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.")

(deftest test-solve1
  (is (= (:name (first (leaders 1000 (parse test-input)))) "Comet"))
  (is (= (dist-after 1000 (first (leaders 1000 (parse test-input)))) 1120))
  (is (= (solve1 input) 2655)))

(deftest test-solve2
  (is (= (score-after 1000 (parse test-input)) {"Dancer" 689 "Comet" 312}))
  (is (= (solve2 input) 1059)))
