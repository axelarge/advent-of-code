(ns advent-of-code.2017.day7-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2017.day7 :refer :all]
            [criterium.core :refer [quick-bench]]))

(def test-input
  (str "pbga (66)\n"
       "xhth (57)\n"
       "ebii (61)\n"
       "havc (66)\n"
       "ktlj (57)\n"
       "fwft (72) -> ktlj, cntj, xhth\n"
       "qoyq (66)\n"
       "padx (45) -> pbga, havc, qoyq\n"
       "tknk (41) -> ugml, padx, fwft\n"
       "jptl (61)\n"
       "ugml (68) -> gyxo, ebii, jptl\n"
       "gyxo (61)\n"
       "cntj (57)"))

(deftest test-solve1
  (is (= (solve1 test-input) "tknk"))
  (is (= (solve1 input) "xegshds")))

(deftest test-solve1'
  (is (= (solve1' test-input) "tknk"))
  (is (= (solve1' input) "xegshds")))

(deftest test-solve2
  (is (= (solve2 test-input) 60))
  (is (= (solve2 input) 299)))

(comment
  (quick-bench (solve1 input))
  (quick-bench (solve1' input)))
