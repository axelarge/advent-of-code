(ns advent-of-code.y2022.day23-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2022.day23 :refer :all]))

(def test-input "....#..\n..###.#\n#...#.#\n.#...##\n#.###..\n##.#.##\n.#..#..")
(deftest test-solve1
  (is (= (solve1 test-input) 110))
  (is (= (solve1 input) 3990)))

(deftest test-solve2
  (is (= (solve2 test-input) 20))
  (is (= (solve2 input) 1057)))
