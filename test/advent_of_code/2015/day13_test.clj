(ns advent-of-code.2015.day13-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day13 :refer :all]))

(def test-input "Alice would gain 54 happiness units by sitting next to Bob.\nAlice would lose 79 happiness units by sitting next to Carol.\nAlice would lose 2 happiness units by sitting next to David.\nBob would gain 83 happiness units by sitting next to Alice.\nBob would lose 7 happiness units by sitting next to Carol.\nBob would lose 63 happiness units by sitting next to David.\nCarol would lose 62 happiness units by sitting next to Alice.\nCarol would gain 60 happiness units by sitting next to Bob.\nCarol would gain 55 happiness units by sitting next to David.\nDavid would gain 46 happiness units by sitting next to Alice.\nDavid would lose 7 happiness units by sitting next to Bob.\nDavid would gain 41 happiness units by sitting next to Carol.")

(deftest test-solve1
  (is (= (solve1 test-input) 330))
  (is (= (solve1 input) 664)))

(deftest test-solve2
  (is (= (solve2 input) 640)))
