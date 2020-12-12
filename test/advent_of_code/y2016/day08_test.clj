(ns advent-of-code.y2016.day08-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2016.day08 :refer :all]))

(deftest test-solve1
  (is (= (->> (str "rect 3x2\n"
                   "rotate column x=1 by 1\n"
                   "rotate row y=0 by 4\n"
                   "rotate column x=1 by 1\n")
              (display (make-screen 7 3))
              draw)
        (str ".#..#.#\n"
             "#.#....\n"
             ".#....."))))
