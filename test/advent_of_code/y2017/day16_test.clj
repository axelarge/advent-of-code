(ns advent-of-code.y2017.day16-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2017.day16 :refer :all]))

(deftest test-solve1
  (is (= (solve1 5 test-input) "baedc"))
  (is (= (solve1 input) "padheomkgjfnblic")))

(deftest test-solve2
  (is (= (solve2 input) "bfcdeakhijmlgopn"))
  ;(is (= (solve2-slow input) "bfcdeakhijmlgopn"))

  (is (= (->> (keep-dancing (dancers 16) (parse input))
              (take 6)
              (map format-dancers))
         ["abcdefghijklmnop"
          "padheomkgjfnblic"
          "neghbdlkjmcpfaoi"
          "iabfhedcjmlongpk"
          "bocfhmnjldpagkei"
          "mkdgneojlhbipfca"])))

