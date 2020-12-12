(ns advent-of-code.y2017.day21
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str "../.# => ##./#../...\n"
       ".#./..#/### => #..#/..../..../#..#"))
(def input (get-input 2017 21))

(def start ".#./..#/###")

(defn flatten-2d [m]
  (mapcat (partial apply mapv (comp (partial apply concat) vector))
          m))

(defn redistribute [m]
  (let [size (count m)
        n (find-where #(zero? (rem size %)) [2 3])]
    (->> m
         (partition n)
         (mapv (fn [rows-in-chunk]
                 (->> rows-in-chunk
                      (mapv #(partition n %))
                      (apply mapv vector)))))))

(def flip-h (partial mapv (comp vec rseq)))
(def flip-v (comp vec rseq))
(def rotate-180 (comp flip-v flip-h))

(defn alternatives [m]
  (->> [m (transpose m)]
       (mapcat (juxt identity flip-h flip-v rotate-180))))

(defn to-matrix [s]
  (mapv vec (str/split s #"/")))

(defn parse-line [line]
  (let [[from to] (str/split line #" => ")]
    (for [from' (alternatives (to-matrix from))]
      [from' to])))

(defn parse [input]
  (->> input
       str/split-lines
       (into {} (mapcat parse-line))))

(defn step [rules m]
  (->> (redistribute m)
       (map (partial map (comp to-matrix rules)))
       flatten-2d))

(defn solve [steps input]
  (let [rules (parse input)]
    (->> (to-matrix start)
         (iterate (partial step rules))
         (#(nth % steps))
         (apply concat)
         (count-where #{\#}))))

(def solve1 (partial solve 5))
(def solve2 (partial solve 18))
