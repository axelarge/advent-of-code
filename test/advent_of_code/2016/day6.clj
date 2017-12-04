(ns advent-of-code.2016.day6
  (:require [clojure.test :refer :all]))

(def test-input "eedadn\ndrvtee\neandsr\nraavrd\natevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar")

(deftest test-solve1
  (is (= (solve1 test-input) "easter")))

(deftest test-solve2
  (is (= (solve2 test-input) "advent")))
