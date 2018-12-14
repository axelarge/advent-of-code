(ns advent-of-code.2018.day14-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day14 :refer :all]))

(deftest test-solve1
  (is (= (solve1 "9") "5158916779"))
  (is (= (solve1 "5") "0124515891"))
  (is (= (solve1 "18") "9251071085"))
  (is (= (solve1 "2018") "5941429882"))
  (is (= (solve1 input) "2107929416")))

(deftest test-solve2
  (is (= (solve2 "51589") 9))
  (is (= (solve2 "01245") 5))
  (is (= (solve2 "92510") 18))
  (is (= (solve2 "59414") 2018))
  #_(is (= (solve2 input) 20307394)))
