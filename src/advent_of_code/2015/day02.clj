(ns advent-of-code.2015.day02
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 2))

(defn parse-line [line]
  (sort (map parse-int (re-seq #"\d+" line))))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn paper [[x y z]]
  (+ (* 2 x y)
     (* 2 x z)
     (* 2 y z)
     (* x y)))

(defn ribbon [[x y z]]
  (+ (* 2 (+ x y))
     (* x y z)))

(defn solve1 [input]
  (->> input
       (parse)
       (map paper)
       (reduce +)))

(defn solve2 [input]
  (->> input
       (parse)
       (map ribbon)
       (reduce +)))
