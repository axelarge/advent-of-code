(ns advent-of-code.2018.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day02 :refer :all]))

(def test-input-1
  (str "abcdef\nbababc\nabbcde\nabcccd\naabcdd\nabcdee\nababab"))

(def test-input-2
  (str "abcde\nfghij\nklmno\npqrst\nfguij\naxcye\nwvxyz"))

(deftest test-solve1
  (is (= (solve1 test-input-1) 12))
  (is (= (solve1 input) 7808)))

(deftest test-solve2
  (is (= (solve2 test-input-2) "fgij"))
  (is (= (solve2 input) "efmyhuckqldtwjyvisipargno")))
