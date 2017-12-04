(ns advent-of-code.2016.day7-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2016.day7 :refer :all]))


(deftest test-solve1
  (is (= (solve1 "abba[mnop]qrst") 1))
  (is (= (solve1 "abcd[bddb]xyyx") 0))
  (is (= (solve1 "aaaa[qwer]tyui") 0))
  (is (= (solve1 "ioxxoj[asdfgh]zxcvbn") 1)))

(deftest test-solve2
  (is (= (solve2 "aba[bab]xyz") 1))
  (is (= (solve2 "xyx[xyx]xyx") 0))
  (is (= (solve2 "aaa[kek]eke") 1))
  (is (= (solve2 "zazbz[bzb]cdb") 1)))
