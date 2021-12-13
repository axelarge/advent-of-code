(ns advent-of-code.y2021.day13-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day13 :refer :all]))

(def test-input "6,10\n0,14\n9,10\n0,3\n10,4\n4,11\n6,0\n6,12\n4,1\n0,13\n10,12\n3,4\n3,0\n8,4\n1,10\n2,14\n8,10\n9,0\n\nfold along y=7\nfold along x=5")

(deftest test-solve1
  (is (= (solve1 test-input) 17))
  (is (= (solve1 input) 795)))

(deftest test-solve2
  (is (= (solve2 test-input)
         (str "#####\n"
              "#   #\n"
              "#   #\n"
              "#   #\n"
              "#####")))
  (is (= (solve2 input)
         (str " ##  ####   ## #  # #    #  #  ##    ##\n"
              "#  # #       # # #  #    #  # #  #    #\n"
              "#    ###     # ##   #    #  # #       #\n"
              "#    #       # # #  #    #  # # ##    #\n"
              "#  # #    #  # # #  #    #  # #  # #  #\n"
              " ##  ####  ##  #  # ####  ##   ###  ## "))))
