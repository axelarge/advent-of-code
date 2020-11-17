(ns advent-of-code.2015.day07-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day07 :refer :all]))

(def test-input "123 -> x\n456 -> y\nx AND y -> d\nx OR y -> e\nx LSHIFT 2 -> f\ny RSHIFT 2 -> g\nNOT x -> h\nNOT y -> i")

(deftest test-solve1
  (is (= (run {} (parse test-input)) {:d 72 :e 507 :f 492 :g 114 :h 65412 :i 65079 :x 123 :y 456}))
  (is (= (solve1 input) 956)))

(deftest test-solve2
  (is (= (solve2 input) 40149)))
