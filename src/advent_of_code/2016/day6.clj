(ns advent-of-code.2016.day6
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (get-input 2016 6))

(defn parse-lines [lines]
  (->> (str/split-lines lines)
       (map #(str/split % #""))
       transpose))

(defn find-element [f coll]
  (first (apply f second (frequencies coll))))

(defn solve [f input]
  (->> input
       parse-lines
       (map (partial find-element f))
       (apply str)))

(def solve1 (partial solve max-key))
(def solve2 (partial solve min-key))
