(ns advent-of-code.y2016.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2016.day03 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "5 10 25") 0))
  (is (= (solve1 "5 10 14") 1))
  (is (= (solve1 input) 993)))

(deftest test-solve2
  (is (= (solve2 (str "101 301 501\n"
                      "102 302 502\n"
                      "103 303 503\n"
                      "201 401 601\n"
                      "202 402 602\n"
                      "203 403 603\n"))
         6))
  (is (= (solve2 input) 1849)))

