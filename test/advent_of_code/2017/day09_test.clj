(ns advent-of-code.2017.day09-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day09 :refer :all]))

(deftest test-solve1
  (is (= (:groups (solve "{}")) 1))
  (is (= (:groups (solve "{{{}}}")) 3))
  (is (= (:groups (solve "{{},{}}")) 3))
  (is (= (:groups (solve "{{{},{},{{}}}}")) 6))
  (is (= (:groups (solve "{<{},{},{{}}>}")) 1))
  (is (= (:groups (solve "{<a>,<a>,<a>,<a>}")) 1))
  (is (= (:groups (solve "{{<a>},{<a>},{<a>},{<a>}}")) 5))
  (is (= (:groups (solve "{{<!>},{<!>},{<!>},{<a>}}")) 2))

  (is (= (solve1 "{}") 1))
  (is (= (solve1 "{{{}}}") 6))
  (is (= (solve1 "{{},{}}") 5))
  (is (= (solve1 "{{{},{},{{}}}}") 16))
  (is (= (solve1 "{<a>,<a>,<a>,<a>}") 1))
  (is (= (solve1 "{{<ab>},{<ab>},{<ab>},{<ab>}}") 9))
  (is (= (solve1 "{{<!!>},{<!!>},{<!!>},{<!!>}}") 9))
  (is (= (solve1 "{{<a!>},{<a!>},{<a!>},{<ab>}}") 3))

  (is (= (solve1 input) 14212)))


(deftest test-solve2
  (is (= (solve2 "<>") 0))
  (is (= (solve2 "<random characters>") 17))
  (is (= (solve2 "<<<<>") 3))
  (is (= (solve2 "<{!>}>") 2))
  (is (= (solve2 "<!!>") 0))
  (is (= (solve2 "<!!!>>") 0))
  (is (= (solve2 "<{o\"i!a,<{i<a>") 10))

  (is (= (solve2 input) 6569)))
