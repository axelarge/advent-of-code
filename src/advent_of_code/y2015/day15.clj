(ns advent-of-code.y2015.day15
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations permutations]]))

(def input (get-input 2015 15))

(defn parse-line [line]
  (reverse (find-ints line)))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn buckets [total n]
  (if (= 1 n)
    [[total]]
    (->> (range (inc total))
         (mapcat (fn [i]
                   (->> (buckets (- total i) (dec n))
                        (map #(cons i %))))))))

(defn combine [spices amounts]
  (->> (map (fn [spice n] (map #(* % n) spice))
            spices amounts)
       (apply map +)
       (map #(max 0 %))))

(defn cookies [spices]
  (->> (buckets 100 (count spices))
       (map #(combine spices %))))

(defn score [cookie]
  (reduce * (rest cookie)))

(defn solve1 [input]
  (->> (parse input)
       (cookies)
       (map score)
       (reduce max)))

(defn solve2 [input]
  (->> (parse input)
       (cookies)
       (filter #(= 500 (first %)))
       (map score)
       (reduce max)))
