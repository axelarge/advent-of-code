(ns advent-of-code.y2016.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2016.day06 :refer :all]))

(def test-input "eedadn\ndrvtee\neandsr\nraavrd\natevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar")

(deftest test-solve1
  (is (= (solve1 test-input) "easter"))
  (is (= (solve1 input) "agmwzecr")))

(deftest test-solve2
  (is (= (solve2 test-input) "advent"))
  (is (= (solve2 input) "owlaxqvq")))
