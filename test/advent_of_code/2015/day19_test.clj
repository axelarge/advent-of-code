(ns advent-of-code.2015.day19-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2015.day19 :refer :all]
            [clojure.string :as str]))

(def test-rules
  (->> "e => H\ne => O\nH => HO\nH => OH\nO => HH"
       (str/split-lines)
       (map parse-rule)))

(deftest test-solve1
  (is (= (num-results test-rules "HOH") 4))
  (is (= (num-results test-rules "HOHOHO") 7))
  (is (= (solve1 input) 535)))

(deftest test-solve2
  (is (= (steps-to-build test-rules "HOH") 3))
  (is (= (steps-to-build test-rules "HOHOHO") 6))
  (is (= (solve2 input) 212)))
