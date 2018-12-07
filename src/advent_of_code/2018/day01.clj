(ns advent-of-code.2018.day01
  (:require [advent-of-code.support :refer :all]
            [clojure.edn :as edn]))

(def input (get-input 2018 1))

(defn parse [input]
  (edn/read-string (str "[" input "]")))

(defn solve1 [input]
  (->> input
       parse
       (apply + 0)))

(defn solve2 [input]
  (->> input
       parse
       (cycle)
       (reductions + 0)
       (reduce (fn [seen x]
                 (if (seen x)
                   (reduced x)
                   (conj seen x)))
               #{})))
