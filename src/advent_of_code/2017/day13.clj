(ns advent-of-code.2017.day13
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input "0: 3\n1: 2\n4: 4\n6: 4")
(def input (get-input 2017 13))

(defn parse-line [line]
  (mapv parse-int (str/split line #"[^\d]+")))

(defn parse [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn position [[t n]]
  (mod t (* 2 (dec n))))

(defn severity [[t n]]
  (* t n))

(defn offset [parsed delay]
  (map (fn [[t n]] [(+ t delay) n]) parsed))

(defn total-severity [parsed]
  (->> parsed
       (filter (comp zero? position))
       (map severity)
       (apply +)))

(def solve1 (comp total-severity parse))

(defn solve2 [input]
  (let [parsed (parse input)]
    (->> (range)
         (filter (comp zero? total-severity (partial offset parsed)))
         first)))
