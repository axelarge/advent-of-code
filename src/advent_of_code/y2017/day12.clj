(ns advent-of-code.y2017.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 12))

(defn parse-line [line]
  (let [[from & to] (find-ints line)]
    [from to]))

(defn parse [input]
  (->> input
       str/split-lines
       (into {} (map parse-line))))

(defn solve1 [input]
  (-> input parse (connected-nodes 0) count))

(defn solve2 [input]
  (let [refs (parse input)]
    (group-count (keys refs)
                 (partial connected-nodes refs))))
