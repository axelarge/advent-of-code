(ns advent-of-code.y2022.day01
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2022 1))

(defn parse [input]
  (->> (str/split input #"\n\n")
       (map #(->> % str/split-lines (map parse-int) (reduce +)))
       (sort (comp - compare))))

(defn solve1 [input]
  (first (parse input)))

(defn solve2 [input]
  (->> input parse (take 3) (reduce +)))
