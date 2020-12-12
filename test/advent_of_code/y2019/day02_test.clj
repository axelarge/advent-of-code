(ns advent-of-code.y2019.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2019.day02 :refer :all]
            [advent-of-code.y2019.intcode :refer [parse]]))

(deftest test-solve1
  (is (= (solve (parse "1,9,10,3,2,3,11,0,99,30,40,50"))
         (parse "3500,9,10,70,2,3,11,0,99,30,40,50")))
  (is (= (solve (parse "1,0,0,0,99"))
         (parse "2,0,0,0,99")))
  (is (= (solve (parse "2,3,0,3,99"))
         (parse "2,3,0,6,99")))
  (is (= (solve (parse "2,4,4,5,99,0"))
         (parse "2,4,4,5,99,9801")))
  (is (= (solve (parse "1,1,1,4,99,5,6,0,99"))
         (parse "30,1,1,4,2,5,6,0,99")))
  (is (= (solve1 input) 2890696)))

(deftest test-solve2
  (is (= (solve2 input) 8226)))
