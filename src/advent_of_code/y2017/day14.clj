(ns advent-of-code.y2017.day14
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.y2017.day10 :as day10]))

(def test-input "flqrgnkx")
(def input "ljoxqyyw")

(defn knot-hash [input n]
  (day10/solve2 (str input "-" n)))

(defn to-bit-string [^String hashed]
  (-> (.toString (BigInteger. hashed 16) 2)
      (left-pad 128 "0")))

(defn bitmap [input]
  (->> (range 128)
       (pmap (comp to-bit-string #(knot-hash input %)))
       vec))

(defn cardinality [m]
  (->> m
       (map (partial count-where #{\1}))
       (apply +)))

(defn neighbors [m [x0 y0]]
  (for [[xd yd] [[-1 0] [1 0] [0 -1] [0 1]]
        :let [x (+ x0 xd)
              y (+ y0 yd)]
        :when (= \1 (get-in m [x y]))]
    [x y]))

(defn connected [m from]
  (connected-nodes (partial neighbors m) from))

(defn connected-fast [m from]
  (connected-nodes-fast (partial neighbors m) from))

(defn groups [bmp]
  (group-count
    (for [x (range 128) y (range 128) :when (= \1 (get-in bmp [x y]))] [x y])
    (partial connected bmp)))

(def solve1 (comp cardinality bitmap))
(def solve2 (comp groups bitmap))
