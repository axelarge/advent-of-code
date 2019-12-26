(ns advent-of-code.2019.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2019.day05 :refer :all]
            [advent-of-code.2019.intcode :as intcode]))


(deftest test-solve1
  (is (every? zero? (-> (intcode/parse-state input)
                        (intcode/set-input [1])
                        (intcode/result)
                        :output
                        butlast)))
  (is (= 7839346 (solve1 input))))

(deftest test-solve2
  (is (= (solve2 input) 447803)))
