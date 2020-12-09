(ns advent-of-code.2020.day02
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 2))

(defn parse-line [line]
  (let [[x y] (find-pos-ints line)
        [c pass] (re-seq #"[a-z]+" line)]
    [x y (first c) pass]))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn valid? [[lo hi c pass]]
  (<= lo (count-where #{c} pass) hi))

(defn valid2? [[x y c pass]]
  (xor (= c (nth pass (dec x)))
       (= c (nth pass (dec y)))))

(defn solve1 [input]
  (count-where valid? (parse input)))

(defn solve2 [input]
  (count-where valid2? (parse input)))
