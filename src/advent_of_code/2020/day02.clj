(ns advent-of-code.2020.day02
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 2))

(defn parse-line [line]
  (let [[x y] (mapv parse-int (re-seq #"\d+" line))
        [c pass] (re-seq #"[a-z]+" line)]
    [x y (first c) pass]))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn valid? [[lo hi c pass]]
  (<= lo (count-where #{c} pass) hi))

(defn valid2? [[x y c pass]]
  (->> [x y]
       (map #(nth pass (dec %)))
       (count-where #{c})
       (= 1)))

(defn solve1 [input]
  (count-where valid? (parse input)))

(defn solve2 [input]
  (count-where valid2? (parse input)))
