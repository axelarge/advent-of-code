(ns advent-of-code.y2020.day03-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2020.day03 :refer :all]))

(def test-input "..##.......\n#...#...#..\n.#....#..#.\n..#.#...#.#\n.#...##..#.\n..#.##.....\n.#.#.#....#\n.#........#\n#.##...#...\n#...##....#\n.#..#...#.#")

(deftest test-solve1
  (is (= (solve1 test-input) 7))
  (is (= (solve1 input) 259)))

(deftest test-solve2
  (is (= (solve2 test-input) 336))
  (is (= (solve2 input) 2224913600)))
