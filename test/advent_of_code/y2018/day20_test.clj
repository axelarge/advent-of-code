(ns advent-of-code.y2018.day20-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2018.day20 :refer :all]))

(deftest test-solve1
  (are [res input] (= (solve1 input) res)
       3 "^WNE$"
       10 "^ENWWW(NEEE|SSE(EE|N))$"
       18 "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"
       23 "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$"
       31 "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")

  (is (= (solve1 input) 3465)))

(deftest test-solve2
  (is (= (solve2 input) 7956)))
