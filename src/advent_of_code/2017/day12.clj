(ns advent-of-code.2017.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 12))

(defn parse-line [line]
  (let [[from & to] (map parse-int (str/split line #"[^\d]+"))]
    [from to]))

(defn parse [input]
  (->> input
       str/split-lines
       (into {} (map parse-line))))

(defn get-group [refs from seen]
  (let [to (remove seen (get refs from))
        seen (into seen to)]
    (into seen (mapcat #(get-group refs % seen) to))))

(defn solve1 [input]
  (-> input parse (get-group 0 #{0}) count))

(defn solve2 [input]
  (loop [refs (parse input)
         groups 0]
    (if-let [from (ffirst refs)]
      (let [group (get-group refs from #{from})]
        (recur (reduce dissoc refs group)
               (inc groups)))
      groups)))
