(ns advent-of-code.2015.day04
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input "yzbqklnj")

(defn solve [prefix input]
  (->> (range)
       (find-where #(str/starts-with? (md5 (str input %)) prefix))))

(defn solve1 [input]
  (solve "00000" input))

(defn solve2 [input]
  (solve "000000" input))
