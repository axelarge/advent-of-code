(ns advent-of-code.y2021.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code.y2021.day12 :refer :all]))


(def test-input1 "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end")
(def test-input2 "dc-end\nHN-start\nstart-kj\ndc-start\ndc-HN\nLN-dc\nHN-end\nkj-sa\nkj-HN\nkj-dc")
(def test-input3 "fs-end\nhe-DX\nfs-he\nstart-DX\npj-DX\nend-zg\nzg-sl\nzg-pj\npj-he\nRW-he\nfs-DX\npj-RW\nzg-RW\nstart-pj\nhe-WI\nzg-he\npj-fs\nstart-RW")

(deftest test-solve1
  (is (= (solve1 test-input1) 10))
  (is (= (solve1 test-input2) 19))
  (is (= (solve1 test-input3) 226))
  (is (= (solve1 input) 3369)))

(deftest test-solve2
  (is (= (solve2 test-input1) 36))
  (is (= (solve2 test-input2) 103))
  (is (= (solve2 test-input3) 3509))
  (is (= (solve2 input) 85883)))
