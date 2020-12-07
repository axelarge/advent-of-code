(ns advent-of-code.2020.day07-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2020.day07 :refer :all]))

(def test-input1 "light red bags contain 1 bright white bag, 2 muted yellow bags.\ndark orange bags contain 3 bright white bags, 4 muted yellow bags.\nbright white bags contain 1 shiny gold bag.\nmuted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\nshiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\ndark olive bags contain 3 faded blue bags, 4 dotted black bags.\nvibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\nfaded blue bags contain no other bags.\ndotted black bags contain no other bags.")
(def test-input2 "shiny gold bags contain 2 dark red bags.\ndark red bags contain 2 dark orange bags.\ndark orange bags contain 2 dark yellow bags.\ndark yellow bags contain 2 dark green bags.\ndark green bags contain 2 dark blue bags.\ndark blue bags contain 2 dark violet bags.\ndark violet bags contain no other bags.")

(deftest test-solve1
  (is (= (solve1 test-input1) 4))
  (is (= (solve1 input) 115)))

(deftest test-solve2
  (is (= (solve2 test-input1) 32))
  (is (= (solve2 test-input2) 126))
  (is (= (solve2 input) 1250)))
