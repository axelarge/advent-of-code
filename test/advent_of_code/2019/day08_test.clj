(ns advent-of-code.2019.day08-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2019.day08 :refer :all]
            [clojure.string :as str]))

(deftest test-solve1
  (is (= (solve1 input) 2975)))

(deftest test-solve2
  (is (= (solve2 "0222112222120000" 2 2) ".#\n#."))
  (is (= (solve2 input)
         (str/join "\n"
                   ["####.#..#.###..#..#.####."
                    "#....#..#.#..#.#..#.#...."
                    "###..####.#..#.#..#.###.."
                    "#....#..#.###..#..#.#...."
                    "#....#..#.#.#..#..#.#...."
                    "####.#..#.#..#..##..####."]))))
