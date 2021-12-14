(ns advent-of-code.y2021.day14-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day14 :refer :all]))

(def test-input "NNCB\n\nCH -> B\nHH -> N\nCB -> H\nNH -> C\nHB -> C\nHC -> B\nHN -> C\nNN -> C\nBH -> H\nNC -> B\nNB -> B\nBN -> B\nBB -> N\nBC -> B\nCC -> N\nCN -> C")

(deftest test-solve1
  (is (= (solve1 test-input) 1588))
  (is (= (solve1 input) 2112)))

(deftest test-solve2
  (is (= (solve2 test-input) 2188189693529))
  (is (= (solve2 input) 3243771149914)))
