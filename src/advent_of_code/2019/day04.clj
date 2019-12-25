(ns advent-of-code.2019.day04
  (:require [advent-of-code.support :refer :all]))

(def input "165432-707912")

(defn parse [input]
  (->> input
       (re-seq #"\d+")
       (mapv parse-int)))

(defn adjacent-digits? [pass]
  (->> (partition 2 1 pass)
       (some #(apply = %))))

(defn no-descend? [pass]
  (->> pass
       (map (comp parse-int str))
       (apply <=)))

(defn valid1? [pass]
  (boolean (and (adjacent-digits? pass)
                (no-descend? pass))))

(defn has-double? [pass]
  (->> (partition-by identity pass)
       (some #(= 2 (count %)))))

(defn valid2? [pass]
  (boolean (and (no-descend? pass)
                (has-double? pass))))

(defn solve1 [input]
  (let [[from to] (parse input)]
    (->> (range from (+ to 1))
         (map str)
         (filter valid1?)
         (count))))

(defn solve2 [input]
  (let [[from to] (parse input)]
    (->> (range from (+ to 1))
         (map str)
         (filter valid2?)
         (count))))

