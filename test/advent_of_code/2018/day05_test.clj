(ns advent-of-code.2018.day05-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2018.day05 :refer :all]))

(defn react-str [input]
  (apply str (reverse (react input))))

(deftest test-solve1
  (is (= (react-str "aA") ""))
  (is (= (react-str "abBA") ""))
  (is (= (react-str "abAB") "abAB"))
  (is (= (react-str "aabAAB") "aabAAB"))
  (is (= (react-str test-input) "dabCBAcaDA"))
  (is (= (solve1 test-input) 10))
  (is (= (solve1 input) 10972)))

(deftest test-solve2
  (is (= (solve2 test-input) 4))
  (is (= (solve2 input) 5278)))
